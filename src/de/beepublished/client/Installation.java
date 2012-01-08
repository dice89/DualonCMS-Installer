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

public class Installation implements WebServiceListener{
	
	private final String dualonZipUrl = "http://localhost/archive.zip";

	protected Shell shell;

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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
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
		shell = new Shell();
		shell.setSize(706, 232);
		shell.setText("SWT Application");
		
		btnDownload = new Button(shell, SWT.NONE);
		btnDownload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// download
				webManager = WebManager.getWebManager();
				webManager.downloadZIPFile(downloadSourceWidget.getDownloadSource(), Installation.this);
				
			}
		});
		btnDownload.setBounds(10, 143, 172, 41);
		btnDownload.setText("1. Download");
		
		btnUpload = new Button(shell, SWT.NONE);
		btnUpload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FTPTarget target = new FTPTarget(loginInformationWidget.getLoginInformation());
				try {
					target.connect();
					target.login();
					target.uploadFolder(unzipedArchive,"");
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
		btnUpload.setBounds(196, 143, 244, 41);
		btnUpload.setText("2. Upload");
		btnUpload.setEnabled(false);
		
		btnInstall = new Button(shell, SWT.NONE);
		btnInstall.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				webManager.installCMS("http://dualon-cms.brickit-mod.de", "http://dualon-cms.brickit-mod.de/services/installation/", Installation.this , loginInformationWidget_1.getLoginInformation());
			}
		});
		btnInstall.setBounds(454, 143, 226, 41);
		btnInstall.setText("3. Install");
		btnInstall.setEnabled(false);
		
		loginInformationWidget = new FTPLoginInformationWidget(shell, SWT.NONE);
		loginInformationWidget.setBounds(196, 10, 244, 127);
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.VERTICAL);
		label.setText("0");
		label.setBounds(188, 18, 2, 150);
		
		Label label_1 = new Label(shell, SWT.SEPARATOR);
		label_1.setText("0");
		label_1.setBounds(446, 10, 2, 158);
		
		loginInformationWidget_1 = new DBLoginInformationWidget(shell, SWT.NONE);
		loginInformationWidget_1.setBounds(454, 10, 226, 127);
		
		downloadSourceWidget = new DownloadSourceWidget(shell, SWT.NONE);
		downloadSourceWidget.setBounds(10, 10, 172, 127);

	}
	
	FTPLoginInformationWidget loginInformationWidget;
	DBLoginInformationWidget loginInformationWidget_1;
	DownloadSourceWidget downloadSourceWidget;

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
	public void onRestInstallationSuccess(
			REST_CMS_Installation_response response) {
		System.out.println("onRestInstallationSuccess");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestInstallationFailed(ServiceException e) {
		System.out.println("onRestInstallationFailed");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestBackupSuccess(REST_CMS_Backup_response response) {
		System.out.println("onRestBackupSuccess");
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
