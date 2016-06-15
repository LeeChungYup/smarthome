package com.keti.homeservice.control;

import com.keti.socket.AsynClientSocket;
import com.keti.socket.IReceiveListner;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

/**
 * 이 클래스는 앱과 게이트웨이의 통신을 제어하는 서비스이다.
 * 
 * 게이트웨이로 부터 받은 정보는 HomeServerController에게 전달되고,
 * HomeServerController로 부터 받은 정보를 게이트웨이에 전달한다.
 * 
 * @author KETI_L
 * */
public class SocketService extends Service {
	private static final String TAG = "SocketService";

	public static final String ACTION_SEND = "com.keti.homeservice.mashup.SocketService.socket_send";
	public static final String ACTION_RECEVE = "com.keti.homeservice.mashup.SocketService.socket_receive";
	public static final String ACTION_CONNECTED = "com.keti.homeservice.mashup.SocketService.socket_connected";

	public static final String RESULT = "RESULT";

	public static boolean isConnected = false;
	
	private AsynClientSocket mAsynClientSocket;

	@Override
	public IBinder onBind(Intent i) {
		return null;
	}

	/**
	 * 서비스 시작시 소켓을 초기화 하고 접속을 시도한다.
	 * */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		mAsynClientSocket = new AsynClientSocket("192.168.0.12", 8080);
		mAsynClientSocket = new AsynClientSocket("192.168.0.33", 7771);
//		mAsynClientSocket = new AsynClientSocket("115.95.190.117", 2727);
//		mAsynClientSocket = new AsynClientSocket(HomeServiceController.homeserverIp, HomeServiceController.homeserverPort);
		mAsynClientSocket.registerReceiveListner(mIReceiveListner);
		mAsynClientSocket.connect();

		getApplicationContext().registerReceiver(sendReceiver, new IntentFilter(ACTION_SEND));

		return Service.START_NOT_STICKY;
	}

	/**
	 * HomeServerController로 부터 받은 정보를 게이트웨이에 전달한다.
	 * */
	BroadcastReceiver sendReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (action.equals(ACTION_SEND)) {
				String msg = intent.getStringExtra(RESULT);

				mAsynClientSocket.sendMessage(msg);
			}
		}
	};

	/**
	 * 게이트웨이로 부터 받은 정보(메시지, 접속상태, 에러정보)를  HomeServerController에게 전달한다.
	 * */
	IReceiveListner mIReceiveListner = new IReceiveListner() {

		@Override
		public void receiveMsg(String rMsg) {
			Log.i(TAG, "receive msg: " + rMsg);

			Intent i = new Intent(ACTION_RECEVE);
			i.putExtra(RESULT, rMsg);
			sendBroadcast(i);
		}

		@Override
		public void onConnected(String msg) {
			Log.i(TAG, "connection state: " + msg);

			isConnected = true;
			
			Intent i = new Intent(ACTION_CONNECTED);
			i.putExtra(RESULT, msg);
			sendBroadcast(i);
		}
		
		@Override
		public void onDisconnected(String msg) {
			isConnected = false;
		}

		@Override
		public void onError(int errId, String errMsg) {
			Log.e(TAG, "error id:" + errId + ", error message: " + errMsg);

			switch (errId) {
			case AsynClientSocket.ERR_INPUT:
			case AsynClientSocket.ERR_OUTPUT:
			case AsynClientSocket.ERR_SOCKET:
				mAsynClientSocket.disconnect();
				mAsynClientSocket.connect();
				isConnected = false;
				break;
			case AsynClientSocket.ERR_CONNECT:
				mAsynClientSocket.connect();
				break;
			}
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();

		mAsynClientSocket.disconnect();
		mAsynClientSocket = null;
		getApplicationContext().unregisterReceiver(sendReceiver);
		
		Log.i(TAG, "Destroied");
	}
}
