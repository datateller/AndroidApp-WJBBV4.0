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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.com.datateller.model.BasicInformation;
import cn.com.datateller.model.User;
import cn.com.datateller.service.InformationService;
import cn.com.datateller.utils.DateUtils;
import cn.com.datateller.utils.DialogHelper;
import cn.com.datateller.utils.ListViewHelper;
import cn.com.datateller.utils.MyListViewAdapter;
import cn.com.datateller.utils.SharedPreferencesUtils;
import cn.com.datateller.utils.UserHelper;

public class KnowledgeActivity extends Activity {

	private static final String TAG="KnowledgeActivity";
	private static final String APPNAME="yangwabao";
	private static final String NAME="BasicKnowledge";
	private ListView listview;
	private Handler handler;
	private String birthday;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_knowledge);
		Log.d(TAG, "In the KnowledgeActivity");
		birthday=SharedPreferencesUtils.readBabyBirthdayInfor(KnowledgeActivity.this);
		int age;
		if(birthday.equals(""))  age=0;
		else{
			//TODO ��ǰ���ڼ�ȥ���ջ������
			age=Integer.valueOf(UserHelper.getBabyAgeInfo(birthday));
		} 
			showBasicKnowledge(age);	
	}

	private void showBasicKnowledge(Integer age) {
		// TODO Auto-generated method stub
		boolean sdcardIsmount = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if(sdcardIsmount==false){
			getBasicKnowledgeFromServerAndWriteToFile(0,null,null);
			return;
		}
		String currentDay = DateUtils.getStandardCurrentDay();
		File fileDir=new File(Environment.getExternalStorageDirectory()+"/"+APPNAME+"/"+currentDay);
//		������ļ��в����ڣ��򴴽����ļ��У������Ĳ����������ļ����н���
		if(!fileDir.exists()){
			fileDir.mkdirs();
			//TODO ɾ��ϵͳ�д��ڵ�ʧЧ�ļ�
		}
		   
		String filename = NAME+birthday+".xml";
		String path = Environment.getExternalStorageDirectory() +"/"+APPNAME+"/"+currentDay;
		boolean isExists = new File(path, filename).exists();
		if (isExists) {
//          ֱ�Ӵ��ļ��ж�ȡbasicknowledge����չʾ����
			InformationService service=new InformationService();
			List<BasicInformation> basicKnowledgeList=service.readBasicInformationFromFile(path,filename);
			show(basicKnowledgeList);
			return;
		} else {
			getBasicKnowledgeFromServerAndWriteToFile(age,path,filename);
		}
	}

	private void getBasicKnowledgeFromServerAndWriteToFile(
			final int age, final String path, final String filename) {
		// TODO Auto-generated method stub
		final ProgressDialog myDialog = ProgressDialog.show(
				KnowledgeActivity.this, "���Ե�", "���ڻ�ȡ����...", true, true);
		handler = new Handler() {
			@Override
			// TODO �������ӹ���Ҳ�ǵ�½ʧ�ܵ�һ��
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				ArrayList basicKnowledge = bundle
						.getParcelableArrayList("basicKnowledge");
				myDialog.dismiss();
				InformationService service=new InformationService();
				if(basicKnowledge==null) {
					DialogHelper.showDialog(KnowledgeActivity.this, "��ȡ֪ʶʧ��");
					return;
				}
				if(path!=null||filename!=null) service.writeBasicKnowledgeToFile(path, filename,basicKnowledge);
				show(basicKnowledge);
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				InformationService service = new InformationService();
				User user = new User();
				user.setUserName(UserHelper
						.readUserName(KnowledgeActivity.this));
				user.setPassword(UserHelper
						.readPassword(KnowledgeActivity.this));
				ArrayList<BasicInformation> basicKnowledgelist = service
						.getBasicInformationFromServer(user,age,"knowledges");
				if(basicKnowledgelist!=null){
					basicKnowledgelist = service.getIconByUrl(basicKnowledgelist);
				}
				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putParcelableArrayList("basicKnowledge",
						(ArrayList<? extends Parcelable>) basicKnowledgelist);
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
		}).start();
	}
	
		private void show(final List<BasicInformation> basicKnowledgeList) {
		// TODO Auto-generated method stub
		ListViewHelper helper=new ListViewHelper();
		List<Map<String, Object>> data=helper.getData(basicKnowledgeList);
		listview=(ListView)findViewById(R.id.basic_knowledge_content);
//		��ʼ��������
		MyListViewAdapter adapter=new MyListViewAdapter(KnowledgeActivity.this,data);
//		����������listview��
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				ListView lview = (ListView) parent;
				int index = (Integer) lview.getItemAtPosition(position);
				int knowledgeId = Integer.valueOf(basicKnowledgeList.get(index)
						.getId());
				String knowledgePicUrl = basicKnowledgeList.get(index).getPic();
				String catetoryTag=null;
				String link=basicKnowledgeList.get(index).getLink();
				if(link.indexOf("knowledge")>0)  
					catetoryTag="knowledge";
				else if(link.indexOf("consumption")>0)
					catetoryTag="consumption";
				else if(link.indexOf("shop")>0)
					catetoryTag="shop";
				Intent intent = new Intent();
				intent.putExtra("knowledgeId", knowledgeId);
				intent.putExtra("title", basicKnowledgeList.get(index).getTitle());
				intent.putExtra("Abstract", basicKnowledgeList.get(index).getAbstract());
				intent.putExtra("knowledgeIconUrl", basicKnowledgeList.get(index).getIcon());
				intent.putExtra("link", basicKnowledgeList.get(index).getLink());
				intent.putExtra("address", basicKnowledgeList.get(index).getAddress());
				intent.putExtra("pic", basicKnowledgeList.get(index).getPic());
				intent.putExtra("catetoryTag", catetoryTag);
				intent.setClass(KnowledgeActivity.this, DetailKnowledgeActivity.class);
                KnowledgeActivity.this.startActivity(intent);				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.knowledge, menu);
		return true;
	}
}
