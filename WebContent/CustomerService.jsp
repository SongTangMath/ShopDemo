<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.zkdx.*"%>
<%@ page import="java.text.DateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>客服的页面</title>
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
	%>
	<div style="text-align: center">
		欢迎你,<%=employee%></div>
<body>
	<form method="POST"
		action="<%out.write(basePath + "CustomerServiceQueryUser");%>">

		<div style="text-align: center">
			<input type="text" name="username"> <input type="submit"
				value="搜索指定用户的订单">
		</div>
	</form>


	<%
	    TreeMap<java.sql.Date, LinkedList<OrderInfo>> map =
	        (TreeMap<java.sql.Date, LinkedList<OrderInfo>>)request.getAttribute("orderInfoMap");
	    String username = request.getParameter("username");
	    User user = (User)request.getAttribute("user");

	    if (map == null) {
	        response.sendRedirect("/ShopDemo/index.html");
	        return;
	    }
	    DateFormat ddtf = DateFormat.getDateTimeInstance();
	%>
	<%
	    String toShow = "未搜索到用户";
	    if (user != null) {
	        toShow = username + " 的历史订单信息";
	    }
	%>
	<div style="text-align: center"><%=toShow%></div>
	<table border="1">
		<tr>
			<th>订单时间</th>
			<th>商品及数量</th>
			<th>总价</th>


		</tr>
		<%
		    for (java.sql.Date date : map.keySet()) {
		        LinkedList<OrderInfo> listOrderInfo = map.get(date);
		        int sum = 0, n = listOrderInfo.size();
		%>






		<tr>
			<td><%=ddtf.format(date)%></td>
			<td>
				<%
				    for (int i = 0; i < n; i++) {
				            out.write("商品名称" + listOrderInfo.get(i).getProductname() + "商品数量"
				                + listOrderInfo.get(i).getProductNumber());
				            out.write("<br>");
				            sum += listOrderInfo.get(i).getProductNumber() * listOrderInfo.get(i).getPrice();
				        }
				%>
			</td>
			<td><%=sum%></td>
		</tr>
		<%
		    }
		%>
		<a href="<%=basePath + "Quit"%>">退出登录并返回首页</a>
</body>
</html>