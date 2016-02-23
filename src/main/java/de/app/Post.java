package de.app;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

		 HttpEntity<?> requestEntity = new HttpEntity<Object>(body, this.rest.getHeaders());
		 
		 	
			@SuppressWarnings("unused")
			Object postForObject = null;
			try {
				Object obj = rest.getRestTemplate().
					postForEntity(URL + uri, requestEntity, LinkedHashMap.class);

				@SuppressWarnings("unchecked")
				ResponseEntity<LinkedHashMap<String, String>> resp = (ResponseEntity<LinkedHashMap<String, String>>) obj;
				
				return resp;

			} catch (Exception e) {
				return new ResponseEntity<LinkedHashMap<String,String>>( HttpStatus.BAD_REQUEST );
			}
	 }

}
