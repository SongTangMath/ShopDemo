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
		action="<%out.write(basePath + "DeleteCategory");%>">
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
			 要删除的类别名称(所有子类别也会被删除)<input type="text"		name="categorynametodel">
			 <input type="submit"
				value="submit">
		</div>
 <br/>

	</form>
	
	<br><br><br>
	<a href="<%=basePath + "Purchaser.jsp"%>" target="_self">返回添加类别的页面</a>
	<br><br>
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