package de.beepublished.client.newui;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import de.beepublished.client.exceptions.ZipVocationException;

public class VariabelLocalFileTarget implements EndPoint {

	private File file;
	
	public VariabelLocalFileTarget(Shell shell) throws IOException,ZipVocationException {
		file = createNewFile(shell);
		// TODO Auto-generated constructor stub
	}
	
	private static File createNewFile(Shell shell){
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
	    dialog.setFilterNames(new String[] { "BeePublished Backup Files"});
	    dialog.setFilterExtensions(new String[] { "*.bpb.zip"}); // Windows
	                                    // wild
	                                    // cards
	    //dialog.setFilterPath("c:\\"); // Windows path
	    dialog.setFileName("backup.bpb.zip");
	    return new File(dialog.open());
	}

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "LocalFile";
	}

}