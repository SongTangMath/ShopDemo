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
			商品名称<input type="text" name="productname" /> <br/>商品售价<input type="number"
				name="productprice" />  <br/>商品进价<input type="number" name="buyingprice" />
			 <br/>商品类别选择<select id="level0" name="level0" onchange="level0CategoryChanged();">
				<option>===请选择大类===</option>
			</select> <select id="level1" name="level1"
				onchange="level1CategoryChanged();">
				<option>===请选择第二层分类===</option>
			</select> <select id="level2" name="level2"
				onchange="level2CategoryChanged();">
				<option>===请选择第三层分类===</option>
			</select>
			 <br/>
			 <input type="submit"
				value="submit">
		</div>
 <br/>

	</form>
	
	</form>

	<div style="text-align: center">通过excel新增商品,格式如下:第一行为各字段名称,后面的行为数据<br>
	序号 商品名称 商品售价 商品进价 商品分类
	</div>
	<form method="POST" enctype="multipart/form-data"
		action="<%out.write(basePath + "addProductsFromExcel");%>">
		<div style="text-align: center">
			请选择excel文件<input type="file" name="excel" />
			 <input type="submit"
				value="submit">
		</div>


	</form>

	<a href="<%=basePath + "Quit"%>" target="_self">退出登录并返回首页</a>
</body>
<script type="text/javascript">
	var level0 = document.getElementById("level0");
	var level1 = document.getElementById("level1");
	var level2 = document.getElementById("level2");
	window.onload = function() {
		var xmlRequest = new XMLHttpRequest();
		xmlRequest.open("get", "http://localhost:8080/ShopDemo/JSONCategory",
				true);
		xmlRequest.send(null);
		xmlRequest.onreadystatechange = function() {
			if (xmlRequest.readyState == 4 && xmlRequest.status == 200) {

				var json = xmlRequest.responseText;
				//console.log(json);
				var js = eval("(" + json + ")");
				for (var i = 0; i < js.length; i++) {
					var op = document.createElement("option");
					op.value = js[i].categoryName;
					var text = document.createTextNode(js[i].categoryName);
					op.appendChild(text);
					level0.appendChild(op);
				}

			}
		};
	};

	function level0CategoryChanged() {

		var xmlRequest = new XMLHttpRequest();

		xmlRequest.open("post", "http://localhost:8080/ShopDemo/JSONCategory",
				true);
		xmlRequest.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		
		console.log("level0CategoryChanged level0= "+ level0.value);
		xmlRequest.send("level0=" + level0.value);
		xmlRequest.onreadystatechange = function() {

			if (xmlRequest.readyState == 4) {
				var json = xmlRequest.responseText;
				
				var js = eval("(" + json + ")");

				level1.options.length = 0;
				var optemp = document.createElement("option");
				var texttemp = document.createTextNode("请选择第二层分类");
				optemp.appendChild(texttemp);
				level1.appendChild(optemp);
				
				
				for (var i = 0; i < js.length; i++) {
					var op = document.createElement("option");
					op.value = js[i].categoryName;
					console.log(js[i].categoryName);
					var text = document.createTextNode(js[i].categoryName);
					op.appendChild(text);
					level1.appendChild(op);
				}
				level1CategoryChanged();
			}
			
		}
		
	}
	function level1CategoryChanged() {
		var xmlRequest = new XMLHttpRequest();

		xmlRequest.open("post", "http://localhost:8080/ShopDemo/JSONCategory",
				true);
		xmlRequest.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		console.log("level1CategoryChanged level1= "+ level1.value);
		xmlRequest.send("level1=" + level1.value);
		xmlRequest.onreadystatechange = function() {

			if (xmlRequest.readyState == 4) {
				var json = xmlRequest.responseText;
				
				var js = eval("(" + json + ")");

				level2.options.length = 0;
				var optemp = document.createElement("option");
				var texttemp = document.createTextNode("请选择第三层分类");
				optemp.appendChild(texttemp);
				level2.appendChild(optemp);

				for (var i = 0; i < js.length; i++) {
					var op = document.createElement("option");
					op.value = js[i].categoryName;					
					console.log(js[i].categoryName);
					var text = document.createTextNode(js[i].categoryName);
					op.appendChild(text);
					level2.appendChild(op);
				}
			}

		}
		
	}
	function level2CategoryChanged() {
	}
</script>
</html>