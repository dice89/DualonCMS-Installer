package de.beepublished.client.widget;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.beepublished.client.EndPointManager;
import de.beepublished.client.NewUI;
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
				NewUI.endPointManager.addEndPoint(newServer);
				editWebPointComposite.setServer(newServer);
				
				// select new web server in combo box
				combo.setItems(NewUI.endPointManager.getForComboBox());
				combo.select(NewUI.endPointManager.getCount()-1);
				
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
		NewUI.endPointManager.removeEndPoint(webServer);
		
		if(NewUI.endPointManager.getCount() == 0){
			WebServer next =new WebServer("", new FTPLoginInformationImpl("", 21, "", "", ""), new DBLoginInformationImpl("", "", "", ""), new WebPageInformationImpl(""));
			NewUI.endPointManager.addEndPoint(next);
		}
		combo.setItems(NewUI.endPointManager.getForComboBox());
		combo.select(0);
		editWebPointComposite.setServer((WebServer) NewUI.endPointManager.getAtIndex(0)); 
		
		NewUI.endPointManager.exportSettings("settings.bps.txt");
		
	}
	
	public void onWebServerUpdated(WebServer webServer){
		int selected = combo.getSelectionIndex();
		combo.setItems(NewUI.endPointManager.getForComboBox());
		combo.select(selected);
		NewUI.endPointManager.exportSettings("settings.bps.txt");
	}

	@Override
	public void widgetSelected(SelectionEvent e) {		
		WebServer s = (WebServer) NewUI.endPointManager.getAtIndex(((Combo) e.getSource()).getSelectionIndex());
		editWebPointComposite.setServer(s);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateComboBox(){
		String[] cbValues = NewUI.endPointManager.getForComboBox();
		combo.setItems(cbValues);
		combo.setItems(cbValues);
	}
	
}
