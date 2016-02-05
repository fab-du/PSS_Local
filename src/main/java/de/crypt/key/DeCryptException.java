package de.crypt.key;

public class DeCryptException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6922203672900955304L;

	public DeCryptException() {}
	
	public DeCryptException( String message ){
		super(message);
	}
	
	public DeCryptException( Throwable cause){
		super(cause);
	}
	
	public DeCryptException( String message, Throwable cause ){
		super(message, cause);
	}
	
}
