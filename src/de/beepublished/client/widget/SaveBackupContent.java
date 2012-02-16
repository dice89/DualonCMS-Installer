package de.beepublished.client.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import de.beepublished.client.BackupThread;
import de.beepublished.client.BeePublishedClient;
import de.beepublished.client.FileBackup;
import de.beepublished.client.ProgressFeedback;
import de.beepublished.client.WebServer;

public class SaveBackupContent extends Composite implements ProgressFeedback {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	private Button saveBackupButton;
	private Label labelFeedback;
	private ProgressBar progressBar;
	private Combo sourceServerCombo;
	private FileComposite fileTarget;
	
	public SaveBackupContent(Composite parent, int style) {
		super(parent, style);
		
		Label sourceServerLabel = new Label(this, SWT.NONE);
		sourceServerLabel.setBounds(10, 10, 80, 15);
		sourceServerLabel.setText("Source server:");
		
		Label targetFileLabel = new Label(this, SWT.NONE);
		targetFileLabel.setText("Target file:");
		targetFileLabel.setBounds(10, 60, 80, 15);
		
		saveBackupButton = new Button(this, SWT.NONE);
		saveBackupButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doAction();
			}
		});
		saveBackupButton.setBounds(10, 113, 100, 25);
		saveBackupButton.setText("Save backup");
		
		sourceServerCombo = new Combo(this, SWT.NONE);
		sourceServerCombo.setBounds(10, 31, 261, 23);

		progressBar = new ProgressBar(this, SWT.INDETERMINATE);
		progressBar.setBounds(10, 144, 430, 17);
		progressBar.setVisible(false);
		
		labelFeedback = new Label(this, SWT.NONE);
		labelFeedback.setBounds(10, 167, 250, 15);
		
		fileTarget = new FileComposite(this, SWT.NONE);
		fileTarget.setBounds(10, 81, 261, 26);
		fileTarget.setSave();
	}
	
	public void updateComboBox(){
		String[] cbValues = BeePublishedClient.endPointManager.getForComboBox();
		sourceServerCombo.setItems(cbValues);
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
				saveBackupButton.setEnabled(false);
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
				saveBackupButton.setEnabled(true);
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
				saveBackupButton.setEnabled(true);
				labelFeedback.setText(e.getLocalizedMessage());
			}
		});
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void doAction(){
		try{
			WebServer source = BeePublishedClient.endPointManager.getSelectedServer(sourceServerCombo);
			FileBackup target = (FileBackup) fileTarget.getFile();
			BackupThread thread = new BackupThread(this, source, target);
			thread.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
