package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.app.model.Document;

@Service
public class ClientDocument extends AbstractFindRequest<Document>{
	
	private String uri = "/api/groups/{preffix}/documents/{suffix2}";
	
	public final AbstractWriteRequest<?, Document> Writer;
	
	@Autowired
	public ClientDocument(RestClient client) {
		super(client, Document.class, Document[].class);
		this.setUri( uri );
		Writer = new AbstractWriteRequest<>(client, Object.class, Document.class );
		Writer.setUri(uri);
	}
	
	@Cacheable(value=de.app.CacheConfig.CACHE_DOCUMENTS)
	public ResponseEntity<Document[]> find() {
		return super.find();
	}

	public ResponseEntity<Document> findOne( Long documentId ) {
		return super.findOne( documentId );
	}
}
