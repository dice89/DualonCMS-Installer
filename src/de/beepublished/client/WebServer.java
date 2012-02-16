package de.beepublished.client;

import de.beepublished.client.WebEndPoint;
import de.beepublished.client.db.DBLoginInformation;
import de.beepublished.client.db.DBLoginInformationImpl;
import de.beepublished.client.ftp.FTPLoginInformation;
import de.beepublished.client.ftp.FTPLoginInformationImpl;
import de.beepublished.client.pageInformation.WebPageInformation;
import de.beepublished.client.pageInformation.WebPageInformationImpl;

public class WebServer implements WebEndPoint {

	public static WebServer deserialize(String serialization){
		String[] values = serialization.split(";",11);
		return new WebServer(values[0], FTPLoginInformationImpl.deserialize(values), DBLoginInformationImpl.deserialize(values), WebPageInformationImpl.deserialize(values));
	}
	
	private String name;
	private FTPLoginInformation ftpInfo;
	private DBLoginInformation dbInfo;
	private WebPageInformation pageInfo;
	
	public WebServer(String name, FTPLoginInformation ftpInfo,
			DBLoginInformation dbInfo, WebPageInformation pageInfo) {
		super();
		this.name = name;
		this.ftpInfo = ftpInfo;
		this.dbInfo = dbInfo;
		this.pageInfo = pageInfo;
	}
	
	@Override
	public String getName() {
		return name;
	}
	@Override
	public int getType() {
		return EndPointType.WEBSERVER;
	}
	@Override
	public FTPLoginInformation getFtpInformation() {
		return ftpInfo;
	}
	@Override
	public DBLoginInformation getDbInformation() {
		return dbInfo;
	}
	@Override
	public WebPageInformation getPageInformation() {
		return pageInfo;
	}

	@Override
	public String serialize() {
		return name+";"+ftpInfo.serialize()+";"+dbInfo.serialize()+";"+pageInfo.serialize();
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	

	public void setFtpInfo(FTPLoginInformation ftpInfo) {
		this.ftpInfo = ftpInfo;
	}

	public void setDbInfo(DBLoginInformation dbInfo) {
		this.dbInfo = dbInfo;
	}

	public void setPageInfo(WebPageInformation pageInfo) {
		this.pageInfo = pageInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dbInfo == null) ? 0 : dbInfo.hashCode());
		result = prime * result + ((ftpInfo == null) ? 0 : ftpInfo.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((pageInfo == null) ? 0 : pageInfo.hashCode());
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
		WebServer other = (WebServer) obj;
		if (dbInfo == null) {
			if (other.dbInfo != null)
				return false;
		} else if (!dbInfo.equals(other.dbInfo))
			return false;
		if (ftpInfo == null) {
			if (other.ftpInfo != null)
				return false;
		} else if (!ftpInfo.equals(other.ftpInfo))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pageInfo == null) {
			if (other.pageInfo != null)
				return false;
		} else if (!pageInfo.equals(other.pageInfo))
			return false;
		return true;
	}
	
	

}