<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
	function UserPage(userId){
		$("#fuser_id").val(userId);
		$("#frmUserId").submit();
	}
</script>
<c:forEach items="${userInfo}" var="userInfo" varStatus="status">
	<header>
		<h3> 
			<a href="javascript:UserPage('${userInfo.user_id }')">${userInfo.user_name }</a> 
		</h3>
		<form action="S_userPage" method = "post" id = "frmUserId">
			<input type = "hidden" id="fuser_id" name ="fuser_id">
			<input type = "hidden" id="f_id" name ="f_id" value = "${session_id }">
		</form>
	</header>
</c:forEach>
			