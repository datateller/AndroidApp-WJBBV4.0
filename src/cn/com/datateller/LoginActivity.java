package cn.com.datateller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import cn.com.datateller.service.UserService;
import cn.com.datateller.utils.DialogHelper;
import cn.com.datateller.utils.UserHelper;

public class LoginActivity extends Activity {

	private static final String TAG = "LoginActivity";
	private EditText etuserName;
	private EditText etpassword;
	private Button registerButton;
	private Button loginButton;
	private String username;
	private String password;
	private int userId;
	private Handler handler;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		username = UserHelper.readUserName(this);
		password = UserHelper.readPassword(this);
		userId = UserHelper.readUserId(this);

		etuserName = (EditText) findViewById(R.id.login_activity_username);
		etpassword = (EditText) findViewById(R.id.login_activity_password);
		registerButton = (Button) findViewById(R.id.registerButton);
		loginButton = (Button) findViewById(R.id.login_activity_loginButton);
		
		registerButton.setOnClickListener(new ButtonClick());
		loginButton.setOnClickListener(new ButtonClick());

		etuserName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    etuserName.setText("");	
			}
		});
		/*etpassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    Log.d(TAG, "###############################");
				etpassword.setText("");	
			}
		});*/
		etpassword.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener(){

			@Override
			public void onFocusChange(View view, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus){
					etpassword.setText("");
				}
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	private class ButtonClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.login_activity_loginButton: {

				// ��ȡ�û���������
				username = etuserName.getText().toString();
				password = etpassword.getText().toString();
				Log.d(TAG, "The user name is " + username);
				Log.d(TAG, "The password is " + password);
				// �û���Ϊ����ֱ�ӷ���
				if (username.trim().length() == 0) {
					Log.d(TAG, "�򿪶Ի���");
					DialogHelper.showDialog(LoginActivity.this, "�û�������Ϊ��");
					return;
				}
				// ����Ϊ����ֱ�ӷ���
				if (password.trim().length() == 0) {
					DialogHelper.showDialog(LoginActivity.this, "���벻��Ϊ��");
					return;
				}
				// ��ҳ������ʾԲ�ν�����
				final ProgressDialog myDialog = ProgressDialog.show(
						LoginActivity.this, "���ڵ�½", "�����У����Ժ�...", true, true);

				// �첽�ύ�û���������
				handler = new Handler() {
					@Override
					// TODO �������ӹ���Ҳ�ǵ�½ʧ�ܵ�һ��
					public void handleMessage(Message msg) {
						Bundle bundle = msg.getData();
						// ��ȡ���������صĽ��
						boolean result = bundle.getBoolean("result");
						String uid=bundle.getString("userId");
						Log.d(TAG, String.valueOf(result));
						myDialog.dismiss();
						// ��½�ɹ��������MainActivity
						if (result == true) {
							Intent intent = new Intent();
							// ���û��������뻺����userInformation�ļ��У��ٴν����ʱ�����ظ������û���������
							UserHelper.deleteUserInfo(LoginActivity.this);
							System.out.println(username);
							System.out.println(password);
							UserHelper.saveUserInfo(LoginActivity.this,
									username, password,Integer.valueOf(uid));
							intent.setClass(LoginActivity.this,
									MainActivity.class);
							startActivity(intent);
						} else {
							// ��½ʧ�ܣ�����ʾ��½ʧ�ܵĶԻ���
							DialogHelper.showDialog(LoginActivity.this, "��½ʧ��");
						}
					}
				};
				// �¿���һ���̣߳���������ύ��½��Ϣ
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						UserService userService = new UserService();
						boolean result = userService.userLogin(username,
								password);
						String userId=userService.getUserIdByNameAndPassword(username,password);
						Message msg = new Message();
						Bundle bundle = new Bundle();
						bundle.putBoolean("result", result);
						bundle.putString("userId", userId);
						msg.setData(bundle);
						// ʹ��handler����message
						handler.sendMessage(msg);
					}
				}).start();
			}
				break;
			case R.id.registerButton: {
				intent.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
				break;
			default:
				break;
			}
		}
	}
	
}
