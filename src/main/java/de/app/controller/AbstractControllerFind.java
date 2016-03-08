package de.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public class AbstractControllerFind {
	
	String baseuri;
	
	public AbstractControllerFind( String baseuri ) {
		this.baseuri = baseuri;
	}

	@RequestMapping( method=RequestMethod.GET )
	@ResponseBody
	public void find( ){
	}
	
	@RequestMapping( method=RequestMethod.GET )
	@ResponseBody
	public void findOne( Long id ){
	}
	
}
