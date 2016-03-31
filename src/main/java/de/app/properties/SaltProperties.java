package de.app.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cipher.salt")
public class SaltProperties extends AbstractCipherProperties{

}
