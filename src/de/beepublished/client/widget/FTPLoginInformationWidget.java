package de.beepublished.client.widget;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.beepublished.client.ftp.FTPLoginInformation;
import de.beepublished.client.ftp.FTPLoginInformationImpl;


public class FTPLoginInformationWidget extends Composite {
	
	public static final int STATUS_UNKNOWN = 1;
	public static final int STATUS_WRONG = 2;
	public static final int STATUS_RIGHT = 3;
	
	private Text inputHost;
	private Text inputPassword;
	private Text inputPort;
	private Text inputUserName;
	
	private Label lblStatusHost;
	private Label lblStatusPw;
	private Label lblStatusPort;
	private Label lblStatusName;
	
	//private InputVerification verification;
	private Label lblDualonRoot;
	private Text inputRoot;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FTPLoginInformationWidget(Composite parent, int style) {
		super(parent, style);
		
		//verification = new InputVerification();
		
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group grpFtpLoginInformation = new Group(this, SWT.NONE);
		grpFtpLoginInformation.setText("FTP Login Information");
		grpFtpLoginInformation.setLayout(new GridLayout(3, false));
		
		Label lblHost = new Label(grpFtpLoginInformation, SWT.RIGHT);
		lblHost.setText("Host");
		
		inputHost = new Text(grpFtpLoginInformation, SWT.BORDER);
		inputHost.setText("localhost");
		//inputHost.addVerifyListener(verification);
		inputHost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblStatusHost = new Label(grpFtpLoginInformation, SWT.NONE);
		lblStatusHost.setText("?");
		
		Label lblPort = new Label(grpFtpLoginInformation, SWT.RIGHT);
		lblPort.setText("Port");
		
		inputPort = new Text(grpFtpLoginInformation, SWT.BORDER);
		inputPort.setText("21");
		//inputPort.addVerifyListener(verification);
		inputPort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblStatusPort = new Label(grpFtpLoginInformation, SWT.NONE);
		lblStatusPort.setText("?");
		
		Label lblUsername = new Label(grpFtpLoginInformation, SWT.RIGHT);
		lblUsername.setText("UserName");
		
		inputUserName = new Text(grpFtpLoginInformation, SWT.BORDER);
		inputUserName.setText("nobody");
		//inputUserName.addVerifyListener(verification);
		inputUserName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblStatusName = new Label(grpFtpLoginInformation, SWT.NONE);
		lblStatusName.setText("?");
		
		Label lblPassword = new Label(grpFtpLoginInformation, SWT.RIGHT);
		lblPassword.setText("Password");
		
		inputPassword = new Text(grpFtpLoginInformation, SWT.BORDER);
		inputPassword.setText("xampp");
		//inputPassword.addVerifyListener(verification);
		inputPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblStatusPw = new Label(grpFtpLoginInformation, SWT.NONE);
		lblStatusPw.setText("?");
		
		lblDualonRoot = new Label(grpFtpLoginInformation, SWT.NONE);
		lblDualonRoot.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDualonRoot.setText("Dualon Root");
		
		inputRoot = new Text(grpFtpLoginInformation, SWT.BORDER);
		inputRoot.setText("dualon-cms");
		inputRoot.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(grpFtpLoginInformation, SWT.NONE);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public FTPLoginInformation getLoginInformation(){
		return new FTPLoginInformationImpl(inputHost.getText(), Integer.parseInt(inputPort.getText()), inputUserName.getText(), inputPassword.getText(), inputRoot.getText());
		/*
		return new FTPLoginInformation() {
			
			@Override
			public String getUserName() {
				return inputUserName.getText();
			}
			
			@Override
			public int getPort() {
				return Integer.parseInt(inputPort.getText());
			}
			
			@Override
			public String getPassword() {
				return inputPassword.getText();
			}
			
			@Override
			public String getHost() {
				return inputHost.getText();
			}
			
			@Override
			public String getFtpUploadRoot() {
				return inputRoot.getText();
			}
		};
		*/
	}
	
	public void setHostAndPortStatus(int newStatus){
		String value = "";
		switch (newStatus) {
			case STATUS_UNKNOWN:
				value = "?";
				break;
			case STATUS_RIGHT:
				value = "OK";
				break;
			case STATUS_WRONG:
				value = "X";
		}
		lblStatusHost.setText(value);
		lblStatusPort.setText(value);
	}
	
	public void setUserAndPwStatus(int newStatus){
		String value = "";
		switch (newStatus) {
			case STATUS_UNKNOWN:
				value = "?";
				break;
			case STATUS_RIGHT:
				value = "OK";
				break;
			case STATUS_WRONG:
				value = "X";
		}
		lblStatusPw.setText(value);
		lblStatusName.setText(value);
	}

}
