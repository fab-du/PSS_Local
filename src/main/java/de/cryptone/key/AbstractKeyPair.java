package de.cryptone.key;


import de.cryptone.key.implementation.KeyPairImpl;

public abstract class AbstractKeyPair extends AbstractKey<AbstractKeyPair> {
   KeyPairImpl implementation = new KeyPairImpl();
   
   String password;
   String salt;
   
	public KeyPairImpl getImplementation() {
		return implementation;
	}
	
	public AbstractKeyPair setImplementation(KeyPairImpl implementation) {
		this.implementation = implementation;
		return this;
	}
	
	public String getPassword() {
		return password;
	}
	public AbstractKeyPair setPassword(String password) {
		this.password = password;
		return this;
	}
	public String getSalt() {
		return salt;
	}
	public AbstractKeyPair setSalt(String salt) {
		this.salt = salt;
		return this;
	}
   

   
}
