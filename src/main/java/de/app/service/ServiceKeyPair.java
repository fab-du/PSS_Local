package de.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.app.client.RestClient;
import de.app.model.KeyPair;

@Component
public class ServiceKeyPair {

	@Autowired
	RestClient rest;
	
	public KeyPair findOne( Long userId ){
		KeyPair keypair = rest.getRestTemplate().getForObject("http://localhost:8080/api/" + userId + "/keypair", KeyPair.class);
		return keypair;
	}
}
