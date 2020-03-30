<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/4
  Time: 19:40
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

    function updateUserRole() {
        var index = layer.load(0, {shade:0.5});
        var chkValue = $('input[name="type"]:checked');
        if (chkValue.length == 0) {
            layer.msg('未选中');
        } else if (chkValue.length > 1) {
            layer.msg('多选');
        } else {
            var id = chkValue.val();
            $.post(
                "<%=request.getContextPath()%>/auth/user/updateUserRole",
                {"type" : id, "userId" : '${id}', "_method" : "PUT"},
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

    }

</script>
</head>
<body>
    <form id = "fm">
        <input type = "button" value = "确定" onclick="updateUserRole()"/>
        <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
            <tr>
                <td>编号</td>
                <td>角色名</td>
            </tr>
            <c:forEach items="${roleList}" var="t">
                <tr>
                    <td><input type='radio' name = 'type' value = '${t.roleId}'/></td>
                    <td>${t.roleName}</td>
                </tr>
            </c:forEach>
        </table>

    </form>


</body>
</html>
