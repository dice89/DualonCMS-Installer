package de.beepublished.client.zip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import de.beepublished.client.exceptions.ZipVocationException;

/**
 *  Class that realizes simple zip handling
 * @author Alex
 *
 */
public class ZipEngine {
	
	/**
	 * Method to unzip a random zip file to a location
	 * 
	 * @param file The reference to the Zip File
	 * @param dest the reference to the destination the zip file should be extracted
	 * @return
	 * @throws ZipVocationException 
	 */
	public static String unzip(File file, String dest) throws ZipVocationException{

		ZipFile archive = null;
		try {
			archive = new ZipFile(file);
			  Enumeration e = archive.entries();
			    while (e.hasMoreElements()) {
			      ZipEntry entry = (ZipEntry) e.nextElement();
			      File zfile = new File(dest, entry.getName());
			      if (entry.isDirectory() && !zfile.exists()) {
			        zfile.mkdirs();
			      } else {
			        if (!zfile.getParentFile().exists()) {
			          zfile.getParentFile().mkdirs();
			        }

			        InputStream in = archive.getInputStream(entry);
			        BufferedOutputStream out = new BufferedOutputStream(
			            new FileOutputStream(file));

			        byte[] buffer = new byte[8192];
			        int read;

			        while (-1 != (read = in.read(buffer))) {
			          out.write(buffer, 0, read);
			        }

			        in.close();
			        out.close();
			      }
			    }
		} catch (ZipException e1) {
			e1.printStackTrace();
			throw new ZipVocationException(e1.getMessage());
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new ZipVocationException(e1.getMessage());
		}
	  return dest;
	}
	
	public static File zip(String location) throws ZipVocationException{
		throw new ZipVocationException("Not implemented yet");
	}
}
