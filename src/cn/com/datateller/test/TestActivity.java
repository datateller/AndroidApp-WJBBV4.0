package cn.com.datateller.test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Assert;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.test.AndroidTestCase;
import android.util.Base64;
import android.util.Log;
import cn.com.datateller.model.Baby;
import cn.com.datateller.model.BasicInformation;
import cn.com.datateller.model.Topic;
import cn.com.datateller.model.User;
import cn.com.datateller.service.CircleService;
import cn.com.datateller.service.ImageService;
import cn.com.datateller.service.InformationService;
import cn.com.datateller.service.UserService;
import cn.com.datateller.utils.DateUtils;
import cn.com.datateller.utils.HttpConnection;
import cn.com.datateller.utils.SexEnum;
import cn.com.datateller.utils.UserHelper;

public class TestActivity extends AndroidTestCase{

	private static final String TAG="TestActivity";
	private static final String NAME="AgeTopic";
	private static final String APPNAME="yangwabao";

	/*	public void testGetBasicKnowledgesFromServer(){
		User user=new User();
		user.setUserName("anonymous");
		user.setPassword("wjbb123");
		int age=3;
		InformationService service=new InformationService();
		Log.d(TAG, user.getUserName());
		List<BasicInformation> basicKnowledge=service.getBasicInformationFromServer(user, age,"knowledges");
		Log.d(TAG, basicKnowledge.toString());
		Log.d(TAG, String.valueOf(basicKnowledge.size()));
//		Assert.assertEquals(basicKnowledge.size(), 5);
		System.out.println(basicKnowledge);
	}*/
	
/*	public void testRegister(){
		User user=new User();
		user.setUserName("hujun");
		user.setPassword("123");
		Baby baby=new Baby();
		baby.setChildname("test");
		baby.setWeight(15.0f);
		baby.setFamilyAddress("北京市海淀区");
		baby.setHeight(55.0f);
		baby.setSchoolAddress("北京市海淀区");
		baby.setSex(SexEnum.BOY);
		baby.setBirthday(new Date());	
		UserService service=new UserService();
		String result=service.Register(user, baby);
		Log.d(TAG, result);
	}*/
	
/*	public void testGetUserIdByNameAndPassword(){
		User user=new User();
		user.setUserName("hujun");
		user.setPassword("123");
		UserService service=new UserService();
		Log.d(TAG,service.getUserIdByNameAndPassword("hujun", "123"));
	}*/
	
/*	public void getBasicInformationFromServer(){
		User user=new User();
		user.setUserName("anonymous");
		user.setPassword("wjbb123");
		InformationService service=new InformationService();
		ArrayList<BasicInformation> list=service.getBasicInformationFromServer(user, 2, "knowledges");
		Log.d(TAG, String.valueOf(list.size()));
		for ( BasicInformation basic : list) {
			System.out.println("test");
			Log.d(TAG, "This is a test");
			System.out.println(basic.toString());
			Log.d(TAG, basic.toString());
		}
	}*/
	
	/*public void testGetCircleInforFromServer(){
	   User user=new User();
	   user.setUserId(3);
	   user.setUserName("shentest2");
	   user.setPassword("shentest2");
	   CircleService service=new CircleService();
	   String html=service.getCircleInforFromServer(user);
	   System.out.println(html);
	}*/
	
/*	public void testDeleteCacheFile(){
		String filename = NAME+".xml";
		String currentDay = DateUtils.getStandardCurrentDay();
		String path = Environment.getExternalStorageDirectory() +"/"+APPNAME+"/"+currentDay;
		InformationService service=new InformationService();
		service.deleteCacheFile(path,filename);
	}*/
/*	
	public void testUploadPic() throws ParseException, Exception, IOException{
		User user=new User();
		user.setUserName("hujun");
		user.setPassword("123");
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		String filepath="/media/external/images/media/15903";
		String result=HttpConnection.upLoadPicFileToServer(user, filepath);
		Log.d(TAG, String.valueOf(result));
	}*/
	
/*	public void testgetHeadFromServer(){
		User user=new User();
		user.setUserName("hujun");
		user.setPassword("123");
		ImageService service=new ImageService();
		Log.d(TAG, String.valueOf(service.getHeadFromServer(user)));
	}*/
	
/*	public void testUserLogin(){
		User user=new User();
		user.setUserName("test123");
		user.setPassword("123");
		UserService service=new UserService();
		Log.d(TAG, String.valueOf(service.userLogin("test123", "123")));
	}*/
	
/*	public void testGetCircleTopic(){
	    User user=new User();
	    user.setUserName("xzh");
	    user.setPassword("111111");
	    CircleService service=new CircleService();
	    String result=service.getCircleInforFromServerByNative(user);
	    Log.d(TAG, result);
	    Log.d(TAG,"#########################"+String.valueOf(service.analysisTopic(result)));
	}*/
	
/*	public void testGetreadCircleInformationFromFile(){
		String currentDay = DateUtils.getStandardCurrentDay();
		String filename = NAME+".xml";
		String path = Environment.getExternalStorageDirectory() +"/"+APPNAME+"/"+currentDay;
		Log.d(TAG, filename);
		Log.d(TAG, path);
		CircleService service=new CircleService();
		List<Topic> basicCircleList=service.readCircleInformationFromFile(path, filename);
		Log.d(TAG, String.valueOf(basicCircleList));
	}*/
	
/*	public void testUserLogin(){
		User user=new User();
		user.setUserName("hujun");
		user.setPassword("123");
		UserService service=new UserService();
		service.userLogin("hujun", "123");
		Log.d(TAG, String.valueOf(service.userLogin("hujun", "123")));
	}*/
	
/*	public void testgetBabyInforFromServerByUsername(){
		User user=new User();
		user.setUserName("hujun");
		user.setPassword("123");
		UserService service=new UserService();
		Log.d(TAG,String.valueOf(service.getBabyInforFromServerByUsername(user)));
	}*/
	
	public void testgetHeadUrlFromServer(){
		User user=new User();
		user.setUserName("xzh");
		user.setPassword("111111");
		ImageService service=new ImageService();
        Log.d(TAG,service.getHeadUrlFromServer(user));
	}
	
	
}
