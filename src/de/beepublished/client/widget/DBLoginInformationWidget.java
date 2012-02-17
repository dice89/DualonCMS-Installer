package de.beepublished.client.widget;
import org.eclipse.swt.widgets.Composite;

import de.beepublished.client.db.DBLoginInformation;
import de.beepublished.client.db.DBLoginInformationImpl;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;


//TODO create class description
public class DBLoginInformationWidget extends Composite {
	private Text inputHost;
	private Text inputName;
	private Text inputUser;
	private Text inputPasswort;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DBLoginInformationWidget(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group grpDbLoginInformation = new Group(this, SWT.NONE);
		grpDbLoginInformation.setText("DB Login Information");
		grpDbLoginInformation.setLayout(new GridLayout(2, false));
		
		Label lblDatenbankname = new Label(grpDbLoginInformation, SWT.NONE);
		lblDatenbankname.setLayoutData(new GridData(75, SWT.DEFAULT));
		lblDatenbankname.setText("Host");
		
		inputHost = new Text(grpDbLoginInformation, SWT.BORDER);
		inputHost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblDatenbankname_1 = new Label(grpDbLoginInformation, SWT.NONE);
		lblDatenbankname_1.setText("Name");
		
		inputName = new Text(grpDbLoginInformation, SWT.BORDER);
		inputName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblUser = new Label(grpDbLoginInformation, SWT.NONE);
		lblUser.setText("User");
		
		inputUser = new Text(grpDbLoginInformation, SWT.BORDER);
		inputUser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPasswort = new Label(grpDbLoginInformation, SWT.NONE);
		lblPasswort.setText("Password");
		
		inputPasswort = new Text(grpDbLoginInformation, SWT.BORDER | SWT.PASSWORD);
		inputPasswort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

	}
	
	public void initialize(DBLoginInformation dbInfo){
		inputHost.setText(dbInfo.getHost());
		inputName.setText(dbInfo.getDBName());
		inputPasswort.setText(dbInfo.getPassword());
		inputUser.setText(dbInfo.getUserName());
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public DBLoginInformation getLoginInformation(){
		
		return new DBLoginInformationImpl(inputHost.getText(), inputName.getText(), inputUser.getText(), inputPasswort.getText());
		
		/*
		return new DBLoginInformation() {
			
			@Override
			public String getUserName() {
				return inputUser.getText();
			}
			
			@Override
			public String getPassword() {
				// TODO Auto-generated method stub
				return inputPasswort.getText();
			}
			
			@Override
			public String getHost() {
				// TODO Auto-generated method stub
				return inputHost.getText();
			}
			
			@Override
			public String getDBName() {
				// TODO Auto-generated method stub
				return inputName.getText();
			}
		};
		*/
	}

}
