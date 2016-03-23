package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import de.app.CryptoneProperties;


/*
 * CREQ : Client Request Entity
 * CRES : Client Response Entity
 */
public abstract class AbstractFindRequest<CRES> extends CRUDHelper{
	
	@Autowired
	CryptoneProperties env;

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
    	response = this.makeRequest( this.buildUrl(client, env.getUrl(), this.getUri(), uriVariableValues), HttpMethod.GET, client, requestEntity, findResponseClazz);
    	return response;
    }
    
    public ResponseEntity<CRES> findOne( Object ...uriVariableValues ){
    	ResponseEntity<CRES> response = null;
    	HttpEntity<?> requestEntity = this.getHttpEntity( this.client.getHeaders() );
    	response = this.makeRequest( this.buildUrl(client, env.getUrl(), this.getUri(), uriVariableValues), HttpMethod.GET, client, requestEntity, responseClazz);
    	return response;
    }
    
}
