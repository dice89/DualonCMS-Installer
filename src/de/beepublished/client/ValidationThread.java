package de.beepublished.client;

import de.beepublished.client.EndPoint;

public class ValidationThread extends Thread {
	private ValidationFeedback target;
	
	private EndPointManager managerSource;
	private EndPointManager managerTarget;
	private int indexSource;
	private int indexTarget;

	public ValidationThread(ValidationFeedback target,
			EndPointManager managerSource, EndPointManager managerTarget,
			int indexSource, int indexTarget) {
		super();
		this.target = target;
		this.managerSource = managerSource;
		this.managerTarget = managerTarget;
		this.indexSource = indexSource;
		this.indexTarget = indexTarget;
	}

	@Override
	public void run() {
		super.run();
		System.out.println("validate input...");
		
		resolveUnresolved();
		
		try{
			EndPoint sourceEndPoint = managerSource.getAtIndex(indexSource);
			EndPoint targetEndPoint = managerTarget.getAtIndex(indexTarget);
			
			if(sourceEndPoint instanceof FileBackup && targetEndPoint instanceof WebServer)
				target.setValidationFeedback(ValidationStatus.VALID_INSTALL);
			else if(sourceEndPoint instanceof WebServer && targetEndPoint instanceof FileBackup)
				target.setValidationFeedback(ValidationStatus.VALID_BACKUP);
			else
				target.setValidationFeedback(ValidationStatus.INVALID);
		} catch (Exception e) {
			target.setValidationFeedback(ValidationStatus.INVALID);
		}
		
	}
	
	private void resolveUnresolved(){
		this.resolveSource();
		this.resolveTarget();
	}
	
	private void resolveSource(){
		if(didSelectExtraItem(managerSource, indexSource)){
			System.out.println("resolveSource");
			target.resolveSource();
		}
	}
	
	private void resolveTarget(){
		if(didSelectExtraItem(managerTarget, indexTarget)){
			System.out.println("resolveTarget");
			target.resolveTarget();
		}
	}
	
	private boolean didSelectExtraItem(EndPointManager manager, int selectionIndex){
		return selectionIndex == manager.getCount();
	}
	
}
