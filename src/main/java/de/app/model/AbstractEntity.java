package de.app.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class AbstractEntity extends AbstractSecureModel{
	
	Long id;
	
	@JsonIgnore
	Date createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
