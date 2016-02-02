package de.app.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import de.app.client.ClientUser;
import de.app.model.User;
import de.app.model.form.FormMisc;

@RestController
@RequestMapping(value="/api/users")
public class ControllerUser {

	
	@Autowired
	ClientUser clientUser;

	
	@RequestMapping( method = RequestMethod.GET )
	public ResponseEntity<User[]> find() throws RestClientException, Exception{
		return clientUser.find(null, null);
	}
	
	@RequestMapping(value="/{userId}",  method = RequestMethod.GET )
	public ResponseEntity<User> find(@PathVariable(value="userId") Long userId) throws RestClientException, Exception{
		return clientUser.findOne( userId, null );
	}

	@RequestMapping(value="/{userId}/validate", method = RequestMethod.POST )
	public ResponseEntity<?> userId_validate( @PathVariable(value="userId") Long userId,
			@RequestBody FormMisc user_to_validate ){
		return null;
	}

	@RequestMapping(value="/{userId}/addFriend", method = RequestMethod.POST )
	public ResponseEntity<?> userId_addFriend( @PathVariable(value="userId") Long userId, @RequestBody FormMisc user_to_add ){
		return null;
	}

	@RequestMapping(value="/{userId}/revoke", method = RequestMethod.POST )
	public ResponseEntity<?> userId_revoke( @PathVariable(value="userId") Long userId,
			@RequestBody FormMisc user_to_revoke ){
		return null;
	}

	@RequestMapping(value="/{userId}/addToGroup", method = RequestMethod.POST )
	public ResponseEntity<?> userId_addToGroup( @PathVariable(value="userId") Long userId,
			@RequestBody FormMisc user_to_add_to_group ){
		return null;
	}
	
	@ExceptionHandler({Exception.class})
	public void exceptionHandler( HttpServletRequest request, Exception exception){
		System.out.println( exception.getMessage());
	}
	
}
