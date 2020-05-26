package com.handbook.vo;

/*

*/
public class S_FRIENDLIST {
	private String fuser_id;
	private String f_id;
	private String m_state;
	private String f_state;
	private String reg_date;
	
	public String getFuser_id() {
		return fuser_id == null ? "" : fuser_id.trim();
	}
	public void setFuser_id(String fuser_id) {
		this.fuser_id = fuser_id;
	}
	
	public String getF_id() {
		return f_id == null ? "" : f_id.trim();
	}
	public void setF_id(String f_id) {
		this.f_id = f_id;
	}
	
	public String getM_state() {
		return m_state == null ? "" : m_state.trim();
	}
	public void setM_state(String m_state) {
		this.m_state = m_state;
	}
	
	public String getF_state() {
		return f_state == null ? "" : f_state.trim();
	}
	public void setF_state(String f_state) {
		this.f_state = f_state;
	}
	
	public String getReg_date() {
		return reg_date == null ? "" : reg_date.trim();
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	
	
	
	
	
	
}
