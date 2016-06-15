package com.keti.homeservice.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.keti.homeservice.activity.DeviceAdapter;
import com.keti.homeservice.activity.MainServiceAdapter;
import com.keti.homeservice.activity.Mode;
import com.keti.homeservice.item.BasicUser;
import com.keti.homeservice.item.Device;
import com.keti.homeservice.item.HomeAppliance;
import com.keti.homeservice.item.ItemFactory;
import com.keti.homeservice.item.KWSensor;
import com.keti.homeservice.item.PhoneInfo;
import com.keti.homeservice.item.Service;
import com.keti.homeservice.item.SmartBandInfo;
import com.keti.homeservice.item.ThermoHygrometh;
import com.keti.homeservice.item.User;
import com.keti.homeservice.mashup.Mashup;
import com.keti.homeservice.msg.HeaderMsg;
import com.keti.homeservice.msg.HttpMsg;
import com.keti.homeservice.msg.NotiMsg;
import com.keti.homeservice.msg.ReqMsg;
import com.keti.homeservice.msg.ResMsg;
import com.keti.homeservice.receiver.IBottomInfoReceiver;
import com.keti.homeservice.receiver.IDeviceStatusReceiver;
import com.keti.homeservice.receiver.IMashupReceiver;
import com.keti.homeservice.receiver.IMessageReceiver;
import com.keti.homeservice.receiver.IQRCodeReceiver;
import com.keti.homeservice.receiver.IServiceStatusReceiver;
import com.keti.homeservice.receiver.IUserReceiver;
import com.keti.homeservice.util.Constants;
import com.keti.homeservice.util.Utils;
import com.keti.httpcommunication.HttpCommunicator;
import com.keti.httpcommunication.IResponseReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * This class controls data between screens(Activities) and communication(Socket, HTTP).
 * 
 * @author KETI_L
 * */
public class HomeServiceController {
	private static final String TAG = "HomeServiceController";

	public static HomeServiceController instance;

	public static HomeServiceController getInstance() {
		if (instance == null) {
			instance = new HomeServiceController();
		}

		return instance;
	}

	public static String homeserverIp;
	public static int homeserverPort;
	public static String mashupdbUrl;
	public static String id;
	public static String password;
	
	private Mashup mMashup;
	private ItemFactory mItemFactory;

	private HttpCommunicator commUserDB;
	private HttpCommunicator commMashup;
	private HttpCommunicator commQRCode;

	private List<IBottomInfoReceiver> mIBottomInfoReceivers;
	private List<IDeviceStatusReceiver> mIDeviceStatusReceivers;
	private List<IServiceStatusReceiver> mIServiceChangeReceivers;
	private List<IMashupReceiver> mIMashupReceivers;
	private List<IMessageReceiver> mIMessageReceivers;
	private List<IQRCodeReceiver> mIQRCodeReceivers;
	private List<IUserReceiver> mIUserReceivers;

	/******************************************  add receiver ******************************************/
	public void addInformationReceiver(IBottomInfoReceiver bottomInfoReceiver) {
		mIBottomInfoReceivers.add(bottomInfoReceiver);
	}

	public void addInformationReceiver(IDeviceStatusReceiver deviceStatusReceiver) {
		mIDeviceStatusReceivers.add(deviceStatusReceiver);
	}

	public void addInformationReceiver(IServiceStatusReceiver serviceChangeReceiver) {
		mIServiceChangeReceivers.add(serviceChangeReceiver);
	}

	public void addInformationReceiver(IMashupReceiver mashupReceiver) {
		mIMashupReceivers.add(mashupReceiver);
	}

	public void addInformationReceiver(IMessageReceiver messageReceiver) {
		mIMessageReceivers.add(messageReceiver);
	}
	
	public void addInformationReceiver(IQRCodeReceiver qrcodeReceiver) {
		mIQRCodeReceivers.add(qrcodeReceiver);
	}
	
