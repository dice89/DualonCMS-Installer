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

import de.beepublished.client.BeePublishedClient;
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
		targetServerCombo.setBounds(356, 93, 250, 23);
		
		Label sourceFileLabel = new Label(this, SWT.NONE);
		sourceFileLabel.setBounds(42, 72, 80, 15);
		sourceFileLabel.setText("Source file:");
		
		Label targetServerLabel = new Label(this, SWT.NONE);
		targetServerLabel.setText("Target server:");
		targetServerLabel.setBounds(356, 72, 80, 15);
		
		loadBackupButton = new Button(this, SWT.NONE);
		loadBackupButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doAction();
			}
		});
		loadBackupButton.setBounds(42, 140, 100, 25);
		loadBackupButton.setText("Load backup");

		progressBar = new ProgressBar(this, SWT.INDETERMINATE);
		progressBar.setBounds(42, 199, 564, 17);
		progressBar.setVisible(false);
		
		labelFeedback = new Label(this, SWT.NONE);
		labelFeedback.setBounds(42, 222, 250, 15);
		
		fileComposite = new FileComposite(this, SWT.NONE);
		fileComposite.setBounds(42, 93, 261, 26);
		fileComposite.setLoad();
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
			FileBackup backup = (FileBackup) fileComposite.getFile();
			WebServer target = BeePublishedClient.endPointManager.getSelectedServer(targetServerCombo);
			InstallThread install = new InstallThread(this, backup, target);
			install.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
