package de.beepublished.client;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;

import org.apache.http.client.HttpClient;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;

import de.beepublished.client.exceptions.ZipVocationException;
import de.beepublished.client.ftp.FTPLoginInformation;
import de.beepublished.client.ftp.FTPTarget;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.interfaces.RestWebServiceListener;
import de.beepublished.client.http.webservice.management.WebManager;
import de.beepublished.client.http.webservice.management.WebServiceListener;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;
import de.beepublished.client.http.webservice.services.ServiceHandler;
import de.beepublished.client.widget.FTPLoginInformationWidget;
import de.beepublished.client.widget.UploadSourceWidget;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import de.beepublished.client.widget.DBLoginInformationWidget;
import de.beepublished.client.zip.ZipEngine;


//TODO create class description
public class MainWindow implements WebServiceListener {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
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
	
	FTPLoginInformationWidget loginInformationWidget;
	UploadSourceWidget uploadSourceWidget;
	Button btnUpload;
	WebManager wmanager;
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(310, 486);
		shell.setText("SWT Application");
		
		loginInformationWidget = new FTPLoginInformationWidget(shell, SWT.NONE);
		loginInformationWidget.setBounds(10, 96, 283, 124);
		
		uploadSourceWidget = new UploadSourceWidget(shell, SWT.NONE);
		uploadSourceWidget.setBounds(10, 10, 283, 64);
		
		btnUpload = new Button(shell, SWT.NONE);
		btnUpload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				
				HttpClient hclient = WebManager.createHttpClient();
				ServiceHandler shandler = new ServiceHandler(hclient);
				wmanager = new WebManager(shandler);
				

				wmanager.downloadZIPFile("http://www.ms-mediagroup.de/archive.zip",  MainWindow.this);
				
				
			}
		});
		btnUpload.setBounds(15, 382, 283, 64);
		btnUpload.setText("UPLOAD");
		
		DBLoginInformationWidget loginInformationWidget_1 = new DBLoginInformationWidget(shell, SWT.NONE);
		loginInformationWidget_1.setBounds(10, 242, 283, 118);

	}

	@Override
	public void onRestZipDownloadSuccess(ServiceFileStreamResponse response) {
		String extractTo = "tempfolder";

		try {
			ZipEngine.unzip(response.getFile(), extractTo);
		} catch (ZipVocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Erfolgreich heruntergeladen und extrahiert");
		
		FTPTarget target = new FTPTarget( new FTPLoginInformation() {
			
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
		});
		try {
			target.connect();
			target.login();
			target.uploadFolder(extractTo+"/cms","");
			target.logout();
			target.disconnect();
			
			wmanager.installCMS("dualon-cms.brickit-mod.de", "d012d73c", "d012d73c", "d012d73c", "http://dualon-cms.brickit-mod.de/cms/services/installation", this);
			
			System.out.println("Upload finished!");
			//btnUpload.setText("Success");
		} catch (FileNotFoundException e1) {
			btnUpload.setText(e1.toString());
		} catch (IOException e1) {
			btnUpload.setText(e1.toString());
		}
	}

	@Override
	public void onRestZipDownloadFailed(ServiceException e) {
		
		System.out.println("onRestZipDownloadFailed");
		
	}

	@Override
	public void onRestInstallationSuccess(
			REST_CMS_Installation_response response) {
		System.out.println("onRestInstallationSuccess");
		
	}

	@Override
	public void onRestInstallationFailed(ServiceException e) {
		System.out.println("onRestInstallationFailed");
		
	}
}
