package cn.com.datateller.model;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = -7426302926562472180L;
	private Integer userid;
	private String userName;
	private String password;
	private Integer childId;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public Integer getUserId() {
		return userid;
	}

	public void setUserId(Integer userId) {
		this.userid = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userid + ", userName=" + userName
				+ ", password=" + password + ", childId=" + childId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
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
		User other = (User) obj;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}
}
