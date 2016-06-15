package com.keti.homeservice.item;

/**
 * This class defines value about temperature and humidity.
 * */
public class ThermoHygrometh implements IDeviceFunc {

	private float temperature;
	private float humidity;

	public ThermoHygrometh(float temperature, float humidity) {
		this.temperature = temperature;
		this.humidity = humidity;
	}
	
	public float getTemperature() {
		return temperature;
	}

	public float getHumidity() {
		return humidity;
	}
}
