package de.beepublished.client.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import de.beepublished.client.BeePublishedClient;
import de.beepublished.client.WebServer;
import de.beepublished.client.db.DBLoginInformationImpl;
import de.beepublished.client.ftp.FTPLoginInformationImpl;
import de.beepublished.client.pageInformation.WebPageInformationImpl;

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
		editWebPointComposite.setBounds(10, 39, 440, 442);
		
		combo = new Combo(this, SWT.NONE);
		combo.addSelectionListener(this);
		combo.setBounds(24, 10, 270, 23);
		
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
		btnCreateNew.setBounds(300, 10, 150, 25);
		btnCreateNew.setText("Create new WebServer");

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
