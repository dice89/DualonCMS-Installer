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
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;

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
		grpQuelle.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(grpQuelle, SWT.NONE);
		
		inputFolderPath = new Text(composite, SWT.BORDER);
		
		Button buttonBrowseFolder = new Button(composite, SWT.NONE);
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
		GroupLayout gl_composite = new GroupLayout(composite);
		gl_composite.setHorizontalGroup(
			gl_composite.createParallelGroup(GroupLayout.LEADING)
				.add(GroupLayout.TRAILING, gl_composite.createSequentialGroup()
					.addContainerGap()
					.add(inputFolderPath, GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
					.add(18)
					.add(buttonBrowseFolder)
					.addContainerGap())
		);
		gl_composite.setVerticalGroup(
			gl_composite.createParallelGroup(GroupLayout.LEADING)
				.add(gl_composite.createSequentialGroup()
					.add(gl_composite.createParallelGroup(GroupLayout.LEADING)
						.add(gl_composite.createSequentialGroup()
							.addContainerGap()
							.add(buttonBrowseFolder))
						.add(gl_composite.createSequentialGroup()
							.add(17)
							.add(inputFolderPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(243, Short.MAX_VALUE))
		);
		composite.setLayout(gl_composite);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public String getFolder(){
		return inputFolderPath.getText();
	}
}
