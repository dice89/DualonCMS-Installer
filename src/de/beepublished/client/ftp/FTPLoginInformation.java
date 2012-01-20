package de.beepublished.client.ftp;


public interface FTPLoginInformation {
	public String getHost();
	public int getPort();
	public String getUserName();
	public String getPassword();	
	public String getFtpUploadRoot();
	public String serialize();
}
