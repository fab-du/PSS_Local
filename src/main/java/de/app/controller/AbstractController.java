package de.app.controller;

import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractController {
	
	@Value("{remote.url}")
	String url;
	
	String uri; 
	
	public abstract void find();
	
	public abstract void findOne();
	
	public abstract void update();
	
	public abstract void create();
	
	public void setUri( String uri ){
		this.uri = uri;
	}
	
	public String getUrl(){
		return this.url;
	}

}
