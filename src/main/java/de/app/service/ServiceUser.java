package de.app.service;

import de.app.model.form.FormRegister;
import de.cryptone.crypto.CryptFactor;
//import de.cryptone.crypto.RSAPBECrypto;

import java.math.BigInteger;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import com.nimbusds.*;
import com.nimbusds.srp6.SRP6ClientCredentials;
import com.nimbusds.srp6.SRP6ClientSession;
import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6Exception;
import com.nimbusds.srp6.SRP6VerifierGenerator;

public class ServiceUser {
		SRP6ClientSession client;

		public Map<String, String> register( Map<String, String> user ){
//			RSAPBECrypto rsa = (RSAPBECrypto) CryptFactor.getInstance(CryptFactor.CRYPT_ASYM_RSA_PBE);
//			
//			de.cryptone.models.KeyPair keypair = rsa.generateKey( user.get("passphrase") );
//
//			Map<String, String> result =  new HashMap<String, String>(); 
//
//			result.put("pubkey", keypair.getPubkey());
//			result.put("prikey", keypair.getPrikey());
//			result.put("pairkeySalt", keypair.getSalt());
//			result.put("email", user.get("email"));
//			result.put("firstname", user.get("firstname"));
//			result.put("secondname", user.get("secondname"));
//
//			result.put("company", user.get("company"));
//			result.put("groupname", user.get("company") + "_" + "group");
//
//			Map<String, String> verifierAndSalt = 
//			this.generateVerifierAndSalt( user.get("email"), user.get("password"));
//			result.putAll(verifierAndSalt);
//			return result;
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
			System.out.println(e.getMessage());
			return null;
		}
	}


}
