package com.keti.homeservice.item;

public class Schedule {
	private int scheduleid;
	private String username;
	private String title;
	private String date;
	private String time;
	private String memo;

	public Schedule(String username, String title, String date, String time, String memo) {
		this.username = username;
		this.title = title;
		this.date = date;
		this.time = time;
		this.memo = memo;
	}

	public Schedule(int scheduleid, String username, String title, String date, String time, String memo) {
		this.scheduleid = scheduleid;
		this.username = username;
		this.title = title;
		this.date = date;
		this.time = time;
		this.memo = memo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public int getScheduleid() {
		return scheduleid;
	}

	public void setScheduleid(int scheduleid) {
		this.scheduleid = scheduleid;
	}

}
