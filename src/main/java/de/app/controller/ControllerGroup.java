package de.app.controller;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
import de.app.service.ServiceGroup;

@RestController
@RequestMapping(value="/api/groups")
public class ControllerGroup extends AbstractController {
	
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
	@Autowired
	ServiceGroup serviceGroup;
	
	@RequestMapping( method = RequestMethod.GET )
	public ResponseEntity<Group[]>  find(){
		return clientGroup.find();
	}
	
	@RequestMapping(value="/{groupId}",  method = RequestMethod.GET )
	public ResponseEntity<Group>  findOne(@PathVariable(value="groupId") Long groupId){
		return clientGroup.findOne(groupId);
	}

	@RequestMapping( method = RequestMethod.POST )
	public ResponseEntity<?> create( @RequestBody Group group, @RequestHeader(value="clientpubkey") String pubkey ) throws RestClientException, Exception{
		Group _group = serviceGroup.create( group, pubkey );
		return clientGroup.getWriter().create(_group, null, null, null, null );
	}

	@RequestMapping( value="/{groupId}/users", method = RequestMethod.GET )
	public ResponseEntity<User[]> users( @PathVariable(value="groupId") Long groupId ){
		return clientUser.find("groups", groupId, null, null);
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
		return clientDocument.find(groupId, null);
	}
	
	@RequestMapping( value="/{groupId}/documents", method = RequestMethod.POST)
	public ResponseEntity<?> addDocument( @PathVariable(value="groupId") Long groupId,@RequestParam("file") MultipartFile file ) throws IOException{
			String url = "http://localhost:8080/api/groups/" + groupId + "/documents";
			return serviceDocument.create( file, url, groupId );
	}
	
	@RequestMapping( value="/{groupId}/documents/{documentId}", method = RequestMethod.GET )
	public ResponseEntity<?> groupId_documents_documentId( @PathVariable(value="groupId") Long groupId,
			@PathVariable(value="documentId") Long documentId){
		return clientDocument.findOne(groupId, "documents", documentId);
	}
	
	@RequestMapping( value="/{groupId}/documents/{documentId}/download/{file}", method = RequestMethod.GET )
	public ResponseEntity<?> groupId_documents_documentId_download( @PathVariable(value="groupId") Long groupId,
			@PathVariable(value="documentId") Long documentId, HttpServletResponse response) throws IOException{
			
			String url = "http://localhost:8080/api/groups/10/documents/1/download";
			  HttpHeaders headers = new HttpHeaders();
			    headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
			    
			    HttpEntity<String> entity = new HttpEntity<String>(headers);
			    
			    ResponseEntity<byte[]> res = 
			    		rest.getRestTemplate().exchange(url,HttpMethod.GET, entity, byte[].class, "1");

			    return ResponseEntity
						.ok()
						.headers( res.getHeaders() )
						.contentLength(res.getBody().length)
						.body( res.getBody());
	}
	
	//TODO
	@RequestMapping( value="/{groupId}/documents/{documentId}/shareDocument", method = RequestMethod.POST )
	public ResponseEntity<?> groupId_documents_documentId_shareDocument( @PathVariable(value="groupId") Long groupId,
			@PathVariable(value="documentId") Long documentId){
		return null;
	}
}
