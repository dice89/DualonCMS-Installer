package de.beepublished.client.http.webservice.management;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;

import de.beepublished.client.db.DBLoginInformation;
import de.beepublished.client.http.webservice.dao.HTTP_CMS_FileDownload;
import de.beepublished.client.http.webservice.dao.REST_CMS_Backup;
import de.beepublished.client.http.webservice.dao.REST_CMS_Backup_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.services.ResponseListener;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;
import de.beepublished.client.http.webservice.services.ServiceHandler;
import de.beepublished.client.http.webservice.services.ServiceResponse;
import de.beepublished.client.pageInformation.WebPageInformation;


/**
 * 
 * Singleton use getWebManager()
 * @author Alex
 *
 */
public class WebManager {
	
	private static WebManager webManager = null;
	ServiceHandler handler;
	public WebManager(ServiceHandler handler) {
		this.handler = handler;
	}
	/**
	 * @param dbLogin TODO
	 * @param listener
	 * @param wenPageInformation TODO
	 */
	public void installCMS(DBLoginInformation dbLogin, WebPageInformation webPageInformation, final WebServiceListener listener){
		ResponseListener responseListener = new ResponseListener(){


			@Override
			public void onResponseFailed(String methodName,
					ServiceException exception) {
				listener.onRestInstallationFailed(exception);
			}

			@Override
			public void onResponseSuccessful(String methodName,
					ServiceResponse serviceResponse) {
				listener.onRestInstallationSuccess((REST_CMS_Installation_response)serviceResponse);
				
			}
			
		};
		
		try {
			handler.processRequestAsynch(new REST_CMS_Installation(webPageInformation.getPageRoot(), dbLogin.getHost(), dbLogin.getDBName(), dbLogin.getPassword(), dbLogin.getUserName(), webPageInformation.getPageRoot()+"/services/installation/") , responseListener);
		} catch (ServiceException e) {
			
		}
	}
	
	public void backupCMS(final WebServiceListener listener, DBLoginInformation dbLogin, WebPageInformation pageInformation){
		ResponseListener responseListener = new ResponseListener(){
			@Override
			public void onResponseFailed(String methodName,
					ServiceException exception) {
				listener.onRestBackupFailed(exception);
			}

			@Override
			public void onResponseSuccessful(String methodName,
					ServiceResponse serviceResponse) {
				listener.onRestBackupSuccess((REST_CMS_Backup_response)serviceResponse);
				
			}
			
		};
		
		try {
			handler.processRequestAsynch(new REST_CMS_Backup( dbLogin.getPassword(), dbLogin.getUserName(), pageInformation.getPageRoot()+"/services/backup/") , responseListener);
		} catch (ServiceException e) {
			
		}
	}
	
	
	public void downloadZIPFile(String url, final WebServiceListener listener){
		ResponseListener responseListener = new ResponseListener(){
			@Override
			public void onResponseFailed(String methodName,
					ServiceException exception) {
				listener.onRestInstallationFailed(exception);
			}

			@Override
			public void onResponseSuccessful(String methodName,
					ServiceResponse serviceResponse) {
				listener.onRestZipDownloadSuccess((ServiceFileStreamResponse) serviceResponse);
				
				
			}
			
		};
		
		try {
			handler.processRequestAsynch(new HTTP_CMS_FileDownload("archive.zip","", url), responseListener);
		} catch (ServiceException e) {
			
		}
	}
	
	public void downloadFile(String savedestination, String save_as, String url, final WebServiceListener listener){
		ResponseListener responseListener = new ResponseListener(){
			@Override
			public void onResponseFailed(String methodName,
					ServiceException exception) {
				listener.onRestInstallationFailed(exception);
			}

			@Override
			public void onResponseSuccessful(String methodName,
					ServiceResponse serviceResponse) {
				listener.onRestZipDownloadSuccess((ServiceFileStreamResponse) serviceResponse);
				
				
			}
			
		};
		
		try {
			handler.processRequestAsynch(new HTTP_CMS_FileDownload(save_as,savedestination, url), responseListener);
		} catch (ServiceException e) {
			
		}
	}
	
	public static WebManager getWebManager(){
		if(webManager != null){
			return webManager;
		}else {
			HttpClient hclient = createHttpClient();
			ServiceHandler shandler = new ServiceHandler(hclient);
			WebManager wmanager = new WebManager(shandler);
			return wmanager;
		}
	}
	
	
	@SuppressWarnings("deprecation")
	private static HttpClient createHttpClient() {
		HttpClient httpClient;
		//create an HTTP scheme
		SchemeRegistry schemeReg = new SchemeRegistry();
		schemeReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		//create a thread-safe HTTP connection manager and client
		ThreadSafeClientConnManager connMgr = new ThreadSafeClientConnManager(new BasicHttpParams(), schemeReg);
		//create the HTTP client out of the thread-safe connection manager and set the proxy
		httpClient = new DefaultHttpClient(connMgr, new BasicHttpParams());
		return httpClient;
	}

}

