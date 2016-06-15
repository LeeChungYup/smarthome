package com.keti.homeservice.item;


public class PhoneInfo {
	private String userName = "user2";
	private double latitude; //����
	private double longitude; //�浵 
	private String address; //�ѱ��ּ�
	private String time; // ����ð� 
	private String state; //���� �Ҹ� ����
	private String callState; //��ȭ����

	public PhoneInfo(){};
	
	public PhoneInfo(double latitude, double longitude, String address, String time,
			String state, String callstate) {

		this.latitude = latitude;
		this.longitude = longitude;
		
		if(address == null) {
			this.address = new String("");
		} else {
			this.address = address;	
		}
		
		if(time == null) {
			this.time = new String();
		} else {
			this.time = time;	
		}
		
		if(state == null) {
			this.state = new String();
		} else {
			this.state = state;	
		}
		
		if(callstate == null) {
			this.callState = new String();
		} else {
			this.callState = callstate;	
		}
	}
	
	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getAddress() {
		return address;
	}

	public String getTime() {
		return time;
	}

	public String getState() {
		return state;
	}

	public String getCallState() {
		return callState;
	}	
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCallState(String callState) {
		this.callState = callState;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return  "userName: " + userName + "\n" +
				"address: " + address + "\n" +
				"time: " + time + "\n" +
				"state: " + state + "\n" +
				"callState: " + callState + "\n" +
				"latitude: " + latitude + "\n" +
				"longitude: " + longitude;
	}
}
