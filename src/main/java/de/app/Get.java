package de.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Get extends IRest {

	@SuppressWarnings("unchecked")
	public	ResponseEntity<? extends ArrayList<HashMap<String,Object>>> listGET( String uri ) {
		
		 try {
			return this.rest.getObject()
						 .getForEntity(URL + "/api" + uri, (Class<? extends ArrayList<HashMap<String,Object>>>)ArrayList.class);
		} catch (Exception e) {
			return new ResponseEntity<ArrayList<HashMap<String,Object>>>( new ArrayList<>(), HttpStatus.NO_CONTENT );
		}
	}

	public ResponseEntity<LinkedHashMap<String, String>> singleGET( String uri ){
		
		return null;
	}
	
}
