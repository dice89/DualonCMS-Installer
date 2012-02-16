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
import de.beepublished.client.HostedBackup;
import de.beepublished.client.InstallThread;
import de.beepublished.client.ProgressFeedback;
import de.beepublished.client.WebServer;

public class InstallContent extends Composite implements ProgressFeedback {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	private Button installButton;
	private Label labelFeedback;
	private ProgressBar progressBar;
	private Combo targetServerCombo;
	
	public InstallContent(Composite parent, int style) {
		super(parent, style);
		
		installButton = new Button(this, SWT.NONE);
		installButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doAction();
			}
		});
		installButton.setBounds(42, 140, 100, 25);
		installButton.setText("Install");
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Target server:");
		label.setBounds(42, 72, 80, 15);
		
		targetServerCombo = new Combo(this, SWT.NONE);
		targetServerCombo.setBounds(42, 93, 250, 23);

		progressBar = new ProgressBar(this, SWT.INDETERMINATE);
		progressBar.setBounds(42, 199, 564, 17);
		progressBar.setVisible(false);
		
		labelFeedback = new Label(this, SWT.NONE);
		labelFeedback.setBounds(42, 222, 250, 15);
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
				installButton.setEnabled(false);
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
				installButton.setEnabled(true);
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
				installButton.setEnabled(true);
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
			WebServer target = BeePublishedClient.endPointManager.getSelectedServer(targetServerCombo);
			InstallThread install = new InstallThread(InstallContent.this, new HostedBackup(), target);
			install.start();
		} catch (Exception ex){
			// TODO add error dialog
		}
	}
}
