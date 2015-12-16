package de.app;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.SecurityProperties.Headers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticServer {

	   @RequestMapping(value = "/")
	     String home( HttpServletRequest request, HttpServletResponse response ){
//		   	SecureRandom random = new SecureRandom();
//		   	String angular_token = new BigInteger(130, random ).toString();
//		   	response.setHeader("X-XSRF-TOKEN", angular_token);
//		   	response.setHeader("Authorization", "SRP");
//		   	response.setHeader("WWW-Authenticate", "SRP");
//		   	response.setHeader("realm", "realm");
//		   	response.setHeader("hash-algorithm", "SHA256");
	        return "index";
	    }

	    @RequestMapping(value = "/app")
	     String home_app(){
	        return "index";
	    }

}
