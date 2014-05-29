package cn.com.datateller;

import cn.com.datateller.service.InformationService;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;

public class DetailCollectionKnowledgeActivity extends Activity {

	private WebView webview;
	private static final String TAG = "DetailCollectionKnowledgeActivity";
	private static final String APPNAME="yangwabao";
	private static final String COLLECT="collect";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_collection_knowledge);
        int knowledgeId=getIntent().getIntExtra("knowledgeId", 0);
		
		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webview.getSettings().setSupportZoom(true);
		webview.setScrollBarStyle(0);
		webview.getSettings().setDefaultTextEncodingName("UTF-8");
		
		String path = Environment.getExternalStorageDirectory()+"/"+APPNAME+"/"+COLLECT;
		String filename=String.valueOf(knowledgeId)+".html";
		InformationService service=new InformationService();
		String knowledge=service.readKnowledgeFromFile(path,filename);
		webview.loadDataWithBaseURL(null, knowledge, "text/html", "UTF-8", null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_collection_knowledge, menu);
		return true;
	}

}
