package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import de.app.model.Group;

@Component
public class ClientGroup extends AbstractFindRequest<Group>{

	private String uri = "/api/groups/{suffix1}/{suffix2}/{suffix3}/{suffix4}/";
	
	AbstractWriteRequest<?, Group> Writer;
	
	@Autowired
	public ClientGroup(RestClient client) {
		super(client, Group.class, Group[].class);
		super.setUri( uri );
		Writer = new AbstractWriteRequest<Object, Group>(client, Object.class, Group.class );
		Writer.setUri(uri);
		System.out.println(Writer.getUri());
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
