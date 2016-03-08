package de.app.service;

import java.io.File;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import de.app.CacheConfig;
import de.app.client.ClientGroup;
import de.app.client.ClientKeyPair;
import de.app.client.ClientSymKey;
import de.app.controller.ControllerUser;
import de.app.model.Group;
import de.app.model.KeyPair;
import de.app.model.KeySym;
import de.app.model.form.FormLoginAuthenticateResponse;
import de.crypto.AESCrypto;
import de.crypto.RSACrypto;
import de.app.client.RestClient;


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
	@Autowired
	RestClient rest;
	@Autowired
	ClientSymKey clientKeySym;
	@Autowired
	ClientKeyPair clientKeyPair;
	
	public Group create( Group group, String pubkey ) throws Exception{
		
		AESCrypto aesCrypto = new AESCrypto();
		KeySym groupKey = aesCrypto.generateKey();

		RSACrypto rsa = new RSACrypto();
		String encSymKey = rsa.encrypt( pubkey, groupKey.getSymkey() );
		groupKey.setSymkey(encSymKey);
		group.setGroupkey(groupKey);
		return group; 
	}
	
	public ResponseEntity<byte[]> download( Long groupId, Long documentId, String file ) throws Exception{
		String url = "http://localhost:8080/api/groups/" + groupId +  "/documents/" + documentId + "/download/" + file;
		rest.setHeader("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
		HttpEntity<String> entity = new HttpEntity<String>(rest.getHeaders());
		
		ResponseEntity<byte[]> res = rest.getRestTemplate().exchange(url,HttpMethod.GET, entity, byte[].class, "1");
		if ( res.getStatusCode() == HttpStatus.OK ){
			String headercontent = res.getHeaders().get( HttpHeaders.CONTENT_DISPOSITION ).toString();
			String[] result = headercontent.split("=");
			String _filename = result[ result.length -1 ];
			String filename = _filename.substring(1, _filename.length() - 2 ).trim();
					
			java.nio.file.Files.write( Paths.get( filename + ".enc"), res.getBody() );
			
			String enc_passphrase = cacheManager.getCache( CacheConfig.CACHE_SESSION).get("passphrase", String.class);
			FormLoginAuthenticateResponse currentUser = cacheManager.getCache(CacheConfig.CACHE_SESSION).get("currentUser", FormLoginAuthenticateResponse.class);
			KeyPair sessionKey = cacheManager.getCache( CacheConfig.CACHE_SESSION).get("pubkey", KeyPair.class);

			RSACrypto rsa = new RSACrypto();
			String dec_passphrase = rsa.decrypt( sessionKey.getPrikey(), enc_passphrase);
			
			KeyPair userKeyPair = clientKeyPair.findOne(currentUser.getCurrentUserId()).getBody();
			KeySym groupkeysym = clientKeySym.findGroupKeySym(groupId);
			String dec_groupkeysym = rsa.decrypt(userKeyPair, dec_passphrase, groupkeysym.getSymkey());
			AESCrypto aes = new AESCrypto();
			aes.decrypt(dec_groupkeysym, new File( filename + ".enc") );
		}
		return res;
	}
}
