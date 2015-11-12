package org.crama.gaat.web;

import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.crama.gaat.model.AccountResponse;
import org.crama.gaat.service.AnalyticsAccountsService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class AnalyticsAuditProcessor implements Runnable { 
   private HttpServletRequest req; 
   private HttpServletResponse resp; 
   private AsyncContext asyncCtx; 
   public AnalyticsAuditProcessor(HttpServletRequest req, HttpServletResponse resp, AsyncContext asyncCtx) { 
      this.req = req; 
      this.resp = resp; 
      this.asyncCtx = asyncCtx; 
   }
   @Override 
   public void run() {  
      HttpSession sess = req.getSession(); 
      OAuthService service = (OAuthService)sess.getAttribute("oauth2Service");
      //Get the all important authorization code 
      String code = req.getParameter("code"); 
      //Construct the access token 
      Token token = service.getAccessToken(null, new Verifier(code)); 
      //Save the token for the duration of the session 
      sess.setAttribute("token", token);
      
      /*//Perform a proxy login 
      try { 
         req.login("fred", "fredfred"); 
      } catch (ServletException e) { 
         //Handle error - should not happen 
    	  e.printStackTrace();
      }*/
      
      //Now do something with it - get the user's G+ profile 
      OAuthRequest oReq = new OAuthRequest(Verb.GET, 
            "https://www.googleapis.com/oauth2/v2/userinfo"); 
      service.signRequest(token, oReq); 
      Response oResp = oReq.send(); 

      //Read the result 
      /*JsonReader reader = Json.createReader(new ByteArrayInputStream( 
            oResp.getBody().getBytes())); 
      JsonObject profile = reader.readObject();*/ 
      /*FileReader reader = new FileReader(new ByteArrayInputStream( 
              oResp.getBody().getBytes()));*/
      
      System.out.println(oResp.getBody());
      
      //JSONParser jsonParser = new JSONParser();
      
      JSONTokener tokener = new JSONTokener(oResp.getBody());
      JSONObject profile = new JSONObject(tokener);
      //JSONObject profile = null;
      
      //profile = (JSONObject) jsonParser.parse(oResp.getBody());

      //Save the user details somewhere or associate it with 
      if (profile != null) {
	      sess.setAttribute("name", (String)profile.get("name")); 
	      sess.setAttribute("email", (String)profile.get("email")); 
	      
	      System.out.println(profile.get("name"));
	      System.out.println(profile.get("email"));
	      
      }
      
      AnalyticsAccountsService auditService = AnalyticsAccountsService.getInstance(service, token);
      List<AccountResponse> accountList = auditService.getAccountsList();
      sess.setAttribute("accountList", accountList);
      
      asyncCtx.complete(); 
   } 
}
