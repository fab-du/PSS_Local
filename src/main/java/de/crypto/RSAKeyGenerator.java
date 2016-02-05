package de.crypto;

import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import de.app.model.KeyPair;
import de.cryptone.utils.Helper;
import de.app.exceptions.BatieCryptoExc;

public  class RSAKeyGenerator {
	
	String algo = "RSA";
	String algoPB = "SHA1withRSA";
	int keysize = 1024;
	
	
	public KeyPair generate( String password ) throws BatieCryptoExc{
		this.getKeyFactory(null, this.algoPB);
		return null;
	}
	
	public KeyPair generate() throws BatieCryptoExc{
		KeyPairGenerator gen = this.getKeyPairGenerator(algo, null);
		
		try {
			gen.initialize(keysize, SecureRandom.getInstanceStrong());
			java.security.KeyPair _keypair = gen.generateKeyPair();
			KeyPair keypair = new KeyPair();
			keypair.setPrikey( Helper.encode( _keypair.getPrivate().getEncoded()));
			keypair.setPubkey( Helper.encode( _keypair.getPublic().getEncoded()));
			return keypair;
		} catch (NoSuchAlgorithmException e) {
		   throw new BatieCryptoExc( e);
		}
		
	}
	
    protected KeyFactory getKeyFactory(String alg, String provider) throws BatieCryptoExc
    {
        try
        {
            return provider == null ? KeyFactory.getInstance(alg) : KeyFactory.getInstance(alg, provider);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new BatieCryptoExc("Couldn't find " + alg + " KeyFactory! " + e, e);
        }
        catch (NoSuchProviderException e)
        {
            throw new BatieCryptoExc("Cannot get KeyFactory instance with provider " + provider, e);
        }
    }
    
	protected KeyPairGenerator getKeyPairGenerator( String alg, String provider) throws BatieCryptoExc{
		  
	     try
	     {
	       return provider == null ? KeyPairGenerator.getInstance(alg) : KeyPairGenerator.getInstance(alg, provider);
	     }
	    catch (NoSuchAlgorithmException e)
	    {
	            throw new BatieCryptoExc("Couldn't find " + alg + " KeyPairGenerator! " + e, e);
	    }
	    catch (NoSuchProviderException e)
	    {
	       throw new BatieCryptoExc("Cannot get KeyPairGenerator instance with provider " + provider, e);
	    }
		
	}
	
}
