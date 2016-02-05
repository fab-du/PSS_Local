package de.crypto;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class AESCrypto {

	Key symkeyFromString(String key) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		byte[] bytes = key.getBytes();
		byte[] keybytes = Base64.getDecoder().decode(bytes);
		Key result = new SecretKeySpec(keybytes, 0, keybytes.length, "AES");
		return result;
	}

	Key generateSymkey() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyGenerator keygen = null;
		Key symkey = null;

		try {
			keygen = KeyGenerator.getInstance("AES");
			keygen.init(128, new SecureRandom());
			symkey = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return symkey;
	}

	public String generateKey() {
		Key key = this.generateSymkey();

		if (key != null) {
			byte[] encoded = key.getEncoded();
			byte[] base64 = Base64.getEncoder().encode(encoded);
			String result = null;
			try {
				result = new String(base64, "UTF8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return result;
		}
		return null;
	}

	public String generateSymKey(String passphrase) {
		return null;
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

	public String decrypt(final String secretkey, final String message) {
		Cipher cipher = null;
		String clearText = null;
		byte[] stringBytes = null;

		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		Key key = this.symkeyFromString(secretkey);
		if (key == null)
			return null;

		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
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
