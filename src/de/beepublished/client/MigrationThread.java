package de.beepublished.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.beepublished.client.ftp.FTPTarget;
import de.beepublished.client.http.webservice.dao.REST_CMS_Backup_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.management.WebManager;
import de.beepublished.client.http.webservice.management.WebServiceListener;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;
import de.beepublished.client.zip.ZipEngine;

public class MigrationThread extends Thread implements WebServiceListener{
	private ProgressFeedback delegate;
	private WebServer source;
	private WebServer target;
	private File temp;
	private FileEndPoint tempBackup;
	

	public MigrationThread(ProgressFeedback delegate, WebServer source, WebServer target) {
		super();
		this.delegate = delegate;
		this.source = source;
		this.target = target;
	}

	@Override
	public void run() {
		// (1) //
		delegate.setFeedback("starting backup...");
		WebManager.getWebManager().backupCMS(this, source.getDbInformation(), source.getPageInformation());
	}

	@Override
	public void onRestZipDownloadSuccess(ServiceFileStreamResponse response) {
		// (3) //
		try {
			File f = response.getFile();
			

			delegate.setFeedback("connect to ftp...");
			FTPTarget ftpTarget = new FTPTarget(source.getFtpInformation());
			ftpTarget.connect();
			ftpTarget.login();
			
			// pack files together
			File result = File.createTempFile("tmp_backup", "");
			result.delete();
			result.mkdir();
			
			File fileFolder = new File(result.getAbsolutePath()+"/files/");
			fileFolder.mkdirs();
			
			File dbFolder = new File(result.getAbsolutePath()+"/db/");
			dbFolder.mkdirs();
			
			delegate.setFeedback("download from ftp...");
			ftpTarget.downloadFTP(fileFolder,source.getFtpInformation().getFtpUploadRoot());
			ftpTarget.logout();
			ftpTarget.disconnect();
			
			delegate.setFeedback("processing download...");
			copyFolder(f, new File(dbFolder.getAbsolutePath()+"/cake.sql"));
			
			System.out.println("Start to create zip");
			
			temp = File.createTempFile("tmp_file_", ".zip");
			

			delegate.setFeedback("creating backup...");
			File finishedZip = ZipEngine.zip(result, temp);
			tempBackup = new FileBackup(finishedZip);
			
			// backup finished, start installation!

			delegate.setFeedback("unzip...");
			tempBackup.process();
			

			delegate.setFeedback("connect to ftp...");
			FTPTarget ftpTargetZiel = new FTPTarget(target.getFtpInformation());
			ftpTarget.connect();
			ftpTarget.login();
			
			delegate.setFeedback("upload files...");
			ftpTarget.uploadFolder(tempBackup.getFiles(),target.getFtpInformation().getFtpUploadRoot());
			
			delegate.setFeedback("upload db...");
			ftpTarget.uploadFile(tempBackup.getSQLDump().getAbsolutePath(), "services/installation/cake.sql");
			ftpTarget.logout();
			ftpTarget.disconnect();
			delegate.setFeedback("install cms...");
			
			WebManager.getWebManager().installCMS(target.getDbInformation(), target.getPageInformation(), this);
			
		} catch (Exception e) {
			delegate.setFailed();
		}
		
	}

	@Override
	public void onRestZipDownloadFailed(ServiceException e) {
		throw new RuntimeException("should not happen :D");
	}

	@Override
	public void onRestInstallationSuccess(REST_CMS_Installation_response response) {
		delegate.setFinished();
	}

	@Override
	public void onRestInstallationFailed(ServiceException e) {
		delegate.setFailed();
	}

	@Override
	public void onRestBackupSuccess(REST_CMS_Backup_response response) {
		// (2) //
		try {
			delegate.setFeedback("downloading db...");
			WebManager.getWebManager().downloadFile("", File.createTempFile("temp", ".sql").getAbsolutePath(), response.getSqlurl(), this);
		} catch (IOException e) {
			delegate.setFailed();
			e.printStackTrace();
		}
	}

	@Override
	public void onRestBackupFailed(ServiceException e) {
		throw new RuntimeException("should not happen :D");
	}
	
	public static void copyFolder(File src, File dest) throws IOException{
		if(src.isDirectory()){
			if(!dest.exists()){
				dest.mkdir();
	    		System.out.println("Directory copied from " + src + "  to " + dest);
	    	}
	 
	    	String files[] = src.list();
	 
	    	for (String file : files) {
	    	   File srcFile = new File(src, file);
	    	   File destFile = new File(dest, file);
	    	   copyFolder(srcFile,destFile);
	    	}
	    } else {
	    	//if file, then copy it
	    	InputStream in = new FileInputStream(src);
	    	OutputStream out = new FileOutputStream(dest); 
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	//copy the file content in bytes 
	    	while ((length = in.read(buffer)) > 0){
	    		out.write(buffer, 0, length);
	    	}
	    	in.close();
	    	out.close();
	    	System.out.println("File copied from " + src + " to " + dest);
	    }
	}

}
