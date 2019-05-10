<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.zkdx.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>展示所有商品</title>
</head>
<body>
		<table border="1">
  <tr>
    <th>商品名称</th>
    <th>商品图片</th>
     <th>单价</th>
    <th>数量选择</th>
  </tr>
  <% List<Product>list = new ProductDAO().getAllProducts(); 
  for (int i = 0;i < list.size();i++){ Product temp=list.get(i);%>
  <tr>
    <td><% out.write(temp.getProductname()); %></td>
    <td><img src=<%out.write(temp.getPictureLink()); %>></td>
    <td><% out.write(temp.getPrice()+""); %></td>
    <td>
    <div>
    
    <div align="left" style="float:left">
        <input type="button" id="leftButton" value="+" />
    </div>
    <div style="float:left" id="quantity<% out.write(""+i);%>"> 1</div>
    <div style="float:left">
        <input type="button" id="rightButton" value="-"/>
    </div>
    <div style="float:left" ><%out.write("库存数量"+temp.getInventoryQuantity()); %></div>
    
    </div>   
 
    </td>
  </tr>
  <% } %>
</table>
</body>


</html>