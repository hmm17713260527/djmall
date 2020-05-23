<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/22
  Time: 15:54
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
        });


        function search() {
            var index = layer.load(0, {shade:0.5});
            var roleId = ${user.roleId}
            $.get("<%=request.getContextPath() %>/auth/order/show",
                $("#fm").serialize(),
                function(data){
                    layer.close(index);
                    if (data.code != 200) {
                        layer.msg(data.msg);
                        return;
                    }

                    var html = "";
                    for (var i = 0; i < data.data.length; i++) {
                        html += "<tr>";
                        html += "<td>"+data.data[i].orderNo+"</td>";
                        html += "<td>"+data.data[i].productName+"</td>";
                        if (roleId == 2) {
                            html += "<td>"+data.data[i].userName+"</td>";
                        }
                        html += "<td>"+data.data[i].totalBuyCount+"</td>";
                        if (data.data[i].skuRate == 0.00 || data.data[i].skuRate == 100.00) {
                            html += "<td>无</td>";
                        } else {
                            html += "<td>"+data.data[i].skuRate+"%</td>";
                        }
                        html += "<td>￥"+data.data[i].payMoney+"</td>";
                        html += "<td>"+data.data[i].name+"</td>";
                        html += data.data[i].totalFreight == 0.00 ? "<td>包邮</td>" : "<td>"+data.data[i].totalFreight+"元</td>";

                        html += "<td>"+data.data[i].receiverName+"-"+data.data[i].receiverPhone+"-"+data.data[i].receiverProvince+"-"+data.data[i].receiverCity+"-"+data.data[i].receiverCounty+"-"+data.data[i].receiverDetail+"</td>";
                        html += "<td>"+data.data[i].receiverName+"</td>";
                        html += "<td>"+data.data[i].receiverPhone+"</td>";
                        html += "<td>"+data.data[i].createTime+"</td>";
                        html += data.data[i].payTime == null ? "<td>暂无</td>" : "<td>"+data.data[i].payTime+"</td>";
                        if (roleId == 1) {
                            if (data.data[i].orderStatus == '已发货') {
                                html += "<td><span id='status_"+i+"'>买家提醒赶紧发货了，<a href='javascript:updateOrderCode("+i+")'>我知道了</a></span></td>";
                            } else {
                                html += "<td>无消息处理</td>";
                            }
                        }

                        if (data.data[i].orderStatus == '已取消') {
                            html += "<td>已取消</td>";
                        } else if (data.data[i].orderStatus == '已完成') {
                            html += "<td>已完成</td>";
                        } else if (data.data[i].orderStatus == '待支付') {
                            html += "<td>待支付</td>";
                        } else if (data.data[i].orderStatus == '待发货' || data.data[i].orderStatus == '已发货') {
                            if (roleId == 1) {
                                html += "<td>待发货，<a href='javascript:updateOrderStatus(&quot;"+data.data[i].orderNo+"&quot;)'>去发货</a></td>";
                            } else {
                                html += "<td>待发货</td>";
                            }
                        } else {
                            html += "<td>待收货</td>";
                        }
                        html += "</tr>";
                    }
                    $("#tbd").html(html);
                });
        }


        function updateOrderStatus(orderNo) {
            $.post(
            "<%=request.getContextPath()%>/auth/order/updateOrderStatus",
                {"orderStatus": '确认收货', "orderNo": orderNo},
                function (data) {
                    window.location.reload();
                }
            )
        }

        function updateOrderCode(i) {
            $("#status_" + i).html("无消息处理");
        }
    </script>
</head>
<body>

<form id="fm">

    <input type="hidden" name="userId" value="${user.userId}"/>
    <input type="hidden" name="roleId" value="${user.roleId}"/>

</form>

    <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
        <tr>
            <th>订单号</th>
            <th>商品名称</th>
            <c:if test="${user.roleId == 2}">
                <th>商家</th>
            </c:if>
            <th>购买数量</th>
            <th>折扣</th>
            <th>付款金额（包含邮费）</th>
            <th>支付方式</th>
            <th>邮费</th>
            <th>收货人信息</th>
            <th>下单人</th>
            <th>下单人电话</th>
            <th>下单时间</th>
            <th>付款时间</th>
            <c:if test="${user.roleId == 1}">
                <th>买家信息</th>
            </c:if>
            <th>订单状态</th>
        </tr>
        <tbody id = "tbd">

        </tbody>

    </table>


</body>
</html>
