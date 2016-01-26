package de.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import de.app.client.ClientGroup;
import de.app.client.ClientUser;
import de.app.model.*;

@RestController
@RequestMapping(value="/api/groups")
public class ControllerGroup {

	
	@Autowired
	ClientGroup clientGroup;

	@Autowired 
	ClientUser clientUser;
	
	@RequestMapping( method = RequestMethod.GET )
	public ResponseEntity<Group[]>  find(){
		return clientGroup.find();
	}
	
	@RequestMapping(value="/{groupId}",  method = RequestMethod.GET )
	public ResponseEntity<Group>  findOne(@PathVariable(value="groupId") Long groupId){
		return clientGroup.findOne(groupId);
	}

	@RequestMapping( method = RequestMethod.POST )
	public ResponseEntity<?> create( @RequestBody Group group ) throws RestClientException, Exception{
		System.out.println( group.toString());
		return clientGroup.Writer.create(group);
	}

	
	@RequestMapping( value="/{groupId}/users", method = RequestMethod.GET )
	public ResponseEntity<User[]> users( @PathVariable(value="groupId") Long groupId ){
		clientUser.setUri( "/api/groups/" + groupId + "/" + "users");
		return clientUser.find(groupId);
	}
	
	@RequestMapping( value="/{groupId}/users", method = RequestMethod.POST )
	public ResponseEntity<?> addUser( @PathVariable(value="groupId") Long groupId, @RequestBody User user ){
		System.out.println( user.getId());
		clientUser.Writer.setUri( "/api/groups/" + groupId + "/" + "users");
		return clientUser.Writer.create(user, groupId);
	}

	@RequestMapping( value="/{groupId}/documents", method = RequestMethod.GET)
	public ResponseEntity<Document[]> documents( @PathVariable(value="groupId") Long groupId){
		return null;
	}
	
	@RequestMapping( value="/{groupId}/documents", method = RequestMethod.POST)
	public ResponseEntity<?> addDocument( @PathVariable(value="groupId") Long groupId, @RequestBody Document document){
		return null;
	}
	
//	@RequestMapping( value="/{groupId}/documents", method = RequestMethod.GET )
//	public ResponseEntity<?> groupId_documents( @PathVariable(value="groupId") Long groupId ){
//		return null;
//	}
//
//	@RequestMapping( value="/{groupId}/documents/addDocument", method = RequestMethod.POST )
//	public ResponseEntity<?> groupId_documents_addDocument( @PathVariable(value="groupId") Long groupId ){
//		return null;
//		
//	}
//
//
//	@RequestMapping( value="/{groupId}/documents/{documentId}", method = RequestMethod.GET )
//	public ResponseEntity<?> groupId_documents_documentId( @PathVariable(value="groupId") Long groupId,
//			@PathVariable(value="documentId") Long documentId){
//		return null;
//	}
//
//	@RequestMapping( value="/{groupId}/documents/{documentId}/changeOwner", method = RequestMethod.POST )
//	public ResponseEntity<?> groupId_documents_documentId_changeOwner( @PathVariable(value="groupId") Long groupId,
//			@PathVariable(value="documentId") Long documentId){
//		return null;
//	}
//
//	@RequestMapping( value="/{groupId}/documents/{documentId}/shareDocument", method = RequestMethod.POST )
//	public ResponseEntity<?> groupId_documents_documentId_shareDocument( @PathVariable(value="groupId") Long groupId,
//			@PathVariable(value="documentId") Long documentId){
//		return null;
//	}

	
//	@RequestMapping(value="/{groupId}/users", method=RequestMethod.GET )
//	public ResponseEntity<?> goupId_users(@PathVariable(value="groupId") Long groupId ) throws RestClientException, Exception{
//		String url = URL + "/api/groups/" + groupId;
//		return request.getObject().getForEntity(url, Map.class);
//	}

}
