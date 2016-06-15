package com.keti.homeservice; 

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.util.Log; 

public class PushWakeLock {     
	private static PowerManager.WakeLock sCpuWakeLock;    
	@SuppressWarnings("deprecation")
	private static KeyguardManager.KeyguardLock mKeyguardLock;    
	private static boolean isScreenLock;     
	
	@SuppressWarnings("deprecation")
	static void acquireCpuWakeLock(Context context) {        
		
		if (sCpuWakeLock != null) {            
			return;        
		}         
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);         
		sCpuWakeLock = pm.newWakeLock(                
				PowerManager.SCREEN_BRIGHT_WAKE_LOCK |                
				PowerManager.ACQUIRE_CAUSES_WAKEUP |                
				PowerManager.ON_AFTER_RELEASE, "hello");        
		
		sCpuWakeLock.acquire();        
	}
	
	static void releaseCpuLock() {        
		
		if (sCpuWakeLock != null) {            
			sCpuWakeLock.release();            
			sCpuWakeLock = null;        
		}    
	}
}



