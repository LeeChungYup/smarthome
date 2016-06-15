package com.keti.homeservice.activity;

import com.keti.homeservice.R;
import com.keti.homeservice.control.HomeServiceController;
import com.keti.homeservice.item.BasicUser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserAddDialog extends Activity implements OnClickListener {
	private static final String TAG = "UserAddDialog";
	
	public static final int USER_ADD_OK = 10;
	public static final int USER_ADD_CANCEL = 11;
	
	private Button btConfirm;
	private Button btCancel;
	
	private EditText txUsername;
	private EditText txPw;
	private EditText txPwConfirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_add_dialog);
        
		btConfirm = (Button) findViewById(R.id.btn_confirm);
		btCancel = (Button) findViewById(R.id.btn_cancel);
		btConfirm.setOnClickListener(this);
		btCancel.setOnClickListener(this);
		
		txUsername = (EditText) findViewById(R.id.id);
		txPw = (EditText) findViewById(R.id.password);
		txPwConfirm = (EditText) findViewById(R.id.password_confirm);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			BasicUser user = new BasicUser(
					txUsername.getText().toString(), 
					txPw.getText().toString(),
					txPwConfirm.getText().toString());
	
			if(!user.idNullCheack()) {
				Log.d(TAG, "USERNAME is null");
				Toast.makeText(UserAddDialog.this, "유저 이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
				break;
			}
			
			if(!user.pwNullCheack()) {
				Log.d(TAG, "PASSWORD is null");
				Toast.makeText(UserAddDialog.this, "패스워드를 입력해 주세요.", Toast.LENGTH_SHORT).show();
				break;
			}
			
			if(user.isCorrect()) {
				HomeServiceController.getInstance().requestAddUserInfo(user);
				setResult(USER_ADD_OK);
	            finish();
			} else {
				Log.d(TAG, "PASSWORD is not correct");
				Toast.makeText(UserAddDialog.this, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
			}
			break;
			
		case R.id.btn_cancel:
			setResult(USER_ADD_CANCEL);
            finish();
			break;
		}
		
	}

	
	
}
