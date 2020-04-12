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


    function search() {
        var index = layer.load(0, {shade:0.5});
        $.get("<%=request.getContextPath() %>/dict/product_sku/attrShow",
            {"productId" : '${productId}'},
            function(data){
                layer.close(index);
                if (data.code != 200) {
                    layer.msg(data.msg);
                    return;
                }

                var html = "";
                for (var i = 0; i < data.data.list.length; i++) {
                    html += "<tr>";
                    html += "<td><input type = 'checkbox' name = 'ids' value = '"+data.data.list[i].attrId+"'/>"+data.data.list[i].attrId+"</td>";
                    html += "<td>"+data.data.list[i].attr+"</td>";
                    html += data.data.list[i].attrValue == null ? "<td>暂无属性值</td>" : "<td>"+data.data.list[i].attrValue+"</td>";
                    html += "</tr>";
                }
                $("#tbd").html(html);

                for (var i = 0; i < data.data.list.length; i++) {
                    $("input:checkbox[name='ids']")[i].checked = data.data.list[i].checked;
                }

            });
    }


    function update() {
        var index = layer.load(1,{shade:0.5});
        var str = "";
        $("input[name='ids']:checked").each(function (index, item) {
            if ($("input[name='ids']:checked").length-1==index) {
                str += $(this).val();
            } else {
                str += $(this).val() + ",";
            }
        });

        $.post(
            "<%=request.getContextPath()%>/dict/product_sku/update/${productId}",
            {"skuIds": str},
            function (data) {

                if (data.code == 200) {
                    layer.msg(data.msg, {
                        icon: 6,
                        time: 1000 //1秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        window.parent.location.reload();
                    });
                } else {
                    layer.msg(data.msg, {icon: 5})
                    layer.close(index);
                }
            })


    }




</script>
<body>

<form id="fm">

    <input type="hidden" name="productId" value="${productId}"/>
    <input type="button" value="保存" onclick="update()">

    <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
        <tr>
            <td>编号</td>
            <td>属性名</td>
            <td>属性值</td>
        </tr>

        <tbody id = "tbd">

        </tbody>

    </table>

</form>
</body>
</html>
