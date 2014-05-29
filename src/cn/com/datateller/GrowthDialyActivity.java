package cn.com.datateller;

import cn.com.datateller.utils.DialogHelper;
import cn.com.datateller.utils.UserHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;

public class GrowthDialyActivity extends Activity {

	private WebView webview;
	private Handler handler;
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_growth_dialy);
		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webview.getSettings().setSupportZoom(true);
		webview.setScrollBarStyle(0);
		webview.getSettings().setDefaultTextEncodingName("UTF-8");
		String uid=String.valueOf(UserHelper.readUserId(GrowthDialyActivity.this));
		/*if(uid.equals("3")){
			DialogHelper.showDialog(GrowthDialyActivity.this, "请您注册成为养娃宝用户，这样能获得我们对您提供的个性化服务");
			Intent intent=new Intent();
			intent.setClass(GrowthDialyActivity.this, LoginActivity.class);
			startActivity(intent);
			return;
		}*/
		
//		webview.loadUrl("http://wjbb.cloudapp.net/quan/gettopicwebview/10/");
//		String urlAddress="http://wjbb.cloudapp.net/quan/gettopicwebview/10/";
		String urlAddress="http://wjbb.cloudapp.net/quan/gettopicwebview/"+uid+"/";
		
		webview.setWebViewClient(new WebViewClient(){   
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
            	loadurl(view,url);//载入网页
                return true;   
            }//重写点击动作,用webview载入
 
        });
        webview.setWebChromeClient(new WebChromeClient(){
        	public void onProgressChanged(WebView view,int progress){//载入进度改变而触发 
             	if(progress==100){
            		handler.sendEmptyMessage(1);//如果全部载入,隐藏进度对话框
            	}   
                super.onProgressChanged(view, progress);   
            }   
        });
 
    	progressDialog=new ProgressDialog(GrowthDialyActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("数据载入中，请稍候！");
        
        loadurl(webview,urlAddress);
        handler=new Handler(){
        	public void handleMessage(Message msg)
    	    {//定义一个Handler，用于处理下载线程与UI间通讯
    	      if (!Thread.currentThread().isInterrupted())
    	      {
    	        switch (msg.what)
    	        {
    	        case 0:
    	        	progressDialog.show();//显示进度对话框        	
    	        	break;
    	        case 1:
    	        	progressDialog.hide();//隐藏进度对话框，不可使用dismiss()、cancel(),否则再次调用show()时，显示的对话框小圆圈不会动。
    	        	break;
    	        }
    	      }
    	      super.handleMessage(msg);
    	    }
        };
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.growth_dialy, menu);
		return true;
	}

	public void loadurl(final WebView view,final String url){
    	new Thread(){
        	public void run(){
        		handler.sendEmptyMessage(0);
        		view.loadUrl(url);//载入网页
        	}
        }.start();
    }
}
