package com.keti.homeservice;

import com.keti.homeservice.activity.DeviceControlActivity;
import com.keti.homeservice.activity.HomeActivity;
import com.keti.homeservice.activity.ServiceManagementActivity;
import com.keti.homeservice.activity.SettingActivity;
import com.keti.homeservice.control.DBController;
import com.keti.homeservice.control.HomeServiceController;
import com.keti.homeservice.control.SocketService;
import com.keti.homeservice.item.BasicUser;
import com.keti.homeservice.item.SmartBandInfo;
import com.keti.homeservice.receiver.IUserReceiver;
import com.keti.homeservice.util.Constants;
import com.keti.homeservice.util.Utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TabHost;

@SuppressWarnings({ "deprecation" })
public class StartActivity extends TabActivity {
	public static final String GIT_TEST_CODE = "GIT_TEST_CODE";
	
	private static final String TAG = "StartActivity";
	
	private Intent scoketService;
	private Intent phoneInfoService;
	
	private boolean isEmergency;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		TabHost tabHost = getTabHost();

		tabHost.addTab(tabHost.newTabSpec("home")
				.setIndicator(getString(R.string.home), getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(new Intent(this, HomeActivity.class)));

		tabHost.addTab(tabHost.newTabSpec("device_control")
				.setIndicator(getString(R.string.dev_ctr), getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(new Intent(this, DeviceControlActivity.class)));

		tabHost.addTab(tabHost.newTabSpec("service_management")
				.setIndicator(getString(R.string.srv_mng), getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(new Intent(this, ServiceManagementActivity.class)));

		tabHost.addTab(tabHost.newTabSpec("setting")
				.setIndicator(getString(R.string.setting), getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(new Intent(this, SettingActivity.class)));

		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget()
					.getChildAt(i)
					.setBackgroundColor(Color.parseColor("#FFFFFF"));
		}

		/**
		 * To initialize
		 * */
		Utils.getInstance().setContext(getApplicationContext());
		HomeServiceController.getInstance();
		DBController.getInstance().createTables();
		
		tabHost.setCurrentTab(0);
		tabHost.setCurrentTab(1);
		tabHost.setCurrentTab(2);
		tabHost.setCurrentTab(3);
		tabHost.setCurrentTab(0);

		scoketService = new Intent(this, SocketService.class);
		startService(scoketService);
//		phoneInfoService = new Intent(this, PhoneInfoService.class);
//		startService(phoneInfoService);

		registerReceiver(
				HomeServiceController.getInstance().getHomeserverResceiver(), 
				new IntentFilter(SocketService.ACTION_CONNECTED));
		registerReceiver(
				HomeServiceController.getInstance().getHomeserverResceiver(),
				new IntentFilter(SocketService.ACTION_RECEVE));
		
		HomeServiceController.getInstance().addInformationReceiver(mIUserReceiver);
		
		isEmergency = false;
	}

	IUserReceiver mIUserReceiver = new IUserReceiver() {

		@Override
		public void onAddUser(BasicUser user) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUsers(BasicUser[] users) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onEmergency(SmartBandInfo smartband) {
			if(smartband != null && smartband.getState() == Constants.USER_STATE_EMERGENCY) {
				if(!isEmergency) {
					isEmergency = true;
					PushWakeLock.acquireCpuWakeLock(StartActivity.this);
					
					Builder mAlertDialog = new AlertDialog.Builder(StartActivity.this);
					mAlertDialog.setOnDismissListener(new OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialog) {
							isEmergency = false;
						}
					});
					mAlertDialog.setTitle("[ Emergency Message ]");
					mAlertDialog.setMessage("사용자가 쓰러졌습니다.");
					mAlertDialog.setNeutralButton("Close", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							isEmergency = false;
						}
					}).show();
				}			
			}

		}	
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();

		DBController.getInstance().closeDB();

		stopService(scoketService);
//		stopService(phoneInfoService);
		unregisterReceiver(HomeServiceController.getInstance().getHomeserverResceiver());
		
		Log.d(TAG, "finish activity");
	}
}
