package com.keti.homeservice.receiver;

public interface IMessageReceiver {
	/**
	 * to update message about progressing context of service.
	 * 
	 * @param message about progressing context of service.
	 * */
	void onSrvInstallMessage(String message);

	/**
	 * to update message about status of device or service.
	 * 
	 * @param message about status of device or service.
	 * */
	void onStatusMessage(String message);
}
