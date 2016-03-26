package de.app.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cipher.aes")
public class AESProperties extends RSAProperties{
}
