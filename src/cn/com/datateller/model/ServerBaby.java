package cn.com.datateller.model;

import java.io.Serializable;

public class ServerBaby implements Serializable {

	private static final long serialVersionUID = -3946429552552401948L;
	private String babyname;
	private String birthday;
	private int userid;
	private String schooladdr;
	private String sex;
	private String username;
	private String homeaddr;
	private String city;
	private float weight;
	private float height;

	public String getBabyname() {
		return babyname;
	}

	public void setBabyname(String babyname) {
		this.babyname = babyname;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getSchooladdr() {
		return schooladdr;
	}

	public void setSchooladdr(String schooladdr) {
		this.schooladdr = schooladdr;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHomeaddr() {
		return homeaddr;
	}

	public void setHomeaddr(String homeaddr) {
		this.homeaddr = homeaddr;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + userid;
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
		ServerBaby other = (ServerBaby) obj;
		if (userid != other.userid)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServerBaby [babyname=" + babyname + ", birthday=" + birthday
				+ ", userid=" + userid + ", schooladdr=" + schooladdr
				+ ", sex=" + sex + ", username=" + username + ", homeaddr="
				+ homeaddr + ", city=" + city + ", weight=" + weight
				+ ", height=" + height + "]";
	}

}
