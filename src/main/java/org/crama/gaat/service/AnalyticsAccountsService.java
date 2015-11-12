package org.crama.gaat.service;

import java.util.ArrayList;
import java.util.List;

import org.crama.gaat.model.AccountResponse;
import org.crama.gaat.model.Property;
import org.crama.gaat.model.View;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class AnalyticsAccountsService {
	
	private static AnalyticsAccountsService instance = null;
	
	private OAuthService service;
	private Token token; 
	
	private AnalyticsAccountsService(OAuthService service, Token token) {
		this.service = service;
		this.token = token;
	}
	public static synchronized AnalyticsAccountsService getInstance(OAuthService service, Token token) {
		if (instance == null) {
			instance = new AnalyticsAccountsService(service, token);			
		}
		return instance;
	}
	public List<AccountResponse> getAccountsList() {
		List<AccountResponse> accountList = new ArrayList<AccountResponse>();
		
		//get all analytics accounts
		OAuthRequest accountsRequest = new OAuthRequest(Verb.GET, 
	    		  "https://www.googleapis.com/analytics/v3/management/accounts"); 
	    service.signRequest(token, accountsRequest); 
	    Response accountsResp = accountsRequest.send(); 
	      
	    System.out.println(accountsResp.getBody());
	      
	    JSONTokener tokener = new JSONTokener(accountsResp.getBody());
	    JSONObject accounts = new JSONObject(tokener);
	      
	    //JSONObject accounts = null;
	    //accounts = (JSONObject) jsonParser.parse(accountsResp.getBody());
	      
	    String username = (String)accounts.get("username");
	    System.out.println(username);
	      
	      
	    JSONArray items = accounts.getJSONArray("items");
	    System.out.println(items);
	    for (int i = 0; i < items.length(); i++) {
	    	JSONObject item = items.getJSONObject(i);
	    	System.out.println(item);
	    	String accountName = item.getString("name");
	    	String accountId = item.getString("id");
	    	System.out.println(accountName);
	    	System.out.println(accountId);
	    	
	    	//get all account properties
	    	OAuthRequest propertiesReq = new OAuthRequest(Verb.GET, 
	    			"https://www.googleapis.com/analytics/v3/management/accounts/" + accountId + "/webproperties"); 
		    service.signRequest(token, propertiesReq); 
		    Response propertiesResp = propertiesReq.send(); 
	    	
		    tokener = new JSONTokener(propertiesResp.getBody());
		    JSONObject properties = new JSONObject(tokener);
		    JSONArray propertiesItems = properties.getJSONArray("items");
		    
		    List<Property> propertyList = new ArrayList<Property>();
		    for (int j = 0; j < propertiesItems.length(); j++) {
		    	JSONObject propertyItem = propertiesItems.getJSONObject(j);
		    	String propertyName = propertyItem.getString("name");
		    	String propertyId = propertyItem.getString("id");
		    	System.out.println(propertyName);
		    	System.out.println(propertyId);
		    	
		    	//get all property views
		    	OAuthRequest viewReq = new OAuthRequest(Verb.GET, 
		    			"https://www.googleapis.com/analytics/v3/management/accounts/" + accountId + "/webproperties/" + propertyId + "/profiles"); 
			    service.signRequest(token, viewReq); 
			    Response viewResp = viewReq.send();
			    
			    tokener = new JSONTokener(viewResp.getBody());
			    JSONObject views = new JSONObject(tokener);
			    
			    JSONArray viewItems = views.getJSONArray("items");
			    List<View> viewList = new ArrayList<View>();
			    
			    for (int k = 0; k < viewItems.length(); k++) {
			    	JSONObject viewItem = viewItems.getJSONObject(k);
			    	String viewName = viewItem.getString("name");
			    	String viewId = viewItem.getString("id");
			    	System.out.println(viewName);
			    	System.out.println(viewId);
			    	
			    	View view = new View(viewId, viewName);
			    	viewList.add(view);
			    }
			    
			    Property property = new Property(propertyId, propertyName, viewList);
			    propertyList.add(property);
			    
		    }
		    
		    AccountResponse account = new AccountResponse(accountId, accountName, propertyList);
		    accountList.add(account);
	    }
	    return accountList;
	}
}
