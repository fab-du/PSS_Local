package de.app.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import de.app.client.ClientGroup;
import de.app.controller.ControllerUser;
import de.app.model.Group;
import de.app.model.KeySym;
import de.crypto.AESCrypto;
import de.crypto.RSACrypto;

@Component
public class ServiceGroup {
	
	@Autowired
	ClientGroup clientGroup;
	@Autowired
	ControllerUser ctrlUser;
	@Autowired
	ServiceDocument serviceDocument;
	@Autowired
	CacheManager cacheManager;

	public Group create( Group group, String pubkey ) throws Exception{
		
		AESCrypto aesCrypto = new AESCrypto();
		KeySym groupKey = aesCrypto.generateKey();

		RSACrypto rsa = new RSACrypto();
		String encSymKey = rsa.encrypt( pubkey, groupKey.getSymkey() );
		groupKey.setSymkey(encSymKey);
		group.setGroupkey(groupKey);
		return group; 
	}

	public void uploadFile(MultipartFile file) throws IOException{
	}
	
	public void shareDocument(){
	}
	
	public void addUser(){
	}
}
