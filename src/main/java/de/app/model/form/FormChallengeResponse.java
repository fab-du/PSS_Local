package de.app.model.form;

public class FormChallengeResponse {
	String b;
	String salt; 

	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b = b;
	}
	@Override
	public String toString() {
		return "FormChallengeResponse [b=" + b + ", salt=" + salt + "]";
	}
}
