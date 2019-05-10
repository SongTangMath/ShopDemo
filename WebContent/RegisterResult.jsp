<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <%String toShow= ("true".equals(request.getAttribute("registerResult"))?"成功":"失败"); %>
<div style="text-align:center">注册结果:<%=toShow %></div>
<div style="text-align:center"><a href="http://localhost:8080/ShopDemo/index.html">返回首页</a></div>
</body>
</html>