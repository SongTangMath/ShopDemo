<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ page import="java.util.*"%>
<%@ page import="com.zkdx.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>员工信息编辑页面</title>
</head>
<body>
<%
String idString=(String)request.getParameter("EmployeeId");
int id=-1;
try{
	id=Integer.parseInt(idString);
}
catch (Exception e){
	e.printStackTrace();
	return ;
}
EmployeeDAO dao=new EmployeeDAO();
Employee employee=dao.getEmployeeById(id);
	String username=(String)request.getSession().getAttribute("adminName");
	
	if(username==null||!username.equals("admin")||employee==null){
		response.sendRedirect(request.getContextPath()+"/index.html");  
	return;}
	
	System.out.println(employee.getId());
	System.out.println(employee);
	%>
	<div >
	<form method="post" action="http://localhost:8080/ShopDemo/EditEmployeeHandler">
	<div style="text-align:center">注意,id和姓名不可修改</div>
	<div style="text-align:center">
		员工id:<input type="text" value="<%=employee.getId()%>" name=EmployeeId 
		id="employeeId" readonly="readonly"/>
	</div>
	<div style="text-align:center">
		员工姓名 :<input type="text" name="employeeName" 
		value="<%=employee.getName()%>" id="employeeName" readonly="readonly"/>
	</div>
	
	<div style="text-align:center">
		员工密码 :<input type="password" name="employeePassword" 
		value="<%=employee.getPassword()%>" id="employeePassword"/>
	</div>
	
	<div style="text-align:center">
		员工部门 :<input type="text" name="employeedepartmentName"
		value="<%=employee.getDepartmentName()%>"  id="employeedepartmentName" />
	</div>
	
	<div style="text-align:center">
		员工职位 :<input type="text" name="employeeJob" 
		value="<%=employee.getJob()%>" id="employeeJob"/>
	</div>
	
	<div style="text-align:center">
		员工薪水 :<input type="text" name="employeeSalary" id="employeeSalary" 
		value="<%=employee.getSalary()%>"
		oninput = "value=value.replace(/[^\d]/g,'')"/>
	</div>
	<input type="hidden" name="Method" value="modify" />
	<div style="text-align:center">
		<input type="submit" value="submit"/>
	</div>
  	
	</form>
</div>
</body>
</html>