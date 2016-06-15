package com.keti.homeservice.item;

public class User {
	private static String id;
	private static String password;
	
	public static void setPassword(String password) {
		User.password = password;
	}
	
	public static void setID(String id) {
		User.id = id;
	}
	
	public static String getID() {
		return id;
	}
	
	public static String getPassword() {
		return password;
	}
}
