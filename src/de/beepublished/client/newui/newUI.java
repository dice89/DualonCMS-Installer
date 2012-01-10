package de.beepublished.client.newui;

import java.awt.FileDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

import de.beepublished.client.db.DBLoginInformation;
import de.beepublished.client.exceptions.ZipVocationException;
import de.beepublished.client.ftp.FTPLoginInformation;
import de.beepublished.client.ftp.FTPTarget;
import de.beepublished.client.http.webservice.dao.REST_CMS_Backup_response;
import de.beepublished.client.http.webservice.dao.REST_CMS_Installation_response;
import de.beepublished.client.http.webservice.management.WebManager;
import de.beepublished.client.http.webservice.management.WebServiceListener;
import de.beepublished.client.http.webservice.services.ServiceException;
import de.beepublished.client.http.webservice.services.ServiceFileStreamResponse;
import de.beepublished.client.widget.WebPageInformation;
import de.beepublished.client.zip.ZipEngine;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Group;

public class newUI {

	protected Shell shell;
	private EndPointManager manager;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			newUI window = new newUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	private ComboSelectionListener listener;

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(454, 137);
		shell.setText("Dualon CMS Installer");
		
		try {
			new VariabelLocalFileSource(shell);
			new VariabelLocalFileTarget(shell);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ZipVocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			fileEndPoint = new LocalFileEndPoint(new File("C:\\Users\\Fabian\\Desktop\\ExampleFileEndPoint.zip"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		manager = new EndPointManager();
		manager.addEndPoint(fileEndPoint);
		manager.addEndPoint(webEndPoint);
		

		Button btnAction = new Button(shell, SWT.NONE);
		btnAction.setBounds(183, 53, 75, 25);
		btnAction.setText("action");
		
		Group grpSource = new Group(shell, SWT.NONE);
		grpSource.setText("Source");
		grpSource.setBounds(10, 37, 157, 53);
		
		Group grpTarget = new Group(shell, SWT.NONE);
		grpTarget.setText("Target");
		grpTarget.setBounds(267, 37, 157, 53);
		
		final Combo source = new Combo(grpSource, SWT.NONE);
		source.setBounds(10, 21, 137, 23);
		source.setItems(manager.getForComboBox());
		
		final Combo target = new Combo(grpTarget, SWT.NONE);
		target.setBounds(10, 20, 137, 23);
		target.setItems(manager.getForComboBox());
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CreateWebPointDialog d = new CreateWebPointDialog(shell, 0);
				Object o = d.open();
				if(o != null){
					manager.addEndPoint((EndPoint) o);
					source.setItems(manager.getForComboBox());
					target.setItems(manager.getForComboBox());
				}
			}
		});
		btnNewButton.setBounds(10, 10, 414, 25);
		btnNewButton.setText("add new EndPoint");
		
		
		
		
		
