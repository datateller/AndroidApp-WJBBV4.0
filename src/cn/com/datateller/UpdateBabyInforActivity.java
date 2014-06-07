package cn.com.datateller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.com.datateller.model.Baby;
import cn.com.datateller.model.User;
import cn.com.datateller.service.UserService;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_baby_infor);
		etchildname=(EditText)findViewById(R.id.etchildrname);
		rgchildsex=(RadioGroup)findViewById(R.id.childsexRadioGroup);
		etchildheight=(EditText)findViewById(R.id.etchildheight);
		etchildweight=(EditText)findViewById(R.id.etchildweight);
		finshButton=(Button)findViewById(R.id.finishButton);
	    etfamilyAddress=(EditText)findViewById(R.id.etfamilyaddress);
	    etschoolAddress=(EditText)findViewById(R.id.etschooladdress);
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
					DialogHelper.showDialog(UpdateBabyInforActivity.this, "Baby 的姓名不能为空");
				}
				int childheight=0;
				if(etchildheight.getText().toString()!="") 
					childheight=Integer.valueOf(etchildheight.getText().toString());
				Log.d(TAG, childheight+"");
				float childweight=0.0f;
				if(etchildweight.getText().toString()!="")
					childweight=Float.valueOf(etchildweight.getText().toString());
				Log.d(TAG, etchildweight+"");
//				TODO 无效的出生日期需要重新判断

				String familyAddress=null;
				if(etfamilyAddress.getText().toString()!=""){
					familyAddress=etfamilyAddress.getText().toString();
				}
				String schoolAddress=null;
				if(etschoolAddress.getText().toString()!=""){
					schoolAddress=etschoolAddress.getText().toString();
				}

				final User user=new User();
				final Baby child=new Baby();
				user.setUserName(UserHelper.readUserName(UpdateBabyInforActivity.this));
				user.setPassword(UserHelper.readPassword(UpdateBabyInforActivity.this));
				Date birthday=new Date();
				try {
					birthday = new SimpleDateFormat("yyyy-MM-dd").parse(SharedPreferencesUtils.readBabyBirthdayInfor(UpdateBabyInforActivity.this));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				child.setWeight(childweight);
				child.setHeight(childheight);
				child.setSex(sex);
				child.setFamilyAddress(familyAddress);
				child.setSchoolAddress(schoolAddress);
				child.setBirthday(birthday);
				System.out.println(sex.getIndex());
				final ProgressDialog myDialog=ProgressDialog.show(UpdateBabyInforActivity.this, "正在更新", "连接中，请稍后...",true,true);
				handler=new Handler(){
					@Override
					public void handleMessage(Message msg){
						Bundle bundle=msg.getData();
						String result=bundle.getString("result");
						System.out.println("In the handler");
						myDialog.dismiss();
						if(result.equals("True")){
//							DialogHelper.showDialog(UpdateBabyInforActivity.this, "Baby信息更新成功");
							Toast.makeText(UpdateBabyInforActivity.this, "宝贝信息更新成功", Toast.LENGTH_SHORT).show();
							Intent intent=new Intent();
							intent.setClass(UpdateBabyInforActivity.this, MainActivity.class);
							startActivity(intent);
						}else if(result.equals("False")){
							DialogHelper.showDialog(UpdateBabyInforActivity.this, "Baby信息更新失败");
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
						//使用handler发送message
						handler.sendMessage(msg);
					}
				}).start();	
			}
		});
        
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_user_infor, menu);
		return true;
	}

}
