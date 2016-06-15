package com.keti.homeservice.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.keti.homeservice.item.Device;
import com.keti.homeservice.item.PhoneInfo;
import com.keti.homeservice.item.Service;
import com.keti.homeservice.util.Utils;

import android.content.Context;
import android.util.Log;
import asia.sonix.android.orm.AbatisService;

/**
 * 이 클래스는 SQLite 데이터 베이스를 재어하여 현재 업데이트된 최신정보(Device, Service, User)를 저장한다.
 * 
 * @author KETI_L
 * @see SQLite, Abatis
 * */
public class DBController {
	private static final String TAG = "DBController";

	public static DBController instance = null;
	public static DBController getInstance() {
		if(instance == null) {
			Context context = Utils.getInstance().getContext();
			instance = new DBController(context);
		}
		
		return instance;
	}
	
	/****************************** sql.xml에 정의된 쿼리 이름 ******************************/
	private static final String CREATE_DEV = "create_homeappliance";
	private static final String CREATE_SRV = "create_srv";
	private static final String CREATE_USER = "create_phone";
	
	private static final String SELECT_DEV = "select_homeappliance";
	private static final String SELECT_SRV = "select_srv";
	private static final String SELECT_PHONE = "select_phone";
	
	private static final String INSERT_DEV = "insert_homeappliance";
	private static final String INSERT_SRV = "insert_srv";
	private static final String INSERT_PHONE = "insert_phone";
	
	private static final String DELETE_DEV = "delete_homeappliance";
	private static final String DELETE_SRV = "delete_srv";
	private static final String DELETE_PHONE = "delete_phone";
	
	/****************************** 각 테이블별 컬럼 이름 ***********************************/
	private static final String DEV_SN = "serialnumber";
	private static final String DEV_DT = "devicetype";
	private static final String DEV_MN = "modelname";
	private static final String DEV_CT = "controltype";
	private static final String DEV_SZ = "size";
	private static final String DEV_WI = "weight";
	private static final String DEV_PW = "power";
	private static final String DEV_MF = "manufacturer";
	private static final String DEV_RT = "regtime";
	
	private static final String SRV_ID = "serviceid";
	private static final String SRV_SN = "servicename";
	private static final String SRV_SV = "serviceversion";
	private static final String SRV_SI = "serviceinfo";
	private static final String SRV_CT = "category";
	private static final String SRV_RD = "requisitedevices";
	private static final String SRV_RT = "regtime";

	private static final String PHONE_AD = "address";
	private static final String PHONE_TM = "time";
	private static final String PHONE_ST = "state";
	private static final String PHONE_CT = "callstate";
	private static final String PHONE_LT = "latitude";
	private static final String PHONE_LG = "longitude";
	
	private static final int RESULT_SUC = 1;
	
	private AbatisService abatisService;

	private DBController(Context context) {
		abatisService = AbatisService.getInstance(context, "smarthome");
	}
	
	/**
	 * 현재 접속중인 데이터 베이스를 종료
	 * */
	public void closeDB() {
		abatisService.close();
	}
	
	/**
	 * 필요한 모든 테이블을 생성
	 * */
	public void createTables() {
		abatisService.execute(CREATE_DEV, null);
		abatisService.execute(CREATE_SRV, null);
		abatisService.execute(CREATE_USER, null);
	}
	
	/**
	 * 디비에 저장된 모든 디바이스 정보를 추출
	 * 
	 * @return List 현재 디비에 저장된 모든 디바이스 정보
	 * */
	public List<Map<String, Object>> selectDev() {
		return abatisService.executeForMapList(SELECT_DEV, null);
	}
	
	/**
	 * 디비에 저장된 모든 서비스 정보를 추출
	 * 
	 * @return List 현재 디비에 저장된 모든 서비스 정보
	 * */
	public List<Map<String, Object>> selectSrv() {
		return abatisService.executeForMapList(SELECT_SRV, null);
	}
	
	/**
	 * 디비에 저장된 모든 유저 정보를 추출
	 * 
	 * @return List 현재 디비에 저장된 모든 유저 정보
	 * */
	public List<Map<String, Object>> selectPhone() {
		Log.d(TAG, "********* START selecting phone *********");
		
		return abatisService.executeForMapList(SELECT_PHONE, null);
	}
	
