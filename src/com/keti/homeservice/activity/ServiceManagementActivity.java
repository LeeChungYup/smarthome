package com.keti.homeservice.activity;

import java.util.List;

import com.keti.homeservice.R;
import com.keti.homeservice.control.HomeServiceController;
import com.keti.homeservice.item.Device;
import com.keti.homeservice.item.Service;
import com.keti.homeservice.item.ThermoHygrometh;
import com.keti.homeservice.receiver.IBottomInfoReceiver;
import com.keti.homeservice.receiver.IMashupReceiver;
import com.keti.homeservice.receiver.IServiceStatusReceiver;
import com.keti.homeservice.util.Constants;
import com.keti.homeservice.util.Utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class displays information and user interface about services(scenario) for user.
 * */
public class ServiceManagementActivity extends Activity implements OnClickListener {
	private TextView basicInfoText;

	private ImageButton btnRefresh;
	private ImageButton btnAdd;
	private ImageButton btnDelete;

	private ListView mashupDBViews;
	private ListView installedServiceViews;

	private Service installedItem;
	private Service deletedItem;

	private MainServiceAdapter mainServiceAdapter;
	private ServiceAdapter mashupServiceAdapter;
	private ServiceAdapter installServiceAdapter;
	private DeviceAdapter mainDeviceAdapter;

	private CenterLockHorizontalScrollview mainDeviceHView;
	private CenterLockHorizontalScrollview mainServiceHView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // ���� ������ ���ִ°�

		setContentView(R.layout.service_management);
		
		/******************************* ȭ�� �ϴ� ���� ���񽺸� ǥ���ϱ� ���� �ʱ�ȭ **************************************/
		mainDeviceHView = (CenterLockHorizontalScrollview) findViewById(R.id.main_dev_list);
		mainServiceHView = (CenterLockHorizontalScrollview) findViewById(R.id.main_srv_list);

		mainDeviceHView.setAdapter(this, mainDeviceAdapter);
		mainServiceHView.setAdapter(this, mainServiceAdapter);

		basicInfoText = (TextView) findViewById(R.id.basic_info);

		/******************************* �Ž��� ��� ���񽺸� ǥ���ϱ����� ������ �ʱ�ȭ **************************************/
		mashupServiceAdapter = new ServiceAdapter(getLayoutInflater());
		installServiceAdapter = new ServiceAdapter(getLayoutInflater());
		mainServiceAdapter = new MainServiceAdapter(getLayoutInflater());
		mainDeviceAdapter = new DeviceAdapter(getLayoutInflater());

