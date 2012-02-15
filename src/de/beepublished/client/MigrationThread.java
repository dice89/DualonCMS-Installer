package de.beepublished.client;

import java.io.File;

/**
 * Migrates a CMS from one WebServer to another WebServer
 * 	Source & Target: WebServer
 * @author fabiankajzar
 *
 */
public class MigrationThread extends Thread implements ProgressFeedback{
	private ProgressFeedback delegate;
	private WebServer source;
	private WebServer target;
	private boolean failed;

	public MigrationThread(ProgressFeedback delegate, WebServer source, WebServer target) {
		super();
		this.delegate = delegate;
		this.source = source;
		this.target = target;
	}
	
	@Override
	public void run() {
		try{
			failed = false;
			File f = File.createTempFile("tmp_file_", ".zip");
			System.out.println("Backing up to: " + f);
			BackupThread backup = new BackupThread(this, source, new FileBackup(f));
			backup.start();
			backup.join();
			if(failed)
				return;
			InstallThread install = new InstallThread(this, backup.getTarget(), target);
			install.start();
			install.join();
			if(!failed)
			delegate.setFinished();
		} catch (Exception e) {
			delegate.setFailed(e);
			e.printStackTrace();
		}
	}

	@Override
	public void setFeedback(String newStatus) {
		if(!failed)
			delegate.setFeedback(newStatus);
	}

	@Override
	public void setStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFinished() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFailed(Exception e) {
		failed = true;
		delegate.setFailed(e);
	}
}
