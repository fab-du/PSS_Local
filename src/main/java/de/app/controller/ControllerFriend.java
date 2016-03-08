package de.app.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.app.model.User;
import de.app.service.ServiceFriend;
import de.app.client.ClientFriend;
import de.app.client.ClientUser;

@RestController
@RequestMapping(value="/api/{userId}/friends")
public class ControllerFriend extends AbstractController{
	
	@Autowired
	ClientFriend clientFriend;
	@Autowired
	ClientUser clientUser;
	@Autowired
	ServiceFriend serviceFriend;

	@Cacheable(de.app.CacheConfig.CACHE_FRIENDS)
	@RequestMapping( method=RequestMethod.GET )
	public ResponseEntity<User[]> find(@PathVariable(value="userId") Long userId){
		return clientFriend.find( userId );
	}
	
	@RequestMapping( value="/{friendId}", method=RequestMethod.GET )
	public ResponseEntity<?> findOne( @PathVariable(value="userId") Long userId, @PathVariable(value="friendId") Long friendId ){
		return clientFriend.findOne( userId, friendId );
	}
	
	@RequestMapping( method=RequestMethod.POST)
	public ResponseEntity<?> create( @PathVariable("userId") Long userId, @RequestBody User user ) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, Exception{
		return serviceFriend.addFriend(userId, user.getId() );
	}

	@RequestMapping( value="/{friendId}/revoke", method=RequestMethod.DELETE )
	public ResponseEntity<?> revoke( @PathVariable(value="userId") Long userId, @PathVariable(value="friendId") Long friendId){
		return clientFriend.revoke(userId, friendId);
	}

	@RequestMapping( value="/{friendId}/addToGroup/{groupId}", method=RequestMethod.PUT )
	public ResponseEntity<?> friendId_users_userId_addToGroup( @PathVariable(value="userId") Long userId, @PathVariable(value="friendId") Long friendId, @PathVariable(value="groupId") Long groupId ){
		return  clientFriend.Writer.put(userId, friendId,"addToGroup", groupId );
	}
}
