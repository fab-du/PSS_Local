package de.app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import de.app.client.RestClient;
import de.app.model.Document;
import de.app.model.KeySym;
import de.crypto.AESCrypto;

@Component
public class ServiceDocument {
	
	@Autowired
	RestClient rest;

	public
	ResponseEntity<?> create( MultipartFile file, String url ) throws IOException{
		if ( file.isEmpty() )
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		InputStream is = file.getInputStream();
		
		FileOutputStream fos = new FileOutputStream(new File(file.getOriginalFilename()));
			int read = 0;
			final byte[] bytes = new byte[1024];
			
			AESCrypto aesCrypto = new AESCrypto();
			KeySym aesKey = aesCrypto.generateKey();
			
			while((read=is.read(bytes)) != -1){
				fos.write(bytes, 0, read);
			}
			
			is.close();
			fos.close();
			File _file = new File( file.getOriginalFilename() );
			aesCrypto.encrypt(aesKey.getSymkey(), _file);
			FileUtils.forceDelete( new File( file.getName()));
			FileUtils.copyFile(new File( file.getName() + ".enc"), new File( file.getName()));
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			 File __file = new File(file.getOriginalFilename());
			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("file", new FileSystemResource(  __file.getAbsolutePath()));
			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new    HttpEntity<LinkedMultiValueMap<String, Object>>(map);
			ResponseEntity<Document> response = rest.getRestTemplate().exchange( url, HttpMethod.POST, requestEntity, Document.class);
			return response;
	}
	

}
