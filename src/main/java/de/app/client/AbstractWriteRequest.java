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
    	response= this.makeRequest( this.buildUrl(client,this.getUrl(), this.getUri(), uriVariableValues), method, client, requestEntity, responseClazz);
    	return response; 
	}
	
	/**
	 * Do a Post Request from Localserver to RemoteServer.
	 * Returns ResponseEntity<> that can be directly  return by 
	 * the LocalServer controller to Webbrowser as Server Response.
	 * All argument , uri and body are required. 
	 * <p>
	 *
	 * @param  uri  path on the RemoteServer , http URI as defined in <a href="https://tools.ietf.org/html/rfc3986"> rfc3986 </a>
	 * @param  body understand http-body, http entity body <a href="https://tools.ietf.org/html/rfc2616#page-43"> rfc2616 entity body </a>
	 * @return @see body     
	 */
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
