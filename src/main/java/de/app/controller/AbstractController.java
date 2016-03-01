package de.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class AbstractController {
	
//	@ExceptionHandler({Exception.class, NullPointerException.class})
//	public ResponseEntity<Map<String, String>>
//	exceptionHandler(HttpServletRequest request, Exception ex ){
//		System.out.println( ex.getClass().toString());
//		System.out.println( ex.getStackTrace() );
//		System.out.println(  ex.getCause() );
//		Map<String, String> errorMessage = new HashMap<String, String>();
//		errorMessage.put("message", "Error while trying loggin in");
//		return new ResponseEntity<Map<String,String>>(errorMessage, HttpStatus.BAD_REQUEST);
//	}
	
}
