package cn.com.datateller.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

import android.util.Log;
import cn.com.datateller.model.Baby;
import cn.com.datateller.model.ServerBaby;
import cn.com.datateller.model.User;
import cn.com.datateller.utils.HttpConnection;
import cn.com.datateller.utils.SexEnum;
import cn.com.datateller.utils.UserHelper;

public class UserService {

//	private static final String HOST = "http://www.yangwabao.com";
	private static final String HOST = "http://168.63.219.187:80";
	private static final String TAG = "UserService";

	public boolean userLogin(String username, String password) {
		// TODO Auto-generated method stub
		// ��ʼ��user
		User user = new User();
		user.setUserName(username);
		user.setPassword(password);

		String urlString = HOST + "/user/informationcheck/";
		List<NameValuePair> userlist = UserHelper
				.initUserInforNameValuePair(user);
		// ��������ύ�û��������룬����ȡ����ֵ
		InputStream in = HttpConnection.communicateWithServer(urlString,
				userlist);
		if (in == null) {
			// ����ֵΪ�գ����ʾ��½ʧ��
			Log.d(TAG, "in is null");
			return false;
		}
		try {
			// ����Ϊ�ַ���"True"ʱ����ʾ��½�ɹ�
			String result = HttpConnection.readString(in);
			Log.d(TAG, "#######################"+result);
			if (result.equals("True"))
				return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public boolean userRegister(User user, Baby baby) {
		// TODO Auto-generated method stub
		String result;
		if (baby != null) {
			result = this.userRegisterWithBaby(user, baby);
			if (result.equals("True"))
				return true;
			else
				return false;
		} else {
			result = this.register(user);
			if (result.equals("True"))
				return true;
			return false;
		}
	}

	public String register(User user) {
		// TODO Auto-generated method stub
		String urlString = HOST + "/user/register/";
		List<NameValuePair> userlist = UserHelper
				.initUserInforNameValuePair(user);
		InputStream in = HttpConnection.communicateWithServer(urlString,
				userlist);
		if (in == null) {
			Log.d(TAG, "result is null");
			return null;
		}
		try {
			return HttpConnection.readString(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private String userRegisterWithBaby(User user, Baby baby) {
		// TODO Auto-generated method stub
		String urlString = HOST + "/user/register/";
		List<NameValuePair> userlist = UserHelper
				.initUserInforNameValuePair(user);
		List<NameValuePair> babylist = initBabyInforNameValuePair(baby);
		userlist.addAll(babylist);
		InputStream in = HttpConnection.communicateWithServer(urlString,
				userlist);
		if (in == null) {
			return null;
		}
		try {
			return HttpConnection.readString(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// ��baby����Ϣд��list��
	private List<NameValuePair> initBabyInforNameValuePair(Baby baby) {
		// TODO Auto-generated method stub
		// ����ת��
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		NameValuePair childnamePair = new BasicNameValuePair("babyname",
				baby.getChildname());
		NameValuePair childbirthdayPair = new BasicNameValuePair("birthday",
				baby.getBirthday());
		NameValuePair childweightPair = new BasicNameValuePair("babyweight",
				String.valueOf(baby.getWeight()));
		NameValuePair childheightPair = new BasicNameValuePair("babyheight",
				String.valueOf(baby.getHeight()));
		NameValuePair childsexPair = new BasicNameValuePair("babysex", baby
				.getSex().getName());
		NameValuePair familyAddressPair=new BasicNameValuePair("homeaddr", baby.getFamilyAddress());
		NameValuePair schoolAddressPair=new BasicNameValuePair("schooladdr", baby.getSchoolAddress());
		list.add(childnamePair);
		list.add(childbirthdayPair);
		list.add(childweightPair);
		list.add(childheightPair);
		list.add(childsexPair);
		list.add(familyAddressPair);
		list.add(schoolAddressPair);
		return list;
	}

	public String UpdateInfo(User user, Baby child) {
		// TODO Auto-generated method stub
		//���¸�����Ϣ
		if(child!=null){
			return this.userInforUpdateWithBaby(user,child);
		}
		return this.userInforUpdate(user);
	}

	public String Register(User user, Baby child) {
		// TODO Auto-generated method stub
		if(child!=null){
			return this.userRegisterWithBabyInfor(user, child);
		}
		return  this.userRegister(user);
	}

	private  String userRegisterWithBabyInfor(User user,Baby baby){
		String urlString = HOST + "/user/register/";
		List<NameValuePair> userlist=UserHelper.initUserInforNameValuePair(user);
		List<NameValuePair> babylist=initBabyInforNameValuePair(baby);
		userlist.addAll(babylist);
		InputStream in=HttpConnection.communicateWithServer(urlString, userlist);
		if(in==null){
			return null;
		}
		try {
			return HttpConnection.readString(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private String userRegister(User user){
		String urlString = HOST + "/user/register/";
		List<NameValuePair> userlist=UserHelper.initUserInforNameValuePair(user);
		InputStream in=HttpConnection.communicateWithServer(urlString, userlist);
		if(in==null){
			Log.d(TAG, "result is null");
			return null;
		}
		try {
			return HttpConnection.readString(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private String userInforUpdateWithBaby(User user,Baby baby){
		String urlString = HOST + "/user/update/";
		List<NameValuePair> userlist=UserHelper.initUserInforNameValuePair(user);
		List<NameValuePair> babylist=initBabyInforNameValuePair(baby);
		userlist.addAll(babylist);
		InputStream in=HttpConnection.communicateWithServer(urlString, userlist);
		if(in==null){
			return null;
		}
		try {
			return HttpConnection.readString(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private String userInforUpdate(User user){
		String urlString = HOST + "/user/update/";
		List<NameValuePair> userlist=UserHelper.initUserInforNameValuePair(user);
		InputStream in=HttpConnection.communicateWithServer(urlString, userlist);
		if(in==null){
			return null;
		}
		try {
			return HttpConnection.readString(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public String getUserIdByNameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		String urlString=HOST+"/user/getinfo/";
		User user=new User();
		user.setUserName(username);
		user.setPassword(password);
		List<NameValuePair> userlist=UserHelper.initUserInforNameValuePair(user);
		InputStream in=HttpConnection.communicateWithServer(urlString, userlist);
		if(in==null){
			return null;
		}
		try {
			String result= HttpConnection.readString(in);
//			return result;
			Gson gson=new Gson();
			User u=gson.fromJson(result, User.class);
			return String.valueOf(u.getUserId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Baby getBabyInforFromServerByUsername(User user) {
		// TODO Auto-generated method stub
		String urlString=HOST+"/user/getinfo/";
		List<NameValuePair> userlist=UserHelper.initUserInforNameValuePair(user);
		InputStream in=HttpConnection.communicateWithServer(urlString, userlist);
		if(in==null){
			Log.d(TAG, "in is null");
			return null;
		}
		try{
			String result=HttpConnection.readString(in);
			Log.d(TAG, "################################"+result);
			Gson gson=new Gson();
			ServerBaby sbaby=gson.fromJson(result, ServerBaby.class);
			Log.d(TAG, "#################################"+String.valueOf(sbaby));
			Baby baby=new Baby();
			baby.setChildname(sbaby.getBabyname());
			baby.setBirthday(sbaby.getBirthday());
			baby.setWeight(sbaby.getWeight());
			baby.setHeight(sbaby.getHeight());
			if(sbaby.getSex().equals("��"))
				baby.setSex(SexEnum.BOY);
			else baby.setSex(SexEnum.GIRL);
			baby.setSchoolAddress(sbaby.getSchooladdr());
			baby.setFamilyAddress(sbaby.getHomeaddr());
			baby.setUserid(sbaby.getUserid());
			return baby;
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}
