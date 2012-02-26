package de.beepublished.client.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import de.beepublished.client.BeePublishedClient;
import de.beepublished.client.ConfirmDialog;
import de.beepublished.client.FileBackup;
import de.beepublished.client.InstallThread;
import de.beepublished.client.ProgressFeedback;
import de.beepublished.client.WebServer;

public class LoadBackupContent extends Composite implements ProgressFeedback {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	private Button loadBackupButton;
	private Label labelFeedback;
	private ProgressBar progressBar;
	
	private Combo targetServerCombo;
	private FileComposite fileComposite;
	
	public LoadBackupContent(Composite parent, int style) {
		super(parent, style);
		
		targetServerCombo = new Combo(this, SWT.NONE);
		targetServerCombo.setBounds(10, 84, 261, 23);
		targetServerCombo.setFont(BeePublishedClient.fontStandard);
		
		Label sourceFileLabel = new Label(this, SWT.NONE);
		sourceFileLabel.setBounds(10, 10, 80, 15);
		sourceFileLabel.setText("Source file:");
		sourceFileLabel.setFont(BeePublishedClient.fontStandard);
		
		Label targetServerLabel = new Label(this, SWT.NONE);
		targetServerLabel.setText("Target server:");
		targetServerLabel.setBounds(10, 63, 80, 15);
		targetServerLabel.setFont(BeePublishedClient.fontStandard);
		
		loadBackupButton = new Button(this, SWT.NONE);
		loadBackupButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doAction();
			}
		});
		loadBackupButton.setBounds(10, 113, 100, 25);
		loadBackupButton.setText("Load backup");
		loadBackupButton.setFont(BeePublishedClient.fontStandard);
		
		progressBar = new ProgressBar(this, SWT.INDETERMINATE);
		progressBar.setBounds(10, 144, 420, 17);
		progressBar.setVisible(false);
		progressBar.setFont(BeePublishedClient.fontStandard);
		
		labelFeedback = new Label(this, SWT.NONE);
		labelFeedback.setBounds(10, 167, 420, 15);
		labelFeedback.setFont(BeePublishedClient.fontStandard);
		
		fileComposite = new FileComposite(this, SWT.NONE);
		fileComposite.setBounds(10, 31, 261, 26);
		fileComposite.setLoad();
		fileComposite.setFont(BeePublishedClient.fontStandard);
	}
	
	public void updateComboBox(){
		String[] cbValues = BeePublishedClient.endPointManager.getForComboBox();
		targetServerCombo.setItems(cbValues);
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
				progressBar.setVisible(true);
				loadBackupButton.setEnabled(false);
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
				loadBackupButton.setEnabled(true);
				progressBar.setVisible(false);
			}
		});
	}
	
	@Override
	public void setFailed(final Exception e) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				progressBar.setVisible(false);
				loadBackupButton.setEnabled(true);
				labelFeedback.setText(e.toString() + " " + e.getLocalizedMessage());
			}
		});
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void doAction(){
		try{
			FileBackup backup = (FileBackup) fileComposite.getFile();
			WebServer target = BeePublishedClient.endPointManager.getSelectedServer(targetServerCombo);
			InstallThread install = new InstallThread(this, backup, target);
			install.start();
		} catch (Exception e) {
			
			// calculate popup position
			Rectangle rec = getParent().getBounds();
			Point popupPosition = getParent().toDisplay(0, 0) ;
			popupPosition.x += (rec.width /2) - (182 / 2);
			popupPosition.y += (rec.height / 2) - (114 / 2);
			
			ConfirmDialog t = new ConfirmDialog(new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,popupPosition);
			t.setDialogTitle("Error");
			t.setDialogText("Please check your selected values!");
			t.open();
		}
	}
}
