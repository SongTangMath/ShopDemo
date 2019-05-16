<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*"%>
<%@ page import="com.zkdx.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	
	String username = (String)request.getSession().getAttribute("adminName");
	
	if (username == null || !username.equals("admin")) {
		response.sendRedirect(request.getContextPath() + "/index.html");  
	return;}
	EmployeeDAO dao = new EmployeeDAO();
	List<Employee>list = dao.listAllEmployees(); 
	String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + "/";
	%>
	<div style="text-align:center"> <p>这里是总经理管理页面</p></div>
	<table border="1" align="center">
	 <tr>
    <th>员工id</th>
    <th>姓名</th>
     <th>部门</th>
    <th>职务</th>
    <th>薪水</th>
    <th>操作</th>
  </tr>
  <% 
  for (int i = 0;i < list.size();i++){ 
	  Employee temp = list.get(i);
  
  %>
  <tr>
    <td><%=temp.getId()%></td>
    <td><%=temp.getName()%></td>
    <td><%=temp.getDepartmentName()%></td>
    <td><%=temp.getJob()%></td>
    <td><%=temp.getSalary()%></td>
    <%	String modifyPath =basePath+"EditEmployee.jsp?&EmployeeId="+temp.getId();
    	String deletePath =basePath+"EditEmployeeHandler?Method=delete&EmployeeId="+temp.getId();
    %>
    <td> <a href="<%=modifyPath%>">编辑    </a>
    <a href="<%=deletePath%>">删除</a> </td>
  </tr>
  <% } %>
	</table>
	
	<br><br><br>
	<div style="text-align:center"> <p>添加新员工</p></div>

	<form method="post" action="http://localhost:8080/ShopDemo/EditEmployeeHandler">
	<div style="text-align:center" id=usernamediv>
		员工id:<input type="text" name="EmployeeId" id="EmployeeId" />
	</div>
	<div id="testIfUsed" style="text-align:center">
	
	</div>
	<div style="text-align:center">
		员工姓名 :<input type="text" name="employeeName"     id="employeeName"/>
	</div>
	
	<div style="text-align:center">
		员工密码 :<input type="password" name="employeePassword"     id="employeePassword"/>
	</div>
	
	<div style="text-align:center">
		员工部门 :<input type="text" name="employeedepartmentName"     id="employeedepartmentName"/>
	</div>
	
	<div style="text-align:center">
		员工职务 :<input type="text" name="employeeJob"     id="employeeJob"/>
	</div>
	
	<div style="text-align:center">
		员工薪水 :<input type="text" name="employeeSalary"     id="employeeSalary"
		oninput = "value=value.replace(/[^\d]/g,'')"/>
	</div>
	<input type="hidden" name="Method" value="create" />
	<div style="text-align:center">
		<input type="submit" value="Register"/>
	</div>
	</form>
	<br><br><br>
	<div style="text-align:center">
	<% Integer status=(Integer)request.getAttribute("status");
	String toShow = "";
	if (status != null && status.equals(1)) toShow="操作成功";
	else if(status!=null&&status.equals(0)) toShow="操作失败"; %>
		上一次操作状态:<%=toShow %></div>
	<div style="text-align:center">
	<a href="<%=basePath+"Quit" %>">退出登录并返回首页</a>
	</div>
	<div style="text-align:center">根据时间段查询销售情况
	<form action="<%=basePath+"QuerySellingStatus" %>">
	查询起始时间(yyyy-mm-dd)<input type="date" name="begindate"/>
	<br>
	查询结束时间(yyyy-mm-dd)<input type="date" name="enddate"/>
	<input type="submit" value="获取结果excel表"/>
	</form>
	
	</div>
</body>
</html>