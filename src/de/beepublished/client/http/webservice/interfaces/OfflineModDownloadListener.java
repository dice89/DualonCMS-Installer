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

public class OfflineModDownloadListener implements WebServiceListener {
	
	 private String xammp_path; //lclpath to htdocs folder of xammp
	 private String ftp_root;	//serve path from ftp root to dualon root
	 private String lcl_homeUrl; 
	 private String lcl_dBHost;
	 private String lcl_dBName;
	 private String lcl_dBPw;
	 private String lcl_dBLogin; 
	 private String lcl_url;
	 private FTPLoginInformation login ;

	 public OfflineModDownloadListener(String xammp_path, String ftp_root,
				String lcl_homeUrl, String lcl_dBHost, String lcl_dBName,
				String lcl_dBPw, String lcl_dBLogin, String lcl_url,
				FTPLoginInformation login) {
			super();
			this.xammp_path = xammp_path; 
			this.ftp_root = ftp_root;
			this.lcl_homeUrl = lcl_homeUrl;
			this.lcl_dBHost = lcl_dBHost;
			this.lcl_dBName = lcl_dBName;
			this.lcl_dBPw = lcl_dBPw;
			this.lcl_dBLogin = lcl_dBLogin;
			this.lcl_url = lcl_url;
			this.login = login;
		}



	@Override
	public void onRestZipDownloadSuccess(ServiceFileStreamResponse response) {
		
		//now check the xammp dir
		FTPTarget target = new FTPTarget(login);
		try {
			//now start ftp connection 
			File localTarget = new File(xammp_path+"/dualonoffline");
			
			target.connect();
			target.login();
			localTarget.mkdirs();
			target.downloadFTP(localTarget, ftp_root);
			
			//copy backup dump in installation folder
		
			File backupFile = 	response.getFile();
			
			File backupTmpFile = new File(xammp_path+"/dualonoffline"+"/services/installation/cake.sql");
			//delete old File
			backupTmpFile.delete();
			//rename backupfile for installation service
			backupFile.renameTo(backupTmpFile);
			
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
		wmanager.downloadFile("backup/", backupfilename, response.getSqlurl(), this);
		//Trigger File download
		System.out.println("Test");
		System.out.println("backup sql dump as well from:" + response.getSqlurl());
		//dann speichern
		
	}

	@Override
	public void onRestBackupFailed(ServiceException e) {
		// TODO Auto-generated method stub
		
	}

}
