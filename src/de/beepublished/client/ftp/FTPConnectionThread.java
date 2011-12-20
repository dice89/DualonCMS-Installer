package de.beepublished.client.ftp;

import org.eclipse.swt.widgets.Display;

import de.beepublished.client.widget.FTPLoginInformationWidget;

public class FTPConnectionThread extends Thread {

	FTPLoginInformationWidget widget;
	FTPLoginInformation loginInformation;
	
	public FTPConnectionThread(FTPLoginInformationWidget widget){
		this.widget = widget;
	}
	
	@Override
	public void run() {
		widget.getDisplay().syncExec(new Runnable() {
			
			@Override
			public void run() {
				try{
					loginInformation = widget.getLoginInformation();
				} catch (Exception e){
					
				}
			}
		});
		
		try{
			FTPTarget target = new FTPTarget(loginInformation);
			target.connect();
			System.out.println("Successfull connected");

			widget.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						widget.setHostAndPortStatus(FTPLoginInformationWidget.STATUS_RIGHT);
					}
				});
			
			
			System.out.println("User: "+loginInformation.getUserName()+" PW: "+loginInformation.getPassword());
			if(target.login())
				widget.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						widget.setUserAndPwStatus(FTPLoginInformationWidget.STATUS_RIGHT);
					}
				});
			else
				widget.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						widget.setUserAndPwStatus(FTPLoginInformationWidget.STATUS_WRONG);
					}
				});
		} catch (Exception e){
			widget.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					widget.setHostAndPortStatus(FTPLoginInformationWidget.STATUS_WRONG);
				}
			});
			e.printStackTrace();
		}
	}

}
