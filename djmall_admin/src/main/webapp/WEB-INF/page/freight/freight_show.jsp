<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/10
  Time: 20:02
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
<script src="<%=request.getContextPath()%>\static\js\jquery.validate.js"></script>
</head>
<script type="text/javascript">

    $(function() {
        search();
    })

    $(function() {
        $("#fm").validate({

            rules:{
                freight:{
                    required:true,
                    digits:true
                }
            },
            messages:{
                freight:{
                    required:"不能为空",
                    digits:"只能是数字"
                }
            }


        })
    })

    function add() {
        var index = layer.load(0, {shade:0.5});
        $.post("<%=request.getContextPath() %>/dict/freight/add",
            $("#fm").serialize(),
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




    function search() {
        var index = layer.load(0, {shade:0.5});
        $.get("<%=request.getContextPath() %>/dict/freight/show",
            {},
            function(data){
                layer.close(index);
                if (data.code != 200) {
                    layer.msg(data.msg);
                    return;
                }

                var html = "";
                for (var i = 0; i < data.data.length; i++) {
                    html += "<tr>";
                    html += "<td>"+data.data[i].baseName+"</td>";
                    html += "<td>"+data.data[i].freight+"</td>";
                    html += "<td>";
                    html += "<shiro:hasPermission name='BTN_FREIGHT_UPDATE'>";
                    html += "<input type='button' value='修改' onclick='toUpdate("+data.data[i].freightId+")'/>";
                    html += "</shiro:hasPermission>";
                    html += "</td>";
                    html += "</tr>";
                }
                $("#tbd").html(html);

            });
    }


    function toUpdate(freightId) {
        layer.open({
            type: 2,
            title: '修改页面',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '80%'],
            content: '<%=request.getContextPath()%>/dict/freight/toUpdate/' + freightId
        });
    }


</script>
<style>
    .error{
        color:red;
    }
</style>
<body>

<form id="fm">

    物流公司:
    <select name = "baseCode">
        <c:forEach items="${baseDataList}" var="b">
            <option value="${b.code}">${b.name}</option>
        </c:forEach>
    </select><br/>
    运费：<span style="color : red">包邮为：0</span><br/>
    <input type="text" name="freight"/><br/>

    <shiro:hasPermission name="BTN_FREIGHT_ADD">
        <input type="button" value="新增" onclick="add()"/>
    </shiro:hasPermission>

</form>

<table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
    <tr>
        <td>物流公司</td>
        <td>运费</td>
        <td>操作</td>
    </tr>

    <tbody id = "tbd">

    </tbody>

</table>

</body>
</html>
