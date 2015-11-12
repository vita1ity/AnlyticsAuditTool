package org.crama.gaat.model;

import java.io.Serializable;
import java.util.List;

public class Property implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7114665408173221307L;
	private String propertyId;
	private String propertyName;
	private List<View> views;
	
	public Property(String propertyId, String propertyName, List<View> views) {
		super();
		this.propertyId = propertyId;
		this.propertyName = propertyName;
		this.views = views;
	}
	
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	
	
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public List<View> getViews() {
		return views;
	}
	public void setViews(List<View> views) {
		this.views = views;
	}
	
	
}
