package de.app.model.form;

public class FormMisc {

	String currentUserEmail;
	Long currentUserId;
	
	String newFriendEmail;
	String newFriendId;
	public String getCurrentUserEmail() {
		return currentUserEmail;
	}
	public void setCurrentUserEmail(String currentUserEmail) {
		this.currentUserEmail = currentUserEmail;
	}
	public Long getCurrentUserId() {
		return currentUserId;
	}
	public void setCurrentUserId(Long currentUserId) {
		this.currentUserId = currentUserId;
	}
	public String getNewFriendEmail() {
		return newFriendEmail;
	}
	public void setNewFriendEmail(String newFriendEmail) {
		this.newFriendEmail = newFriendEmail;
	}
	public String getNewFriendId() {
		return newFriendId;
	}
	public void setNewFriendId(String newFriendId) {
		this.newFriendId = newFriendId;
	}

	@Override
	public String toString() {
		return "FormMisc [currentUserEmail=" + currentUserEmail + ", currentUserId=" + currentUserId
				+ ", newFriendEmail=" + newFriendEmail + ", newFriendId=" + newFriendId + "]";
	}
	
	 

}
