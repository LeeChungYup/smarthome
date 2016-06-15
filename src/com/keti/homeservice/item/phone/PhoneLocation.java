package com.keti.homeservice.item.phone;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;

/**
 * 
 * ���� �浵�� �ѱ� �ּҰ��� �������� Ŭ����
 */
public class PhoneLocation {
	
	private Context context;
	
	@SuppressWarnings("unused")
	private double oldLatitude;
	
	private double latitude;
	private double longitude;
	private String address=null;
	private LocationManager locationManager;
	
	@SuppressWarnings("unused")
	private PhoneLocationListener mPhoneLocationListener;
	
	
	public PhoneLocation(Context context) {
		this.context = context;
		setLocation();
	}
	
	@SuppressWarnings("unused")
	private void checkLocation() {
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					setLocation();
					
					//if �ٲ��
//					mPhoneLocationListener.receiveLocation(latitude, longitude, address);
				}
			}
			
		});
	}
	
	/*
	 * ����  latitude
	 */
	public double getLatitude(){
		return latitude;
	}
	
	/*
	 *  �浵 longitude 
	 */
	public double getLongitude(){
		return longitude;
	}
	
	/*
	 *  �ѱ��ּ� ã�� �޼���
	 */
	public String getAddress(){
		return address;		
	}
	
	private void updateLocation(Location location){
		if(location != null){
			latitude = location.getLatitude();
			longitude = location.getLongitude();
		}
	}
	
	private void setLocation() {
		
	    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();  
        criteria.setPowerRequirement(Criteria.POWER_LOW);  
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);   
        String locProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(locProvider);
        updateLocation(location);  
//        getKoreaAddress(latitude,longitude);
        getEngAddress(latitude,longitude);
	}
	
	
	private String getEngAddress(double lat, double lng) {                                    
        StringBuffer bf = new StringBuffer();
        Geocoder geocoder = new Geocoder(context, Locale.US);
        List<Address> list;
        Address addr;
        try {
          if (geocoder != null) {
                list = geocoder.getFromLocation(lat, lng, 1);
               if (list != null && list.size() > 0) {
                    addr = list.get(0);
                    address = addr.getFeatureName() + ", " + 
                    		addr.getThoroughfare() + ", " + 
                    		addr.getLocality() + ", " + 
                    		addr.getAdminArea() + ", " + 
                    		addr.getCountryName();
                    }
            }  
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return bf.toString();	
    } 


	/**
     * �ѱ� �ּ� ã�� �޼ҵ�
     */
	@SuppressWarnings("unused")
	private String getKoreaAddress(double lat, double lng) {                                    
        StringBuffer bf = new StringBuffer();
        Geocoder geocoder = new Geocoder(context, Locale.KOREA);
        List<Address> list;
        Address addr;
        try {
          if (geocoder != null) {
                // ����° �μ��� �ִ������ε� �ϳ��� ���Ϲ޵��� �����ߴ�
                list = geocoder.getFromLocation(lat, lng, 1);
                // ������ �����ͷ� �ּҰ� ���ϵ� �����Ͱ� ������
               if (list != null && list.size() > 0) {
                    // �ּ�
                    addr = list.get(0);
                    address= addr.getCountryName()+" "+ addr.getAdminArea()+" "+addr.getLocality()+" "+addr.getThoroughfare()+" "+addr.getFeatureName();
                    // ������ �ּ� ������ (����/�浵 ���� ����)    
                    }
            }  
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bf.toString();	
    } 
	
}
