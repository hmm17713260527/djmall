<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/2
  Time: 15:59
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
        $.post("<%=request.getContextPath() %>/auth/base_data/show",
            $("#fm").serialize(),
            function(data){
                layer.close(index);
                if (data.code != 200) {
                    layer.msg(data.msg);
                    return;
                }

                var html = "";
                var html1 = "分类上级:";
                html1 += "<select name='parentCode'>";
                html1 += "<option value='SYSTEM'>==SYSTEM==</option>";
                for (var i = 0; i < data.data.baseDataList.length; i++) {
                    html += "<tr>";
                    html += "<td>"+data.data.baseDataList[i].code+"</td>";
                    html += "<td>"+data.data.baseDataList[i].name+"</td>";
                    html += "<td>"+data.data.baseDataList[i].parentCode+"</td>";
                    html += "<td>";
                    html += "<shiro:hasPermission name='BASE_DATA_UPDATE'>";
                    html += "<input type='button' value='修改' onclick='toUpdate("+data.data.baseDataList[i].id+")'/>";
                    html += "</shiro:hasPermission>";
                    html += "</td>";
                    html += "</tr>";

                    if (data.data.baseDataList[i].parentCode == "SYSTEM") {
                        html1 += "<option value='data.data.baseDataList[i].code'>"+data.data.baseDataList[i].name+"</option>";
                    }
                }
                $("#tbd").html(html);

                html1 += "<select/><br/>";
                html1 += "分类名称：";
                html1 += "<input type='text' name='name'/><br/>";
                html1 += "分类CODE：";
                html1 += "<input type='text' name='code'/><br/>";

                $("#tbd1").html(html1);
            });
    }

    function toUpdate(baseId) {
        layer.open({
            type: 2,
            title: '修改页面',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '80%'],
            content: '<%=request.getContextPath()%>/auth/base_data/toUpdate/' + baseId
        });
    }


    function add() {
        var index = layer.load(1,{shade:0.5});
        $.post("<%=request.getContextPath()%>/auth/base_data/add",
            $("#fm").serialize(),
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
                    search();
                });
            }
        )
    }


</script>
</head>
<body>

<form id = "fm">


    <table>
        <tbody id = "tbd1">
        </tbody>
    </table>

    <shiro:hasPermission name="BASE_DATA_ADD">
        <input type="button" value="新增" onclick="add()"/>
    </shiro:hasPermission>


    <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
        <tr>
            <td>CODE</td>
            <td>字典名</td>
            <td>上级CODE</td>
            <td>操作</td>
        </tr>

        <tbody id = "tbd">

        </tbody>

    </table>


</form>


</body>
</html>
