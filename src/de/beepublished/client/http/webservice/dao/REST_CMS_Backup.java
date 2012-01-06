package de.beepublished.client.http.webservice.dao;

import org.apache.http.message.BasicNameValuePair;

import de.beepublished.client.http.webservice.services.LightweightService;
import de.beepublished.client.http.webservice.services.ServiceResponse;

public class REST_CMS_Backup extends LightweightService {

	public  REST_CMS_Backup(String dBPw, String dBLogin, String uri){
		super();
		serviceURL = uri;
		methodKeyValuePairs.add(new BasicNameValuePair("DBPw", dBPw));
		methodKeyValuePairs.add(new BasicNameValuePair("DBLogin", dBLogin));

	}


	@Override
	public Class<? extends ServiceResponse> getResponseClass() {
		return REST_CMS_Backup_response.class;
	}

}
