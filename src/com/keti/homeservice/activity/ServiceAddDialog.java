package com.keti.homeservice.activity;

import com.keti.homeservice.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

public class ServiceAddDialog extends Activity implements OnClickListener {
//	private static final String TAG = "ServiceAddDialog";
	
	public static final int SERVICE_ADD_OK = 100;
	public static final int SERVICE_ADD_CANCEL = 111;
	
	private Button btRegister;
	private Button btCancel;
	
	private ListView serviceList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.service_add_dialog);
        
		btRegister = (Button) findViewById(R.id.btn_confirm);
		btCancel = (Button) findViewById(R.id.btn_cancel);
		btRegister.setOnClickListener(this);
		btCancel.setOnClickListener(this);
		
		serviceList =  (ListView) findViewById(R.id.service_list);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
//			HomeServiceController.getInstance().registerService(null);
			setResult(SERVICE_ADD_OK);
            finish();
			break;
			
		case R.id.btn_cancel:
			setResult(SERVICE_ADD_CANCEL);
            finish();
			break;
		}
		
	}
}
