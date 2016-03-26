package de.app.model;

import java.util.Date;


import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class KeySym extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String symkey;
	String salt; 

	public Long version;
	@JsonIgnore
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
	@Override
	public String toString() {
		return "KeySym [symkey=" + symkey + ", salt=" + salt + ", version=" + version + ", date=" + date + "]";
	} 
	
	
}
