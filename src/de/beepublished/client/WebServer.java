package de.beepublished.client;

import de.beepublished.client.WebEndPoint;
import de.beepublished.client.db.DBLoginInformation;
import de.beepublished.client.ftp.FTPLoginInformation;
import de.beepublished.client.pageInformation.WebPageInformation;

public class WebServer implements WebEndPoint {

	private String name;
	private int type;
	private FTPLoginInformation ftpInfo;
	private DBLoginInformation dbInfo;
	private WebPageInformation pageInfo;
	
	public WebServer(String name, int type, FTPLoginInformation ftpInfo,
			DBLoginInformation dbInfo, WebPageInformation pageInfo) {
		super();
		this.name = name;
		this.type = type;
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

}
