package de.beepublished.client.http.webservice.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public abstract class FileService extends Service{
	private String filename;
	private String filedestination;
	
	
	public FileService(String filename, String filedestination) {
		super();
		this.filename = filename;
		this.filedestination = filedestination;
	}
	
	public String getFilename() {
		return filename;
	}
	public String getFiledestination() {
		return filedestination;
	}
	
	public static  ServiceFileStreamResponse handleResponse(FileService service) throws ServiceException{
		  OutputStream out = null;
		   URLConnection conn = null;
		   InputStream in = null;
		   File f =null;
		   try {
		    // Get the URL
		    URL uri =  new URL(service.getServiceURL());
		    // Open an output stream to the destination file on our local filesystem
		    f = new File(service.getFiledestination()+"" + service.getFilename());
		    f.createNewFile();
		    out = new BufferedOutputStream(new FileOutputStream(f));
		    conn = uri.openConnection();
		    in = conn.getInputStream();

		    // Get the data
		    byte[] buffer = new byte[1024];
		    int numRead;
		    while ((numRead = in.read(buffer)) != -1) {
		        out.write(buffer, 0, numRead);
		        out.flush();
		    }        
		 
		    out.close();
		    
		    
		    // Done! Just clean up and get out
		   } catch (Exception e) {
			throw new ServiceException(-1);
		}
		return new ServiceFileStreamResponse(service.getFilename(),f);
	}
}
