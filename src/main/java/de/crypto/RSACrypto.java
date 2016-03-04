package de.crypto;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import de.cryptone.utils.Helper;

public class RSACrypto {
	
	private KeyPair generatePairkey() {
		KeyPairGenerator keyPairGen = null;
		KeyPair keyPair = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024);
			keyPair = keyPairGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
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
	
	private Map<String, Object> generateSecretFromPassphrase( String passphrase ) throws NoSuchAlgorithmException, InvalidKeySpecException{
		Map<String, Object> result= new HashMap<>();
		SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		rand.nextBytes(salt);
		
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		PBEKeySpec spec = new PBEKeySpec(passphrase.toCharArray(), salt, 1000, 128);
		SecretKey _key =  factory.generateSecret(spec);
		SecretKey encKey = new SecretKeySpec(_key.getEncoded(), "AES");
		result.put("secret", encKey);
		result.put("salt", Helper.encode(salt));
		return result;
	}
	
	public de.app.model.KeyPair generateKey( String passphrase ) throws NoSuchAlgorithmException, InvalidKeySpecException {
		AESCrypto aes = new AESCrypto();
		
		de.app.model.KeyPair keypair = this.generateKey();
		Map<String, Object> ret = this.generateSecretFromPassphrase(passphrase);
		SecretKey secretkey = (SecretKey) ret.get("secret");
		String prikey = aes.encrypt( Helper.encode(secretkey.getEncoded()), keypair.getPrikey());
		keypair.setSalt( (String) ret.get("salt") );
		keypair.setPrikey(prikey);

		return keypair;
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
	
	public String decrypt( final de.app.model.KeyPair key , final String passphrase, final String message ) throws NoSuchAlgorithmException, InvalidKeySpecException{
		byte[] salt = Helper.decode(key.getSalt());
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		PBEKeySpec spec = new PBEKeySpec(passphrase.toCharArray(), salt, 1000, 128);
		SecretKey _key =  factory.generateSecret(spec);
		SecretKey encKey = new SecretKeySpec(_key.getEncoded(), "AES");
		AESCrypto aes = new AESCrypto();
		String dec_prikey = aes.decrypt(Helper.encode( encKey.getEncoded()), key.getPrikey());
		String ret = this.decrypt(dec_prikey, message);
		
		return ret;
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

}
