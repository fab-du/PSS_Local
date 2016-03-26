package de.app.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


public  class AbstractWriteRequest<CRES, CREQ > extends CRUDHelper{

    protected final RestClient client;
	
    protected final Class<CRES> responseClazz;
    protected final Class<CREQ> requestClazz;

	public AbstractWriteRequest( RestClient client , Class<CRES> responseClazz, Class<CREQ> requestClazz ) {
		this.client = client;
		this.responseClazz = responseClazz;
		this.requestClazz = requestClazz;	
	}
	
	private 
	ResponseEntity<CRES> crud( CREQ body, HttpMethod method, Object ...uriVariableValues){
		ResponseEntity<CRES> response = null;
    	HttpEntity<CREQ> requestEntity = this.getHttpEntity( this.client.getHeaders(), body );
    	response= this.makeRequest( this.buildUrl(client,super.getUrl(), this.getUri(), uriVariableValues), method, client, requestEntity, responseClazz);
    	return response; 
	}
	
	public 
	ResponseEntity<CRES> create( CREQ body, Object ...uriVariableValues){
		return this.crud(body, HttpMethod.POST, uriVariableValues);
	}
	
	public 
	ResponseEntity<CRES> put( CREQ body , Object ...uriVariableValues){
		return this.crud(body, HttpMethod.PUT, uriVariableValues);
	}
	
	public 
	ResponseEntity<CRES> put( Object ...uriVariableValues){
		return this.crud(null, HttpMethod.PUT, uriVariableValues);
	}
	
	public 
	ResponseEntity<CRES> delete( CREQ body , Object ...uriVariableValues){
		return this.crud(body, HttpMethod.DELETE, uriVariableValues);
	}
	
	public 
	ResponseEntity<CRES> delete( Object ...uriVariableValues){
		return this.crud(null, HttpMethod.DELETE, uriVariableValues);
	}
	
	public 
	ResponseEntity<CRES> update( CREQ body , Object ...uriVariableValues){
		return this.crud(body, HttpMethod.PATCH, uriVariableValues);
	}
	
	public 
	ResponseEntity<CRES> update( Object ...uriVariableValues){
		return this.crud(null, HttpMethod.PATCH, uriVariableValues);
	}
	
    
    HttpEntity<CREQ> getHttpEntity( HttpHeaders headers,  CREQ body ){
      	 HttpEntity<CREQ> requestEntity = new HttpEntity<CREQ>( body, headers );
      	 return requestEntity;
    }
}
