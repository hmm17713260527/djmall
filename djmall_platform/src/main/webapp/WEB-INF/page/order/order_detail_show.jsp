<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/20
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>\static\layui-v2.5.5\layui\css\layui.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jsencrypt.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layui-v2.5.5\layui\layui.all.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\md5-min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\cookie.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\slideVerify\js\jq-slideVerify.js" charset="utf-8"></script>
</head>
<body>
<form>

    订单编号:${order.orderNo}<br/>
    收货信息:${order.receiverName}-${order.receiverPhone}<br/>
    地址:${order.receiverProvince}-${order.receiverCity}-${order.receiverCounty}-${order.receiverDetail}<br/>
    商品信息:
    <table>
        <tr>
            <td>编号</td>
            <td>商品信息</td>
            <td>数量</td>
            <td>实际金额</td>
            <td>折扣</td>
        </tr>

<%--        <c:if test="${order.parentOrderNo == null}">--%>
            <c:forEach var="orderDetail" items="${order.orderDetailList}" varStatus="i">
                <tr>
                    <td>${i.index + 1}</td>
                    <td>${orderDetail.productName}-${orderDetail.skuInfo}</td>
                    <td>${orderDetail.buyCount}</td>
                    <td>${orderDetail.skuPrice}</td>
                    <td>
                        <c:if test="${orderDetail.skuRate == 0}">
                            无
                        </c:if>
                        <c:if test="${orderDetail.skuRate != 0}">
                            ${orderDetail.skuRate}%
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
<%--        </c:if>--%>
<%--        <c:if test="${order.parentOrderNo != null}">--%>
<%--            <tr>--%>
<%--                <td>1</td>--%>
<%--                <td>${order.productName}-${order.skuIfo}</td>--%>
<%--                <td>${order.buyCount}</td>--%>
<%--                <td>${order.skuPrice}</td>--%>
<%--                <td>--%>
<%--                    <c:if test="${order.skuRate == 0}">--%>
<%--                        无--%>
<%--                    </c:if>--%>
<%--                    <c:if test="${order.skuRate != 0}">--%>
<%--                        ${order.skuRate}%--%>
<%--                    </c:if>--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--        </c:if>--%>


    </table><br/>

    下单时间:${order.createTime}<br/>
    支付方式:${order.name}<br/>
    支付时间:
    <c:if test="${order.payTime != null}">
        ${order.payTime}<br/>
    </c:if>
    <c:if test="${order.payTime == null}">
        暂无<br/>
    </c:if>

    商品总金额:${order.totalMoney}<br/>
    运费:${order.totalFreight}<br/>
    实付金额:${order.totalPayMoney}<br/>

</form>


</body>
</html>
