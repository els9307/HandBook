<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE HTML>
<!--
	Future Imperfect by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
	<head>
		<title>HandBook_SNS</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="assets/css/main.css" />
		<!-- 모달창 이벤트를 위해 추가 아래 3개  -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />
		<!-- jquery 사용을 위함  -->
		<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
	</head>
	<style>
		.logoBox {
    width: 150px;
    height: 150px; 
    border-radius: 70%;
    overflow: hidden;
}
	</style>
	<script>
		$(document).ready(function(){
			
			$("#click").click(function(){
				$.ajax({
					type : "post",
					url : "testResult",
					success : function(data){
						alert(data);
						$("#test").html(data);
					}
				})
			})

			$("#click").click(function(){
				$("#frmsss").submit();
			})
			
			$("#myPage-Btn").click(function(){
				$("#frmUserPage").submit();
			})
			$("#index").click(function(){
				
			})
		})
		function postIndex(){
			$("#frmIndex").submit();
		}
	</script>
	<body>

<!-- Wrapper -->
<form action="S_myPage" method="post" id="frmUserPage">
	<input type="hidden" name="user_id" value="${session_id}">
</form>
<form action="index" method="post" id="frmIndex">
	<input type="hidden" name="user_id" value="${session_id}">
</form>
<div id ="test"></div>
<form action="testResult" method="post" id="frmsss"></form>
			<div id="wrapper">

				<!-- Header -->
					<header id="header">
						<h1><a href="javascript:postIndex()" id="">Future Imperfect</a></h1>
						<nav class="main">
							<ul>
								<li class="search">
									<a class="fa-search" href="#search">Search</a>
									<form id="search" method="get" action="#">
										<input type="text" name="query" placeholder="Search" />
									</form>
								</li>
								<li class="menu">
									<a class="fa-bars" href="#menu">Menu</a>
								</li>
							</ul>
						</nav>
					</header>

				<!-- Menu -->
					<section id="menu">

						<!-- Search -->
							<section>
								<form class="search" method="get" action="#">
									<input type="text" name="query" placeholder="Search" />
								</form>
							</section>

						<!-- Links -->
							<section>
								<ul class="links">
									<li>
										<a href="#">
											<h3>아니 한글로 적을껀데</h3>
											<p>메뉴 뭐 할거 있는지 생각해달라고</p>
										</a>
									</li>
									<li>
										<a href="#">
											<h3>Dolor sit amet</h3>
											<p>Sed vitae justo condimentum</p>
										</a>
									</li>
									<li>
										<a href="#">
											<h3>Feugiat veroeros</h3>
											<p>Phasellus sed ultricies mi congue</p>
										</a>
									</li>
									<li>
										<a href="#">
											<h3>Etiam sed consequat</h3>
											<p>Porta lectus amet ultricies</p>
										</a>
									</li>
								</ul>
							</section>

							<!-- Actions -->
							<section>
								<ul class="actions vertical">
								${session_id}
									<c:if test="${session_id == null}">
										<li><a href="S_login" class="button big fit">LogIn</a></li>
										<li><a href="S_join" class="button big fit">Join</a></li>
									</c:if>
									<c:if test="${session_id != null}">
										<li><a href="#" id="myPage-Btn" class="button big fit">MY PAGE </a></li>
										<li><a href="logOut" class="button big fit">LOGOUT </a></li>
									</c:if>
								</ul>
							</section>
						</section>
			<!-- Main -->
					<div id="main">

                        <div id="index"></div>
                        
	