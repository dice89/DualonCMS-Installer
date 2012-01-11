package de.beepublished.client.newui;

import java.io.File;
import java.io.IOException;

import de.beepublished.client.exceptions.ZipVocationException;

public interface FileEndPoint extends EndPoint{
	public File getFiles();
	public File getSQLDump();
	public File getBackupFile();
	public void process() throws IOException, ZipVocationException;
}