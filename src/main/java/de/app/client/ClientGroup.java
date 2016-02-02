package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.app.model.Group;

@Component
public class ClientGroup extends AbstractFindRequest<Group>{

	private String uri = "/api/groups/{suffix1}/{suffix2}/{suffix3}/{suffix4}/";
	
	public  AbstractWriteRequest<?, Group> Writer;
	
	@Autowired
	public ClientGroup(RestClient client) {
		super(client, Group.class, Group[].class);
		super.setUri( uri );
		Writer = new AbstractWriteRequest<>(client, Object.class, Group.class );
		Writer.setUri(uri);
	}
}
