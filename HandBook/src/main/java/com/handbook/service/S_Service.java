package com.handbook.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.handbook.mapper.S_Mapper;
import com.handbook.vo.S_BOARD;
import com.handbook.vo.S_FRIENDLIST;
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

	public void BoardInsert(S_BOARD board) {
		s_mapper.BoardInsert(board);
	}
	public void BoardUpdate(S_BOARD board) {
		s_mapper.BoardUpdate(board);
	}
	public S_BOARD BoardDetail(S_BOARD board) {
		return s_mapper.BoardDetail(board);
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

	public S_USERINFO  getUserPage(String user_id) {
		S_USERINFO userInfo = s_mapper.getUserPage(user_id);
		return userInfo;
	}
	
	public void ApplyFriend(S_FRIENDLIST friendList) {
		s_mapper.ApplyFriend(friendList);
	}
	
	public List <S_USERINFO>  UserSearch(String userName) {
		List <S_USERINFO> userInfo = s_mapper.UserSearch(userName);
		return userInfo;
	}
	
	public S_FRIENDLIST GetState(S_FRIENDLIST fList){
		S_FRIENDLIST friendList = s_mapper.GetState(fList);
		return friendList;
	}
}
