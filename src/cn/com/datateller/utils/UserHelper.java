package cn.com.datateller.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import cn.com.datateller.model.User;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

public class UserHelper {

	private static final String TAG="UserHelper";
	private static final String USERINFOR="userInformation";
	
	public static String readUserName(Context context){
		SharedPreferences sharedPreferences=context.getSharedPreferences(USERINFOR, Activity.MODE_PRIVATE);
		return sharedPreferences.getString("username", "");
	}
	
	public static String readPassword(Context context){
		SharedPreferences sharedPreferences=context.getSharedPreferences(USERINFOR, Activity.MODE_PRIVATE);
		return sharedPreferences.getString("password", "");
	}
	
	public static int readUserId(Context context) {
		// TODO Auto-generated method stub
		SharedPreferences sharedPreferences=context.getSharedPreferences(USERINFOR, Activity.MODE_PRIVATE);
		return sharedPreferences.getInt("id", 3);
	}
	
	public static boolean deleteUserInfo(Context context){
		SharedPreferences sharedPreferences=context.getSharedPreferences(USERINFOR, Activity.MODE_PRIVATE);
		return sharedPreferences.edit().clear().commit();
	}
	
	public static boolean saveUserInfo(Context context,String username,String password,int id){
		SharedPreferences sharedPreferences=context.getSharedPreferences(USERINFOR, Activity.MODE_PRIVATE);
		System.out.println(sharedPreferences);
		Editor editor=sharedPreferences.edit();
		editor.putString("username", username);
		editor.putString("password", password);
		editor.putInt("id", id);
		return editor.commit();
	}
	
	public static  List<NameValuePair> initUserInforNameValuePair(User user){
		user.setUserName(Base64.encodeToString(user.getUserName().getBytes(), Base64.DEFAULT));
		user.setPassword(Base64.encodeToString(user.getPassword().getBytes(), Base64.DEFAULT));
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		NameValuePair userNamePair = new BasicNameValuePair("username",
				user.getUserName());
		NameValuePair passwordPair = new BasicNameValuePair("password",
				user.getPassword());
		list.add(userNamePair);
		list.add(passwordPair);
		return list;
	}

}
