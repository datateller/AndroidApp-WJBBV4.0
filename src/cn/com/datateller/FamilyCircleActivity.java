package cn.com.datateller;

import cn.com.datateller.utils.UserHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;

public class FamilyCircleActivity extends Activity {

	private WebView webview;
	private Handler handler;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_family_circle);
		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webview.getSettings().setSupportZoom(true);
		webview.setScrollBarStyle(0);
		webview.getSettings().setDefaultTextEncodingName("UTF-8");
		final Integer uid=UserHelper.readUserId(FamilyCircleActivity.this);
		
//		String urlAddress="http://wjbb.cloudapp.net/quan/gettopicwebview/10/";
		String urlAddress="http://wjbb.cloudapp.net/quan/gettopicwebview/"+uid+"/";
		
		webview.setWebViewClient(new WebViewClient(){   
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
            	loadurl(view,url,uid);//������ҳ
                return true;   
            }//��д�������,��webview����
 
        });
        webview.setWebChromeClient(new WebChromeClient(){
        	public void onProgressChanged(WebView view,int progress){//������ȸı������ 
             	if(progress==100){
            		handler.sendEmptyMessage(1);//���ȫ������,���ؽ��ȶԻ���
            	}   
                super.onProgressChanged(view, progress);   
            }   
        });
 
    	progressDialog=new ProgressDialog(FamilyCircleActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("���������У����Ժ�");
        
        loadurl(webview,urlAddress,uid);
        handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {// ����һ��Handler�����ڴ��������߳���UI��ͨѶ

				// Log.d(TAG, "InLine 68"+String.valueOf(msg.what));
				Bundle bundle = msg.getData();
				int result = bundle.getInt("msgid");
				switch (result) {
				case 0:
					progressDialog.show();// ��ʾ���ȶԻ���
					break;
				case 1:
					progressDialog.dismiss();// ���ؽ��ȶԻ��򣬲���ʹ��dismiss()��cancel(),�����ٴε���show()ʱ����ʾ�ĶԻ���СԲȦ���ᶯ��
					AlertDialog.Builder builder = new AlertDialog.Builder(
							FamilyCircleActivity.this);
					builder.setTitle("��ʾ");
					builder.setMessage("�Բ�������δע���Ϊ���ޱ����û�����½���ޱ��������������Ϊ���ṩ�ĸ���ĸ��Ի�����");
					builder.setPositiveButton("ȷ��", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							// dialog.dismiss();
							Intent intent = new Intent();
							intent.setClass(FamilyCircleActivity.this,
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
		getMenuInflater().inflate(R.menu.family_circle, menu);
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
					view.loadUrl(url);// ������ҳ
				}
			}
		}.start();
	}
}
