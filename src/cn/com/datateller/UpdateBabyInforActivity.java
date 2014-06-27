package cn.com.datateller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import cn.com.datateller.model.Baby;
import cn.com.datateller.model.User;
import cn.com.datateller.service.UserService;
import cn.com.datateller.utils.DateUtils;
import cn.com.datateller.utils.DialogHelper;
import cn.com.datateller.utils.SexEnum;
import cn.com.datateller.utils.SharedPreferencesUtils;
import cn.com.datateller.utils.UserHelper;

public class UpdateBabyInforActivity extends Activity {

	private static final String TAG="RegisterWithChildInfoActivity"; 
	private EditText etchildname;
	private RadioGroup rgchildsex;
	private EditText etchildheight;
	private EditText etchildweight;
	private EditText etfamilyAddress;  
	private EditText etschoolAddress;
	private Button finshButton;
	private SexEnum sex=SexEnum.BOY;
    private Handler handler;
    private EditText birthdayEditText;
	private int mYear;
	private int mMonth;
	private int mDay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_baby_infor);
		User user = new User();
		user.setUserName(UserHelper.readUserName(UpdateBabyInforActivity.this));
		user.setPassword(UserHelper.readPassword(UpdateBabyInforActivity.this));
		
		mYear = DateUtils.getCurrentYear();
		mMonth = DateUtils.getCurrentMonth();
		mDay = DateUtils.getCurrentDayOfMonth();
		
		etchildname=(EditText)findViewById(R.id.etchildrname);
		rgchildsex=(RadioGroup)findViewById(R.id.childsexRadioGroup);
		etchildheight=(EditText)findViewById(R.id.etchildheight);
		etchildweight=(EditText)findViewById(R.id.etchildweight);
		finshButton=(Button)findViewById(R.id.finishButton);
	    etfamilyAddress=(EditText)findViewById(R.id.etfamilyaddress);
	    etschoolAddress=(EditText)findViewById(R.id.etschooladdress);
	    birthdayEditText=(EditText)findViewById(R.id.birthdayEditText);
//	         �ر�Ĭ�ϵ����뷨
	    birthdayEditText.setInputType(InputType.TYPE_NULL);
	    birthdayEditText.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(0);
			}
		});

		birthdayEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
					hideIM(v);
					showDialog(0);
				}
			}
		});
	    
      
	    if(user.getUserName().equals("anonymous")){
	    	AlertDialog.Builder builder=new AlertDialog.Builder(UpdateBabyInforActivity.this);
			builder.setTitle("��ʾ");
			builder.setMessage("�Բ�������δ��½���ޱ����޷��޸����ı�����Ϣ��лл��");
			builder.setPositiveButton("ȷ��", new android.content.DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
                    Intent intent=new Intent();
                    intent.setClass(UpdateBabyInforActivity.this, LoginActivity.class);
                    startActivity(intent);
				}
			});
			builder.show();
	    	return;
	    }
	    getBabyInforFromServer(user);
	    
	    rgchildsex.setOnCheckedChangeListener(new OnCheckedChangeListener() {	
			@Override
			public void onCheckedChanged(RadioGroup group, int checkId) {
				// TODO Auto-generated method stub
				if(checkId==R.id.boyButton){
					sex.setIndex(1);
				}else{
					sex.setIndex(2);
				}
			}
		});
	    
	    
        finshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String childname=etchildname.getText().toString();
				Log.d(TAG, childname);
				if(childname==""){
					DialogHelper.showDialog(UpdateBabyInforActivity.this, "Baby ����������Ϊ��");
				}
				float childheight=0;
				if(etchildheight.getText().toString()!="") 
					childheight=Float.valueOf(etchildheight.getText().toString());
				Log.d(TAG, childheight+"");
				float childweight=0.0f;
				if(etchildweight.getText().toString()!="")
					childweight=Float.valueOf(etchildweight.getText().toString());
				Log.d(TAG, etchildweight+"");
