package com.handbook.vo;

public class S_BOARD {

	private String b_num;
	private String b_user_id;
	private String b_title;
	private String b_content;
	private String b_img;
	private String b_thumbimg;
	private String reg_date;
	private String mod_date;
	public String getB_num() {
		return b_num == null ? "" : b_num.trim();
	}
	public String getB_user_id() {
		return b_user_id == null ? "" : b_user_id.trim();
	}
	public String getB_title() {
		return b_title == null ? "" : b_title.trim();
	}
	public String getB_content() {
		return b_content == null ? "" : b_content.trim();
	}
	public String getB_img() {
		return b_img == null ? "" : b_img.trim();
	}
	public String getB_thumbimg() {
		return b_thumbimg == null ? "" : b_thumbimg.trim();
	}
	public String getReg_date() {
		return reg_date == null ? "" : reg_date.trim();
	}
	public String getMod_date() {
		return mod_date == null ? "" : mod_date.trim();
	}
	public void setB_num(String b_num) {
		this.b_num = b_num;
	}
	public void setB_user_id(String b_user_id) {
		this.b_user_id = b_user_id;
	}
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}
	public void setB_content(String b_content) {
		this.b_content = b_content;
	}
	public void setB_img(String b_img) {
		this.b_img = b_img;
	}
	public void setB_thumbimg(String b_thumbimg) {
		this.b_thumbimg = b_thumbimg;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}
	
}
