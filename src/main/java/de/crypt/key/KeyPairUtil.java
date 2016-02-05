package de.crypt.key;

import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public abstract class KeyPairUtil {
	
	protected String provider;
	protected SecureRandom secureRandom;

    protected KeyPairUtil(String provider, SecureRandom secureRandom)
    {
        this.provider = provider;
        this.secureRandom = secureRandom;
    }
	
    
    protected KeyFactory getKeyFactory() throws DeCryptException
    {
        String agl = getAlgorithm();
        try
        {
            return provider == null ? KeyFactory.getInstance(agl) : KeyFactory.getInstance(agl, provider);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new DeCryptException("Couldn't find " + agl + " KeyFactory! " + e, e);
        }
        catch (NoSuchProviderException e)
        {
            throw new DeCryptException("Cannot get KeyFactory instance with provider " + provider, e);
        }
    }
    
    abstract String getAlgorithm();

	protected KeyPairGenerator getKeyPairGenerator() throws DeCryptException{
		  
		String alg = getAlgorithm();
	     try
	     {
	       return provider == null ? KeyPairGenerator.getInstance(alg) : KeyPairGenerator.getInstance(alg, provider);
	     }
	    catch (NoSuchAlgorithmException e)
	    {
	            throw new DeCryptException("Couldn't find " + alg + " KeyPairGenerator! " + e, e);
	    }
	    catch (NoSuchProviderException e)
	    {
	       throw new DeCryptException("Cannot get KeyPairGenerator instance with provider " + provider, e);
	    }
		
	}
	
	
}
