<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<p>员工登陆页面</p>
<form method="post" action="http://localhost:8080/ShopDemo/EmployeeLogin">
		<div style="text-align:center" id=usernamediv>
		用户名:<input type="text" placeholder="请输入您的员工id" name="employeeId" id="employeeId" />
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