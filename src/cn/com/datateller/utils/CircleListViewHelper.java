package cn.com.datateller.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.datateller.model.Topic;

public class CircleListViewHelper {

	public List<Map<String, Object>> getData(List<Topic> basicTopicList) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (int i = 0; i < basicTopicList.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("fromuser", basicTopicList.get(i).getFrom_user());
			map.put("content", basicTopicList.get(i).getContent());
			map.put("createtime", basicTopicList.get(i).getCreate_time());
			map.put("replynum", basicTopicList.get(i).getComments_num());
			map.put("headurl",basicTopicList.get(i).getHeadurl());
			list.add(map);
		}
		System.out.println(list.size());
		return list;
	}
	
}
