package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.app.CryptoneProperties;
import de.app.model.Document;

@Service
public class ClientDocument extends AbstractFindRequest<Document>{
	
	CryptoneProperties env;
	public final AbstractWriteRequest<?, Document> Writer;
	
	@Autowired
	public ClientDocument(RestClient client, CryptoneProperties env) {
		super(client, Document.class, Document[].class);
		this.env = env;
		this.setUri(env.getDocuments());
		this.setUrl(env.getUrl());
		Writer = new AbstractWriteRequest<>(client, Object.class, Document.class );
		Writer.setUri(env.getDocuments());
		Writer.setUrl(env.getUrl());
	}

	public ResponseEntity<Document[]> find(Long groupId) {
		return super.find( groupId, null );
	}

	public ResponseEntity<Document> findOne( Long groupId, Long documentId ) {
		return super.findOne( groupId, documentId );
	}
}
