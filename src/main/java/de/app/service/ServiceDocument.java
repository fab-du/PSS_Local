package de.app.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;

import de.app.RestRequest;
import de.app.model.KeyPair;
import de.cryptone.crypto.CryptFactor;
import de.cryptone.crypto.RSAPBECrypto;

public class ServiceDocument {

//	@Autowired
//	RestRequest rest; 
//	private final static String URL = "http://localhost:8080";
//
//
//	public String getPublicKey( Long currentUserId ) {
//	     String url =  URL + "/api/keypair/" + currentUserId; 
//
//     	 String pubkey;
//		try {
//			pubkey = rest.getObject()
//			.getForEntity(url, String.class ).getBody();
//
//			if( pubkey != null ) return pubkey;
//     	 					  return null;
//		} catch (Exception e) {
//			return null;
//		}
//	}

	private String decrypt_symkey( String enc_symkey, String passphrase, String enc_prikey, String prikey_salt ){
		RSAPBECrypto rsa = ( RSAPBECrypto ) CryptFactor.getInstance(CryptFactor.CRYPT_ASYM_RSA_PBE);
		String ret = rsa.decrypt(enc_prikey, passphrase, prikey_salt, enc_symkey);
		return ret;
	}

	public void addDocument( String path, String enc_symkey, String enc_prikey, String passphrase, KeyPair keypair   ){
		String symkey = this.decrypt_symkey(enc_symkey, passphrase, enc_prikey, keypair.getSalt() );
		this.encrypt(symkey, new File(path), new File(path + ".enc"));
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
             
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
             
            byte[] outputBytes = cipher.doFinal(inputBytes);
             
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
             
            inputStream.close();
            outputStream.close();
             
        } catch ( Exception ex) {
          return; 
        }	
	}

}
