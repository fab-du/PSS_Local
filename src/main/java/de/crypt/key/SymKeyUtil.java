package de.crypt.key;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;


public abstract class SymKeyUtil {
	
	protected String provider;
	protected SecureRandom secureRandom;

    protected SymKeyUtil(String provider, SecureRandom secureRandom)
    {
        this.provider = provider;
        this.secureRandom = secureRandom;
    }
	
    
    protected SecretKeyFactory getKeyFactory() throws DeCryptException
    {
        String agl = getAlgorithm();
        try
        {
            return provider == null ? SecretKeyFactory.getInstance(agl) : SecretKeyFactory.getInstance(agl, provider);
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

	protected KeyGenerator getKeyPairGenerator() throws DeCryptException{
		  
		String alg = getAlgorithm();
	     try
	     {
	       return provider == null ? KeyGenerator.getInstance(alg) : KeyGenerator.getInstance(alg, provider);
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
