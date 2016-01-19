package de.app;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;

import javax.annotation.Priority;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import de.cryptone.key.*;

@Component
@Priority(value=1)
public class RequestFilter implements Filter {
	
	@Autowired
	RestRequest rest;

	public static int counter;
	public static boolean isLoggedIn = false;

	public static final String PROTECTED_URI = "api";
	
	public static final String URL = "http://localhost:8080";
	
	public static final String CONTENT_SECURITY_POLICY = "content-security-header", 
							   CONTENT_SECURITY_POLICY_VALUE = "script-src 'self'";
	
	public static final String X_XSRF_TOKEN = "X-XSRF-TOKEN";
	
	public static final String REALM = "realm", 
							   REALM_VALUE="realm";
	
	public static final String WWW_AUTHENTICATION = "WWW-Authentication";
	
	public static final String HASH_ALGORITHM = "hash-algorithm"; 
	
	public static final String EXPIRES_IN = "EXPIRES-IN";
	
	public static final String CLIENT_PUBLIC_KEY = "CLIENT-PUBLIC-KEY";
	
	public static final String SERVER_PUBLIC_KEY = "SERVER-PUBLIC-KEY";
	
	

	
	public static final String AUTHORIZATION = "Authorization",
			   AUTHORIZATION_VALUE = "Bearer ";
	
	
	@Autowired
	RestRequest client;


	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
	    HttpServletResponse response = (HttpServletResponse) res;
	    HttpServletRequest request = (HttpServletRequest) req;
	    
	    
	    /*
	     * if Authorization Header is not set, and user attempt to
	     * reach protected api
	     * redirect to default error handler
	     */
	    String authorization = request.getHeader( AUTHORIZATION );
	    if( authorization == null ){
	    	String apiUri = null;
	    	try {
	    		apiUri = request.getRequestURI().trim().split("/")[1]; 
			} catch (Exception e) {
				System.out.println( e.toString() );
			}
	    	
	    	if( apiUri != null && apiUri.trim().equals( PROTECTED_URI ) ){
	    		this.redirect(request, response, "/auth_error");
	    	}
	    }

	    
	    /*
	     * if the X-XSRF-TOKEN is not set, that mean it s the first
	     * REQ to the Local Server. 
	     * Set X-XSRF-TOKEN
	     */
	    String xsrfToken = request.getHeader( X_XSRF_TOKEN );
	    if( xsrfToken == null ){
	    	
	     	SecureRandom random = new SecureRandom();
		    xsrfToken = new BigInteger(130, random ).toString();
		    response.setHeader( X_XSRF_TOKEN , xsrfToken);
	    	response.setHeader(CONTENT_SECURITY_POLICY, CONTENT_SECURITY_POLICY_VALUE);    	
	    	String keypair = new KeyPair().generate(); 
	    	System.out.println(keypair);
	    	Map<String, String> _keypair = new Gson().fromJson(keypair, Map.class);
	    	response.setHeader(CLIENT_PUBLIC_KEY, _keypair.get("pubKey"));
	    	rest.setHeader( CLIENT_PUBLIC_KEY, _keypair.get("pubKey"));
	    }

	    
	    
	    String sessionUri = null; 
	    try {
		     sessionUri = request.getRequestURI().trim().split("/")[1].trim();  
		} catch (Exception e) {
			System.out.println( e.toString() );
		}
	    
	    try {
	    		chain.doFilter(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}	
	}



	public void redirect( HttpServletRequest request, HttpServletResponse response, String uri ){
		try {
			request.getServletContext().getRequestDispatcher(uri).forward(request, response);
			return;
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy() {}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

}
