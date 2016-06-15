package com.keti.homeservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

public class Intro extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.intro);

		Toast.makeText(this, this.getString(R.string.intro_msg), Toast.LENGTH_SHORT).show();

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				
				Intent startActivity = new Intent(Intro.this, StartActivity.class);
				startActivity(startActivity);

				finish();
			}
		}, 2000);	
	}
	
	
}
