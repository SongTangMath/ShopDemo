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
	    Employee employee = (Employee)request.getSession().getAttribute("employee");
	    String ProductID = request.getParameter("ProductID");
	    if (employee == null || ProductID == null || "".equals(ProductID)) {
	        response.sendRedirect(request.getContextPath() + "/index.html");
	        return;
	    }
	    ExtendedAttributeDAO dao = new ExtendedAttributeDAO();
	    List<ExtendedAttribute> list = new ArrayList<ExtendedAttribute>();
	    try {
	        System.out.println(ProductID);
	        list=dao.listAttributesByProductID(Integer.parseInt(ProductID));
	    } catch (NumberFormatException e) {
	        System.out.println("NumberFormatException");
	    }
	    int n = list.size();
	    System.out.println("size: "+n);
	    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	        + request.getContextPath() + "/";
	%>
	<table border="1" align="center">
		<tr>
			<th>属性id</th>
			<th>产品id</th>
			<th>产品名称</th>
			<th>属性名称</th>
			<th>属性值</th>
			<th>操作</th>
		</tr>
		<%
		    for (int i = 0; i < n; i++) {
		        ExtendedAttribute attr = list.get(i);
		        String deletePath = basePath + "ModifyProductExtendedAttributeHandler?method=delete&attributeid="
		            + attr.getAttributeID()+"&ProductID="+ProductID;
		%>
		<tr>
			<td><%=attr.getAttributeID()%></td>
			<td><%=attr.getProductID()%></td>
			<td><%=attr.getProductname()%></td>
			<td><%=attr.getAttributeName()%></td>
			<td><%=attr.getAttributeValue()%></td>
			<td><a href="<%=deletePath%>">删除</a></td>
		</tr>
		<%
		    }
		    String returnPath = basePath + "ProductsPlanner.jsp";
		    String addPath = basePath + "ModifyProductExtendedAttributeHandler?method=add";
		%>
	</table>
	<div style="text-align: center">
	增加属性
	<br/>
	<form method="post" action="<%=addPath%>">
		属性名<input type="text" name="attributename" /> <br> 
		属性可选值,不同的值用空格分隔<input
			type="text" name="attributevalue" />
			<input type="hidden" name="ProductID" value="<%=ProductID %>" />
			<input type="submit" value="submit" />
	</form>
	</div>

	<a href="<%=returnPath%>">返回上一级页面</a>
</body>
</html>