package de.beepublished.client.http.webservice.dao;



import de.beepublished.client.http.webservice.services.FileService;
import de.beepublished.client.http.webservice.services.ServiceResponse;

public class HTTP_CMS_FileDownload extends FileService{


	public HTTP_CMS_FileDownload(String filename, String filedestination, String serviceURL) {
		super(filename, filedestination);
		this.serviceURL = serviceURL;
	}

	@Override
	public Class<? extends ServiceResponse> getResponseClass() {
		return HTTP_CMS_FileDownload_response.class;
	}

}
