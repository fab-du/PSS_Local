package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import de.app.CryptoneProperties;
import de.app.model.KeyPair;

@Component
public class ClientKeyPair extends AbstractFindRequest<KeyPair> {
	
	CryptoneProperties env;
	
	@Autowired
	public ClientKeyPair(RestClient client, CryptoneProperties env) {
		super(client, KeyPair.class, KeyPair[].class);
		this.env = env;
		super.setUrl(env.getUrl());
		this.setUri( env.getKeypairs() );
	}
	
	public ResponseEntity<KeyPair> findUserKeyPair( Long userId ){
		return this.findOne( userId );
	}
}
