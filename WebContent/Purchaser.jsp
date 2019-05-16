<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.zkdx.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>采购员的页面</title>
</head>
<%
    Employee employee = (Employee)request.getSession().getAttribute("employee");

    if (employee == null || !"采购员".equals(employee.getJob())) {
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
		action="<%out.write(basePath + "ModifyProductNumber");%>">

		<table border="1">
			<tr>
				<th>商品名称</th>
				<th>商品图片</th>
				<th>单价</th>
				<th>库存数量</th>
				<th>当前文案</th>
				<th>进货数量</th>

			</tr>
			<%
			    List<Product> list = new ProductDAO().getAllProducts();
			    for (int i = 0; i < list.size(); i++) {
			        Product temp = list.get(i);
			%>
			<tr>
				<td><%=temp.getProductname()%></td>
				<td><img src="<%=temp.getPictureLink()%>"></td>
				<td><%=temp.getPrice()%></td>
				<td><div><%=temp.getInventoryQuantity()%></div></td>
				<td><div><%=temp.getProductplan() == null ? "" : temp.getProductplan()%></div>
				</td>
				<td>进货数量<input type="number"
					name="<%="addProductID" + temp.getId()%>" />
				</td>

			</tr>
			<%
			    }
			%>
		</table>
		<div style="text-align: center">
			<input type="submit" value="submit">
		</div>

	</form>

	<div style="text-align: center">下面是新增商品的表单</div>
	<form method="POST"
		action="<%out.write(basePath + "addNewProduct");%>">
		<div style="text-align: center">
			商品名称<input type="text" name="productname" /> 商品售价<input type="number"
				name="productprice" /> 商品进价<input type="number" name="buyingprice" />
			商品类别<input type="text" name="productcategory" /> <input type="submit"
				value="submit">
		</div>


	</form>

	<a href="<%=basePath + "Quit"%>">退出登录并返回首页</a>
</body>
</html>