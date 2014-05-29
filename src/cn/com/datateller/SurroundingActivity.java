package cn.com.datateller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.datateller.model.BasicInformation;
import cn.com.datateller.model.User;
import cn.com.datateller.service.InformationService;
import cn.com.datateller.utils.DateUtils;
import cn.com.datateller.utils.DialogHelper;
import cn.com.datateller.utils.ListViewHelper;
import cn.com.datateller.utils.MyListViewAdapter;
import cn.com.datateller.utils.UserHelper;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SurroundingActivity extends Activity {

	private static final String TAG="SurroundingActivity";
	private static final String APPNAME="yangwabao";
	private static final String NAME="Surrounding";
	private ListView listview;
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_surrounding);
		showSurrounding();
	}

	private void showSurrounding() {
		// TODO Auto-generated method stub
		boolean sdcardIsmount = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if(sdcardIsmount==false){
			getBasicSurroundinglInforFromServerAndWriteToFile(0,null,null);
			return;
		}
		String currentDay = DateUtils.getStandardCurrentDay();
		File fileDir=new File(Environment.getExternalStorageDirectory()+"/"+APPNAME+"/"+currentDay);
		if(!fileDir.exists()){
			fileDir.mkdirs();
			//TODO 删除系统中存在的失效文件
		}
		String filename = NAME+".xml";
		String path = Environment.getExternalStorageDirectory() +"/"+APPNAME+"/"+currentDay;
		boolean isExists = new File(path, filename).exists();
		if (isExists) {
//          直接从文件中读取basicknowledge，并展示返回
			InformationService service=new InformationService();
			List<BasicInformation> basicSurroundingList=service.readBasicInformationFromFile(path, filename);
			show(basicSurroundingList);
			return;
		} else {
			getBasicSurroundinglInforFromServerAndWriteToFile(0,path,filename);
		}
	}

	private void getBasicSurroundinglInforFromServerAndWriteToFile(final int day, final String path, final String filename) {
		// TODO Auto-generated method stub
		final ProgressDialog myDialog = ProgressDialog.show(
				SurroundingActivity.this, "请稍等", "正在获取数据...", true, true);
		handler = new Handler() {
			@Override
			// TODO 网络连接过长也是登陆失败的一种
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				ArrayList basicSurrounding = bundle
						.getParcelableArrayList("basicCommerical");
				myDialog.dismiss();
				InformationService service=new InformationService();
				if(basicSurrounding==null) {
					DialogHelper.showDialog(SurroundingActivity.this, "获取特价商品信息失败");
					return;
				}
				if(path!=null||filename!=null) service.writeBasicKnowledgeToFile(path, filename,basicSurrounding);
				show(basicSurrounding);
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				InformationService service = new InformationService();
				User user = new User();
				user.setUserName(UserHelper
						.readUserName(SurroundingActivity.this));
				user.setPassword(UserHelper
						.readPassword(SurroundingActivity.this));
				ArrayList<BasicInformation> basicCommericallist = service
						.getBasicInformationFromServer(user,0,"shops");
				if(basicCommericallist!=null){
					basicCommericallist = service.getIconByUrl(basicCommericallist);
				}
				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putParcelableArrayList("basicCommerical",
						(ArrayList<? extends Parcelable>) basicCommericallist);
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comsumption, menu);
		return true;
	}

	private void show(final List<BasicInformation> basicSurroundingList) {
		// TODO Auto-generated method stub
		ListViewHelper helper=new ListViewHelper();
		List<Map<String, Object>> data=helper.getData(basicSurroundingList);
		listview=(ListView)findViewById(R.id.basic_knowledge_content);
//		初始化适配器
		MyListViewAdapter adapter=new MyListViewAdapter(SurroundingActivity.this,data);
//		绑定适配器到listview上
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				ListView lview = (ListView) parent;
				int index = (Integer) lview.getItemAtPosition(position);
				int knowledgeId = Integer.valueOf(basicSurroundingList.get(index)
						.getId());
				String knowledgePicUrl = basicSurroundingList.get(index).getPic();
				String catetoryTag=null;
				String link=basicSurroundingList.get(index).getLink();
				if(link.indexOf("knowledge")>0)  
					catetoryTag="knowledge";
				else if(link.indexOf("consumption")>0)
					catetoryTag="consumption";
				else if(link.indexOf("shop")>0)
					catetoryTag="shop";
				Intent intent = new Intent();
				intent.putExtra("surroundingId", knowledgeId);
				intent.putExtra("catetoryTag", catetoryTag);
				intent.setClass(SurroundingActivity.this, DetailSurroundingActivity.class);
				SurroundingActivity.this.startActivity(intent);				
			}
		});
	}
}
