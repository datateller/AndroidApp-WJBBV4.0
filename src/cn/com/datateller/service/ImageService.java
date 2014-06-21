package cn.com.datateller.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.NameValuePair;

import cn.com.datateller.model.User;
import cn.com.datateller.utils.DateUtils;
import cn.com.datateller.utils.HttpConnection;
import cn.com.datateller.utils.UserHelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ImageService {

	private static final String HOST = "http://yangwabao.com";
	private static final String APPNAME="yangwabao";
	private static final String TAG="ImageService";
	private static final String USERHEADPIC="userhead.jpg";
	
	public Bitmap getImageFromServer(String urlString) {
		// TODO Auto-generated method stub
		InputStream in = getImageStream(urlString);
		if(in==null) return null;
		Bitmap bitmap = BitmapFactory.decodeStream(in);
		return bitmap;
	}

	public boolean SaveBitMap(Bitmap bitmap, String picFilename) {
		// TODO Auto-generated method stub
		String currentDay=DateUtils.getStandardCurrentDay();
		String path = Environment.getExternalStorageDirectory()+"/"+APPNAME+"/"+currentDay;
		File file = new File(path, picFilename);
		if (file.exists())
			return true;
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean SaveBitMap(Bitmap bitmap, String path,String picFilename) {
		// TODO Auto-generated method stub
		File file = new File(path, picFilename);
		if (file.exists())
			return true;
		Log.d(TAG, "#########################"+path+picFilename);
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	
	
	public InputStream getImageStream(String path) {
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return conn.getInputStream();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String getHeadUrlFromServer(User user){
		String urlString=HOST+"/photos/gethead/";
		List<NameValuePair> userlist=UserHelper.initUserInforNameValuePair(user);
		InputStream in = HttpConnection.communicateWithServer(urlString,userlist);
		if (in == null) {
			// 返回值为空，则表示登陆失败
			Log.d(TAG, "in is null");
			return "false";
		}
		try {
			String result = HttpConnection.readString(in);
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String getHeadFromServer(User user) {
		// TODO Auto-generated method stub
		String urlpath=getHeadUrlFromServer(user);
		Bitmap bitmap=getImageFromServer(urlpath);	
		Log.d(TAG, String.valueOf(bitmap));
		String filepath = Environment.getExternalStorageDirectory()+"/"+APPNAME+"/";
		File file = new File(filepath,USERHEADPIC);
		if(file.exists())
			file.delete();
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filepath+USERHEADPIC;
	}
}
