package de.crypto;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import de.cryptone.utils.Helper;

public class RSACrypto {

	private KeyPair generatePairkey() {
		KeyPairGenerator keyPairGen = null;
		KeyPair keyPair = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
			 SecureRandom secRandom = SecureRandom.getInstanceStrong();
			keyPairGen.initialize(1024, secRandom);
			keyPair = keyPairGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return keyPair;
	}

	public de.app.model.KeyPair generateKey() {
		de.app.model.KeyPair _keypair = new de.app.model.KeyPair();
		KeyPair keypair = this.generatePairkey();
		String prikey = Helper.encode(keypair.getPrivate().getEncoded());
		String pubkey = Helper.encode(keypair.getPublic().getEncoded());
		_keypair.setPrikey(prikey);
		_keypair.setPubkey(pubkey);

		return _keypair;
	}

	public String encrypt(final String key, final String message) {
		return this.encrypt(this.publickeyFromString(key), message);
	}

	private String encrypt(PublicKey key, String message) {
		Cipher cipher = null;
		byte[] raw = null;

		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] stringBytes = message.getBytes();
			raw = cipher.doFinal(stringBytes);
		} catch (Exception e) {
			return null;
		}

		if (raw == null) {
			return null;
		}

		return Base64.getEncoder().encodeToString(raw);
	}

	public String decrypt(final String key, final String message) {
		return this.decrypt(this.privatekeyFromString(key), message);
	}

	private String decrypt(PrivateKey key, String message) {
		Cipher cipher = null;
		String clearText = null;

		try {

			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] raw = Base64.getDecoder().decode(message);
			byte[] stringBytes = cipher.doFinal(raw);
			clearText = new String(stringBytes, "UTF8");
		} catch (Exception e) {
			return null;
		}

		return clearText;
	}

	String privatekeyToString(PrivateKey prikey) {
		byte[] encodedprikey = prikey.getEncoded();
		String base64String = null;
		try {
			base64String = new String(Base64.getEncoder().encode(encodedprikey), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return base64String;
	}

	PrivateKey privatekeyFromString(String prikey) {
		byte prikeybytes[] = Base64.getDecoder().decode(prikey.getBytes());
		KeyFactory keyFactory = null;
		PrivateKey privatekey = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			KeySpec privateKeySpec = new PKCS8EncodedKeySpec(prikeybytes);
			privatekey = keyFactory.generatePrivate(privateKeySpec);
		} catch (Exception e) {
			return null;
		}

		return privatekey;
	}

	PublicKey publickeyFromString(String pubkey) {
		byte[] pubkeyBytes = Base64.getDecoder().decode(pubkey.getBytes());
		X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(pubkeyBytes);
		KeyFactory kf = null;
		PublicKey publickey = null;
		try {
			kf = KeyFactory.getInstance("RSA");
			publickey = kf.generatePublic(X509publicKey);
		} catch (Exception e) {
			return null;
		}

		return publickey;
	}

	String map(KeyPair keypair) {
		String encodedPub = Helper.encode(keypair.getPublic().getEncoded());
		String encodedPri = Helper.encode(keypair.getPrivate().getEncoded());
		Map<String, String> result = new HashMap<>();
		result.put("pubkey", encodedPub);
		result.put("prikey", encodedPri);
		return Helper.toJson(result);
	}
}
