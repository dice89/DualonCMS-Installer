package de.beepublished.client;

import de.beepublished.client.ftp.FTPTarget;
import de.beepublished.client.http.webservice.dao.REST_CMS_Backup_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.management.WebManager;
import de.beepublished.client.http.webservice.management.WebServiceListener;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;

/**
 * Installs the cms
 * 	Source: FileEndPoint
 * 	Target: WebServer
 * @author fabiankajzar
 *
 */
public class InstallThread extends Thread implements WebServiceListener {
	private ProgressFeedback delegate;
	private FileEndPoint source;
	private WebServer target;
	private boolean finished = false;

	public InstallThread(ProgressFeedback delegate, FileEndPoint source, WebServer target) {
		this.delegate = delegate;
		this.source = source;
		this.target = target;
	}

	@Override
	public void run() {
		try {
			delegate.setStarted();
			
			/*delegate.setFeedback("unzip...");
			source.process();

			delegate.setFeedback("connect to ftp...");
			FTPTarget ftpTarget = new FTPTarget(target.getFtpInformation());
			
			try {
				ftpTarget.connect();
			} catch (Exception e){
				delegate.setFailed(e);
				this.destroy();
				
			}
			
			try {
				ftpTarget.login();
			} catch (Exception e){
				delegate.setFailed(e);
				//this.destroy();
				
			}
			
			
			try{
			delegate.setFeedback("upload files...");
			ftpTarget.uploadFolder(source.getFiles(),target.getFtpInformation().getFtpUploadRoot());
			delegate.setFeedback("upload db...");
			ftpTarget.uploadFile(source.getSQLDump().getAbsolutePath(), "app/webroot/services/installation/cake.sql");
			ftpTarget.logout();
			ftpTarget.disconnect();
			
			delegate.setFeedback("setting chmod");
			ftpTarget.connect();
			ftpTarget.login();
			if(!target.getFtpInformation().getFtpUploadRoot().equals(""))
				ftpTarget.changeWorkingDirectory(target.getFtpInformation().getFtpUploadRoot());
			
			ftpTarget.changeWorkingDirectory("app");
			ftpTarget.changeWorkingDirectory("webroot");
			
			ftpTarget.changeCHMOD("CHMOD 777 uploads");
			} catch (Exception e){
				delegate.setFailed(e);
			}
			
			
			delegate.setFeedback("change CHMOD other folders");
			try{
			ftpTarget.logout();
			ftpTarget.disconnect();
			
			delegate.setFeedback("setting chmod");
			ftpTarget.connect();
			ftpTarget.login();
			if(!target.getFtpInformation().getFtpUploadRoot().equals(""))
				ftpTarget.changeWorkingDirectory(target.getFtpInformation().getFtpUploadRoot());
			
			
			ftpTarget.changetmpMODS();
			} catch (Exception e){
				delegate.setFailed(e);
			}
			*/
			delegate.setFeedback("install cms... WebService is called");
			
		
			
			WebManager.getWebManager().installCMS(target.getDbInformation(), target.getPageInformation(), this);
			
			
			while(!finished){
				Thread.sleep(100);
			}
			System.out.println("installed");
		} catch (Exception ex) {
			ex.printStackTrace();
			delegate.setFailed(ex);
		}
	}

	@Override
	public void onRestInstallationSuccess(
			REST_CMS_Installation_response response) {
		if(response.getResponseCode() != 0) {
				delegate.setFailed(new Exception("Wrong DB!"));
		} else {
			delegate.setFinished();		
			finished = true;
		}
		
	}

	@Override
	public void onRestInstallationFailed(ServiceException e) {
		delegate.setFailed(e);	
		finished = true;	
	}

	@Override
	public void onRestBackupSuccess(REST_CMS_Backup_response response) {
		throw new RuntimeException("Failure probably wront Root location");
	}

	@Override
	public void onRestBackupFailed(ServiceException e) {
		throw new RuntimeException("Failure probably wrong Root location");
	}
	
	@Override
	public void onRestZipDownloadSuccess(ServiceFileStreamResponse response) {
		throw new RuntimeException("Failure while downloading");
	}

	@Override
	public void onRestZipDownloadFailed(ServiceException e) {
		throw new RuntimeException("should not happen :D");
	}
	
}
