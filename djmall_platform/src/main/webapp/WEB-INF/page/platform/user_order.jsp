<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/7
  Time: 17:02
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
            token_post("<%=request.getContextPath() %>/platform/auth/userShoppingShow?TOKEN=" + getToken(),
                {"ids" : cookie.get("IDS"), "userShoppingId" : '${id}'},
                function(data){
                    layer.close(index);
                    if (data.code != 200) {
                        layer.msg(data.msg);
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.length; i++) {
                        html += '<fieldset style="width: 450px">';
                        html += '<legend>商品信息</legend>';
                        html += '<table>';
                        html += '<tr>';
                        html += '<td>';
                        html += '<input type="hidden" name = "ids" value = '+data.data[i].userShoppingId+'>';
                        html += '<input type="hidden" name = "productNames" value = '+data.data[i].productName+'>';

                        // //info
                        // //商户ID
                        // html += '<input type="hidden" name = "infoList['+i+'].businessId" value = '+data.data[i].buyerId+'>';
                        // //商品ID
                        // html += '<input type="hidden" name = "infoList['+i+'].productId" value = '+data.data[i].productSpuId+'>';
                        // //商品sku_id
                        // html += '<input type="hidden" name = "infoList['+i+'].productSkuId" value = '+data.data[i].productSkuId+'>';
                        // //订单总金额
                        // html += '<input type="hidden" name = "infoList['+i+'].skuPrice" value = '+data.data[i].skuPrice+'>';
                        // //实付总金额
                        // html += '<input type="hidden" name = "infoList['+i+'].ratePrice" value = '+data.data[i].ratePrice+'>';
                        // //总运费
                        // if (data.data[i].freight == '包邮') {
                        //     html += '<input type="hidden" name = "infoList['+i+'].totalFreight" value = '+0.00+'>';
                        // } else {
                        //     html += '<input type="hidden" name = "infoList['+i+'].totalFreight" value = '+data.data[i].freight+'>';
                        // }
                        // //总购买数量
                        // html += '<input type="hidden" name = "infoList['+i+'].totalBuyCount" value = '+data.data[i].productCount+'>';
                        //
                        // //detail
                        // //属性值
                        // html += '<input type="hidden" name = "infoList['+i+'].skuInfo" value = '+data.data[i].skuAttrValueNames+'>';
                        // //折扣
                        // html += '<input type="hidden" name = "infoList['+i+'].skuRate" value = '+data.data[i].skuRate+'>';

                        html += "商品名:"+data.data[i].productName+"";
                        html += '</td>';
                        html += "<td>原价:"+data.data[i].skuPriceShow+"</td>";
                        html += "<td>数量:x"+data.data[i].productCount+"</td>";
                        html += '</tr>';

                        html += '<tr>';
                        html += "<td>"+data.data[i].skuAttrValueNames+"</td>";
                        html += "<td>折扣:"+data.data[i].skuRateShow+",现价<span style='color: red'>"+data.data[i].ratePriceShow+"<span/></td>";
                        html += "<td>邮费："+data.data[i].freight+"</td>";
                        html += '</tr>';
                        html += '</table>';
                        html += '</fieldset><br>';

                    }
                    $("#tbd").html(html);

                });
        }

        function commitOrder() {
            // if (val == 1) {
            //     $("#orderStatus").val("待支付");
            // }
            // if (val == 2) {
            //     $("#orderStatus").val("已取消");
            // }
            $("#orderStatus").val("待支付");

            var userId = cookie.get("USER_ID");
            $("#userId").val(userId);
            token_post("<%=request.getContextPath()%>/order/auth/commitOrder?TOKEN="+getToken(),
                $("#fm").serialize(),
                function(data){
                if (data != null) {
                    $("#aliPayDiv").append(data);
                    return;
                }

                    window.location.href = "<%=request.getContextPath()%>/platform/auth/toIndex?TOKEN=" + getToken()

                    <%--if(data.code == -1){--%>
                    <%--    layer.close(index);--%>
                    <%--    layer.msg(data.msg, {icon: 5});--%>
                    <%--    return--%>
                    <%--}--%>
                    <%--window.location.href = "<%=request.getContextPath()%>/platform/auth/toIndex?TOKEN=" + getToken()--%>
                }
            )
        }

        function toFindUserShopping() {
            window.location.href = "<%=request.getContextPath()%>/platform/auth/toFindUserShopping?TOKEN=" + getToken();
        }

        function toAdd() {
            layer.open({
                type: 2,
                title: '新增地址页面',
                shadeClose: true,
                shade: 0.8,
                area: ['380px', '80%'],
                content: '<%=request.getContextPath()%>/platform/auth/toAdd?TOKEN='+getToken()
            });
        }

    </script>
</head>
<body>
<div id="aliPayDiv"></div>
<form id = "fm">

    收货人信息
    <select name="siteId">
        <c:forEach items="${userSiteList}" var="site">
            <option value="${site.siteId}">${site.consignee}-${site.phone}-${site.provinceShow}${site.cityShow}${site.countyShow}${site.site}</option>
        </c:forEach>
    </select>
    <a href="javascript:toAdd()">点我，收货地址+1</a><br>
    <div id = "tbd"></div>
    支付方式
    <select name="payType">
        <c:forEach items="${baseDataList}" var="b">
            <option value="${b.code}">${b.name}</option>
        </c:forEach>
    </select><br>
    已选择
    <input type="hidden" name="totalBuyCount" value="${orderMessage.sumNumber}"/><span style="color: red">${orderMessage.sumNumber}</span>件商品，
    总商品金额：
    <input type="hidden" name="totalMoney" value="${orderMessage.oldPrice}"/>￥<span>${orderMessage.oldPrice}</span>，
    商品折后金额：￥<span>${orderMessage.ratePrice}</span>，
    运费：
    <input type="hidden" name="totalFreight" value="${orderMessage.freightPrice}">￥<span>${orderMessage.freightPrice}</span><br>
    应付总金额：
    <input type="hidden" name="totalPayMoney" value="${orderMessage.sumPrice}"/><span style="color: red">${orderMessage.sumPrice}</span><br/>
    <input type="hidden" id="orderStatus" name="orderStatus"/>
    <input type="hidden" id="userId" name="buyerId"/>
    <input type="button" value="提交订单" onclick="commitOrder()">
    <input type="button" value="取消订单" onclick="toFindUserShopping()">

</form>
</body>
</html>
