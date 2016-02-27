package de.app.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


/*
 * CREQ : Client Request Entity
 * CRES : Client Response Entity
 */
public abstract class AbstractFindRequest<CRES> extends CRUDHelper{
    private String url = "http://localhost:8080/";
    
    protected final RestClient client;
    
    protected final Class<CRES> responseClazz;
    protected final Class<CRES[]> findResponseClazz;
    
    public AbstractFindRequest(RestClient client , Class<CRES> responseClazz, Class<CRES[]> findResponseClazz) {
    	this.client = client;
    	this.responseClazz = responseClazz;
    	this.findResponseClazz = findResponseClazz;
    }
    
    public ResponseEntity<CRES[]> find( Object ...uriVariableValues ) {
    	ResponseEntity<CRES[]> response = null;
    	HttpEntity<?> requestEntity = this.getHttpEntity( this.client.getHeaders() );
    	response = this.makeRequest( this.buildUrl(client, url, this.getUri(), uriVariableValues), HttpMethod.GET, client, requestEntity, findResponseClazz);
    	return response;
    }
    
    public ResponseEntity<CRES> findOne( Object ...uriVariableValues ){
    	ResponseEntity<CRES> response = null;
    	HttpEntity<?> requestEntity = this.getHttpEntity( this.client.getHeaders() );
    	response = this.makeRequest( this.buildUrl(client, url, this.getUri(), uriVariableValues), HttpMethod.GET, client, requestEntity, responseClazz);
    	return response;
    }
    
}
