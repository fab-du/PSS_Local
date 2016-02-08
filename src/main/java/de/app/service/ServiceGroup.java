package de.app.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.app.client.ClientGroup;
import de.app.model.Group;
import de.app.model.KeySym;
import de.crypto.AESCrypto;

@Service
public class ServiceGroup {
	
	@Autowired
	ClientGroup clientGroup;
	
	@Autowired
	ServiceDocument serviceDocument;

	public ResponseEntity<?> create( Group group ){
		
		if( group == null )
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		
		AESCrypto aesCrypto = new AESCrypto();
		KeySym groupKey = aesCrypto.generateKey();
		group.setGroupkey(groupKey);
		return clientGroup.Writer.create(group, null, null, null, null);
	}

	public void uploadFile(MultipartFile file) throws IOException{
	}
	
	public void shareDocument(){
	}
	
	public void addUser(){
	}


}
