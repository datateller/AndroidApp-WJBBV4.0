package cn.com.datateller.service;

import java.io.InputStream;
import java.util.List;

import org.apache.http.NameValuePair;

import cn.com.datateller.model.User;
import cn.com.datateller.utils.HttpConnection;
import cn.com.datateller.utils.UserHelper;


public class CircleService {

	private static final String HOST = "http://168.63.219.187:80/";
	private static final String TAG = "CircleService";
	private static final String APPNAME = "yangwabao";
	
	public String getCircleInforFromServer(User user) {
		// TODO Auto-generated method stub
		/*
		 * String urlString = HOST + "mobile/getconsumptions/";
		Log.d(TAG, user.getUserName());
		List<NameValuePair> userlist = UserHelper
				.initUserInforNameValuePair(user);
		NameValuePair agePair = new BasicNameValuePair("age",
				String.valueOf(age));
		NameValuePair knumber = new BasicNameValuePair("knumber",
				String.valueOf(5));
		NameValuePair snumber = new BasicNameValuePair("snumber",
				String.valueOf(2));
		NameValuePair cnumber = new BasicNameValuePair("cnumber",
				String.valueOf(2));
		userlist.add(agePair);
		userlist.add(knumber);
		userlist.add(snumber);
		userlist.add(cnumber);

		InputStream stream = HttpConnection.communicateWithServer(urlString,
				userlist);
		if (stream == null)
			return null;
		String input = HttpConnection.InputStreamToString(stream);
		Log.d(TAG, input);
		if (input.equals("AUTH_FAILED"))
			return null;
		ArrayList<BasicInformation> list;
		System.out.println(input.toString());
		list = AnalysisBasicKnowledge(input.substring(1, input.length()-1));
//		list =AnalysisBasicKnowledge(input);
		return list;*/
		String urlString=HOST+"/quan/gettopicwebview/14/";
		List<NameValuePair> userlist = UserHelper
				.initUserInforNameValuePair(user);
		InputStream stream = HttpConnection.communicateWithServer(urlString,
				userlist);
		if (stream == null)
			return null;
		String input = HttpConnection.InputStreamToString(stream);
		return input;
	}

}
