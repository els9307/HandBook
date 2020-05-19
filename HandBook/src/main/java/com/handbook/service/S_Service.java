package com.handbook.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.handbook.mapper.S_Mapper;
import com.handbook.vo.S_BOARD;
import com.handbook.vo.S_USERINFO;

@Service("s_service")
public class S_Service {

	@Resource(name = "s_mapper")
	private S_Mapper s_mapper;
	
	
	public int idChk(S_USERINFO userinfo) {
		int result = s_mapper.idChk(userinfo);
		return result;
	}
	public void userJoin(S_USERINFO userinfo) {
		s_mapper.userJoin(userinfo);
	}
	public S_USERINFO userlogin(S_USERINFO userinfo) {
		return s_mapper.userlogin(userinfo);
	}
	public void userUpdate(S_USERINFO userinfo) {
		s_mapper.userUpdate(userinfo);
	}
	public void testInsert(String test) {
		s_mapper.testInsert(test);
	}
	public void BoardInsert(S_BOARD board){
		 s_mapper.BoardInsert(board);
	}
	public List<S_BOARD> ListResult(S_BOARD board) {
		
		return s_mapper.ListResult(board);
	}
	public void detailViewDelete(String b_num) {
		s_mapper.detailViewDelete(b_num);
	}
	public List<S_USERINFO> friendList(S_USERINFO userinfo) {
		List<S_USERINFO> arr = s_mapper.frinedList(userinfo);
		return arr;	 
	}
}
