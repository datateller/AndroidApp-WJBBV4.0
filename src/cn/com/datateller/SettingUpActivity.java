package cn.com.datateller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.com.datateller.utils.SharedPreferencesUtils;
import cn.com.datateller.utils.UserHelper;

public class SettingUpActivity extends Activity {

	private Button updateBabyBirthdayButton;
	private Button exitAppButton;
	private Button updateUserInforButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_up);
		updateBabyBirthdayButton=(Button)findViewById(R.id.updateBabyBirthdayButton);
		exitAppButton=(Button)findViewById(R.id.exitApp_Button);
		updateUserInforButton=(Button)findViewById(R.id.updateUserInforButton);
		updateBabyBirthdayButton.setOnClickListener(new ButtonClick());
		exitAppButton.setOnClickListener(new ButtonClick());
		updateUserInforButton.setOnClickListener(new ButtonClick());
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
				SharedPreferencesUtils.deleteBabyBirthdayInfor(SettingUpActivity.this);
				UserHelper.saveUserInfo(SettingUpActivity.this, "anonymous", "wjbb123",3);
				SharedPreferencesUtils.saveBabyBirthdayInfor(SettingUpActivity.this, "");
				intent.setClass(SettingUpActivity.this, MainActivity.class);
				break;
			case R.id.updateUserInforButton:
				Integer uid=UserHelper.readUserId(SettingUpActivity.this);
				if(uid==3){
					AlertDialog.Builder builder=new AlertDialog.Builder(SettingUpActivity.this);
        			builder.setTitle("提示");
        			builder.setMessage("对不起，您尚未登陆养娃宝，无法修改个人信息，请您登陆应用，谢谢！");	
        			builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener(){

        				public void onClick(DialogInterface dialog, int which) {
        					// TODO Auto-generated method stub
//        					dialog.dismiss();
        					Intent i=new Intent();
        					i.setClass(SettingUpActivity.this, LoginActivity.class);
        					startActivity(i);
        				}
        			}).show();
        			return;
				}
				intent.setClass(SettingUpActivity.this, UpdateBabyInforActivity.class);
				break;
			default:
				break;
			}
			startActivity(intent);
		}
	}
}
