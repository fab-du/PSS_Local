package de.app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.http.multipart.MultipartFileReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.springframework.web.multipart.MultipartFile;

import de.app.CacheConfig;
import de.app.client.ClientKeyPair;
import de.app.client.ClientSymKey;
import de.app.client.RestClient;
import de.app.model.Document;
import de.app.model.KeyPair;
import de.app.model.KeySym;
import de.app.model.form.FormLoginAuthenticateResponse;
import de.crypto.AESCrypto;
import de.crypto.RSACrypto;

@Service
public class ServiceDocument {
	
	@Autowired
	RestClient client;
	@Autowired
	ClientSymKey clientKeySym;
	@Autowired 
	CacheManager cacheManager;
	@Autowired
	ClientKeyPair clientKeypair;

	public
	ResponseEntity<?> create( MultipartFile file, String url, Long groupId ) throws Exception{
		if ( file.isEmpty() )
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		InputStream is = file.getInputStream();
		
		FileOutputStream fos = new FileOutputStream(new File(file.getOriginalFilename()));
			int read = 0;
			final byte[] bytes = new byte[1024];
			
			AESCrypto aesCrypto = new AESCrypto();
			KeySym aesKey = clientKeySym.findOne( groupId ).getBody();
			
			
			while((read=is.read(bytes)) != -1){
				fos.write(bytes, 0, read);
			}
			
			is.close();
			fos.close();
			File _file = new File( file.getOriginalFilename() );
			RSACrypto rsa = new RSACrypto();
			
			KeyPair sessionKey = cacheManager.getCache( CacheConfig.CACHE_SESSION ).get("pubkey", KeyPair.class );
			String enc_passphrase  = cacheManager.getCache( CacheConfig.CACHE_SESSION ).get("passphrase", String.class );
			String dec_passphrase = rsa.decrypt( sessionKey.getPrikey(), enc_passphrase);

			FormLoginAuthenticateResponse res =
					cacheManager.getCache(CacheConfig.CACHE_SESSION).get("currentUser", FormLoginAuthenticateResponse.class);
			
			KeyPair keypair = clientKeypair.findOne( res.getCurrentUserId() ).getBody();
			
			String _symkey = rsa.decrypt(keypair, dec_passphrase, aesKey.getSymkey());
			
			aesKey.setSymkey( _symkey );
			aesCrypto.encrypt(aesKey.getSymkey(), _file);
			
			FileUtils.copyFile(new File( _file.getName() + ".enc"), new File( _file.getName()));
			client.setHeader("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
			 File __file = new File(_file.getName());
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			
			//map.add("file", new FileSystemResource( __file ));
			Resource resource = new FileSystemResource( __file.getAbsolutePath());
			map.add("file", resource);
			System.out.println("commeee ehrhekhrleoieorhh");
			ResponseEntity<?> response = client.getRestTemplate().exchange( url, HttpMethod.POST, new HttpEntity<>( map, client.getHeaders()), Document.class);
			FileUtils.forceDelete(__file);
			return response;
	}
	

}
