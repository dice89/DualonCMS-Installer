package de.beepublished.client;

import java.io.File;

import de.beepublished.client.zip.ZipEngine;

public class FileBackup implements FileEndPoint {
	
	private File backupFile;
	private File dbFile;
	private File filesRoot;
	
	public FileBackup(File backupFile) {
		super();
		this.backupFile = backupFile;
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
		try {
			// setup temp directory
			File extracted = File.createTempFile("tmp_installation", "");
			extracted.delete();
			extracted.mkdir();
			
			ZipEngine.unzip(backupFile, extracted);
			
			filesRoot = new File(extracted.getAbsolutePath()+"/files/");
			dbFile = new File(extracted.getAbsolutePath()+"/db/cake.sql");
			
			assert(filesRoot.isDirectory());
			assert(dbFile.isFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}

}
