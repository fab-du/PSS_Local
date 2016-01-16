package de.cryptone.utils;

public class CryptUtilException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CryptUtilException() {
	}

	public CryptUtilException( String message ) {
		super( message );
	}
	
	public CryptUtilException( String message, Throwable cause ) {
		super( message, cause );
	}
	
	public CryptUtilException( String message, Throwable cause , boolean enableSuppression, boolean writableStackTrace) {
		super( message, cause, enableSuppression, writableStackTrace );
	}
	
}