	public void addInformationReceiver(IUserReceiver userReceiver) {
		mIUserReceivers.add(userReceiver);
	}

	/******************************************  delete receiver ******************************************/
	public void deleteInformationReceiver(IBottomInfoReceiver mIBottomInfoReceiver) {
		mIBottomInfoReceivers.remove(mIBottomInfoReceiver);
	}
	
	public void deleteInformationReceiver(IDeviceStatusReceiver mIDeviceStatusReceiver) {
		mIDeviceStatusReceivers.remove(mIDeviceStatusReceiver);
	}
	
	public void deleteInformationReceiver(IServiceStatusReceiver mIServiceChangeReceiver) {
		mIServiceChangeReceivers.remove(mIServiceChangeReceiver);
	}

	public void deleteInformationReceiver(IMashupReceiver mIMashupReceiver) {
		mIMashupReceivers.remove(mIMashupReceiver);
	}
	
	public void deleteInformationReceiver(IMessageReceiver mIMessageReceiver) {
		mIMessageReceivers.remove(mIMessageReceiver);
	}
	
	public void deleteInformationReceiver(IQRCodeReceiver qrcodeReceiver) {
		mIQRCodeReceivers.remove(qrcodeReceiver);
	}
	
	/******************************************  construct ******************************************/
	private HomeServiceController() {
		mMashup = new Mashup();
		mItemFactory = new ItemFactory();

		mIBottomInfoReceivers = new ArrayList<IBottomInfoReceiver>();
		mIMashupReceivers = new ArrayList<IMashupReceiver>();
		mIDeviceStatusReceivers = new ArrayList<IDeviceStatusReceiver>();
		mIMessageReceivers = new ArrayList<IMessageReceiver>();
		mIServiceChangeReceivers = new ArrayList<IServiceStatusReceiver>();
		mIQRCodeReceivers = new ArrayList<IQRCodeReceiver>();
		mIUserReceivers = new ArrayList<IUserReceiver>();

		commUserDB = new HttpCommunicator(
				"http://115.95.190.117:1988/smarthome/user/user.php",
//				"http://192.168.0.9/smarthome/user/user.php",
				userDBReceiver);

		commMashup = new HttpCommunicator(
//				"http://115.95.190.117:1988/smarthome/mashup/mashup.php",
				"http://192.168.0.190/smarthome/mashup/mashup.php",
				mashupResponseReceiver);

		commQRCode = new HttpCommunicator(
//				"http://115.95.190.117:1988/smarthome/qrcode/qrcode.php",
				"http://192.168.0.190/smarthome/qrcode/qrcode.php",
				qrcodeReceiver);
	}

	/******************************************  request ******************************************/
	public void requestAllDeviceInfo() {
		final HttpMsg httpMsg = new HttpMsg(
				User.getID(),
				Constants.TO_MASHUP_DB,
				Constants.USER_NAME,
				Constants.TYPE_REQ,
				Constants.CMD_SELECT,
				Constants.COUNT_ALL,
				Constants.CT_DEV,
				null
				);
		
		commMashup.get(ResMsg.SELECT_ALL_DEV, httpMsg.getItem());
	}

	public void requestAllServiceInfo() {
		HttpMsg httpMsg = new HttpMsg(
				User.getID(),
				Constants.TO_MASHUP_DB,
				Constants.USER_NAME,
				Constants.TYPE_REQ,
				Constants.CMD_SELECT,
				Constants.COUNT_ALL,
				Constants.CT_SRV,
				null
				);
		
		commMashup.get(ResMsg.SELECT_ALL_SRV, httpMsg.getItem());
	}
	
	public void registerService(Service service) {
		HttpMsg httpMsg = new HttpMsg(
				User.getID(),
				Constants.TO_MASHUP_DB,
				Constants.USER_NAME,
				Constants.TYPE_REQ,
				Constants.CMD_INSERT,
				Constants.COUNT_ONE,
				Constants.CT_SRV,
				mItemFactory.toJsonMsg(service));
		
		commMashup.post(httpMsg.getItem());
	}
	
