package de.beepublished.client;

public interface ProgressFeedback {
	public void setFeedback(String newStatus);
	public void setStarted();
	public void setFinished();
	public void setFailed();
}
