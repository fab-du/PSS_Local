package de.app.service;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;

import com.nimbusds.srp6.SRP6ClientCredentials;
import com.nimbusds.srp6.SRP6ClientSession;
import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6Exception;
import com.nimbusds.srp6.SRP6VerifierGenerator;

import de.app.model.User;
import de.cryptone.salt.Salt;
import de.cryptone.utils.Helper;
import io.jsonwebtoken.impl.crypto.RsaProvider;

public class ServiceUser {
		SRP6ClientSession client;

	public Map<String, String> register( User user ) throws NoSuchAlgorithmException{
		KeyPair keypair = RsaProvider.generateKeyPair(512 );
		byte[] salt = ByteUtil.randomBytes(512);
		Key symkey = new AesKey(salt);
		
		
		

		//Map<String, String> result =  new HashMap<String, String>(); 

//		result.put("pubkey", keypair.getPubkey());
//		result.put("prikey", keypair.getPrikey());
//		result.put("pairkeySalt", keypair.getSalt());
//		result.put("email", user.get("email"));
//		result.put("firstname", user.get("firstname"));
//		result.put("secondname", user.get("secondname"));

//		result.put("company", user.get("company"));
//		result.put("groupname", user.get("company") + "_" + "group");
//
//		Map<String, String> verifierAndSalt = 
//		this.generateVerifierAndSalt( user.get("email"), user.get("password"));
//		result.putAll(verifierAndSalt);
		return null;
	}

	public Map<String, String> addUser( String useremail, String currentUserEmail, String pubkey ){
			Map<String, String> result = new HashMap<String, String>(); 
			result.put("host", currentUserEmail );
			result.put("newuserEmail", useremail );
			return result;
	}


	public Map<String, String> generateVerifierAndSalt( String email, String password ){
		Map<String, String> result = new HashMap<String, String>();
		
//		Returns an SRP-6a crypto parameters instance with precomputed 
//		512-bit prime 'N', matching 'g' value and "SHA-1" hash algorithm.
//		Returns: SRP-6a crypto parameters instance with 512-bit prime 'N', 
//		matching 'g' value and "SHA-1" hash algorithm.
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

	public Map<String, String> step2( Map<String, String> saltAndB ){
		try {
			SRP6ClientCredentials cred = client.step2( SRP6CryptoParams.getInstance(), 
					new BigInteger( saltAndB.get("salt") ),
					new BigInteger( saltAndB.get("B")) );
			Map<String, String> ret = new HashMap<String, String>();
			ret.put( "A" , cred.A.toString() );
			ret.put( "M1", cred.M1.toString() );
			return ret;

		} catch (SRP6Exception e) {
			System.out.println("From Step 2");
			System.out.println(e.getMessage());
			return null;
		}
	}


}
