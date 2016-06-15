package com.keti.homeservice.receiver;

import com.keti.homeservice.activity.DeviceAdapter;
import com.keti.homeservice.activity.MainServiceAdapter;
import com.keti.homeservice.item.Device;

public interface IBottomInfoReceiver {
	/**
	 * to update temperature and humidity at the bottom of screen.
	 * 
	 * @param device that has basic device.
	 * */
	void updateBasicInfo(Device device);

	/**
	 * to update devices' information at the bottom of screen.
	 * 
	 * @param daviceAdapter that has all of devices' information.
	 * */
	void updateMainDevice(DeviceAdapter daviceAdapter);

	/**
	 * to update services' information at the bottom of screen.
	 * 
	 * @param serviceAdapter that has all of services' information.
	 * */
	void updateMainService(MainServiceAdapter serviceAdapter);
}
