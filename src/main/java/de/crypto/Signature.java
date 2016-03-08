package de.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import de.app.model.KeyPair;
import de.cryptone.utils.Helper;

public class Signature {

    public final static String SIGNATURE_ALGO  = "SHA256withRSA";

    public final static String ENTRY_ISS       = "iss";

    public final static String ENTRY_SIGNATURE = "signature";
    
    public final static String EXC_MESS_NULL   = "no arguments provided:";


	public String sign( String privatekey , String obj ) throws NoSuchAlgorithmException, 
																SignatureException, 
                                                                InvalidKeyException,
																Exception{
		
		if( privatekey == null || obj == null )
			throw new Exception( EXC_MESS_NULL + "String sign( String privatekey , String obj )" );
		
		Map<String, String> result = new HashMap<String, String>();
		java.security.Signature sign = java.security.Signature.getInstance( SIGNATURE_ALGO ); 
        sign.initSign( Helper.priKeyFromString( privatekey ) );
        sign.update(obj.getBytes());
        byte[] signature = sign.sign();
        result.put("iss", obj);
        result.put("signature", Helper.encode(signature));
        return Helper.toJson(result);
	}
	
	public String sign( KeyPair key , String passphrase, String obj ) throws Exception{
		byte[] salt = Helper.decode(key.getSalt());
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		PBEKeySpec spec = new PBEKeySpec(passphrase.toCharArray(), salt, 1000, 128);
		SecretKey _key =  factory.generateSecret(spec);
		SecretKey encKey = new SecretKeySpec(_key.getEncoded(), "AES");
		AESCrypto aes = new AESCrypto();
		String dec_prikey = aes.decrypt(Helper.encode( encKey.getEncoded()), key.getPrikey());
		return this.sign(dec_prikey, obj);
	}
	
	public boolean verify(String publicKey , Object obj) throws NoSuchAlgorithmException,  
		                                                        SignatureException,        
		                                                        InvalidKeyException {      
		
        java.security.Signature sign = java.security.Signature.getInstance( SIGNATURE_ALGO );
        String _obj = (String) obj;
        sign.initVerify( Helper.pubKeyFromString( publicKey ));
        sign.update( _obj.getBytes() );
        byte[] fromBase64 = Helper.decode( _obj );
        return sign.verify( fromBase64 );
	}
}
