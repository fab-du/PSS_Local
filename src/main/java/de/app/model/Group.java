package de.app.model;

import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
public class Group extends AbstractEntity{
	
	
	
	public Group(String name, Long gvid, KeySym groupkey) {
		this.name = name;
		this.gvid = gvid;
		this.groupkey = groupkey;
	}

	@NotNull
	String name;

	Long gvid;
	
	KeySym groupkey;

	public Group() {}
	
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

	@Override
	public String toString() {
		return "Group [name=" + name + ", gvid=" + gvid + "]";
	}
	
}