		/******************************* �Ž��� ��� ��ϰ� ��ġ�� ���� ����� ǥ���ϱ� ���� �ʱ�ȭ **************************************/
		mashupDBViews = (ListView) findViewById(R.id.list_mashup);
		installedServiceViews = (ListView) findViewById(R.id.list_install_dev);
		mashupDBViews.setSelector(new ColorDrawable(0xff78cce8));
		installedServiceViews.setSelector(new ColorDrawable(0xff78cce8));
		mashupDBViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				installedItem = (Service) mashupDBViews.getItemAtPosition(position);
			}

		});

		installedServiceViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				deletedItem = (Service) installedServiceViews.getItemAtPosition(position);
			}

		});
		
		/******************************* ���� ������ �ʿ��� ��ư�� �ʱ�ȭ �ϰ� �����ʸ� ��� **************************************/
		btnAdd = (ImageButton) findViewById(R.id.btn_add);
		btnDelete = (ImageButton) findViewById(R.id.btn_delete);
		btnRefresh = (ImageButton) findViewById(R.id.btn_refresh);

		btnAdd.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnRefresh.setOnClickListener(this);

		/******************************* �ʿ� ������ �޾ƿ� ���ù� ��� �� ���� ���� ��û **************************************/
		HomeServiceController.getInstance().addInformationReceiver(mIMashupReceiver);
		HomeServiceController.getInstance().addInformationReceiver(mIBottomInfoReceiver);
		HomeServiceController.getInstance().addInformationReceiver(mIServiceStatusReceiver);
		HomeServiceController.getInstance().requestAllServiceInfo();
	}

	IBottomInfoReceiver mIBottomInfoReceiver = new IBottomInfoReceiver() {

		@Override
		public void updateMainDevice(DeviceAdapter daviceAdaper) {
			mainDeviceHView.setAdapter(ServiceManagementActivity.this, daviceAdaper);
		}

		@Override
		public void updateMainService(MainServiceAdapter serviceAdapter) {
			mainServiceHView.setAdapter(ServiceManagementActivity.this, serviceAdapter);
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

	IServiceStatusReceiver mIServiceStatusReceiver = new IServiceStatusReceiver() {

		@Override
		public void onAddInstallService(Service service) {
			installService(service);
		}

		@Override
		public void onStartService(Service service) {
			if(installService(service)) {
				String name = Utils.getInstance().toSrvKorean(service.getServiceName());
				String format = ServiceManagementActivity.this.getString(R.string.run_service);
				String msg = String.format(format, name);
				
				HomeServiceController.getInstance().dispatchStatusMessage(msg);
			}
		}

		@Override
		public void onStopService(Service service) {
			if(deleteService(service)) {
				String name = Utils.getInstance().toSrvKorean(service.getServiceName());
				String format = ServiceManagementActivity.this.getString(R.string.stop_service);
				String msg = String.format(format, name);
				HomeServiceController.getInstance().dispatchStatusMessage(msg);
			}
		}

	};

	IMashupReceiver mIMashupReceiver = new IMashupReceiver() {
		@Override
		public void onReceiveServiceInfo(Service service) {
			service.setUsable(false);
			mashupServiceAdapter.addService(service);
			mashupDBViews.setAdapter(mashupServiceAdapter);
		}

		@Override
		public void onReceiveAllServiceInfo(List<Service> services) {
			mainServiceAdapter.exchangeService(services);
			mashupServiceAdapter.exchangeService(services);
			installServiceAdapter.exchangeService(services);
			
			for (Service service : services) {
				service.setUsable(false);
				mashupServiceAdapter.addService(service);
			}

			mainServiceHView.setAdapter(ServiceManagementActivity.this, mainServiceAdapter);
			mashupDBViews.setAdapter(mashupServiceAdapter);
			installedServiceViews.setAdapter(installServiceAdapter);
		}

		@Override
		public void onReceiveUsableServiceInfo(List<Service> services) {
			for (Service s : services) {
				mashupServiceAdapter.markUsableService(s);
				mainServiceAdapter.addService(s);	
			}
			mashupDBViews.setAdapter(mashupServiceAdapter);
			mainServiceHView.setAdapter(ServiceManagementActivity.this, mainServiceAdapter);

			HomeServiceController.getInstance().dispatchMainService(mainServiceAdapter);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_refresh:
			refreshService();
			break;
		case R.id.btn_add:
			if(installedItem != null) {
				if (!installedItem.isUsable()) {
					Toast.makeText(this, getString(R.string.short_of_dev), Toast.LENGTH_SHORT).show();
				} else {
					if (!installedItem.isInstalled()) {
						HomeServiceController.getInstance().requestStartService(installedItem);
					} else {
						Toast.makeText(this, getString(R.string.install_already_srv), Toast.LENGTH_SHORT).show();
					}
				}
			}
			break;
		case R.id.btn_delete:
			if (deletedItem != null && deletedItem.isInstalled()) {
				HomeServiceController.getInstance().requestStopService(deletedItem);
			}
			break;
		}
	}

	/**
	 * ��ġ�� ���� ��Ͽ� ���񽺸� ��ġ
	 * 
	 * @param service ��ġ�� ����
	 * */
	private boolean installService(Service service) {
		if (service != null) {
			if (installServiceAdapter.addService(service)) {
				mashupServiceAdapter.setInstalled(service, true);
				mainServiceAdapter.setInstalled(service, true);
				installServiceAdapter.setInstalled(service, true);
				
				installedServiceViews.setAdapter(installServiceAdapter);

				HomeServiceController.getInstance().dispatchMainService(mainServiceAdapter);
				HomeServiceController.getInstance().dispatchServiceMessage(installServiceAdapter.getServiceList());

				Toast.makeText(this, getString(R.string.install_srv), Toast.LENGTH_SHORT).show();
			
				return true;
			}
		}
		return false;
	}

	/**
	 * ��ġ�� ���� ��Ͽ��� ���񽺸� ����
	 * 
	 * @param service ������ ����
	 * */
	private boolean deleteService(Service service) {
		if (service != null) {
			if (installServiceAdapter.deleteService(service)) {
				mashupServiceAdapter.setInstalled(service, false);
				mainServiceAdapter.setInstalled(service, false);
				installServiceAdapter.setInstalled(service, false);
				
				installedServiceViews.setAdapter(installServiceAdapter);
				
				HomeServiceController.getInstance().dispatchMainService(mainServiceAdapter);
				HomeServiceController.getInstance().dispatchServiceMessage(installServiceAdapter.getServiceList());
				service = null;
				
				Toast.makeText(this, getString(R.string.suc_delete_srv), Toast.LENGTH_SHORT).show();
			
				return true;
			}
		}
		return false;
	}

	/**
	 * �Ž��� ��񿡼� ���񽺸� �ٽ� ��û�ϱ� ���� �Լ�
	 * 
	 * */
	private void refreshService() {
		installedItem = null;
		HomeServiceController.getInstance().requestAllServiceInfo();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		HomeServiceController.getInstance().deleteInformationReceiver(mIMashupReceiver);
		HomeServiceController.getInstance().deleteInformationReceiver(mIBottomInfoReceiver);
		HomeServiceController.getInstance().deleteInformationReceiver(mIServiceStatusReceiver);
	}
}
