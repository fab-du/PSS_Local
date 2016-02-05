package de.app.model;

import java.util.Date;


import org.springframework.data.annotation.LastModifiedDate;

public class KeySym extends AbstractEntity{

	String symkey;
	
	String salt; 

	public Long version;
	public @LastModifiedDate Date date;
	
	public String getSymkey() {
		return symkey;
	}
	public void setSymkey(String symkey) {
		this.symkey = symkey;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	} 
}
