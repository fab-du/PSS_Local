package de.app;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cipher")
public class CipherProperties {

	String sym_algo;
	String sym_keylenght;
	String symkeygen_algo;

}
