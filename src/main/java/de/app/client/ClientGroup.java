package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.app.model.Group;

@Component
public class ClientGroup extends AbstractFindRequest<Group>{

	public final static String URI = "/api/groups";

	
	@Autowired
	public ClientGroup(RestClient client) {
		super(client, Group.class, Group[].class);
		super.setUri( URI );
	}

}
