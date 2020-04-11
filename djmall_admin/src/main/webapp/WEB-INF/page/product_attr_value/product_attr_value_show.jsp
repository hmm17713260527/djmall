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
                name:{
                    required:true,
                    remote: {
                        type: 'GET',
                        url: "<%=request.getContextPath()%>/dict/product_attr_value/findProductAttrValueByName",
                        data:{
                            name:function() {
                                return $("#name").val();
                            },
                            dataType:"json",
                            dataFilter:function(data,type){
                                if (data == 'true'){
                                    return true;
                                }else {
                                    return false	;
                                }
                            }

                        }
                    }
                }
            },
            messages:{
                name:{
                    required:"不能为空",
                    remote:"已存在"
                }
            }


        })
    })

    function add() {
        var index = layer.load(0, {shade:0.5});
        $.post("<%=request.getContextPath() %>/dict/product_attr_value/add",
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
        $.get("<%=request.getContextPath() %>/dict/product_attr_value/show",
            {"attrId":'${productAttr.attrId}'},
            function(data){
                layer.close(index);
                if (data.code != 200) {
                    layer.msg(data.msg);
                    return;
                }

                var html = "";
                for (var i = 0; i < data.data.list.length; i++) {
                    html += "<tr>";
                    html += "<td>"+data.data.list[i].attrValueId+"</td>";
                    html += "<td>"+data.data.list[i].name+"</td>";
                    html += "<td>";
                    html += "<shiro:hasPermission name='BTN_ATTR_DEL'>";
                    html += "<input type='button' value='移除' onclick='del("+data.data.list[i].attrValueId+")'/>";
                    html += "</shiro:hasPermission>";
                    html += "</td>";
                    html += "</tr>";
                }
                $("#tbd").html(html);

            });
    }


    function del(attrValueId) {
        var index = layer.load(0, {shade:0.5});
        $.post("<%=request.getContextPath()%>/dict/product_attr_value/del",
            {"attrValueId" : attrValueId, "_method" : "DELETE"},
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


</script>
<style>
    .error{
        color:red;
    }
</style>
<body>

<form id="fm">
    <input type = "hidden" name = "attrId" value = "${productAttr.attrId}">
    属性名:${productAttr.attr}<br/>
    属性值：<input type="text" name="name" id="name"/>
    <shiro:hasPermission name="BTN_ATTR_ADD">
        <input type="button" value="新增" onclick="add()"/>
    </shiro:hasPermission>


</form>

<table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
    <tr>
        <td>编号</td>
        <td>属性值</td>
        <td>操作</td>
    </tr>

    <tbody id = "tbd">

    </tbody>

</table>

</body>
</html>
