package com.keti.homeservice.activity;

import java.util.ArrayList;
import java.util.List;

import com.keti.homeservice.R;
import com.keti.homeservice.item.Service;
import com.keti.homeservice.util.Utils;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ServiceAdapter extends BaseAdapter {
	protected List<Service> services;
	private LayoutInflater inflater;

	public ServiceAdapter(LayoutInflater inflater) {
		services = new ArrayList<Service>();
		this.inflater = inflater;
	}

	/**
	 * 리스트에 모든 서비스 제어
	 * */
	public void clearService() {
		services.clear();
	}
	
	/**
	 * 기존 서비스의 아이디와 동일한 서비스의 객체로 바꾼다.
	 * 
	 * @param excItems 기존의 서비스과 교환할 서비스 리스트
	 * */
	public void exchangeService(List<Service> excItems) {
		for(int i=0; i<services.size(); i++) {
			for(int j=0; j<excItems.size(); j++) {
				Service ori = services.get(i);
				Service exc = excItems.get(i);
				if(ori.getServiceId().equals(exc.getServiceId())) {
					exc.setInstalled(ori.isInstalled());
					services.remove(i);
					services.add(i, exc);
				}
			}
		}
	}
	
	/**
	 * 현재 리스트에 중복된 서비스가 있는지 검사.
	 * 
	 * @param s 중복여부를 검사할 서비스
	 * */
	public Boolean checkDuplicateService(Service s) {
		int tServiceId = Integer.parseInt(s.getServiceId());

		for (Service srv : services) {
			int servuceId = Integer.parseInt(srv.getServiceId());
			if (tServiceId == servuceId) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 현재 서비스를 이용할 수 있는지 여부를 표시.
	 * 
	 * @param service 서비스 이용여부를 표시할 서비스 
	 * */
	public void markUsableService(Service service) {
		String targe = service.getServiceId();
		for(Service s : services) {
			if(targe.equals(s.getServiceId())) {
				s.setUsable(true);
			}
		}
	}

	/**
	 * 리스트에 서비스를 추가.
	 * 
	 * @param service 추가할 서비스
	 * @return true 서비스 추가 성공, false 서비스 추가 실패
	 * */
	public boolean addService(Service service) {
		String targe = service.getServiceId();
		
		for(Service s : services) {
			if(targe.equals(s.getServiceId())) {
				return false;
			}
		}

		return services.add(service);
	}
	
	/**
	 * 리스트에 서비스를 제거.
	 * 
	 * @param service 제거할 서비스
	 * @return true 서비스 제거 성공, false 서비스 제거 실패
	 * */
	public boolean deleteService(Service service) {
		String targe = service.getServiceId();
		
		for(Service s : services) {
			if(targe.equals(s.getServiceId())) {
				return services.remove(s);
			}
		}
		
		return false;
	}
	
	public String getServiceList() {
		String list = "";
		for(int i=0; i<services.size(); i++) {
			Service s = services.get(i);
			list += Utils.getInstance().toSrvKorean(s.getServiceName());
			
			if(i == services.size()-1)
				break;
			
			list += "\n";
		}
		
		return list;
	}

	@Override
	public boolean isEmpty() {
		return services.isEmpty();
	}
	
	@Override
	public int getCount() {
		return services.size(); // datas의 개수를 리턴
	}

	@Override
	public Object getItem(int position) {
		return services.get(position);// datas의 특정 인덱스 위치 객체 리턴.
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	public void setInstalled(Service service, boolean installed) {
		String id = service.getServiceId();
		for(Service s : services) {
			if(id.equals(s.getServiceId())) {
				s.setInstalled(installed);
				break;
			}
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.customlistview, null);
		}
		
		final Service s = services.get(position);
		
		TextView textview_category = (TextView) convertView.findViewById(R.id.textview_category);
		TextView textview_servicename = (TextView) convertView.findViewById(R.id.textview_servicename);
		TextView textview_serviceversion = (TextView) convertView.findViewById(R.id.textview_serviceversion);

		if(!s.isUsable()) {
			textview_category.setTextColor(Color.GRAY);
			textview_servicename.setTextColor(Color.GRAY);
			textview_serviceversion.setTextColor(Color.GRAY);
		} else {
			textview_category.setTextColor(Color.BLACK);
			textview_servicename.setTextColor(Color.BLACK);
			textview_serviceversion.setTextColor(Color.BLACK);
		}
			
		textview_category.setText(Utils.getInstance().toCtgKorean(s.getCategory()));
		textview_servicename.setText(Utils.getInstance().toSrvKorean(s.getServiceName()));
		textview_serviceversion.setText(services.get(position).getServiceVersion() + " / " + s.getServiceInfo());

		
		return convertView;
	}

}