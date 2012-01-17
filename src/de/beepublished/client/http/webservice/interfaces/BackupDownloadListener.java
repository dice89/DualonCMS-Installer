package de.beepublished.client.http.webservice.interfaces;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import de.beepublished.client.exceptions.ZipVocationException;
import de.beepublished.client.http.webservice.dao.REST_CMS_Backup_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.management.WebServiceListener;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;
import de.beepublished.client.zip.ZipEngine;

public class BackupDownloadListener  implements WebServiceListener  {
	
	
	
	private String rootdir = null;

	public BackupDownloadListener(String rootdir) {
		super();
		this.rootdir = rootdir;

	}

	@Override
	public void onRestZipDownloadSuccess(ServiceFileStreamResponse response) {
		
		//FTPTarget target = new FTPTarget(login);
		try {
			File localTarget = new File("tmp");
			/*
			target.connect();
			target.login();
			localTarget.mkdirs();
			target.downloadFTP(localTarget, rootdir);
			
			*/
			//copy backup dump in installation folder
		
			File backupFile = 	response.getFile();
			
			File backupTmpFile = new File("tmp/services/installation/cake.sql");
			//delete old File
			backupTmpFile.delete();
			//rename backupfile for installation service
			backupFile.renameTo(backupTmpFile);
			
			//Zip the Content of tmp
			
			ZipEngine.zip(localTarget.getPath(), "backup/backup.zip");
			//WebManager.cleanupdir("tmp");
			
		} catch (SocketException e1) {
			System.out.println("Fail");
			e1.printStackTrace();
		} catch (IOException e1) {
			System.out.println("Fail");
			e1.printStackTrace();
		} catch (ZipVocationException e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}

	@Override
	public void onRestZipDownloadFailed(ServiceException e) {
			//show error Messag
		e.printStackTrace();
	}

	@Override
	public void onRestInstallationSuccess(
			REST_CMS_Installation_response response) {
		//not used
	}

	@Override
	public void onRestInstallationFailed(ServiceException e) {
		//not used
	}

	@Override
	public void onRestBackupSuccess(REST_CMS_Backup_response response) {
		//not used
	}

	@Override
	public void onRestBackupFailed(ServiceException e) {
		//not used
	}
	/*
private static FTPLoginInformation login = new FTPLoginInformation() {
		
		@Override
		public String getUserName() {
			return "web200";
		}
		
		@Override
		public int getPort() {
			return 21;
		}
		
		@Override
		public String getPassword() {
			return "PdNO4FNM";
		}
		
		@Override
		public String getHost() {
			return "web200.mis08.de";
		}

		@Override
		public String getFtpUploadRoot() {
			// TODO Auto-generated method stub
			return null;
		}
	};
	*/
}
