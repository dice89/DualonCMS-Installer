package de.beepublished.client.http.webservice.dao;

import de.beepublished.client.http.webservice.services.LightweightService;
import de.beepublished.client.http.webservice.services.ServiceResponse;

public class HTTP_CMS_FileDownload extends LightweightService {



	@Override
	public Class<? extends ServiceResponse> getResponseClass() {
		return HTTP_CMS_FileDownload_response.class;
	}

}
