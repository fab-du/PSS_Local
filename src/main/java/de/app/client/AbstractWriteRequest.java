package de.app.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

public  class AbstractWriteRequest<CRES, CREQ > {
	
    private final String url = "http://localhost:8080";
    private String uri = "";
    
    protected final RestClient client;
    
    protected final Class<CRES> responseClazz;
    protected final Class<CREQ> requestClazz;

	public AbstractWriteRequest( RestClient client , Class<CRES> responseClazz, Class<CREQ> requestClazz ) {
		this.client = client;
		this.responseClazz = responseClazz;
		this.requestClazz = requestClazz;	
	}
	
	public 
	ResponseEntity<CRES> create( CREQ body ){
		ResponseEntity<CRES> response = null;
    	HttpEntity<?> requestEntity = this.getHttpEntity( this.client.getHeaders(), body );
    	response = client.getRestTemplate().exchange(url + uri, HttpMethod.POST,  requestEntity,   responseClazz );
		return response; 
	}
	
	public 
	ResponseEntity<CRES> create( CREQ body, Long id ){
		ResponseEntity<CRES> response = null;
    	HttpEntity<?> requestEntity = this.getHttpEntity( this.client.getHeaders(), body );
    	System.out.println( url + uri);
    	response = client.getRestTemplate().exchange(url + uri, HttpMethod.POST,  requestEntity,   responseClazz , id);
		return response; 
	}
	
	public void update(){
	}
	
	public ResponseEntity<?>  delete(Long id, Long _id, String suffix ){
		ResponseEntity<?> response = null;
    	HttpEntity<?> requestEntity = this.getHttpEntity( this.client.getHeaders());
    	System.out.println( url + uri + "/" + _id);
    	response = client.getRestTemplate().exchange(url + uri + "/" + _id + suffix, HttpMethod.DELETE,  requestEntity,   ResponseEntity.class, id, _id);
		return response; 
	}
	
	
	public void setUri( String uri ){
		this.uri = uri;
	}
	

	
    HttpEntity<?> getHttpEntity( HttpHeaders headers ){
   	 HttpEntity<?> requestEntity = new HttpEntity<>( headers );
   	 return requestEntity;
   }
    
    HttpEntity<?> getHttpEntity( HttpHeaders headers, CREQ body ){
      	 HttpEntity<CREQ> requestEntity = new HttpEntity<CREQ>( body, headers );
      	 return requestEntity;
      }
}
