package de.beepublished.client.newui;

import de.beepublished.client.db.DBLoginInformation;
import de.beepublished.client.ftp.FTPLoginInformation;
import de.beepublished.client.widget.WebPageInformation;

public interface WebEndPoint extends EndPoint {
	public FTPLoginInformation getFtpInformation();
	public DBLoginInformation getDbInformation();
	public WebPageInformation getPageInformation();
}
