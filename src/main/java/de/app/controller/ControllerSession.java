package de.app.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.google.gson.Gson;

import de.app.Post;
import de.app.RestRequest;
import de.app.service.ServiceUser;
import de.cryptone.key.KeyPair;

@RestController
@RequestMapping(value="/session")
public class ControllerSession {

	@Autowired
	RestRequest request;

	@Autowired 
	Post POST;
	public final static String URL = "http://localhost:8080";

	ServiceUser serviceuser = new ServiceUser();
	

	@RequestMapping( value="/login", method = RequestMethod.POST )
	@Produces("application/json")
	public ResponseEntity<LinkedHashMap<String, String>>
	login_challenge( @RequestBody Map<String, String> authdata, HttpServletRequest mRequest, HttpServletResponse response ) throws RestClientException, Exception{
		
		ResponseEntity<LinkedHashMap<String, String>> result = null;
		String email = authdata.get("email"); 
		String password = authdata.get("password"); 
		
		serviceuser.step1( email, password);
		Map<String, String> challenge = new HashMap<String, String>();
		challenge.put("email", email);

		result = POST.simplePost("/session/login/challenge", challenge );

		LinkedHashMap<String, String> body = result.getBody();
		
		if( body  == null ){
			LinkedHashMap<String, String> errorMessage = new LinkedHashMap<String, String>();
			return 
			new ResponseEntity<LinkedHashMap<String,String>>(errorMessage, HttpStatus.UNAUTHORIZED );
		}
		
		Map<String, String> result1 = serviceuser.step2( body );

		if( result1  == null ){
			LinkedHashMap<String, String> errorMessage = new LinkedHashMap<String, String>();
			return 
			new ResponseEntity<LinkedHashMap<String,String>>(errorMessage, HttpStatus.UNAUTHORIZED );
		}
		
		/*
		 * Generate KeyPair. 
		 * the public key will be sent to the RemoteServer and will
		 * be store until the session ends. 
		 * And the private key will be send to the browser.
		 */
		
		KeyPair keyGenerator = new KeyPair();
		String keypair = keyGenerator.algorithm("RSA").generate();
		System.out.println(keypair);
		Gson gson = new Gson();
		Map<String, String> _keypair = new HashMap<>();
		_keypair = gson.fromJson(keypair, Map.class);
		
		if( _keypair  == null ){
			LinkedHashMap<String, String> errorMessage = new LinkedHashMap<String, String>();
			return 
			new ResponseEntity<LinkedHashMap<String,String>>(errorMessage, HttpStatus.UNAUTHORIZED );
		}

		result1.put("client_pubkey", _keypair.get("pubKey"));
		result1.put("email", email);
		result1.put("B", body.get("B"));
		
		ResponseEntity<LinkedHashMap<String, String>> endResponse = 
		POST.simplePost("/session/login/authenticate", result1);

		HttpHeaders headers = new HttpHeaders(); 
		headers = endResponse.getHeaders();
		LinkedHashMap<String, String> zwischenErg = endResponse.getBody();
		
		if ( zwischenErg == null ){
			LinkedHashMap<String, String> errorMessage = new LinkedHashMap<String, String>();
			return 
			new ResponseEntity<LinkedHashMap<String,String>>(errorMessage, HttpStatus.UNAUTHORIZED );
		}
		zwischenErg.put("prikey", _keypair.get("priKey"));
		return new ResponseEntity<LinkedHashMap<String, String>>( zwischenErg, headers, HttpStatus.OK );
	}
	

	@RequestMapping( value="/login/authenticate", method = RequestMethod.POST )
	public ResponseEntity<LinkedHashMap<String, String>> 
		login( @RequestBody Map<String, String> authdata  ) throws RestClientException, Exception{

			ResponseEntity<LinkedHashMap<String, String>> result = null;
			try {
				result = POST.simplePost("/session/login/authenticate", authdata);
				return result;
			} catch (Exception e) {
				return new ResponseEntity<LinkedHashMap<String,String>>( HttpStatus.UNAUTHORIZED );
			}
	}

	@RequestMapping( value="/register", method = RequestMethod.POST )
	@Produces("application/json")
	public ResponseEntity<LinkedHashMap<String, String>> 
		register( @RequestBody Map<String, String> newregister ){
//			Map<String, String> result = serviceuser.register( newregister );
//		return POST.simplePost("/session/register", result);
		return null;
	}

	@RequestMapping( value="/logout", method = RequestMethod.POST )
	public ResponseEntity<LinkedHashMap<String, String>>
	logout(  ){
		return null;
	}
	
}
