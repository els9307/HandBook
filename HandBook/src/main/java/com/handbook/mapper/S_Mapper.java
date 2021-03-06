package com.handbook.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.handbook.vo.S_BOARD;
import com.handbook.vo.S_FRIENDLIST;
import com.handbook.vo.S_USERINFO;

@Repository("s_mapper")
public interface S_Mapper {

	
	public int idChk(S_USERINFO userinfo);
	
	public void userJoin(S_USERINFO userinfo);
	
	public S_USERINFO userlogin(S_USERINFO userinfo);
	
	public void userUpdate(S_USERINFO userinfo);
	public void testInsert(String test);
	public void BoardInsert(S_BOARD board);
	public void BoardUpdate(S_BOARD board);
	public S_BOARD BoardDetail(S_BOARD board);
	public List<S_BOARD> ListResult(S_BOARD board);
	public void detailViewDelete(String b_num);

	public List<S_USERINFO> frinedList(S_USERINFO userinfo);
	
	public S_USERINFO getUserPage(S_FRIENDLIST fList);
	
	public void ApplyFriend(S_FRIENDLIST friendList);
	public void EndFriend(S_FRIENDLIST friendList);
	
	public List <S_USERINFO>  UserSearch(String userName);
	public S_FRIENDLIST GetState(S_FRIENDLIST fList);
	
	public int GetApplyCount(String fuser_id) ;
	public List<S_FRIENDLIST> ApplyView(S_FRIENDLIST friendList);
	public void AcceptFriend(S_FRIENDLIST friendList) ;
}
