package de.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public abstract class IRest {
	
	@Autowired
	RestRequest rest;

	public final String URL = "http://localhost:8080";


}
