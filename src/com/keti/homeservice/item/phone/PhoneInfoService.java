package com.keti.homeservice.item.phone;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.keti.homeservice.control.HomeServiceController;
import com.keti.homeservice.item.PhoneInfo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 이 클래스는 필요한 유저정보(위치정보, 보유중인 폰 상태)를 받아서 HomServerController에 전달한다.
 * 
 * @author KETI_L
 * */
public class PhoneInfoService extends Service {
	private static final String TAG = "PhoneInfoService";
	
	/*
	 * 서비스 실행시 시작함
	 */
	LocationManager locationManager;
	LocationListener locationListener;

	public int code;
	public InputStream is = null;
	public String strLine = null;;
	public String strResult = null;
	public static String url = "http://susemi.pe.kr/location/insert.php";

	private static final int REC_SEC = 5000*2;
	private Thread UserInfoReceiver;
	private boolean onRunning;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		final Context context = getApplicationContext();
		
		TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		manager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		
		onRunning = true;
		UserInfoReceiver = new Thread(new Runnable() {
			@Override
	        public void run() {	
				while(onRunning) {
					try {
						Thread.sleep(REC_SEC);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            		
					PhoneLocation phonelocation = new PhoneLocation(context);//위치 
					PhoneTime phonetime = new PhoneTime();//시간
					PhoneState phonestate = new PhoneState(context);//통화중유무
					
		            // new insertDATA().execute(""); //웹서버에 전송
					// Log.d("TEST", str);
					

					PhoneInfo phoneinfo = new PhoneInfo(
							phonelocation.getLatitude(), 
							phonelocation.getLongitude(),
							phonelocation.getAddress(), 
							phonetime.getTime(), 
							phonestate.getPhoneState(),
							mState);

					HomeServiceController.getInstance().notifyPhoneInfo(phoneinfo);
				}
			}
		});
		UserInfoReceiver.start();
		
		return Service.START_NOT_STICKY;
	}

	class insertDATA extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			ArrayList<NameValuePair> values = new ArrayList<NameValuePair>();

			values.add(new BasicNameValuePair("username", "user1"));

			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url);
				httppost.setEntity(new UrlEncodedFormEntity(values, "utf-8"));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				Log.i(TAG, "Connection Successful");

			} catch (Exception e) {
				Log.i(TAG, e.toString());
			}

			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				while ((strLine = reader.readLine()) != null) {
					sb.append(strLine + "\n");
				}
				is.close();
				strResult = sb.toString();
				Log.i(TAG, "Result Retrieved");
				Log.i(TAG, strResult);
			} catch (Exception e) {
				Log.i(TAG, e.toString());
			}

			try {
				JSONObject json = new JSONObject(strResult);
				code = (json.getInt("code"));
				if (code == 1) {
					Log.i(TAG, "Data Successfully Inserted");
				} else {
					Log.i(TAG, "Data not inserted");
				}
			} catch (Exception e) {
				Log.i(TAG, e.toString());
			}

			return null;
		}

	}
	
	private String mState;
	PhoneStateListener phoneStateListener = new PhoneStateListener() {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			
			if (state == TelephonyManager.CALL_STATE_IDLE) {
				mState = "IDLE";			
			} else if (state == TelephonyManager.CALL_STATE_RINGING) {
				mState = "RINGING";
			} else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
				mState = "OFFHOOK";
			}
		}
	};
	
	PhoneLocationListener mPhoneLocationListener = new PhoneLocationListener() {

		@Override
		public void receiveLocation(double latitude, double longitude, String address) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		onRunning = false;	
		Log.i(TAG, "finish service");
	}
}
