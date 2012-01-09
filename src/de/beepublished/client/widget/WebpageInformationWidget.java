package de.beepublished.client.widget;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class WebpageInformationWidget extends Composite {
	private Text txtHttplocalhostdualoncms;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public WebpageInformationWidget(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		Group grpWebpageInformation = new Group(this, SWT.NONE);
		grpWebpageInformation.setLayout(new GridLayout(2, false));
		FormData fd_grpWebpageInformation = new FormData();
		fd_grpWebpageInformation.bottom = new FormAttachment(100);
		fd_grpWebpageInformation.right = new FormAttachment(100);
		fd_grpWebpageInformation.top = new FormAttachment(0);
		fd_grpWebpageInformation.left = new FormAttachment(0);
		grpWebpageInformation.setLayoutData(fd_grpWebpageInformation);
		grpWebpageInformation.setText("Webpage Information");
		
		Label lblWebRoot = new Label(grpWebpageInformation, SWT.NONE);
		lblWebRoot.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblWebRoot.setText("Web Root");
		
		txtHttplocalhostdualoncms = new Text(grpWebpageInformation, SWT.BORDER);
		txtHttplocalhostdualoncms.setText("http://localhost/dualon-cms");
		txtHttplocalhostdualoncms.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public WebPageInformation getPageInformation(){
		return new WebPageInformation() {
			
			@Override
			public String getPageRoot() {
				return txtHttplocalhostdualoncms.getText();
			}
		};
	}
}
