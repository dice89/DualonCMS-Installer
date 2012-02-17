package de.beepublished.client;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

public class ConfirmDialog extends Dialog {

	public static final int YES = 1;
	public static final int NO = 2;
	
	protected int result;
	protected Shell shlTitle;
	private String title = "TITLE";
	private String text = "TEXT";
	private Point position;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ConfirmDialog(Shell parent, int style,Point position) {
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
		shlTitle.setLocation(position);
		shlTitle.setSize(182, 114);
		shlTitle.setText(title);
		shlTitle.setLayout(null);
		
		Button btnNo = new Button(shlTitle, SWT.NONE);
		btnNo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = NO;
				shlTitle.dispose();
			}
		});
		btnNo.setBounds(91, 51, 75, 25);
		btnNo.setText("OK");
		
		Label lblNewLabel = new Label(shlTitle, SWT.WRAP);
		lblNewLabel.setBounds(10, 10, 156, 35);
		lblNewLabel.setText(text);

	}

	public void setDialogTitle(String string) {
		title = string;
	}
	
	public void setDialogText(String text){
		this.text = text;
	}
}
