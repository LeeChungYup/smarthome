package com.keti.homeservice.item.phone;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 시간을 가져오는 클래스
 */
public class PhoneTime {

	private long now;
	private Date date;
	private String time;
	
	public String getTime(){
		
		now = System.currentTimeMillis();
		date = new Date(now);
		SimpleDateFormat sdfnow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    time = sdfnow.format(date);
		  
		return time;
	}
}
