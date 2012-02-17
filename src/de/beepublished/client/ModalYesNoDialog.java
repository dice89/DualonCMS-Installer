package de.beepublished.client;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

public class ModalYesNoDialog extends Dialog {

	public static final int YES = 1;
	public static final int NO = 2;
	
	protected int result;
	protected Shell shlTitle;
	private String title = "TITLE";
	private String message = "MESSAGE";
	private Point position;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ModalYesNoDialog(Shell parent, int style, Point position) {
		super(parent, style);
		setText("SWT Dialog");
		this.position = position;
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
		
		shlTitle.setLocation(position);
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
		shlTitle.setSize(182, 114);
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
		btnYes.setBounds(10, 47, 75, 25);
		btnYes.setText("Yes");
		
		Button btnNo = new Button(shlTitle, SWT.NONE);
		btnNo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = NO;
				shlTitle.dispose();
			}
		});
		btnNo.setBounds(91, 47, 75, 25);
		btnNo.setText("No");
		
		Label lblNewLabel = new Label(shlTitle, SWT.WRAP);
		lblNewLabel.setText(message);
		lblNewLabel.setBounds(10, 10, 156, 31);

	}

	public void setDialogTitle(String string) {
		title = string;
	}
	
	public void setMessage(String msg){
		message = msg;
	}
}
