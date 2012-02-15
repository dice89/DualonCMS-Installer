package de.beepublished.client;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.GridData;

public class CloseDialog extends Shell {

	public static final int RESULT_YES = 1;
	public static final int RESULT_NO = 2;
	public static final int RESULT_CANCEL = 3;
	
	public int getResult(){
		return result;
	}
	
	private int result;
	
	/**
	 * Create the shell.
	 * @param display
	 */
	public CloseDialog(Display display) {
		super(display, SWT.TITLE | SWT.APPLICATION_MODAL);
		setMinimumSize(new Point(250, 28));
		createContents();
		this.pack();
		setLayout(new GridLayout(2, false));
		
		Button btnNein = new Button(this, SWT.NONE);
		btnNein.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		btnNein.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = RESULT_NO;
				getDisplay().dispose();
			}
		});
		btnNein.setText("Nein");
		
		Button btnJa = new Button(this, SWT.NONE);
		btnJa.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		btnJa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = RESULT_YES;
				getDisplay().dispose();
			}
		});
		btnJa.setText("Ja");
		this.open();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Save current settings?");
		setSize(349, 92);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

