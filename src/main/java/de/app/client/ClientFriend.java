package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class ClientFriend extends ClientUser{
	
	@Value("${remote.users}")
	String uri="/api/{currentUserId}/friends";

	@Autowired
	public ClientFriend(RestClient client) {
		super(client);
		this.setUri(uri);
		this.Writer.setUri(uri);
	}
	
}
