package de.cryptone.key;


public abstract class AbstractKey <T extends AbstractKey> extends Object{
	
	String algo; 
	int keyLenght;

	
	protected abstract T getThis();
	public abstract String generate();
	
	public AbstractKey algorithm( String algo ){
		this.algo = algo;
		return this;
	}
	
	private String getAlgo(){
		return this.algo;
	}
	
	public void keyLenght( int keyLenght ){
		this.keyLenght = keyLenght;
	}
	
	private int getKeylenght(){
		return this.keyLenght;
	}
	
	
}
