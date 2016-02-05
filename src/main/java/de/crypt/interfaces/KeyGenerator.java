package de.crypt.interfaces;



public interface KeyGenerator {

	/**
	 * 
	 * Generate Key as string/json-string 
	 * 
	 * @author Siyapdje, Fabrice Dufils
	 * @version 1.0 
	 */
	public String generate();
	
	/**
	 * 
	 * Generate Key as string/json-string Password based.
	 * 
	 * @author Siyapdje, Fabrice Dufils
	 * @version 1.0 
	 */
	public String generate( String passphrase );
	
}
