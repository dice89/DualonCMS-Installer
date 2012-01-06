package de.beepublished.client.http.webservice.dao;

import org.simpleframework.xml.Element;

import de.beepublished.client.http.webservice.services.ServiceResponse;

public class REST_CMS_Backup_response  extends ServiceResponse {
	
	
	@Element(name="RC", required =false)
	protected int responseCode;
	
	@Element(name="ZipUrl", required =true)
	protected String zipurl;
	
	@Element(name="SqlUrl", required =false)
	protected String  sqlurl;

	public int getResponseCode() {
		return responseCode;
	}

	public String getZipurl() {
		return zipurl;
	}

	public String getSqlurl() {
		return sqlurl;
	}
	
	
}
