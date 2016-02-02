package de.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import de.app.client.ClientDocument;
import de.app.client.ClientGroup;
import de.app.client.ClientUser;
import de.app.client.RestClient;
import de.app.model.*;
import de.app.service.ServiceDocument;

@RestController
@RequestMapping(value="/api/groups")
public class ControllerGroup {
	
	@Autowired
	RestClient rest;
	
	@Autowired
	ClientGroup clientGroup;
	
	@Autowired
	ClientDocument clientDocument;

	@Autowired 
	ClientUser clientUser;
	
	@Autowired
	ServiceDocument serviceDocument;
	
	@RequestMapping( method = RequestMethod.GET )
	public ResponseEntity<Group[]>  find(){
		return clientGroup.find(null, null, null, null);
	}
	
	@RequestMapping(value="/{groupId}",  method = RequestMethod.GET )
	public ResponseEntity<Group>  findOne(@PathVariable(value="groupId") Long groupId){
		return clientGroup.findOne(groupId, null, null, null);
	}

	@RequestMapping( method = RequestMethod.POST )
	public ResponseEntity<?> create( @RequestBody Group group ) throws RestClientException, Exception{
		return clientGroup.Writer.create(group, null, null, null, null);
	}

	@RequestMapping( value="/{groupId}/users", method = RequestMethod.GET )
	public ResponseEntity<User[]> users( @PathVariable(value="groupId") Long groupId ){
		clientUser.setUri( "/api/groups/" + groupId + "/" + "users");
		return clientUser.find(groupId, "users", null, null);
	}
	
	@RequestMapping( value="/{groupId}/users", method = RequestMethod.POST )
	public ResponseEntity<?> addUser( @PathVariable(value="groupId") Long groupId, @RequestBody User user ){
		return clientUser.Writer.create(user, groupId, null, null, null);
	}
	
	@RequestMapping( value="/users/{userId}", method= RequestMethod.GET )
	public ResponseEntity<Group[]>  find_where_user_group(@PathVariable(value="userId") Long userId ){
		return clientGroup.find("users", userId, null, null);
	}

	@RequestMapping( value="/{groupId}/documents", method = RequestMethod.GET)
	public ResponseEntity<Document[]> documents( @PathVariable(value="groupId") Long groupId){
		return clientDocument.find(groupId, "documents", null, null);
	}
	
	@RequestMapping( value="/{groupId}/documents", method = RequestMethod.POST)
	public ResponseEntity<?> addDocument( @PathVariable(value="groupId") Long groupId,@RequestParam("file") MultipartFile file ) throws IOException{
		 	serviceDocument.create(file);
		 	
			String url = "http://localhost:8080/api/groups/" + groupId + "/documents";
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			 File _file = new File(file.getOriginalFilename());
			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("file", new FileSystemResource(  _file.getAbsolutePath()));
			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new    HttpEntity<LinkedMultiValueMap<String, Object>>(map);
			ResponseEntity<Document> response = rest.getRestTemplate().exchange( url, HttpMethod.POST, requestEntity, Document.class);
	    	return response;
	}
	
	@RequestMapping( value="/{groupId}/documents/{documentId}", method = RequestMethod.GET )
	public ResponseEntity<?> groupId_documents_documentId( @PathVariable(value="groupId") Long groupId,
			@PathVariable(value="documentId") Long documentId){
		return clientDocument.findOne(groupId, "documents", documentId);
	}
	
	@RequestMapping( value="/{groupId}/documents/{documentId}/download", method = RequestMethod.GET )
	public ResponseEntity<InputStreamResource> groupId_documents_documentId_download( @PathVariable(value="groupId") Long groupId,
			@PathVariable(value="documentId") Long documentId, HttpServletResponse response) throws IOException{
		File file = new File("deployment.jpg");
//		InputStream is = new FileInputStream( file);
//		response.setHeader("Content-Type", "application/jpg");
//		response.setHeader("Content-Disposition", "attachment; filename='deployment.jpg'"); 
//		IOUtils.copy(is, response.getOutputStream());
//		response.flushBuffer();
		
		  HttpHeaders respHeaders = new HttpHeaders();
		   // respHeaders.setContentType("application/jpg");
		    respHeaders.setContentLength(12345678);
		    respHeaders.setContentDispositionFormData("attachment", "deployment.jpg");
		 InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
		    return new ResponseEntity<InputStreamResource>(isr,respHeaders, HttpStatus.OK);
		
	}
	

	//TODO
	@RequestMapping( value="/{groupId}/documents/{documentId}/shareDocument", method = RequestMethod.POST )
	public ResponseEntity<?> groupId_documents_documentId_shareDocument( @PathVariable(value="groupId") Long groupId,
			@PathVariable(value="documentId") Long documentId){
		return null;
	}

}
