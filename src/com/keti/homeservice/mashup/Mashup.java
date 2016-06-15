package com.keti.homeservice.mashup;

import java.util.ArrayList;
import java.util.List;

import com.keti.homeservice.item.Device;
import com.keti.homeservice.item.Service;

import android.util.Log;

public class Mashup {
	private static final String TAG = "Mashup";
	
	private static final String PAR_TOKEN = "/";

	public List<Service> searchUsableService(List<Device> devices, List<Service> services) {
		List<Service> usableServices = new ArrayList<Service>();

		if(devices == null || services == null) {
			return usableServices;
		}
		
		Log.i(TAG, "**start to search usable service");
		
		boolean equalFlag = false;
		for(Service service : services) {
			String[] deviceTypes = service.getRequisiteDevices().split(PAR_TOKEN);
			for(String deviceType : deviceTypes) {
				for(Device device : devices) {
					String type = device.getDeviceType();
					
					if(deviceType.equals(type)) {
						equalFlag = true;
						break;
					} else {
						equalFlag = false;
					}
				}
			}
			
			if(equalFlag) {
				Log.i(TAG, "usable service: " + service.getServiceName());
				usableServices.add(service);		
			}
		}
		
		Log.i(TAG, "**end to search usable service");
		
		return usableServices;
	}
}
