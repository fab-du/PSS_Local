package de.app;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RestRequest implements FactoryBean<RestTemplate>, 
InitializingBean{
	
	public static final String CONTENT_SECURITY_POLICY = "content-security-header", 
			   CONTENT_SECURITY_POLICY_VALUE = "script-src 'self'";
	
public static final String HASH_ALGORITHM = "hash-algorithm",
						   HASH_ALGORITHM_VALUE = "sha256";
public static final String ALG = "alg",
						   ALG_VALUE = "RS512";

public static final String TYP = "typ",
						   TYP_VALUE = "JWT";


	private HttpHeaders headers = new HttpHeaders();
	
	private RestTemplate restTemplate;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		restTemplate = new RestTemplate();
		headers.add("REALM", "REALM");
		headers.add(CONTENT_SECURITY_POLICY, CONTENT_SECURITY_POLICY_VALUE);
		headers.add(HASH_ALGORITHM, HASH_ALGORITHM_VALUE);
		headers.add(TYP, TYP_VALUE);
		headers.add("tototot", "tototot");
	}

	@Override
	public RestTemplate getObject() throws Exception {
		return restTemplate;
	}

	@Override
	public Class<?> getObjectType() {
		return RestTemplate.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	public void setHeaders( HttpHeaders httpHeaders ){
		this.headers.putAll(httpHeaders);
	}
	
	public void setHeader( String headerName, String headerValue ){
		this.headers.add(headerName, headerValue);
	}
	
	public HttpHeaders getHeader(){
		return this.headers;
	}

}
