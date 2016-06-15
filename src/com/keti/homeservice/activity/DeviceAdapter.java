package com.keti.homeservice.activity;

import java.util.ArrayList;
import java.util.List;

import com.keti.homeservice.R;
import com.keti.homeservice.item.Device;
import com.keti.homeservice.util.Constants;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class DeviceAdapter extends BaseAdapter {
	private List<Device> devices;
	private LayoutInflater inflater;

	public DeviceAdapter(LayoutInflater inflater) {
		devices = new ArrayList<Device>();
		this.inflater = inflater;
	}

	/**
	 * 리스트에 모든 디바이스 제거
	 * */
	public void clearDevice() {
		devices.clear();
	}

	/**
	 * 리스트에 디바이스 추가
	 * 
	 * @param device 추가할 디바이스
	 * @return true 디바이스 추가 성공, false 디바이스 추가 실패
	 * */
	public boolean addDevice(Device device) {
		String targetSN = device.getSerialNumber();
		for(Device d : devices) {
			if(targetSN.equals(d.getSerialNumber())) {
				return false;
			}
		}
		
		return devices.add(device);
	}
	
	/**
	 * 기존 등록된 디바이스와 동일한 새로운 디바이스의 객체로 바꾼다.
	 * 
	 * @param device 교체할 디바이스
	 * @return true 디바이스 교환 성공, false 디바이스 교환 실패
	 * */
	public boolean exchangeDevice(Device device) {
		String targetSN = device.getSerialNumber();
		for(int i=0; i<devices.size(); i++) {
			String serialNumber = devices.get(i).getSerialNumber();
			if(targetSN.equals(serialNumber)) {
				devices.remove(i);
				devices.add(i, device);
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int getCount() {
		return devices.size();
	}

	@Override
	public Object getItem(int position) {
		return devices.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.device_item, null);
		}

		Device d = devices.get(position);
		ImageView mImageView = (ImageView) convertView.findViewById(R.id.dev_item);
		
		String dType = d.getDeviceType();
		if(dType.equals(Constants.DEV_TYPE_TV))
		{
			if(d.isOnOffStatus()) {
				mImageView.setImageResource(R.drawable.tv_on);
				Log.d("ONOFF", "DEV_TYPE_TV: ON");
			} else {
				mImageView.setImageResource(R.drawable.tv_off);	
				Log.d("ONOFF", "DEV_TYPE_TV: OFf");
			}
		}
		else if(dType.equals(Constants.DEV_TYPE_FAN))
		{
			if(d.isOnOffStatus()) {
				mImageView.setImageResource(R.drawable.fan_on);
				Log.d("ONOFF", "DEV_TYPE_FAN: ON");
			} else {
				mImageView.setImageResource(R.drawable.fan_off);	
				Log.d("ONOFF", "DEV_TYPE_FAN: OFf");
			}
		}
		else if(dType.equals(Constants.DEV_TYPE_AC))
		{
			if(d.isOnOffStatus()) {
				mImageView.setImageResource(R.drawable.ac_on);
				Log.d("ONOFF", "DEV_TYPE_AC: ON");
			} else {
				mImageView.setImageResource(R.drawable.ac_off);	
				Log.d("ONOFF", "DEV_TYPE_AC: OFf");
			}
		}
		else if(dType.equals(Constants.DEV_TYPE_ACCLEANER))
		{
			if(d.isOnOffStatus()) {
				mImageView.setImageResource(R.drawable.aircleaner_on);
				Log.d("ONOFF", "DEV_TYPE_ACCLEANER: ON");
			} else {
				mImageView.setImageResource(R.drawable.aircleaner_off);	
				Log.d("ONOFF", "DEV_TYPE_ACCLEANER: OFF");
			}
		}
		else if(dType.equals(Constants.DEV_TYPE_HUMIDIFIER))
		{
			if(d.isOnOffStatus()) {
				mImageView.setImageResource(R.drawable.humidifier_on);
				Log.d("ONOFF", "DEV_TYPE_HUMIDIFIER: ON");
			} else {
				mImageView.setImageResource(R.drawable.humidifier_off);	
				Log.d("ONOFF", "DEV_TYPE_HUMIDIFIER: OFf");
			}
		}
		else if(dType.equals(Constants.DEV_TYPE_DEHUMIDIFIER))
		{
			if(d.isOnOffStatus()) {
				mImageView.setImageResource(R.drawable.dehumidifier_on);
				Log.d("ONOFF", "DEV_TYPE_DEHUMIDIFIER: ON");
			} else {
				mImageView.setImageResource(R.drawable.dehumidifier_off);	
				Log.d("ONOFF", "DEV_TYPE_DEHUMIDIFIER: OFF");
			}
		}
		else if(dType.equals(Constants.DEV_TYPE_LAMP))
		{
			if(d.isOnOffStatus()) {
				mImageView.setImageResource(R.drawable.lamp_on);
				Log.d("ONOFF", "DEV_TYPE_LAMP: ON");
			} else {
				mImageView.setImageResource(R.drawable.lamp_off);
				Log.d("ONOFF", "DEV_TYPE_LAMP: OFF");
			}
		}
		else if(dType.equals(Constants.DEV_TYPE_WARMER))
		{
			if(d.isOnOffStatus()) {
				mImageView.setImageResource(R.drawable.warmer_on);
				Log.d("ONOFF", "DEV_TYPE_WARMER: ON");
			} else {
				mImageView.setImageResource(R.drawable.warmer_off);	
				Log.d("ONOFF", "DEV_TYPE_WARMER: OFf");
			}
		}
		else if(dType.equals(Constants.DEV_TYPE_AUDIO))
		{
			if(d.isOnOffStatus()) {
				mImageView.setImageResource(R.drawable.audio_on);
				Log.d("ONOFF", "DEV_TYPE_AUDIO: ON");
			} else {
				mImageView.setImageResource(R.drawable.audio_off);	
				Log.d("ONOFF", "DEV_TYPE_AUDIO: OFF");
			}
		}
		else if(dType.equals(Constants.DEV_TYPE_ROBOTCLEANER))
		{
			if(d.isOnOffStatus()) {
				mImageView.setImageResource(R.drawable.robotcleaner_on);
				Log.d("ONOFF", "DEV_TYPE_ROBOTCLEANER: ON");
			} else {
				mImageView.setImageResource(R.drawable.robotcleaner_off);	
				Log.d("ONOFF", "DEV_TYPE_ROBOTCLEANER: OFF");
			}
		}
		else if(dType.equals(Constants.DEV_TYPE_DVD))
		{
			if(d.isOnOffStatus()) {
				mImageView.setImageResource(R.drawable.dvd_on);
				Log.d("ONOFF", "DEV_TYPE_DVD: ON");
			} else {
				mImageView.setImageResource(R.drawable.dvd_off);	
				Log.d("ONOFF", "DEV_TYPE_DVD: OFF");
			}
		}
		else if(dType.equals(Constants.DEV_TYPE_HEATER))
		{
			if(d.isOnOffStatus()) {
				mImageView.setImageResource(R.drawable.heater_on);
				Log.d("ONOFF", "DEV_TYPE_HEATER: ON");
			} else {
				mImageView.setImageResource(R.drawable.heater_off);	
				Log.d("ONOFF", "DEV_TYPE_HEATER: OFF");
			}
		}
		else if(dType.equals(Constants.DEV_TYPE_WASHINGMACHINE))
		{
			if(d.isOnOffStatus()) {
				mImageView.setImageResource(R.drawable.washingmachine_on);
				Log.d("ONOFF", "DEV_TYPE_WASHINGMACHINE: ON");
			} else {
				mImageView.setImageResource(R.drawable.washingmachine_off);	
				Log.d("ONOFF", "DEV_TYPE_WASHINGMACHINE: OFF");
			}
		}
		
		return convertView;
	}

}