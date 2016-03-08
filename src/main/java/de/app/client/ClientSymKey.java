package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.app.model.KeySym;

@Service
public class ClientSymKey extends AbstractFindRequest<KeySym>{
	
	String uri = "/api/{groupId}/keysym";
	
	@Autowired
	public ClientSymKey(RestClient client) {
		super(client, KeySym.class, KeySym[].class);
		this.setUri(uri);
	}
	
	public KeySym findGroupKeySym( Long groupId ){
		return this.findOne( groupId ).getBody();
	}
}
