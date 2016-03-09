package de.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import de.app.CacheConfig;
import de.app.model.KeyPair;
import de.crypto.RSACrypto;

@Service
public class ServiceSession {
	
	@Autowired
	CacheManager cacheManager;
	
	String decryptPassphrase() throws Exception{
		String enc_passphrase = cacheManager.getCache( CacheConfig.CACHE_SESSION).get("passphrase", String.class);
		KeyPair sessionKey = cacheManager.getCache( CacheConfig.CACHE_SESSION).get("pubkey", KeyPair.class);
		RSACrypto rsa = new RSACrypto();
		String dec_passphrase = rsa.decrypt( sessionKey.getPrikey(), enc_passphrase);
		return dec_passphrase;
	}
}
