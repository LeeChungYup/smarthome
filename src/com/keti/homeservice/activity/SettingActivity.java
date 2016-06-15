package com.keti.homeservice.activity;

import com.google.zxing.client.android.integration.IntentIntegrator;
import com.google.zxing.client.android.integration.IntentResult;
import com.keti.homeservice.R;
import com.keti.homeservice.control.HomeServiceController;
import com.keti.homeservice.item.BasicUser;
import com.keti.homeservice.item.Device;
import com.keti.homeservice.item.SmartBandInfo;
import com.keti.homeservice.item.ThermoHygrometh;
import com.keti.homeservice.receiver.IBottomInfoReceiver;
import com.keti.homeservice.receiver.IQRCodeReceiver;
import com.keti.homeservice.receiver.IUserReceiver;
import com.keti.homeservice.util.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This class displays information and user interface about setting for user.
 * */
public class SettingActivity extends Activity implements OnClickListener {
	private static final String TAG = "SettingActivity";

	private TextView basicInfoText;
	
	private TextView addDeviceInfoText;
	@SuppressWarnings("unused")
	private TextView addServiceInfoText;
	@SuppressWarnings("unused")
	private TextView addUserInfoText;

	private CenterLockHorizontalScrollview mainDeviceHView;
	private CenterLockHorizontalScrollview mainServiceHView;
	
	private static final int REQ_USER_ADD_DIALOG = 1; 
	private static final int REQ_SERVICE_ADD_DIALOG = 2; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		mainDeviceHView = (CenterLockHorizontalScrollview) findViewById(R.id.main_dev_list);
		mainServiceHView = (CenterLockHorizontalScrollview) findViewById(R.id.main_srv_list);
		
		basicInfoText = (TextView) findViewById(R.id.basic_info);
		
		addDeviceInfoText = (TextView) findViewById(R.id.add_dev_info);
		addServiceInfoText = (TextView) findViewById(R.id.add_service_info);
		addUserInfoText = (TextView) findViewById(R.id.add_user_info);

		Button qrcodeButton = (Button) findViewById(R.id.btn_qrcode);
		Button addUserButton = (Button) findViewById(R.id.btn_add_user);
		Button addServiceButton = (Button) findViewById(R.id.btn_add_srv);
		
		qrcodeButton.setOnClickListener(this);
		addServiceButton.setOnClickListener(this);
		addUserButton.setOnClickListener(this);

		HomeServiceController.getInstance().addInformationReceiver(mIBottomInfoReceiver);
		HomeServiceController.getInstance().addInformationReceiver(mIQRCodeReceiver);
		HomeServiceController.getInstance().addInformationReceiver(mIUserReceiver);
	}
	
	/**
	 * This receiver is used to show information at the bottom of screen.
	 * */
	IBottomInfoReceiver mIBottomInfoReceiver = new IBottomInfoReceiver() {

		@Override
		public void updateMainDevice(DeviceAdapter daviceAdaper) {
			mainDeviceHView.setAdapter(SettingActivity.this, daviceAdaper);
		}

		@Override
		public void updateMainService(MainServiceAdapter serviceAdapter) {
			mainServiceHView.setAdapter(SettingActivity.this, serviceAdapter);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// QR코드/바코드를 스캔한 결과 값을 가져옴
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

		switch (resultCode) {
		case RESULT_OK:
			String modelName = result.getContents();
			
			Log.d("QRCODE", "model name: " + modelName);
			
			HomeServiceController.getInstance().requestQRCode(modelName);

			break;
		case RESULT_CANCELED:
			Log.e(TAG, "QR Reader canceled");
			break;
			
		case UserAddDialog.USER_ADD_OK:
			
			break;
			
		case UserAddDialog.USER_ADD_CANCEL:
			
			break;
			
		case ServiceAddDialog.SERVICE_ADD_OK:
			
			break;
			
		case ServiceAddDialog.SERVICE_ADD_CANCEL:
			
			break;
		}
	}
	
	IQRCodeReceiver mIQRCodeReceiver = new IQRCodeReceiver() {

		@Override
		public void onGetDeviceInfo(Device homeappliance) {
			addDeviceInfoText.setGravity(Gravity.LEFT | Gravity.CENTER_HORIZONTAL);
			addDeviceInfoText.setText(homeappliance.toString());
		}
		
	};
	
	IUserReceiver mIUserReceiver = new IUserReceiver() {

		@Override
		public void onAddUser(BasicUser user) {
			Log.d(TAG, "Add User: " + user.toString());
			String info = addUserInfoText.getText().toString();
			addUserInfoText.setGravity(Gravity.LEFT | Gravity.CENTER_HORIZONTAL);
			addUserInfoText.setText(info + "\t\t\t" + user.getUsername() + "\n\n");
		}

		@Override
		public void onUsers(BasicUser[] users) {
//			Log.d(TAG, "All Users: " + users.toString());
			String info = "사용자 리스트\n\n\n";
			for(int i=0; i<users.length; i++) {
				info += "\t\t\t" + users[i].getUsername() + "\n\n";
			}
			
			addUserInfoText.setGravity(Gravity.LEFT | Gravity.CENTER_HORIZONTAL);
			addUserInfoText.setText(info);
		}

		@Override
		public void onEmergency(SmartBandInfo smartband) {
		}
		
	};

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_qrcode:
			// QR코드/바코드 스캐너를 구동
			IntentIntegrator.initiateScan(this);
			break;
		case R.id.btn_add_srv:
//			String ServiceMsg = getString(R.string.no_service);
//			Toast.makeText(this, ServiceMsg, Toast.LENGTH_SHORT).show();
			startActivityForResult(
					new Intent(SettingActivity.this, ServiceAddDialog.class),
					REQ_SERVICE_ADD_DIALOG);
			break;
		case R.id.btn_add_user:
//			String UserMsg = getString(R.string.no_service);
//			Toast.makeText(this, UserMsg, Toast.LENGTH_SHORT).show();
			startActivityForResult(
					new Intent(SettingActivity.this, UserAddDialog.class),
					REQ_USER_ADD_DIALOG);
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();

		HomeServiceController.getInstance().deleteInformationReceiver(mIBottomInfoReceiver);
	}
}
