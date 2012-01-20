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
			
			delegate.setFeedback("unzip...");
			source.process();

			delegate.setFeedback("connect to ftp...");
			FTPTarget ftpTarget = new FTPTarget(target.getFtpInformation());
			ftpTarget.connect();
			ftpTarget.login();
			
			delegate.setFeedback("upload files...");
			ftpTarget.uploadFolder(source.getFiles(),target.getFtpInformation().getFtpUploadRoot());
			delegate.setFeedback("upload db...");
			ftpTarget.uploadFile(source.getSQLDump().getAbsolutePath(), "services/installation/cake.sql");
			ftpTarget.logout();
			ftpTarget.disconnect();
			delegate.setFeedback("install cms...");
			
			WebManager.getWebManager().installCMS(target.getDbInformation(), target.getPageInformation(), this);
			
			while(!finished){
				Thread.sleep(100);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			delegate.setFailed();
		}
	}

	@Override
	public void onRestInstallationSuccess(
			REST_CMS_Installation_response response) {
		delegate.setFinished();		
		finished = true;
	}

	@Override
	public void onRestInstallationFailed(ServiceException e) {
		delegate.setFailed();	
		finished = true;	
	}

	@Override
	public void onRestBackupSuccess(REST_CMS_Backup_response response) {
		throw new RuntimeException("should not happen :D");
	}

	@Override
	public void onRestBackupFailed(ServiceException e) {
		throw new RuntimeException("should not happen :D");
	}
	
	@Override
	public void onRestZipDownloadSuccess(ServiceFileStreamResponse response) {
		throw new RuntimeException("should not happen :D");
	}

	@Override
	public void onRestZipDownloadFailed(ServiceException e) {
		throw new RuntimeException("should not happen :D");
	}
	
}
