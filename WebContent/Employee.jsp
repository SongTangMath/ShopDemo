<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ page import="java.util.*"%>
<%@ page import="com.zkdx.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>员工页面</title>
</head>

<%
	
	Employee employee=(Employee)request.getSession().getAttribute("employee");
	
	if(employee==null){
		response.sendRedirect(request.getContextPath()+"/index.html");  
	return;
		}
	String basePath = request.getScheme() + "://"
	          + request.getServerName() + ":" + request.getServerPort()
	          + request.getContextPath() + "/";
	%>
<body>
	<div style="text-align:center">欢迎你,<%=employee %></div>
	
<a href="<%=basePath+"Quit" %>">退出登录并返回首页</a>
	
</body>
</html>