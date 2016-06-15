package com.keti.homeservice.receiver;

import com.keti.homeservice.item.Device;

public interface IQRCodeReceiver {
	public void onGetDeviceInfo(Device homeappliance);
}
