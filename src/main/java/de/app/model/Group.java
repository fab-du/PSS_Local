package de.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Group {
	//Mongoose related variable
	@JsonIgnore
	Object _id;
	@JsonIgnore
	Object __v; 
	
	Long id;
	String name;
	String createdAt;
	
	public Group() {
	}

	public Object get_id() {
		return _id;
	}

	public void set_id(Object _id) {
		this._id = _id;
	}

	public Object get__v() {
		return __v;
	}

	public void set__v(Object __v) {
		this.__v = __v;
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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

}
