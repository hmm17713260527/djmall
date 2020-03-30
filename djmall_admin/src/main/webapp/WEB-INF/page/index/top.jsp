<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>top</title>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
</head>
<body>

	<h1 align="center" style="color : red">欢迎登陆北京点金教育平台，万元高薪，尽在点金</h1>
	<a href="<%=request.getContextPath()%>/auth/user/toLogin">退出</a>
	<center>
		<div id="datetime" align="right" style="color:black">
			<script>
		 		setInterval("document.getElementById('datetime').innerHTML=new Date().toLocaleString();", 1000);
			</script>
		</div>
	</center>
	
</body>
</html>