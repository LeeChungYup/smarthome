package com.keti.homeservice.activity;

import com.keti.homeservice.R;
import com.keti.homeservice.control.HomeServiceController;
import com.keti.homeservice.item.Device;
import com.keti.homeservice.item.ThermoHygrometh;
import com.keti.homeservice.receiver.IBottomInfoReceiver;
import com.keti.homeservice.receiver.IMessageReceiver;
import com.keti.homeservice.util.Constants;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.widget.TextView;

/**
 * This class displays overall information and user interface about HomeService for user.
 * */
public class HomeActivity extends Activity {
	private TextView basicInfoText;

	private TextView serviceMsg;
	private TextView innerEnvMsg;
	private TextView homeMsg;

	private CenterLockHorizontalScrollview mainDeviceHView;
	private CenterLockHorizontalScrollview mainServiceHView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home);

		basicInfoText = (TextView) findViewById(R.id.basic_info);

		serviceMsg = (TextView) findViewById(R.id.tv_on_srv);
		innerEnvMsg = (TextView) findViewById(R.id.tv_inner_env);
		homeMsg = (TextView) findViewById(R.id.tv_home_msg);

		serviceMsg.setMovementMethod(new ScrollingMovementMethod());
		innerEnvMsg.setMovementMethod(new ScrollingMovementMethod());
		homeMsg.setMovementMethod(new ScrollingMovementMethod());

		mainDeviceHView = (CenterLockHorizontalScrollview) findViewById(R.id.main_dev_list);
		mainServiceHView = (CenterLockHorizontalScrollview) findViewById(R.id.main_srv_list);

		HomeServiceController.getInstance().addInformationReceiver(mIBottomInfoReceiver);
		HomeServiceController.getInstance().addInformationReceiver(mIMessageReceiver);
	}

	/**
	 * This receiver is used to show information at the bottom of screen.
	 * */
	IBottomInfoReceiver mIBottomInfoReceiver = new IBottomInfoReceiver() {

		@Override
		public void updateMainDevice(DeviceAdapter daviceAdaper) {
			mainDeviceHView.setAdapter(HomeActivity.this, daviceAdaper);
		}

		@Override
		public void updateMainService(MainServiceAdapter serviceAdapter) {
			mainServiceHView.setAdapter(HomeActivity.this, serviceAdapter);
		}

		@Override
		public void updateBasicInfo(Device device) {
			String deviceType = device.getDeviceType();
			if (deviceType.equals(Constants.DEV_TYPE_THERMO_HYGROMETH)) {
				ThermoHygrometh th = (ThermoHygrometh) device.getFunc();
				float tmp = th.getTemperature();
				float hum = th.getHumidity();

				basicInfoText.setText(String.format(getString(R.string.tem_humd), tmp, hum));
				innerEnvMsg.setText(String.format(getString(R.string.current_tmp_hum), tmp, hum));
			}
		}

	};

	/**
	 * this receiver is used to show installed services' information and status message.
	 * */
	IMessageReceiver mIMessageReceiver = new IMessageReceiver() {

		@Override
		public void onSrvInstallMessage(String message) {
			serviceMsg.setText(message);
		}

		@Override
		public void onStatusMessage(String message) {
			homeMsg.append(message + "\n");
			homeMsg.scrollTo(0, getScrollAmount());
		}

	};

	@Override
	protected void onDestroy() {
		super.onDestroy();

		HomeServiceController.getInstance().deleteInformationReceiver(mIBottomInfoReceiver);
		HomeServiceController.getInstance().deleteInformationReceiver(mIMessageReceiver);
	}

	private int getScrollAmount() {
		int scroll_amount = (int) ((homeMsg.getLineCount() * homeMsg.getLineHeight())
				- (homeMsg.getBottom() - homeMsg.getTop())) - (int) homeMsg.getTextSize();
		return scroll_amount;
	}
}
