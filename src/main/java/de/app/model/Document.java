package de.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Document {

	Long id ; 
	String path;
	String name; 
	
	@JsonIgnore
	Object _id;
	@JsonIgnore
	Object __v;
	
	
	public Object get__v() {
		return __v;
	}

	public void set__v(Object __v) {
		this.__v = __v;
	}

	public Document() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object get_id() {
		return _id;
	}

	public void set_id(Object _id) {
		this._id = _id;
	}
	
	
}
