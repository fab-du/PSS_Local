package de.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import de.app.CacheConfig;
import de.app.model.KeyPair;
import de.app.model.form.FormLoginAuthenticateResponse;
import de.crypto.RSACrypto;
import de.crypto.Signature;

@Service
public class ServiceSession {
	
	@Autowired
	CacheManager cacheManager;
	@Autowired
	RSACrypto rsa;
	
	public String decryptPassphrase() throws Exception{
		String enc_passphrase = cacheManager.getCache( CacheConfig.CACHE_SESSION).get("passphrase", String.class);
		KeyPair sessionKey = cacheManager.getCache( CacheConfig.CACHE_SESSION).get("pubkey", KeyPair.class);
		return rsa.decrypt( sessionKey.getPrikey(), enc_passphrase);
	}
	
	public String decryptWithCurrentUserPK( String message ) throws Exception{
		FormLoginAuthenticateResponse currentUser = cacheManager.getCache(CacheConfig.CACHE_SESSION ).get("currentUser", FormLoginAuthenticateResponse.class);
		String dec_passphrase = this.decryptPassphrase();
		return rsa.decrypt(currentUser.getUserkeypair(), dec_passphrase, message);
	}
	
	public String encryptWithCurrentUserPubkey( String message ) throws Exception{
		FormLoginAuthenticateResponse currentUser = cacheManager.getCache(CacheConfig.CACHE_SESSION ).get("currentUser", FormLoginAuthenticateResponse.class);
		return rsa.encrypt( currentUser.getUserkeypair().getPubkey(), message);
	}

    public String sign( String pubkey ) throws Exception{
		FormLoginAuthenticateResponse currentUser = cacheManager.getCache(CacheConfig.CACHE_SESSION ).get("currentUser", FormLoginAuthenticateResponse.class);
		String dec_passphrase = this.decryptPassphrase();
        return new Signature().sign(  currentUser.getUserkeypair(), dec_passphrase, pubkey );
    }
}
