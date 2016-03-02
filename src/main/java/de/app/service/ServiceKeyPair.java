package de.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.app.client.ClientKeyPair;
import de.app.model.KeyPair;

@Component
public class ServiceKeyPair {

	@Autowired
	ClientKeyPair clientKeyPair;
	
	public KeyPair findOne( Long userId ){
		return clientKeyPair.findUserKeyPair(userId).getBody();
	}
}
