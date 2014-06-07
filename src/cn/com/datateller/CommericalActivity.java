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
import cn.com.datateller.utils.UserHelper;

public class CommericalActivity extends Activity {

	private static final String TAG="CommericalActivity";
	private static final String APPNAME="yangwabao";
	private static final String NAME="CommericalInformation";
	private ListView listview;
	private Handler handler;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commerical);
		showCommericalInformation();
	}

	private void showCommericalInformation() {
		boolean sdcardIsmount = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if(sdcardIsmount==false){
			getBasicCommericalInforFromServerAndWriteToFile(0,null,null);
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
			List<BasicInformation> basicCommericalList=service.readBasicInformationFromFile(path, filename);
			show(basicCommericalList);
			return;
		} else {
			getBasicCommericalInforFromServerAndWriteToFile(0,path,filename);
		}
	}

	private void show(final List<BasicInformation> basicCommericalList) {
		// TODO Auto-generated method stub
		ListViewHelper helper=new ListViewHelper();
		List<Map<String, Object>> data=helper.getData(basicCommericalList);
		listview=(ListView)findViewById(R.id.basic_knowledge_content);
//		初始化适配器
		MyListViewAdapter adapter=new MyListViewAdapter(CommericalActivity.this,data);
//		绑定适配器到listview上
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				ListView lview = (ListView) parent;
				int index = (Integer) lview.getItemAtPosition(position);
				int knowledgeId = Integer.valueOf(basicCommericalList.get(index)
						.getId());
				String knowledgePicUrl = basicCommericalList.get(index).getPic();
				String catetoryTag=null;
				String link=basicCommericalList.get(index).getLink();
				if(link.indexOf("knowledge")>0)  
					catetoryTag="knowledge";
				else if(link.indexOf("consumption")>0)
					catetoryTag="consumption";
				else if(link.indexOf("shop")>0)
					catetoryTag="shop";
				Intent intent = new Intent();
				intent.putExtra("commericalId", knowledgeId);
				intent.putExtra("catetoryTag", catetoryTag);
				intent.setClass(CommericalActivity.this, DetailCommericalActivity.class);
				CommericalActivity.this.startActivity(intent);				
			}
		});
	}

	private void getBasicCommericalInforFromServerAndWriteToFile(final int day, final String path, final String filename) {
		// TODO Auto-generated method stub
		final ProgressDialog myDialog = ProgressDialog.show(
				CommericalActivity.this, "请稍等", "正在获取数据...", true, true);
		handler = new Handler() {
			@Override
			// TODO 网络连接过长也是登陆失败的一种
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				ArrayList basicCommercial = bundle
						.getParcelableArrayList("basicCommerical");
				myDialog.dismiss();
				InformationService service=new InformationService();
				if(basicCommercial==null) {
					DialogHelper.showDialog(CommericalActivity.this, "获取特价商品信息失败");
					return;
				}
				if(path!=null||filename!=null) service.writeBasicKnowledgeToFile(path, filename,basicCommercial);
				show(basicCommercial);
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				InformationService service = new InformationService();
				User user = new User();
				user.setUserName(UserHelper
						.readUserName(CommericalActivity.this));
				user.setPassword(UserHelper
						.readPassword(CommericalActivity.this));
				ArrayList<BasicInformation> basicCommericallist = service
						.getBasicInformationFromServer(user,0,"consumptions");
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
		getMenuInflater().inflate(R.menu.commerical, menu);
		return true;
	}

}
