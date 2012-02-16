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
import de.beepublished.client.MigrationThread;
import de.beepublished.client.ProgressFeedback;
import de.beepublished.client.WebServer;

public class MigrateContent extends Composite implements ProgressFeedback {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	private Button migrateButton;
	private Label labelFeedback;
	private ProgressBar progressBar;
	
	private Combo sourceServerCombo;
	private Combo targetServerCombo;
	
	public MigrateContent(Composite parent, int style) {
		super(parent, style);
		
		sourceServerCombo = new Combo(this, SWT.NONE);
		sourceServerCombo.setBounds(42, 93, 250, 23);
		
		targetServerCombo = new Combo(this, SWT.NONE);
		targetServerCombo.setBounds(356, 93, 250, 23);
		
		Label sourceServerLabel = new Label(this, SWT.NONE);
		sourceServerLabel.setBounds(42, 72, 80, 15);
		sourceServerLabel.setText("Source server:");
		
		Label targetServerLabel = new Label(this, SWT.NONE);
		targetServerLabel.setText("Target server:");
		targetServerLabel.setBounds(356, 72, 80, 15);
		
		migrateButton = new Button(this, SWT.NONE);
		migrateButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doAction();
			}
		});
		migrateButton.setBounds(42, 140, 100, 25);
		migrateButton.setText("Migrate");
		
		progressBar = new ProgressBar(this, SWT.INDETERMINATE);
		progressBar.setBounds(42, 199, 564, 17);
		progressBar.setVisible(false);
		
		labelFeedback = new Label(this, SWT.NONE);
		labelFeedback.setBounds(42, 222, 250, 15);
	}
	
	public void updateComboBox(){
		String[] cbValues = BeePublishedClient.endPointManager.getForComboBox();
		targetServerCombo.setItems(cbValues);
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
				migrateButton.setEnabled(false);
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
				migrateButton.setEnabled(true);
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
				migrateButton.setEnabled(true);
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
			WebServer target = BeePublishedClient.endPointManager.getSelectedServer(targetServerCombo);
			MigrationThread migrate = new MigrationThread(this, source, target);
			migrate.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
