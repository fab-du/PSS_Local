package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.app.model.User;


@Component
public class ClientUser extends AbstractFindRequest<User> {
	
	public  AbstractWriteRequest<?, User> Writer;
	
	String uri="/api/{prefix1}/{prefix2}/users/{suffix1}/{suffix2}";
	
	@Override
	public void setUri(String uri) {
		super.setUri(uri);
	}
	
	@Autowired
	public ClientUser(RestClient client) {
		super(client, User.class, User[].class );
		this.setUri(uri);
		Writer = new AbstractWriteRequest<>(client, Object.class, User.class );
		Writer.setUri(uri);
	}
	
	
	public AbstractWriteRequest<?, User> getWriter(){
		return this.Writer;
	}
	
}
