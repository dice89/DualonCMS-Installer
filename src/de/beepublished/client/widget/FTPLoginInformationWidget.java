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
		grpFtpLoginInformation.setLayout(new GridLayout(2, false));
		
		Label lblHost = new Label(grpFtpLoginInformation, SWT.RIGHT);
		lblHost.setAlignment(SWT.LEFT);
		lblHost.setLayoutData(new GridData(75, SWT.DEFAULT));
		lblHost.setText("Host");
		
		inputHost = new Text(grpFtpLoginInformation, SWT.BORDER);
		//inputHost.addVerifyListener(verification);
		inputHost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPort = new Label(grpFtpLoginInformation, SWT.RIGHT);
		lblPort.setText("Port");
		
		inputPort = new Text(grpFtpLoginInformation, SWT.BORDER);
		//inputPort.addVerifyListener(verification);
		inputPort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblUsername = new Label(grpFtpLoginInformation, SWT.RIGHT);
		lblUsername.setText("User");
		
		inputUserName = new Text(grpFtpLoginInformation, SWT.BORDER);
		//inputUserName.addVerifyListener(verification);
		inputUserName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPassword = new Label(grpFtpLoginInformation, SWT.RIGHT);
		lblPassword.setText("Password");
		
		inputPassword = new Text(grpFtpLoginInformation, SWT.BORDER | SWT.PASSWORD);
		//inputPassword.addVerifyListener(verification);
		inputPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		lblDualonRoot = new Label(grpFtpLoginInformation, SWT.NONE);
		lblDualonRoot.setText("FTP Root");
		
		inputRoot = new Text(grpFtpLoginInformation, SWT.BORDER);
		inputRoot.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

	}
	
	public void initialize(FTPLoginInformation ftpInfo){
		inputHost.setText(ftpInfo.getHost());
		inputPassword.setText(ftpInfo.getPassword());
		inputPort.setText(ftpInfo.getPort()+"");
		inputRoot.setText(ftpInfo.getFtpUploadRoot());
		inputUserName.setText(ftpInfo.getUserName());
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public FTPLoginInformation getLoginInformation(){
		int port = 21;
		try{
			port = Integer.parseInt(inputPort.getText());
		} catch (Exception e) {}
		return new FTPLoginInformationImpl(inputHost.getText(), port, inputUserName.getText(), inputPassword.getText(), inputRoot.getText());

	}

}
