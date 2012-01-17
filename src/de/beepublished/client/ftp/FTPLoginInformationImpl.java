package de.beepublished.client.ftp;


public class FTPLoginInformationImpl implements FTPLoginInformation {

	String host;
	int port;
	String userName;
	String password;
	String ftpUploadRoot;
	
	public FTPLoginInformationImpl(String host, int port, String userName,
			String password, String ftpUploadRoot) {
		super();
		this.host = host;
		this.port = port;
		this.userName = userName;
		this.password = password;
		this.ftpUploadRoot = ftpUploadRoot;
	}
	
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String getFtpUploadRoot() {
		return ftpUploadRoot;
	}
	
	

}
