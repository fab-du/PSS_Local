package de.cryptone.key;

import java.util.HashMap;
import java.util.Map;
import de.cryptone.utils.Helper;

public class KeyPair extends AbstractKeyPair {

	
	@Override
	protected AbstractKeyPair getThis() {
		return this;
	}

	@Override
	public String generate() {
		java.security.KeyPair keypair = super.implementation.generateKey();
		String priKey = Helper.encode(keypair.getPrivate().getEncoded()); 
		String  pubKey = Helper.encode(keypair.getPublic().getEncoded());
		Map<String, String> result = new HashMap<String, String>();
		
		if( super.password == null ){
			result.put("priKey", priKey);
			result.put("pubKey", pubKey);
			return Helper.toJson(result);
		}
		
		return null;
	}



}
