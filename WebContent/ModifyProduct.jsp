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
		response.sendRedirect(request.getContextPath()+"/index.html");  
	return;
	}
	ProductDAO dao = new ProductDAO();
	Product product = dao.getProductById(Integer.parseInt(ProductID));
	System.out.println(product);
	 String basePath = request.getScheme() + "://"
	          + request.getServerName() + ":" + request.getServerPort()
	          + request.getContextPath() + "/";
	
		%>
		<div style="text-align:center">当前图片</div>
		<div style="margin: 100px 300px ">
	<img src="<%=product.getPictureLink() %>" >
	<div>
		<form method="POST" action="<%out.write(basePath+"ModifyProduct"); %>" 
		enctype="multipart/form-data">
		请选择图片<input type="file" name="newpicture"/>
		<br>
		或者直接输入URL<input type="url" name="url"/>
		<%String productplan=product.getProductplan()==null?"":product.getProductplan();
		System.out.println("productplan: "+productplan);
		%>
		<br>当前文案<br>
		<input type="text" name="productplan" value=
		"<%=productplan %>" >
		<input type="hidden" name="productname" value="<%=product.getProductname()%>">
		<input type="submit" value="submit">
		
		</form>
	</div>
</div>
</body>
</html>