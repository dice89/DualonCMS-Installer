package de.beepublished.client;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ModalYesNoDialog extends Dialog {

	public static final int YES = 1;
	public static final int NO = 2;
	
	protected int result;
	protected Shell shlTitle;
	private String title = "TITLE";

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ModalYesNoDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public int open() {
		createContents();
		shlTitle.open();
		shlTitle.layout();
		Display display = getParent().getDisplay();
		while (!shlTitle.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlTitle = new Shell(getParent(), getStyle());
		shlTitle.setSize(182, 75);
		shlTitle.setText(title);
		shlTitle.setLayout(null);
		
		Button btnYes = new Button(shlTitle, SWT.NONE);
		btnYes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = YES;
				shlTitle.dispose();
			}
		});
		btnYes.setBounds(10, 10, 75, 25);
		btnYes.setText("Yes");
		
		Button btnNo = new Button(shlTitle, SWT.NONE);
		btnNo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = NO;
				shlTitle.dispose();
			}
		});
		btnNo.setBounds(91, 10, 75, 25);
		btnNo.setText("No");

	}

	public void setDialogTitle(String string) {
		title = string;
	}
}
