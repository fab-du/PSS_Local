package de.app.model.form;

public class FormRegister {

	String email;
	String firstname;
	String secondname;
	String company;
	String passphrase;

	String verifier;
	String salt;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getPassphrase() {
		return passphrase;
	}
	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}
	public String getVerifier() {
		return verifier;
	}
	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}


	@Override
	public String toString() {
		return "FormRegister [email=" + email + ", firstname=" + firstname + ", secondname=" + secondname + ", company="
				+ company + ", passphrase=" + passphrase + ", verifier=" + verifier + ", salt=" + salt + "]";
	}
	

}
