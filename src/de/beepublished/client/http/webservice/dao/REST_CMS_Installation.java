package de.beepublished.client.http.webservice.dao;

import org.apache.http.message.BasicNameValuePair;

import de.beepublished.client.http.webservice.services.LightweightService;
import de.beepublished.client.http.webservice.services.ServiceResponse;

public class  REST_CMS_Installation extends LightweightService {
	
	public  REST_CMS_Installation(String homeUrl, String dBHost, String dBName, String dBPw, String dBLogin, String uri){
		super();
		serviceURL = uri;
		methodKeyValuePairs.add(new BasicNameValuePair("DBHost", dBHost));
		methodKeyValuePairs.add(new BasicNameValuePair("DBName",dBName));
		methodKeyValuePairs.add(new BasicNameValuePair("DBPw", dBPw));
		methodKeyValuePairs.add(new BasicNameValuePair("DBLogin", dBLogin));
		methodKeyValuePairs.add(new BasicNameValuePair("HomeUrl", homeUrl));
	}

	@Override
	public Class<? extends ServiceResponse> getResponseClass() {
		return REST_CMS_Installation_response.class;
	}

}
