package de.crypto;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Assert.*;

import de.app.model.KeyPair;
import de.crypto.AESCrypto;
import de.crypto.RSACrypto;

public class RSATest {
	
	List<String> symKeys; 
	List<KeyPair> pairKeys;
	public static int KEY=10;
	
	@Before
	public void initSymKeys(){
		symKeys = new ArrayList<String>();
		AESCrypto aes = new AESCrypto();
		for (int i = 0; i < KEY; i++) {
			//symKeys.add( aes.generateKey());
		}
	}
	
	@Before
	public void initPairKeys() throws NoSuchAlgorithmException{
		pairKeys = new ArrayList<>();
		RSACrypto rsa = new RSACrypto();
		for (int i = 0; i < KEY; i++) {
			pairKeys.add( rsa.generateKey());
		}
	}
	
	@Test
	public void testListNotNull(){
		System.out.println( "AES Key must have been generated NOT NULL");
		Assert.assertNotNull( symKeys );
	}

}
