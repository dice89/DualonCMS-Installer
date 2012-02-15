package de.beepublished.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import de.beepublished.client.db.DBLoginInformationImpl;
import de.beepublished.client.ftp.FTPLoginInformationImpl;
import de.beepublished.client.pageInformation.WebPageInformationImpl;
import de.beepublished.client.widget.CreateWebPointDialog;

public class BeePublishedClient implements SelectionListener, ValidationFeedback, ProgressFeedback {
	
	public static final String ADDITION_SOURCE = "...existing backup";
	public static final String ADDITION_TARGET = "...new backup";

	protected Shell shlBeepublishedClient;
	
	private Combo comboQuelle;
	private Combo comboZiel;
	private Button buttonAction;
	private Label labelFeedback;
	private ProgressBar progressBar;
	private CreateWebPointDialog createWebPointDialogSource;
	private CreateWebPointDialog createWebPointDialogTarget;
	
	// Model
	private EndPointManager managerQuelle;
	private EndPointManager managerZiel;

	/**
	 * Launch the application.
	 * @param args
	*/
	public static void main(String[] args) {
		
		
		
		if(args.length != 0 && args[0].equals("-console")){
				new Console(args);
		} else {	
			try {
				
				Display.getDefault().syncExec(new Runnable(){

					@Override
					public void run() {
						BeePublishedClient window = new BeePublishedClient();
						window.open();
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		
		// Close event
				shlBeepublishedClient.addListener(SWT.Close, new Listener() {
					@Override
					public void handleEvent(Event event) {
						
						Display display = Display.getDefault();
						CloseDialog shell1 = new CloseDialog(display);
					    while (!shell1.isDisposed()) {
					        if (!display.readAndDispatch()) {
					            display.sleep();
					        }
					    }
					    
					    switch (shell1.getResult()) {
						case CloseDialog.RESULT_YES:

							// begin
							try{
							
						    	File f = new File("setting.bps.txt");
								PrintWriter inputStream = new PrintWriter(f);new FileInputStream(f);
								List<EndPoint> pointsQuelle = managerQuelle.getEndPoints();
								List<EndPoint> pointsZiele = managerZiel.getEndPoints();
										
								ArrayList<WebServer> result = new ArrayList<WebServer>();
										
									
								for(EndPoint p : pointsQuelle){
									if(p instanceof WebServer){
										if(!result.contains((WebServer) p)){
											result.add((WebServer) p);
										}
									}
								}
										
								for(EndPoint p : pointsZiele){
									if(p instanceof WebServer){
										if(!result.contains((WebServer) p)){
											result.add((WebServer) p);
										}
									}
								}
										
								for(WebServer server : result){
									String serialization = server.serialize();
									System.out.println(serialization);
									inputStream.println(serialization);
								}
										
								inputStream.flush();
								inputStream.close();
							
							} catch (Exception e) {
								// TODO: handle exception
							}
							// end
							
						    display.dispose();
							break;
						case CloseDialog.RESULT_NO:
							break;
					    }
					    display.dispose();
					}
				});
		
		
		shlBeepublishedClient.open();
		shlBeepublishedClient.layout();
		while (!shlBeepublishedClient.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	
		 * @wbp.parser.entryPoint
		 
	 */
	protected void createContents() {
		shlBeepublishedClient = new Shell();
		shlBeepublishedClient.setSize(780, 821);
		
		shlBeepublishedClient.setText("BeePublished - Client");
		shlBeepublishedClient.setLayout(new GridLayout(3, false));
		
		Label descriptionLabel = new Label(shlBeepublishedClient, SWT.NONE);
		descriptionLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		descriptionLabel.setText("Please specifiy your Source and Target. They can be either a File or a Server.");
		
		Group grpQuelle = new Group(shlBeepublishedClient, SWT.NONE);
		grpQuelle.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		grpQuelle.setText("Source");
		grpQuelle.setLayout(new GridLayout(1, false));
		
		comboQuelle = new Combo(grpQuelle, SWT.NONE);
		comboQuelle.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		
		Button btnCreateNewFileprofile = new Button(grpQuelle, SWT.NONE);
		btnCreateNewFileprofile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				resolveSource();
			}
		});
		btnCreateNewFileprofile.setText("Create new Fileprofile");
		
		Button btnCreateNewServerprofile = new Button(grpQuelle, SWT.NONE);
		btnCreateNewServerprofile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WebServer newServer = new WebServer("WebServer", new FTPLoginInformationImpl("", 21, "", "", ""), new DBLoginInformationImpl("", "", "", ""), new WebPageInformationImpl(""));
				managerQuelle.addEndPoint(newServer);
				managerZiel.addEndPoint(newServer);
				createWebPointDialogSource.initializeWithWebServer(newServer);
				BeePublishedClient.this.setup();
			}
		});
		btnCreateNewServerprofile.setText("Create new Serverprofile");
		comboQuelle.addSelectionListener(this);
		
		createWebPointDialogSource = new CreateWebPointDialog(grpQuelle, SWT.NONE);
		createWebPointDialogSource.setVisible(false);
		
		buttonAction = new Button(shlBeepublishedClient, SWT.NONE);
		buttonAction.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		buttonAction.setText("               ...              ");
		buttonAction.addSelectionListener(this);
		buttonAction.setEnabled(false);
		
	
		
		buttonAction.setFocus();
		
		Group grpZiele = new Group(shlBeepublishedClient, SWT.NONE);
		grpZiele.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 2));
		grpZiele.setText("Target");
		grpZiele.setLayout(new GridLayout(1, false));
		
