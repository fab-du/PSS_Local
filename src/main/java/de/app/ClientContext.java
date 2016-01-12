package de.app;

import java.net.URI;
import org.apache.http.HttpHost;
import org.apache.http.client.AuthCache;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class ClientContext extends HttpComponentsClientHttpRequestFactory{
	
	   HttpHost host;
	    public ClientContext (HttpHost host) {
	        super();
	        this.host = host;
	    }
	 
	    protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
	        return createHttpContext();
	    }
	    private HttpContext createHttpContext() {

	        BasicHttpContext localcontext = new BasicHttpContext();
	        return localcontext;
	    }

}
