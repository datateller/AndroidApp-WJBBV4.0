package cn.com.datateller;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;

public class AgeCircleDetailActivity extends Activity {

	private WebView webview;
	private Handler handler;
	private ProgressDialog progressDialog;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_age_circle_detail);
		final int topicId = getIntent().getIntExtra("topicId", 0);
		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webview.getSettings().setSupportZoom(true);
		webview.setScrollBarStyle(0);
		webview.getSettings().setDefaultTextEncodingName("UTF-8");
		final int uid=18;
		String urlAddress="http://wjbb.cloudapp.net/quan/gettopicwebview/"+uid+"/";
		webview.setWebViewClient(new WebViewClient(){   
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
            	loadurl(view,url,uid);//载入网页
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
 
    	progressDialog=new ProgressDialog(AgeCircleDetailActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("数据载入中，请稍候！");
        
        loadurl(webview,urlAddress,uid);
        handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯

				// Log.d(TAG, "InLine 68"+String.valueOf(msg.what));
				Bundle bundle = msg.getData();
				int result = bundle.getInt("msgid");
				switch (result) {
				case 0:
					progressDialog.show();// 显示进度对话框
					break;
				case 1:
					progressDialog.dismiss();// 隐藏进度对话框，不可使用dismiss()、cancel(),否则再次调用show()时，显示的对话框小圆圈不会动。
					AlertDialog.Builder builder = new AlertDialog.Builder(
							AgeCircleDetailActivity.this);
					builder.setTitle("提示");
					builder.setMessage("对不起，您尚未注册成为养娃宝的用户，登陆养娃宝，您将获得我们为您提供的更多的个性化服务");
					builder.setPositiveButton("确认", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							// dialog.dismiss();
							Intent intent = new Intent();
							intent.setClass(AgeCircleDetailActivity.this,
									LoginActivity.class);
							startActivity(intent);
						}
					}).show();
					break;
				}
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.age_circle_detail, menu);
		return true;
	}

	public void loadurl(final WebView view, final String url, final Integer uid) {

		new Thread() {
			public void run() {
				Message msg = new Message();
				Bundle bundle = new Bundle();
				if (uid == 3) {
					// handler.sendEmptyMessage(1);
					bundle.putInt("msgid", 1);
					msg.setData(bundle);
					handler.sendMessage(msg);
				} else {
					bundle.putInt("msgid", 0);
					msg.setData(bundle);
					handler.sendMessage(msg);
					view.loadUrl(url);// 载入网页
				}
			}
		}.start();
	}
}
