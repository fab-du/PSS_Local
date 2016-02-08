package de.app.model;

public class Group extends AbstractEntity{
	
	String name;
	
	Long gvid;
	
	KeySym groupkey;

	public Group() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGvid() {
		return gvid;
	}

	public void setGvid(Long gvid) {
		this.gvid = gvid;
	}

	public KeySym getGroupkey() {
		return groupkey;
	}

	public void setGroupkey(KeySym groupkey) {
		this.groupkey = groupkey;
	}
}
