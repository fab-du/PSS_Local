package de.app.controller;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
import de.app.model.KeyPair;
import de.app.model.form.FormAuthentication;
import de.app.model.form.FormChallengeResponse;
import de.app.model.form.FormLogin;
import de.app.model.form.FormLoginChallenge;
import de.app.model.form.FormRegister;
import de.app.service.ServiceGroup;
import de.app.service.ServiceUser;
import de.crypto.RSACrypto;

@RestController
@RequestMapping(value="/session")
public class ControllerSession {

	@Autowired
	ClientSession clientSession;
	@Autowired
	ServiceUser serviceuser;
	@Autowired
	ServiceGroup serviceGroup;

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
		register( @RequestBody FormRegister registration ) throws NoSuchAlgorithmException, InvalidKeySpecException{

		System.out.println( registration.toString());
		SRP6CryptoParams config = SRP6CryptoParams.getInstance(); 

		SRP6VerifierGenerator gen = new SRP6VerifierGenerator(config); 
		BigInteger salt = new BigInteger(SRP6VerifierGenerator.generateRandomSalt());

		BigInteger verifier = gen.generateVerifier(salt, registration.getEmail(), registration.getPassword());
		registration.setVerifier(verifier.toString());

		RSACrypto rsacrypto = new RSACrypto();
		KeyPair pairkey = rsacrypto.generateKey( registration.getPassphrase() );

		/*
		 * remove password and passphrase
		 */
		registration.setPassphrase(null);
		registration.setPassword(null);
		
		registration.setSalt(pairkey.getSalt());
		registration.setPubkey(pairkey.getPubkey());
		registration.setPrikey(pairkey.getPrikey());

		return clientSession.register(registration);
	}

	@RequestMapping( value="/logout", method = RequestMethod.POST )
	public ResponseEntity<?>
	logout(){		
		return clientSession.logout();
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
