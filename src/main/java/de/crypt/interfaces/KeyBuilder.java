package de.crypt.interfaces;


public class KeyBuilder {

	String algo; 
	
	public void algo( String algo ){
		this.algo = algo; 
	}
	
	public String getAlgo(){
		return this.algo; 
	}
}
