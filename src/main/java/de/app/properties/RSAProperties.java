package de.app.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cipher.asymmetric")
public class RSAProperties {

	String keylenght;
	String algo;
	
	public String getKeylenght() {
		return keylenght;
	}
	public void setKeylenght(String keylenght) {
		this.keylenght = keylenght;
	}
	public String getAlgo() {
		return algo;
	}
	public void setAlgo(String algo) {
		this.algo = algo;
	}
	
}
