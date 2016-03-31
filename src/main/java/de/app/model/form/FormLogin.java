package de.app.model.form;

import javax.validation.constraints.NotNull;

public class FormLogin {
	
	@NotNull
	String email;
	@NotNull
	String password;
	String passphrase;

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

	@Override
	public String toString() {
		return "FormLogin [email=" + email + ", password=" + password + ", passphrase=" + passphrase + "]";
	}
	
}
