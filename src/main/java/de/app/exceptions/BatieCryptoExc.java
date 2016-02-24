package de.app.exceptions;

public class BatieCryptoExc extends Exception{

	private static final long serialVersionUID = 1L;

	public BatieCryptoExc() {}
	
	public BatieCryptoExc( String message ){
		super(message);
	}
	
	public BatieCryptoExc( Throwable cause){
		super(cause);
	}
	
	public BatieCryptoExc( String message, Throwable cause ){
		super(message, cause);
	}
}
