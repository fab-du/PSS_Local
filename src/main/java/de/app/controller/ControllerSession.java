package de.app.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
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

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6VerifierGenerator;

import de.app.Post;
import de.app.client.RestClient;
import de.app.exceptions.BatieCryptoExc;
import de.app.service.ServiceUser;
import de.cryptone.key.KeyPair;
import de.crypto.RSAKeyGenerator;

@RestController
@RequestMapping(value="/session")
public class ControllerSession {

	@Autowired
	RestClient request;

	@Autowired 
	Post POST;
	public final static String URL = "http://localhost:8080";

	ServiceUser serviceuser = new ServiceUser();
	

	@RequestMapping( value="/login", method = RequestMethod.POST )
	@Produces("application/json")
	public ResponseEntity<LinkedHashMap<String, String>>
	login_challenge( @RequestBody Map<String, String> authdata ) throws RestClientException, Exception{
		
		ResponseEntity<LinkedHashMap<String, String>> result = null;
		String email = authdata.get("email"); 
		String password = authdata.get("password"); 
		
		System.out.println( email + "//" + password);
		
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
		
		System.out.println("Body not null");
		
		Map<String, String> result1 = serviceuser.step2( body );

		if( result1  == null ){
			LinkedHashMap<String, String> errorMessage = new LinkedHashMap<String, String>();
			return 
			new ResponseEntity<LinkedHashMap<String,String>>(errorMessage, HttpStatus.UNAUTHORIZED );
		}
		
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
		if( endResponse.getStatusCode().equals(HttpStatus.OK)) {
			HttpHeaders responseHeaders = endResponse.getHeaders();
			String authorization = responseHeaders.get("Authorization").toString();
			System.out.println(authorization);
			request.setHeader("Authorization", authorization);
		}
		return endResponse;

//		HttpHeaders headers = new HttpHeaders(); 
//		headers = endResponse.getHeaders();
//		LinkedHashMap<String, String> zwischenErg = endResponse.getBody();
//		
//		if ( zwischenErg == null ){
//			LinkedHashMap<String, String> errorMessage = new LinkedHashMap<String, String>();
//			return new ResponseEntity<LinkedHashMap<String,String>>(errorMessage, HttpStatus.UNAUTHORIZED );
//		}
//		zwischenErg.put("prikey", _keypair.get("priKey"));
//		return new ResponseEntity<LinkedHashMap<String, String>>( zwischenErg, headers, HttpStatus.OK );
	}
	

	@RequestMapping( value="/register", method = RequestMethod.POST )
	@Produces("application/json")
	public ResponseEntity<LinkedHashMap<String, String>> 
		register( @RequestBody Map<String, String> registration ) throws BatieCryptoExc{

		System.out.println("comme hrer");
		Preconditions.checkNotNull( registration, "No registration daten provided");
		
		Preconditions.checkNotNull( registration.get("email"), "No Email provided"); 
		Preconditions.checkNotNull( registration.get("firstname"), "Email not provided"); 
		Preconditions.checkNotNull( registration.get("secondname"), "Secondname not provided"); 
		Preconditions.checkNotNull( registration.get("company"), "Company not provided"); 
		Preconditions.checkNotNull( registration.get("password"), "Password not provided"); 
		Preconditions.checkNotNull( registration.get("passphrase"), "Passphrase not provided"); 

		System.out.println( registration.get("email"));
		
		SRP6CryptoParams config = SRP6CryptoParams.getInstance(); 
		
		SRP6VerifierGenerator gen = new SRP6VerifierGenerator(config); 
		BigInteger salt = new BigInteger(SRP6VerifierGenerator.generateRandomSalt());

		BigInteger verifier = gen.generateVerifier(salt, registration.get("email"), registration.get("password"));
		
		Map<String, String> data = new LinkedHashMap<>();
		data.put("email", registration.get("email"));
		data.put("firstname", registration.get("firstname"));
		data.put("secondname", registration.get("secondname"));
		data.put("company", registration.get("company"));
		data.put("verifier", verifier.toString());
		data.put("salt", salt.toString());
		
		RSAKeyGenerator keygen = new RSAKeyGenerator();
		de.app.model.KeyPair pairkey = keygen.generate();

		data.put("pubkey", pairkey.getPubkey());
		data.put("prikey", pairkey.getPrikey());
		
		return POST.simplePost("/session/register", data);
	}

	@RequestMapping( value="/logout", method = RequestMethod.POST )
	public ResponseEntity<LinkedHashMap<String, String>>
	logout(){		
		return null;
	}
	
}
