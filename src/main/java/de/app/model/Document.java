package de.app.model;

import java.util.Date;

import org.springframework.data.annotation.LastModifiedDate;

public class Document extends AbstractEntity{
	String name, path;
	public Long version;
	public @LastModifiedDate Date date;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
	
}
