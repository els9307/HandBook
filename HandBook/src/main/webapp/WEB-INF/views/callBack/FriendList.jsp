<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Mini Posts -->
<script>
		function test(a){
			$("#fName").val(a);
			$("#frmtest123").submit();
		}

</script>
<section>
	<div class="mini-posts">
<form action="S_userPage" method="post" id="frmtest123">
	<input type="hidden" id="fName" name="fName" > 
</form>
		<!-- Mini Post -->
		<article class="mini-post">
			<c:forEach items="${arr}" var="arr" varStatus="status">
				<header>
					<h3> 
						<a href="javascript:test('${arr.s_friendlist.f_name }')">${arr.s_friendlist.f_name }</a>
					</h3>
					<a href="#" class="author"><img src="${pageContext.request.contextPath}${arr.user_thumbimg}" alt="" /></a>
				</header>
			</c:forEach>
		</article>
	</div>
</section>