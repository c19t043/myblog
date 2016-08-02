<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>test</title>
  </head>
  
  <body>
   	<form action="${pageContext.request.contextPath}/test/add.action" method="post">
   		姓名： <input type="text" name="name"><br>
   		<input type="submit" value="submit">
   	</form>
  </body>
</html>
