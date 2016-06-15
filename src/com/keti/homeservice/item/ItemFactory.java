package com.keti.homeservice.item;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.keti.homeservice.msg.HeaderMsg;
import com.keti.homeservice.msg.NotiMsg;
import com.keti.homeservice.msg.ResMsg;
import com.keti.homeservice.util.Constants;

import android.util.Log;

public class ItemFactory {
	private static final String TAG = "ItemFactory";
	
	private Gson mGson;

	public ItemFactory() {
		mGson = new GsonBuilder().create();
	}
	
	public Device[] assembleDevices(Object result) {
		Device[] devices = null;

		try {
			devices = mGson.fromJson(mGson.toJson(result), Device[].class);

		} catch (JsonSyntaxException e) {
			Log.e(TAG, e.getMessage());
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage());
		}

		return devices;
	}
	
	public Device[] assembleDevices(List<Map<String, Object>> devList) {
		Device[] devices = new Device[devList.size()];

		for(int i=0; i<devList.size(); i++) {
			Device d = new Device();
			d.setSerialNumber((String) devList.get(i).get("serialNumber"));
			d.setDeviceType((String) devList.get(i).get("deviceType"));
			d.setModelName((String) devList.get(i).get("modelName"));
			d.setControlType((String) devList.get(i).get("controlType"));
			d.setLocation((String) devList.get(i).get("location"));
			d.setManufacturer((String) devList.get(i).get("manufacturer"));
			d.setRegTime((String) devList.get(i).get("regTime"));
			
			devices[i] = d;
		}
		
		return devices;
	}
	
	public PhoneInfo[] assemblePhoneInfo(List<Map<String, Object>> userList) {
		PhoneInfo[] userInfo = new PhoneInfo[userList.size()];
		
		for(int i=0; i<userList.size(); i++) {
			PhoneInfo u = new PhoneInfo();
			u.setAddress((String) userList.get(i).get("address"));
			u.setTime((String) userList.get(i).get("time"));
			u.setState((String) userList.get(i).get("state"));
			u.setCallState((String) userList.get(i).get("callState"));
			u.setLatitude(Double.parseDouble((String) userList.get(i).get("latitude")));
			u.setLongitude(Double.parseDouble((String) userList.get(i).get("longitude")));
			
			userInfo[i] = u;
		}
		
		return userInfo;
	}

	public Service[] assembleServices(Object result) {
		Service[] services = null;

		try {
			services = mGson.fromJson(mGson.toJson(result), Service[].class);
		} catch (JsonSyntaxException e) {
			Log.e(TAG, e.getMessage());
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage());
		}

		return services;
	}
	
	public BasicUser[] assembleUsers(Object result) {
		BasicUser[] users = null;

		try {
			users = mGson.fromJson(mGson.toJson(result), BasicUser[].class);
		} catch (JsonSyntaxException e) {
			Log.e(TAG, e.getMessage());
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage());
		}

		return users;
	}
	
	public BasicUser assembleUser(Object result) {
		BasicUser user = null;

		try {
			user = mGson.fromJson(mGson.toJson(result), BasicUser.class);
		} catch (JsonSyntaxException e) {
			Log.e(TAG, e.getMessage());
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage());
		}

		return user;
	}
	
	public Service[] assembleServices(List<Map<String, Object>> srvList) {
		Service[] services = new Service[srvList.size()];

		for(int i=0; i<srvList.size(); i++) {
			Service s = new Service();
			
			s.setServiceId((String) srvList.get(i).get("serviceId"));
			s.setServiceName((String) srvList.get(i).get("serviceName"));
			s.setServiceVersion((String) srvList.get(i).get("serviceVersion"));
			s.setServiceInfo((String) srvList.get(i).get("serviceInfo"));
			s.setCategory((String) srvList.get(i).get("category"));
			s.setRequisiteDevices((String) srvList.get(i).get("requisiteDevices"));
			s.setRegTime((String) srvList.get(i).get("regTime"));
			
			services[i] = s;
		}
		
		return services;
	}

	public Device assembleDevice(String result) {
		Device device = null;

		try {
			device = mGson.fromJson(result, Device.class);
		} catch (JsonSyntaxException e) {
			Log.e(TAG, e.getMessage());
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage());
		}

		return device;
	}
	
	public Device assembleDevice(Object result) {
		Device device = null;

		try {
			device = mGson.fromJson(mGson.toJson(result), Device.class);
		} catch (JsonSyntaxException e) {
			Log.e(TAG, e.getMessage());
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage());
		}

		return device;
	}
	
	public Device assembleAirQuality(Object result) {
		Device d = assembleDevice(result);
		
		if(Constants.DEV_TYPE_AIR_QUALITY.equals(d.getDeviceType())) {
			return d;
		}
		
		return null;
	}
	
	public HomeAppliance assembleHomeAppliance(Object result) {
		Device d = assembleDevice(result);
		
		HomeAppliance homeAppliance = null;
		
		if("HomeAppliance".equals(d.getDeviceType())) {
			try {
				homeAppliance = mGson.fromJson(mGson.toJson(d.getKWFunc()), HomeAppliance.class);
			} catch (JsonSyntaxException e) {
				Log.e(TAG, e.getMessage());
			} catch (JsonParseException e) {
				Log.e(TAG, e.getMessage());
			}
		}
		
		return homeAppliance;
	}
	
	public KWSensor assembleSensor(Object result) {
		Device d = assembleDevice(result);
		
		KWSensor sensor = null;
		
		if("Sensor".equals(d.getDeviceType())) {
			try {
				sensor = mGson.fromJson(mGson.toJson(d.getKWFunc()), KWSensor.class);
			} catch (JsonSyntaxException e) {
				Log.e(TAG, e.getMessage());
			} catch (JsonParseException e) {
				Log.e(TAG, e.getMessage());
			}
		}
		
		return sensor;
	}

	public Service assembleService(Object result) {
		Service service = null;

		try {
			service = mGson.fromJson(mGson.toJson(result), Service.class);
		} catch (JsonSyntaxException e) {
			Log.e(TAG, e.getMessage());
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage());
		}

		return service;
	}
	
	public SmartBandInfo assembleSmartband(Object result) {
		SmartBandInfo smartband = null;

		try {
			smartband = mGson.fromJson(mGson.toJson(result), SmartBandInfo.class);
		} catch (JsonSyntaxException e) {
			Log.e(TAG, e.getMessage());
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage());
		}

		return smartband;
	}
	
	public HeaderMsg assembleHeaderMsg(String message) {
		HeaderMsg headerMessage = null;

		try {
			headerMessage = mGson.fromJson(message, HeaderMsg.class);
		} catch (JsonSyntaxException e) {
			Log.e(TAG, e.getMessage());
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage());
		}

		return headerMessage;
	}
	
	public HomeAppliance assembleHomeAppliance(String message ) {
		HomeAppliance homeappliance = null;

		try {
			homeappliance = mGson.fromJson(message, HomeAppliance.class);
		} catch (JsonSyntaxException e) {
			Log.e(TAG, e.getMessage());
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage());
		}

		return homeappliance;
	}

	public NotiMsg assembleNotiMsg(String message) {
		NotiMsg notiMessage = null;

		try {
			notiMessage = mGson.fromJson(message, NotiMsg.class);
		} catch (JsonSyntaxException e) {
			Log.e(TAG, e.getMessage());
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage());
		}

		return notiMessage;
	}
	
	public ResMsg assembleRspMsg(String message) {
		ResMsg responseMessage = null;

		try {
			responseMessage = mGson.fromJson(message, ResMsg.class);
		} catch (JsonSyntaxException e) {
			Log.e(TAG, e.getMessage());
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage());
		}

		return responseMessage;
	}

	public String toJsonMsg(Object src) {
		return mGson.toJson(src);
	}

}