	public void requestAllServiceStatus() {
		ReqMsg rMsg = new ReqMsg(
				User.getID(),
				Constants.TO_HOME_GATEWAY,
				Constants.USER_NAME,
				Constants.TYPE_REQ, 
				Constants.CMD_STATUS,
				Constants.COUNT_ALL, 
				Constants.CT_SRV,
				null);

		Intent i = new Intent(SocketService.ACTION_SEND);
		i.putExtra(SocketService.RESULT, mItemFactory.toJsonMsg(rMsg));
		Utils.getInstance().getContext().sendBroadcast(i);
	}
	
	public void requestAllDeviceStatus() {
		ReqMsg rMsg = new ReqMsg(
				User.getID(), 
				Constants.TO_HOME_GATEWAY,
				Constants.USER_NAME,
				Constants.TYPE_REQ, 
				Constants.CMD_STATUS, 
				Constants.COUNT_ALL, 
				Constants.CT_DEV, 
				null);

		Intent i = new Intent(SocketService.ACTION_SEND);
		i.putExtra(SocketService.RESULT, mItemFactory.toJsonMsg(rMsg));
		Utils.getInstance().getContext().sendBroadcast(i);
	}

	public void requestAddDeviceInfo(Device device) {

		HttpMsg httpMsg = new HttpMsg(
				User.getID(),
				Constants.TO_MASHUP_DB,
				Constants.USER_NAME,
				Constants.TYPE_REQ,
				Constants.CMD_INSERT,
				Constants.COUNT_ONE,
				Constants.CT_DEV,
				mItemFactory.toJsonMsg(device)
				);
		
		commMashup.post(httpMsg.getItem());
	}

	public void requestAddServiceInfo(Service service) {
		HttpMsg httpMsg = new HttpMsg(
				User.getID(),
				Constants.TO_MASHUP_DB,
				Constants.USER_NAME,
				Constants.TYPE_REQ,
				Constants.CMD_INSERT,
				Constants.COUNT_ONE,
				Constants.CT_SRV,
				mItemFactory.toJsonMsg(service)
				);
		
		commMashup.post(httpMsg.getItem());
	}

	public void requestStartService(Service service) {
		ReqMsg rMsg = new ReqMsg(
				Constants.MY_NAME, 
				Constants.TO_HOME_GATEWAY,
				Constants.USER_NAME,
				Constants.TYPE_REQ, 
				Constants.CMD_INSERT, 
				Constants.COUNT_ONE, 
				Constants.CT_SRV, 
				service);

		Intent i = new Intent(SocketService.ACTION_SEND);
		i.putExtra(SocketService.RESULT, mItemFactory.toJsonMsg(rMsg));
		Utils.getInstance().getContext().sendBroadcast(i);
	}

	public void requestStopService(Service service) {
		ReqMsg rMsg = new ReqMsg(
				Constants.MY_NAME,
				Constants.TO_HOME_GATEWAY,
				Constants.USER_NAME,
				Constants.TYPE_REQ, 
				Constants.CMD_DELETE, 
				Constants.COUNT_ONE,
				Constants.CT_SRV, 
				service);

		Intent i = new Intent(SocketService.ACTION_SEND);
		i.putExtra(SocketService.RESULT, mItemFactory.toJsonMsg(rMsg));
		Utils.getInstance().getContext().sendBroadcast(i);
	}

	public void requestQRCode(String modelName) {
		HttpMsg httpMsg = new HttpMsg(
				User.getID(),
				Constants.TO_QRCODE_DB,
				Constants.USER_NAME,
				Constants.TYPE_REQ,
				Constants.CMD_SELECT,
				Constants.COUNT_ONE,
				Constants.CT_QRCODE);

		httpMsg.setModelName(modelName);
		
		commQRCode.get(ResMsg.SELECT_ONE_QRCODE, httpMsg.getItem());
	}
	
