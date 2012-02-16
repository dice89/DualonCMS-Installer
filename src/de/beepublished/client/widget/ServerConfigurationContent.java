package de.beepublished.client.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;

import de.beepublished.client.BeePublishedClient;
import de.beepublished.client.WebServer;
import de.beepublished.client.db.DBLoginInformationImpl;
import de.beepublished.client.ftp.FTPLoginInformationImpl;
import de.beepublished.client.pageInformation.WebPageInformationImpl;
import org.eclipse.swt.widgets.Group;

public class ServerConfigurationContent extends Composite implements SelectionListener{

	EditWebPointComposite editWebPointComposite;
	Combo combo;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ServerConfigurationContent(Composite parent, int style) {
		super(parent, style);
		
		editWebPointComposite = new EditWebPointComposite(this, SWT.NONE);
		editWebPointComposite.setBounds(0, 100, 450, 442);
		
		combo = new Combo(this, SWT.NONE);
		combo.addSelectionListener(this);
		combo.setBounds(10, 71, 270, 23);
		
		Button btnCreateNew = new Button(this, SWT.NONE);
		btnCreateNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// create new WebServer
				WebServer newServer = new WebServer("New WebServer", new FTPLoginInformationImpl("", 21, "", "", ""), new DBLoginInformationImpl("", "", "", ""), new WebPageInformationImpl(""));
				BeePublishedClient.endPointManager.addEndPoint(newServer);
				editWebPointComposite.setServer(newServer);
				
				// select new web server in combo box
				combo.setItems(BeePublishedClient.endPointManager.getForComboBox());
				combo.select(BeePublishedClient.endPointManager.getCount()-1);
				
			}
		});
		btnCreateNew.setBounds(290, 69, 150, 25);
		btnCreateNew.setText("Create new Server");
		
		Group grpGeneral = new Group(this, SWT.NONE);
		grpGeneral.setText("General");
		grpGeneral.setBounds(10, 10, 430, 49);
		
		Button btnImportSettingFile = new Button(grpGeneral, SWT.NONE);
		btnImportSettingFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
			    dialog.setFilterNames(new String[] { "BeePublished Settings"});
			    dialog.setFilterExtensions(new String[] { "*.bps.txt"});
			    String fileName = dialog.open();
			    if(fileName != null){
			    	BeePublishedClient.endPointManager.importSettings(fileName);
			    	updateComboBox();
			    }
			}
		});
		btnImportSettingFile.setBounds(10, 16, 146, 25);
		btnImportSettingFile.setText("Import settings from file");
		
		Button btnExportSettingFile = new Button(grpGeneral, SWT.NONE);
		btnExportSettingFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
			    dialog.setFilterNames(new String[] { "BeePublished Settings"});
			    dialog.setFilterExtensions(new String[] { "*.bps.txt"});
			    dialog.setFileName("backup.bps.txt");
			    String fileName = dialog.open();
			    if(fileName != null){
			    	BeePublishedClient.endPointManager.exportSettings(fileName);
			    }
			}
		});
		btnExportSettingFile.setText("Export settings to file");
		btnExportSettingFile.setBounds(162, 16, 146, 25);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void onWebServerDeleted(WebServer webServer){
		BeePublishedClient.endPointManager.removeEndPoint(webServer);
		
		if(BeePublishedClient.endPointManager.getCount() == 0){
			WebServer next =new WebServer("", new FTPLoginInformationImpl("", 21, "", "", ""), new DBLoginInformationImpl("", "", "", ""), new WebPageInformationImpl(""));
			BeePublishedClient.endPointManager.addEndPoint(next);
		}
		combo.setItems(BeePublishedClient.endPointManager.getForComboBox());
		combo.select(0);
		editWebPointComposite.setServer((WebServer) BeePublishedClient.endPointManager.getAtIndex(0)); 
		
		BeePublishedClient.endPointManager.exportSettings("settings.bps.txt");
		
	}
	
	public void onWebServerUpdated(WebServer webServer){
		int selected = combo.getSelectionIndex();
		combo.setItems(BeePublishedClient.endPointManager.getForComboBox());
		combo.select(selected);
		BeePublishedClient.endPointManager.exportSettings("settings.bps.txt");
	}

	@Override
	public void widgetSelected(SelectionEvent e) {		
		WebServer s = (WebServer) BeePublishedClient.endPointManager.getAtIndex(((Combo) e.getSource()).getSelectionIndex());
		editWebPointComposite.setServer(s);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateComboBox(){
		String[] cbValues = BeePublishedClient.endPointManager.getForComboBox();
		combo.setItems(cbValues);
		combo.setItems(cbValues);
	}
}
