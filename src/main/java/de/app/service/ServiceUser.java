package de.app.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimbusds.srp6.SRP6ClientCredentials;
import com.nimbusds.srp6.SRP6ClientSession;
import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6Exception;
import com.nimbusds.srp6.SRP6VerifierGenerator;

import de.app.client.RestClient;
import de.app.model.form.FormAuthentication;
import de.app.model.form.FormChallengeResponse;

@Service
public class ServiceUser {

	@Autowired
	RestClient restClient;
	SRP6ClientSession client;

	public Map<String, String> generateVerifierAndSalt( String email, String password ){
		Map<String, String> result = new HashMap<String, String>();
		
		//	Returns an SRP-6a crypto parameters instance with precomputed 
		//	512-bit prime 'N', matching 'g' value and "SHA-1" hash algorithm.
		//	Returns: SRP-6a crypto parameters instance with 512-bit prime 'N', 
		//	matching 'g' value and "SHA-1" hash algorithm.
		SRP6CryptoParams config = SRP6CryptoParams.getInstance();	
		// Create verifier generator
		SRP6VerifierGenerator gen = new SRP6VerifierGenerator(config);
		// Random 16 byte salt 's'
		BigInteger salt = new BigInteger(SRP6VerifierGenerator.generateRandomSalt());
		// Compute verifier 'v'
		BigInteger verifier = gen.generateVerifier(salt, email, password);
		String s = salt.toString();
		String v = verifier.toString();
		result.put("verifier", v);
		result.put("salt", s);
		return result;
	}

	public void step1( String email, String password ){
		client = new SRP6ClientSession();
		client.step1(email, password );
	}

	public FormAuthentication step2( FormChallengeResponse saltAndB ){
	 try{
			SRP6ClientCredentials cred = client.step2( SRP6CryptoParams.getInstance(), new BigInteger(saltAndB.getSalt()),new BigInteger(saltAndB.getB()));
			return new FormAuthentication( cred.A.toString(), cred.M1.toString());
		} catch (SRP6Exception e) {
			return null;
		}
	}
	
	public void saveAuthenticationToken( String authenticationToken ) throws Exception{
		if( authenticationToken == null )
			throw new Exception("Malformed Token");
			restClient.setHeader("Authentication", authenticationToken);
	}

}
