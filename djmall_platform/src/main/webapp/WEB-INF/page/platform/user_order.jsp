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



    </script>
</head>
<body>
<form id = "fm">

    收货人信息
    <select name="siteId">
        <c:forEach items="${userSiteList}" var="site">
            <option value="${site.siteId}">${site.consignee}-${site.phone}-${site.provinceShow}${site.cityShow}${site.countyShow}${site.site}</option>
        </c:forEach>
    </select>

    <div id = "tbd"></div>

    支付方式
    <select name="payType">
        <c:forEach items="${baseDataList}" var="b">
            <option value="${b.code}">${b.name}</option>
        </c:forEach>
    </select><br>
    <input type="button" value="提交订单" onclick="commitOrder()">
    <input type="button" value="取消订单" onclick="cancelOrder()">

</form>
</body>
</html>
