<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/4
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<title>Title</title>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jsencrypt.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
<script type="text/javascript">

    $(function() {
        show();
    })

    function show() {
        var index = layer.load(0, {shade:0.5});
        $.get("<%=request.getContextPath()%>/auth/role/show",
            $("#fm").serialize(),
            function(data){
                layer.close(index);
                if (data.code != 200) {
                    layer.msg(data.msg);
                    return;
                }
                var html = "";
                for (var i = 0; i < data.data.length; i++) {
                    html += "<tr>";
                    html += "<td>"+data.data[i].roleId+"</td>";
                    html += "<td>"+data.data[i].roleName+"</td>";
                    html += "<td>";
                    html += "<shiro:hasPermission name='ROLE_RELEVANCE'>";
                    html += "<input type='button' value='关联资源' onclick='toRoleResource("+data.data[i].roleId+")'/>";
                    html += "</shiro:hasPermission>";
                    html += "||";
                    html += "<shiro:hasPermission name='ROLE_UPDATE'>";
                    html += "<input type='button' value='编辑' onclick='toUpdate("+data.data[i].roleId+")'/>";
                    html += "</shiro:hasPermission>";
                    html += "||";
                    html += "<shiro:hasPermission name='ROLE_DEL'>";
                    html += "<input type='button' value='删除' onclick='del("+data.data[i].roleId+")'/>";
                    html += "</shiro:hasPermission>";
                    html += "</td>";
                    html += "</tr>";
                }
                $("#tbd").html(html);



            })
    }

    function del(roleId) {
        var index = layer.load(1,{shade:0.5});
        $.post("<%=request.getContextPath()%>/auth/role/del",
            {"roleId" : roleId, "_method" : "DELETE"},
            function(data){
                if(data.code == -1){
                    layer.close(index);
                    layer.msg(data.msg, {icon: 5});
                    return
                }
                layer.msg(data.msg, {
                    icon: 6,
                    time: 2000
                }, function(){
                    show();
                });
            }
        )
    }



    function toUpdate(roleId) {
        layer.open({
            type: 2,
            title: '编辑页面',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '80%'],
            content: '<%=request.getContextPath()%>/auth/role/toUpdate/' + roleId
        });
    }


    function toRoleResource(roleId) {
        layer.open({
            type: 2,
            title: '关联资源页面',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '80%'],
            content: '<%=request.getContextPath()%>/auth/role/toRoleResource/' + roleId
        });
    }


    function toAdd() {
        layer.open({
            type: 2,
            title: '新增页面',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '80%'],
            content: '<%=request.getContextPath()%>/auth/role/toAdd'
        });
    }



</script>
</head>
<body>
<form id = "fm">

    <input type="hidden" name = "isDel" value="1">
    <shiro:hasPermission name="ROLE_ADD">
        <input type="button" value="新增" onclick="toAdd()"/>
    </shiro:hasPermission>
    <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
        <tr>
            <td>编号</td>
            <td>角色名</td>
            <td>操作</td>
        </tr>

        <tbody id = "tbd">

        </tbody>

    </table>


</form>




</body>
</html>
