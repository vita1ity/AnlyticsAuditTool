package org.crama.gaat.service;

import org.crama.gaat.model.AnalyticsResponse;
import org.crama.gaat.model.BasicSettings;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class AnalyticsAnalizerService {
	private static AnalyticsAnalizerService instance = null;
	
	private OAuthService service;
	private Token token;
	
	private AnalyticsAnalizerService(OAuthService service, Token token) {
		this.service = service;
		this.token = token;
	}
	public static synchronized AnalyticsAnalizerService getInstance(OAuthService service, Token token) {
		if (instance == null) {
			instance = new AnalyticsAnalizerService(service, token);			
		}
		return instance;
	}
	
	public AnalyticsResponse getAnalytics(String accountId, String propertyId, String viewId) {
		AnalyticsResponse analytics = new AnalyticsResponse();
		BasicSettings basicSettings = getBasicSettings(accountId, propertyId, viewId);
		analytics.setBasicSettings(basicSettings);
		
		return analytics;
	}
	
	private BasicSettings getBasicSettings(String accountId, String propertyId, String viewId) {
		OAuthRequest viewReq = new OAuthRequest(Verb.GET, 
    			"https://www.googleapis.com/analytics/v3/management/accounts/" + accountId + "/webproperties/" + propertyId + "/profiles/" + viewId); 
	    service.signRequest(token, viewReq); 
	    Response viewResp = viewReq.send();
	    
	    JSONTokener tokener = new JSONTokener(viewResp.getBody());
	    JSONObject view = new JSONObject(tokener);
	    
	    String currency = view.getString("currency");
	    String timezone= view.getString("timezone");
	    System.out.println("Currency: " + currency + ", timezone: " + timezone);
	    
	    boolean pass = true;
	    //check if at least one of the properties not set
	    //then test fails
	    if (currency == null || currency.equals("") || timezone == null || timezone.equals("")) {
	    	pass = false;
	    }
	    BasicSettings basicSettings = new BasicSettings(currency, timezone, pass);
	    
	    
		return basicSettings;
	}
}
