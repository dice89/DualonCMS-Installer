package de.beepublished.client;

import java.io.File;
import java.io.IOException;

import de.beepublished.client.exceptions.ZipVocationException;
import de.beepublished.client.zip.ZipEngine;

public class FileBackup implements FileEndPoint {
	
	private File backupFile;
	private File dbFile;
	private File filesRoot;
	
	public FileBackup(File backupFile) {
		super();
		this.backupFile = backupFile;
		//filesRoot = new File(backupFile.getAbsolutePath()+"/files/");
		//dbFile = new File(backupFile.getAbsolutePath()+"/db/cake.sql");
	}

	@Override
	public String getName() {
		return backupFile.getName();
	}

	@Override
	public int getType() {
		return EndPointType.FILE;
	}

	@Override
	public File getFiles() {
		return filesRoot;
	}

	@Override
	public File getSQLDump() {
		return dbFile;
	}

	@Override
	public File getBackupFile() {
		return backupFile;
	}

	@Override
	public void process() {
		File extracted;
		// setup temp directory
		try {
			extracted = File.createTempFile("tmp_installation", "");
			extracted.delete();
			extracted.mkdir();
			
			
			ZipEngine.unzip(backupFile, extracted);
			
			filesRoot = new File(extracted.getAbsolutePath()+"/files/");
			dbFile = new File(extracted.getAbsolutePath()+"/db/cake.sql");
			
			assert(filesRoot.isDirectory());
			assert(dbFile.isFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZipVocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
