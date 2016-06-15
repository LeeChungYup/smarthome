package com.keti.homeservice.item.phone;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * ��ȭ�� ���� �ƴ��� �޾ƿ��� Ŭ����
 *
 */
public class PhoneCallState {
	
	@SuppressWarnings("unused")
	private Context context;
	private TelephonyManager manager;
	
	public PhoneCallState(Context context, PhoneStateListener phoneStateListener){
		this.context = context;
		manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		manager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		
	}
}
