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
		String signature = serviceSession.sign( friendKeyPair.getPubkey()); 
		
		Friendship friendship = new Friendship();
		friendship.setSignature( signature );
		friendship.setFriendId( friendId );
		
		HttpEntity<Friendship> toPost = new HttpEntity<Friendship>( friendship, client.getHeaders());
		return client.getRestTemplate().postForObject("http://localhost:8080/api/" + userId + "/friends", toPost, ResponseEntity.class);
	}

	public ResponseEntity<?> addUserToGroup( Long userId, Long friendId, Long groupId ) throws Exception{
		KeyPair friendKeyPair = serviceKeyPair.findOne(friendId);
		KeySym groupKey 	  = clientKeySym.findGroupKeySym(groupId);
		
		String dec_groupSecretKey = serviceSession.decryptWithCurrentUserPK( groupKey.getSymkey());
        RSACrypto rsa  = new RSACrypto();
		String enc_groupSecretKey = rsa.encrypt( friendKeyPair.getPubkey(), dec_groupSecretKey);
		groupKey.setSymkey( enc_groupSecretKey );

		HttpEntity<KeySym> toPost = new HttpEntity<KeySym>( groupKey, client.getHeaders());
		return client.getRestTemplate().postForObject("http://localhost:8080/api/xx/" + groupId + "/users/" + friendId , toPost, ResponseEntity.class);
	}
	
}
