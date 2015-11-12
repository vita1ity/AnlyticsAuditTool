package org.crama.gaat.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.crama.gaat.model.AnalyticsResponse;
import org.crama.gaat.service.AnalyticsAnalizerService;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import com.google.gson.Gson;

@WebServlet("/get-analytics") 
public class AnalyticsAnalizerServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4356708251179199512L;
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accountId = request.getParameter("accountId");
		String propertyId = request.getParameter("propertyId");
		String viewId = request.getParameter("viewId");
		System.out.println("Account: " + accountId + ", property: " + propertyId + ", view: " + viewId);
		
		 HttpSession sess = request.getSession(); 
		 OAuthService service = (OAuthService)sess.getAttribute("oauth2Service");
		 Token token = (Token)sess.getAttribute("accessToken");
		
		AnalyticsAnalizerService analizerService = AnalyticsAnalizerService.getInstance(service, token);
		AnalyticsResponse analyticsResponse = analizerService.getAnalytics(accountId, propertyId, viewId);
		
		String jsonAnalytics = new Gson().toJson(analyticsResponse);
		response.getWriter().write(jsonAnalytics);
	}

}
