<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>top</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>\static\layui-v2.5.5\layui\css\layui.css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jsencrypt.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\layui-v2.5.5\layui\layui.all.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\md5-min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\token.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\cookie.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\slideVerify\js\jq-slideVerify.js" charset="utf-8"></script>
	<script type="text/javascript">

		// 首页
		function toIndex() {
			window.location.href = "<%=request.getContextPath()%>/platform/toShow";
		}
	</script>
</head>
<body>

	<a  href="javascript:toIndex()">首页</a>&nbsp;
	<center>
		<div id="datetime" align="right" style="color:black">
			<script>
		 		setInterval("document.getElementById('datetime').innerHTML=new Date().toLocaleString();", 1000);
			</script>
		</div>
	</center>
	
</body>
</html>