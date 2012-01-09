package de.beepublished.client.newui;

import java.io.File;
import java.io.IOException;

import de.beepublished.client.exceptions.ZipVocationException;
import de.beepublished.client.zip.ZipEngine;

public class LocalFileEndPoint implements FileEndPoint {
	
	private File zipFile;
	private File tmpFiles;
	private File tmpSQL;
	
	public LocalFileEndPoint(File zip) throws IOException, ZipVocationException{
		this.zipFile = zip;
		
		File extracted;
		// setup temp directory
		extracted = File.createTempFile("tmp_installation", "");
		extracted.delete();
		extracted.mkdir();
		
		
		ZipEngine.unzip(zipFile, extracted);
		
		tmpFiles = new File(extracted.getAbsolutePath()+"/files/");
		tmpSQL = new File(extracted.getAbsolutePath()+"/db/cake.sql");
		
		assert(tmpFiles.isDirectory());
		assert(tmpSQL.isFile());
	}
	
	@Override
	public String getName() {
		return zipFile.getName();
	}

	@Override
	public File getFiles() {
		return tmpFiles;
	}

	@Override
	public File getSQLDump() {
		return tmpSQL;
	}

	@Override
	public String getType() {
		return "LocalFile";
	}

}
