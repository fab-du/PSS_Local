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
	
	private HttpHeaders headers;
	
	private RestTemplate restTemplate;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
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
