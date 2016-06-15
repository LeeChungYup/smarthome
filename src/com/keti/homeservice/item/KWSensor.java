package com.keti.homeservice.item;

public class KWSensor {
	protected String location;
	protected int temperature;
	protected int humidity;
	protected int luminance;
	protected int co2;
	
	public int getTemperature() {
		return temperature;
	}

	public int getHumidity() {
		return humidity;
	}

	public int getLuminance() {
		return luminance;
	}

	public int getCo2() {
		return co2;
	}

}
