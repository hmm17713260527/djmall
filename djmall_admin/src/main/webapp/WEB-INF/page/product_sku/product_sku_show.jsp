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
        $.get("<%=request.getContextPath() %>/dict/product_sku/show",
            {"parentCode" : "PRODUCT_TYPE"},
            function(data){
                layer.close(index);
                if (data.code != 200) {
                    layer.msg(data.msg);
                    return;
                }

                var html = "";
                for (var i = 0; i < data.data.list.length; i++) {
                    html += "<tr>";
                    html += "<td>"+data.data.list[i].skuId+"</td>";
                    html += "<td>"+data.data.list[i].name+"</td>";
                    html += data.data.list[i].attr == null ? "<td>暂无属性</td>" : "<td>"+data.data.list[i].attr+"</td>";
                    html += "<td>";
                    html += "<shiro:hasPermission name='BTN_PRODUCT_SKU_RELEVANCE'>";
                    html += "<input type='button' value='关联属性' onclick='toAttr(&quot;"+data.data.list[i].code+"&quot;)'/>";
                    html += "</shiro:hasPermission>";
                    html += "</td>";
                    html += "</tr>";
                }
                $("#tbd").html(html);

            });
    }

    function toAttr(code) {
        layer.open({
            type: 2,
            title: '关联属性页面',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '80%'],
            content: '<%=request.getContextPath()%>/dict/product_sku/toAttr/' + code
        });
    }

    <%--function toAttr(productId) {--%>

    <%--    window.location.href= "<%=request.getContextPath()%>/dict/product_sku/toAttr/"+productId;--%>
    <%--}--%>


</script>
<body>

<form id="fm">


    <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
        <tr>
            <td>编号</td>
            <td>商品类型</td>
            <td>属性名</td>
            <td>操作</td>
        </tr>

        <tbody id = "tbd">

        </tbody>

    </table>

</form>
</body>
</html>
