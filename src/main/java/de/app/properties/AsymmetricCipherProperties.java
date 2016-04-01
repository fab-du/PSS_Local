package de.app.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cipher.asymmetric")
public class AsymmetricCipherProperties {

	int keylenght;
	String key_algo;
	String cipher_algo;
	
	public int getKeylenght() {
		return keylenght;
	}
	public void setKeylenght(int keylenght) {
		this.keylenght = keylenght;
	}
	public String getKey_algo() {
		return key_algo;
	}
	public void setKey_algo(String key_algo) {
		this.key_algo = key_algo;
	}
	public String getCipher_algo() {
		return cipher_algo;
	}
	public void setCipher_algo(String cipher_algo) {
		this.cipher_algo = cipher_algo;
	}
}
