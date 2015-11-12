package org.crama.gaat.model;

import java.io.Serializable;

public class View implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2026916306597918109L;
	
	private String viewId;
	private String viewName;
	
	public View(String viewId, String viewName) {
		super();
		this.viewId = viewId;
		this.viewName = viewName;
	}
	
	public String getViewId() {
		return viewId;
	}
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	
}
