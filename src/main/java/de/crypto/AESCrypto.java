package de.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import de.app.model.KeySym;
import de.cryptone.utils.Helper;

public class AESCrypto {

    public final static int SALT_LENGHT = 32;  

    public final static int KEY_LENGHT = 128;

    public final static String SYM_KEY_ALGO  = "AES";

    public final static String SYM_CIPHER_ALGO = "AES/ECB/PKCS7Padding"; 

    public final static String EXC_MESS_NULL = "no arguments provided:";
    
    Integer key_length;
    String sym_key_algo;
    String sym_cipher_algo;
    
	public AESCrypto(int key_length, String sym_key_algo, String sym_cipher_algo) {
		super();
		this.key_length = key_length;
		this.sym_key_algo = sym_key_algo;
		this.sym_cipher_algo = sym_cipher_algo;
	}

	public AESCrypto(){
		super();
		new AESCrypto( KEY_LENGHT, SYM_KEY_ALGO, SYM_CIPHER_ALGO );
		this.key_length = (this.key_length != null ) ? this.key_length : KEY_LENGHT;
		this.sym_key_algo = ( this.sym_key_algo != null ) ? this.sym_key_algo : SYM_KEY_ALGO;
		this.sym_cipher_algo = ( this.sym_cipher_algo != null ) ? this.sym_cipher_algo : SYM_CIPHER_ALGO;
	}

	public KeySym generateKey() throws NoSuchAlgorithmException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        KeyGenerator keygen = null;
        KeySym result = new KeySym();
        byte[] salt = new byte[ SALT_LENGHT ];
        new SecureRandom();
        final SecureRandom r =  SecureRandom.getInstanceStrong();
        r.nextBytes(salt);
        keygen = KeyGenerator.getInstance( this.sym_key_algo );
        keygen.init( this.key_length , r );
        Key k = keygen.generateKey();
        result.setSymkey( Helper.encode( k.getEncoded()));
        result.setSalt( Helper.encode(salt));
        return result;
	}
	
	Key symkeyFromString(String key) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		byte[] bytes = key.getBytes();
		byte[] keybytes = Base64.getDecoder().decode(bytes);
		Key result = new SecretKeySpec( keybytes, 0, keybytes.length, this.sym_key_algo );
		return result;
	}

	public  String decrypt( final String secretkey, final String message ) throws Exception {
		Security.addProvider( new org.bouncycastle.jce.provider.BouncyCastleProvider());
		byte[] stringBytes = null;

         if ( secretkey == null || message == null ) 
             throw new Exception( EXC_MESS_NULL + "public  String decrypt( final String secretkey, final String message )" );

		 Key key = this.symkeyFromString(secretkey);
         Cipher cipher = Cipher.getInstance( this.sym_cipher_algo , "BC");
         cipher.init(Cipher.DECRYPT_MODE, key);
         byte[] raw = Base64.getDecoder().decode(message);
         stringBytes = cipher.doFinal(raw);
         String clearText= new String(stringBytes, "UTF8");
		 return clearText;
	}

	public String encrypt(final String secretkey, final String message) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        if ( secretkey == null || message == null )
             throw new Exception( EXC_MESS_NULL + "public  String encrypt( final String secretkey, final String message )" );

		Key key = this.symkeyFromString(secretkey);

        Cipher cipher = Cipher.getInstance(SYM_CIPHER_ALGO, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] stringBytes = message.getBytes();
        byte[] raw = cipher.doFinal(stringBytes);

		if (raw == null) 
            throw new Exception("Encryption goes wrong");

		return Base64.getEncoder().encodeToString(raw);
	}
	
	private void chifferImp( final String secretkey, File file, int modus) throws Exception{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
        if ( secretkey == null || file == null  )
            throw new Exception( EXC_MESS_NULL + "private void chifferImp( final String secretkey, File file, int modus)" );

		Key key = this.symkeyFromString(secretkey);
		
		Cipher cipher = Cipher.getInstance( this.sym_cipher_algo, "BC");
		cipher.init(modus, key);

        BufferedInputStream bis = new BufferedInputStream( new FileInputStream(file));
        
        File result = null;
        
        if ( modus == Cipher.ENCRYPT_MODE ){
        	result = new File(file.getName() + ".enc");
        }
        else{
        	result = new File(file.getName() + ".dec");
        }
        		
        BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream(result));
			
        byte[] readed = new byte[64];
			
        while( (bis.read(readed)) != -1 ){
            byte[] encrypted = cipher.update(readed, 0, 64);
            bos.write(encrypted);
        }
			
        bis.close();
        bos.close();
	}

	public void decrypt(final String secretkey, File file) throws Exception {
		this.chifferImp(secretkey, file, Cipher.DECRYPT_MODE);
	}
	
	public void encrypt( final String secretkey, File file ) throws Exception{
		this.chifferImp(secretkey, file, Cipher.ENCRYPT_MODE);
	}
}
