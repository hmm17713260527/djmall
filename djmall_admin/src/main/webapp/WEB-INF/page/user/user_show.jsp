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
                for (var i = 0; i < data.data.list.length; i++) {
                    html += "<tr>";
                    html += "<td><input type='checkbox' name = 'ids' value = '"+data.data.list[i].userId+"'/></td>";
                    html += "<td>"+data.data.list[i].userName+"</td>";
                    html += "<td>"+data.data.list[i].nickName+"</td>";
                    html += "<td>"+data.data.list[i].sexShow+"</td>";
                    html += "<td>"+data.data.list[i].phone+"</td>";
                    html += "<td>"+data.data.list[i].email+"</td>";
                    html += data.data.list[i].roleName == null ? "<td>角色已删除</td>" : "<td>"+data.data.list[i].roleName+"</td>";
                    html += "<td>"+data.data.list[i].statusShow+"</td>";
                    html += "<td>"+data.data.list[i].createTime+"</td>";
                    html += "<td>"+data.data.list[i].endTime+"</td>";
                    html += "</tr>";
                }
                $("#tbd").html(html);
                var html1 = "角色:";
                for (var i = 0; i < data.data.paramList.length; i++) {
                    html1 += "<input type = 'radio' name = 'type' value = '"+data.data.paramList[i].roleId+"'>"+data.data.paramList[i].roleName;
                }
                $("#tbd1").html(html1);

                var pageNo = $("#pageNo").val();
                var pageHtml = "<input type='button' value='上一页' onclick='page("+data.data.pages+", "+(parseInt(pageNo) - 1)+")'>";
                pageHtml += "<input type='button' value='下一页' onclick='page("+data.data.pages+", "+(parseInt(pageNo) + 1)+")')'>";
                $("#pageInfo").html(pageHtml);

            });
    }

    function toSearch() {
        $("#pageNo").val(1);
        search();
    }

    function page(totalNum, page) {

        if (page < 1) {
            layer.msg('已经到首页啦!', {icon:0});
            return;
        }
        if (page > totalNum) {
            layer.msg('已经到尾页啦!!', {icon:0});
            return;
        }
        $("#pageNo").val(page);
        search();

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

    function updatePwd() {
        var chkValue = $('input[name="ids"]:checked');
        if (chkValue.length == 0) {
            layer.msg('未选中');
        } else if (chkValue.length > 1) {
            layer.msg('多选');
        } else {
            var id = chkValue.val();
            var index = layer.load(0, {shade:0.5});
            $.post("<%=request.getContextPath()%>/auth/user/resetPwd",
                {"userId" : id, "isDel" : 0, "_method" : "PUT"},
                function(data){
                    layer.close(index);
                    layer.msg(data.msg, function(){
                        if (data.code != 200) {
                            return;
                        }
                        search();
                    });

                })
        }
    }


</script>
</head>
<body>


<form id = "fm">
    <input type="hidden" value="1" id="pageNo" name="pageNo">
    用户名/手机号/邮箱:<input type = "text" name = "userName"/><br/>
    <table>
        <tbody id = "tbd1">
        </tbody>
    </table>

    性别:
    <c:forEach items="${baseDataSexList}" var="s">
        <input type = "radio" name = "sex" value = "${s.baseId}">${s.name}
    </c:forEach><br/>

    状态:
    <select name = "status">
        <option value="">==请选择==</option>
        <c:forEach items="${baseDataList}" var="b">
            <option value="${b.baseId}">${b.name}</option>
        </c:forEach>
    </select>
    <input type = "hidden" name = "isDel" value = "1"/><br/>
    <input type = "button" value = "search" onclick = "toSearch()"/><br/>

    <shiro:hasPermission name="BTN_USER_UPDATE">
        <input type="button" value="修改" onclick="toUpdate()"/>
    </shiro:hasPermission>
    <shiro:hasPermission name="BTN_USER_UPDATE_STATUS">
        <input type="button" value="激活" onclick="tooUpdate()"/>
    </shiro:hasPermission>
    <shiro:hasPermission name="BTN_USER_UPDATE_PWD">
        <input type="button" value="重置密码" onclick="updatePwd()"/>
    </shiro:hasPermission>
    <shiro:hasPermission name="BTN_USER_DEL">
        <input type="button" value="删除" onclick="del()"/>
    </shiro:hasPermission>
    <shiro:hasPermission name="BTN_USER_UPDATE_TYPE">
        <input type="button" value="授权" onclick="toooUpdate()"/>
    </shiro:hasPermission>
    <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
        <tr>
            <td>用户id</td>
            <td>用户名</td>
            <td>昵称</td>
            <td>性别</td>
            <td>手机号</td>
            <td>邮箱</td>
            <td>级别</td>
            <td>状态</td>
            <td>注册时间</td>
            <td>最后登录时间</td>
        </tr>

        <tbody id = "tbd">

        </tbody>

    </table>

<div id="pageInfo">

</div>

</form>

</body>
</html>
