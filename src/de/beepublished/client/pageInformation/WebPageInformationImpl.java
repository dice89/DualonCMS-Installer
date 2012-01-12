package de.beepublished.client.pageInformation;


public class WebPageInformationImpl implements WebPageInformation {

	String pageRoot;

	public WebPageInformationImpl(String pageRoot) {
		super();
		this.pageRoot = pageRoot;
	}

	public String getPageRoot() {
		return pageRoot;
	}

}
