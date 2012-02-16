package de.beepublished.client.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import de.beepublished.client.ConfirmDialog;
import de.beepublished.client.ModalYesNoDialog;
import de.beepublished.client.WebServer;

public class EditWebPointComposite extends Composite {
	
	ProfileNameWidget nameWidget;
	DBLoginInformationWidget dbWidget;
	FTPLoginInformationWidget ftpWidget;
	WebpageInformationWidget pageWidget;
	
	WebServer currentWebServer;
	
	private Button btnNewButton;
	private Button btnDeleteProfile;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public EditWebPointComposite(final Composite parent, int style) {
		super(parent, style);
				
		nameWidget = new ProfileNameWidget(this, SWT.NONE);
		nameWidget.setBounds(10, 10, 430, 49);
		
		dbWidget = new DBLoginInformationWidget(this, SWT.NONE);
		dbWidget.setBounds(10, 65, 430, 132);
		
		ftpWidget = new FTPLoginInformationWidget(this, SWT.NONE);
		ftpWidget.setBounds(10, 203, 430, 153);
		
		pageWidget = new WebpageInformationWidget(this, SWT.NONE);
		pageWidget.setBounds(10, 364, 430, 49);
		
		btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				currentWebServer.setName(nameWidget.getName());
				currentWebServer.setDbInfo(dbWidget.getLoginInformation());
				currentWebServer.setFtpInfo(ftpWidget.getLoginInformation());
				currentWebServer.setPageInfo(pageWidget.getPageInformation());
				
				((ServerConfigurationContent) parent).onWebServerUpdated(currentWebServer);
				
				ConfirmDialog t = new ConfirmDialog(new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				t.setDialogTitle("Success");
				t.setDialogText("Server saved");
				t.open();
			}
		});
		btnNewButton.setBounds(345, 417, 95, 25);
		btnNewButton.setText("Save Server");
		
		btnDeleteProfile = new Button(this, SWT.NONE);
		btnDeleteProfile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				ModalYesNoDialog t = new ModalYesNoDialog(new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				t.setDialogTitle("Do you want to delete the server?");
				int result = t.open();
				if(result == ModalYesNoDialog.YES)
					((ServerConfigurationContent) parent).onWebServerDeleted(currentWebServer);
			}
		});
		btnDeleteProfile.setBounds(244, 417, 95, 25);
		btnDeleteProfile.setText("Delete Server");
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void setServer(WebServer webserver){
		this.currentWebServer = webserver;
		this.initializeWithWebServer();
	}
	
	private void initializeWithWebServer(){
		this.nameWidget.initialize(this.currentWebServer.getName());
		this.dbWidget.initialize(this.currentWebServer.getDbInformation());
		this.ftpWidget.initialize(this.currentWebServer.getFtpInformation());
		this.pageWidget.initialize(this.currentWebServer.getPageInformation());
	}
}
