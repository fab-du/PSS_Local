package de.cryptone.key.implementation;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class KeySymImpl {
	
	private static String SYM_ALGO = "AES";
	private static int ITERATION = 2000;
	private static int KEY_LENGHT = 256;
	
	public  SecretKey generateKey(){
		KeyGenerator keygen;
		try {
			keygen = KeyGenerator.getInstance("AES");
			SecretKey key = keygen.generateKey();
			SecretKey secretkey = new SecretKeySpec(key.getEncoded(), ITERATION , KEY_LENGHT, SYM_ALGO);
			return secretkey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	

}
