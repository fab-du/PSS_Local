package de.app;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

import de.app.client.RestClient;


@Service
@Transactional
public class Post {

	@Autowired
	RestClient rest;

	public final String URL = "http://localhost:8080";

	/**
	 * Do a Post Request from Localserver to RemoteServer.
	 * Returns ResponseEntity<> that can be directly  return by 
	 * the LocalServer controller as Server Response.
	 * All argument , uri and body are required. 
	 * <p>
	 *
	 * @param  uri  path on the RemoteServer , http URI as defined in <a href="https://tools.ietf.org/html/rfc3986"> rfc3986 </a>
	 * @param  body understand http-body, http entity body <a href="https://tools.ietf.org/html/rfc2616#page-43"> rfc2616 entity body </a>
	 * @return @see body     
	 */
	 public ResponseEntity<LinkedHashMap<String, String>>
		 simplePost( String uri, Map<String, String> body ){

		 try {
			 this.bodyEntegrityCheck(body);
		} catch (Exception e) {
			return new ResponseEntity<LinkedHashMap<String,String>>( HttpStatus.UNAUTHORIZED );
		}
		 
		 HttpEntity<?> requestEntity = new HttpEntity<Object>(body, this.rest.getHeaders());
		 
		 	
			@SuppressWarnings("unused")
			Object postForObject = null;
			try {
				Object obj = rest.getRestTemplate().
					postForEntity(URL + uri, requestEntity, LinkedHashMap.class);

				@SuppressWarnings("unchecked")
				ResponseEntity<LinkedHashMap<String, String>> resp = (ResponseEntity<LinkedHashMap<String, String>>) obj;
				
				if( uri.trim().equals("/session/login/challenge") || uri.trim().equals("/session/login/authenticate")){
					HttpHeaders _head = resp.getHeaders();
					Iterator<Entry<String, List<String>>> it = _head.entrySet().iterator();
				
					while( it.hasNext() ){
						Entry<String, List<String>> el = it.next();
						System.out.print( el.getKey());
						System.out.println( el.getValue().toString());
					}
					
				}
				
				return resp;

			} catch (Exception e) {
				return new ResponseEntity<LinkedHashMap<String,String>>( HttpStatus.BAD_REQUEST );
			}

	 }

	 public ResponseEntity< LinkedHashMap<String, String>> 
	 	multipartPost( String uri , Map<String, String> body){
		return null; 
	 }

	 public void bodyEntegrityCheck( Map<String, String> body  ){
		 Preconditions.checkNotNull(body, "Please submit some data");
		 Preconditions.checkArgument(!body.isEmpty(), "Empty map");

		 Set<String> keys = body.keySet();
		 Iterator<String> it =  keys.iterator();

		 while( it.hasNext() ){
			 String key = it.next();
			 String value = body.get(key);
			 Preconditions.checkNotNull( value, "Null value please submitted" );
			 Preconditions.checkArgument(!value.isEmpty(), "Empty Body" );
		 } 
	 }


}
