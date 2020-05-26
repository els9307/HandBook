package com.handbook.vo;

public class S_ImgPathName {

	private String subName;
	private String realName;
	
	public String getSubName() {
		return subName == null ? "" : subName.trim();
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public String getRealName() {
		return realName == null ? "" : realName.trim();
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
}
