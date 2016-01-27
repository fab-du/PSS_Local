package de.app.controller;


import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.jose4j.lang.JoseException;

import de.cryptone.salt.Salt;
import de.cryptone.utils.Helper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.JwtSignatureValidator;
import io.jsonwebtoken.impl.crypto.MacProvider;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import io.jsonwebtoken.impl.crypto.RsaSignatureValidator;

public class Main {
	
	public static void main(String[] args) throws JoseException, NoSuchAlgorithmException {
		//Key key = MacProvider.generateKey();

		
		 Key key = new AesKey(ByteUtil.randomBytes(16));

		String s = Jwts.builder().setSubject("Joe").signWith(SignatureAlgorithm.HS512, key).compact();
		System.out.println( s );
		
		try {

		    Jws<Claims> claims = Jwts.parser().requireSubject("Joe").setSigningKey(key).parseClaimsJws(s);
		    //OK, we can trust this JWT

		} catch (SignatureException e) {
		    System.out.println( "error");

		    //don't trust the JWT!
		}
	}
	

}
