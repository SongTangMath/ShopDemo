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
    User user = (User) request.getSession().getAttribute("user");

			if (user == null) {
				response.sendRedirect(request.getContextPath() + "/index.html");
				return;
			}
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/";
%>
<div style="text-align: center">
	欢迎你,<%=user%></div>
<body>
	<form method="POST"
		action="<%out.write(basePath + "UserQueryHandler");%>">

		<div style="text-align: center">
			<input type="text" name="productcategory" /> <input type="submit"
				value="按关键字搜索商品" />
		</div>
	</form>

	<form method="POST" action="<%out.write(basePath + "UserQueryHandler");%>">

		<div style="text-align: center">
			<select id="level0" name="level0" onchange="level0CategoryChanged();">
				<option>===请选择大类===</option>
			</select> <select id="level1" name="level1"
				onchange="level1CategoryChanged();">
				<option>===请选择第二层分类===</option>
			</select> <select id="level2" name="level2"
				onchange="level2CategoryChanged();">
				<option>===请选择第三层分类===</option>
			</select>
			 <input type="submit"
				value="按照类别搜索商品" />
		
		</div>
	</form>
	<form method="POST"
		action="<%out.write(basePath + "UserBuyHandler");%>">

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
			    List<Product> list = (List<Product>) request.getAttribute("productList");
						if (list == null) {
							response.sendRedirect(request.getContextPath() + "/index.html");
							return;
						}

						if (list.size() == 0) {
							out.write("对不起没有找到对应的商品类别");
						}
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
				<td>购买数量<input type="number"
					name="<%="buyProductID" + temp.getId()%>" />
				</td>

			</tr>
			<%
			    }
			%>
		</table>
		<div style="text-align: center">
			<input type="submit" value="购买上面商品">
		</div>

		<div style="text-align: center">历史订单信息</div>
		<%
		    OrderInfoDAO dao = new OrderInfoDAO();
					TreeMap<java.sql.Date, LinkedList<OrderInfo>> map = dao.mapOrdersByUsername(user.getUsername());
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
		xmlRequest.send("level0=" + level0.value);
		xmlRequest.onreadystatechange = function() {

			if (xmlRequest.readyState == 4) {
				var json = xmlRequest.responseText;
				console.log(json);
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
			}

		}
		level1CategoryChanged();
	}
	function level1CategoryChanged() {
		var xmlRequest = new XMLHttpRequest();

		xmlRequest.open("post", "http://localhost:8080/ShopDemo/JSONCategory",
				true);
		xmlRequest.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		xmlRequest.send("level1=" + level1.value);
		xmlRequest.onreadystatechange = function() {

			if (xmlRequest.readyState == 4) {
				var json = xmlRequest.responseText;
				console.log(json);
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