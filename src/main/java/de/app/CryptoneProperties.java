package de.app;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("remoteserver.uri")
public class CryptoneProperties {

	String challenge;
	String authenticate;
	String logout;
	String register;
	
	String users;
	String documents;
	String groups;
	String friends;
	String keypairs;
	String symkeys;
	String url;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getKeypairs() {
		return keypairs;
	}
	public void setKeypairs(String keypairs) {
		this.keypairs = keypairs;
	}
	public String getSymkeys() {
		return symkeys;
	}
	public void setSymkeys(String symkeys) {
		this.symkeys = symkeys;
	}
	public String getChallenge() {
		return challenge;
	}
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}
	public String getAuthenticate() {
		return authenticate;
	}
	public void setAuthenticate(String authenticate) {
		this.authenticate = authenticate;
	}
	public String getLogout() {
		return logout;
	}
	public void setLogout(String logout) {
		this.logout = logout;
	}
	public String getRegister() {
		return register;
	}
	public void setRegister(String register) {
		this.register = register;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public String getDocuments() {
		return documents;
	}
	public void setDocuments(String documents) {
		this.documents = documents;
	}
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	public String getFriends() {
		return friends;
	}
	public void setFriends(String friends) {
		this.friends = friends;
	}
	
}
