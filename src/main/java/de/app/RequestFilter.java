package de.app;

import java.io.IOException;

import javax.annotation.Priority;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.app.client.RestClient;

@Component
@Priority(value=1)
public class RequestFilter implements Filter {
	@Autowired
	RestClient restClient;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		System.out.println( "=============================================");
		System.out.println( req.getHeader("Authorization"));
		
		String authToken = req.getHeader("Authorization");
		if ( authToken != null  && restClient.getHeaders().get("Authorization") == null ){
			restClient.setHeader("Authorization", authToken);
		}
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {}
}
