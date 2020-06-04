<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<script>
$(document).ready(function(){
	$("#gdsImg1").change(function(){
		alert("xzczxcz");
		var form =  $("#frmUpdate")[0];
		var data = new FormData(form);
		var textarea =  $("#b_content_U").val();
		$.ajax({
            type: "POST",
            url: "subName",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (img) {
      			var br = textarea + "<br><img src = ${pageContext.request.contextPath}"+img.realAddress+img.realName + "/>" ;
      			$("#b_content_U").val(br);
      			$("#subAddress1").val(img.subAddress);
      			$("#imgPath1").val(img.imgPath);
      			$("#realAddress1").val(img.realAddress);
      			alert("값="+$("#subAddress1").val());
            	$("#b_Update_Btn").click(function(){
            		alert("123");
                	$("#frmUpdate").submit();
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
/* 	$("#Update_esc").click(function(){
		alert("1234");
		$("#b_title_UP").val("");
		$("#b_content_UP").val("");
	});
 */

})

function Fn_Detail(num,session_id){
	$.ajax({
		type : "post",
		url : "BoardDetail",
		data : {"b_num" : num ,
				"session_id" : session_id},
		dataType : "json",
		success : function(data){
			$("#b_title_UP").val(data.b_title);
			$("#b_content_UP").val(data.b_content);
		}
	})  
}
	
function Fn_DetailDelete(b_num){
	$("#Delete_b_num").val(b_num);
	$("#frmDetailDelete").submit();
}

</script>

<form action="BoardDetail" method="post" id="frim">
	<input type="hidden" id="num" name="b_num">
	<input type="hidden" id="session_id" name="session_id">
</form>
<form action="detailViewDelete" method="post" id="frmDetailDelete">
	<input type="hidden" id="Delete_b_num" name="b_num">
</form>
<c:forEach items="${list }" var="list">
<article class="post">
	<header>
		<div class="title">
			<h2><a href="#">${list.b_title }</a></h2>
			<p>${list.b_num } 테스트</p>
		</div>
		<div class="meta">
			<time class="published" datetime="2015-11-01">November 1, 2015</time>
			<a href="#" class="author">asdasdasd</a>
			<a href="S_userPage" class="author">
				<span class="name">${list.b_user_id }</span>
				<img src="${pageContext.request.contextPath}${list.b_img}" alt="" />
			</a>
		</div>
	</header>
<!-- 	<a href="#" class="image featured"><img src="images/pic01.jpg" alt="" /></a> -->
	<%-- <p>${list.b_content }</p> --%>
	<pre>${list.b_content }</pre>
	<footer>
		<ul class="actions">
			<c:if test="${session_id == list.b_user_id }">
				<li><a href="#board_UP" id="update-Btn" class="button big"  rel="modal:open" 
					onclick="javascript:Fn_Detail('${list.b_num }','${session_id }')">UPDATE</a></li>
				<li><a href="#" class="button big" onclick="javascript:Fn_DetailDelete('${list.b_num}')">DELETE</a></li>
			</c:if>
		</ul>
		<ul class="stats">
			<li><a href="#">General</a></li>
			<li><a href="#" class="icon fa-heart">28</a></li>
			<li><a href="#" class="icon fa-comment">128</a></li>
		</ul>
	</footer>


</article>

</c:forEach>
<div id="board_UP" class="modal">
	<form action="Board_Update" method="post" id="frmUpdate" enctype="multipart/form-data">
		<input type="hidden" id="UploadFlag" name="UploadFlag" value="1">
		<input type="hidden" id="b_user_id" name="b_user_id" value="${session_id }"> 
			<input type="hidden" id="realAddress1" name="realAddress">
			<input type="hidden" id="subAddress1" name="subAddress">
			<input type="hidden" id="imgPath1" name="imgPath">
		<div>제목 : <input type="text" style="width: 60%; display: inline;" id="b_title_UP" name="b_title" value=""></div>
		
		<p style="display: contents;">내용 :$ </p>
		
		<textarea id="b_content_UP" name="b_content" rows="" cols="" ></textarea>
		
		<div class="mini-posts">
			<div class="select_img"><img src="" /></div>
		</div>
		
	 	<input type="file" id="gdsImg1" name="file" />
	 </form>
	 	<div style="text-align: right;">
	 		<input type="button" value="확인" id="b_Update_Btn"/>
	 		<a href="#" id="esc" rel="modal:close" style="border: 0px;"><input type="button" value="취소"></a>
	 	</div>

</div> 