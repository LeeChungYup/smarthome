package com.keti.homeservice.item;

public class BasicUser {

	private String username;
	private String password;
	private String passwordConfirm;

	public BasicUser(String username, String password, String passwordConfirm) {
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}
	
	public boolean isCorrect() {
		
		if(password.equals(passwordConfirm)) {
			return true;
		}
		
		return false;
	}
	
	public boolean idNullCheack() {
		if(!username.equals("")) {
			return true;
		}
		
		return false;
	}
	
	public boolean pwNullCheack() {
		if(!password.equals("") && !passwordConfirm.equals("")) {
			return true;
		}
		
		return false;
	}
	
	public String getUsername() {
		return username;
	}
	
	@Override
	public String toString() {
		return "id: " + username;
	}
	

}
