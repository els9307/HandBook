<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<script>
$(document).ready(function(){
	$("#gdsImg1").change(function(){
		alert("xzczxcz");
		var form =  $("#frmUpdate")[0];
		var data = new FormData(form);
		var textarea =  $("#b_content").val();
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
      			$("#b_content_U").val(br);
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
	$("#b_Update_Btn").click(function(){
		$("#frmUpdate").submit();
	})
})
function Fn_DetailDelete(b_num){
	$("#b_num").val(b_num);
	$("#frmDetailDelete").submit();
}
function Fn_Update(){
	$("#b_user_id").val("${session_id}");
	$("#frmUpdate").submit();
}


</script>

		
<form action="detailViewDelete" method="post" id="frmDetailDelete">
	<input type="hidden" id="b_num" name="b_num">
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
			<a href="#" class="author">
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
				<li><a href="#ex2" id="update-Btn" class="button big"  rel="modal:open" id="model-Open">UPDATE</a></li>
				<li><a href="#" class="button big" onclick="javascript:Fn_DetailDelete('${list.b_num}')">DELETE</a></li>
			</c:if>
		</ul>
		<ul class="stats">
			<li><a href="#">General</a></li>
			<li><a href="#" class="icon fa-heart">28</a></li>
			<li><a href="#" class="icon fa-comment">128</a></li>
		</ul>
	</footer>
	
<div id="ex2" class="modal">
	<form action="board_Update" method="post" id="frmUpdate" enctype="multipart/form-data">
		
	
		<div>제목 : <input type="text" style="width: 60%; display: inline;" id="b_title" name="b_title" value="${list.b_title }"></div>
		
		<p style="display: contents;">내용 : </p>
		
		<textarea id="b_content_U" name="b_content" rows="" cols="" > ${list.b_content }</textarea>
		
		<div class="mini-posts">
			<div class="select_img"><img src="" /></div>
		</div>
		
	 	<input type="file" id="gdsImg1" name="file" />
	 </form>
	 	<div style="text-align: right;"><input type="button" value="확인" id="b_Update_Btn"/></div>

</div> 
</article>
</c:forEach>
