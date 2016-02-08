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

	public void sign( String subject, String base64Issuer, String base64EncodedKeyBytes ){
		Jwts.parser().setSigningKey(base64EncodedKeyBytes).parse(base64Issuer);
	}
	
	public static String ALGORITHM = "MD5"; 
	
	
	
	public String sign( String privatekey , Object obj) {
		
		java.security.Signature sign;
		Map<String, String> result = new HashMap<String, String>();
		
		try {
			sign = java.security.Signature.getInstance("SHA256withRSA"); 
				String _obj = (String) obj;
				sign.initSign( Helper.priKeyFromString( privatekey ) );
				sign.update(_obj.getBytes());
				byte[] signature = sign.sign();
				
				result.put("iss", _obj);
				result.put("signature", Helper.encode(signature));
				return Helper.toJson(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (SignatureException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public boolean verify(String publicKey , Object obj) {


		boolean ret = false;
		
		java.security.Signature sign;
		Map<String, String> result = new HashMap<String, String>();
		
		
			try {
				sign = java.security.Signature.getInstance("SHA256withRSA");
					String _obj = (String) obj;
					sign.initVerify( Helper.pubKeyFromString( publicKey ));
					sign.update( _obj.getBytes() );
					byte[] fromBase64 = Helper.decode( _obj );
					ret = sign.verify( fromBase64 );
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (SignatureException e) {
				e.printStackTrace();
			} 
			
			return ret;
	}
	
}
