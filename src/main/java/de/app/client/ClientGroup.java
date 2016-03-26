package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.app.CryptoneProperties;
import de.app.model.Group;

@Service
public class ClientGroup extends AbstractFindRequest<Group>{
	
	CryptoneProperties env;
	AbstractWriteRequest<?, Group> Writer;
	
	@Autowired
	public ClientGroup(RestClient client,CryptoneProperties env) {
		super(client, Group.class, Group[].class);
		this.env = env;
		super.setUri( env.getGroups() );
		super.setUrl(env.getUrl());
		Writer = new AbstractWriteRequest<Object, Group>(client, Object.class, Group.class );
		Writer.setUri( env.getGroups() );
		Writer.setUrl(env.getUrl());
	}
	
	public AbstractWriteRequest<?, Group> getWriter(){
		return this.Writer;
	}
	
	@Cacheable(value=de.app.CacheConfig.CACHE_GROUPS )
	public ResponseEntity<Group[]> find() {
		return super.find(null, null, null, null);
	}
	
	public ResponseEntity<Group> findOne( Long groupId ) {
		return super.findOne(groupId, null, null, null);
	}
}
