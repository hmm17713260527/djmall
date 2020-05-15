<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>index</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>\static\layui-v2.5.5\layui\css\layui.css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jsencrypt.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\layui-v2.5.5\layui\layui.all.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\md5-min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\token.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\cookie.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\slideVerify\js\jq-slideVerify.js" charset="utf-8"></script>
</head>
<frameset rows="25%,*">
	<frame src="<%=request.getContextPath() %>/platform/toTop?TOKEN=${TOKEN}" name="top"/>
	<frameset cols="20%,*">
		<frame src="<%=request.getContextPath() %>/platform/toLeft?TOKEN=${TOKEN}" name="left" />
		<frame src="<%=request.getContextPath() %>/platform/toRight?TOKEN=${TOKEN}" name="right" />
	</frameset>
</frameset>
</html>