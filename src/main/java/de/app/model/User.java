package de.app.model;

import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User extends AbstractEntity{
	/*
	 * user : 
	 * 		 - email
	 * 		 - company
	 */
	String firstname;
	String secondname;
	
	String company; 

	@NotBlank
	@Email
	String email;
	
	@JsonIgnore
	String password;
	
	@JsonIgnore
	String passphrase;
	
	@JsonIgnore
	boolean validated=false;
	
	public User() {	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}



	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSecondname() {
		return secondname;
	}

	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getPassphrase() {
		return passphrase;
	}



	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}
	
	

}
