<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/7
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Title</title>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
<script type="text/javascript">

    function update (){
        var index = layer.load(1,{shade:0.5});
        $.post(
            "<%=request.getContextPath()%>/auth/res/update",
            $("#fm").serialize(),
            function(data){
                if (data.code != -1) {
                    layer.msg(data.msg, {icon: 6}, function(){
                        parent.window.location.href = "<%=request.getContextPath()%>/auth/res/toShow";
                    });
                    return;
                }
                layer.msg(data.msg, {icon: 5})
                layer.close(index);

            }
        )
    }

</script>
</head>
<body>

<form id = "fm">
    <input type="hidden" name = "_method" value = "PUT">
    <input type="hidden" name="resId" value="${resource.resId}"><br />
    资源名:<input type="text" name="resourceName" value="${resource.resourceName}"><br />
    资源路径:<input type="text" name="url" value="${resource.url}"><br />
    资源编码:<input type="text" name="resourceCode" value="${resource.resourceCode}"><br />
    <input type="button" value="修改提交" onclick="update()"><br />
</form>

</body>
</html>
