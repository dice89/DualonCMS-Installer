package de.beepublished.client.http.webservice.dao;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;


public class HTTP_CMS_FileDownload_response extends ServiceFileStreamResponse{
	
	private static final String CMSARCHIVEFN = "archive.zip";

	public HTTP_CMS_FileDownload_response(String filename, String filetype,
			File file) {
		super(filename, filetype, file);
	}

	public static  HTTP_CMS_FileDownload_response handleResponse(ByteArrayInputStream stream){
		FileWriter fw = null;
		File f = null;
		try {
			f = new File(CMSARCHIVEFN);
			fw = new FileWriter(f);
			
			
			
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
