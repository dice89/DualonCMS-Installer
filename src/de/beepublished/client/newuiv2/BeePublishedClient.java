package de.beepublished.client.newuiv2;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

public class BeePublishedClient implements SelectionListener, ValidationFeedback {
	
	public static final String ADDITION_SOURCE = "...existing backup";
	public static final String ADDITION_TARGET = "...new backup";

	protected Shell shlBeepublishedClient;
	
	private Combo comboQuelle;
	private Combo comboZiel;
	private Button buttonAction;
	
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
		comboQuelle.setBounds(10, 20, 130, 23);
		comboQuelle.addSelectionListener(this);
		
		comboZiel = new Combo(grpZiele, SWT.NONE);
		comboZiel.setBounds(10, 20, 130, 23);
		comboZiel.addSelectionListener(this);
		
		buttonAction = new Button(shlBeepublishedClient, SWT.NONE);
		buttonAction.setBounds(166, 23, 102, 31);
		buttonAction.setText("...");
		buttonAction.addSelectionListener(this);
		
		// setup Model
		managerQuelle = new EndPointManager(ADDITION_SOURCE);
		managerZiel = new EndPointManager(ADDITION_TARGET);
		
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
		else if (e.getSource() instanceof Button)
			handleButtonSelection(e);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {		
	}
	
	private void handleButtonSelection(SelectionEvent e){
		System.out.println("Button Selection");
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
	public void setValidationFeedback(int validationStatus) {
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
					BeePublishedClient.this.changeButton("Valid");
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
			    dialog.open();
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
			    dialog.open();
			}
		});
	}
}
