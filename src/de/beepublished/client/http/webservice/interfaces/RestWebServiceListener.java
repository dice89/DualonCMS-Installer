package de.beepublished.client.http.webservice.interfaces;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import de.beepublished.client.exceptions.ZipVocationException;
import de.beepublished.client.http.webservice.dao.HTTP_CMS_FileDownload_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.management.WebServiceListener;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;
import de.beepublished.client.zip.ZipEngine;


public class RestWebServiceListener implements WebServiceListener {

	public RestWebServiceListener() {
		super();

	}
	@Override
	public void onRestInstallationSuccess(
			REST_CMS_Installation_response response) {
		System.out.println("Erfolg");
		
	}

	@Override
	public void onRestInstallationFailed(ServiceException e) {
		System.out.println("blupp");
		
	}
	@Override
	public void onRestZipDownloadSuccess(ServiceFileStreamResponse response) {
		
		String extractTo = "C:\\Users\\Alex\\test";

		try {
			ZipEngine.unzip(response.getFile(), extractTo);
		} catch (ZipVocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Erfolgreich heruntergeladen und extrahiert");
		
	}
	@Override
	public void onRestZipDownloadFailed(ServiceException e) {
		System.out.println("Fail2");
		
	}

}
