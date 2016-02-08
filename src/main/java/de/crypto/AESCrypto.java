package de.crypto;

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
	
	public byte[] encrypt( final String secretkey, final byte[] message ){
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher cipher = null;
		byte[] raw = null;

		Key key = this.symkeyFromString(secretkey);

		if (key == null)
			return null;
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			raw = cipher.doFinal(message);

		} catch (Exception e) {
			return null;
		}

		if (raw == null)
			return null;
				return raw;
	}

	public String decrypt(final String secretkey, final String message) {
		Cipher cipher = null;
		String clearText = null;
		byte[] stringBytes = null;

		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		Key key = this.symkeyFromString(secretkey);
		if (key == null)
			return null;

		try {
			cipher = Cipher.getInstance("AES/ECB/NoPadding", "BC");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] raw = Base64.getDecoder().decode(message);
			stringBytes = cipher.doFinal(raw);
			clearText = new String(stringBytes, "UTF8");
		} catch (Exception e) {
			return null;
		}

		return clearText;
	}
}
