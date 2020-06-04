<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Mini Posts -->
<script>
		function GetFName(userid){
			$("#f_id").val(userid);
			$("#frmUserpage").submit();
		}

</script>
<section>
		<form action="S_userPage" method="post" id="frmUserpage">
			<input type="hidden" id="f_id" name="f_id" > 
		</form>
	<div class="mini-posts">
	

		
		<!-- Mini Post -->
		<article class="mini-post">
			<c:forEach items="${arr}" var="arr" varStatus="status">
				<header>
					<h3> 
						<a href="javascript:GetFName('${arr.s_friendlist.f_id }')">${arr.s_friendlist.f_id }</a> 
					</h3>
					<a href="#" class="author"><img src="${pageContext.request.contextPath}${arr.user_thumbimg}" alt="" /></a>
				</header>
			</c:forEach>
		</article>
	</div>
</section>