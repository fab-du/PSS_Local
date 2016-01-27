package de.app.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sun.mail.iap.Response;

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
		 serviceDocument.create(file);
		
		
		String url = "http://localhost:8080/api/documents";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		 File _file = new File(file.getOriginalFilename());
		 
		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		
		
		map.add("file", new FileSystemResource(  _file.getAbsolutePath()));
		
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new    HttpEntity<LinkedMultiValueMap<String, Object>>(map);

		
		ResponseEntity<?> response = rest.getRestTemplate().exchange( url, HttpMethod.POST, requestEntity,
                ResponseEntity.class);
    	return response;
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
