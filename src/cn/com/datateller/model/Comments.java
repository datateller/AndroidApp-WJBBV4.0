package cn.com.datateller.model;

import java.io.Serializable;

public class Comments implements Serializable {

	private static final long serialVersionUID = 8971769223201696674L;
	private String from_user;
	private String content;
	private String create_time;

	public Comments() {
		// TODO Auto-generated constructor stub
	}

	public String getFrom_user() {
		return from_user;
	}

	public void setFrom_user(String from_user) {
		this.from_user = from_user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "Comments [from_user=" + from_user + ", content=" + content
				+ ", create_time=" + create_time + "]";
	}

}
