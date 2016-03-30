package de.app.controller;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6VerifierGenerator;

import de.app.CacheConfig;
import de.app.client.ClientSession;
import de.app.client.RestClient;
import de.app.model.Group;
import de.app.model.KeyPair;
import de.app.model.KeySym;
import de.app.model.form.FormAuthentication;
import de.app.model.form.FormChallengeResponse;
import de.app.model.form.FormLogin;
import de.app.model.form.FormLoginAuthenticateResponse;
import de.app.model.form.FormLoginChallenge;
import de.app.model.form.FormRegister;
import de.app.service.ServiceGroup;
import de.app.service.ServiceUser;
import de.crypto.AESCrypto;
import de.crypto.RSACrypto;

@RestController
@RequestMapping(value="/session")
@Service
public class ControllerSession extends AbstractController{

	@Autowired
	ApplicationContext appContext;
	@Autowired
	ClientSession clientSession;
	@Autowired
	ServiceUser serviceuser;
	@Autowired
	ServiceGroup serviceGroup;
	@Autowired 
	CacheManager cacheManager;
	@Autowired
	RestClient client;

	@RequestMapping( value="/login", method = RequestMethod.POST )
	@Produces("application/json")
	@Consumes("application/json")
	@Cacheable(value=CacheConfig.CACHE_SESSION)
	public ResponseEntity<FormLoginAuthenticateResponse>
	login_challenge( @RequestBody FormLogin authdata ) throws RestClientException, Exception{
		System.out.println(authdata.toString());
		
		RSACrypto rsa = new RSACrypto();
		serviceuser.step1( authdata.getEmail()	, authdata.getPassword());

		FormLoginChallenge challenge = new FormLoginChallenge( authdata.getEmail());
		 ResponseEntity<FormChallengeResponse> challengeResponse = clientSession.loginChallenge(challenge);
		 
		 if( challengeResponse.getBody() == null ) 
			 throw new Exception("Login error");
		
		FormAuthentication formAuth = serviceuser.step2( challengeResponse.getBody() );

		formAuth.setEmail( authdata.getEmail());
		KeyPair sessionkey = serviceuser.generateSessionKey();
		formAuth.setSpubkey(sessionkey.getPubkey());
		ResponseEntity<FormLoginAuthenticateResponse> response = clientSession.loginAuthenticate(formAuth);
		
		cacheManager.getCache( CacheConfig.CACHE_SESSION).put("pubkey", sessionkey );
		
		if ( authdata.getPassphrase() != null ){
			cacheManager.getCache( CacheConfig.CACHE_SESSION).put("passphrase", rsa.encrypt(sessionkey.getPubkey(), authdata.getPassphrase()));	
		}
		
		cacheManager.getCache(CacheConfig.CACHE_SESSION).put("currentUser", response.getBody());
		return response;
	}
	
	@RequestMapping( value="/register", method = RequestMethod.POST )
	@Produces("application/json")
	public ResponseEntity<?> 
		register( @RequestBody FormRegister registration ) throws NoSuchAlgorithmException, InvalidKeySpecException, Exception{

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

		Group group = new Group();
		
		group.setName( registration.getEmail() + "_privateGroup");
		AESCrypto aesCrypto = new AESCrypto();
		KeySym groupKey = aesCrypto.generateKey();

		RSACrypto rsa = new RSACrypto();
		String encSymKey = rsa.encrypt( pairkey.getPubkey(), groupKey.getSymkey() );
		groupKey.setSymkey(encSymKey);
		group.setGroupkey(groupKey);
		
		registration.setGroup(group);
		registration.setSalt(pairkey.getSalt());
		registration.setSrpsalt( salt.toString());
		registration.setPubkey(pairkey.getPubkey());
		registration.setPrikey(pairkey.getPrikey());

		return clientSession.register(registration);
	}

	@RequestMapping( value="/logout", method = RequestMethod.POST )
	public ResponseEntity<?>
	logout(){		
		ResponseEntity<?> response = clientSession.logout();
		Collection<String> cacheNames = cacheManager.getCacheNames(); 
		cacheNames.forEach( name -> {
			cacheManager.getCache(name).clear();
		});
		
		client.clearHeaders();
		return response;
	}

}
