package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ClientFriend extends ClientUser{
	String uri="/api/{preffix1}/friends/{suffix1}/{suffix2}/{suffix3}";

	@Autowired
	public ClientFriend(RestClient client) {
		super(client);
		this.setUri(uri);
		this.Writer.setUri(uri);
	}
	
}