		comboZiel = new Combo(grpZiele, SWT.NONE);
		comboZiel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		comboZiel.addSelectionListener(this);
		
		Button btnCreateNewFileprofileTarget = new Button(grpZiele, SWT.NONE);
		btnCreateNewFileprofileTarget.setText("Create new Fileprofile");
		
		Button btnCreateNewServerprofileTarget = new Button(grpZiele, SWT.NONE);
		btnCreateNewServerprofileTarget.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WebServer newServer = new WebServer("WebServer", new FTPLoginInformationImpl("", 21, "", "", ""), new DBLoginInformationImpl("", "", "", ""), new WebPageInformationImpl(""));
				managerQuelle.addEndPoint(newServer);
				managerZiel.addEndPoint(newServer);
				createWebPointDialogTarget.initializeWithWebServer(newServer);
				BeePublishedClient.this.setup();
			}
		});
		btnCreateNewServerprofileTarget.setText("Create new Serverprofile");
		
		createWebPointDialogTarget = new CreateWebPointDialog(grpZiele, SWT.NONE);
		createWebPointDialogTarget.setVisible(false);
		Menu menu = new Menu(shlBeepublishedClient, SWT.BAR);
		shlBeepublishedClient.setMenuBar(menu);
		
		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setText("Settings");
		
		Menu menu_1 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_1);
		
		MenuItem mntmExportSettings = new MenuItem(menu_1, SWT.NONE);
		mntmExportSettings.setText("export Settings");
		
		MenuItem mntmImportSettings = new MenuItem(menu_1, SWT.NONE);
		mntmImportSettings.setText("import Settings");
		
		progressBar = new ProgressBar(shlBeepublishedClient, SWT.INDETERMINATE);
		progressBar.setVisible(false);
		new Label(shlBeepublishedClient, SWT.NONE);
		new Label(shlBeepublishedClient, SWT.NONE);
		new Label(shlBeepublishedClient, SWT.NONE);
		new Label(shlBeepublishedClient, SWT.NONE);
		
		labelFeedback = new Label(shlBeepublishedClient, SWT.CENTER);
		labelFeedback.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		labelFeedback.setText(".");
		labelFeedback.setAlignment(SWT.LEFT);
		new Label(shlBeepublishedClient, SWT.NONE);
		new Label(shlBeepublishedClient, SWT.NONE);
		
		mntmImportSettings.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shlBeepublishedClient, SWT.OPEN);
			    dialog.setFilterNames(new String[] { "BeePublished Setting Files"});
			    dialog.setFilterExtensions(new String[] { "*.bps.txt"});
			    //dialog.setFileName("setting.bps.txt");
			    String fileName = dialog.open();
			    if(fileName != null){
			    	
			    	List<WebServer> server = WebServerImporter.importWebserver(fileName);
			    	
			    	for(WebServer s : server){
			    		managerQuelle.addEndPoint(s);
						managerZiel.addEndPoint(s);
					}
					BeePublishedClient.this.setup();
			    	
			    }
			}
		});
		mntmExportSettings.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				FileDialog dialog = new FileDialog(shlBeepublishedClient, SWT.SAVE);
			    dialog.setFilterNames(new String[] { "BeePublished Setting Files"});
			    dialog.setFilterExtensions(new String[] { "*.bps.txt"});
			    dialog.setFileName("setting.bps.txt");
			    String fileName = dialog.open();
			    if(fileName != null){
			    	try {
				    	File f = new File(fileName);
						PrintWriter inputStream = new PrintWriter(f);new FileInputStream(f);
						List<EndPoint> pointsQuelle = managerQuelle.getEndPoints();
						List<EndPoint> pointsZiele = managerZiel.getEndPoints();
						
						ArrayList<WebServer> result = new ArrayList<WebServer>();
						
						
						for(EndPoint p : pointsQuelle){
							if(p instanceof WebServer){
								if(!result.contains((WebServer) p)){
									result.add((WebServer) p);
								}
							}
						}
						
						for(EndPoint p : pointsZiele){
							if(p instanceof WebServer){
								if(!result.contains((WebServer) p)){
									result.add((WebServer) p);
								}
							}
						}
						
						for(WebServer server : result){
							String serialization = server.serialize();
							System.out.println(serialization);
							inputStream.println(serialization);
						}
						
						inputStream.flush();
						inputStream.close();
			    	
			    	} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }
				
				
				
				
			}
		});
		
		// setup Model
		managerQuelle = new EndPointManager(ADDITION_SOURCE);
		managerZiel = new EndPointManager(ADDITION_TARGET);
		
		// try to import existing settings
				try{
			    	List<WebServer> server = WebServerImporter.importWebserver("setting.bps.txt");
			    	for(WebServer s : server){
			    		managerQuelle.addEndPoint(s);
						managerZiel.addEndPoint(s);
					}
				} catch (Exception e) { }
		
		managerQuelle.addEndPoint(new HostedBackup());
		
		setup();
	}
	
	private void setup(){
		comboQuelle.setItems(managerQuelle.getForComboBox());
		comboZiel.setItems(managerZiel.getForComboBox());
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource() instanceof Combo)
			handleComboBoxSelection(e);
		else if (e.getSource() instanceof Button){
			handlePerformButton(e);
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	
	private void handlePerformButton(SelectionEvent e){
		System.out.println("Button handlePerformButton");

		progressBar.setVisible(true);
		buttonAction.setEnabled(false);
		
		if(buttonAction.getText().equals("Install")){
			InstallThread installThread = new InstallThread(this, (FileEndPoint) managerQuelle.getAtIndex(comboQuelle.getSelectionIndex()), (WebServer) managerZiel.getAtIndex(comboZiel.getSelectionIndex()));
			installThread.start();
		}
		
		if(buttonAction.getText().equals("Backup")){
			BackupThread installThread = new BackupThread(this, (WebServer) managerQuelle.getAtIndex(comboQuelle.getSelectionIndex()), (FileBackup) managerZiel.getAtIndex(comboZiel.getSelectionIndex()));
			installThread.start();
		}
		
		if(buttonAction.getText().equals("Migrate")){
			MigrationThread migrateThread = new MigrationThread(this, (WebServer) managerQuelle.getAtIndex(comboQuelle.getSelectionIndex()), (WebServer) managerZiel.getAtIndex(comboZiel.getSelectionIndex()));
			migrateThread.start();
		}
	}
	
	private void handleComboBoxSelection(SelectionEvent e){
		System.out.println("Combo Selection");
		new ValidationThread(this,managerQuelle, managerZiel,comboQuelle.getSelectionIndex(),comboZiel.getSelectionIndex()).start();
		
		Combo o =(Combo) e.getSource();
		
		try{
		WebServer s = (WebServer) managerQuelle.getAtIndex(o.getSelectionIndex());
		if(o.equals(comboQuelle)){
			createWebPointDialogSource.setVisible(true);
			createWebPointDialogSource.initializeWithWebServer(s);
		} else {
			createWebPointDialogTarget.setVisible(true);
			createWebPointDialogTarget.initializeWithWebServer(s);
		}
		} catch (Exception es){
			if(o.equals(comboQuelle)){
				createWebPointDialogSource.setVisible(false);
				createWebPointDialogSource.redraw();
				createWebPointDialogSource.pack(true);
			} else {
				createWebPointDialogTarget.setVisible(false);
			}
		}
	}
	
	/**
	 * setzt den Text des Buttons neu
	 * @param newText
	 */
	public void changeButton(String newText){
		if(newText != null){
			buttonAction.setText(newText);
			buttonAction.setEnabled(true);
		} else {
			buttonAction.setEnabled(false);
		}
	}

	@Override
	public void setValidationFeedback(final int validationStatus) {
		if(validationStatus == ValidationStatus.INVALID){
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					BeePublishedClient.this.changeButton(null);
				}
			});
		} else {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					if(validationStatus == ValidationStatus.VALID_INSTALL)
						BeePublishedClient.this.changeButton("Install");
					else if(validationStatus == ValidationStatus.VALID_BACKUP)
						BeePublishedClient.this.changeButton("Backup");
					else if(validationStatus == ValidationStatus.VALID_MIGRATION)
						BeePublishedClient.this.changeButton("Migrate");
				}
			});
		}
	}

	@Override
	public void resolveSource() {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				FileDialog dialog = new FileDialog(shlBeepublishedClient, SWT.OPEN);
			    dialog.setFilterNames(new String[] { "BeePublished Backup Files"});
			    dialog.setFilterExtensions(new String[] { "*.bpb.zip"}); // Windows
			    String fileName = dialog.open();
			    if(fileName != null){
			    	managerQuelle.addEndPoint(new FileBackup(new File(fileName)));
			    	
			    	comboQuelle.setItems(managerQuelle.getForComboBox());
			    	comboQuelle.select(comboQuelle.getItemCount()-2);
			    }
			}
		});
	}

	@Override
	public void resolveTarget() {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				FileDialog dialog = new FileDialog(shlBeepublishedClient, SWT.SAVE);
			    dialog.setFilterNames(new String[] { "BeePublished Backup Files"});
			    dialog.setFilterExtensions(new String[] { "*.bpb.zip"});
			    dialog.setFileName("backup.bpb.zip");
			    String fileName = dialog.open();
			    if(fileName != null){
			    	managerZiel.addEndPoint(new FileBackup(new File(fileName)));

			    	comboZiel.setItems(managerZiel.getForComboBox());
			    	comboZiel.select(comboZiel.getItemCount()-2);
			    }
			}
		});
	}

	@Override
	public void setFeedback(final String newStatus) {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				labelFeedback.setText(newStatus);
			}
		});
	}

	@Override
	public void setStarted() {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				labelFeedback.setText("Started...");
			}
		});
	}

	@Override
	public void setFinished() {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				labelFeedback.setText("Success");
				buttonAction.setEnabled(true);
				progressBar.setVisible(false);
			}
		});
	}

	@Override
	public void setFailed(final Exception e) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				labelFeedback.setText("Failed: " + e.getLocalizedMessage());
				buttonAction.setEnabled(true);
				progressBar.setVisible(false);
			}
		});
	}
}
