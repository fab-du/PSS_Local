package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.app.CryptoneProperties;
import de.app.model.KeySym;

@Service
public class ClientSymKey extends AbstractFindRequest<KeySym>{
	CryptoneProperties env;
	
	@Autowired
	public ClientSymKey(RestClient client,CryptoneProperties env) {
		super(client, KeySym.class, KeySym[].class);
		this.env = env;
		this.setUri(env.getSymkeys());
	}
	
	public KeySym findGroupKeySym( Long groupId ){
		return this.findOne( groupId ).getBody();
	}
}
