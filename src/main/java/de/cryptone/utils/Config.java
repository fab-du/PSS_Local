package de.cryptone.utils;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;



@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {
	
	String provider() default Konst.PROVIDER;
	int    keylenght() default Konst.ASYM_KEY_LENGHT; 
	
}
