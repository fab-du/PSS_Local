package de.app.client;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.app.model.User;


@Component
public class ClientUser extends AbstractFindRequest<User> {
	
	public final static String URI = "/api/users";
	
	public final AbstractWriteRequest<?, User> Writer;

	
	@Autowired
	public ClientUser(RestClient client) {
		super(client, User.class, User[].class );
		this.setUri(URI);
		Writer = new AbstractWriteRequest<>(client, Object.class, User.class );
	}
	
	
	public AbstractWriteRequest<?, User> getWriter(){
		return this.Writer;
	}
	
}
