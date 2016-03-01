package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import de.app.model.User;

@Service
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
	
	@Cacheable(value=de.app.CacheConfig.CACHE_USERS)
	public ResponseEntity<User[]> find() {
		return super.find(null, null, null, null);
	}
	
	public ResponseEntity<User> findOne( Long userId ) {
		return super.findOne(null,null, userId, null);
	}
}
