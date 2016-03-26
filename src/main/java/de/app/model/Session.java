package de.app.model;

public class Session extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String email;
	String B;
	String salt;
	String token;
	Long expires;
	
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

	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public Long getExpires() {
		return expires;
	}

	public void setExpires(Long expires) {
		this.expires = expires;
	}
	
	@Override
	public String toString() {
		return "Session [email=" + email + ", B=" + B + ", salt=" + salt + ", token=" + token + "]";
	}
}
