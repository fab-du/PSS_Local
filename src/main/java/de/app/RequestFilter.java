package de.app;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.nimbusds.srp6.cli.SRP6Client.User;

@Component
@Priority(value=1)
public class RequestFilter implements Filter {

	public static final String URL = "http://localhost:8080";

	@Autowired
	RestRequest client;


	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
	    HttpServletResponse response = (HttpServletResponse) res;
	    HttpServletRequest request = (HttpServletRequest) req;


	    String xrf_token    = request.getHeader("X-XSRF-TOKEN");
		String auth_header  = request.getHeader("Authorization");
		String auth1_header = request.getHeader("WWW-Authenticate");
		String realm_header = request.getHeader("realm");
		String hash_header  = request.getHeader("hash-algorithm");

	    if( xrf_token == null ){
		   	SecureRandom random = new SecureRandom();
		   	String angular_token = new BigInteger(130, random ).toString();
		   	response.setHeader("X-XSRF-TOKEN", angular_token);
	    }else{}

	    if( auth_header  == null ) response.setHeader("Authorization", "SRP");
	    if( auth1_header == null ) response.setHeader("WWW-Authenticate", "SRP");
		if( realm_header == null ) response.setHeader("realm", "realm");
		if(hash_header   == null ) response.setHeader("hash-algorithm", "SHA256");

		response.setHeader("Access-Control-Expose-Headers", "Client_pubkey, Expires");

		//response.sendRedirect("/session/notAuthenticate");


	    String method = request.getMethod();
				      try {
							chain.doFilter(request, response);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ServletException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
	}



	@Override
	public void destroy() {}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

}
