package de.beepublished.client.newui;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import de.beepublished.client.exceptions.ZipVocationException;

public class VariabelLocalFileTarget implements FileEndPoint{

	private boolean isFinal = false;
	
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

	@Override
	public File getFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getSQLDump() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void process() throws IOException, ZipVocationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public File getBackupFile() {
		return file;
	}

	@Override
	public boolean isFinal() {
		return isFinal;
	}
	
	public void setFinal(){
		isFinal = true;
	}

}