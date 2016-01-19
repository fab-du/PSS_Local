package de.cryptone.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class AppProperties {
	
	public final static String propertyFileName = "cryptUtils.properties";
	
	
	public static String getProperty( String _key ){
		
		Properties properties = new Properties();

		try {
			File file = new File(propertyFileName);
			FileInputStream fileInput = new FileInputStream(file);

			properties.load(fileInput);
			fileInput.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String value = null;
		
		Enumeration<Object> keys = properties.keys();
		
		while( keys.hasMoreElements() ){
			
			String key = (String) keys.nextElement();
			if( _key.trim().equals( key.trim() )){
				value = properties.getProperty(key);
				break;
			}
		}
		
		return value;
	}

	
	public static void  getProperty(  ){
		try {
			File file = new File(propertyFileName);
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration<Object> enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
				System.out.println(key + ": " + value);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
		String foo = AppProperties.getProperty("MD.algo");
		System.out.println( foo );
		
		@Config(keylenght = 100 )
		String provider = null;
		
		System.out.println( provider );
	}
}
