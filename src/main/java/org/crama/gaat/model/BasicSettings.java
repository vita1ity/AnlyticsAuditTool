package org.crama.gaat.model;

public class BasicSettings {
	
	private String currency;
	private String timezone;
	private boolean pass;
	
	public BasicSettings(String currency, String timezone, boolean pass) {
		super();
		this.currency = currency;
		this.timezone = timezone;
		this.pass = pass;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public boolean getPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}
	
	
}
