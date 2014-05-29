package cn.com.datateller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.com.datateller.utils.DialogHelper;
import cn.com.datateller.utils.UserHelper;

public class RegisterActivity extends Activity {

    private static final String TAG="RegisterActivity";
	
	private EditText etuserName;
	private EditText etpassword;
	private EditText etconfirmpassword;
	private Button nextButton;
	private String username;
	private String password;
	private String confirmpassword;
	private Handler handler;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		etuserName=(EditText)findViewById(R.id.etuserName);
		etpassword=(EditText)findViewById(R.id.etpassword);
		etconfirmpassword=(EditText)findViewById(R.id.etconfirmpassword);
		nextButton=(Button)findViewById(R.id.nextButton);
		nextButton.setOnClickListener(new ButtonClick());
		
	}

	private class ButtonClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			username=etuserName.getText().toString();
			password=etpassword.getText().toString();
			confirmpassword=etconfirmpassword.getText().toString();
			
			if(username.trim().length()==0){
				DialogHelper.showDialog(RegisterActivity.this, "用户名不能为空");
				return;
			}
			
			if(password.trim().length()==0){
				DialogHelper.showDialog(RegisterActivity.this, "密码不能为空");
				return;
			}
			
			if(confirmpassword.trim().length()==0){
				DialogHelper.showDialog(RegisterActivity.this, "确认密码不能为空");
				return;
			}
			
			if(!password.equals(confirmpassword)){
				DialogHelper.showDialog(RegisterActivity.this, "密码与确认密码不一致");
				return;
			}
		
			final Intent intent=new Intent();
			switch (v.getId()) {
			case R.id.nextButton:
				intent.setClass(RegisterActivity.this, RegisterWithBabyInforActivity.class);
				intent.putExtra("username", username);
				intent.putExtra("password", password);
				RegisterActivity.this.startActivity(intent);
				break;

			default:
				break;
			}		
		}	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
