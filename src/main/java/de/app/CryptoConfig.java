package de.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import de.app.properties.AsymmetricCipherProperties;
import de.app.properties.SymmetricCipherProperties;
import de.crypto.AESCrypto;
import de.crypto.RSACrypto;

@Service
public class CryptoConfig {
	
	AsymmetricCipherProperties asymProp;
	SymmetricCipherProperties  symProp;
	
	public CryptoConfig() {
		super();
	}
	
	@Autowired
	public CryptoConfig(AsymmetricCipherProperties asymProp, SymmetricCipherProperties symProp) {
		System.out.println( asymProp.getCipher_algo());
		this.asymProp = asymProp;
		this.symProp  = symProp;
	}
	
	@Bean
	RSACrypto rsaCrypto(){
		return new RSACrypto(asymProp.getKeylenght(), asymProp.getCipher_algo(), asymProp.getKey_algo()); 
	}
	
	@Bean
	AESCrypto aesCrypto(){
		return new AESCrypto(symProp.getKeylenght(), symProp.getKey_algo(), symProp.getCipher_algo());
	}
}
