package com.keti.homeservice;

import com.keti.homeservice.control.HomeServiceController;
import com.keti.homeservice.item.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class InputActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.input);
		
		final EditText homeServerIp = (EditText) findViewById(R.id.server_ip_edit);
		final EditText homeServerPort = (EditText) findViewById(R.id.server_port_edit);
		final EditText mahupDbUrl = (EditText) findViewById(R.id.mashup_db_url_edit);
		
		final EditText id = (EditText) findViewById(R.id.edit_id);
		final EditText password = (EditText) findViewById(R.id.edit_password);
		
		Button connectButton = (Button) findViewById(R.id.login_btn);
		connectButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				HomeServiceController.homeserverIp = homeServerIp.getText().toString();
				HomeServiceController.homeserverPort = Integer.parseInt(homeServerPort.getText().toString());
				HomeServiceController.mashupdbUrl = mahupDbUrl.getText().toString();
//				User.setID(id.getText().toString());
				User.setID("junhwanjang");
				User.setPassword(password.getText().toString());
				
				Intent introActivity = new Intent(InputActivity.this, Intro.class);
				InputActivity.this.startActivity(introActivity);
				
				finish();
			}
			
		});
	}
}
