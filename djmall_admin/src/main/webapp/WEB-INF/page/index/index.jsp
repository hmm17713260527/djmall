<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>index</title>
</head>
<frameset rows="25%,*">
	<frame src="<%=request.getContextPath() %>/index/toTop" name="top"/>
	<frameset cols="20%,*">
		<frame src="<%=request.getContextPath() %>/index/toLeft" name="left" />
		<frame src="<%=request.getContextPath() %>/index/toRight" name="right" />
	</frameset>
</frameset>
</html>