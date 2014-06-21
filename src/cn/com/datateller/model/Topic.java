package cn.com.datateller.model;

import java.io.Serializable;

public class Topic implements Serializable {

	private static final long serialVersionUID = -9049110328620872795L;
	private int topicid;
	private String create_time;
	private String headurl;
	private String from_user;
	private String update_time;
	private String content;
	private int comments_num;

	public int getTopicid() {
		return topicid;
	}

	public void setTopicid(int topicid) {
		this.topicid = topicid;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

	public String getFrom_user() {
		return from_user;
	}

	public void setFrom_user(String from_user) {
		this.from_user = from_user;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getComments_num() {
		return comments_num;
	}

	public void setComments_num(int comments_num) {
		this.comments_num = comments_num;
	}

	@Override
	public String toString() {
		return "Topic [topicid=" + topicid + ", create_time=" + create_time
				+ ", headurl=" + headurl + ", from_user=" + from_user
				+ ", update_time=" + update_time + ", content=" + content
				+ ", comments_num=" + comments_num + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + topicid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		if (topicid != other.topicid)
			return false;
		return true;
	}

}
