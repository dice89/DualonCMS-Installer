package de.beepublished.client.http.webservice.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.conn.EofSensorInputStream;
import org.apache.http.conn.EofSensorWatcher;

import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;


public class HTTP_CMS_FileDownload_response extends ServiceFileStreamResponse{

	public HTTP_CMS_FileDownload_response(String filename, File file) {
		super(filename, file);
		
	}
	

	
}
