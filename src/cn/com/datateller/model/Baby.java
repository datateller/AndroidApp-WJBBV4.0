package cn.com.datateller.model;

import java.io.Serializable;
import java.util.Date;

import cn.com.datateller.utils.SexEnum;

public class Baby implements Serializable{

	private static final long serialVersionUID = -1174610883438428159L;
	private Integer childId;
	private String birthday;
	private SexEnum sex;
	private float weight;
	private float height;
	private String childname;
	private String familyAddress;
	private String schoolAddress;
	private int userid;

	public Baby() {
		// TODO Auto-generated constructor stub
	}

	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	public SexEnum getSex() {
		return sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setSex(SexEnum sex) {
		this.sex = sex;
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

	public String getChildname() {
		return childname;
	}

	public void setChildname(String childname) {
		this.childname = childname;
	}



	@Override
	public String toString() {
		return "Baby [childId=" + childId + ", birthday=" + birthday + ", sex="
				+ sex + ", weight=" + weight + ", height=" + height
				+ ", childname=" + childname + ", familyAddress="
				+ familyAddress + ", schoolAddress=" + schoolAddress
				+ ", userid=" + userid + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childId == null) ? 0 : childId.hashCode());
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
		Baby other = (Baby) obj;
		if (childId == null) {
			if (other.childId != null)
				return false;
		} else if (!childId.equals(other.childId))
			return false;
		return true;
	}

	public String getFamilyAddress() {
		return familyAddress;
	}

	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}

	public String getSchoolAddress() {
		return schoolAddress;
	}

	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

}
