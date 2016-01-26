package de.app.client;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


/*
 * CREQ : Client Request Entity
 * CRES : Client Response Entity
 */
public abstract class AbstractFindRequest<CRES> {

    private static final String ID_VAR = "currentUserId";
   
    @Value("${remote.url}")
    private String url;
    
    private String uri;
    
    protected final RestClient client;
    
    protected final Class<CRES> responseClazz;
    protected final Class<CRES[]> findResponseClazz;
    
    public AbstractFindRequest(RestClient client , Class<CRES> responseClazz, Class<CRES[]> findResponseClazz) {
    	this.client = client;
    	this.responseClazz = responseClazz;
    	this.findResponseClazz = findResponseClazz;
    }
    
    public ResponseEntity<CRES[]> find(){
    	ResponseEntity<CRES[]> response = null;
    	HttpEntity<?> requestEntity = this.getHttpEntity( this.client.getHeaders() );

    	System.out.println( url + uri);
    	response = client.getRestTemplate().exchange(url + uri, HttpMethod.GET,  requestEntity,   findResponseClazz);
    	
    	return response;
    }
    
    
    public ResponseEntity<CRES[]> find(Long id){
    	ResponseEntity<CRES[]> response = null;
    	HttpEntity<?> requestEntity = this.getHttpEntity( this.client.getHeaders() );

    	System.out.println( url + uri);
    	response = client.getRestTemplate().exchange(url + uri, HttpMethod.GET,  requestEntity,   findResponseClazz, id);
    	
    	return response;
    }
    
    
    public ResponseEntity<CRES> findOne( Long id ){
    	ResponseEntity<CRES> response = null;
    	HttpEntity<?> requestEntity = this.getHttpEntity( this.client.getHeaders() );
    	response = client.getRestTemplate().exchange(url + uri + "/" + id, HttpMethod.GET,  requestEntity,   responseClazz, id);
    	return response;
    }
    
    public ResponseEntity<CRES> findOne( Long id , Long _id){
    	ResponseEntity<CRES> response = null;
    	HttpEntity<?> requestEntity = this.getHttpEntity( this.client.getHeaders() );
    	response = client.getRestTemplate().exchange(url + uri + "/" + _id, HttpMethod.GET,  requestEntity,   responseClazz, id, _id);
    	return response;
    }
    
    public Map<String, Long> createPathVariable( Long id ){
    	return Collections.singletonMap(ID_VAR, id); 
    }
    
    public void setUri( String uri ){
    	this.uri = uri;
    }
    
    HttpEntity<?> getHttpEntity( HttpHeaders headers ){
    	 HttpEntity<?> requestEntity = new HttpEntity<Object>( headers );
    	 return requestEntity;
    }
}
