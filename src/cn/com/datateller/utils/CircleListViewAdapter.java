package cn.com.datateller.utils;

import java.util.List;
import java.util.Map;

import cn.com.datateller.R;
import cn.com.datateller.utils.MyListViewAdapter.ViewHolder;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CircleListViewAdapter extends BaseAdapter{

	private LayoutInflater mInflater = null;
	private List<Map<String, Object>> data;
	
	static class ViewHolder {
		public TextView fromuser;
		public TextView content;
		public TextView createtime;
		public TextView replynum;
	}
	
	public CircleListViewAdapter(Context context,List<Map<String, Object>> data) {
		// TODO Auto-generated constructor stub
		super();
		this.mInflater = LayoutInflater.from(context);
		this.data=data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(cn.com.datateller.R.layout.topic_listview_item,
					null);
			holder.fromuser = (TextView) convertView.findViewById(R.id.fromUserTextView);
			holder.content = (TextView) convertView
					.findViewById(cn.com.datateller.R.id.topicContentTextView);
			holder.createtime=(TextView)convertView.findViewById(R.id.topicCreateTimeTextView);
			holder.replynum=(TextView)convertView.findViewById(R.id.topicReplyNumTextView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.fromuser.setText((String) data.get(position).get("fromuser"));
		holder.content.setText((String)data.get(position).get("content"));
		holder.createtime.setText((String)data.get(position).get("createtime"));
		holder.replynum.setText(String.valueOf(data.get(position).get("replynum")));
		return convertView;
	}
}
