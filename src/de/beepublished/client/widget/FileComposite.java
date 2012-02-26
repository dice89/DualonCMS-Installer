package de.beepublished.client.widget;

import java.io.File;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

import de.beepublished.client.BeePublishedClient;
import de.beepublished.client.FileBackup;
import de.beepublished.client.FileEndPoint;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class FileComposite extends Composite {
	
	private boolean isSave;
	private Text text;
	private FileEndPoint endPoint;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FileComposite(Composite parent, int style) {
		super(parent, style);
		
		text = new Text(this, SWT.BORDER);
		text.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				doAction();
			}
		});
		text.setBounds(1, 1, 215, 23);
		text.setFont(BeePublishedClient.fontStandard);
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doAction();
			}
		});
		button.setText("...");
		button.setBounds(227, 1, 34, 25);
		button.setFont(BeePublishedClient.fontStandard);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void setSave(){
		this.isSave = true;
	}
	
	public void setLoad(){
		this.isSave = false;
	}
	
	public FileEndPoint getFile(){
		return endPoint;
	}
	
	public void doAction(){
		if(isSave){
			FileDialog dialog = new FileDialog(this.getShell(), SWT.SAVE);
		    dialog.setFilterNames(new String[] { "BeePublished Backup Files"});
		    dialog.setFilterExtensions(new String[] { "*.bpb.zip"});
		    dialog.setFileName("backup.bpb.zip");
		    String fileName = dialog.open();
		    if(fileName != null){
		    	endPoint = new FileBackup(new File(fileName));
		    	text.setText(endPoint.getName());
		    }
		} else {
			FileDialog dialog = new FileDialog(this.getShell(), SWT.OPEN);
		    dialog.setFilterNames(new String[] { "BeePublished Backup Files"});
		    dialog.setFilterExtensions(new String[] { "*.bpb.zip"}); // Windows
		    String fileName = dialog.open();
		    if(fileName != null){
		    	endPoint = new FileBackup(new File(fileName));
		    	text.setText(endPoint.getName());
		    }
		}
	}
}
