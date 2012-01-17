package de.beepublished.client;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import de.beepublished.client.widget.CreateWebPointDialog;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;

public class BeePublishedClient implements SelectionListener, ValidationFeedback, ProgressFeedback {
	
	public static final String ADDITION_SOURCE = "...existing backup";
	public static final String ADDITION_TARGET = "...new backup";

	protected Shell shlBeepublishedClient;
	
	private Combo comboQuelle;
	private Combo comboZiel;
	private Button buttonAction;
	private Label labelFeedback;
	
	// Model
	private EndPointManager managerQuelle;
	private EndPointManager managerZiel;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BeePublishedClient window = new BeePublishedClient();
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
	 */
	protected void createContents() {
		shlBeepublishedClient = new Shell();
		shlBeepublishedClient.setSize(450, 130);
		shlBeepublishedClient.setText("BeePublished - Client");
		shlBeepublishedClient.setLayout(new FormLayout());
		
		Group grpQuelle = new Group(shlBeepublishedClient, SWT.NONE);
		FormData fd_grpQuelle = new FormData();
		fd_grpQuelle.bottom = new FormAttachment(0, 51);
		fd_grpQuelle.right = new FormAttachment(0, 160);
		fd_grpQuelle.top = new FormAttachment(0, 10);
		fd_grpQuelle.left = new FormAttachment(0, 10);
		grpQuelle.setLayoutData(fd_grpQuelle);
		grpQuelle.setText("Quelle");
		
		Group grpZiele = new Group(shlBeepublishedClient, SWT.NONE);
		FormData fd_grpZiele = new FormData();
		fd_grpZiele.bottom = new FormAttachment(0, 51);
		fd_grpZiele.right = new FormAttachment(0, 424);
		fd_grpZiele.top = new FormAttachment(0, 10);
		fd_grpZiele.left = new FormAttachment(0, 274);
		grpZiele.setLayoutData(fd_grpZiele);
		grpZiele.setText("Ziele");
		grpQuelle.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		comboQuelle = new Combo(grpQuelle, SWT.NONE);
		comboQuelle.addSelectionListener(this);
		grpZiele.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		comboZiel = new Combo(grpZiele, SWT.NONE);
		comboZiel.addSelectionListener(this);
		
		buttonAction = new Button(shlBeepublishedClient, SWT.NONE);
		FormData fd_buttonAction = new FormData();
		fd_buttonAction.bottom = new FormAttachment(grpQuelle, 0, SWT.BOTTOM);
		fd_buttonAction.right = new FormAttachment(grpZiele, -6);
		fd_buttonAction.top = new FormAttachment(0, 20);
		fd_buttonAction.left = new FormAttachment(0, 166);
		buttonAction.setLayoutData(fd_buttonAction);
		buttonAction.setText("...");
		buttonAction.addSelectionListener(this);
		
		labelFeedback = new Label(shlBeepublishedClient, SWT.CENTER);
		FormData fd_labelFeedback = new FormData();
		fd_labelFeedback.top = new FormAttachment(grpQuelle, 6);
		fd_labelFeedback.left = new FormAttachment(0, 117);
		fd_labelFeedback.right = new FormAttachment(100, -118);
		fd_labelFeedback.bottom = new FormAttachment(0, 71);
		labelFeedback.setLayoutData(fd_labelFeedback);
		
		Menu menu = new Menu(shlBeepublishedClient, SWT.BAR);
		shlBeepublishedClient.setMenuBar(menu);
		
		MenuItem mntmAddServer = new MenuItem(menu, SWT.NONE);
		mntmAddServer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleAddServerButton(e);
			}
		});
		mntmAddServer.setText("add Server");
		
		// setup Model
		managerQuelle = new EndPointManager(ADDITION_SOURCE);
		managerZiel = new EndPointManager(ADDITION_TARGET);
		
		//managerQuelle.addEndPoint(new HostedBackup());
		
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
		if(buttonAction.getText().equals("Install")){
			InstallThread installThread = new InstallThread(this, (FileEndPoint) managerQuelle.getAtIndex(comboQuelle.getSelectionIndex()), (WebServer) managerZiel.getAtIndex(comboZiel.getSelectionIndex()));
			installThread.start();
			buttonAction.setEnabled(false);
		}
		
		if(buttonAction.getText().equals("Backup")){
			BackupThread installThread = new BackupThread(this, (WebServer) managerQuelle.getAtIndex(comboQuelle.getSelectionIndex()), (FileBackup) managerZiel.getAtIndex(comboZiel.getSelectionIndex()));
			installThread.start();
			buttonAction.setEnabled(false);
		}
		
		if(buttonAction.getText().equals("Migrate")){
			MigrationThread migrateThread = new MigrationThread(this, (WebServer) managerQuelle.getAtIndex(comboQuelle.getSelectionIndex()), (WebServer) managerZiel.getAtIndex(comboZiel.getSelectionIndex()));
			migrateThread.start();
			buttonAction.setEnabled(false);
		}
	}
	
	private void handleAddServerButton(SelectionEvent e){
		System.out.println("Button handleAddServerButton");
		CreateWebPointDialog dialog = new CreateWebPointDialog(shlBeepublishedClient, 0);
		WebServer server = (WebServer) dialog.open();
		if(server != null){
			managerQuelle.addEndPoint(server);
			managerZiel.addEndPoint(server);
			comboQuelle.setItems(managerQuelle.getForComboBox());
			comboZiel.setItems(managerZiel.getForComboBox());
		}
	}
	
	private void handleComboBoxSelection(SelectionEvent e){
		System.out.println("Combo Selection");
		new ValidationThread(this,managerQuelle, managerZiel,comboQuelle.getSelectionIndex(),comboZiel.getSelectionIndex()).start();
		//System.out.println(didSelectExtraItem((Combo) e.getSource()));
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
			}
		});
	}

	@Override
	public void setFailed() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				labelFeedback.setText("Failed!");
				buttonAction.setEnabled(true);
			}
		});
	}
}
