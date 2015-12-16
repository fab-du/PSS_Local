package de.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.app.model.User;
import de.app.model.form.FormMisc;

@RestController
@RequestMapping(value="/api/friends")
public class CtrlFriend {

	@RequestMapping( method=RequestMethod.GET  )
	public ResponseEntity<User> get(){
		return null;
	}

	@RequestMapping( method=RequestMethod.POST)
	public ResponseEntity<?> post( FormMisc newFriend ){
		return null;
	}

	@RequestMapping( value="/{friendId}", method=RequestMethod.GET )
	public ResponseEntity<?> friendId( @PathVariable(value="friendId") Long friendId ){
		return null;
	}

	@RequestMapping( value="/{friendId}/users", method=RequestMethod.GET )
	public ResponseEntity<?> friendId_users( @PathVariable(value="friendId") Long friendId ){
		return null;
	}

	@RequestMapping( value="/{friendId}/users/{userId}", method=RequestMethod.GET )
	public ResponseEntity<?> friendId_users_userId( @PathVariable(value="friendId") Long friendId,
			@PathVariable(value="userId") Long userId ){
		return null;
	}

	@RequestMapping( value="/{friendId}/users/{userId}/validate", method=RequestMethod.GET )
	public ResponseEntity<?> friendId_users_userId_validate( @PathVariable(value="friendId") Long friendId,
			@PathVariable(value="userId") Long userId ){
		return null;
	}

	@RequestMapping( value="/{friendId}/users/{userId}/addFriend", method=RequestMethod.GET )
	public ResponseEntity<?> friendId_users_userId_addFriend( @PathVariable(value="friendId") Long friendId,
			@PathVariable(value="userId") Long userId ){
		return null;
	}

	@RequestMapping( value="/{friendId}/users/{userId}/revoke", method=RequestMethod.GET )
	public ResponseEntity<?> friendId_users_userId_revoke( @PathVariable(value="friendId") Long friendId,
			@PathVariable(value="userId") Long userId ){
		return null;
	}

	@RequestMapping( value="/{friendId}/users/{userId}/addToGroup", method=RequestMethod.GET )
	public ResponseEntity<?> friendId_users_userId_addToGroup( @PathVariable(value="friendId") Long friendId,
			@PathVariable(value="userId") Long userId ){
		return null;
	}

}
