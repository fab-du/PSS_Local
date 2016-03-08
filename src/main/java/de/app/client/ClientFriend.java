package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import de.app.model.User;

@Service
public class ClientFriend extends ClientUser{
	String uri="/api/{preffix1}/friends/{suffix1}/{suffix2}/{suffix3}";

	@Autowired
	public ClientFriend(RestClient client) {
		super(client);
		this.setUri(uri);
		this.Writer.setUri(uri);
	}
	
	@Cacheable(value=de.app.CacheConfig.CACHE_FRIENDS)
	public ResponseEntity<User[]> find( Long userId ) {
		return super.find(  userId, null, null, null );
	}
	
	public ResponseEntity<User> findOne( Long userId, Long friendId ) {
		return super.findOne(  userId, friendId, null, null );
	}
	
	public ResponseEntity<?> revoke ( Long userId, Long friendId ){
		return this.Writer.delete( userId, friendId, "revoke", null );
	}
	
	public ResponseEntity<?> addFriendToGroup ( Long userId, Long friendId, Long groupId ){
		return this.Writer.put( userId, friendId, "addToGroup", groupId );
	}
}
