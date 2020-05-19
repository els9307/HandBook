package com.handbook.vo;


public class S_FRIENDLIST {
	private String m_name;
	private String f_name;
	private String n_state;
	private String reg_date;
	
	public String getM_name() {
		return m_name  == null ? "" : m_name.trim();
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public String getF_name() {
		return f_name == null ? "" : f_name.trim();
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	public String getN_state() {
		return n_state == null ? "" : n_state.trim();
	}
	public void setN_state(String n_state) {
		this.n_state = n_state;
	}
	public String getReg_date() {
		return reg_date == null ? "" : reg_date.trim();
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	
	
}
