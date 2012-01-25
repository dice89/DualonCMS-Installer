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
	@Override
	public String serialize() {
		return host+";"+DBName+";"+userName+";"+password;
	}
	public static DBLoginInformation deserialize(String[] values) {
		
		return new DBLoginInformationImpl(values[6],values[7], values[8], values[9]);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DBName == null) ? 0 : DBName.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
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
		DBLoginInformationImpl other = (DBLoginInformationImpl) obj;
		if (DBName == null) {
			if (other.DBName != null)
				return false;
		} else if (!DBName.equals(other.DBName))
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
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	
}
