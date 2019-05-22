<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.*"%>
<%@ page import="com.zkdx.*"%>
<%@ page import="java.text.DateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>分页显示所有订单,每页10条记录</title>
</head>
<body>
	<%
	    Employee employee = (Employee)request.getSession().getAttribute("employee");
	    System.out.println(employee);
	    if (employee == null || !"客服".equals(employee.getJob())) {
	        response.sendRedirect(request.getContextPath() + "/index.html");
	        return;
	    }
	    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	        + request.getContextPath() + "/";
	    OrderInfoDAO dao = new OrderInfoDAO();
        int totalOrderQuantity=dao.getTotalOrderQuantity();
        request.getSession().setAttribute("totalOrderQuantity", totalOrderQuantity);
	%>
	<div style="text-align: center">
		欢迎你,<%=employee%></div>
	<div style="text-align: center">
	<form method="POST"
		action="<%out.write(basePath + "ListOrderByPage");%>">		
			<%
			     totalOrderQuantity = (Integer)request.getSession().getAttribute("totalOrderQuantity");
			    int pages = -1;
			    if (totalOrderQuantity % 10 == 0){
			        pages = totalOrderQuantity / 10;
			    }
			       
			    else{
			        pages = totalOrderQuantity / 10 + 1;}
			    
			    List<OrderInfo> list= (List<OrderInfo>)request.getAttribute("orderInfoList");
			%>
			
			<table border="1">
		<tr>
			<th>订单时间</th>
			<th>订单id</th>
			<th>用户名</th>
			<th>商品及数量</th>
			<th>该订单总价</th>
		</tr>
		<%
		if(list!=null){
		    for (OrderInfo info:list) {		       
		        
		%>
		<tr>
			<td><%=info.getDate()%></td>
			<td><%=info.getOrderid()%></td>
			<td><%=info.getUsername()%></td>
			
			<td>
				<%				   
				out.write("商品名称 " + info.getProductname());
				out.write("<br>商品数量:" + info.getProductNumber());

				out.write("<br>额外属性信息:" + info.getExtendedAttributeString());

				out.write("<br>");
				out.write("<br>");			        
				%>
			</td>
			<td><%=info.getPrice()*info.getProductNumber()%></td>
		</tr>
		<%
		    }}
		%>
		</table>	
			请输入页数编号,共(<%=pages%>)页<input type="number" name="orderlistindex">
			<input type="submit" value="GO!">
		
	</form>
	<a href="<%=basePath + "Quit"%>">退出登录并返回首页</a>
	</div>
</body>
</html>