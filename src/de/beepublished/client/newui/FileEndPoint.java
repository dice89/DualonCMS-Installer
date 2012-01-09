package de.beepublished.client.newui;

import java.io.File;

public interface FileEndPoint extends EndPoint{
	public File getFiles();
	public File getSQLDump();
}