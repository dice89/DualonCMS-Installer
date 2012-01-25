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
	public String serialize(){
		return host+";"+port+";"+userName+";"+password+";"+ftpUploadRoot;
	}

	public static FTPLoginInformation deserialize(String[] values) {
		return new FTPLoginInformationImpl(values[1], Integer.parseInt(values[2]), values[3],values[4], values[5]);
		// TODO Auto-generated method stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ftpUploadRoot == null) ? 0 : ftpUploadRoot.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + port;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FTPLoginInformationImpl other = (FTPLoginInformationImpl) obj;
		if (ftpUploadRoot == null) {
			if (other.ftpUploadRoot != null)
				return false;
		} else if (!ftpUploadRoot.equals(other.ftpUploadRoot))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (port != other.port)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	
}