	public void requestAllUserInfo() {
		ReqMsg rMsg = new ReqMsg(
				Constants.MY_NAME,
				Constants.TO_USER_DB,
				Constants.USER_NAME,
				Constants.TYPE_REQ, 
				Constants.CMD_SELECT, 
				Constants.COUNT_ALL,
				Constants.CT_USER, 
				null);

		Intent i = new Intent(SocketService.ACTION_SEND);
		i.putExtra(SocketService.RESULT, mItemFactory.toJsonMsg(rMsg));
		Utils.getInstance().getContext().sendBroadcast(i);
		
		Log.d("CHUNG", mItemFactory.toJsonMsg(rMsg));
	}

	public void requestAddUserInfo(BasicUser user) {
		ReqMsg rMsg = new ReqMsg(
				Constants.MY_NAME,
				Constants.TO_USER_DB,
				Constants.USER_NAME,
				Constants.TYPE_REQ, 
				Constants.CMD_INSERT, 
				Constants.COUNT_ONE,
				Constants.CT_USER, 
				user);

		Intent i = new Intent(SocketService.ACTION_SEND);
		i.putExtra(SocketService.RESULT, mItemFactory.toJsonMsg(rMsg));
		Utils.getInstance().getContext().sendBroadcast(i);
	}

	public void notifyPhoneInfo(PhoneInfo phoneInfo) {
		switch(Mode.COM_MODE) {
		case Mode.MIRROR_TO_USERDB:
			HttpMsg httpMsg = new HttpMsg(
					User.getID(),
					Constants.TO_USER_DB,
					Constants.USER_NAME,
					Constants.TYPE_REQ,
					Constants.CMD_INSERT,
					Constants.COUNT_ONE,
					Constants.CT_PHONE,
					mItemFactory.toJsonMsg(phoneInfo)
					);
			
			commUserDB.post(httpMsg.getItem());
			break;
		case Mode.MIRROR_TO_GATEWAY:
			NotiMsg nMsg = new NotiMsg(
					User.getID(), 
					Constants.TO_USER_DB,
					Constants.USER_NAME,
					Constants.TYPE_NOTI, 
					Constants.CMD_INSERT,
					Constants.COUNT_ONE, 
					Constants.CT_PHONE, 
					phoneInfo);

			if(SocketService.isConnected) {
				Intent i = new Intent(SocketService.ACTION_SEND);
				i.putExtra(SocketService.RESULT, mItemFactory.toJsonMsg(nMsg));
				Utils.getInstance().getContext().sendBroadcast(i);	
			} else {
				DBController.getInstance().insertPhone(phoneInfo);
			}
			break;
		}
	}

	/******************************************  Dispatch data ******************************************/
	private void dispatchAllServiceInfo(List<Service> services) {
		for (IMashupReceiver r : mIMashupReceivers) {
			r.onReceiveAllServiceInfo(services);
		}
	}

	private void dispatchUsableServiceInfo(List<Service> services) {
		for (IMashupReceiver r : mIMashupReceivers) {
			r.onReceiveUsableServiceInfo(services);
		}
	}

	@SuppressWarnings("unused")
	private void dispatchServiceInfo(Service service) {
		for (IMashupReceiver r : mIMashupReceivers) {
			r.onReceiveServiceInfo(service);
		}
	}

	private void dispatchDeviceStatus(Device d) {
		for (IDeviceStatusReceiver r : mIDeviceStatusReceivers) {
			r.onReceiveDeviceStatus(d);
		}
	}

	private void dispatchChangedDeviceStatus(Device d) {
		for (IDeviceStatusReceiver r : mIDeviceStatusReceivers) {
			r.onChangeDeviceStatus(d);
		}
	}
	
