package de.cryptone.key;

import java.util.Map;
import java.util.HashMap;

import javax.crypto.SecretKey;

import de.cryptone.key.implementation.KeyPBESymImpl;
import de.cryptone.salt.Salt;
import de.cryptone.utils.Helper;

public  class KeyPBESym extends AbstractKey<AbstractKey>{
	
	PBEKeyInfos infos = new PBEKeyInfos();
	KeyPBESymImpl implementation = new KeyPBESymImpl();
	

	public KeyPBESym setPassword(String password) {
		infos.setPassword(password);
		return this;
	}
	
	public KeyPBESym setSalt( String salt ){
		infos.salt = salt;
		return this;
	}
	
	public String generate() {
		if( infos.password == null ) return null; 
		
		byte[] salt;
	
		if( infos.salt != null ) salt = infos.salt.getBytes();
								 salt = Salt.generateSalt(8);
								 
		SecretKey key = this.implementation.generateKey( infos.getPassword(), salt );
		String keySym =  Helper.encode( key.getEncoded() );
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("keySym", keySym );
		result.put("salt", Helper.encode(salt));
		return Helper.toJson( result);		
	}
	
	@Override
	protected KeyPBESym getThis() {
		return this;
	}

}
