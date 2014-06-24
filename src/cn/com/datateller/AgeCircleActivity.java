package cn.com.datateller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import cn.com.datateller.model.Topic;
import cn.com.datateller.model.User;
import cn.com.datateller.service.CircleService;
import cn.com.datateller.utils.CircleListViewAdapter;
import cn.com.datateller.utils.CircleListViewHelper;
import cn.com.datateller.utils.DateUtils;
import cn.com.datateller.utils.DialogHelper;
import cn.com.datateller.utils.UserHelper;

public class AgeCircleActivity extends Activity {

	private static final String TAG="KnowledgeActivity";
	private static final String APPNAME="yangwabao";
	private static final String NAME="AgeTopic";
	private ListView listview;
	private Handler handler;
	private Integer userid;
	private ImageButton freshButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_age_circle);
		userid=UserHelper.readUserId(AgeCircleActivity.this);
        freshButton=(ImageButton)findViewById(R.id.freshButton);
        freshButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				CircleService service=new CircleService();
				String currentDay = DateUtils.getStandardCurrentDay();
				String filename = NAME+".xml";
				String path = Environment.getExternalStorageDirectory() +"/"+APPNAME+"/"+currentDay;
				service.deleteCacheFile(path,filename);
				showAgeCircleInformation();
			}
		});
		showAgeCircleInformation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.age_circle, menu);
		return true;
	}

	private void showAgeCircleInformation(){
		boolean sdcardIsmount = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if(sdcardIsmount==false){
			getCircleInforFromServerAndWriteToFile(0,null,null);
			return;
		}
		String currentDay = DateUtils.getStandardCurrentDay();
		File fileDir=new File(Environment.getExternalStorageDirectory()+"/"+APPNAME+"/"+currentDay);
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		String filename = NAME+".xml";
		String path = Environment.getExternalStorageDirectory() +"/"+APPNAME+"/"+currentDay;
		boolean isExists = new File(path, filename).exists();
		if (isExists) {
//          ֱ�Ӵ��ļ��ж�ȡbasicknowledge����չʾ����
			CircleService service=new CircleService();
			List<Topic> basicCircleList=service.readCircleInformationFromFile(path, filename);
			show(basicCircleList);
			return;
		} else {
			getCircleInforFromServerAndWriteToFile(0,path,filename);
		}
	}

	private void getCircleInforFromServerAndWriteToFile(int tmp, final String path,
			final String filename) {
		// TODO Auto-generated method stub
		final ProgressDialog myDialog = ProgressDialog.show(
				AgeCircleActivity.this, "���Ե�", "���ڻ�ȡ����...", true, true);

		handler = new Handler() {
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				ArrayList basicTopic = bundle
						.getParcelableArrayList("basicTopic");
				myDialog.dismiss();
				CircleService service=new CircleService();
				if(basicTopic==null) {
					//�����ȡ��basicTopic����ȷ������ݲ�ͬ��ԭ����д��������û������ע��͵�½ģ��
					DialogHelper.showDialog(AgeCircleActivity.this, "��ȡ����Ȧ��Ϣʧ��");
					return;
				}
				if(path!=null||filename!=null) service.writeBasicKnowledgeToFile(path, filename,basicTopic);
				show(basicTopic);
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				CircleService service=new CircleService();
				User user = new User();
				user.setUserName(UserHelper
						.readUserName(AgeCircleActivity.this));
				user.setPassword(UserHelper
						.readPassword(AgeCircleActivity.this));
				String result=service.getCircleInforFromServerByNative(user);
				List<Topic> topicList=service.analysisTopic(result);
				if(topicList!=null){
					topicList=service.getUserHeadByUrl(topicList);
				}
				Log.d(TAG, String.valueOf(topicList));
				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putParcelableArrayList("basicTopic",
						(ArrayList<? extends Parcelable>)topicList );
				msg.setData(bundle);
				handler.sendMessage(msg);
			}}).start();
	}
	
	private void show(final List<Topic> basicCircleList) {
		// TODO Auto-generated method stub
		CircleListViewHelper helper=new CircleListViewHelper();
		List<Map<String, Object>> data=helper.getData(basicCircleList);
		listview=(ListView)findViewById(R.id.topic_content);
		CircleListViewAdapter adapter=new CircleListViewAdapter(AgeCircleActivity.this, data);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				ListView lview = (ListView) parent;
				int index = (Integer) lview.getItemAtPosition(position);
				int topicId=Integer.valueOf(basicCircleList.get(index).getTopicid());
				Intent intent = new Intent();
				intent.putExtra("topicId", topicId);
				intent.putExtra("userid", userid);
				intent.setClass(AgeCircleActivity.this, AgeCircleDetailActivity.class);
				AgeCircleActivity.this.startActivity(intent);
			}
		});
	}
}
