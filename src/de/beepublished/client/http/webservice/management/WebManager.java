package de.beepublished.client.http.webservice.management;

import java.io.File;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;

import de.beepublished.client.db.DBLoginInformation;
import de.beepublished.client.exceptions.ZipVocationException;
import de.beepublished.client.ftp.FTPLoginInformation;
import de.beepublished.client.ftp.FTPTarget;
import de.beepublished.client.http.webservice.dao.HTTP_CMS_FileDownload;
import de.beepublished.client.http.webservice.dao.HTTP_CMS_FileDownload_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Backup;
import de.beepublished.client.http.webservice.dao.REST_CMS_Backup_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.interfaces.BackupUpLoadListener;
import de.beepublished.client.http.webservice.interfaces.OfflineModDownloadListener;
import de.beepublished.client.http.webservice.interfaces.OfflineModUploadListener;
import de.beepublished.client.http.webservice.interfaces.RestWebServiceListener;
import de.beepublished.client.http.webservice.services.ResponseListener;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;
import de.beepublished.client.http.webservice.services.ServiceHandler;
import de.beepublished.client.http.webservice.services.ServiceResponse;
import de.beepublished.client.zip.ZipEngine;




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
	/**
	 * @param homeUrl root dir of dualon
	 * @param url  service remote adress
	 * @param listener
	 * @param dbLogin TODO
	 */
	public void installCMS(String homeUrl, String url, final WebServiceListener listener, DBLoginInformation dbLogin){
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
			handler.processRequestAsynch(new REST_CMS_Installation(homeUrl, dbLogin.getHost(), dbLogin.getDBName(), dbLogin.getPassword(), dbLogin.getUserName(), url) , responseListener);
		} catch (ServiceException e) {
			
		}
	}
	
	public void backupCMS(String url, final WebServiceListener listener, DBLoginInformation dbLogin){
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
			handler.processRequestAsynch(new REST_CMS_Backup( dbLogin.getPassword(), dbLogin.getUserName(), url) , responseListener);
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
	
	
	public static void main(String args[]){
		
		
		DBLoginInformation localDbLogin = new DBLoginInformation() {
			
			@Override
			public String getUserName() {
				return "root";
			}
			
			@Override
			public String getPassword() {
				return "";
			}
			
			@Override
			public String getHost() {
				return "localhost";
			}
			
			@Override
			public String getDBName() {
				return "dualon-cms";
			}
		};
		
		
		//nach jedem Service sollte ein cleanup dir von tmp durchgeführt werden
		
		
		//urls to services
		
		String installation_url  = "/services/installation";
		
		
		
		 String xammp_path = "C:/xampp/htdocs"; //lclpath to htdocs folder of xammp
		
		 //remote urls
		 String ftp_root = "/html/dualon";//server path from ftp root to dualon root
		 String remote_homeUrl = "http://www.direktbankkonten.de/dualon"; //home url under which to page is called
		 String remote_dBHost  = "localhost"; // db server normally localhost
		 String remote_dBName = "usr_web200_1"; //database name where cake is installed
		 String remote_dBPw  = "PdNO4FNM"; //db -pw
		 String remote_dBLogin = "web200"; //db login
		 //String remote_url;
		 
		 //lcl urls
		 String lcl_homeUrl = "localhost/offline"; //Home Url of localhost
		 String lcl_dBHost = "localhost"; //local database server normally localhost
		 String lcl_dBName = "offlinecake"; //local database name
		 String lcl_dBPw = "alex"; //local db password
		 String lcl_dBLogin = "alex";  //local db user
		 //String lcl_url;
		 FTPLoginInformation login; //the ftp login which is used
		
		
		//cleanupdir("tmp");
		
		WebManager wmanager = getWebManager();

		wmanager.downloadZIPFile("http://www.ms-mediagroup.de/archive.zip",  new RestWebServiceListener());
		//wmanager.installCMS("www.ms-mediagroup.de/Dualon/DualonCMS","mysql5.concept2designs.de", "db115933_10", "cms1", "db115933_10", "http://www.ms-mediagroup.de/Dualon/DualonCMS/services/installation/",new RestWebServiceListener());
		//wmanager.backupCMS("PdNO4FNM", "web200","http://www.direktbankkonten.de/dualon/services/backup/",new RestWebServiceListener("/html/dualon"));
		wmanager.installCMS("www.localhost.de/dualon", "localhost.de/dualon/services/installation/", new RestWebServiceListener(), localDbLogin);
		//wmanager.installCMS("http://www.direktbankkonten.de/dualon","localhost", "usr_web200_1", "PdNO4FNM", "web200", "http://www.direktbankkonten.de/dualon/services/installation/",new RestWebServiceListener("/html/dualon"));
		//downloadtoWorkOffline(ftp_root, "PdNO4FNM", "web200", "www.direktbankkonten.de/dualon/", xammp_path, lcl_homeUrl, lcl_dBHost, lcl_dBName, lcl_dBPw, lcl_dBLogin, installation_url );
	}
	
	/**
	 * @param pathfromFTProot
	 * @param homeUrl
	 * @param ftpLogin
	 * @param dbLogin TODO
	 */
	public static void uploadBackup(String pathfromFTProot, String homeUrl, FTPLoginInformation ftpLogin, DBLoginInformation dbLogin){
		
		WebManager wmanager = getWebManager();
		
		// Trigger FTP Upload"/html/dualon"
		File zip = new File("backup/backup.zip");
		
		FTPTarget target = new FTPTarget(ftpLogin);
		try{
			//unzip
			ZipEngine.unzip(zip, "backup/tmp");
			//upload
			target.connect();
			target.login();
			target.uploadFolder(new File("backup/tmp"), pathfromFTProot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		wmanager.installCMS(homeUrl,homeUrl+"/services/installation/", new BackupUpLoadListener(), dbLogin);
	
		
		
	}
	
	
	/**
	 * Example implementation of dowload to work offline
	 * @param pathfromFTProot
	 * @param remote_dbPw
	 * @param remote_dbUser
	 * @param remote_homeURL
	 * @param xammp_path
	 * @param lcl_homeUrl
	 * @param lcl_dBHost
	 * @param lcl_dBName
	 * @param lcl_dBPw
	 * @param lcl_dBLogin
	 * @param lcl_url
	 */
	public static void downloadtoWorkOffline(String pathfromFTProot, String remote_dbPw, String remote_dbUser, String remote_homeURL, String xammp_path, String lcl_homeUrl, String lcl_dBHost, String lcl_dBName, String lcl_dBPw, String lcl_dBLogin, String lcl_url ){
		WebManager wmanager = getWebManager();
		wmanager.backupCMS("http://"+remote_homeURL+"/services/backup/", new OfflineModDownloadListener(xammp_path, pathfromFTProot, lcl_homeUrl, lcl_dBHost, lcl_dBName, lcl_dBPw, lcl_dBLogin, lcl_url, login),null);
	}
	
	public static void uploadOfflineMode(String pathfromFTProot, String remote_dbPw, String remote_dbUser, String remote_homeURL, String xammp_path, String lcl_homeUrl, String lcl_dBHost, String lcl_dBName, String lcl_dBPw, String lcl_dBLogin, String lcl_url ){
		WebManager wmanager = getWebManager();
	
		//wmanager.installCMS(homeUrl,dBHost, dBName, dBPw, dBLogin, homeUrl+"/services/installation/",new BackupUpLoadListener());
		wmanager.backupCMS(lcl_homeUrl+lcl_url, new OfflineModUploadListener(xammp_path, pathfromFTProot, lcl_homeUrl, lcl_dBHost, lcl_dBName, lcl_dBPw, lcl_dBLogin, lcl_url, login), null);
	}
	
	/** Deletes a Dir and all underlaying structures
	 * @param path
	 */
	public static void  cleanupdir(String path){	
		 File folder = new File(path);
		 if(folder.listFiles().length > 0){
		 for(File f : folder.listFiles()){
				if(f.isFile()){
					f.delete();
				}
				if(f.isDirectory()){
					cleanupdir(f.getAbsolutePath());
					f.delete();
				}	
			}	
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
			return "web200";
		}
		
		@Override
		public int getPort() {
			return 21;
		}
		
		@Override
		public String getPassword() {
			return "PdNO4FNM";
		}
		
		@Override
		public String getHost() {
			return "web200.mis08.de";
		}
	};
}

