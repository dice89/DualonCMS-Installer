package de.beepublished.client.http.webservice.interfaces;

import de.beepublished.client.http.webservice.dao.REST_CMS_Backup_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.management.WebServiceListener;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;

public class BackupDownloadListener  implements WebServiceListener  {

	@Override
	public void onRestZipDownloadSuccess(ServiceFileStreamResponse response) {
			
		if(response.getFilename().contains("archive")){
			//save in backup Folder with specific Date as zip archive
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

}
