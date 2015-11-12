package org.crama.gaat.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Google2Api;
import org.scribe.oauth.OAuthService;

import com.google.api.services.analytics.AnalyticsScopes;

@WebServlet("/googleoauth") 
public class GoogleOAuthServlet extends HttpServlet {
	 private static final String CLIENT_ID = "216876413557-d5vb3prnpbs38gu71qk1okum7jgaek8s.apps.googleusercontent.com"; 
	   private static final String CLIENT_SECRET = "QC6nSo5FFwUVTqtuLmzQ0AqN";
	   @Override 
	   protected void doGet(HttpServletRequest req, HttpServletResponse res) 
	      throws IOException, ServletException {
	      //Configure 
	      ServiceBuilder builder= new ServiceBuilder(); 
	      OAuthService service = builder.provider(Google2Api.class) 
	         .apiKey(CLIENT_ID) 
	         .apiSecret(CLIENT_SECRET) 
	         .callback("http://localhost:8080/gaat/audit") 
	         .scope("openid profile email " + 
	               "https://www.googleapis.com/auth/plus.login " + 
	               "https://www.googleapis.com/auth/plus.me " +
	               AnalyticsScopes.ANALYTICS_READONLY)  
	         .debug() 
	         .build(); //Now build the call
	      HttpSession sess = req.getSession(); 
	      sess.setAttribute("oauth2Service", service);
	      res.sendRedirect(service.getAuthorizationUrl(null)); 
	   } 
}
