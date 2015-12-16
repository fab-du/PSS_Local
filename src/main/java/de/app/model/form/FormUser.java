package de.app.model.form;

public class FormUser extends FormCurrentUser {

	Long id;
	String username;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "FormUser [id=" + id + ", username=" + username + "]";
	}

}
