package de.beepublished.client.http.webservice.services;

import java.io.File;

public class ServiceFileStreamResponse extends ServiceResponse{
	private String filename;
	private File file;
	
	
	public ServiceFileStreamResponse(String filename,File file) {
		super();
		this.filename = filename;

		this.file = file;
	}


	public String getFilename() {
		return filename;
	}

	public File getFile() {
		return file;
	}
	
}
