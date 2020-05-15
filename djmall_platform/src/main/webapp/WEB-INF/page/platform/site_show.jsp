<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/1
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jsencrypt.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\cookie.js"></script>
    <script type="text/javascript">

        $(function() {
            search();
        });

        function search() {
            var index = layer.load(0, {shade:0.5});
            token_post("<%=request.getContextPath() %>/platform/auth/userSiteShow?TOKEN=" + getToken(),
                {"userId" : '${userId}'},
                function(data){
                    layer.close(index);
                    if (data.code != 200) {
                        layer.msg(data.msg);
                        return;
                    }

                    var html = "";
                    for (var i = 0; i < data.data.length; i++) {
                        html += "<tr>";
                        html += "<td>"+data.data[i].siteId+"</td>";
                        html += "<td>"+data.data[i].consignee+"</td>";
                        html += "<td>"+data.data[i].provinceShow+"-"+data.data[i].cityShow+"-"+data.data[i].countyShow+"-"+data.data[i].site+"</td>";
                        html += "<td>"+data.data[i].phone+"</td>";
                        html += "<td>";
                        html += "<a onclick = 'toUpdate("+data.data[i].siteId+")'>修改</a>|";
                        html += "<a onclick = 'del("+data.data[i].siteId+")'>删除</a>";
                        html += "</td>";

                        html += "</tr>";
                    }
                    $("#tbd").html(html);
                });
        }

        function del(siteId) {
            var index = layer.load(1,{shade:0.5});
            token_post("<%=request.getContextPath() %>/platform/auth/delUserSiteById?TOKEN=" + getToken(),
                {"id" : siteId},
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
                        window.location.href = "<%=request.getContextPath()%>/platform/auth/toUserSiteShow?TOKEN="+getToken();
                    });
                }
            )
        }

        function toUpdate(id) {
            layer.open({
                type: 2,
                title: '修改页面',
                shadeClose: true,
                shade: 0.8,
                area: ['380px', '80%'],
                content: '<%=request.getContextPath()%>/platform/auth/toUpdateSite?TOKEN='+getToken()+'&siteId='+id
            });
        }

        function toAdd() {
            layer.open({
                type: 2,
                title: '新增页面',
                shadeClose: true,
                shade: 0.8,
                area: ['380px', '80%'],
                content: '<%=request.getContextPath()%>/platform/auth/toAdd?TOKEN='+getToken()
            });
        }

    </script>
</head>
<body>

<form id="frm">

    <input type="button" value="新增收货地址" onclick="toAdd()"/>

    <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
        <tr>
            <td>编号</td>
            <td>收件人</td>
            <td>详细地址</td>
            <td>手机号</td>
            <td>操作</td>
        </tr>

        <tbody id = "tbd">

        </tbody>

    </table>

</form>

</body>
</html>
