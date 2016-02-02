package de.app.model;

import java.util.Date;


public class Group {
	Long id; 
	
	String name;
	
	Long gvid;
	
	Date createdAt;
	

	public Group() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGvid() {
		return gvid;
	}

	public void setGvid(Long gvid) {
		this.gvid = gvid;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
