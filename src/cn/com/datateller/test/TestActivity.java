package cn.com.datateller.test;

import java.util.Date;
import java.util.List;

import cn.com.datateller.model.Baby;
import cn.com.datateller.model.BasicInformation;
import cn.com.datateller.model.User;
import cn.com.datateller.service.CircleService;
import cn.com.datateller.service.InformationService;
import cn.com.datateller.service.UserService;
import cn.com.datateller.utils.SexEnum;
import android.test.AndroidTestCase;
import android.util.Log;

public class TestActivity extends AndroidTestCase{

	private static final String TAG="TestActivity";
	
/*	public void testGetBasicKnowledgesFromServer(){
		User user=new User();
		user.setUserName("anonymous");
		user.setPassword("wjbb123");
		int age=2;
		InformationService service=new InformationService();
		Log.d(TAG, user.getUserName());
		List<BasicInformation> basicKnowledge=service.getBasicInformationFromServer(user, age,"knowledges");
		Log.d(TAG, basicKnowledge.toString());
		System.out.println(basicKnowledge);
	}
	*/
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
	
	public void testGetUserIdByNameAndPassword(){
		User user=new User();
		user.setUserName("hujun");
		user.setPassword("123");
		UserService service=new UserService();
		Log.d(TAG,service.getUserIdByNameAndPassword("hujun", "123"));
		
	}
	
	
	/*public void testGetCircleInforFromServer(){
	   User user=new User();
	   user.setUserId(3);
	   user.setUserName("shentest2");
	   user.setPassword("shentest2");
	   CircleService service=new CircleService();
	   String html=service.getCircleInforFromServer(user);
	   System.out.println(html);
	   
		
	}*/
	
	
	
}
