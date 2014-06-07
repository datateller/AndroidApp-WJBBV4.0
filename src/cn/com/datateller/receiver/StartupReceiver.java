package cn.com.datateller.receiver;

import cn.com.datateller.service.NotificationService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



public class StartupReceiver extends BroadcastReceiver {

	private final static String TAG="StartupReceiver";
	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++)
			 Log.d(TAG, "In the StartupReceiver ");
		Intent service=new Intent(context,NotificationService.class);
		context.startService(service);
		/*Intent mainIntent = new Intent(context, WelcomeActivity.class);
		mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(mainIntent);*/
	}

}
