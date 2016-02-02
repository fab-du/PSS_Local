package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.app.model.Document;

@Component
public class ClientDocument extends AbstractFindRequest<Document>{
	
	private String uri = "/api/{prefix}/documents/{suffix1}/{suffix2}";
	
	public final AbstractWriteRequest<?, Document> Writer;
	
	@Autowired
	public ClientDocument(RestClient client) {
		super(client, Document.class, Document[].class);
		this.setUri( uri );
		Writer = new AbstractWriteRequest<>(client, Object.class, Document.class );
		Writer.setUri(uri);
	}
}
