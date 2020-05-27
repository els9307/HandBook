<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
               <!-- Pagination --><!-- 이전 , 다음 -->
<!--                      <ul class="actions pagination">
                        <li><a href="" class="disabled button big previous">Previous Page</a></li>
                        <li><a href="#" class="button big next">Next Page</a></li>
                     </ul> -->

               </div>

            <!-- Sidebar -->


<script>
   $(document).ready(function() {
      $.ajax({
         type : "post",
         url : "friendList",
         data : {"user_id" : "${session_id}"},
         success : function(data){
            $("#friendList").html(data);
         }
      })
   })
   

</script>            
<section id="sidebar">

   <!-- Intro -->
   <section id="intro">
   <%--    <a href="#" class="logo"><img src="${pageContext.request.contextPath}${info.user_img}" alt="" /></a> --%>
   <img class="logoBox" src="${pageContext.request.contextPath}${info.user_img}">
      <header>
         <h2>${session_id }</h2>
      </header>
   </section>

   <!-- Mini Posts -->
   

   <div id="friendList"></div>
   
   
   <!-- Footer -->
   <section id="footer">
      <p class="copyright">
         &copy; Untitled. Design: <a href="http://html5up.net">HTML5 UP</a>.
         Images: <a href="http://unsplash.com">Unsplash</a>.
      </p>
   </section>

</section>

</div>

      <!-- Scripts --><!-- 
         <script src="assets/js/jquery.min.js"></script> -->
         <script src="assets/js/skel.min.js"></script>
         <script src="assets/js/util.js"></script>
         <!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
         <script src="assets/js/main.js"></script>

   </body>
</html>