	/**
	 * 디비에 디바이스 정보을 저장
	 * 
	 * @param Device[] 저장할 모든 디바이스
	 * @return RESULT_SUC 저장 성공, other 저장 실패
	 * */
	public int insertDevice(Device[] devices) {
		int result = 0;
		
		for(Device d : devices) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(DEV_SN, d.getSerialNumber());
			params.put(DEV_DT, d.getDeviceType());	
			params.put(DEV_MN, d.getModelName());
			params.put(DEV_CT, d.getControlType());
			params.put(DEV_SZ, d.getSize());	
			params.put(DEV_WI, d.getWeight());	
			params.put(DEV_PW, d.getPower());	
			params.put(DEV_MF, d.getManufacturer());	
			params.put(DEV_RT, d.getRegTime());	

			result = abatisService.execute(INSERT_DEV, params);

			if(result == RESULT_SUC) {
				Log.d(TAG, "Sucess to insert device info.: " + params.toString());
			} else {
				Log.d(TAG, "Fail to insert device info.: " + params.toString());
			}
		}
		
		return result;
	}
	
	/**
	 * 디비에 디바이스 정보를 저장
	 * 
	 * @param Device 저장할 디바이스
	 * @return RESULT_SUC 저장 성공, other 저장 실패
	 * */
	public int insertDevice(Device device) {
		int result = 0;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(DEV_SN, device.getSerialNumber());
		params.put(DEV_DT, device.getDeviceType());	
		params.put(DEV_MN, device.getModelName());	
		params.put(DEV_CT, device.getControlType());
		params.put(DEV_SZ, device.getSize());	
		params.put(DEV_WI, device.getWeight());	
		params.put(DEV_PW, device.getPower());	
		params.put(DEV_MF, device.getManufacturer());	
		params.put(DEV_RT, device.getRegTime());	

		try {
			result = abatisService.execute(INSERT_DEV, params);
			if(result == RESULT_SUC) {
				Log.d(TAG, "Sucess to insert devcie info.: " + params.toString());
			} else {
				Log.d(TAG, "Fail to insert devcie infor.: " + params.toString());
			}
		} catch (NullPointerException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	/**
	 * 디비에 서비스 정보들을 저장
	 * 
	 * @param Service[] 저장할 모든 서비스
	 * @return RESULT_SUC 저장 성공, other 저장 실패
	 * */
	public int insertSrv(Service[] services) {
		int result = 0;
		
		for(Service s : services) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(SRV_ID, s.getServiceId());
			params.put(SRV_SN, s.getServiceName());	
			params.put(SRV_SV, s.getServiceVersion());	
			params.put(SRV_SI, s.getServiceInfo());	
			params.put(SRV_CT, s.getCategory());
			params.put(SRV_RD, s.getRequisiteDevices());	
			params.put(SRV_RT, s.getRegTime());	

			try {
				result = abatisService.execute(INSERT_SRV, params);
				
				if(result == RESULT_SUC) {
					Log.d(TAG, "Sucess to insert service info.: " + params.toString());
				} else {
					Log.d(TAG, "Fail to insert service info.: " + params.toString());
				}	
			} catch (NullPointerException e) {
				Log.e(TAG, e.getMessage());
			}
		}

		return result;
	}
	
	/**
	 * 디비에 유저 정보들을 저장
	 * 
	 * @param PhoneInfo 저장할 유저
	 * @return RESULT_SUC 저장 성공, other 저장 실패
	 * */
	public int insertPhone(PhoneInfo p) {
		int result = 0;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PHONE_AD, p.getAddress());
		params.put(PHONE_TM, p.getTime());	
		params.put(PHONE_ST, p.getState());	
		params.put(PHONE_CT, p.getCallState());	
		params.put(PHONE_LT, p.getLatitude());
		params.put(PHONE_LG, p.getLongitude());
			
		try {
			result = abatisService.execute(INSERT_PHONE, params);	
			
			if(result == RESULT_SUC) {
				Log.d(TAG, "Sucess to insert phone info.: " + params.toString());
			} else {
				Log.d(TAG, "Fail to insert phone info.: " + params.toString());
			}
		} catch(NullPointerException e) {
			Log.e(TAG, e.getMessage());
		}

		return result;
	}

	/**
	 * 디비에 저장된 모든 디바이스 정보를 제거
	 * 
	 * @return 제거한 디바이스 정보 갯수
	 * */
	public int deleteDevice() {
		return abatisService.execute(DELETE_DEV, null);
	}
	
	/**
	 * 디비에 저장된 모든 서비스 정보를 제거
	 * 
	 * @return 제거한 서비스 정보 갯수
	 * */
	public int deleteService() {
		return abatisService.execute(DELETE_SRV, null);
	}
	
	/**
	 * 디비에 저장된 모든 유저 정보를 제거
	 * 
	 * @return 제거한 유저 정보 갯수
	 * */
	public int deletePhone() {
		return abatisService.execute(DELETE_PHONE, null);
	}
}
