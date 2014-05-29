package cn.com.datateller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.com.datateller.utils.UserHelper;

public class SettingUpActivity extends Activity {

	private Button updateBabyBirthdayButton;
	private Button exitAppButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_up);
		updateBabyBirthdayButton=(Button)findViewById(R.id.updateBabyBirthdayButton);
		exitAppButton=(Button)findViewById(R.id.exitApp_Button);
		updateBabyBirthdayButton.setOnClickListener(new ButtonClick());
		exitAppButton.setOnClickListener(new ButtonClick());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting_up, menu);
		return true;
	}
	
	private class ButtonClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch (v.getId()) {
			case R.id.updateBabyBirthdayButton:
				intent.setClass(SettingUpActivity.this, UpdateBabyBirthdayActivity.class);
				break;
			case R.id.exitApp_Button:
				UserHelper.deleteUserInfo(SettingUpActivity.this);
				UserHelper.saveUserInfo(SettingUpActivity.this, "anonymous", "wjbb123",3);
				intent.setClass(SettingUpActivity.this, MainActivity.class);
			default:
				break;
			}
			startActivity(intent);
		}
	}
}
