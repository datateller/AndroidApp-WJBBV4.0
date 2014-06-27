package cn.com.datateller;

import java.io.FileNotFoundException;

import cn.com.datateller.model.User;
import cn.com.datateller.service.ImageService;
import cn.com.datateller.utils.DialogHelper;
import cn.com.datateller.utils.HttpConnection;
import cn.com.datateller.utils.UserHelper;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class TopicSendActivity extends Activity {

	public static final String TAG="TopicSendActivity";
	private ImageView confirmImageview;
	private EditText topicContentEditText;
	private ImageView topicPicImageview;
	private User user=new User();
	private String filename="";
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_send);
		confirmImageview=(ImageView)findViewById(R.id.confirmImageview);
		topicContentEditText=(EditText)findViewById(R.id.topicContentEditText);
		topicPicImageview=(ImageView)findViewById(R.id.topicPicImageview);
		
		confirmImageview.setOnClickListener(new ButtonClick());
		topicContentEditText.setOnClickListener(new ButtonClick());
		topicPicImageview.setOnClickListener(new ButtonClick());
		user.setUserName(UserHelper.readUserName(TopicSendActivity.this));
		user.setPassword(UserHelper.readPassword(TopicSendActivity.this));
		Log.d(TAG, user.getUserName());
		Log.d(TAG, user.getPassword());

	}

	private class ButtonClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.confirmImageview:
				if(String.valueOf(topicContentEditText.getText()).equals("请输入内容")&&
						(topicPicImageview.getDrawable()==getResources().getDrawable(R.drawable.uploadpic))){
					DialogHelper.showDialog(TopicSendActivity.this, "您尚未输入内容和上传图片，无法发表新帖");
					return;
				}
				String content="";
				content=String.valueOf(topicContentEditText.getText());
				Log.d(TAG, "########################"+"70");
				postTopic(content);
				
				break;
			case R.id.topicContentEditText:
				Log.d(TAG, "#############"+String.valueOf(topicContentEditText.getText()));
				if(String.valueOf(topicContentEditText.getText()).equals("请输入内容")){
					topicContentEditText.setText("");
				}
				break;
			case R.id.topicPicImageview:
				Intent pictureIntent=new Intent();
				pictureIntent.setType("image/*");
				pictureIntent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(pictureIntent, 1);
				break;
			default:
				break;
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.topic_send, menu);
		return true;
	}

	private void postTopic(final String content) {
		// TODO Auto-generated method stub
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg){
				Bundle bundle = msg.getData();
				String result=bundle.getString("result");
				Log.d(TAG, "###########################"+result);
				if(result.equals("OK")){
					Toast.makeText(TopicSendActivity.this, "发帖成功", Toast.LENGTH_LONG).show();
					Intent intent=new Intent();
					intent.setClass(TopicSendActivity.this, AgeCircleActivity.class);
					startActivity(intent);
				}else{
					Toast.makeText(TopicSendActivity.this, "发帖失败", Toast.LENGTH_LONG).show();
				}
			}
		};
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result="";
				try {
					Log.d(TAG, "########################filename"+filename);
					result = HttpConnection.postTopic(user,filename,content);
					Log.d(TAG, "######################"+result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg=new Message();
				Bundle bundle = new Bundle();
				bundle.putString("result", result);
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
		}).start();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		   if (resultCode == RESULT_OK) {  
	            Uri uri = data.getData();  
	            Log.e("uri", uri.toString());  
	            ContentResolver cr = this.getContentResolver();  
	            try {  
	                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
	                Log.d(TAG, String.valueOf(uri));
	                ImageView imageView = (ImageView) findViewById(R.id.topicPicImageview);  
	                /* 将Bitmap设定到ImageView */  
	                imageView.setImageBitmap(bitmap);
	                filename=getFilenameFromUricontent(uri);
					Log.d(TAG, "#######################"+filename);
//					upLoadHeadPicToServer(user,filename);
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
		Cursor actualimagecursor = TopicSendActivity.this.managedQuery(uri,proj,null,null,null); 
		int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); 
		actualimagecursor.moveToFirst();   
		String img_path = actualimagecursor.getString(actual_image_column_index); 
		return img_path;
	}
}
