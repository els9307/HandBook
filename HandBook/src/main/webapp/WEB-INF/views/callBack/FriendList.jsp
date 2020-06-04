<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Mini Posts -->
<script>
	function GetFName(userid) {
		$("#fuser_id").val(userid);
		$("#frmUserpage").submit();
	}
</script>
<section>
	<form action="S_userPage" method="post" id="frmUserpage">
		<input type="hidden" id="fuser_id" name="fuser_id">
		<input type="hidden" name="f_id" value = ${session_id }>
	</form>
	<div class="mini-posts">



		<!-- Mini Post -->
		<article class="mini-post">
			<c:forEach items="${arr}" var="arr" varStatus="status">
				<header>
					 <c:choose>			
						<c:when test="${arr.user_id eq session_id}">
							<h3>
								<a href="javascript:GetFName('${arr.s_friendlist.f_id }')">${arr.s_friendlist.f_id }</a>
							</h3>
						</c:when>
						<c:when test="${arr.s_friendlist.f_id eq session_id}">
							<h3>
								<a href="javascript:GetFName('${arr.s_friendlist.fuser_id }')">${arr.s_friendlist.fuser_id }</a>
							</h3>
						</c:when>

					</c:choose> 
					
					</header>
			</c:forEach>
		
		</article>
	</div>
</section>