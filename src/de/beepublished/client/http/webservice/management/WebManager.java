package de.beepublished.client.http.webservice.management;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;

import de.beepublished.client.ftp.FTPLoginInformation;
import de.beepublished.client.ftp.FTPTarget;
import de.beepublished.client.http.webservice.dao.HTTP_CMS_FileDownload;
import de.beepublished.client.http.webservice.dao.HTTP_CMS_FileDownload_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Backup;
import de.beepublished.client.http.webservice.dao.REST_CMS_Backup_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.interfaces.RestWebServiceListener;
import de.beepublished.client.http.webservice.services.ResponseListener;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;
import de.beepublished.client.http.webservice.services.ServiceHandler;
import de.beepublished.client.http.webservice.services.ServiceResponse;




/**
 * 
 * Singleton use getWebManager()
 * @author Alex
 *
 */
public class WebManager {
	
	private static WebManager webManager = null;
	private long userid;
	
	ServiceHandler handler;
	public WebManager(ServiceHandler handler) {
		this.handler = handler;
	}
	public void installCMS(String homeUrl, String dBHost, String dBName, String dBPw, String dBLogin, String url, final WebServiceListener listener){
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
			handler.processRequestAsynch(new REST_CMS_Installation(homeUrl, dBHost, dBName, dBPw, dBLogin, url) , responseListener);
		} catch (ServiceException e) {
			
		}
	}
	
	public void backupCMS(String dBPw, String dBLogin, String url, final WebServiceListener listener){
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
			handler.processRequestAsynch(new REST_CMS_Backup( dBPw, dBLogin, url) , responseListener);
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
			handler.processRequestAsynch(new HTTP_CMS_FileDownload(save_as,"savedestination", url), responseListener);
		} catch (ServiceException e) {
			
		}
	}
	
	
	public static void main(String args[]){
		
		WebManager wmanager = getWebManager();


		wmanager.downloadZIPFile("http://www.ms-mediagroup.de/Dualon/installation/archive.zip",  new RestWebServiceListener());
		//wmanager.installCMS("www.ms-mediagroup.de/Dualon/DualonCMS","mysql5.concept2designs.de", "db115933_10", "cms1", "db115933_10", "http://www.ms-mediagroup.de/Dualon/DualonCMS/services/installation/",new RestWebServiceListener());
		//wmanager.backupCMS("alex", "alex","http://localhost/DualonCMS/services/backup/",new RestWebServiceListener());

	
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
	public static HttpClient createHttpClient() {
		// TODO Auto-generated method stub
		//set proxy if available
		/*if(proxyHostname != null)
		{
			proxyHttpHost = new HttpHost(proxyHostname, proxyPort);
		}
 */
		HttpClient httpClient;
		//create an HTTP scheme
		SchemeRegistry schemeReg = new SchemeRegistry();
		schemeReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		//create a thread-safe HTTP connection manager and client
		ThreadSafeClientConnManager connMgr = new ThreadSafeClientConnManager(new BasicHttpParams(), schemeReg);
		//create the HTTP client out of the thread-safe connection manager and set the proxy
		httpClient = new DefaultHttpClient(connMgr, new BasicHttpParams());
	/*	if(proxyHttpHost != null)
		{
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxyHttpHost);
		}*/
		
		return httpClient;
	}
private static FTPLoginInformation login = new FTPLoginInformation() {
		
		@Override
		public String getUserName() {
			return "115933-cms";
		}
		
		@Override
		public int getPort() {
			return 21;
		}
		
		@Override
		public String getPassword() {
			return "cms1";
		}
		
		@Override
		public String getHost() {
			return "ftp.abi2008ms.de";
		}
	};
}

