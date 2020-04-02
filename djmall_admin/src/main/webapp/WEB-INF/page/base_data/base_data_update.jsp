<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/2
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Title</title>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
<script type="text/javascript">

    function update() {
        var index = layer.load(0, {shade:0.5});
        $.post(
            "<%=request.getContextPath()%>/auth/base_data/update",
            $("#fm").serialize(),
            function(data){
                layer.close(index);
                layer.msg(data.msg, function(){
                    if (data.code != 200) {
                        return;
                    }
                    parent.window.location.href = "<%=request.getContextPath()%>/auth/base_data/toShow";
                });

            })
    }


</script>
</head>
<body>

<form id = "fm">
    <input type="hidden" name = "_method" value = "PUT">
    <input type="hidden" name = "baseId" value="${base.baseId}"/>
    CODE:<input type = "text" name = "code" value="${base.code}"/><br/>
    字典名:<input type = "text" name = "name" value="${base.name}"/><br/>
    <shiro:hasPermission name="BASE_DATA_UPDATE">
        <input type="button" value="修改" onclick="update()"/>
    </shiro:hasPermission>
</form>

</body>
</html>
