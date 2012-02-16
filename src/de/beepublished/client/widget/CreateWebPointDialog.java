package de.beepublished.client.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.beepublished.client.WebServer;

public class CreateWebPointDialog extends Composite {

	protected WebServer result;
	protected Shell shell;

	
	DBLoginInformationWidget loginInformationWidget;
	FTPLoginInformationWidget loginInformationWidget_1;
	WebpageInformationWidget webpageInformationWidget;
	
	/**
	 * Create the Composite.
	 * @param parent
	 * @param style
	 */
	public CreateWebPointDialog(Composite parent, int style) {
		super(parent, style);
		/////
		
		Group grpCreateNewEndpoint = new Group(getParent(), SWT.NONE);
		grpCreateNewEndpoint.setText("Selected Profile");
		grpCreateNewEndpoint.setLayout(new GridLayout(1, false));
		
		Label lblName = new Label(grpCreateNewEndpoint, SWT.NONE);
		lblName.setText("Name");
		
		inputName = new Text(grpCreateNewEndpoint, SWT.BORDER);
		inputName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		inputName.setText("Local XAMPP Webserver");
		
		loginInformationWidget = new DBLoginInformationWidget(grpCreateNewEndpoint, SWT.NONE);
		loginInformationWidget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		loginInformationWidget_1 = new FTPLoginInformationWidget(grpCreateNewEndpoint, SWT.NONE);
		loginInformationWidget_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		webpageInformationWidget = new WebpageInformationWidget(grpCreateNewEndpoint, SWT.NONE);
		webpageInformationWidget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Button btnNewButton = new Button(grpCreateNewEndpoint, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result.setName(inputName.getText());
				result.setDbInfo(loginInformationWidget.getLoginInformation());
				result.setFtpInfo(loginInformationWidget_1.getLoginInformation());
				result.setPageInfo(webpageInformationWidget.getPageInformation());
			}
		});
		GridData gd_btnNewButton = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_btnNewButton.widthHint = 287;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("save this Profile");

	}	

	private Text inputName;
	
	public void initializeWithWebServer(WebServer webserver){
		this.result = webserver;
		
		inputName.setText(webserver.getName());
		loginInformationWidget.initialize(webserver.getDbInformation());
		loginInformationWidget_1.initialize(webserver.getFtpInformation());
		webpageInformationWidget.initialize(webserver.getPageInformation());
	}

	
}
