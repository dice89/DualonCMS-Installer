package de.beepublished.client;

import de.beepublished.client.EndPoint;
import de.beepublished.client.db.DBLoginInformation;
import de.beepublished.client.ftp.FTPLoginInformation;
import de.beepublished.client.pageInformation.WebPageInformation;

public interface WebEndPoint extends EndPoint {
	public FTPLoginInformation getFtpInformation();
	public DBLoginInformation getDbInformation();
	public WebPageInformation getPageInformation();
}
