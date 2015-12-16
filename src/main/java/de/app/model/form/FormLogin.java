package de.app.model.form;

public class FormLogin {

	String email;
	/*
	 * Authentication specific attributs
	 */
	String B;
	String A;
	String M1;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getB() {
		return B;
	}
	public void setB(String b) {
		B = b;
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
	@Override
	public String toString() {
		return "FormLogin [email=" + email + ", B=" + B + ", A=" + A + ", M1=" + M1 + "]";
	}




}
