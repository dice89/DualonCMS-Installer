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
	
	public String serialize(){
		return pageRoot;
	}

	public static WebPageInformation deserialize(String[] values) {
		return new WebPageInformationImpl(values[10]);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((pageRoot == null) ? 0 : pageRoot.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebPageInformationImpl other = (WebPageInformationImpl) obj;
		if (pageRoot == null) {
			if (other.pageRoot != null)
				return false;
		} else if (!pageRoot.equals(other.pageRoot))
			return false;
		return true;
	}

	
}
