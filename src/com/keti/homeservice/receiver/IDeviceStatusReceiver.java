package com.keti.homeservice.receiver;

import com.keti.homeservice.item.Device;
import com.keti.homeservice.item.HomeAppliance;
import com.keti.homeservice.item.KWSensor;

public interface IDeviceStatusReceiver {
	/**
	 * to update device's current status form home server
	 * 
	 * @param device that has basic device.
	 * */
	void onReceiveDeviceStatus(Device device);
	
	/**
	 * to update device's status that was changed from home server.
	 * 
	 * @param device that has basic device.
	 * */
	void onChangeDeviceStatus(Device device);
	
	// for test
	void onReceiveDeviceStatus(HomeAppliance homeAppliance);

	void onChangeDeviceStatus(HomeAppliance homeAppliance);
	
	void onChangeDeviceStatus(KWSensor sensor);

}
