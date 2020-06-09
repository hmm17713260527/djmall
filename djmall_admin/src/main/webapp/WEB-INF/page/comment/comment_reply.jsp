<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/7
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
    <script src="<%=request.getContextPath()%>\static\js\jquery.validate.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
    <script type="text/javascript">


        function reply() {
            var index = layer.load(1,{shade:0.5});
            $.post("<%=request.getContextPath()%>/comment/reply",
                $("#fm").serialize(),
                function(data){
                    layer.close(index);
                    if (data.code != 200) {
                        layer.msg(data.msg);
                        return;
                    }
                    parent.window.location.href = "<%=request.getContextPath()%>/comment/toShow";


                }
            )
        }


    </script>
</head>
<body>

<form id="fm">
    <input type="hidden" name="parentId" value="${parentId}">
    <input type="hidden" name="userId" value="${userId}">
    <textarea rows="4px" cols="30px" name="comment"></textarea><br/>

    <input type="button" value="ok" onclick="reply()">
</form>

</body>
</html>
