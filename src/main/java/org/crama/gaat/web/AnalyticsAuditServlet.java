package org.crama.gaat.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.crama.gaat.model.AccountResponse;
import org.crama.gaat.service.AnalyticsAccountsService;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

@WebServlet(urlPatterns={"/audit"}, asyncSupported=true) 
public class AnalyticsAuditServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2044950679085632388L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
      throws IOException, ServletException {
      //Check if the user have rejected 
      String error = req.getParameter("error"); 
      if ((null != error) && ("access_denied".equals(error.trim()))) { 
         HttpSession sess = req.getSession(); 
         sess.invalidate(); 
         resp.sendRedirect(req.getContextPath()); 
         return; 
      }
      //OK the user have consented so lets find out about the user 
      /*AsyncContext ctx = req.startAsync(); 
      ctx.start(new AnalyticsAuditProcessor(req, resp, ctx));*/ 
      
      HttpSession sess = req.getSession(); 
      OAuthService service = (OAuthService)sess.getAttribute("oauth2Service");
     
      
      //Get the all important authorization code 
      String code = req.getParameter("code"); 
      //Construct the access token  
      Token accessToken = (Token)sess.getAttribute("accessToken");
      if (accessToken == null) {
    	  accessToken = service.getAccessToken(null, new Verifier(code));
    	  //Save the token for the duration of the session 
    	  sess.setAttribute("accessToken", accessToken);
      }
      
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
      service.signRequest(accessToken, oReq); 
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
      
      AnalyticsAccountsService auditService = AnalyticsAccountsService.getInstance(service, accessToken);
      List<AccountResponse> accountList = auditService.getAccountsList();
      req.setAttribute("accountList", accountList);
      
      RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/audit.jsp");
      rd.forward(req, resp);
   } 
	
}



