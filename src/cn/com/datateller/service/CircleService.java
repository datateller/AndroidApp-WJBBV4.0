package cn.com.datateller.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import android.graphics.Bitmap;
import android.os.Environment;
import cn.com.datateller.model.Topic;
import cn.com.datateller.model.User;
import cn.com.datateller.utils.DateUtils;
import cn.com.datateller.utils.FileUtils;
import cn.com.datateller.utils.HttpConnection;
import cn.com.datateller.utils.UserHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CircleService {

	private static final String HOST = "http://yangwabao.com";
	private static final String TAG = "CircleService";
	private static final String APPNAME = "yangwabao";
	private static final String TOPICPATH = "topic";

	public String getCircleInforFromServer(User user) {
		// TODO Auto-generated method stub
		String urlString = HOST + "/quan/gettopicwebview/14/";
		List<NameValuePair> userlist = UserHelper
				.initUserInforNameValuePair(user);
		InputStream stream = HttpConnection.communicateWithServer(urlString,
				userlist);
		if (stream == null)
			return null;
		String input = HttpConnection.InputStreamToString(stream);
		return input;
	}

	public String getCircleInforFromServerByNative(User user) {
		// TODO Auto-generated method stub
		String urlString = HOST + "/quan/getcircletopiclist/";
		List<NameValuePair> userlist = UserHelper
				.initUserInforNameValuePair(user);
		InputStream stream = HttpConnection.communicateWithServer(urlString,
				userlist);
		if (stream == null)
			return null;
		String input = HttpConnection.InputStreamToString(stream);
		return input;
	}

	public List<Topic> analysisTopic(String stream) {
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<Topic>>() {
		}.getType();
		return gson.fromJson(stream, type);
	}

	public ArrayList<Topic> readCircleInformationFromFile(String path,
			String filename) {
		// TODO Auto-generated method stub
		ArrayList<Topic> list = null;
		try {
			File file = new File(path, filename);
			FileInputStream is = new FileInputStream(file);
			list = new ArrayList<Topic>();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			Element eleRoot = doc.getRootElement();
			Iterator<Element> iter = eleRoot.elementIterator();
			while (iter.hasNext()) {
				Element eleIdTitle = iter.next();
				if ("BasicTopic".equals(eleIdTitle.getName())) {
					Topic topic = new Topic();
					Iterator<Element> innerIter = eleIdTitle.elementIterator();
					while (innerIter.hasNext()) {
						Element ele = innerIter.next();
						if ("topicid".equals(ele.getName())) {
							topic.setTopicid(Integer.valueOf(ele.getText()));
						} else if ("create_time".equals(ele.getName())) {
							topic.setCreate_time(ele.getText());
						} else if ("from_user".equals(ele.getName())) {
							topic.setFrom_user(ele.getText());
						} else if ("update_time".equals(ele.getName())) {
							topic.setUpdate_time(ele.getText());
						} else if ("content".equals(ele.getName())) {
							topic.setContent(ele.getText());
						} else if ("headurl".equals(ele.getName())) {
							topic.setHeadurl(ele.getText());
						} else if ("commentsnum".equals(ele.getName())) {
							topic.setComments_num(Integer.valueOf(ele.getText()));
						}
					}
					list.add(topic);
					topic = null;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void writeBasicKnowledgeToFile(String path, String filename,
			ArrayList<Topic> basicTopic) {
		// TODO Auto-generated method stub
		StringWriter xmlWriter = new StringWriter();
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("utf-8");
		Element elementRoot = doc.addElement("BasicTopics");
		int length = basicTopic.size();
		for (int i = 0; i < length; i++) {
			Element topic = elementRoot.addElement("BasicTopic");
			Element topicId = topic.addElement("topicid");
			topicId.addText(basicTopic.get(i).getTopicid() + "");
			Element topicCreatetime = topic.addElement("create_time");
			topicCreatetime.addText(basicTopic.get(i).getCreate_time() + "");
			Element topicFromUser = topic.addElement("from_user");
			topicFromUser.addText(basicTopic.get(i).getFrom_user() + "");
			Element topicUpdateTime = topic.addElement("update_time");
			topicUpdateTime.addText(basicTopic.get(i).getUpdate_time() + "");
			Element topicContent = topic.addElement("content");
			topicContent.addText(basicTopic.get(i).getContent() + "");
			Element topicHeadUrl = topic.addElement("headurl");
			topicHeadUrl.addText(basicTopic.get(i).getHeadurl());
			Element topicCommentNum = topic.addElement("commentsnum");
			topicCommentNum.addText(basicTopic.get(i).getComments_num() + "");
		}
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		outputFormat.setEncoding("utf-8");
		outputFormat.setIndent(false);
		outputFormat.setNewlines(true);
		outputFormat.setTrimText(true);
		XMLWriter output = new XMLWriter(xmlWriter, outputFormat);
		try {
			output.write(doc);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileUtils.SaveXml(path, filename, xmlWriter.toString());
	}

	public List<Topic> getUserHeadByUrl(List<Topic> topicList) {
		// TODO Auto-generated method stub
		ImageService service = new ImageService();
		String path = Environment.getExternalStorageDirectory() + "/" + APPNAME
				+ "/" + TOPICPATH + "/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		for (Topic topic : topicList) {
			Bitmap bitmap = service.getImageFromServer(topic.getHeadurl());
			if (bitmap != null) {
				String headFilename = String
						.valueOf(System.currentTimeMillis()) + ".gif";
				service.SaveBitMap(bitmap, path, headFilename);
				topic.setHeadurl(path + headFilename);
			}else{
//				topic.setHeadurl(path+"default.gif");
//				for()
			}
		}
		return topicList;
	}
}
