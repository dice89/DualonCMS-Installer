package de.beepublished.client.widget;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;

public class DownloadSourceWidget extends Composite {
	private Text text;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DownloadSourceWidget(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		
		Group grpDownloadSource = new Group(this, SWT.NONE);
		grpDownloadSource.setText("Download Source");
		FormData fd_grpDownloadSource = new FormData();
		fd_grpDownloadSource.bottom = new FormAttachment(0, 300);
		fd_grpDownloadSource.right = new FormAttachment(0, 450);
		fd_grpDownloadSource.top = new FormAttachment(0);
		fd_grpDownloadSource.left = new FormAttachment(0);
		grpDownloadSource.setLayoutData(fd_grpDownloadSource);
		grpDownloadSource.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text = new Text(grpDownloadSource, SWT.BORDER);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public String getDownloadSource(){
		return text.getText();
	}
}
