package cn.com.datateller.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {

	private static final String DATEINFOR="dateinformation";
	public static boolean saveBabyBirthdayInfor(Context context,String dateString){
		SharedPreferences sharedPreferences=context.getSharedPreferences(DATEINFOR, Activity.MODE_PRIVATE);
		//如果sharedPreferences中存储了日期，则使用新的日期更新
		if(readBabyBirthdayInfor(context)!=null) {
			sharedPreferences.edit().clear().commit();
		}
		Editor editor=sharedPreferences.edit();
		editor.putString("date", dateString);
		return editor.commit();
	}
	
	public static String readBabyBirthdayInfor(Context context){
		SharedPreferences sharedPreferences=context.getSharedPreferences(DATEINFOR, Activity.MODE_PRIVATE);
		return sharedPreferences.getString("date", "");
	}
	
	public static boolean saveDateInfor(Context context,String dateString){
		SharedPreferences sharedPreferences=context.getSharedPreferences(DATEINFOR, Activity.MODE_PRIVATE);
		//如果sharedPreferences中存储了日期，则使用新的日期更新
		if(readDateInfor(context)!=null) {
			sharedPreferences.edit().clear().commit();
		}
		Editor editor=sharedPreferences.edit();
		editor.putString("date", dateString);
		return editor.commit();
	}
	
	public static String readDateInfor(Context context){
		SharedPreferences sharedPreferences=context.getSharedPreferences(DATEINFOR, Activity.MODE_PRIVATE);
		return sharedPreferences.getString("date", "");
	}
}
