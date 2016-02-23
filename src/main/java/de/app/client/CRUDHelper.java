package de.app.client;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public abstract class CRUDHelper {
	
	private String uri;
	
	public URI buildUrl( RestClient client, String url, String uri , Object ...uriVariableValues ){
    	URI _uri = client.getRestTemplate().getUriTemplateHandler().expand( url + uri, uriVariableValues);
		return _uri;
	}
	
	public <responseClazz> ResponseEntity<responseClazz> makeRequest( URI uri, HttpMethod method, RestClient client, HttpEntity<?> requestClazz, Class<responseClazz> responseClazz ){
			 return client.getRestTemplate().exchange(uri, method,  requestClazz,   responseClazz);
	}
	
	HttpEntity<?> getHttpEntity( HttpHeaders headers ){
		 HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		 return requestEntity;
	}
	
	public void setUri( String uri ){
		this.uri = uri;
	}
	
	public String getUri(){
		return this.uri; 
	}
}
