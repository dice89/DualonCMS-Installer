package de.beepublished.client.db;


public class DBLoginInformationImpl implements DBLoginInformation {

	String host;
	String DBName;
	String userName;
	String password;
	
	public DBLoginInformationImpl(String host, String dBName, String userName,
			String password) {
		super();
		this.host = host;
		this.DBName = dBName;
		this.userName = userName;
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public String getDBName() {
		return DBName;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	
	
}
