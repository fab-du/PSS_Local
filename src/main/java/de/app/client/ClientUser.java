package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.app.CryptoneProperties;
import de.app.model.User;

@Service
public class ClientUser extends AbstractFindRequest<User> {
	CryptoneProperties env;
	
	public  AbstractWriteRequest<?, User> Writer;
	

	
	@Autowired
	public ClientUser(RestClient client,CryptoneProperties env ) {
		super(client, User.class, User[].class );
		this.env = env;
		this.setUri(env.getUsers());
		super.setUrl(env.getUrl());

		Writer = new AbstractWriteRequest<>(client, Object.class, User.class );
		Writer.setUri(env.getUsers());
		Writer.setUrl(env.getUrl());
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
