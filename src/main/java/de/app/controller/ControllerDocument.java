package de.app.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import de.app.Get;
import de.app.RestRequest;
import de.app.client.ClientDocument;
import de.app.model.Document;


@RestController
@RequestMapping(value="/api/documents")
public class ControllerDocument {
	
	@Autowired
	ClientDocument clientDocument;
	
	
	@RequestMapping( method=RequestMethod.GET  )
	public ResponseEntity<Document[]> find(){
		return clientDocument.find();
	}
	
	@RequestMapping(value="/{documentId}", method=RequestMethod.GET  )
	public ResponseEntity<Document> findOne( @PathVariable(value="documentId") Long documentId ){
		return clientDocument.findOne( documentId );
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
