package de.beepublished.client.widget;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class UploadSourceWidget extends Composite {
	private Text inputFolderPath;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public UploadSourceWidget(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group grpQuelle = new Group(this, SWT.NONE);
		grpQuelle.setText("Quelle");
		grpQuelle.setLayout(new GridLayout(2, false));
		
		inputFolderPath = new Text(grpQuelle, SWT.BORDER);
		inputFolderPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button buttonBrowseFolder = new Button(grpQuelle, SWT.NONE);
		buttonBrowseFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog fileDialog = new DirectoryDialog(getShell());
				fileDialog.setFilterPath(inputFolderPath.getText());
				fileDialog.setText("BeePublished Source");
				fileDialog.setMessage("Select the directory of the BeePublished Source");
				String selectedDirectory = fileDialog.open();
				if(selectedDirectory != null){
					inputFolderPath.setText(selectedDirectory);
				}
			}
		});
		buttonBrowseFolder.setText("...");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public String getFolder(){
		return inputFolderPath.getText();
	}
}
