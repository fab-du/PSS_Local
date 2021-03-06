package de.app.model.form;

import javax.validation.constraints.NotNull;

import de.app.model.Group;
import de.app.model.KeySym;

public class FormRegister {

	@NotNull
	String email;
	@NotNull
	String firstname;
	@NotNull
	String secondname;
	@NotNull
	String company;
	@NotNull
	String password;
	@NotNull
	String passphrase;
	String verifier;
	String salt;
	String pubkey;
	String prikey; 
	String srpsalt;
	
	KeySym groupsymkey;
	
	Group group;

	
	public Group getGroup() {
		return group;
	}
	
	
	public void setSrpsalt( String srpsalt ){
		this.srpsalt = srpsalt;
	}
	public String getSrpsalt(){
		return srpsalt;
	}

	public KeySym getGroupsymkey() {
		return groupsymkey;
	}
	public void setGroupsymkey(KeySym groupsymkey) {
		this.groupsymkey = groupsymkey;
	}


	public void setGroup(Group group) {
		this.group = group;
	}
	
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
	public String getPubkey() {
		return pubkey;
	}
	public void setPubkey(String pubkey) {
		this.pubkey = pubkey;
	}
	public String getPrikey() {
		return prikey;
	}
	public void setPrikey(String prikey) {
		this.prikey = prikey;
	}
	
	@Override
	public String toString() {
		return "FormRegister [email=" + email + ", firstname=" + firstname + ", secondname=" + secondname + ", company="
				+ company + ", password=" + password + ", passphrase=" + passphrase + ", verifier=" + verifier
				+ ", salt=" + salt + ", pubkey=" + pubkey + ", prikey=" + prikey + "]";
	}
	
}
