<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/28
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
    <script src="<%=request.getContextPath()%>\static\js\jquery.validate.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    <script type="text/javascript">

        function update() {
            var index = layer.load(0, {shade:0.5});
            $.post(
                "<%=request.getContextPath()%>/product/sku/update",
                $("#fm").serialize(),
                function(data){
                    layer.close(index);
                    layer.msg(data.msg, function(){
                        if (data.code != 200) {
                            return;
                        }
                        parent.window.location.href = "<%=request.getContextPath()%>/product/spu/toShow";
                    });

                })
        }


    </script>
</head>
<body>

<form id = "fm">
    <input type="hidden" name = "_method" value = "PUT">
    <input type="hidden" name = "productSkuId" value="${product.productSkuId}"/>
    SKU属性:${product.skuAttrNames}"<br/>
    库存:<input type = "text" name = "skuCount" value="${product.skuCount}"/><br/>
    价格:<input type = "text" name = "skuPrice" value="${product.skuPrice}"/><br/>
    折扣:<input type = "text" name = "skuRate" value="${product.skuRate}"/><br/>
    <input type="button" value="修改" onclick="update()"/>
</form>

</body>
</html>
