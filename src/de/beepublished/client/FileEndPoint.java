package de.beepublished.client;

import java.io.File;

public interface FileEndPoint extends EndPoint {
	public File getFiles();
	public File getSQLDump();
	public File getBackupFile();
	public void process();
}
