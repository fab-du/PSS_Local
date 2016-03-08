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

    public final static String  ASYM_KEY_ALGO    = "RSA";

    public final static String  ASYM_CIFFER_ALGO = "RSA";

    public final static String  ENTRY_SECRET     = "secret";

    public final static String  ENTRY_SALT       = "salt";

    public final static int KEY_LENGHT           = 1024;

    public final static String EXC_MESS_NULL = "no arguments provided:";

	private KeyPair generatePairkey() throws NoSuchAlgorithmException {
		 KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance( ASYM_KEY_ALGO );
		 keyPairGen.initialize( KEY_LENGHT );
		 return keyPairGen.generateKeyPair();
	}

	public de.app.model.KeyPair generateKey() throws NoSuchAlgorithmException {
		de.app.model.KeyPair _keypair = new de.app.model.KeyPair();
		KeyPair keypair = this.generatePairkey();
		String prikey = Helper.encode(keypair.getPrivate().getEncoded());
		String pubkey = Helper.encode(keypair.getPublic().getEncoded());
		_keypair.setPrikey(prikey);
		_keypair.setPubkey(pubkey);
		return _keypair;
	}
	
	public Map<String, Object> generateSecretFromPassphrase( String passphrase ) throws NoSuchAlgorithmException, InvalidKeySpecException{
		Map<String, Object> result= new HashMap<>();
		SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		rand.nextBytes(salt);
		
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		PBEKeySpec spec = new PBEKeySpec(passphrase.toCharArray(), salt, 1000, 128);
		SecretKey _key =  factory.generateSecret(spec);
		SecretKey encKey = new SecretKeySpec(_key.getEncoded(), "AES");
		result.put( ENTRY_SECRET, encKey);
		result.put( ENTRY_SALT, Helper.encode(salt));
		return result;
	}
	
	public de.app.model.KeyPair generateKey( String passphrase ) throws NoSuchAlgorithmException, InvalidKeySpecException, Exception {
		AESCrypto aes = new AESCrypto();
		
		de.app.model.KeyPair keypair = this.generateKey();
		Map<String, Object> ret = this.generateSecretFromPassphrase(passphrase);
		SecretKey secretkey = (SecretKey) ret.get( ENTRY_SECRET );
		String prikey;
		prikey = aes.encrypt( Helper.encode(secretkey.getEncoded()), keypair.getPrikey());
		keypair.setSalt( (String) ret.get(ENTRY_SALT) );
		keypair.setPrikey(prikey);
		return keypair;
	}

	public String encrypt(final String key, final String message) throws Exception {
		return this.encrypt(this.publickeyFromString(key), message);
	}

	private String encrypt(PublicKey key, String message) throws Exception {
        Cipher cipher = Cipher.getInstance( ASYM_CIFFER_ALGO );
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] stringBytes = message.getBytes();
        byte[] raw = cipher.doFinal(stringBytes);

		if (raw == null) 
		    throw new Exception("Encryption goes wrong");

		return Base64.getEncoder().encodeToString(raw);
	}

	public String decrypt(final String key, final String message) throws Exception {
		return this.decrypt(this.privatekeyFromString(key), message);
	}
	
	public String decrypt( final de.app.model.KeyPair key , final String passphrase, final String message ) throws NoSuchAlgorithmException,
																												   InvalidKeySpecException,
																												   Exception{
		byte[] salt = Helper.decode(key.getSalt());
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		PBEKeySpec spec = new PBEKeySpec(passphrase.toCharArray(), salt, 1000, 128);
		SecretKey _key =  factory.generateSecret(spec);
		SecretKey encKey = new SecretKeySpec(_key.getEncoded(), "AES");
		AESCrypto aes = new AESCrypto();
		String dec_prikey = aes.decrypt(Helper.encode( encKey.getEncoded()), key.getPrikey());
		return this.decrypt(dec_prikey, message);
	}

	private String decrypt(PrivateKey key, String message) throws Exception {
        Cipher cipher = Cipher.getInstance( ASYM_CIFFER_ALGO );
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] raw = Base64.getDecoder().decode(message);
        byte[] stringBytes = cipher.doFinal(raw);
        return new String(stringBytes, "UTF8");
	}

	PrivateKey privatekeyFromString(String prikey) throws Exception {
        if ( prikey == null )
            throw new Exception( EXC_MESS_NULL +  "PrivateKey privatekeyFromString(String prikey)" );

		byte prikeybytes[] = Base64.getDecoder().decode(prikey.getBytes());
		KeyFactory  keyFactory = KeyFactory.getInstance("RSA");
		KeySpec privateKeySpec = new PKCS8EncodedKeySpec(prikeybytes);
		return keyFactory.generatePrivate(privateKeySpec);
	}

	PublicKey publickeyFromString(String pubkey) throws Exception {
        if ( pubkey == null )
            throw new Exception( EXC_MESS_NULL +  "PublicKey publickeyFromString(String pubkey)" );

		byte[] pubkeyBytes = Base64.getDecoder().decode(pubkey.getBytes());
		X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(pubkeyBytes);
        KeyFactory kf = KeyFactory.getInstance( ASYM_KEY_ALGO );
        return kf.generatePublic(X509publicKey);
	}

}
