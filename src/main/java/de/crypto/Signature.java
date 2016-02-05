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
	
	boolean primitive = false;
	int keylenght;
	String  prikey; 
	String  pubkey;
	
	Object obj;
	String signature;
	
	public Signature primitive(){
		primitive = true;
		return this;
	}
	
	public Signature signature( String signature ){
		this.signature = signature;
		return this;
	}
	
	@Config( keylenght = 1024)
	private int keylenght(){
		return this.keylenght;
	}
	
	public Signature privateKey( String prikey ){
		this.prikey = prikey;
		return this;
	}
	
	public Signature publickey( String pubkey ){
		this.pubkey = pubkey;
		return this;
	}
	
	public Signature data( Object obj ){
		this.obj = obj;
		return this;
	}
	
	
	public String sign() {
		
		if( this.prikey == null ){
			return null; 
		}
		else if( this.obj == null ){
			return null;
		}
		else{}
		
		java.security.Signature sign;
		Map<String, String> result = new HashMap<String, String>();
		
		try {
			sign = java.security.Signature.getInstance("SHA256withRSA"); 
			if( primitive == true ){
				String _obj = (String) this.obj;
				sign.initSign( Helper.priKeyFromString( this.prikey ) );
				sign.update(_obj.getBytes());
				byte[] signature = sign.sign();
				
				result.put("iss", _obj);
				result.put("signature", Helper.encode(signature));
				return Helper.toJson(result);
			}
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
		return null;
	}

	
	public boolean verify() {

		if( this.pubkey == null ){
			return false; 
		}
		else if( this.obj == null ){
			return false;
		}
		else if( this.signature == null){
			return false;
		}
		else{}

		boolean ret = false;
		
		java.security.Signature sign;
		Map<String, String> result = new HashMap<String, String>();
		
		
			try {
				sign = java.security.Signature.getInstance("SHA256withRSA");
				if( primitive == true ){
					String _obj = (String) this.obj;
					sign.initVerify( Helper.pubKeyFromString( this.pubkey ));
					sign.update( _obj.getBytes() );
					byte[] fromBase64 = Helper.decode( this.signature );
					ret = sign.verify( fromBase64 );
				}
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
