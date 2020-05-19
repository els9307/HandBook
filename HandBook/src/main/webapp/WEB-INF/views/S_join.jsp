<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>HandBook-Join</title>

  <!-- Custom fonts for this template-->
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="assets/css/sb-admin-2.min.css" rel="stylesheet">

</head>
<style>
	.bg-gradient-primary{
		background-color: #e4a1a1;
	}
</style>
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
<script>



	
	$(document).ready(function(){
		
		var emailCheck = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		//이메일 유효성
		$("#idChk").click(function(){
				$.ajax({
					type : "post",
					url : "idChk",
					dataType : "json",
					data : {"user_id" : $("#user_id").val()},
					success : function(data){
						if(data == 1){
							alert("중복된 아이디 입니다.");
						}else if(data == 0){
							alert("가입가능한 아이디 입니다.");
						}
					}
				})
		})

		
		$("#Join_Btn").click(function(){
			if($("#user_id").val() == ""){
				alert("아이디를 입력해주세요");
				return false;
			}
			if($("#user_pwd").val() == ""){
				alert("비밀번호를 입력해주세요");
				return false;
			}
   			
   			$("#frmJoin").submit();
		})

	})
	
</script>
<body class="bg-gradient-primary">

  <div class="container">

    <div class="card o-hidden border-0 shadow-lg my-5">
      <div class="card-body p-0">
        <!-- Nested Row within Card Body -->
        <div class="row">
          <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
          <div class="col-lg-7">
            <div class="p-5">
              <div class="text-center">
                <h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
              </div>
              
              <form action="User_Join" method="POST" id="frmJoin" > 
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <input type="text" class="form-control form-control-user" id="user_id" name="user_id" placeholder="ID" >
                  </div>
                  <div class="col-sm-6">
				  	<a href="#" class="btn btn-google btn-user btn-block" id="idChk">
				  		중복체크
                    </a>
                  </div>
                </div>
                <div class="form-group">
                  <input type="text" class="form-control form-control-user" id="user_name" name="user_name" placeholder="NAME">
                </div>
                <div class="form-group">
                  <input type="email" class="form-control form-control-user" id="user_email" name="user_email" placeholder="EMAIL" >
                </div>
                <div class="form-group">
                  <input type="text" class="form-control form-control-user" id="user_nickname" name="user_nickname" placeholder="NICKNAME" >
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <input type="password" class="form-control form-control-user" id="user_pwd" name="user_pwd" placeholder="Password">
                  </div>
                  <div class="col-sm-6">
                    <input type="password" class="form-control form-control-user" id="user_pwd_check" name="user_pwd_check" placeholder="Repeat Password">
                  </div>
                </div>
              </form>
              
                <a href="#" id="Join_Btn" class="btn btn-primary btn-user btn-block">
                  Register Account
                </a>
                <hr>
                <a href="index.html" class="btn btn-google btn-user btn-block">
                  <i class="fab fa-google fa-fw"></i> Register with Google
                </a>
                <a href="index.html" class="btn btn-facebook btn-user btn-block">
                  <i class="fab fa-facebook-f fa-fw"></i> Register with Facebook
                </a>
              <hr>
              <div class="text-center">
                <a class="small" href="forgot-password.html">Forgot Password?</a>
              </div>
              <div class="text-center">
                <a class="small" href="login.html">Already have an account? Login!</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>



</body>

</html>
