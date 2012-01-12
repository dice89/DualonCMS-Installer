package de.beepublished.client.http.webservice.dao;

import java.io.File;

import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;


public class HTTP_CMS_FileDownload_response extends ServiceFileStreamResponse{

	public HTTP_CMS_FileDownload_response(String filename, File file) {
		super(filename, file);
		
	}
	

	
}
