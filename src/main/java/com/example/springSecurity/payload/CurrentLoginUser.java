package com.example.springSecurity.payload;

public class CurrentLoginUser {
	private String username;
	private String email;
	
	public CurrentLoginUser(String username, String email) {
		this.username = username;
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
