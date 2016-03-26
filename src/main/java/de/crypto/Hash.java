package de.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import de.cryptone.utils.Helper;

public class Hash {
	
    public final static String HASH_ALGO = "MD5";
	
	public String hash( byte[] obj ) throws NoSuchAlgorithmException{
	    MessageDigest md = MessageDigest.getInstance( HASH_ALGO );
		md.update(obj);
	   	byte[] digest = md.digest();
	   	return Helper.encode(digest);
	}

	public boolean checkHash(byte[] obj , String digesta) throws NoSuchAlgorithmException{
		byte[] _digesta = Helper.decode(digesta);
        byte[] digestb = Helper.decode(this.hash(obj));
        return MessageDigest.isEqual(_digesta, digestb);
	}
}
