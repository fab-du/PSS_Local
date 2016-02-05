package de.crypt.interfaces;

public interface Chiffer <INClazz , OUTClazz > {
	
	/**
	 * 
	 * @param input the input that have to be chiffer
	 *
	 * @author Siyapdje, Fabrice Dufils
	 * @version 1.0 
	 */
	public
	OUTClazz chiffer( INClazz input, AbstractSecretKey key );
	
	/**
	 * 
	 * @param input the input that have to be dechiffer
	 * 
	 * @author Siyapdje, Fabrice Dufils
	 * @version 1.0 
	 */
	public
	OUTClazz dechiffer( INClazz input, AbstractPublicKey key );

}
