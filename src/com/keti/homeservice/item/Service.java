package com.keti.homeservice.item;

/**
 * This class defines DB schema information about service(scenario).
 * 
 * */
public class Service {
	private String serviceId;
	private String serviceName;
	private String serviceVersion;
	private String serviceInfo;
	private String category;
	private String requisiteDevices;
	private String regTime;
	private String rule;

	private boolean isInstalled;
	private boolean isUsable;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(String serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRequisiteDevices() {
		return requisiteDevices;
	}

	public void setRequisiteDevices(String requisiteDevices) {
		this.requisiteDevices = requisiteDevices;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public void setUsable(boolean isUsable) {
		this.isUsable = isUsable;
	}

	public boolean isUsable() {
		return isUsable;
	}

	public boolean isInstalled() {
		return isInstalled;
	}

	public void setInstalled(boolean isInstalled) {
		this.isInstalled = isInstalled;
	}
	
	@Override
	public String toString() {
		return "ID: " + serviceId + "\n" + 
				"Name: " + serviceName + "\n" + 
				"Version: " + serviceVersion + "\n" + 
				"Info: " + serviceInfo + "\n" + 
				"Cate.: " + category + "\n" + 
				"Devices: " + requisiteDevices + "\n" + 
				"RegTime: " + regTime + "\n" +
				"rule: " + rule + "\n";
	}

}
