package de.app.model.form;

public class FormCurrentUser {

	String currentUserName;
	String currentUserId;


	public String getCurrentUserName() {
		return currentUserName;
	}
	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}
	public String getCurrentUserId() {
		return currentUserId;
	}
	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	@Override
	public String toString() {
		return "FormCurrentUser [currentUserName=" + currentUserName + ", currentUserId=" + currentUserId + "]";
	}

}
