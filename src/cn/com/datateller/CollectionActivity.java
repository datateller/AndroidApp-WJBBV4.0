package cn.com.datateller;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.datateller.model.BasicInformation;
import cn.com.datateller.service.InformationService;
import cn.com.datateller.utils.DialogHelper;
import cn.com.datateller.utils.ListViewHelper;
import cn.com.datateller.utils.MyListViewAdapter;

public class CollectionActivity extends Activity {

	private static final String TAG="CollectKnowledgeActivity";
	private static final String COLLECT="collect";
	private static final String NAME="BasicKnowledge";
	private static final String BASICKNOWLEDGE="basicKnowlege";
	private static final String APPNAME="yangwabao";
	private TextView collect_titleText;
	private ListView listview;
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);
		showCollectKnowledge();
	}

	private void showCollectKnowledge() {
		// TODO Auto-generated method stub
		String path = Environment.getExternalStorageDirectory()+"/"+APPNAME+"/"+COLLECT;
		File fileDir=new File(path);
		if(!fileDir.exists()){
			DialogHelper.showDialog(CollectionActivity.this, "对不起，您还没有收藏知识");
			return;
		}
		String filename=BASICKNOWLEDGE+".xml";
		File file=new File(path,filename);
		if(!file.exists()){
			DialogHelper.showDialog(CollectionActivity.this, "对不起，您还没有收藏知识");
			return;
		}
		InformationService service=new InformationService();
		List<BasicInformation> basicKnowledgeList=service.readBasicInformationFromFile(path,filename);
		show(basicKnowledgeList);
	}

	private void show(final List<BasicInformation> basicKnowledgeList) {
		// TODO Auto-generated method stub
		ListViewHelper helper=new ListViewHelper();
		List<Map<String, Object>> data=helper.getData(basicKnowledgeList);
		listview=(ListView)findViewById(R.id.collect_basic_knowledge_content);
//		初始化适配器
		MyListViewAdapter adapter=new MyListViewAdapter(CollectionActivity.this,data);
//		绑定适配器到listview上
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
				Intent intent = new Intent();
				intent.putExtra("knowledgeId", knowledgeId);
				intent.setClass(CollectionActivity.this, DetailCollectionKnowledgeActivity.class);
				CollectionActivity.this.startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.collection, menu);
		return true;
	}

}
