package de.beepublished.client.zip;

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
import java.util.zip.ZipOutputStream;

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
			            new FileOutputStream(zfile));

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
	
	public static File zip(String location, String targetFileName) throws ZipVocationException, IOException{
		File locationFile = new File(location);
		assert(locationFile.isDirectory());
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targetFileName));
		addDir(locationFile,out,locationFile);
		out.flush();
		out.close();
		return new File(targetFileName);
		
	}
	
	  static void addDir(File dirObj, ZipOutputStream out,File relativeTo) throws IOException {
		    File[] files = dirObj.listFiles();
		    byte[] tmpBuf = new byte[1024];

		    for (int i = 0; i < files.length; i++) {
		      if (files[i].isDirectory()) {
		        addDir(files[i], out,relativeTo);
		        continue;
		      }
		      FileInputStream in = new FileInputStream(files[i].getAbsolutePath());
		      System.out.println(" Adding: " + files[i].getAbsolutePath());
		      out.putNextEntry(new ZipEntry(relativeTo.toURI().relativize(new File(files[i].getAbsolutePath()).toURI()).getPath()));
		      int len;
		      while ((len = in.read(tmpBuf)) > 0) {
		        out.write(tmpBuf, 0, len);
		      }
		      out.closeEntry();
		      in.close();
		    }
		  }
}
