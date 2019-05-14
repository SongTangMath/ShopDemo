<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
            <%@ page import="java.util.*"%>
<%@ page import="com.zkdx.*"%>
<%@ page import="java.text.DateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户的页面</title>
</head>
<% 
 User user = (User)request.getSession().getAttribute("user");
	
	if (user == null ) {
		response.sendRedirect(request.getContextPath() + "/index.html");  
	return;
		}
  String basePath = request.getScheme() + "://"
          + request.getServerName() + ":" + request.getServerPort()
          + request.getContextPath() + "/";%>
<div style="text-align:center">欢迎你,<%=user %></div>
<body>
<form method="POST" action="<%out.write(basePath+"UserBuyHandler"); %>" >

		<table border="1">
  <tr>
    <th>商品名称</th>
    <th>商品图片</th>
     <th>单价</th>
    <th>库存数量</th>
    <th>当前文案</th>
    <th>购买数量(若超过库存则认为全部购买)</th>
   
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
    <td>购买数量<input type="number"  name="<%="buyProductID"+temp.getId() %>"/>
    </td>
    
  </tr>
  <% } %>
</table>
<div style="text-align:center"><input type="submit" value="购买上面商品"></div>

<div style="text-align:center">历史订单信息</div>
<% OrderInfoDAO dao=new OrderInfoDAO();
HashMap<java.sql.Date,LinkedList<OrderInfo>> map =dao.mapOrdersByUsername(user.getUsername());
DateFormat ddtf = DateFormat.getDateTimeInstance();  
//System.out.println(ddtf.format(date));
%>
		<table border="1">
  <tr>
    <th>订单时间</th>
    <th>商品及数量</th>
     <th>总价</th>
    
   
  </tr>
  <% 
  //Collections.sort(listOrder,(a,b)->a.getDate().compareTo(b.getDate()));
  
  for(java.sql.Date date:map.keySet()){
      LinkedList<OrderInfo> listOrderInfo=map.get(date);
      int sum=0,n=listOrderInfo.size();
     %>
    
 
 
     
          
 
  <tr>
  	<td><%=ddtf.format(date) %></td>
  	<td><%
  	for(int i=0;i<n;i++){
  	    out.write("商品名称"+listOrderInfo.get(i).getProductname() + 
  	        "商品数量"+listOrderInfo.get(i).getProductNumber());
  	    out.write("<br>");
  	sum+=listOrderInfo.get(i).getProductNumber()*listOrderInfo.get(i).getPrice();
  	}
  	%></td>
  	<td><%=sum %></td>
  </tr>
<%} %>
<a href="<%=basePath+"Quit" %>">退出登录并返回首页</a>
</body>
</html>