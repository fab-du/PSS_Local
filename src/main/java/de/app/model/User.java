package de.app.model;

public class User {
	String name ;
	String vorname;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", vorname=" + vorname + "]";
	} 
}
