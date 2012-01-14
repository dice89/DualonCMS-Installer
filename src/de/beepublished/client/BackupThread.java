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

public class BackupThread extends Thread implements WebServiceListener{
	private ProgressFeedback delegate;
	private WebServer source;
	private FileBackup target;
	
	public BackupThread(ProgressFeedback delegate, WebServer source,FileBackup target) {
		this.delegate = delegate;
		this.source = source;
		this.target = target;
	}

	@Override
	public void run() {
		delegate.setFeedback("starting backup...");
		WebManager.getWebManager().backupCMS(this, source.getDbInformation(), source.getPageInformation());
	}

	@Override
	public void onRestZipDownloadSuccess(ServiceFileStreamResponse response) {
		try {
			File f = response.getFile();
			
			FTPTarget ftpTarget = new FTPTarget(source.getFtpInformation());
			ftpTarget.connect();
			ftpTarget.login();
			System.out.println("Start to download files");
			
			// pack files together
			File result = File.createTempFile("tmp_backup", "");
			result.delete();
			result.mkdir();
			
			File fileFolder = new File(result.getAbsolutePath()+"/files/");
			fileFolder.mkdirs();
			
			File dbFolder = new File(result.getAbsolutePath()+"/db/");
			dbFolder.mkdirs();
			
			ftpTarget.downloadFTP(fileFolder,source.getFtpInformation().getFtpUploadRoot());
			ftpTarget.logout();
			ftpTarget.disconnect();
			
			copyFolder(f, new File(dbFolder.getAbsolutePath()+"/cake.sql"));
			
			System.out.println("Start to create zip");
			
			File finishedZip = ZipEngine.zip(result, target.getBackupFile());
			
			System.out.println("----------------------------------------");
			System.out.println("Backup finished: " + finishedZip.getAbsolutePath());
			System.out.println("----------------------------------------");
			
			delegate.setFinished();
			
		} catch (Exception e) {
			delegate.setFailed();
		}
		
	}

	@Override
	public void onRestZipDownloadFailed(ServiceException e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestInstallationSuccess(
			REST_CMS_Installation_response response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestInstallationFailed(ServiceException e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestBackupSuccess(REST_CMS_Backup_response response) {
		try {
			delegate.setFeedback("downloading db file...");
			WebManager.getWebManager().downloadFile("", File.createTempFile("temp", ".sql").getAbsolutePath(), response.getSqlurl(), this);
		} catch (IOException e) {
			delegate.setFeedback("Backup failed!");
			delegate.setFailed();
			e.printStackTrace();
		}
	}

	@Override
	public void onRestBackupFailed(ServiceException e) {
		delegate.setFeedback("Backup failed!");
		delegate.setFailed();
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
