package de.app.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cipher.hash")
public class HashProperties extends AbstractCipherProperties{

}
