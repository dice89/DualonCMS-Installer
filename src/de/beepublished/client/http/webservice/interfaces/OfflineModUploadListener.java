package de.beepublished.client.http.webservice.interfaces;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import de.beepublished.client.ftp.FTPLoginInformation;
import de.beepublished.client.ftp.FTPTarget;
import de.beepublished.client.http.webservice.dao.REST_CMS_Backup_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.management.WebManager;
import de.beepublished.client.http.webservice.management.WebServiceListener;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;

public class OfflineModUploadListener implements WebServiceListener {
	
	 private String xammp_path; //lclpath to htdocs folder of xammp
	 private String ftp_root;	//serve path from ftp root to dualon root
	 private String remote_homeUrl; 
	 private String remote_dBHost;
	 private String remote_dBName;
	 private String remote_dBPw;
	 private String remote_dBLogin; 
	 private String remote_url;
	 private FTPLoginInformation login ;





	public OfflineModUploadListener(String xammp_path, String ftp_root,
			String remote_homeUrl, String remote_dBHost, String remote_dBName,
			String remote_dBPw, String remote_dBLogin, String remote_url,
			FTPLoginInformation login) {
		super();
		this.xammp_path = xammp_path;
		this.ftp_root = ftp_root;
		this.remote_homeUrl = remote_homeUrl;
		this.remote_dBHost = remote_dBHost;
		this.remote_dBName = remote_dBName;
		this.remote_dBPw = remote_dBPw;
		this.remote_dBLogin = remote_dBLogin;
		this.remote_url = remote_url;
		this.login = login;
	}

	@Override
	public void onRestZipDownloadSuccess(ServiceFileStreamResponse response) {
		
		//now check the xammp dir
		FTPTarget target = new FTPTarget(login);
		try {
			//now start ftp connection 
			
			
	
			//copy backup dump in installation folder
		
			File backupFile = 	response.getFile();
			
			File backupTmpFile = new File(xammp_path+"/dualonoffline"+"/services/installation/cake.sql");
			//delete old File
			backupTmpFile.delete();
			//rename backupfile for installation service
			backupFile.renameTo(backupTmpFile);
			
			target.connect();
			target.login();
			target.uploadFile(xammp_path+"/dualonoffline", ftp_root+remote_url);
			
			
			//now trigger installation
			WebManager.getWebManager().installCMS(null, null, this);
			
		} catch (SocketException e1) {
			System.out.println("Fail");
			e1.printStackTrace();
		} catch (IOException e1) {
			System.out.println("Fail");
			e1.printStackTrace();
		}
		
	}

	@Override
	public void onRestZipDownloadFailed(ServiceException e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestInstallationSuccess(
			REST_CMS_Installation_response response) {
		System.out.println("Success to offline mode");
		
	}

	@Override
	public void onRestInstallationFailed(ServiceException e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestBackupSuccess(REST_CMS_Backup_response response) {
		String burl = response.getSqlurl();
		String[] burlsplit = burl.split("/");
		String backupfilename = burlsplit[burlsplit.length-1];
		WebManager wmanager = WebManager.getWebManager();

		
		FTPTarget target = new FTPTarget(login);
		try {
			//now start ftp connection 
			//copy backup dump in installation folder
		
			File backupFile = 	new File(xammp_path+"/dualonoffline/backup/"+backupfilename);
			
			File backupTmpFile = new File(xammp_path+"/dualonoffline"+"/services/installation/cake.sql");
			//delete old File
			backupTmpFile.delete();
			//rename backupfile for installation service
			backupFile.renameTo(backupTmpFile);
			
			target.connect();
			target.login();
			target.uploadFile(xammp_path+"/dualonoffline", ftp_root+remote_url);
			
			
			//now trigger installation
			WebManager.getWebManager().installCMS(null, null, this);
			
		} catch (SocketException e1) {
			System.out.println("Fail");
			e1.printStackTrace();
		} catch (IOException e1) {
			System.out.println("Fail");
			e1.printStackTrace();
		}
	}

	@Override
	public void onRestBackupFailed(ServiceException e) {
		// TODO Auto-generated method stub
		
	}

}
