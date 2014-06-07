package cn.com.datateller.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import android.os.Environment;
import android.test.AndroidTestCase;
import android.util.Log;
import cn.com.datateller.model.BasicInformation;
import cn.com.datateller.model.User;
import cn.com.datateller.service.InformationService;
import cn.com.datateller.utils.DateUtils;

public class TestActivity extends AndroidTestCase{

	private static final String TAG="TestActivity";
	private static final String NAME="BasicKnowledge";
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
	
	public void testDeleteCacheFile(){
		String filename = NAME+".xml";
		String currentDay = DateUtils.getStandardCurrentDay();
		String path = Environment.getExternalStorageDirectory() +"/"+APPNAME+"/"+currentDay;
		InformationService service=new InformationService();
		service.deleteCacheFile(path,filename);
		
	}
	
}
