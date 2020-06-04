<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<script>
 $(document).ready(function(){	
	 
 })
 
function AboutFriend(flag){
	 $("#flag").val(flag);

	 
	if (flag == 0) {
		 var applyConfirm = confirm("친구 신청하시겠습니까?");
		if (applyConfirm) {
			$("#applyFriend").submit();
		} else {
			return false;
		}
	} 
	if (flag == 1) {
		 var resetConfirm = confirm("친구신청을 취소하겠습니까?");
		if (resetConfirm) {
			$("#applyFriend").submit();
		} else {
			return false;
		}
	} 
	if (flag == 2) {
		 var endConfirm = confirm("친구를 끊겠습니까?");
		if (endConfirm) {
			$("#applyFriend").submit();
		} else {
			return false;
		}
	}
}
</script>

<input type ="hidden" id = "userName" value = "${session_id }"/>
<article class="post">
	<header>
		<div class="meta">
			<!-- <time class="published" datetime="2015-11-01">November 1, 2015</time> -->
			<!-- 사진 - 이름 출력  -->
			<a href="#" class="author"><span class="name">${session_id}</span><img src="images/avatar.jpg" alt="" /></a>
		</div>
		<div class="title">
			<h2><a href="#"></a></h2>
			<p><a href="#ex1" rel="modal:open" id="model-Open">${userInfo.user_id }님에게 글을 남기시겠습니까 ?</a></p>
		</div>

	</header>

	
	<!-- 이부분 ajax로 글리스트 출력 -->

<div id="ex1" class="modal">
	<form action="BoardInsert" method="post" id="frm_B_Insert" enctype="multipart/form-data">
	<input type="hidden" id="b_user_id" name="b_user_id" value="${session_id }">
	<input type="hidden" id="check" name="check" value="INSERT">
		<div>제목 : <input type="text" style="width: 60%; display: inline;" id="b_title" name="b_title"></div>
			<p style="display: contents;">내용 : </p>
			<textarea id="b_content" name="b_content" rows="" cols="" ></textarea>
			<div class="mini-posts">
				<div class="select_img"><img src="" /></div>
			</div>
	<input type="hidden" id="realName" name="realName">

	 <!-- </form>
	<form action="flieName" method="post" id="frmTest" enctype="multipart/form-data"> 
		 --><input type="file" id="gdsImg" name="file" />
	</form>
	 	 	<div style="text-align: right;"><input type="button" value="확인" id="B_Inser_Btn" /></div>
	
</div>

<form id = "applyFriend" action="ApplyFriend" method = "post">
	<input type = "hidden" id = "fuser_id" name = "fuser_id" value = "${session_id }"/>
	<input type = "hidden" id = "f_id" name = "f_id" value = "${userInfo.user_id }"/>
	<input type = "hidden" id = "flag" name = "flag"/>
</form>

<ul class="actions">
		<c:choose>
		<c:when test="${friendList.m_state eq null}"> 
			<li><a href= "javascript:AboutFriend(0)" class="button big" >친구 신청</a></li>
		</c:when>
		<c:when test="${friendList.m_state eq '1'}">
			<li><a href= "javascript:AboutFriend(1)" class="button big" >수락 대기중</a></li>
		</c:when>
		<c:when test="${friendList.m_state eq '2'}">
			<li><a href= "javascript:AboutFriend(2)" class="button big" >친구 끊기</a></li>
		</c:when>
		</c:choose>
		<li><a href="#" class="button big">친구</a></li>
		<li><a href="#" class="button big">정보</a></li>
</ul>
</article>
