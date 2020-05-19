<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Mini Posts -->
<section>
	<div class="mini-posts">

		<!-- Mini Post -->
		<article class="mini-post">
			<c:forEach items="${arr}" var="arr" varStatus="status">
				<header>
					<h3> 
						<a href="#">${arr.s_friendlist.f_name }</a>
						<a href="S_userPage">${arr.s_friendlist.f_name }</a>
					</h3>
					<a href="#" class="author"><img src="${pageContext.request.contextPath}${arr.user_thumbimg}" alt="" /></a>
				</header>
			</c:forEach>
		</article>
	</div>
</section>