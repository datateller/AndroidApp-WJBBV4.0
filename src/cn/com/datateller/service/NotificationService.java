package cn.com.datateller.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import cn.com.datateller.MainActivity;
import cn.com.datateller.R;

public class NotificationService extends Service{
	private final static String TAG="NotificationService";
	private Handler handler;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		handler = new Handler() {
			@Override
			// TODO 网络连接过长也是登陆失败的一种
			public void handleMessage(Message msg) {
			    Bundle bundle=msg.getData();
			    int count=bundle.getInt("count");
				Context context=getApplicationContext();
				NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
				
				
				PendingIntent pintent=PendingIntent.getActivity(context, 0, new Intent(context,MainActivity.class), 0);		
		        int defaults=Notification.DEFAULT_ALL;
				Notification.Builder builder=new Notification.Builder(context)
		            .setSmallIcon(R.drawable.ic_launcher)
		            .setTicker("养娃宝给您发新的消息了")
		            .setContentIntent(pintent)
		            .setDefaults(Notification.FLAG_NO_CLEAR);
		       Notification notification= builder.getNotification();
		       manager.notify(count, notification);	
				
			}
		};
		// 新开启一个线程，向服务器提交登陆信息
		new Thread(new Runnable() {
			@Override
			public void run() {
				int count=0;
				while(true){
					count++;
					try {
						Thread.sleep(1000*60*12);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putInt("count", count);
					msg.setData(bundle);
					handler.sendMessage(msg);
				}
			}
		}).start();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}
}
