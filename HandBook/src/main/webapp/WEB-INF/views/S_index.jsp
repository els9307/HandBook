<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<script>
$(document).ready(function(){
	
	$.ajax({
		type : "post",
		url : "ListResult",
		success : function(data){
			$("#listResult").html(data);
		}
	}) 
	 $("#B_Inser_Btn").click(function(){
  	 	$("#frm_B_Insert").submit();
	 })
	 
	$("#gdsImg").change(function(){
		var form =  $("#frm_B_Insert")[0];
		var data = new FormData(form);
		var text = "";
		var textarea =  $("#b_content").val();
		var hidden = $("#realName").val();
		$.ajax({
            type: "POST",
            url: "RealName",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (realName) {
            	alert(realName);
      			var br = textarea + "<br><img src = ${pageContext.request.contextPath}"+realName + "/>" ;
      			$("#b_content").val(br);
            	$("#realName").val(realName);
            	$("#B_Inser_Btn").click(function(){
                	$("#frm_B_Insert").submit();
    			});
            }
		})
		//파일 첨부시 이미지 출력
 		if(this.files && this.files[0]) {
  			var reader = new FileReader;
  			reader.onload = function(data) {
   			$(".select_img img").attr("src", data.target.result).width(300);        
  			}
  			reader.readAsDataURL(this.files[0]);
 		}
 		
	});
});
</script>
<article class="post">
	<header>
		<div class="meta">
			<time class="published" datetime="2015-11-01">November 1, 2015</time>
			<a href="#" class="author"><span class="name">Jane Doe</span><img src="images/avatar.jpg" alt="" /></a>
		</div>
		<div class="title">
			<h2><a href="#"></a></h2>
			<p><a href="#ex1" rel="modal:open" id="model-Open">${session_id }님 무슨 생각을 하시고 계신가요 ?</a></p>
			<pre>${board.b_content }</pre>
		</div>

	</header>
<div id="listResult"></div>

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

</article>