//				TODO ��Ч�ĳ���������Ҫ�����ж�

				String familyAddress=null;
				if(etfamilyAddress.getText().toString()!=""){
					familyAddress=etfamilyAddress.getText().toString();
				}
				String schoolAddress=null;
				if(etschoolAddress.getText().toString()!=""){
					schoolAddress=etschoolAddress.getText().toString();
				}

				if (birthdayEditText.getText() == null) {
					DialogHelper.showDialog(UpdateBabyInforActivity.this,
							"������д�����ĳ�������");
					return;
				}
				String dateString = birthdayEditText.getText().toString();
				String year = dateString.substring(0, 4);
				String month = dateString.substring(5, 7);
				String day = dateString.substring(8, 10);
				String formatDateString = year + "-" + month + "-" + day;
				SharedPreferencesUtils.saveBabyBirthdayInfor(
						UpdateBabyInforActivity.this, formatDateString);
				
				final User user=new User();
				final Baby child=new Baby();
				user.setUserName(UserHelper.readUserName(UpdateBabyInforActivity.this));
				user.setPassword(UserHelper.readPassword(UpdateBabyInforActivity.this));
				String birthday;
			    birthday = SharedPreferencesUtils.readBabyBirthdayInfor(UpdateBabyInforActivity.this);
				child.setWeight(childweight);
				child.setHeight(childheight);
				child.setSex(sex);
				child.setFamilyAddress(familyAddress);
				child.setSchoolAddress(schoolAddress);
				child.setBirthday(birthday);
				System.out.println(sex.getIndex());
				final ProgressDialog myDialog=ProgressDialog.show(UpdateBabyInforActivity.this, "���ڸ���", "�����У����Ժ�...",true,true);
				handler=new Handler(){
					@Override
					public void handleMessage(Message msg){
						Bundle bundle=msg.getData();
						String result=bundle.getString("result");
						System.out.println("In the handler");
						myDialog.dismiss();
						if(result.equals("True")){
//							DialogHelper.showDialog(UpdateBabyInforActivity.this, "Baby��Ϣ���³ɹ�");
							Toast.makeText(UpdateBabyInforActivity.this, "������Ϣ���³ɹ�", Toast.LENGTH_SHORT).show();
							Intent intent=new Intent();
							intent.setClass(UpdateBabyInforActivity.this, MainActivity.class);
							startActivity(intent);
						}else if(result.equals("False")){
							DialogHelper.showDialog(UpdateBabyInforActivity.this, "Baby��Ϣ����ʧ��");
						}
					}
				};
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						String result=new UserService().UpdateInfo(user, child);
						Message msg=new Message();
						Bundle bundle=new Bundle();
						bundle.putString("result", result);
						msg.setData(bundle);
						//ʹ��handler����message
						handler.sendMessage(msg);
					}
				}).start();	
			}
		});
        
	}
	private void getBabyInforFromServer(final User user) {
		// TODO Auto-generated method stub
		final ProgressDialog myDialog = ProgressDialog.show(
				UpdateBabyInforActivity.this, "���ڻ�ȡ������Ϣ", "���Ժ�...", true, true);
		final Handler babyInforHandler=new Handler(){
			@Override
			public void handleMessage(Message msg){
				Bundle bundle=msg.getData();
				Baby baby=(Baby)bundle.getSerializable("babyinfor");
				myDialog.dismiss();
				InitComponent(baby);
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				UserService service=new UserService();
				Baby baby=service.getBabyInforFromServerByUsername(user);
				Message msg=new Message();
				Bundle bundle=new Bundle();
				bundle.putSerializable("babyinfor", baby);
				msg.setData(bundle);
				babyInforHandler.sendMessage(msg);
			}
		}).start();
	}
	//���ӷ������л�ȡ������ҳ������ʾ����
	protected void InitComponent(Baby baby) {
		// TODO Auto-generated method stub
	    etchildname.setText(baby.getChildname());
	    etchildweight.setText(String.valueOf(baby.getWeight()));
	    etchildheight.setText(String.valueOf(baby.getHeight()));
	    etfamilyAddress.setText(String.valueOf(baby.getFamilyAddress()));
	    etschoolAddress.setText(String.valueOf(baby.getSchoolAddress()));
	    if(baby.getSex().getIndex()==1)
     	  rgchildsex.check(R.id.boyButton);
	    else rgchildsex.check(R.id.girlButton);
	    birthdayEditText.setText(baby.getBirthday());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_user_infor, menu);
		return true;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			String mm;
			String dd;
			if (monthOfYear <= 9) {
				mMonth = monthOfYear + 1;
				mm = "0" + mMonth;
			} else {
				mMonth = monthOfYear + 1;
				mm = String.valueOf(mMonth);
			}
			if (dayOfMonth <= 9) {
				mDay = dayOfMonth;
				dd = "0" + mDay;
			} else {
				mDay = dayOfMonth;
				dd = String.valueOf(mDay);
			}
			mDay = dayOfMonth;
			birthdayEditText.setText(String.valueOf(mYear) + "��" + mm + "��"
					+ dd + "��");
		}
	};

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:
			// ��Ĭ�ϵ����ڴ������ڿؼ���
			Log.d(TAG, String.valueOf(id));
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		case 1:
			Log.d(TAG, String.valueOf(id));
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	// �����ֻ�����
	private void hideIM(View edt) {
		try {
			InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
			IBinder windowToken = edt.getWindowToken();
			if (windowToken != null) {
				im.hideSoftInputFromWindow(windowToken, 0);
			}
		} catch (Exception e) {

		}
	}
	
}
