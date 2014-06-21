package cn.com.datateller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
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
		User user = new User();
		user.setUserName(UserHelper.readUserName(UpdateBabyInforActivity.this));
		user.setPassword(UserHelper.readPassword(UpdateBabyInforActivity.this));
		
		etchildname=(EditText)findViewById(R.id.etchildrname);
		rgchildsex=(RadioGroup)findViewById(R.id.childsexRadioGroup);
		etchildheight=(EditText)findViewById(R.id.etchildheight);
		etchildweight=(EditText)findViewById(R.id.etchildweight);
		finshButton=(Button)findViewById(R.id.finishButton);
	    etfamilyAddress=(EditText)findViewById(R.id.etfamilyaddress);
	    etschoolAddress=(EditText)findViewById(R.id.etschooladdress);
      
	    if(user.getUserName().equals("anonymous")){
	    	AlertDialog.Builder builder=new AlertDialog.Builder(UpdateBabyInforActivity.this);
			builder.setTitle("提示");
			builder.setMessage("对不起，您尚未登陆养娃宝，无法修改您的宝贝信息，谢谢！");
			builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener(){

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
					DialogHelper.showDialog(UpdateBabyInforActivity.this, "Baby 的姓名不能为空");
				}
				float childheight=0;
				if(etchildheight.getText().toString()!="") 
					childheight=Float.valueOf(etchildheight.getText().toString());
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
				String birthday;
			    birthday = SharedPreferencesUtils.readBabyBirthdayInfor(UpdateBabyInforActivity.this);
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
	private void getBabyInforFromServer(final User user) {
		// TODO Auto-generated method stub
		final ProgressDialog myDialog = ProgressDialog.show(
				UpdateBabyInforActivity.this, "正在获取宝贝信息", "请稍后...", true, true);
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
	//将从服务器中获取数据在页面上显示出来
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
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_user_infor, menu);
		return true;
	}

}
