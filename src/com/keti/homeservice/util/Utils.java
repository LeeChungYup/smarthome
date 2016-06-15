package com.keti.homeservice.util;

import com.keti.homeservice.R;
import com.keti.homeservice.item.Device;
import com.keti.homeservice.item.ThermoHygrometh;

import android.content.Context;

public class Utils {
	private static Utils instance;
	
	private Context mContext;

	public Context getContext() {
		return this.mContext;
	}
	
	public void setContext(Context context) {
		this.mContext = context;
	}
	
	public static Utils getInstance() {
		if(instance == null) {
			instance = new Utils();
		}
		
		return instance;
	}
	
	private Utils() {}

	public static boolean isInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		} catch(NullPointerException e) {
			return false;
		}
		
		// only got here if we didn't return false
		return true;
	}
	
	
	/**
	 * 서비스관련 영문을 한글로 바꿔준다.
	 * */
	public String toSrvKorean(String eng) {
		if (eng.equals(Constants.SN_OUT_MODE)) {
			return mContext.getString(R.string.srv_out_mode);
		} else if (eng.equals(Constants.SN_IN_MODE)) {
			return mContext.getString(R.string.srv_in_mode);
		} else if (eng.equals(Constants.SN_INNER_TEMP_CTR)) {
			return mContext.getString(R.string.srv_inner_temp_ctr);
		} else if (eng.equals(Constants.SN_CLEAN_MODE)) {
			return mContext.getString(R.string.srv_clean_mode);
		} else if (eng.equals(Constants.SN_AIRCLEANER)) {
			return mContext.getString(R.string.srv_aircleaner);
		} else if (eng.equals(Constants.SN_AIRCON)) {
			return mContext.getString(R.string.srv_aircon);
		} else if (eng.equals(Constants.SN_DEHUMIDIFIER)) {
			return mContext.getString(R.string.srv_dehumidifier);
		} else if (eng.equals(Constants.SN_HUMIDIFIER)) {
			return mContext.getString(R.string.srv_humidifier);
		}

		return null;
	}

	/**
	 * 카테고리관련 영문을 한글로 바꿔준다.
	 * */
	public String toCtgKorean(String eng) {
		if (eng.equals(Constants.CATEGORY_SECURITY)) {
			return mContext.getString(R.string.ctg_sercurity);
		} else if (eng.equals(Constants.CATEGORY_ENVR)) {
			return mContext.getString(R.string.ctg_environment);
		}

		return null;
	}

	/**
	 * 디바이스 상태에 관련하여 한글 메시지를 추출한다.
	 * 
	 * @param device 디바이스 상태
	 * */
	public String getHomeServerMsg(Device device) {
		String deviceType = device.getDeviceType();
		if (deviceType.equals(Constants.DEV_TYPE_TV)) {
			if (device.isOnOffStatus()) {
				return mContext.getString(R.string.turunon_tv);
			} else {
				return mContext.getString(R.string.turunoff_tv);
			}
		} else if (deviceType.equals(Constants.DEV_TYPE_FAN)) {
			if (device.isOnOffStatus()) {
				return mContext.getString(R.string.turunon_fan);
			} else {
				return mContext.getString(R.string.turunoff_fan);
			}
		} else if (deviceType.equals(Constants.DEV_TYPE_AC)) {
			if (device.isOnOffStatus()) {
				return mContext.getString(R.string.turunon_ac);
			} else {
				return mContext.getString(R.string.turunoff_ac);
			}
		} else if (deviceType.equals(Constants.DEV_TYPE_WARMER)) {
			if (device.isOnOffStatus()) {
				return mContext.getString(R.string.turunon_warmer);
			} else {
				return mContext.getString(R.string.turunoff_warmer);
			}
		} else if (deviceType.equals(Constants.DEV_TYPE_HUMIDIFIER)) {
			if (device.isOnOffStatus()) {
				return mContext.getString(R.string.turunon_hum);
			} else {
				return mContext.getString(R.string.turunoff_hum);
			}
		} else if (deviceType.equals(Constants.DEV_TYPE_LAMP)) {
			if (device.isOnOffStatus()) {
				return mContext.getString(R.string.turunon_lamp);
			} else {
				return mContext.getString(R.string.turunoff_lamp);
			}
		} else if (deviceType.equals(Constants.DEV_TYPE_THERMO_HYGROMETH)) {
			ThermoHygrometh th = (ThermoHygrometh) device.getFunc();
			return String.format(mContext.getString(R.string.change_tmp_hum), th.getTemperature(), th.getHumidity());
		} else if (deviceType.equals(Constants.DEV_TYPE_AIR_QUALITY)) {
			ThermoHygrometh th = (ThermoHygrometh) device.getFunc();
			return String.format(mContext.getString(R.string.change_tmp_hum), th.getTemperature(), th.getHumidity());
		}

		return null;
	}
}
