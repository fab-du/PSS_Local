package de.app.model.form;

public class FormAuthentication {
	String email;
	String A;
	String M1;
	
	public FormAuthentication(String A, String M1) {
		this.A = A;
		this.M1 = M1;
	}
	
	public String getA() {
		return A;
	}
	public void setA(String a) {
		A = a;
	}
	public String getM1() {
		return M1;
	}
	public void setM1(String m1) {
		M1 = m1;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
