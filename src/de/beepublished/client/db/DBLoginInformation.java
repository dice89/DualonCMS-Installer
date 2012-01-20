package de.beepublished.client.db;

public interface DBLoginInformation {
	public String getHost();
	public String getDBName();
	public String getUserName();
	public String getPassword();	
	public String serialize();
}
