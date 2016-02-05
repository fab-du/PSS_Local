package de.crypt.key;

import java.security.KeyPair;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import de.crypt.interfaces.KeyGenerator;
import de.cryptone.utils.Helper;

public class KeyPairGenerator extends KeyPairUtil implements KeyGenerator  {
	
	protected KeyPairGenerator(String provider, SecureRandom secureRandom) {
		super(provider, secureRandom);
	}

	String algorithm;

	@Override
	public 
	String generate() {
		
		try {
			java.security.KeyPairGenerator keygen = this.getKeyPairGenerator();
			keygen.initialize(1024, secureRandom);
			KeyPair keypair = keygen.generateKeyPair();
			return this.map(keypair);
		} catch (DeCryptException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public 
	String generate(String passphrase) {
		return null;
	}

	@Override
	String getAlgorithm() {
		return algorithm;
	}
	
	public 
	void algorithm( String algorithm ){
		this.algorithm = algorithm;
	}
	
	String map( KeyPair keypair  ){
		String encodedPub = Helper.encode( keypair.getPublic().getEncoded());
		String encodedPri = Helper.encode( keypair.getPrivate().getEncoded());
		Map<String, String> result = new HashMap<>();
		result.put("pubkey", encodedPub);
		result.put("prikey", encodedPri);
		return Helper.toJson(result);
	}
	
}
