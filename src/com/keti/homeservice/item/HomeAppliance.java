package com.keti.homeservice.item;

public class HomeAppliance extends KWSensor {
	private String username;	// user name
	private String usstate;		// user state : watching TV / cooking / sleeping / washing / none
	private String uslocation;	// user location
	private byte homeapp1;		// home appliance id
	private String homeapp2;	// home appliance name
	private String app_state;	// home appliance state: on/off
	private int app_ele;		// measuring amount of electricity used
	private String date;
	private String day;
	private String time;

	
	public String getUsername() {
		return username;
	}

	public String getUsstate() {
		return usstate;
	}
	
	public String getUslocation() {
		return uslocation;
	}

	public byte getHomeapp1() {
		return homeapp1;
	}

	public String getHomeapp2() {
		return homeapp2;
	}

	public boolean getApp_state() {
		if("ON".equals(app_state)) {
			return true;
		} else if("OFF".equals(app_state)) {
			return false;
		}
		
		return false;
	}

	public int getApp_ele() {
		return app_ele;
	}

	public String getDate() {
		return date;
	}

	public String getDay() {
		return day;
	}

	public String getTime() {
		return time;
	}
}
