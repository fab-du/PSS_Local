package de.cryptone.key;

import java.security.Key;

import javax.crypto.SecretKey;

import de.cryptone.key.implementation.KeySymImpl;
import de.cryptone.utils.Helper;


public class KeySym extends AbstractKey<AbstractKey>{

	KeySymImpl implementation = new KeySymImpl(); 

	String salt; 

	@Override
	protected AbstractKey getThis() {
		return this;
	}

	@Override
	public String generate() {
		SecretKey key = implementation.generateKey();
		return Helper.encode(key.getEncoded());
	}
}
