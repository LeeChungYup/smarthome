package com.keti.homeservice.item;

public class SmartBandInfo {

	private int step;
	private double kcal;
	private float bodyTemperature;
	private int fallDetect;
	private float heartRate;
	private int state;

	public SmartBandInfo(){}
	
	public SmartBandInfo(int step, double kcal, float bodyTemperature, int fallDetect, float heartRate) {
		this.step = step;
		this.kcal = kcal;
		this.bodyTemperature = bodyTemperature;
		this.fallDetect = fallDetect;
		this.heartRate = heartRate;
	}
	
	public void update(int step, double kcal, float bodyTemperature, int fallDetect, float heartRate, int state) {
		this.step = step;
		this.kcal = kcal;
		this.bodyTemperature = bodyTemperature;
		this.fallDetect = fallDetect;
		this.heartRate = heartRate;
		this.state = state;
	}
	
	public int getStep() {
		return step;
	}
	
	public float getHeartRate() {
		return heartRate;
	}
	
	public float getBodyTemperature() {
		return bodyTemperature;
	}

	public int getFallDetect() {
		return fallDetect;
	}
	
	public int getState() {
		return state;
	}
	
	@Override
	public String toString() {
		return "kcal: " + kcal + ", " +
				"step: " + step + ", " +
				"body temperature: " + bodyTemperature + ", " +
				"fall detect: " + fallDetect + ", " +
				"heart rate: " + heartRate + ", " +
				"state: " + state;
	}
}
