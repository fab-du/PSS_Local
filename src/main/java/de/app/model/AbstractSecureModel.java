package de.app.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class AbstractSecureModel implements Serializable {

	/*
     * JWS Signature
     * */
    String signature;

    /*
     * Hash value
     * */
    String hashValue;

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getHashValue() {
		return hashValue;
	}

	public void setHashValue(String hashValue) {
		this.hashValue = hashValue;
	}
    
    
}
