package de.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import de.app.client.RestClient;
import de.app.model.Friendship;
import de.app.model.KeyPair;
import de.crypto.Signature;

@Component
public class ServiceFriend {

	@Autowired
	ServiceKeyPair serviceKeyPair;
	
	@Autowired
	RestClient client;
	
	public ResponseEntity<?> addFriend( Long userId, Long friendId ){
		Friendship friendship = new Friendship();
		
		KeyPair friendKeyPair = serviceKeyPair.findOne(friendId);
		KeyPair userKeyPair = serviceKeyPair.findOne(userId);
		
		Signature sign = new Signature(); 
		
		String signature = sign.primitive()
							   .privateKey( userKeyPair.getPrikey())
							   .data(friendKeyPair.getPubkey())
							   .sign();
		
		System.out.println(signature);
		friendship.setSignature( signature );
		friendship.setFriendId( friendId );
		
		HttpEntity<Friendship> toPost = new HttpEntity<Friendship>(friendship);
		
		return client.getRestTemplate().postForObject("http://localhost:8080/api/" + userId + "/friends", toPost, ResponseEntity.class);
	}
}
