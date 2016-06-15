package com.keti.homeservice.activity;

import com.keti.homeservice.R;
import com.keti.homeservice.control.HomeServiceController;
import com.keti.homeservice.item.Device;
import com.keti.homeservice.item.HomeAppliance;
import com.keti.homeservice.item.HomeApplianceInfo;
import com.keti.homeservice.item.KWSensor;
import com.keti.homeservice.item.ThermoHygrometh;
import com.keti.homeservice.receiver.IBottomInfoReceiver;
import com.keti.homeservice.receiver.IDeviceStatusReceiver;
import com.keti.homeservice.util.Constants;
import com.keti.homeservice.util.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

/**
 * This class displays information and user interface about device for user.
 * */
public class DeviceControlActivity extends Activity {
	private TextView basicInfoText;

	private DeviceAdapter mainDeviceAdapter;
	private DeviceAdapter kitchen;
	private DeviceAdapter bedroom1;
	private DeviceAdapter bedroom2;
	private DeviceAdapter bathroom;
	private DeviceAdapter livingroom;

	private CenterLockHorizontalScrollview mainServiceHView;
	private CenterLockHorizontalScrollview mainDeviceHView;
	private CenterLockHorizontalScrollview livingroomHView;
	private CenterLockHorizontalScrollview bedroomoneHView;
	private CenterLockHorizontalScrollview bedroomtwoHView;
	private CenterLockHorizontalScrollview kitchenHView;
	private CenterLockHorizontalScrollview bathroomHView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.device_control);

		basicInfoText = (TextView) findViewById(R.id.basic_info);

		mainServiceHView = (CenterLockHorizontalScrollview) findViewById(R.id.main_srv_list);
		mainDeviceHView = (CenterLockHorizontalScrollview) findViewById(R.id.main_dev_list);
		livingroomHView = (CenterLockHorizontalScrollview) findViewById(R.id.livingroom_dev_list);
		bedroomoneHView = (CenterLockHorizontalScrollview) findViewById(R.id.bedroom1_dev_list);
		bedroomtwoHView = (CenterLockHorizontalScrollview) findViewById(R.id.bedroom2_dev_list);
		kitchenHView = (CenterLockHorizontalScrollview) findViewById(R.id.kitchen_dev_list);
		bathroomHView = (CenterLockHorizontalScrollview) findViewById(R.id.bathroom_dev_list);

		mainDeviceAdapter = new DeviceAdapter(getLayoutInflater());
		livingroom = new DeviceAdapter(getLayoutInflater());
		bedroom1 = new DeviceAdapter(getLayoutInflater());
		bedroom2 = new DeviceAdapter(getLayoutInflater());
		kitchen = new DeviceAdapter(getLayoutInflater());
		bathroom = new DeviceAdapter(getLayoutInflater());

		mainDeviceHView.setAdapter(this, mainDeviceAdapter);
		livingroomHView.setAdapter(this, livingroom);
		bedroomoneHView.setAdapter(this, bedroom1);
		bedroomtwoHView.setAdapter(this, bedroom2);
		kitchenHView.setAdapter(this, kitchen);
		bathroomHView.setAdapter(this, bathroom);

		HomeServiceController.getInstance().addInformationReceiver(mIDeviceStatusReceiver);
		HomeServiceController.getInstance().addInformationReceiver(IBottomInfoReceiver);
		HomeServiceController.getInstance().requestAllDeviceInfo();
	}

	IBottomInfoReceiver IBottomInfoReceiver = new IBottomInfoReceiver() {

		@Override
		public void updateMainDevice(DeviceAdapter daviceAdaper) {
			mainDeviceHView.setAdapter(DeviceControlActivity.this, daviceAdaper);
		}

		@Override
		public void updateMainService(MainServiceAdapter serviceAdapter) {
			mainServiceHView.setAdapter(DeviceControlActivity.this, serviceAdapter);
		}

		@Override
		public void updateBasicInfo(Device device) {
			String deviceType = device.getDeviceType();
			if (deviceType.equals(Constants.DEV_TYPE_THERMO_HYGROMETH)) {
				ThermoHygrometh th = (ThermoHygrometh) device.getFunc();
				float tmp = th.getTemperature();
				float hum = th.getHumidity();

				basicInfoText.setText(String.format(getString(R.string.tem_humd), tmp, hum));
			} else if (deviceType.equals(Constants.DEV_TYPE_AIR_QUALITY)) {
				ThermoHygrometh th = (ThermoHygrometh) device.getFunc();
				float tmp = th.getTemperature();
				float hum = th.getHumidity();

				basicInfoText.setText(String.format(getString(R.string.tem_humd), tmp, hum));
			}
		}

	};

	IDeviceStatusReceiver mIDeviceStatusReceiver = new IDeviceStatusReceiver() {

		@Override
		public void onReceiveDeviceStatus(Device deviceInfo) {
			String location = deviceInfo.getLocation();

			if (location.equals(Constants.LOC_LIVINGROOM)) {
				livingroom.addDevice(deviceInfo);
				livingroomHView.setAdapter(DeviceControlActivity.this, livingroom);

				mainDeviceAdapter.addDevice(deviceInfo);
				HomeServiceController.getInstance().dispatchMainDevice(mainDeviceAdapter);
			} else if (location.equals(Constants.LOC_BEDROOM_ONE)) {
				bedroom1.addDevice(deviceInfo);

				bedroomoneHView.setAdapter(DeviceControlActivity.this, bedroom1);
			} else if (location.equals(Constants.LOC_BEDROOM_TWO)) {
				bedroom2.addDevice(deviceInfo);

				bedroomtwoHView.setAdapter(DeviceControlActivity.this, bedroom2);
			} else if (location.equals(Constants.LOC_KITCHEN)) {
				kitchen.addDevice(deviceInfo);

				kitchenHView.setAdapter(DeviceControlActivity.this, kitchen);
			} else if (location.equals(Constants.LOC_BATHROOM)) {
				bathroom.addDevice(deviceInfo);

				bathroomHView.setAdapter(DeviceControlActivity.this, bathroom);
			}

		}

		@Override
		public void onChangeDeviceStatus(Device deviceInfo) {
			String location = deviceInfo.getLocation();

			if (location.equals(Constants.LOC_LIVINGROOM)) {

				livingroom.exchangeDevice(deviceInfo);
				mainDeviceAdapter.exchangeDevice(deviceInfo);

				HomeServiceController.getInstance().dispatchMainDevice(mainDeviceAdapter);

				livingroomHView.setAdapter(DeviceControlActivity.this, livingroom);
				mainDeviceHView.setAdapter(DeviceControlActivity.this, mainDeviceAdapter);
			} else if (location.equals(Constants.LOC_BEDROOM_ONE)) {

				bedroom1.exchangeDevice(deviceInfo);
				bedroomoneHView.setAdapter(DeviceControlActivity.this, bedroom1);
			} else if (location.equals(Constants.LOC_BEDROOM_TWO)) {

				bedroom2.exchangeDevice(deviceInfo);
				bedroomtwoHView.setAdapter(DeviceControlActivity.this, bedroom2);
			} else if (location.equals(Constants.LOC_KITCHEN)) {

				kitchen.exchangeDevice(deviceInfo);
				kitchenHView.setAdapter(DeviceControlActivity.this, kitchen);
			} else if (location.equals(Constants.LOC_BATHROOM)) {

				bathroom.exchangeDevice(deviceInfo);
				bathroomHView.setAdapter(DeviceControlActivity.this, bathroom);
			}

			String deviceType = deviceInfo.getDeviceType();
			if (deviceType.equals(Constants.DEV_TYPE_THERMO_HYGROMETH)) {
				HomeServiceController.getInstance().dispatchMainInfo(deviceInfo);
			} else if (deviceType.equals(Constants.DEV_TYPE_AIR_QUALITY)) {
				HomeServiceController.getInstance().dispatchMainInfo(deviceInfo);
			}

			HomeServiceController.getInstance().dispatchStatusMessage(Utils.getInstance().getHomeServerMsg(deviceInfo));
		}

		@Override
		public void onReceiveDeviceStatus(HomeAppliance homeAppliance) {
			
		}

		@Override
		public void onChangeDeviceStatus(HomeAppliance homeAppliance) {
//			int deviceId = homeAppliance.getHomeapp1();
			String deviceName = homeAppliance.getHomeapp2();

			if(deviceName != null) {
				changeDeviceStatus(deviceName, homeAppliance);
			}
		}

		@Override
		public void onChangeDeviceStatus(KWSensor sensor) {
			Log.d("SENSOR", "humidity: " + sensor.getHumidity());
			Log.d("SENSOR", "co2: " + sensor.getCo2());
			Log.d("SENSOR", "liminance: " + sensor.getLuminance());
			Log.d("SENSOR", "temperature: " + sensor.getTemperature());
		}

	};
	
	private void changeDeviceStatus(String deviceName, HomeAppliance homeAppliance) {
		Device d = null;
		
		if(HomeApplianceInfo.HOMEAPPLIANCE_AC_NAME.equals(deviceName)) {
			d = new Device (
					"ac_name", 
					Constants.DEV_TYPE_AC,
					"modelName",
					"manuKETI",
					"12",
					"34",
					Constants.CONTROL_TYPE_TCP,
					Constants.LOC_LIVINGROOM,
					"power100",
					homeAppliance.getDate() + " / " + homeAppliance.getDay() + " / " + homeAppliance.getTime());
			d.setOnOffStatus(homeAppliance.getApp_state());

			arrangeHameAppliance(d);
			
		} else if(HomeApplianceInfo.HOMEAPPLIANCE_HEATER_NAME.equals(deviceName)) {
			d = new Device (
					"heater_name", 
					Constants.DEV_TYPE_HEATER,
					"model_kw",
					"manu_kw",
					"12",
					"34",
					Constants.CONTROL_TYPE_TCP,
					Constants.LOC_BEDROOM_ONE,
					"power100",
					homeAppliance.getDate() + " / " + homeAppliance.getDay() + " / " + homeAppliance.getTime());
			d.setOnOffStatus(homeAppliance.getApp_state());
			
			arrangeHameAppliance(d);
			
		} else if(HomeApplianceInfo.HOMEAPPLIANCE_TV_NAME.equals(deviceName)) {
			d = new Device (
					"tv_name12321424", 
					Constants.DEV_TYPE_TV,
					"model_kw",
					"manu_kw",
					"12",
					"34",
					Constants.CONTROL_TYPE_TCP,
					Constants.LOC_LIVINGROOM,
					"power100",
					homeAppliance.getDate() + " / " + homeAppliance.getDay() + " / " + homeAppliance.getTime());
			d.setOnOffStatus(homeAppliance.getApp_state());
		
			arrangeHameAppliance(d);
			
		} else if(HomeApplianceInfo.HOMEAPPLIANCE_WASHINGMACHINE_NAME.equals(deviceName)) {
			d = new Device (
					"washingmachine_name", 
					Constants.DEV_TYPE_WASHINGMACHINE,
					"model_kw",
					"manu_kw",
					"12",
					"34",
					Constants.CONTROL_TYPE_TCP,
					Constants.LOC_KITCHEN,
					"power100",
					homeAppliance.getDate() + " / " + homeAppliance.getDay() + " / " + homeAppliance.getTime());
			d.setOnOffStatus(homeAppliance.getApp_state());
			
			arrangeHameAppliance(d);
			
		} else if(deviceName.equals(HomeApplianceInfo.HOMEAPPLIANCE_AC_NAME)) {
			d = new Device (
					"ac", 
					Constants.DEV_TYPE_AC,
					"modelName",
					"manuKETI",
					"12",
					"34",
					Constants.CONTROL_TYPE_TCP,
					Constants.LOC_LIVINGROOM,
					"power100",
					homeAppliance.getDate() + " / " + homeAppliance.getDay() + " / " + homeAppliance.getTime());
			d.setOnOffStatus(homeAppliance.getApp_state());
			
			arrangeHameAppliance(d);
			setMainHomeApppliance(d);
			
		} else if (deviceName.equals(HomeApplianceInfo.HOMEAPPLIANCE_ACCLEANER_NAME)) {
			d = new Device (
					"ac_cleaner", 
					Constants.DEV_TYPE_ACCLEANER,
					"modelName",
					"manuKETI",
					"12",
					"34",
					Constants.CONTROL_TYPE_TCP,
					Constants.LOC_LIVINGROOM,
					"power100",
					homeAppliance.getDate() + " / " + homeAppliance.getDay() + " / " + homeAppliance.getTime());
			d.setOnOffStatus(homeAppliance.getApp_state());
			
			arrangeHameAppliance(d);
			setMainHomeApppliance(d);
			
		} else if (deviceName.equals(HomeApplianceInfo.HOMEAPPLIANCE_AUDIO_NAME)) {
			d = new Device (
					"audio", 
					Constants.DEV_TYPE_AUDIO,
					"modelName",
					"manu_kw",
					"12",
					"34",
					Constants.CONTROL_TYPE_TCP,
					Constants.LOC_KITCHEN,
					"power100",
					homeAppliance.getDate() + " / " + homeAppliance.getDay() + " / " + homeAppliance.getTime());
			d.setOnOffStatus(homeAppliance.getApp_state());
			
			arrangeHameAppliance(d);
			setMainHomeApppliance(d);
			
		} else if (deviceName.equals(HomeApplianceInfo.HOMEAPPLIANCE_DEHUMIDIFIER_NAME)) {
			d = new Device (
					"dehumidifier", 
					Constants.DEV_TYPE_DEHUMIDIFIER,
					"modelName",
					"manu_kw",
					"12",
					"34",
					Constants.CONTROL_TYPE_TCP,
					Constants.LOC_LIVINGROOM,
					"power100",
					homeAppliance.getDate() + " / " + homeAppliance.getDay() + " / " + homeAppliance.getTime());

			d.setOnOffStatus(homeAppliance.getApp_state());
			
			arrangeHameAppliance(d);
			setMainHomeApppliance(d);
			
		} else if (deviceName.equals(HomeApplianceInfo.HOMEAPPLIANCE_DVD_NAME)) {
			d = new Device (
					"dvd", 
					Constants.DEV_TYPE_DVD,
					"modelName",
					"manuKETI",
					"12",
					"34",
					Constants.CONTROL_TYPE_TCP,
					Constants.LOC_BEDROOM_ONE,
					"power100",
					homeAppliance.getDate() + " / " + homeAppliance.getDay() + " / " + homeAppliance.getTime());
			d.setOnOffStatus(homeAppliance.getApp_state());
			
			arrangeHameAppliance(d);
			
		} else if (deviceName.equals(HomeApplianceInfo.HOMEAPPLIANCE_HUMIDIFIER_NAME)) {
			d = new Device (
					"humidifier", 
					Constants.DEV_TYPE_HUMIDIFIER,
					"modelName",
					"manuKETI",
					"12",
					"34",
					Constants.CONTROL_TYPE_TCP,
					Constants.LOC_BEDROOM_TWO,
					"power100",
					homeAppliance.getDate() + " / " + homeAppliance.getDay() + " / " + homeAppliance.getTime());
			d.setOnOffStatus(homeAppliance.getApp_state());
			
			arrangeHameAppliance(d);
			
		} else if (deviceName.equals(HomeApplianceInfo.HOMEAPPLIANCE_ROBOTCLEANER_NAME)) {
			d = new Device (
					"robot_cleaner", 
					Constants.DEV_TYPE_ROBOTCLEANER,
					"modelName",
					"manu_kw",
					"12",
					"34",
					Constants.CONTROL_TYPE_TCP,
					Constants.LOC_LIVINGROOM,
					"power100",
					homeAppliance.getDate() + " / " + homeAppliance.getDay() + " / " + homeAppliance.getTime());
			d.setOnOffStatus(homeAppliance.getApp_state());

			arrangeHameAppliance(d);
		}
	}
	
	private void arrangeHameAppliance(Device d) {
		String location = d.getLocation();
		
		if(location.equals(HomeApplianceInfo.LOCATION_LIVINGROOM)) {
			if(!livingroom.addDevice(d)) 
				livingroom.exchangeDevice(d);

			livingroomHView.setAdapter(DeviceControlActivity.this, livingroom);
		} else if (location.equals(HomeApplianceInfo.LOCATION_BATHROOM)) {
			if(!bathroom.addDevice(d)) 
				bathroom.exchangeDevice(d);

			bathroomHView.setAdapter(DeviceControlActivity.this, livingroom);
		} else if (location.equals(HomeApplianceInfo.LOCATION_BEDROOM1)) {
			if(!bedroom1.addDevice(d))
				bedroom1.exchangeDevice(d);
			
			bedroomoneHView.setAdapter(DeviceControlActivity.this, bedroom1);
		} else if (location.equals(HomeApplianceInfo.LOCATION_BEDROOM2)) {
			if(!bedroom2.addDevice(d))
				bedroom2.exchangeDevice(d);
			
			bedroomtwoHView.setAdapter(DeviceControlActivity.this, bedroom2);
		} else if (location.equals(HomeApplianceInfo.LOCATION_KITCHEN)) {
			if(!kitchen.addDevice(d))
				kitchen.exchangeDevice(d);
			
			kitchenHView.setAdapter(DeviceControlActivity.this, kitchen);
		} else if (location.equals(HomeApplianceInfo.LOCATION_OUTSIDE)) {

		} else if (location.equals(HomeApplianceInfo.LOCATION_ENTRY)) {

		}
	}
	
	private void setMainHomeApppliance(Device d) {
		if(!mainDeviceAdapter.addDevice(d))
			mainDeviceAdapter.exchangeDevice(d);

		HomeServiceController.getInstance().dispatchMainDevice(mainDeviceAdapter);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		HomeServiceController.getInstance().deleteInformationReceiver(mIDeviceStatusReceiver);
		HomeServiceController.getInstance().deleteInformationReceiver(IBottomInfoReceiver);
	}
	
}

