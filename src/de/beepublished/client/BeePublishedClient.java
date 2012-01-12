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

public class BeePublishedClient implements SelectionListener, ValidationFeedback, ProgressFeedback {
	
	public static final String ADDITION_SOURCE = "...existing backup";
	public static final String ADDITION_TARGET = "...new backup";

	protected Shell shlBeepublishedClient;
	
	private Combo comboQuelle;
	private Combo comboZiel;
	private Button buttonAction;
	private Button buttonAddServer;
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
		shlBeepublishedClient.setSize(450, 110);
		shlBeepublishedClient.setText("BeePublished - Client");
		
		Group grpQuelle = new Group(shlBeepublishedClient, SWT.NONE);
		grpQuelle.setText("Quelle");
		grpQuelle.setBounds(10, 10, 150, 55);
		
		Group grpZiele = new Group(shlBeepublishedClient, SWT.NONE);
		grpZiele.setText("Ziele");
		grpZiele.setBounds(274, 10, 150, 55);
		
		comboQuelle = new Combo(grpQuelle, SWT.NONE);
		comboQuelle.setBounds(10, 10, 130, 23);
		comboQuelle.addSelectionListener(this);
		
		comboZiel = new Combo(grpZiele, SWT.NONE);
		comboZiel.setBounds(10, 10, 130, 23);
		comboZiel.addSelectionListener(this);
		
		buttonAction = new Button(shlBeepublishedClient, SWT.NONE);
		buttonAction.setBounds(166, 34, 102, 31);
		buttonAction.setText("...");
		buttonAction.addSelectionListener(this);
		
		buttonAddServer = new Button(shlBeepublishedClient, SWT.NONE);
		buttonAddServer.setBounds(170, 0, 94, 28);
		buttonAddServer.setText("add Server");
		
		labelFeedback = new Label(shlBeepublishedClient, SWT.NONE);
		labelFeedback.setBounds(125, 71, 199, 14);
		buttonAddServer.addSelectionListener(this);
		
		// setup Model
		managerQuelle = new EndPointManager(ADDITION_SOURCE);
		managerZiel = new EndPointManager(ADDITION_TARGET);
		
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
			if(e.getSource().equals(buttonAction))
				handlePerformButton(e);
			else if(e.getSource().equals(buttonAddServer))
				handleAddServerButton(e);
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
					else
						BeePublishedClient.this.changeButton("Backup");
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
