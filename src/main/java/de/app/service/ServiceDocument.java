package de.app.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import de.app.RestRequest;
import de.app.model.KeyPair;

public class ServiceDocument {
	
	
	@Autowired
	RestRequest rest;


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

	public void changeOwner( Long currentOwnerId, Long newOwnerId, Long documentId ){
		
	}

	public void shareDocument( Long documentId, Long newOwnerId ){
		
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
	
	private String writeFile( MultipartFile file ){
	    String name = file.getOriginalFilename();
	    System.out.println("come here");
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }	
	}
	

	public void fetchDocument( InputStream in , MultipartHttpServletRequest mRequest ) throws RestClientException, Exception{
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
		    HttpEntity<String> entity = new HttpEntity<String>(headers);

		    ResponseEntity<byte[]> response = rest.getObject().exchange(
		            "https://www.google.com/images/srpr/logo11w.png",
		            HttpMethod.GET, entity, byte[].class, "1");

		    if (response.getStatusCode() == HttpStatus.OK) {
		        Files.write(Paths.get("google.png"), response.getBody());
		    }
	
	}

}
