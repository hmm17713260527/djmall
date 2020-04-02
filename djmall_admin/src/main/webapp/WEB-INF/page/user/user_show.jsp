<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/4
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Title</title>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jsencrypt.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
<script type="text/javascript">

    $(function() {
        search();
    })


    function search() {
        var index = layer.load(0, {shade:0.5});
        $.post("<%=request.getContextPath() %>/auth/user/show",
            $("#fm").serialize(),
            function(data){
                layer.close(index);
                if (data.code != 200) {
                    layer.msg(data.msg);
                    return;
                }

                var html = "";
                for (var i = 0; i < data.data.userList.length; i++) {
                    html += "<tr>";
                    html += "<td><input type='checkbox' name = 'ids' value = '"+data.data.userList[i].id+"'/></td>";
                    html += "<td>"+data.data.userList[i].userName+"</td>";
                    html += "<td>"+data.data.userList[i].nickName+"</td>";
                    html += "<td>"+data.data.userList[i].phone+"</td>";
                    html += "<td>"+data.data.userList[i].email+"</td>";
                    html += data.data.userList[i].roleName == null ? "<td>角色已删除</td>" : "<td>"+data.data.userList[i].roleName+"</td>";
                    html += "<td>"+data.data.userList[i].statusShow+"</td>";
                    html += "</tr>";
                }
                $("#tbd").html(html);
                var html1 = "角色:";
                for (var i = 0; i < data.data.roleList.length; i++) {
                    html1 += "<input type = 'radio' name = 'type' value = '"+data.data.roleList[i].id+"'>"+data.data.roleList[i].roleName;
                }
                $("#tbd1").html(html1);
            });
    }


    function toUpdate() {
        var chkValue = $('input[name="ids"]:checked');
        if (chkValue.length == 0) {
            layer.msg('未选中');
        } else if (chkValue.length > 1) {
            layer.msg('多选');
        } else {
            var id = chkValue.val();
            window.location.href= "<%=request.getContextPath()%>/auth/user/toUpdate/"+id;
        }
    }

    function toooUpdate() {
        var chkValue = $('input[name="ids"]:checked');
        if (chkValue.length == 0) {
            layer.msg('未选中');
        } else if (chkValue.length > 1) {
            layer.msg('多选');
        } else {
            var id = chkValue.val();
            window.location.href= "<%=request.getContextPath()%>/auth/user/toooUpdate/"+id;
        }
    }

    function tooUpdate() {
        var chkValue = $('input[name="ids"]:checked');
        if (chkValue.length == 0) {
            layer.msg('未选中');
        } else if (chkValue.length > 1) {
            layer.msg('多选');
        } else {
            var id = chkValue.val();
            var index = layer.load(0, {shade:0.5});
            $.post("<%=request.getContextPath()%>/auth/user/updateStatus",
                {"userId" : id, "status" : 10, "_method" : "PUT"},
                function(data){
                    layer.close(index);
                    layer.msg(data.data, function(){
                        if (data.code != 200) {
                            return;
                        }
                        window.location.href = "<%=request.getContextPath()%>/auth/user/toShow";
                    });

                })
        }
    }

   function del() {
        var chkValue = $('input[name="ids"]:checked');
        if (chkValue.length == 0) {
            layer.msg('未选中');
        } else if (chkValue.length > 1) {
            layer.msg('多选');
        } else {
            var id = chkValue.val();
            var index = layer.load(0, {shade:0.5});
            $.post("<%=request.getContextPath()%>/auth/user/del",
                {"userId" : id, "isDel" : 2, "_method" : "DELETE"},
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
    用户名/手机号/邮箱:<input type = "text" name = "userName"/><br/>
    <table>
        <tbody id = "tbd1">
        </tbody>
    </table>
    状态:
    <select name = "status">
        <c:forEach items="${baseDataList}" var="b">
            <option value="${b.baseId}">${b.name}</option>
        </c:forEach>
<%--        <option value = "">请选择</option>--%>
<%--        <option value = 1>正常</option>--%>
<%--        <option value = 2>未激活</option>--%>
    </select>
    <input type = "hidden" name = "isDel" value = "1"/><br/>
    <input type = "button" value = "search" onclick = "search()"/><br/>

    <shiro:hasPermission name="USER_UPDATE">
        <input type="button" value="修改" onclick="toUpdate()"/>
    </shiro:hasPermission>
    <shiro:hasPermission name="USER_UPDATE_STATUS">
        <input type="button" value="激活" onclick="tooUpdate()"/>
    </shiro:hasPermission>
    <shiro:hasPermission name="USER_DEL">
        <input type="button" value="删除" onclick="del()"/>
    </shiro:hasPermission>
    <shiro:hasPermission name="USER_UPDATE_TYPE">
        <input type="button" value="授权" onclick="toooUpdate()"/>
    </shiro:hasPermission>
    <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
        <tr>
            <td>用户id</td>
            <td>用户名</td>
            <td>昵称</td>
            <td>手机号</td>
            <td>邮箱</td>
            <td>级别</td>
            <td>状态</td>
        </tr>

        <tbody id = "tbd">

        </tbody>

    </table>


</form>




</body>
</html>
