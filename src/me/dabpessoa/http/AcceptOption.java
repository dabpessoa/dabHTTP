package me.dabpessoa.http;

public enum AcceptOption {

	JSON("application/json"),
	HTML("text/html");
	
	private String mimeType;		
	
	private AcceptOption(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public String getMimeTypeString() {
		return mimeType;
	}
	
}
