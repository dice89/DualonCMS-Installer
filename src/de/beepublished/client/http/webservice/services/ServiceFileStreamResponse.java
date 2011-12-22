package de.beepublished.client.http.webservice.services;

import java.io.File;

public class ServiceFileStreamResponse extends ServiceResponse{
	private String filename;
	private String filetype;
	private File file;
	
	
	public ServiceFileStreamResponse(String filename, String filetype, File file) {
		super();
		this.filename = filename;
		this.filetype = filetype;
		this.file = file;
	}


	public String getFilename() {
		return filename;
	}


	public String getFiletype() {
		return filetype;
	}


	public File getFile() {
		return file;
	}
	
}
