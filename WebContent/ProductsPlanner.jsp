<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*"%>
<%@ page import="com.zkdx.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品策划的页面</title>
</head>
 <% 
  Employee employee = (Employee)request.getSession().getAttribute("employee");
	
	if (employee == null || !"商品策划".equals(employee.getJob())) {
		response.sendRedirect(request.getContextPath() + "/index.html");  
	return;
		}
  String basePath = request.getScheme() + "://"
          + request.getServerName() + ":" + request.getServerPort()
          + request.getContextPath() + "/";%>
<div style="text-align:center">欢迎你,<%=employee %></div>
<body>

		<table border="1">
  <tr>
    <th>商品名称</th>
    <th>商品图片</th>
     <th>单价</th>
    <th>库存数量</th>
    <th>当前文案</th>
    <th>修改图片文案</th>
   
  </tr>
 	<%
  List<Product>list=new ProductDAO().getAllProducts(); 
  for(int i=0;i<list.size();i++){ Product temp=list.get(i);
 	%>
  <tr>
    <td><%=temp.getProductname()%> </td>
    <td><img src="<%=temp.getPictureLink() %>" ></td>
    <td><%=temp.getPrice()%> </td>
    <td><div><%=temp.getInventoryQuantity()%></div>   
    </td>
    <td><div><%=temp.getProductplan()==null? "":temp.getProductplan()%></div>   
    </td>
    <td><a href=
    "<%out.write(basePath+"ModifyProduct.jsp?ProductID="+temp.getId());%>">
    修改图片文案</a></td>
    
  </tr>
  <% } %>
</table>
</body>
</html>