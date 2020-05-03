<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>index</title>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\token.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\cookie.js"></script>
</head>
<frameset rows="25%,*">
	<frame src="<%=request.getContextPath() %>/platform/toTop" name="top"/>
	<frameset cols="20%,*">
		<frame src="<%=request.getContextPath() %>/platform/toLeft" name="left" />
		<frame src="<%=request.getContextPath() %>/platform/toRight" name="right" />
	</frameset>
</frameset>
</html>