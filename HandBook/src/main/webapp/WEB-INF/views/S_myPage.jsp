<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<script>
$(document).ready(function(){

/* 	$("#UserUpdate_Btn").click(function(){
		var passWord = $("#user_pwd").val();
		if(passWord != "" && passWord != null){
			$("#check").val("Y");
		}else{
			$("#check").val("N");
		} */
		
//	})
	$("#gdsImg").change(function(){
		alert("123123");
		var form = $("#frmUserUpdate")[0];
		var data = new FormData(form);
		$.ajax({
			type : "post",
			url : "RealName",
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			timeout : 600000,
			success : function(realName){
				alert(realName);
				$("#UserUpdate_Btn").click(function(){
					$("#realName").val(realName);
					$("#frmUserUpdate").submit();
					alert("다시 로그인 해 주세요.");
				})
			}
		})
 		if(this.files && this.files[0]) {
  			var reader = new FileReader;
  			reader.onload = function(data) {
   			$(".select_img img").attr("src", data.target.result).width(300);        
  			}
  			reader.readAsDataURL(this.files[0]);
 		}
	});
})
</script>
<!-- Form -->
	<h3>회원정보 수정</h3>
	<form method="post" action="user_Update" id="frmUserUpdate" enctype="multipart/form-data">
	<input type="hidden" id="check" name="check">
	<input type="hidden" id="realName" name="realName">
		<div class="row uniform">
			<div class="6u 12u$(xsmall)">
				<h3>아이디</h3>
				<input type="text" name="user_id" id="user_id" value="${info.user_id }" placeholder="Name" readonly="readonly"/>
			</div>
			<div class="mini-posts">
				<label for="gdsImg">이미지</label>
				 <div class="select_img"><img src="${pageContext.request.contextPath}${info.user_img}" /></div>
				<input type="file" id="gdsImg" name="file" />
			<%-- 	<%=request.getRealPath("/") %> --%>
			</div>
			<div class="6u$ 12u$(xsmall)">
				<h3>이메일</h3>
				<input type="email" name="user_email" id="user_email" value="${info.user_email }" placeholder="Email" />
			</div>
			<div class="6u 12u$(xsmall)">
				<h3>이름</h3>
				<input type="text" name="user_name" id="user_name" value="${info.user_name }" placeholder="Name" />
			</div>
			<div class="6u$ 12u$(xsmall)">
				<h3>닉네임</h3>
				<input type="text" name="user_nickname" id="user_nickname" value="${info.user_nickname }" placeholder="Email" />
			</div>
			
			<div class="6u 12u$(xsmall)">
				<h3>비밀번호</h3>
				<input type="password" name="user_pwd" id="user_pwd" value="" placeholder="password" />
				<input type="password" name="user_pwd_check" id="user_pwd_check" value="" placeholder="password_Check" />
			</div>
			<!-- Break -->
			<div class="12u$">
				<ul class="actions">
					<li><input type="button" value="정보수정하기" id="UserUpdate_Btn" class="special" /></li>
					<li><input type="reset" value="회원탈퇴" /></li>
				</ul>
			</div>
		</div>
	</form>