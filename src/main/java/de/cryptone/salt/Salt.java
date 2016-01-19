package de.cryptone.salt;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Salt {

	public static byte[] generateSalt(int lenght){
		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
		byte[] result = new byte[lenght];
		random.nextBytes(result);
		return result;
	}
	
	public static String generateSaltString( int lenght ){
		byte[] ret = Salt.generateSalt(lenght);
		return Base64.getEncoder().encodeToString(ret);
	}
	
}
