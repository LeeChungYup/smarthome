package com.keti.homeservice.receiver;

import java.util.List;

import com.keti.homeservice.item.Service;

public interface IMashupReceiver {
	/**
	 * to check if the service registered by user is valid.
	 * 
	 * @param service that has service's information.
	 * */
	void onReceiveServiceInfo(Service service);

	/**
	 * to update all of services' information that mash up device and service.
	 * 
	 * @param services that has all of usable services' information.
	 * */
	void onReceiveUsableServiceInfo(List<Service> services);

	/**
	 * to update all of services' information that is registered in Mashup-DB.
	 * 
	 * @param services that has all of services' information.
	 * */
	void onReceiveAllServiceInfo(List<Service> services);
}
