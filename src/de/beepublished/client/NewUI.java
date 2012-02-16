package de.beepublished.client;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import de.beepublished.client.widget.CreateWebPointDialog;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import de.beepublished.client.widget.ServerConfigurationContent;
import de.beepublished.client.widget.InstallContent;
import de.beepublished.client.widget.LoadBackupContent;
import de.beepublished.client.widget.MigrateContent;
import org.eclipse.swt.custom.ScrolledComposite;
import de.beepublished.client.widget.SaveBackupContent;

public class NewUI {

	protected Shell shell;
	public static EndPointManager endPointManager;

	private InstallContent installContent;
	private LoadBackupContent loadBackupContent;
	private MigrateContent migrateContent;
	private SaveBackupContent saveBackupContent;
	private ServerConfigurationContent serverConfigurationContent;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			NewUI window = new NewUI();
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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(800, 600);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setMinimumSize(800, 600);
		
		endPointManager = new EndPointManager();
		endPointManager.importSettings("settings.bps.txt");
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Composite composite_1 = new Composite(scrolledComposite, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		scrolledComposite.setContent(composite_1);
		
		TabFolder tabFolder = new TabFolder(composite_1, SWT.NONE);
		
		TabItem tbtmIntallation = new TabItem(tabFolder, SWT.NONE);
		tbtmIntallation.setText("Intallation");
		
		installContent = new InstallContent(tabFolder, SWT.NONE);
		tbtmIntallation.setControl(installContent);
		
		TabItem tbtmBackup = new TabItem(tabFolder, SWT.NONE);
		tbtmBackup.setText("Create Backup");
		
		saveBackupContent = new SaveBackupContent(tabFolder, SWT.NONE);
		tbtmBackup.setControl(saveBackupContent);
		
		TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("Load Backup");
		
		loadBackupContent = new LoadBackupContent(tabFolder, SWT.NONE);
		tbtmNewItem.setControl(loadBackupContent);
		
		TabItem tbtmMigrate = new TabItem(tabFolder, SWT.NONE);
		tbtmMigrate.setText("Migrate");
		
		migrateContent = new MigrateContent(tabFolder, SWT.NONE);
		tbtmMigrate.setControl(migrateContent);
		
		TabItem tbtmServerConfiguration = new TabItem(tabFolder, SWT.NONE);
		tbtmServerConfiguration.setText("Server Configuration");
		
		serverConfigurationContent = new ServerConfigurationContent(tabFolder, SWT.NONE);
		tbtmServerConfiguration.setControl(serverConfigurationContent);
		
		tabFolder.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateComboboxEntries();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.updateComboboxEntries();
	}
	
	private void updateComboboxEntries(){
		installContent.updateComboBox();
		loadBackupContent.updateComboBox();
		migrateContent.updateComboBox();
		saveBackupContent.updateComboBox();
		serverConfigurationContent.updateComboBox();
	}
}
