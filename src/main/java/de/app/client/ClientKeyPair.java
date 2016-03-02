package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.app.model.KeyPair;

@Service
public class ClientKeyPair extends AbstractFindRequest<KeyPair> {

	String uri = "/api/{userId}/keypair";
	
	@Autowired
	public ClientKeyPair(RestClient client) {
		super(client, KeyPair.class, KeyPair[].class);
		this.setUri(uri);
	}
	
	public ResponseEntity<KeyPair> findUserKeyPair( Long userId ){
		return this.findOne( userId );
	}
}
