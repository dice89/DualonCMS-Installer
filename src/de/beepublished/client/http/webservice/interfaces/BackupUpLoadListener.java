package de.beepublished.client.http.webservice.interfaces;

import de.beepublished.client.http.webservice.dao.REST_CMS_Backup_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.management.WebServiceListener;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;

public class BackupUpLoadListener implements WebServiceListener {

	@Override
	public void onRestZipDownloadSuccess(ServiceFileStreamResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestZipDownloadFailed(ServiceException e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestInstallationSuccess(
			REST_CMS_Installation_response response) {
		System.out.println("Backup Erfolgreich eingespielt");
		
	}

	@Override
	public void onRestInstallationFailed(ServiceException e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestBackupSuccess(REST_CMS_Backup_response response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestBackupFailed(ServiceException e) {
		// TODO Auto-generated method stub
		
	}

}
