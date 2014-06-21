package cn.com.datateller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.datateller.model.User;
import cn.com.datateller.service.ImageService;
import cn.com.datateller.utils.HttpConnection;
import cn.com.datateller.utils.SharedPreferencesUtils;
import cn.com.datateller.utils.UserHelper;

public class SettingUpActivity extends Activity {

	private static final String TAG="SettingUpActivity";
	private Button updateBabyBirthdayButton;
	private Button exitAppButton;
	private Button updateUserInforButton;
	private ImageView personImageView;
	private TextView usernameTextView;
	private Handler handler;
	private User user=new User();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_up);
		updateBabyBirthdayButton=(Button)findViewById(R.id.updateBabyBirthdayButton);
		exitAppButton=(Button)findViewById(R.id.exitApp_Button);
		updateUserInforButton=(Button)findViewById(R.id.updateUserInforButton);
		personImageView=(ImageView)findViewById(R.id.personImageView);
		usernameTextView=(TextView)findViewById(R.id.usernameTextView);
		
		String username=UserHelper.readUserName(SettingUpActivity.this);
		String password=UserHelper.readPassword(SettingUpActivity.this);
		usernameTextView.setText(username);
		
		user.setUserName(username);
		user.setPassword(password);
		
		getUserHeadFromServer(user);
		
		updateBabyBirthdayButton.setOnClickListener(new ButtonClick());
		exitAppButton.setOnClickListener(new ButtonClick());
		updateUserInforButton.setOnClickListener(new ButtonClick());
		
		personImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent pictureIntent=new Intent();
				pictureIntent.setType("image/*");
				pictureIntent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(pictureIntent, 1);
			}
		});
	}
	private void getUserHeadFromServer(final User user) {
		// TODO Auto-generated method stub
		String localpath=UserHelper.readUserHeadPicUrl(SettingUpActivity.this);
		if((!localpath.equals(""))&&(!localpath.contains("http://"))){
			Bitmap bitmap=BitmapFactory.decodeFile(localpath);
			personImageView.setImageBitmap(bitmap); 
			return;
		}
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg){
				Bundle bundle = msg.getData();
				String filepath=bundle.getString("headfilepath");
				UserHelper.writeUserHeadPicUrl(SettingUpActivity.this, filepath);
				Bitmap bitmap=BitmapFactory.decodeFile(filepath);
				personImageView.setImageBitmap(bitmap); 
			}
		};
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ImageService service=new ImageService();
				String filepath=service.getHeadFromServer(user);
				Message msg=new Message();
				Bundle bundle = new Bundle();
				bundle.putString("headfilepath", filepath);
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
		}).start();
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		   if (resultCode == RESULT_OK) {  
	            Uri uri = data.getData();  
	            Log.e("uri", uri.toString());  
	            ContentResolver cr = this.getContentResolver();  
	            try {  
	                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
	                Log.d(TAG, String.valueOf(uri));
	                ImageView imageView = (ImageView) findViewById(R.id.personImageView);  
	                /* 将Bitmap设定到ImageView */  
	                imageView.setImageBitmap(bitmap);
	                String filename=getFilenameFromUricontent(uri);
					Log.d(TAG, "#######################"+filename);
					upLoadHeadPicToServer(user,filename);
					Log.d(TAG, "#############UploadComplete");
	            } catch (FileNotFoundException e) {  
	                Log.e("Exception", e.getMessage(),e);  
	            }  
	        }  
	        super.onActivityResult(requestCode, resultCode, data);  
	    }
	
	private String getFilenameFromUricontent(Uri uri) {
		// TODO Auto-generated method stub
		String[] proj = { MediaStore.Images.Media.DATA };  
		Cursor actualimagecursor = SettingUpActivity.this.managedQuery(uri,proj,null,null,null); 
		int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); 
		actualimagecursor.moveToFirst();   
		String img_path = actualimagecursor.getString(actual_image_column_index); 
		return img_path;
	}
	private void upLoadHeadPicToServer(final User user2, final String filename) {
		// TODO Auto-generated method stub
		Log.d(TAG, "########################begin");
		final Handler uploadHandler=new Handler(){
			@Override
			public void handleMessage(Message msg){
				Bundle bundle = msg.getData();
				String filepath=bundle.getString("urlString");
				UserHelper.writeUserHeadPicUrl(SettingUpActivity.this, filepath);
				Log.d(TAG, "#############"+String.valueOf(UserHelper.readUserHeadPicUrl(SettingUpActivity.this)));
			}
		};
		new Thread(new  Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String urlString=HttpConnection.upLoadPicFileToServer(user2,filename);
					Message msg=new Message();
					Bundle bundle = new Bundle();
					bundle.putString("urlString", urlString);
					msg.setData(bundle);
					uploadHandler.sendMessage(msg);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}  
}
