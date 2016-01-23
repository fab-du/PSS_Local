package de.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.google.gson.Gson;

import de.app.RestRequest;
import de.app.client.ClientGroup;
import de.app.model.*;
import de.app.service.ServiceGroup;

@RestController
@RequestMapping(value="/api/groups")
public class ControllerGroup {

	@Inject
	RestRequest request;
	private final static String URL = "http://localhost:8080";
	
	@Autowired
	ClientGroup clientGroup;

	@RequestMapping( method = RequestMethod.GET )
	public ResponseEntity<Group[]>  find(){
		return clientGroup.find();
	}
	
	@RequestMapping(value="/{groupId}",  method = RequestMethod.GET )
	public ResponseEntity<Group>  findOne(@PathVariable(value="groupId") Long groupId){
		return clientGroup.findOne(groupId);
	}

	@RequestMapping( method = RequestMethod.POST )
	public ResponseEntity<Map<String, String>> post( @RequestBody Map<String, String> misc ) throws RestClientException, Exception{
     Long foo = new Long(  misc.get("currentUserId")) ;
     String url =  URL + "/api/keypair/" + misc.get("currentUserId");

     	 String pubkey = 
				request.getObject()
				.getForEntity(url, String.class ).getBody();

     	 String groupname = misc.get("name");
     	 String currentUserId = misc.get("currentUserId");

     	 ServiceGroup servicegroup = new ServiceGroup();
     	 Map<String,String> result = servicegroup.createGroup(groupname, pubkey, currentUserId );
     	
     	 System.out.println( result.values().toString());
     	 	
     	 request.getObject().postForEntity(URL + "/api/groups", result, ResponseEntity.class);

		return new ResponseEntity<Map<String,String>>( misc , HttpStatus.CREATED);
	}


	@RequestMapping( value="/{groupId}/documents", method = RequestMethod.GET )
	public ResponseEntity<?> groupId_documents( @PathVariable(value="groupId") Long groupId ){
		return null;
		
	}

	@RequestMapping( value="/{groupId}/documents/addDocument", method = RequestMethod.POST )
	public ResponseEntity<?> groupId_documents_addDocument( @PathVariable(value="groupId") Long groupId ){
		return null;
		
	}


	@RequestMapping( value="/{groupId}/documents/{documentId}", method = RequestMethod.GET )
	public ResponseEntity<?> groupId_documents_documentId( @PathVariable(value="groupId") Long groupId,
			@PathVariable(value="documentId") Long documentId){
		return null;
	}

	@RequestMapping( value="/{groupId}/documents/{documentId}/changeOwner", method = RequestMethod.POST )
	public ResponseEntity<?> groupId_documents_documentId_changeOwner( @PathVariable(value="groupId") Long groupId,
			@PathVariable(value="documentId") Long documentId){
		return null;
	}

	@RequestMapping( value="/{groupId}/documents/{documentId}/shareDocument", method = RequestMethod.POST )
	public ResponseEntity<?> groupId_documents_documentId_shareDocument( @PathVariable(value="groupId") Long groupId,
			@PathVariable(value="documentId") Long documentId){
		return null;
	}

	@RequestMapping(value="/{groupId}/users", method=RequestMethod.GET )
	public ResponseEntity<?> goupId_users(@PathVariable(value="groupId") Long groupId ) throws RestClientException, Exception{
		String url = URL + "/api/groups/" + groupId;
		return request.getObject().getForEntity(url, Map.class);
	}

	@RequestMapping(value="/{groupId}/users/{userId}", method=RequestMethod.GET )
	public ResponseEntity<?> goupId_users_userId(@PathVariable(value="groupId") Long groupId,
			@PathVariable(value="userId") Long userId ) throws RestClientException, Exception{
		String url = URL + "/api/groups/" + groupId + "/users/" + userId;
		return request.getObject().getForEntity(url, Map.class);
	}

	@RequestMapping(value="/{groupId}/users/{userId}/validate", method=RequestMethod.POST )
	public ResponseEntity<?> goupId_users_userId_validate(@PathVariable(value="groupId") Long groupId,
			@PathVariable(value="userId") Long userId ){
		return null;
	}

	@RequestMapping(value="/{groupId}/users/{userId}/addFriend", method=RequestMethod.POST )
	public ResponseEntity<?> goupId_users_userId_addFriend(@PathVariable(value="groupId") Long groupId,
			@PathVariable(value="userId") Long userId ){
		return null;
	}

	@RequestMapping(value="/{groupId}/users/{userId}/revoke", method=RequestMethod.POST )
	public ResponseEntity<?> goupId_users_userId_revoke(@PathVariable(value="groupId") Long groupId,
			@PathVariable(value="userId") Long userId ){
		return null;
	}

}