		listener = new ComboSelectionListener(source, target,btnAction);
		
		
		target.addSelectionListener(listener);
		source.addSelectionListener(listener);
		

	}
	
	private class ComboSelectionListener implements SelectionListener{

		public ComboSelectionListener(Combo source, Combo target, Button actionTarget){
			this.source = source;
			this.target = target;
			this.actionTarget = actionTarget;
			actionTarget.addSelectionListener(curentListener);
		}
		
		private Combo source;
		private Combo target;
		private Button actionTarget;
		
		private EndPoint sourceValue;
		private EndPoint targetValue;
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			fillSourceAndTarget();
			setButtonAction();
			System.out.println("widget Selected");
			System.out.println(sourceValue + "" + targetValue);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("widget Selected");
		}
		
		private void fillSourceAndTarget(){
			sourceValue = manager.getEndPoint(source.getText());
			targetValue = manager.getEndPoint(target.getText());
		}
		
		private SelectionListener curentListener = new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("NO ACTION");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		
		private void setButtonAction(){
			if(sourceValue.getType().equals("WebServer") && targetValue.getType().equals("LocalFile")){
				actionTarget.setEnabled(true);
				actionTarget.setText("get backup");
				actionTarget.removeSelectionListener(curentListener);
				curentListener = new BackupSelectionListener(sourceValue, targetValue);
				actionTarget.addSelectionListener(curentListener);
			}else if (sourceValue.getType().equals("LocalFile") && targetValue.getType().equals("WebServer")){
				actionTarget.setEnabled(true);
				actionTarget.setText("install");
				actionTarget.removeSelectionListener(curentListener);
				curentListener = new InstallationSelectionListener(sourceValue, targetValue);
				actionTarget.addSelectionListener(curentListener);
			} else {
				actionTarget.setEnabled(false);
				actionTarget.setText("bad combination");
			}
		}
	}
	private class BackupSelectionListener implements SelectionListener,WebServiceListener{

		private WebEndPoint source;
		private FileEndPoint target;
		
		public BackupSelectionListener(EndPoint source, EndPoint target) {
			this.source = (WebEndPoint) source;
			this.target = (FileEndPoint) target;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			WebManager.getWebManager().backupCMS(this, source.getDbInformation(), source.getPageInformation());
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onRestZipDownloadSuccess(ServiceFileStreamResponse response) {
			
			try {
				File f = response.getFile();
				
				
				FTPTarget ftpTarget = new FTPTarget(source.getFtpInformation());
				ftpTarget.connect();
				ftpTarget.login();
				System.out.println("Start to download files");
				
				File extracted;	
				extracted = File.createTempFile("tmp_backup", "");

				// setup temp directory
				extracted.delete();
				extracted.mkdir();
				
				// pack files together 				// setup temp directory
				File result = File.createTempFile("tmp_backup", "");
				result.delete();
				result.mkdir();
				
				File fileFolder = new File(result.getAbsolutePath()+"/files/");
				fileFolder.mkdirs();
				
				File dbFolder = new File(result.getAbsolutePath()+"/db/");
				dbFolder.mkdirs();
				
				ftpTarget.downloadFTP(fileFolder,source.getFtpInformation().getFtpUploadRoot());
				ftpTarget.logout();
				ftpTarget.disconnect();
				
				copyFolder(f, new File(dbFolder.getAbsolutePath()+"/cake.sql"));
				
				System.out.println("Start to create zip");
				
				File finishedZip = ZipEngine.zip(result, File.createTempFile("result", ".zip"));
				
				System.out.println("----------------------------------------");
				System.out.println("Backup finished: " + finishedZip.getAbsolutePath());
				System.out.println("----------------------------------------");
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onRestZipDownloadFailed(ServiceException e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onRestInstallationSuccess(REST_CMS_Installation_response response) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onRestInstallationFailed(ServiceException e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onRestBackupSuccess(REST_CMS_Backup_response response) {
			try {
				WebManager.getWebManager().downloadFile("", File.createTempFile("temp", ".sql").getAbsolutePath(), response.getSqlurl(), this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		@Override
		public void onRestBackupFailed(ServiceException e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class InstallationSelectionListener implements SelectionListener, WebServiceListener{
		private FileEndPoint source;
		private WebEndPoint target;
		
		public InstallationSelectionListener(EndPoint source, EndPoint target){
			this.source = (FileEndPoint) source;
			this.target = (WebEndPoint) target;
		}
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				System.out.println("Start to upload Files");
				FTPTarget ftpTarget = new FTPTarget(target.getFtpInformation());
				ftpTarget.connect();
				ftpTarget.login();
				ftpTarget.uploadFolder(source.getFiles(),target.getFtpInformation().getFtpUploadRoot());
				System.out.println("Start to upload SQL File");
				ftpTarget.uploadFile(source.getSQLDump().getAbsolutePath(), "services/installation/cake.sql");
				ftpTarget.logout();
				ftpTarget.disconnect();
				System.out.println("Start to install CMS");
				
				WebManager.getWebManager().installCMS(target.getDbInformation(), target.getPageInformation(), this);
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			System.out.println("widgetDefaultSelected");
		}

		@Override
		public void onRestZipDownloadSuccess(ServiceFileStreamResponse response) {
			System.out.println("onRestZipDownloadSuccess");
			
		}

		@Override
		public void onRestZipDownloadFailed(ServiceException e) {
			System.out.println("onRestZipDownloadFailed");
			
		}

		@Override
		public void onRestInstallationSuccess(
				REST_CMS_Installation_response response) {
			System.out.println("onRestInstallationSuccess");
			
		}

		@Override
		public void onRestInstallationFailed(ServiceException e) {
			System.out.println("onRestInstallationFailed");
			
		}

		@Override
		public void onRestBackupSuccess(REST_CMS_Backup_response response) {
			System.out.println("onRestBackupSuccess");
			
		}

		@Override
		public void onRestBackupFailed(ServiceException e) {
			System.out.println("onRestBackupFailed");
			
		}
		
	}
	
	private EndPoint fileEndPoint;
	
	private EndPoint webEndPoint = new WebEndPoint() {
		
		@Override
		public String getType() {
			return "WebServer";
		}
		
		@Override
		public String getName() {
			return "Local XAMPP";
		}
		
		@Override
		public WebPageInformation getPageInformation() {
			return new WebPageInformation() {
				@Override
				public String getPageRoot() {
					return "http://localhost/dualon-cms";
				}
			};
		}
		
		@Override
		public FTPLoginInformation getFtpInformation() {
			return new FTPLoginInformation() {
				@Override
				public String getUserName() {
					return "newuser";
				}
				@Override
				public int getPort() {
					return 21;
				}
				@Override
				public String getPassword() {
					return "xampp";
				}
				@Override
				public String getHost() {
					return "localhost";
				}
				@Override
				public String getFtpUploadRoot() {
					return "dualon-cms";
				}
			};
		}
		
		@Override
		public DBLoginInformation getDbInformation() {
			return new DBLoginInformation() {
				
				@Override
				public String getUserName() {
					return "root";
				}
				
				@Override
				public String getPassword() {
					return "";
				}
				
				@Override
				public String getHost() {
					return "localhost";
				}
				
				@Override
				public String getDBName() {
					return "dualoncms";
				}
			};
		}
	};
	
	public static void copyFolder(File src, File dest)
	    	throws IOException{
	 
	    	if(src.isDirectory()){
	 
	    		//if directory not exists, create it
	    		if(!dest.exists()){
	    		   dest.mkdir();
	    		   System.out.println("Directory copied from " 
	                              + src + "  to " + dest);
	    		}
	 
	    		//list all the directory contents
	    		String files[] = src.list();
	 
	    		for (String file : files) {
	    		   //construct the src and dest file structure
	    		   File srcFile = new File(src, file);
	    		   File destFile = new File(dest, file);
	    		   //recursive copy
	    		   copyFolder(srcFile,destFile);
	    		}
	 
	    	}else{
	    		//if file, then copy it
	    		//Use bytes stream to support all file types
	    		InputStream in = new FileInputStream(src);
	    	        OutputStream out = new FileOutputStream(dest); 
	 
	    	        byte[] buffer = new byte[1024];
	 
	    	        int length;
	    	        //copy the file content in bytes 
	    	        while ((length = in.read(buffer)) > 0){
	    	    	   out.write(buffer, 0, length);
	    	        }
	 
	    	        in.close();
	    	        out.close();
	    	        System.out.println("File copied from " + src + " to " + dest);
	    	}
	    }
}
