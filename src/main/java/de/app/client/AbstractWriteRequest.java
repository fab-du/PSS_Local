package de.app.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

public  class AbstractWriteRequest<CRES, CREQ > {
	
    private final String url = "http://localhost:3000";
    private String uri = "";
    
    protected final RestClient client;
    
    protected final Class<CRES> responseClazz;
    protected final Class<CREQ> requestClazz;

	public AbstractWriteRequest( RestClient client , Class<CRES> responseClazz, Class<CREQ> requestClazz ) {
		this.client = client;
		this.responseClazz = responseClazz;
		this.requestClazz = requestClazz;	
	}
	
	ResponseEntity<CRES> create( CREQ body ){
		ResponseEntity<CRES> response = null;
    	HttpEntity<?> requestEntity = this.getHttpEntity( this.client.getHeaders() );

    	response = client.getRestTemplate().exchange(url + uri, HttpMethod.POST,  requestEntity,   responseClazz );
		return response; 
	}
	
	public void update(){
		
	}
	
	public void delete(){
		
	}
	
	public void setUri( String uri ){
		this.uri = uri;
	}
	
    HttpEntity<?> getHttpEntity( HttpHeaders headers ){
   	 HttpEntity<?> requestEntity = new HttpEntity<>( headers );
   	 return requestEntity;
   }
}
