package de.app.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import de.app.client.ClientFriend;
import de.app.client.ClientSymKey;
import de.app.client.RestClient;
import de.app.model.Friendship;
import de.app.model.KeyPair;
import de.app.model.KeySym;
import de.crypto.RSACrypto;
import de.crypto.Signature;

@Component
public class ServiceFriend {

	@Autowired
	ServiceKeyPair serviceKeyPair;
	@Autowired
	RestClient client;
	@Autowired
	ClientFriend clientFriend;
	@Autowired
	CacheManager cacheManager;
	@Autowired
	ClientSymKey clientKeySym;
	@Autowired
	ServiceSession serviceSession;
	
	public ResponseEntity<?> addFriend( Long userId, Long friendId ) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, Exception{
		KeyPair friendKeyPair = serviceKeyPair.findOne(friendId);
		KeyPair userKeyPair = serviceKeyPair.findOne(userId);
		
		String dec_passphrase = serviceSession.decryptPassphrase();
		
		Signature sign = new Signature(); 
		String signature = sign.sign( userKeyPair, dec_passphrase, friendKeyPair.getPubkey()); 
		
		Friendship friendship = new Friendship();
		friendship.setSignature( signature );
		friendship.setFriendId( friendId );
		
		HttpEntity<Friendship> toPost = new HttpEntity<Friendship>( friendship, client.getHeaders());
		return client.getRestTemplate().postForObject("http://localhost:8080/api/" + userId + "/friends", toPost, ResponseEntity.class);
	}

	public void addUserToGroup( Long userId, Long friendId, Long groupId ) throws Exception{
		KeyPair friendKeyPair = serviceKeyPair.findOne(friendId);
		KeyPair userKeyPair   = serviceKeyPair.findOne(userId);
		KeySym groupKey 	  = clientKeySym.findGroupKeySym(groupId);
		
		String dec_passphrase = serviceSession.decryptPassphrase();
		RSACrypto rsa = new RSACrypto();
		String dec_groupSecretKey = rsa.decrypt(userKeyPair, dec_passphrase, groupKey.getSymkey());
		String enc_groupSecretKey = rsa.encrypt( friendKeyPair.getPubkey(), dec_groupSecretKey);
		groupKey.setSymkey( enc_groupSecretKey );
	}
	
}
