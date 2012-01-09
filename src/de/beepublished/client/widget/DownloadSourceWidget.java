package de.beepublished.client.widget;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

public class DownloadSourceWidget extends Composite {
	private Text txtHttplocalhostarchivezip;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DownloadSourceWidget(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group grpDownloadSource = new Group(this, SWT.NONE);
		grpDownloadSource.setText("Download Source");
		grpDownloadSource.setLayout(new GridLayout(2, false));
		
		Label lblDualonCmsDownload = new Label(grpDownloadSource, SWT.NONE);
		lblDualonCmsDownload.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDualonCmsDownload.setText("Dualon CMS Download");
		
		txtHttplocalhostarchivezip = new Text(grpDownloadSource, SWT.BORDER);
		txtHttplocalhostarchivezip.setText("http://localhost/archive.zip");
		txtHttplocalhostarchivezip.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public String getDownloadSource(){
		return txtHttplocalhostarchivezip.getText();
	}
}
