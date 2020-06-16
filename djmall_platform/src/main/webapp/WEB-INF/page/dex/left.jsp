<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>left</title>
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

<body align="center">

<a href="<%=request.getContextPath()%>/platform/auth/toUpdateUser?TOKEN=${TOKEN}" target="right">个人信息</a><br/>

<a href="<%=request.getContextPath()%>/platform/auth/toUserSiteShow?TOKEN=${TOKEN}" target="right">收货地址</a><br/>

<a href="<%=request.getContextPath()%>/platform/auth/toUserOrderShow?TOKEN=${TOKEN}" target="right">我的订单</a><br/>　

<a href="<%=request.getContextPath()%>/platform/auth/echarsLineShow?TOKEN=${TOKEN}" target="right">折线图</a><br/>　

<a href="<%=request.getContextPath()%>/platform/auth/echarsHistogramShow?TOKEN=${TOKEN}" target="right">柱状图</a><br/>　

<a href="<%=request.getContextPath()%>/platform/auth/echarsPieShow?TOKEN=${TOKEN}" target="right">饼图</a>　


</body>
</html>