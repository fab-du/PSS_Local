package de.app.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.app.model.Document;

@Component
public class ClientDocument extends AbstractFindRequest<Document>{
	
	public final static String URI = "/api/documents";
	
	public final AbstractWriteRequest<?, Document> Writer;
	
	@Autowired
	public ClientDocument(RestClient client) {
		super(client, Document.class, Document[].class);
		this.setUri( URI );
		Writer = new AbstractWriteRequest<>(client, Object.class, Document.class );
	}
	
	public AbstractWriteRequest<?, Document> getWriter(){
		return this.Writer;
	}
	
}
