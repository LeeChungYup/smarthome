package com.keti.homeservice.receiver;

import com.keti.homeservice.item.BasicUser;
import com.keti.homeservice.item.SmartBandInfo;

public interface IUserReceiver {
	public void onAddUser(BasicUser user);
	
	public void onUsers(BasicUser[] users);
	
	public void onEmergency(SmartBandInfo smartband);
}
