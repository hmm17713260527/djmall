<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/4
  Time: 18:22
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

    function update() {
        var index = layer.load(0, {shade:0.5});
        $.post(
            "<%=request.getContextPath()%>/auth/user/update",
            $("#fm").serialize(),
            function(data){
                layer.close(index);
                layer.msg(data.msg, function(){
                    if (data.code != 200) {
                        return;
                    }
                    window.location.href = "<%=request.getContextPath()%>/auth/user/toShow";
                });

            })
    }

</script>
</head>
<body>

<form id = "fm">
    <input type="hidden" name = "_method" value = "PUT">
    <input type="hidden" name = "userId" value="${user.userId}"/>
    用户名:<input type = "text" name = "userName" value="${user.userName}"/><br/>
    手机:<input type = "text" name = "phone" value="${user.phone}"/><br/>
    邮箱:<input type = "text" name = "email" value="${user.email}"/><br/>
    性别:<input type = "radio" name = "sex" value="MAN" <c:if test = "${user.sex == 'MAN'}">checked</c:if>/>男
    <input type = "radio" name = "sex" value="WOMAN" <c:if test = "${user.sex == 'WOMAN'}">checked</c:if>/>女<br/>
    <input type="button" value="修改" onclick="update()"/>
</form>

</body>
</html>
