package de.beepublished.client;

import java.io.File;

import de.beepublished.client.ftp.FTPLoginInformationImpl;
import de.beepublished.client.ftp.FTPTarget;
import de.beepublished.client.zip.ZipEngine;

public class HostedBackup implements FileEndPoint {

	private File backupFile;
	private File dbFile;
	private File filesRoot;
	
	public HostedBackup() {
		super();
	}

	@Override
	public String getName() {
		return "Get latest version";
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
	public void process() throws Exception {			
		FTPTarget target = new FTPTarget(new FTPLoginInformationImpl("beepublished.de", 21, "f006089b", "ZmFZHS3ckXrAWSSY", ""));
		target.connect();
		target.login();
			
		target.setBinaryFileType();
		this.backupFile = File.createTempFile("tmp_installation_hosted_download", ".zip");
		target.downloadFile("installationfinalBeePublished.bpb.zip", backupFile);
			
		target.logout();
		target.disconnect();
			
			
		// setup temp directory
		File extracted = File.createTempFile("tmp_installation", "");
		extracted.delete();
		extracted.mkdir();
			
		ZipEngine.unzip(backupFile, extracted);
		
		filesRoot = new File(extracted.getAbsolutePath()+"/files/");
		dbFile = new File(extracted.getAbsolutePath()+"/db/cake.sql");
			
		assert(filesRoot.isDirectory());
		assert(dbFile.isFile());
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}

}

