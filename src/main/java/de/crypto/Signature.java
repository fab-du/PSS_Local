package de.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

import de.cryptone.utils.Config;
import de.cryptone.utils.Helper;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.crypto.JwtSigner;

public class Signature {

    public final static String SIGNATURE_ALGO  = "SHA256withRSA";

    public final static String ENTRY_ISS       = "iss";

    public final static String ENTRY_SIGNATURE = "signature";

	public String sign( String privatekey , Object obj, Class clazz ) throws NoSuchAlgorithmException, 
                                                                             SignatureException, 
                                                                             InvalidKeyException {
		
		Map<String, String> result = new HashMap<String, String>();
		java.security.Signature sign = java.security.Signature.getInstance( SIGNATURE_ALGO ); 
        String _obj = (String) obj;
        sign.initSign( Helper.priKeyFromString( privatekey ) );
        sign.update(_obj.getBytes());
        byte[] signature = sign.sign();
        result.put("iss", _obj);
        result.put("signature", Helper.encode(signature));
        return Helper.toJson(result);
	}

	public boolean verify(String publicKey , Object obj) throws NoSuchAlgorithmException,  
		                                                        SignatureException,        
		                                                        InvalidKeyException {      
        boolean ret = false; 
		java.security.Signature sign;
		Map<String, String> result = new HashMap<String, String>();
		
        java.security.Signature sign = java.security.Signature.getInstance( SIGNATURE_ALGO );
        String _obj = (String) obj;
        sign.initVerify( Helper.pubKeyFromString( publicKey ));
        sign.update( _obj.getBytes() );
        byte[] fromBase64 = Helper.decode( _obj );
        return sign.verify( fromBase64 );
	}
}
