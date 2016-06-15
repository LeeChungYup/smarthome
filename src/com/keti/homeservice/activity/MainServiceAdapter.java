package com.keti.homeservice.activity;

import com.keti.homeservice.R;
import com.keti.homeservice.control.HomeServiceController;
import com.keti.homeservice.item.Service;
import com.keti.homeservice.util.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainServiceAdapter extends ServiceAdapter {
	private static final int MAX_MAIN_SERVICE = 3;
	
	private LayoutInflater inflater;

	public MainServiceAdapter(LayoutInflater inflater) {
		super(inflater);

		this.inflater = inflater;
	}
	
	/**
	 * 메인 서비스의 최대 갯수를 초과하지 않은 한도에서 서비스를 추가
	 * 
	 * @param service 추가할 서비스
	 * */
	@Override
	public boolean addService(Service service) {
		if(services.size() >= MAX_MAIN_SERVICE) {
			return false;
		}
		
		return super.addService(service);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.service_item, null);
		}
		final Service s = services.get(position);
		
		TextView serviceText = (TextView) convertView.findViewById(R.id.srv_name);
		
		Button serviceButton = (Button) convertView.findViewById(R.id.srv_btn);
		serviceButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(s.isInstalled()) {
					s.setInstalled(false);
					HomeServiceController.getInstance().requestStopService(s);
				} else {
					s.setInstalled(true);
					HomeServiceController.getInstance().requestStartService(s);
				}
			}
			
		});
		
		String serviceName = Utils.getInstance().toSrvKorean(s.getServiceName());
		if(s.isInstalled()) {
			serviceButton.setBackgroundResource(R.drawable.pause_btn);
		} else {
			serviceButton.setBackgroundResource(R.drawable.play_btn);
		}
		serviceText.setText(serviceName);
		
		return convertView;
	}

}