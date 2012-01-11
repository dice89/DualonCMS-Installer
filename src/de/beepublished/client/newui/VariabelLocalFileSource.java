package de.beepublished.client.newui;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import de.beepublished.client.exceptions.ZipVocationException;

public class VariabelLocalFileSource extends LocalFileEndPoint {

	
	public VariabelLocalFileSource(Shell shell) throws IOException,ZipVocationException {
		super(createNewFile(shell));
		// TODO Auto-generated constructor stub
	}
	
	private static File createNewFile(Shell shell){
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
	    dialog.setFilterNames(new String[] { "BeePublished Backup Files"});
	    dialog.setFilterExtensions(new String[] { "*.bpb.zip"}); // Windows
	                                    // wild
	                                    // cards
	    //dialog.setFilterPath("c:\\"); // Windows path
	    //dialog.setFileName("backup.zip");
	    return new File(dialog.open());
	}

}