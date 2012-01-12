package de.beepublished.client.http.webservice.management;

import de.beepublished.client.http.webservice.dao.REST_CMS_Backup_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;



public interface WebServiceListener {
	public void onRestZipDownloadSuccess(ServiceFileStreamResponse response);
	public void onRestZipDownloadFailed(ServiceException e);
	
	public void onRestInstallationSuccess(REST_CMS_Installation_response response);
	public void onRestInstallationFailed(ServiceException e);
	
	public void onRestBackupSuccess(REST_CMS_Backup_response response);
	public void onRestBackupFailed(ServiceException e);


}
