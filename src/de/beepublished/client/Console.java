package de.beepublished.client;

import java.io.File;
import java.util.ArrayList;

public class Console implements ProgressFeedback{
	
	private String argAction;
	private String argConfig;
	private String argSource;
	private String argTarget;
	private ArrayList<WebServer> servers;

	public Console(String[] args) {
		
		argAction = args[1];
		argConfig = args[2];
		argSource = args[3];
		argTarget = args[4];
		
		servers = (ArrayList<WebServer>) WebServerImporter.importWebserver(argConfig);
		
		System.out.println("Action: "+argAction);
		System.out.println("Config: "+argConfig);
		System.out.println("Source: "+argSource);
		System.out.println("Target: "+argTarget);
		
		if(argAction.equals("install")){
			System.out.println("start installation");
			InstallThread t = new InstallThread(this, new FileBackup(new File(argSource)), getWebServerWithName(argTarget));
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setFeedback(String newStatus) {
		System.out.println(newStatus);
	}

	@Override
	public void setStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFinished() {
		System.out.println("Finished");
	}

	@Override
	public void setFailed() {
		System.out.println("error");
	}
	
	private WebServer getWebServerWithName(String name){
		for(WebServer s : servers)
			if(s.getName().equals(name))
				return s;
		throw new RuntimeException();
	}

}