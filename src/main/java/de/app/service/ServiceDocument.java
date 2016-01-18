package de.app.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import de.app.model.KeyPair;

public class ServiceDocument {


	private String decrypt_symkey( String enc_symkey, String passphrase, String enc_prikey, String prikey_salt ){
		return null;
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
