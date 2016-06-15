package com.keti.homeservice.receiver;

import com.keti.homeservice.item.Service;

public interface IServiceStatusReceiver {
	/**
	 * to update currently running service.
	 * 
	 * @param service that has service information.
	 * */
	void onAddInstallService(Service service);

	/**
	 * to update started service.
	 * 
	 * @param service that has service information.
	 * */
	void onStartService(Service service);

	/**
	 * to update stopped service.
	 * 
	 * @param service that has service information.
	 * */
	void onStopService(Service service);
}
