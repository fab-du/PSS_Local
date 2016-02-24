package de.app.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.app.client.ClientDocument;
import de.app.client.RestClient;
import de.app.model.Document;
import de.app.service.ServiceDocument;


@RestController
@RequestMapping(value="/api/documents")
public class ControllerDocument {
	
	@Autowired
	ClientDocument clientDocument;
	
	@Autowired
	ServiceDocument serviceDocument;
	
	@Autowired
	RestClient rest;
	
	
	@RequestMapping( method=RequestMethod.GET  )
	public ResponseEntity<Document[]> find(){
		return clientDocument.find();
	}
	
	@RequestMapping(value="/{documentId}", method=RequestMethod.GET  )
	public ResponseEntity<Document> findOne( @PathVariable(value="documentId") Long documentId ){
		return clientDocument.findOne( documentId );
	}
	
	@RequestMapping( method=RequestMethod.POST )
	public ResponseEntity<?> create(@RequestParam("file") MultipartFile file) throws IOException{
		String url = "http://localhost:8080/api/documents"; 
		return serviceDocument.create(file, url);
	}

	@RequestMapping(value="/{documentId}/changeOwner", method=RequestMethod.POST  )
	public ResponseEntity<?> documentId_changeOwner( @PathVariable(value="documentId") Long documentId ){
		return null;
	}

	@RequestMapping(value="/{documentId}/shareDocument", method=RequestMethod.POST  )
	public ResponseEntity<?> documentId_shareDocument( @PathVariable(value="documentId") Long documentId ){
		return null;
	}

	@ExceptionHandler({Exception.class})
	public void exceptionHandler( HttpServletRequest request, Exception exception){
		System.out.println( exception.getMessage());
	}
}
