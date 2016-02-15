package de.app;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Enumeration;
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

import de.app.client.RestClient;

@Component
@Priority(value=1)
public class RequestFilter implements Filter {
	
	@Autowired
	RestClient restClient;
	
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
	
	public static final String CLIENT_PRIVATE_KEY = "CLIENT-PRIVATE-KEY";

	
	public static final String SERVER_PUBLIC_KEY = "SERVER-PUBLIC-KEY";
	
	

	
	public static final String AUTHORIZATION = "Authorization",
			   AUTHORIZATION_VALUE = "Bearer ";




	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
//		
//		String xsrf = req.getHeader(X_XSRF_TOKEN);
//		System.out.println(xsrf);
//		if ( xsrf == null || xsrf == "" ){
//	     	SecureRandom random = new SecureRandom();
//		    String xsrfToken = new BigInteger(64, random ).toString();
//		    System.out.println(xsrfToken);
//		    res.setHeader( X_XSRF_TOKEN , xsrfToken);	
//		}
		
		
		Enumeration<String> headernames = req.getHeaderNames();
		
		if(this.isApiUri( req.getRequestURI()) )
		while( headernames.hasMoreElements() ){
			String headername = headernames.nextElement();
			String headervalue = req.getHeader( headername );
			System.out.println( headername + "-----" + headervalue);
			//restClient.setHeader(headername, headervalue);
		}
		
		chain.doFilter(request, response);
	}

	boolean isApiUri( String uri ){
		String[] uris = uri.split("/"); 
		
		try {
			if (uris[1].trim().equals("api") || uris[1].trim().equals("session"))
				return true;
				return false;
		} catch (Exception e) {
			return false;
		}
	}



	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	

}
