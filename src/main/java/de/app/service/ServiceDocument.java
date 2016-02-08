package de.app.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import de.app.model.Group;
import de.app.model.KeyPair;
import de.app.model.KeySym;
import de.crypto.AESCrypto;

@Component
public class ServiceDocument {

	public
	ResponseEntity<?> create( MultipartFile file ) throws IOException{
		if ( file.isEmpty() )
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		InputStream is = file.getInputStream();
		
		FileOutputStream fos = new FileOutputStream(new File(file.getOriginalFilename()));
			int read = 0;
			final byte[] bytes = new byte[1024];
			byte[] encryptedBytes = new byte[1024];
			
			AESCrypto aesCrypto = new AESCrypto();
			KeySym aesKey = aesCrypto.generateKey();
			
			while((read=is.read(bytes)) != -1){
				encryptedBytes = aesCrypto.encrypt(aesKey.getSymkey(), bytes);
				fos.write(encryptedBytes, 0, read);
			}
			
			is.close();
			fos.close();
			return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	public
	ResponseEntity<?> create( MultipartFile file, Group group ) throws IOException{
		if ( file.isEmpty() )
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		InputStream is = file.getInputStream();
		
		FileOutputStream fos = new FileOutputStream(new File(file.getOriginalFilename()));
			int read = 0;
			final byte[] bytes = new byte[1024];
		
			while((read=is.read(bytes)) != -1){
				fos.write(bytes, 0, read);
			}
			
			is.close();
			fos.close();
			return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	private String decrypt_symkey( String enc_symkey, String passphrase, String enc_prikey, String prikey_salt ){
		return null;
	}

	public void addDocument( String path, String enc_symkey, String enc_prikey, String passphrase, KeyPair keypair   ){
		String symkey = this.decrypt_symkey(enc_symkey, passphrase, enc_prikey, keypair.getSalt() );
		this.encrypt(symkey, new File(path), new File(path + ".enc"));
	}
	
	public void addDocument( MultipartFile file) throws IOException{
		  MultiValueMap<String, Object> parts = 
		          new LinkedMultiValueMap<String, Object>();
		  parts.add("file", new ByteArrayResource(file.getBytes()));
		  parts.add("filename", file.getOriginalFilename());
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		  
		  HttpEntity<MultiValueMap<String, Object>> requestEntity =
		          new HttpEntity<MultiValueMap<String, Object>>(parts, headers);

	}

	public void encrypt( String key, File inputfile, File outputfile ){
		this.doCrypto(Cipher.ENCRYPT_MODE, key, inputfile, outputfile);
	}

	public void decrypt( String key, File inputfile, File outputfile ){
		this.doCrypto(Cipher.DECRYPT_MODE, key, inputfile, outputfile);
	}

	private  void doCrypto( int cipherMode, String key, File inputFile, File outputFile ){
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, secretKey);
             
            FileInputStream fis = new FileInputStream(inputFile);
            FileOutputStream fos = new FileOutputStream(outputFile);

            byte[] chunk = new byte[64];
            
            int readed;
            while( ( readed = fis.read(chunk)) != -1 ){
            	byte[] cipherBytes = cipher.update(chunk, 0, readed);
            	fos.write(cipherBytes, 0, readed);
            }
            
            fis.close();
            fos.close();
             
        } catch ( Exception ex) {
          return; 
        }	
	}

}
