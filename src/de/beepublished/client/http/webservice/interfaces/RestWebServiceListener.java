package de.beepublished.client.http.webservice.interfaces;




import de.beepublished.client.http.webservice.dao.REST_CMS_Backup_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.management.WebManager;
import de.beepublished.client.http.webservice.management.WebServiceListener;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;




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
		/*
		System.out.println("Zip download success... start unzip");
		String extractTo = "tmp";

		try {
			//ZipEngine.unzip(response.getFile(), extractTo);
		} catch (ZipVocationException e) {
			
			e.printStackTrace();
		}
		System.out.println("unzip sucess start .... start ftp upload"); 
		
		// Trigger FTP Upload
		//FTPTarget target = new FTPTarget(login);
		try{
			target.connect();
			target.login();
			target.uploadFolder(new File(extractTo+""), login.getFtpUploadRoot());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("ftp completed  ....start installing");
		//Cleanup the mess
		WebManager.cleanupdir("tmp");
		WebManager wmanager = WebManager.getWebManager();

		wmanager.installCMS(null,null, this);
		*/

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

}
