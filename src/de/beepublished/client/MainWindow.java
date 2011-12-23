package de.beepublished.client;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;

import de.beepublished.client.ftp.FTPTarget;
import de.beepublished.client.widget.FTPLoginInformationWidget;
import de.beepublished.client.widget.UploadSourceWidget;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import de.beepublished.client.widget.DBLoginInformationWidget;


//TODO create class description
public class MainWindow {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
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
		shell.setSize(310, 486);
		shell.setText("SWT Application");
		
		final FTPLoginInformationWidget loginInformationWidget = new FTPLoginInformationWidget(shell, SWT.NONE);
		loginInformationWidget.setBounds(10, 96, 283, 124);
		
		final UploadSourceWidget uploadSourceWidget = new UploadSourceWidget(shell, SWT.NONE);
		uploadSourceWidget.setBounds(10, 10, 283, 64);
		
		final Button btnUpload = new Button(shell, SWT.NONE);
		btnUpload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FTPTarget target = new FTPTarget(loginInformationWidget.getLoginInformation());
				try {
					target.connect();
					target.uploadFolder(uploadSourceWidget.getFolder(),"");
					target.disconnect();
					btnUpload.setText("Success");
				} catch (FileNotFoundException e1) {
					btnUpload.setText(e1.toString());
				} catch (IOException e1) {
					btnUpload.setText(e1.toString());
				}
			}
		});
		btnUpload.setBounds(15, 382, 283, 64);
		btnUpload.setText("UPLOAD");
		
		DBLoginInformationWidget loginInformationWidget_1 = new DBLoginInformationWidget(shell, SWT.NONE);
		loginInformationWidget_1.setBounds(10, 242, 283, 118);

	}
}