	private void dispatchChangedHomeApplianceStatus(HomeAppliance ha) {
		for (IDeviceStatusReceiver r : mIDeviceStatusReceivers) {
			r.onChangeDeviceStatus(ha);
		}
	}
	
	private void dispatchChangedSensor(KWSensor sensor) {
		for (IDeviceStatusReceiver r : mIDeviceStatusReceivers) {
			r.onChangeDeviceStatus(sensor);
		}
	}

	private void dispatchServiceStatus(Service service) {
		for (IServiceStatusReceiver r : mIServiceChangeReceivers) {
			r.onAddInstallService(service);
		}
	}

	private void dispatchStartService(Service service) {
		for (IServiceStatusReceiver r : mIServiceChangeReceivers) {
			r.onStartService(service);
		}
	}

	private void dispatchStopService(Service service) {
		for (IServiceStatusReceiver r : mIServiceChangeReceivers) {
			r.onStopService(service);
		}
	}

	public void dispatchMainDevice(DeviceAdapter deviceAdapter) {
		for (IBottomInfoReceiver r : mIBottomInfoReceivers) {
			r.updateMainDevice(deviceAdapter);
		}
	}

	public void dispatchMainService(MainServiceAdapter mainServiceAdapter) {
		for (IBottomInfoReceiver r : mIBottomInfoReceivers) {
			r.updateMainService(mainServiceAdapter);
		}
	}

	public void dispatchMainInfo(Device device) {
		for (IBottomInfoReceiver r : mIBottomInfoReceivers) {
			r.updateBasicInfo(device);
		}
	}

	public void dispatchServiceMessage(String message) {
		for (IMessageReceiver r : mIMessageReceivers) {
			r.onSrvInstallMessage(message);
		}
	}

	public void dispatchStatusMessage(String message) {
		for (IMessageReceiver r : mIMessageReceivers) {
			r.onStatusMessage(message);
		}
	}
	
	private void dispatchNewDeviceInfo(Device homeappliance) {
		for (IQRCodeReceiver r : mIQRCodeReceivers) {
			r.onGetDeviceInfo(homeappliance);
		}
	}
	
	private void dispatchUsers(BasicUser[] users) {
		for (IUserReceiver r : mIUserReceivers) {
			r.onUsers(users);
		}
	}
	
	private void dispatchEmergency(SmartBandInfo smartband) {
		for (IUserReceiver r : mIUserReceivers) {
			r.onEmergency(smartband);
		}
	}
	
	private void dispatchNewUser(BasicUser user) {
		for (IUserReceiver r : mIUserReceivers) {
			r.onAddUser(user);
		}
	}
	
	
	/******************************************* Update data ****************************************************/
	private void updateDevices(Device[] devList) {
		if (devList != null) {
			devices.clear();
			for(Device d : Arrays.asList(devList)) {
				devices.add(d);
			}
			
			DBController.getInstance().deleteDevice();
			DBController.getInstance().insertDevice(devList);

			List<Service> l = mMashup.searchUsableService(devices, services);
			dispatchUsableServiceInfo(l);
		}
	}
	
	private void updateServices(Service[] srvList) {
		if (srvList != null) {
			services.clear();
			for(Service s : Arrays.asList(srvList)) {
				services.add(s);
			}

			DBController.getInstance().deleteService();
			DBController.getInstance().insertSrv(srvList);

			dispatchAllServiceInfo(services);
			
			List<Service> l = mMashup.searchUsableService(devices, services);
			dispatchUsableServiceInfo(l);
		}
	}
	
	private void updateDeviceState(Device[] devList) {
		if(devList != null) {
			for (Device d : devList) {
				dispatchDeviceStatus(d);
			}
		}
	}
	
	private void updateServiceState(Service[] srvList) {
		if(srvList != null) {
			for (Service s : srvList) {
				dispatchServiceStatus(s);
			}	
		}
	}
	
