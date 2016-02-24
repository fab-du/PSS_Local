package de.app.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6VerifierGenerator;

import de.app.client.ClientSession;
import de.app.exceptions.BatieCryptoExc;
import de.app.model.form.FormAuthentication;
import de.app.model.form.FormChallengeResponse;
import de.app.model.form.FormLogin;
import de.app.model.form.FormLoginChallenge;
import de.app.model.form.FormRegister;
import de.app.service.ServiceUser;
import de.crypto.RSAKeyGenerator;

@RestController
@RequestMapping(value="/session")
public class ControllerSession {

	@Autowired
	ClientSession clientSession;
	@Autowired
	ServiceUser serviceuser;

	@RequestMapping( value="/login", method = RequestMethod.POST )
	@Produces("application/json")
	@Consumes("application/json")
	public ResponseEntity<?>
	login_challenge( @RequestBody FormLogin authdata ) throws RestClientException, Exception{
		
		System.out.println( authdata.getEmail());
		serviceuser.step1( authdata.getEmail()	, authdata.getPassword());

		FormLoginChallenge challenge = new FormLoginChallenge( authdata.getEmail());
		 ResponseEntity<FormChallengeResponse> challengeResponse = clientSession.loginChallenge(challenge);
		 
		 System.out.println( challengeResponse.getBody().toString());
		 if( challengeResponse.getBody() == null ) 
			 throw new Exception("Login error");
		
		FormAuthentication formAuth = serviceuser.step2( challengeResponse.getBody() );

		formAuth.setEmail( authdata.getEmail());
		return clientSession.loginAuthenticate(formAuth);
	}
	

	@RequestMapping( value="/register", method = RequestMethod.POST )
	@Produces("application/json")
	public ResponseEntity<?> 
		register( @RequestBody FormRegister registration ) throws BatieCryptoExc{

		SRP6CryptoParams config = SRP6CryptoParams.getInstance(); 
		
		SRP6VerifierGenerator gen = new SRP6VerifierGenerator(config); 
		BigInteger salt = new BigInteger(SRP6VerifierGenerator.generateRandomSalt());

		BigInteger verifier = gen.generateVerifier(salt, registration.getEmail(), registration.getPassword());
		RSAKeyGenerator keygen = new RSAKeyGenerator();
		de.app.model.KeyPair pairkey = keygen.generate();
		
		registration.setPassphrase(null);
		registration.setPassword(null);
		registration.setSalt(salt.toString());
		registration.setVerifier(verifier.toString());
		registration.setPubkey( pairkey.getPubkey());
		registration.setPrikey( pairkey.getPrikey());

		return clientSession.register(registration);
	}

	@RequestMapping( value="/logout", method = RequestMethod.POST )
	public ResponseEntity<LinkedHashMap<String, String>>
	logout(){		
		return null;
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>>
	exceptionHandler( Exception ex ){
		System.out.println( ex.getLocalizedMessage() );
		Map<String, String> errorMessage = new HashMap<String, String>();
		errorMessage.put("message", "Error while trying loggin in");
		return new ResponseEntity<Map<String,String>>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
}
