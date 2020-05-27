package com.handbook.vo;

public class S_ImgPathName {

	private String subName;
	private String realName;
	private String subAddress;
	private String realAddress;
	private String imgPath;
	
	
	public String getImgPath() {
		return imgPath == null ? "" : imgPath.trim();
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getSubName() {
		return subName == null ? "" : subName.trim();
	}
	public String getRealName() {
		return realName == null ? "" : realName.trim();
	}
	public String getSubAddress() {
		return subAddress == null ? "" : subAddress.trim();
	}
	public String getRealAddress() {
		return realAddress == null ? "" : realAddress.trim();
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public void setSubAddress(String subAddress) {
		this.subAddress = subAddress;
	}
	public void setRealAddress(String realAddress) {
		this.realAddress = realAddress;
	}
	
	
	
}
