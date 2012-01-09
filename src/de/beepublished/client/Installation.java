package de.beepublished.client;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import de.beepublished.client.exceptions.ZipVocationException;
import de.beepublished.client.ftp.FTPTarget;
import de.beepublished.client.http.webservice.dao.REST_CMS_Backup_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.management.WebManager;
import de.beepublished.client.http.webservice.management.WebServiceListener;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;
import de.beepublished.client.widget.DBLoginInformationWidget;
import de.beepublished.client.widget.FTPLoginInformationWidget;
import de.beepublished.client.zip.ZipEngine;
import de.beepublished.client.widget.DownloadSourceWidget;
import de.beepublished.client.widget.WebpageInformationWidget;

public class Installation implements WebServiceListener{
	
	protected Shell shlDualonCmsInstaller;

	private WebManager webManager;
	
	private File downloadedZipArchive;
	private File unzipedArchive;
	/*
	private FTPLoginInformation ftpLoginInformation = new FTPLoginInformation() {
		@Override
		public String getUserName() {
			return "f005f379";
		}
		@Override
		public int getPort() {
			return 21;
		}
		@Override
		public String getPassword() {
			return "3PwtGY9UqcyQzVCo";
		}
		@Override
		public String getHost() {
			return "dualon-cms.brickit-mod.de";
		}
	};
	
	private DBLoginInformation dbLoginInformation = new DBLoginInformation() {
		@Override
		public String getUserName() {
			return "d012e6ee";
		}
		@Override
		public String getPassword() {
			return "8H5YVbdAZVPvfUak";
		}
		@Override
		public String getHost() {
			return "dualon-cms.brickit-mod.de";
		}
		@Override
		public String getDBName() {
			return "d012e6ee";
		}
	};
	*/
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {	
		try {
			Installation window = new Installation();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlDualonCmsInstaller.open();
		shlDualonCmsInstaller.layout();
		while (!shlDualonCmsInstaller.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	Button btnDownload;
	Button btnUpload;
	Button btnInstall;
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlDualonCmsInstaller = new Shell();
		shlDualonCmsInstaller.setSize(706, 364);
		shlDualonCmsInstaller.setText("Dualon CMS Installer");
		
		btnDownload = new Button(shlDualonCmsInstaller, SWT.NONE);
		btnDownload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// download
				webManager = WebManager.getWebManager();
				webManager.downloadZIPFile(sourceWidget.getDownloadSource(), Installation.this);
				
			}
		});
		btnDownload.setBounds(10, 198, 172, 41);
		btnDownload.setText("1. Download");
		
		btnUpload = new Button(shlDualonCmsInstaller, SWT.NONE);
		btnUpload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FTPTarget target = new FTPTarget(ftpWidget.getLoginInformation());
				try {
					target.connect();
					target.login();
					target.uploadFolder(unzipedArchive,ftpWidget.getLoginInformation().getFtpUploadRoot());
					target.logout();
					target.disconnect();
					System.out.println("uploaded finished");
					
					// TODO deletion fix
					//deleteDir(unzipedArchive);
					
					downloadedZipArchive.delete();
					
					changeButtonState(btnUpload, false);
					changeButtonState(btnInstall, true);
					
					
					
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnUpload.setBounds(196, 198, 244, 41);
		btnUpload.setText("2. Upload");
		btnUpload.setEnabled(false);
		
		btnInstall = new Button(shlDualonCmsInstaller, SWT.NONE);
		btnInstall.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				webManager.installCMS(dbWidget.getLoginInformation(), pageWidget.getPageInformation(), Installation.this);
			}
		});
		btnInstall.setBounds(454, 198, 226, 41);
		btnInstall.setText("3. Install");
		btnInstall.setEnabled(false);
		
		ftpWidget = new FTPLoginInformationWidget(shlDualonCmsInstaller, SWT.NONE);
		ftpWidget.setBounds(196, 10, 244, 153);
		
		Label label = new Label(shlDualonCmsInstaller, SWT.SEPARATOR | SWT.VERTICAL);
		label.setText("0");
		label.setBounds(188, 18, 2, 174);
		
		Label label_1 = new Label(shlDualonCmsInstaller, SWT.SEPARATOR);
		label_1.setText("0");
		label_1.setBounds(446, 10, 2, 182);
		
		dbWidget = new DBLoginInformationWidget(shlDualonCmsInstaller, SWT.NONE);
		dbWidget.setBounds(454, 10, 226, 127);
		
		sourceWidget = new DownloadSourceWidget(shlDualonCmsInstaller, SWT.NONE);
		sourceWidget.setBounds(10, 10, 172, 127);
		
		pageWidget = new WebpageInformationWidget(shlDualonCmsInstaller, SWT.NONE);
		pageWidget.setBounds(454, 143, 226, 49);
		
		Button btnNewButton = new Button(shlDualonCmsInstaller, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(webManager == null)
					webManager = WebManager.getWebManager();
				webManager.backupCMS(Installation.this, dbWidget.getLoginInformation(), pageWidget.getPageInformation());
			}
		});
		btnNewButton.setBounds(196, 245, 244, 30);
		btnNewButton.setText("Backup");

	}
	
	FTPLoginInformationWidget ftpWidget;
	DBLoginInformationWidget dbWidget;
	DownloadSourceWidget sourceWidget;
	WebpageInformationWidget pageWidget;

	@Override
	public void onRestZipDownloadSuccess(ServiceFileStreamResponse response) {
		try {
			System.out.println("onRestZipDownloadSuccess");
			downloadedZipArchive = response.getFile();
			
			System.out.println("start to extract zip file");
			unzipedArchive = ZipEngine.unzip(downloadedZipArchive, downloadedZipArchive.getName().substring(0, downloadedZipArchive.getName().length() -4));
			System.out.println("finished to extract zip file");

			changeButtonState(btnDownload, false);
			changeButtonState(btnUpload, true);
		} catch (ZipVocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onRestZipDownloadFailed(ServiceException e) {
		System.out.println("onRestZipDownloadFailed");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestInstallationSuccess(REST_CMS_Installation_response response) {
		System.out.println("onRestInstallationSuccess");
		// TODO Auto-generated method stub
		System.out.println("Response code: "+response.getResponseCode());
		
	}

	@Override
	public void onRestInstallationFailed(ServiceException e) {
		System.out.println("onRestInstallationFailed");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestBackupSuccess(REST_CMS_Backup_response response) {
		System.out.println("onRestBackupSuccess");
		System.out.println(response.getResponseCode());
		System.out.println(response.getSqlurl());
		System.out.println(response.getZipurl());
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestBackupFailed(ServiceException e) {
		System.out.println("onRestBackupFailed");
		// TODO Auto-generated method stub
		
	}
	
	private void changeButtonState(final Button button, final boolean newState){
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				button.setEnabled(newState);
			}
		});
	}
}
