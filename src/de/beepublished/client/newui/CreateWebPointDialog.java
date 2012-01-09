package de.beepublished.client.newui;

import javax.swing.text.Style;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

import de.beepublished.client.db.DBLoginInformation;
import de.beepublished.client.ftp.FTPLoginInformation;
import de.beepublished.client.widget.DBLoginInformationWidget;
import de.beepublished.client.widget.FTPLoginInformationWidget;
import de.beepublished.client.widget.WebPageInformation;
import de.beepublished.client.widget.WebpageInformationWidget;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CreateWebPointDialog extends Dialog {

	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public CreateWebPointDialog(Shell parent, int style) {
		super(parent, SWT.APPLICATION_MODAL);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}
	

	private Text inputName;

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 466);
		shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group grpCreateNewEndpoint = new Group(shell, SWT.NONE);
		grpCreateNewEndpoint.setText("Create new EndPoint");
		grpCreateNewEndpoint.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(grpCreateNewEndpoint, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name");
		
		inputName = new Text(grpCreateNewEndpoint, SWT.BORDER);
		inputName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblDbInformation = new Label(grpCreateNewEndpoint, SWT.NONE);
		lblDbInformation.setText("DB Information");
		
		final DBLoginInformationWidget loginInformationWidget = new DBLoginInformationWidget(grpCreateNewEndpoint, SWT.NONE);
		
		Label lblFtpInformation = new Label(grpCreateNewEndpoint, SWT.NONE);
		lblFtpInformation.setText("FTP Information");
		
		final FTPLoginInformationWidget loginInformationWidget_1 = new FTPLoginInformationWidget(grpCreateNewEndpoint, SWT.NONE);
		
		Label lblWebpageInformation = new Label(grpCreateNewEndpoint, SWT.NONE);
		lblWebpageInformation.setText("Webpage Information");
		
		final WebpageInformationWidget webpageInformationWidget = new WebpageInformationWidget(grpCreateNewEndpoint, SWT.NONE);
		new Label(grpCreateNewEndpoint, SWT.NONE);
		
		Button btnNewButton = new Button(grpCreateNewEndpoint, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = new WebEndPoint() {
					
					@Override
					public String getType() {
						return "WebServer";
					}
					
					@Override
					public String getName() {
						return inputName.getText();
					}
					
					@Override
					public WebPageInformation getPageInformation() {
						return webpageInformationWidget.getPageInformation();
					}
					
					@Override
					public FTPLoginInformation getFtpInformation() {
						return loginInformationWidget_1.getLoginInformation();
					}
					
					@Override
					public DBLoginInformation getDbInformation() {
						return loginInformationWidget.getLoginInformation();
					}
				};
			}
		});
		GridData gd_btnNewButton = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_btnNewButton.widthHint = 287;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("add new EndPoint");

	}
}
