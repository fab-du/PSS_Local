package de.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.app.model.User;
import de.app.client.ClientFriend;


@RestController
@RequestMapping(value="/api/{userId}/friends")
public class ControllerFriend {
	
	@Autowired
	ClientFriend clientFriend;

	@RequestMapping( method=RequestMethod.GET  )
	public ResponseEntity<User[]> find(@PathVariable(value="userId") Long userId){
		return clientFriend.find(  userId, null, null, null );
	}
	
	@RequestMapping( value="/{friendId}", method=RequestMethod.GET )
	public ResponseEntity<?> findOne( @PathVariable(value="userId") Long userId, @PathVariable(value="friendId") Long friendId ){
		return clientFriend.findOne( userId, friendId, null, null);
	}
	
	@RequestMapping( method=RequestMethod.POST)
	public ResponseEntity<?> create( @PathVariable("userId") Long userId, @RequestBody User user ){
		return clientFriend.Writer.create( user, userId, null, null, null);
	}


	@RequestMapping( value="/{friendId}/revoke", method=RequestMethod.DELETE )
	public ResponseEntity<?> revoke( @PathVariable(value="userId") Long userId, @PathVariable(value="friendId") Long friendId){
		return clientFriend.Writer.delete(null, userId, friendId, "/revoke", null);
	}

	@RequestMapping( value="/{friendId}/addToGroup/{groupId}", method=RequestMethod.PUT )
	public ResponseEntity<?> friendId_users_userId_addToGroup( @PathVariable(value="userId") Long userId, @PathVariable(value="friendId") Long friendId, @PathVariable(value="groupId") Long groupId ){
		return  clientFriend.Writer.put(userId, friendId,"addToGroup", groupId );
	}

}
