<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/1
  Time: 21:57
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
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\cookie.js"></script>
    <script type="text/javascript">


        $(function() {
            var skuCount = ${product.skuCount};

            findCount(skuCount);
        });

        function findCount(count) {
            $("#skuCount").val(count);
            if (count > 0) {
                $("#count").html("有货")
            } else {
                $("#count").html("无货")
            }
        }

        function getProSku(productSkuId) {
            $.get(
                "<%=request.getContextPath()%>/platform/getProductSkuById",
                {"productSkuId":productSkuId},
                function(data) {
                    $("#skuPrice").html(data.data.skuPrice);
                    $("#skuRate").html(data.data.skuRateShow);
                    $("#rate").html(data.data.skuRate);
                    $("#productDescribe").html(data.data.productDescribe);

                    findCount(data.data.skuCount);
                }
            )
        }

        function jsNumber(a) {
            if (a == 2) {
                var count = ${product.skuCount};
                var addNumber = parseInt($("#number").val()) + 1;
                if (addNumber > 200) {
                    layer.msg("不能超过200", {icon: 5});
                    return;
                }
                if (addNumber > count) {
                    $("#count").html("库存不足")
                    return;
                }
                $("#number").val(parseInt($("#number").val()) + 1);
            }
            if (a == 1) {
                var minusNumber = parseInt($("#number").val());
                if (minusNumber <= 1) {
                    layer.msg("购买不能小于0件", {icon: 5});
                    return;
                }
                $("#number").val(parseInt($("#number").val()) - 1);
            }

        }



        function addUserShopping() {

            if ($("#skuCount").val() == 0) {
                layer.msg("无货");
                return;
            }
            var index = layer.load(1,{shade:0.5});
            var userId = cookie.get("USER_ID");
            $("#userId").val(userId);
            token_post("<%=request.getContextPath() %>/platform/auth/addUserShopping?TOKEN=" + getToken(),
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
                        window.location.href = "<%=request.getContextPath()%>/platform/toShow";
                    });
                }
            )
        }


        function userShopping() {
            if ($("#skuCount").val() == 0) {
                layer.msg("无货");
                return;
            }

            var index = layer.load(1,{shade:0.5});
            var userId = cookie.get("USER_ID");
            $("#userId").val(userId);
            token_post("<%=request.getContextPath() %>/platform/auth/addUserShopping?TOKEN=" + getToken(),
                $("#fm").serialize(),
                function(data){
                    layer.close(index);
                    window.location.href = "<%=request.getContextPath()%>/platform/auth/toUserOrder?TOKEN="+getToken()+"&id="+data.data+"&userId="+userId;
                }
            )
        }

    </script>
</head>
<body>

<form id="fm">
    <input type="hidden" id="userId" name="userId">
    <input type="hidden" name="productSpuId" value="${product.productId}">
    <input type="hidden" name="isDel" value="1">
    <input type="hidden" id="skuCount">
    <input type="hidden" name="skuPrice" value="${product.skuPrice}">
    <input type="hidden" id = "rate" name="skuRate" value="${product.skuRate}">
    <img src="http://q9cgmldxi.bkt.clouddn.com/${product.img}" width="100px" height="100px"><br>
    名称:${product.productName}&nbsp;
    原价:<span id="skuPrice">${product.skuPrice}</span><br>
    折扣:
    <span id="skuRate">${product.skuRateShow}</span>
    邮费:${product.freight}<br>
    商品描述:<span id="productDescribe">${product.productDescribe}</span><br>
    点赞量:${product.praise}&nbsp;评论量:0<br>
    选择商品系信息
    <c:forEach items="${product.productSkuList}" var="p">
        <input type="radio" name="productSkuId" value="${p.productSkuId}"<c:if test="${p.isDefault == 0}">checked</c:if> onclick="getProSku(this.value)">${p.skuAttrValueNames}
    </c:forEach><br>
    <input type="hidden" name="skuCount" value="${product.skuCount}">
    购买数量:
    <input type="button" value="-" onclick="jsNumber(1)">
    <input type="text" value="1" name="productCount" id="number">
    <input type="button" value="+" onclick="jsNumber(2)"><br>
    <span id="count" style="color: red"></span><br>
    <input type="button" value="加入购物车" onclick="addUserShopping()">
    <input type="button" value="立即购买" onclick="userShopping()">
</form>

</body>
</html>