	private void insertDevice(Device device) {
		if(device != null) {
			devices.add(device);
			
			DBController.getInstance().insertDevice(device);

			List<Service> l = mMashup.searchUsableService(devices, services);
			dispatchUsableServiceInfo(l);
		}
	}
	
	/******************************************  Classify message ******************************************/
	private static List<Device> devices = new ArrayList<Device>();
	private static List<Service> services = new ArrayList<Service>();
	private void classifyMsg(ResMsg resMsg) {
		switch (resMsg.whatMsg()) {
		case HeaderMsg.SELECT_ALL_DEV:
			Device[] devList = mItemFactory.assembleDevices(resMsg.getResult());
			updateDevices(devList);
			break;
		case HeaderMsg.SELECT_ALL_SRV:
			Service[] srvList = mItemFactory.assembleServices(resMsg.getResult());
			updateServices(srvList);
			
//			for(Service s: srvList) {
//				Log.d("KETI", s.toString());
//			}
			
			break;
		case HeaderMsg.INSERT_ONE_DEV:
			Device device = mItemFactory.assembleDevice(resMsg.getResult());
			insertDevice(device);
			break;
		case HeaderMsg.STATE_ALL_DEV:
			Device[] devStList = mItemFactory.assembleDevices(resMsg.getResult());
			updateDeviceState(devStList);
			break;
		case HeaderMsg.STATE_ALL_SRV:
			Service[] srvStList = mItemFactory.assembleServices(resMsg.getResult());
			updateServiceState(srvStList);
			break;
		case HeaderMsg.INSERT_ONE_SRV:
			Service startService = mItemFactory.assembleService(resMsg.getResult());
			dispatchStartService(startService);
			break;
		case HeaderMsg.DELETE_ONE_SRV:
			Service stopService = mItemFactory.assembleService(resMsg.getResult());
			dispatchStopService(stopService);
			break;
		case HeaderMsg.SELECT_ALL_USER:
			BasicUser[] users = mItemFactory.assembleUsers(resMsg.getResult());
			dispatchUsers(users);
			break;
		case HeaderMsg.INSERT_ONE_USER:
			BasicUser user = mItemFactory.assembleUser(resMsg.getResult());
			dispatchNewUser(user);
			break;
		}
	}
	
	private void classifyMsg(NotiMsg notiMsg) {
		switch(notiMsg.whatMsg()) {
		case HeaderMsg.STATUS_ONE_DEV:
//			Device d = mItemFactory.assembleDevice(notiMsg.getData());
//			dispatchChangedDeviceStatus(d);
			
			HomeAppliance ha = mItemFactory.assembleHomeAppliance(notiMsg.getData());
			if(ha != null) {
				dispatchChangedHomeApplianceStatus(ha);				
			}
			
			KWSensor sensor = mItemFactory.assembleSensor(notiMsg.getData());
			if(sensor != null) {
				dispatchChangedSensor(sensor);		
				Device d = new Device(
						"serial_number",
						Constants.DEV_TYPE_THERMO_HYGROMETH,
						"model_name",
						"manufacturer",
						"10cm",
						"10g",
						Constants.CONTROL_TYPE_TCP,
						Constants.LOC_LIVINGROOM,
						"100w",
						notiMsg.getData().toString());
				
				ThermoHygrometh th = new ThermoHygrometh(
						sensor.getTemperature(),
						sensor.getHumidity());
			
				d.setOnOffStatus(true);
				d.setFunc(th);
				dispatchChangedDeviceStatus(d);
			}
			
//			Device d = mItemFactory.assembleAirQuality(notiMsg.getData());
//			if(d != null) {
//				dispatchChangedDeviceStatus(d);
//			}
			
			break;
		case HeaderMsg.STATE_USER:
			SmartBandInfo smartband = mItemFactory.assembleSmartband(notiMsg.getData());
			if(smartband != null) {
				dispatchEmergency(smartband);			
			}
			break;
		}
	}
	
