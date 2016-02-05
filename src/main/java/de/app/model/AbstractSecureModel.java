package de.app.model;

public abstract class AbstractSecureModel {
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
