package de.beepublished.client.http.webservice.interfaces;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import de.beepublished.client.exceptions.ZipVocationException;
import de.beepublished.client.ftp.FTPLoginInformation;
import de.beepublished.client.ftp.FTPTarget;
import de.beepublished.client.http.webservice.dao.HTTP_CMS_FileDownload_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Backup_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.management.WebManager;
import de.beepublished.client.http.webservice.management.WebServiceListener;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;
import de.beepublished.client.zip.ZipEngine;


public class RestWebServiceListener implements WebServiceListener {
	
	private String rootdir = null;
	
	public RestWebServiceListener(String rootdir) {
		super();
		this.rootdir = rootdir;
	}
	
	public RestWebServiceListener() {
		super();

	}
	@Override
	public void onRestInstallationSuccess(
			REST_CMS_Installation_response response) {
		System.out.println("Der erste umfassende Test ist gelungen!");	
	}

	@Override
	public void onRestInstallationFailed(ServiceException e) {
		System.out.println("blupp");
		
	}
	@Override
	public void onRestZipDownloadSuccess(ServiceFileStreamResponse response) {
		System.out.println("Zip download success... start unzip");
		String extractTo = "tmp";

		try {
			ZipEngine.unzip(response.getFile(), extractTo);
		} catch (ZipVocationException e) {
			
			e.printStackTrace();
		}
		System.out.println("unzip sucess start .... start ftp upload"); 
		
		// Trigger FTP Upload
		FTPTarget target = new FTPTarget(login);
		try{
			target.connect();
			target.login();
			target.uploadFolder(new File(extractTo+""), "/html/dualon");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("ftp completed  ....start installing");
		//Cleanup the mess
		WebManager.cleanupdir("tmp");
		WebManager wmanager = WebManager.getWebManager();
		wmanager.installCMS(null,null, this);
	}
	@Override
	public void onRestZipDownloadFailed(ServiceException e) {
		System.out.println("Fail2");
		
	}
	@Override
	public void onRestBackupSuccess(REST_CMS_Backup_response response) {
		String burl = response.getSqlurl();
		String[] burlsplit = burl.split("/");
		String backupfilename = burlsplit[burlsplit.length-1];
		WebManager wmanager = WebManager.getWebManager();
		wmanager.downloadFile("backup/", backupfilename, response.getSqlurl(), new BackupDownloadListener(rootdir));
		//Trigger File download
		System.out.println("Test");
		System.out.println("backup sql dump as well from:" + response.getSqlurl());
		//dann speichern
	}
	@Override
	public void onRestBackupFailed(ServiceException e) {
		//Trigger nothing
		System.out.println("Fail");
	}
	
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
	};
}
