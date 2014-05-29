package cn.com.datateller;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import cn.com.datateller.model.User;
import cn.com.datateller.service.InformationService;
import cn.com.datateller.utils.DateUtils;
import cn.com.datateller.utils.DialogHelper;
import cn.com.datateller.utils.UserHelper;

public class DetailCommericalActivity extends Activity {

	private WebView webview;
	private Handler handler;
	private static final String TAG = "DetailCommericalActivity";
	private static final String NAME = "Commerical";
	private static final String APPNAME = "yangwabao";
	private static final String BASICCOMMERICAL = "basicCommerical";
	private String categoryTag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_commerical);
		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webview.getSettings().setSupportZoom(true);
		webview.setScrollBarStyle(0);
		webview.getSettings().setDefaultTextEncodingName("UTF-8");

		final int commericalId = getIntent().getIntExtra("commericalId", 0);
		categoryTag = getIntent().getStringExtra("catetoryTag");
		
		ShowCommerical(commericalId);
	}

	private void ShowCommerical(int commericalId) {
		// TODO Auto-generated method stub
		boolean sdcardIsmount = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if(sdcardIsmount==false){
			getKnowledgeFromServerAndWriteToFile(commericalId,null,null);
			return;
		}
		String currentDay = DateUtils.getStandardCurrentDay();
		String filename = NAME + String.valueOf(commericalId) + ".html";
		String path = Environment.getExternalStorageDirectory()+"/"+APPNAME+"/"+currentDay;
		boolean isExists = new File(path, filename).exists();
		if (isExists){
			InformationService service=new InformationService();
			String knowledge;
			knowledge=service.readKnowledgeFromFile(path,filename);
			show(knowledge);
		}else{
			getKnowledgeFromServerAndWriteToFile(commericalId,path,filename);
		}
	}



	private void getKnowledgeFromServerAndWriteToFile(final int commericalId,
			final String path, final String filename) {
		// TODO Auto-generated method stub
		final ProgressDialog myDialog = ProgressDialog.show(
				DetailCommericalActivity.this, "请稍等", "正在获取数据...", true, true);
				handler = new Handler() {
					@Override
					// TODO 网络连接过长也是登陆失败的一种
					public void handleMessage(Message msg) {
						Bundle bundle = msg.getData();
						String knowledge=bundle.getString("knowledge");
						myDialog.dismiss();
						if(filename==null) {
							DialogHelper.showDialog(DetailCommericalActivity.this, "获取知识失败");
							return;
						}
						InformationService service=new InformationService();
						if(path!=null||filename!=null) service.writeKnowledgeToFile(path, filename,knowledge);
						show(knowledge);
					}
				};
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						InformationService service = new InformationService();
						User user = new User();
						user.setUserName(UserHelper
								.readUserName(DetailCommericalActivity.this));
						user.setPassword(UserHelper
								.readPassword(DetailCommericalActivity.this));
						String knowledge=service.getInformationById(commericalId, user,categoryTag);
						
						/*Knowledge knowledge=service.getKnowledgeById(knowledgeId, user);
						//TODO 此处的Abstract以及icon字段的值，暂时设为test
						knowledge.setAbstract("test");
						knowledge.setIcon("test");*/
						Message msg = new Message();
						Bundle bundle = new Bundle();
						bundle.putString("knowledge", knowledge);
						msg.setData(bundle);
						handler.sendMessage(msg);
					}
				}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_commerical, menu);
		return true;
	}

	private void show(String knowledge) {
		// TODO Auto-generated method stub
		webview.loadDataWithBaseURL(null, knowledge, "text/html", "UTF-8", null);
	}
	
}