	/***************************************  receive message ***************************************/
	IResponseReceiver mashupResponseReceiver = new IResponseReceiver() {
		@Override
		public void receive(String responseMsg) {
			Log.i(TAG, "revice: " + responseMsg);

			ResMsg rspMsg = mItemFactory.assembleRspMsg(responseMsg);

			if(rspMsg != null) {
				if(rspMsg.getType() == Constants.TYPE_RES_SUC) {
					classifyMsg(rspMsg);
				}
			}
		}

		@Override
		public void onError(int msgId, String errMsg) {
			Log.d(TAG, "mashup db receive err: " + errMsg);
			
			switch(msgId) {
			case ResMsg.SELECT_ALL_DEV:
				List<Map<String, Object>> devList = DBController.getInstance().selectDev();

				Device[] devData = mItemFactory.assembleDevices(devList);

				if (devData != null) {
					devices.clear();
					for(Device d : Arrays.asList(devData)) {
						devices.add(d);
					}
					
					List<Service> l = mMashup.searchUsableService(devices, services);
					dispatchUsableServiceInfo(l);
				}
				
				break;
			case ResMsg.SELECT_ALL_SRV:
				List<Map<String, Object>> srvList = DBController.getInstance().selectSrv();

				Service[] serviceInfo = mItemFactory.assembleServices(srvList);

				if (serviceInfo != null) {
					services.clear();
					for(Service s : Arrays.asList(serviceInfo)) {
						services.add(s);
					}

					dispatchAllServiceInfo(services);

					List<Service> l = mMashup.searchUsableService(devices, services);
					dispatchUsableServiceInfo(l);
				}
				break;
			}
		}
	};
	
	IResponseReceiver userDBReceiver = new IResponseReceiver() {

		@Override
		public void onError(int id, String message) {
			Log.e(TAG, "user-db recieve err" + message);
		}

		@Override
		public void receive(String message) {
			Log.e(TAG, "user-db recieve: " + message);
		}
		
	};
	
	IResponseReceiver qrcodeReceiver = new IResponseReceiver() {

		@Override
		public void onError(int id, String message) {
			Log.e(TAG, "qrcode recieve err" + message);
		}

		@Override
		public void receive(String message) {
			ResMsg res = mItemFactory.assembleRspMsg(message);
			
			if(res.getType() == Constants.TYPE_RES_SUC) {
				Device dev = mItemFactory.assembleDevice(res.getResult());
				
				dispatchNewDeviceInfo(dev);

				
				requestAddDeviceInfo(dev);	
			}
		}
		
	};
	
	BroadcastReceiver gatewayResceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (action.equals(SocketService.ACTION_RECEVE)) {
				String result = intent.getStringExtra(SocketService.RESULT);
				Log.d("LCY", result);
				
				HeaderMsg header = mItemFactory.assembleHeaderMsg(result);
//				HomeAppliance header = mItemFactory.assembleHomeAppliance(result);
				switch(header.getType()) {
				case Constants.TYPE_RES_SUC:
					ResMsg res = mItemFactory.assembleRspMsg(result);
					classifyMsg(res);
					
					break;
				case Constants.TYPE_NOTI:
					NotiMsg noti = mItemFactory.assembleNotiMsg(result);
					classifyMsg(noti);

//					Log.d("NOTI_TEST", result);
//					classifyMsg(header);
					break;
				}
				
			} else if (action.equals(SocketService.ACTION_CONNECTED)) {
				requestAllDeviceStatus();
				requestAllServiceStatus();
				requestAllUserInfo();
				
				List<Map<String, Object>> phoneList = DBController.getInstance().selectPhone();
			
				PhoneInfo[] phone = mItemFactory.assemblePhoneInfo(phoneList);
				for(PhoneInfo p : phone) {
					notifyPhoneInfo(p);
				}
				
				DBController.getInstance().deletePhone();
			}
		}
	};
	
	public BroadcastReceiver getHomeserverResceiver() {
		return gatewayResceiver;
	}
}
