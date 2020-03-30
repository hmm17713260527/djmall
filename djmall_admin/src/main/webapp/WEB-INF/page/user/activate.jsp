<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
<script type="text/javascript">

	$(function() {
		layer.msg("激活成功");
		search();
	})
	
	function search() {
		var index = layer.load(0, {shade:0.5});
		$.post("<%=request.getContextPath()%>/auth/user/updateStatusByEmail",
				{"status":1, "email":'${email}', "_method" : "PUT"},
				function(data){
					layer.close(index);
					window.location.href = "<%=request.getContextPath()%>/auth/user/toLogin";
				})
	}


</script>
</head>
<body>


</body>
</html>