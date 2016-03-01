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

	
	public KeySym generateKey(){
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			try {
				KeyGenerator keygen = null;
				KeySym result = new KeySym();
				byte[] salt = new byte[32];
				new SecureRandom();
				final SecureRandom r =  SecureRandom.getInstanceStrong();
				r.nextBytes(salt);
				keygen = KeyGenerator.getInstance("AES");
				keygen.init(128, r );
				Key k = keygen.generateKey();
				result.setSymkey( Helper.encode( k.getEncoded()));
				result.setSalt( Helper.encode(salt));
				return result;
			} catch (NoSuchAlgorithmException e) {
				return null;
			}
	}
	
	Key symkeyFromString(String key) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		byte[] bytes = key.getBytes();
		byte[] keybytes = Base64.getDecoder().decode(bytes);
		Key result = new SecretKeySpec(keybytes, 0, keybytes.length, "AES");
		return result;
	}


	public String encrypt(final String secretkey, final String message) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher cipher = null;
		byte[] raw = null;

		Key key = this.symkeyFromString(secretkey);

		if (key == null)
			return null;

		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] stringBytes = message.getBytes();
			raw = cipher.doFinal(stringBytes);

		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}

		if (raw == null)
			return null;
		return Base64.getEncoder().encodeToString(raw);
	}
	
	private void chifferImp( final String secretkey, File file, int modus){
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		Cipher cipher = null;
		Key key = this.symkeyFromString(secretkey);

		if (key == null) return;
		
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "BC");
			cipher.init(modus, key);

			BufferedInputStream bis = new BufferedInputStream( new FileInputStream(file));
			File result = new File(file.getName() + ".enc");
			BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream(result));
			
			byte[] readed = new byte[64];
			
			while( (bis.read(readed)) != -1 ){
				byte[] encrypted = cipher.update(readed, 0, 64);
				bos.write(encrypted);
			}
			
			bis.close();
			bos.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}

	}

	public void decrypt(final String secretkey, File file) {
		this.chifferImp(secretkey, file, Cipher.DECRYPT_MODE);
	}
	
	public void encrypt( final String secretkey, File file ){
		this.chifferImp(secretkey, file, Cipher.ENCRYPT_MODE);
	}
	
	
	public static void main(String[] args) {
		AESCrypto aes = new AESCrypto();
		KeySym key = aes.generateKey();
		System.out.println( key.toString() );
	}
}
