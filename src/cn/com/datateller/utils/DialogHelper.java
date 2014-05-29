package cn.com.datateller.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

public class DialogHelper {
	private static final String TAG="DialogHelper";
	
	public static void showDialog(Context context,String message){
		Log.d(TAG, "In the function showDialog of the ActivityUtil");
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setTitle("提示");
		builder.setMessage(message);
		builder.setPositiveButton("确认", new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
//				dialog.dismiss();
			}
		});
		builder.show();
	}
}
