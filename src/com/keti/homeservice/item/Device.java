package com.keti.homeservice.item;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.keti.homeservice.util.Constants;

/**
 * This class defines DB schema information about device.
 * 
 * */
public class Device {
	private String serialNumber;
	private String deviceType;
	private String modelName;
	private String manufacturer;
	private String size;
	private String weight;
	private String controlType;
	private String location;
	private String power;
	private String regTime;

	// general function
	private boolean onOffStatus;

	// specific function
	private Object func;

	public Device(){};
	
	public Device(String serialNumber, String deviceType, String modelName, String manufacturer, String size,
			String weight, String controlType, String location) {
		this.serialNumber = serialNumber;
		this.deviceType = deviceType;
		this.modelName = modelName;
		this.manufacturer = manufacturer;
		this.size = size;
		this.weight = weight;
		this.controlType = controlType;
		this.location = location;
		
	}
	
	public Device(String serialNumber, String deviceType, String modelName, String manufacturer, String size,
			String weight, String controlType, String location, String power, String regTime) {
		this.serialNumber = serialNumber;
		this.deviceType = deviceType;
		this.modelName = modelName;
		this.manufacturer = manufacturer;
		this.size = size;
		this.weight = weight;
		this.controlType = controlType;
		this.location = location;
		this.power = power;
		this.regTime = regTime;
	}

	public Device(String serialNumber, String location, boolean onOffStatus) {
		this.serialNumber = serialNumber;
		this.location = location;
		this.onOffStatus = onOffStatus;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public String getModelName() {
		return modelName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getSize() {
		return size;
	}

	public String getWeight() {
		return weight;
	}

	public String getControlType() {
		return controlType;
	}

	public String getLocation() {
		return location;
	}

	public String getRegTime() {
		return regTime;
	}

	public boolean isOnOffStatus() {
		return onOffStatus;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setPower(String power) {
		this.power = power;
	}
	
	public String getPower() {
		return power;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public void setOnOffStatus(boolean onOffStatus) {
		this.onOffStatus = onOffStatus;
	}

	public void setFunc(Object func) {
		this.func = func;
	}
	
	public IDeviceFunc getFunc() {
		Gson gson = new GsonBuilder().create();
		if(deviceType.equals(Constants.DEV_TYPE_THERMO_HYGROMETH)) {
			ThermoHygrometh th = gson.fromJson(gson.toJson(func), ThermoHygrometh.class);
			return th;
		} else if(deviceType.equals(Constants.DEV_TYPE_AIR_QUALITY)) {
			ThermoHygrometh th = gson.fromJson(gson.toJson(func), ThermoHygrometh.class);
			return th;
		}
		
		return null;
	}
	
	public Object getKWFunc() {
		return func;
	}

	@Override
	public String toString() {
		return "시리얼넘버\t\t\t: " + serialNumber + "\n"  + 
				"디바이스 타입\t: " + deviceType + "\n" + 
				"모델명\t\t\t\t\t\t: " + modelName + "\n" + 
				"제조사\t\t\t\t\t\t: " + manufacturer + "\n" + 
				"크기\t\t\t\t\t\t\t: " + size + "\n" + 
				"무게\t\t\t\t\t\t\t: " + weight + "\n" + 
				"제어 종류\t\t\t\t: " + controlType + "\n" + 
				"위치\t\t\t\t\t\t\t: " + location + "\n" + 
				"전력\t\t\t\t\t\t\t: " + power + "\n" +
				"등록일자\t\t\t\t: " + regTime + "\n";
	}

}
