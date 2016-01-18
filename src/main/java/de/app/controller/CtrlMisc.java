package de.app.controller;

import java.util.LinkedHashMap;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
public class CtrlMisc extends ResponseEntityExceptionHandler{
	
	@RequestMapping( value="/auth_error", method = RequestMethod.GET )
	public ResponseEntity<LinkedHashMap<String, String>> 
	unauthorized(){
		LinkedHashMap<String, String> response = new LinkedHashMap<>();
		response.put("error", "not authenticate");
		return new ResponseEntity<>( response, HttpStatus.UNAUTHORIZED ); 
	}
	
}
