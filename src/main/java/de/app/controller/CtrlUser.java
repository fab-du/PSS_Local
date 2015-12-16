package de.app.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import de.app.Get;

import de.app.model.form.FormMisc;
import de.app.model.form.FormRegister;

@RestController
@RequestMapping(value="/api/users")
public class CtrlUser {

	@Autowired
	Get GET;

	public final static String URL = "http://localhost:8080";
	
	
	@RequestMapping( method = RequestMethod.GET )
	public ResponseEntity<? extends ArrayList<HashMap<String,Object>>> get() throws RestClientException, Exception{
		return GET.listGET("/api/users");
	}

	@RequestMapping( method = RequestMethod.POST )
	public ResponseEntity<?> post( @RequestBody FormRegister newuser ) throws RestClientException, Exception{
		//String response = "";
		return null;
		//response = (String)request.getObject().postForEntity(URL + "/api/users", newuser , String.class).getBody();
		//return new ResponseEntity<String>(newuser.getEmail() + response, org.springframework.http.HttpStatus.CREATED);
	}

	@RequestMapping(value="/{userId}", method = RequestMethod.GET )
	public ResponseEntity<?> userId( @PathVariable(value="userId") Long userId ){
		return null;
	}

	@RequestMapping(value="/{userId}/validate", method = RequestMethod.POST )
	public ResponseEntity<?> userId_validate( @PathVariable(value="userId") Long userId,
			@RequestBody FormMisc user_to_validate ){
		return null;
	}

	@RequestMapping(value="/{userId}/addFriend", method = RequestMethod.POST )
	public ResponseEntity<?> userId_addFriend( @PathVariable(value="userId") Long userId,
			@RequestBody FormMisc user_to_add ){
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
	

}
