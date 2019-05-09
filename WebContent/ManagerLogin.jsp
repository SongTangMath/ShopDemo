<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="http://localhost:8080/ShopDemo/ManagerLogin">
<p>总经理登陆页面</p>
		<div style="text-align:center" id=usernamediv>
		用户名:<input type="text" value="admin" name="username" id="username" readonly="readonly"/>
	</div>
	<div id="testIfUsed" style="text-align:center">
	
	</div>
	<div style="text-align:center">
		密码 :<input type="password" name="password"     id="password"/>
	</div>
	
	<div style="text-align:center">
		<input type="submit" value="Login"/>
	</div>
	</form>
</body>
</html>