<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Mini Posts -->
<script>
function GetFName(userid){
		$("#f_id").val(userid);
		$("#frmUserpage").submit();
}

function AcceptFriend(f_id,flag){
	$("#fuser_id").val(f_id);
	$("#flag").val(flag);
	$("#FriendAccept").submit();
	
}
</script>
<section>
		<form action="S_userPage" method="post" id="frmUserpage">
			<input type="hidden" id="f_id" name="f_id" > 
		</form>
		
		<form action="FriendAccept" method="post" id="FriendAccept">
			<input type="hidden" id="fuser_id" name="fuser_id" > 
			<input type="hidden" id="flag" name="flag" > 
			<input type="hidden" id="f_id" name="f_id" value = "${session_id }" >
		</form>
	<div class="mini-posts">
	
		<!-- Mini Post -->
		<article class="mini-post">
			<c:forEach items="${applyList}" var="applyList" varStatus="status">
				<header>
					<h3>
						<a href="javascript:GetFName('${applyList.fuser_id }')">${applyList.fuser_id }</a>
					</h3>
					<input type = "button" id = "applyBtn" value = "수락" onclick="javascript:AcceptFriend('${applyList.fuser_id }','1')">
					<input type = "button" id = "cancelBtn" value = "거절" onclick="javascript:AcceptFriend('${applyList.fuser_id }','2')">
				</header>
			</c:forEach>
		</article>
	</div>
</section>