package de.cryptone.key.implementation;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class KeyPBESymImpl {

	private static int ITERATION = 65536;
	private static int KEY_LENGHT = 256;
	private static String PBE_ALGO = "PBKDF2WithHmacSHA1";
	
	public SecretKey generateKey( String password, byte[] salt){
		
		//PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, ITERATION);
		PBEKeySpec pbeKeySpec = new PBEKeySpec( password.toCharArray(), salt, ITERATION, KEY_LENGHT);
		
		SecretKeyFactory keyFac;
		
		try {
			keyFac = SecretKeyFactory.getInstance(PBE_ALGO);
			SecretKey key = keyFac.generateSecret(pbeKeySpec);
			SecretKey secret = new SecretKeySpec(key.getEncoded(), "AES");
			return secret;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	

}
