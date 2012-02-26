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
		sourceServerCombo.setBounds(10, 31, 250, 23);
		sourceServerCombo.setFont(BeePublishedClient.fontStandard);
		
		targetServerCombo = new Combo(this, SWT.NONE);
		targetServerCombo.setBounds(10, 85, 250, 23);
		targetServerCombo.setFont(BeePublishedClient.fontStandard);
		
		Label sourceServerLabel = new Label(this, SWT.NONE);
		sourceServerLabel.setBounds(10, 10, 80, 15);
		sourceServerLabel.setText("Source server:");
		sourceServerLabel.setFont(BeePublishedClient.fontStandard);
		
		Label targetServerLabel = new Label(this, SWT.NONE);
		targetServerLabel.setText("Target server:");
		targetServerLabel.setBounds(10, 60, 80, 15);
		targetServerLabel.setFont(BeePublishedClient.fontStandard);
		
		migrateButton = new Button(this, SWT.NONE);
		migrateButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doAction();
			}
		});
		migrateButton.setBounds(10, 114, 100, 25);
		migrateButton.setText("Migrate");
		migrateButton.setFont(BeePublishedClient.fontStandard);
		
		progressBar = new ProgressBar(this, SWT.INDETERMINATE);
		progressBar.setBounds(10, 145, 430, 17);
		progressBar.setVisible(false);
		
		labelFeedback = new Label(this, SWT.NONE);
		labelFeedback.setBounds(10, 168, 430, 15);
		labelFeedback.setFont(BeePublishedClient.fontStandard);
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
			WebServer source = BeePublishedClient.endPointManager.getSelectedServer(sourceServerCombo);
			WebServer target = BeePublishedClient.endPointManager.getSelectedServer(targetServerCombo);
			MigrationThread migrate = new MigrationThread(this, source, target);
			migrate.start();
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
