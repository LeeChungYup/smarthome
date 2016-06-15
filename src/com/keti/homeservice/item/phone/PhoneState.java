package com.keti.homeservice.item.phone;

import android.content.Context;
import android.media.AudioManager;

/**
 * ���Ҹ����� �������� �������� �޾ƿ��� Ŭ����
 */
public class PhoneState {

	private Context context;
	private String state;
	
	public PhoneState(Context context) {
		this.context = context;
	}
	
	public String getPhoneState() {
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		
		if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE ) {
			state ="VIBRATE";
		}else if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL){
			state ="RING";
		}else if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT){
			state ="SILENT";
		}
		return state;
	}
}
