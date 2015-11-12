package org.crama.gaat.model;

import java.io.Serializable;
import java.util.List;

public class AccountResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String accountId;
	private String accountName;
	private List<Property> properties;
	
	public AccountResponse(String accountId, String accountName, List<Property> propertyList) {
		super();
		this.accountId = accountId;
		this.accountName = accountName;
		this.properties = propertyList;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public List<Property> getProperties() {
		return properties;
	}
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	
	
	
}
