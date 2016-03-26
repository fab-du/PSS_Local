package de.app.model;

public class UserGroup extends AbstractEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long useringroupId;

	private long groupId;

	/*
	 * User copy of the group sym. key encrypt with user pub. key
	 */
	
	KeySym keysym;

	boolean isGroupLead;

	User users = new User();

	Group groups = new Group();

	public long getUseringroupId() {
		return useringroupId;
	}

	public void setUseringroupId(long useringroupId) {
		this.useringroupId = useringroupId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public KeySym getKeysym() {
		return keysym;
	}

	public void setKeysym(KeySym keysym) {
		this.keysym = keysym;
	}

	public boolean isGroupLead() {
		return isGroupLead;
	}

	public void setGroupLead(boolean isGroupLead) {
		this.isGroupLead = isGroupLead;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public Group getGroups() {
		return groups;
	}

	public void setGroups(Group groups) {
		this.groups = groups;
	}
	
}
