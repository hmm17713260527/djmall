<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/19
  Time: 17:45
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
    <script type="text/javascript">

        $(function () {
            findOrder(1);
        });

        function findOrder(a) {
            var s = "待支付";

            var buyerId = cookie.get("USER_ID");
            var pageNo = $("#pageNo_"+s).val();
            token_post(
                "<%=request.getContextPath()%>/order/auth/findOrderList?TOKEN="+getToken(),
                {"orderStatus": s, "buyerId": buyerId, "pageNo" : pageNo},
                function (data) {
                    if (data.data == null) {
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        var o = data.data.list[i];
                        html += "<tr>";
                        html += "<td>";
                        html += "<a href='javascript:toOrderShow(&quot;"+o.orderNo+"&quot;)'>"+o.orderNo+"</a>";
                        html += "</td>";
                        html += "<td>"+o.productName+"</td>";
                        html += "<td>"+o.totalBuyCount+"</td>";
                        html += "<td>￥"+o.totalPayMoney+"</td>";
                        html += "<td>"+o.name+"</td>";
                        html += o.totalFreight == '0.00' ? "<td>包邮</td>" : "<td>平邮-"+o.totalFreight+"元</td>";
                        html += "<td>"+o.createTime+"</td>";
                        html += "<td>";
                        html += "待支付,  ";
                        html += "<a href='javascript:updateOrderStatus(&quot;"+o.orderNo+"&quot;, &quot;"+'待发货'+"&quot;)'>去支付</a>，";
                        html += "<a href='javascript:updateOrderStatus(&quot;"+o.orderNo+"&quot;, &quot;"+'已取消'+"&quot;)'>取消订单</a>";
                        html += "</td>";
                        html += "</tr>";
                    }
                    $("#"+s).append(html);

                    var pageNo = $("#pageNo_"+s).val();
                    var pageHtml = "<input type='button' id = '"+s+"' value='加载更多' onclick='page("+data.data.pages+", "+(parseInt(pageNo) + 1)+","+a+")'>";
                    $("#pageInfo_"+s).html(pageHtml);

                }
            )
        }

        function page(totalNum, page, a) {
            var i = "";
            if (a == 1) {
                i = "待支付";
            } else if (a == 2) {
                i = "待收货";
            } else if (a == 3) {
                i = "已完成";
            } else {
                i = "已取消";
            }

            if (page > totalNum) {
                $("#"+i).val("我是有限度的！！！");
            } else {
                $("#pageNo_"+i).val(page);
                if (a == 1) {
                    findOrder(a);
                } else {
                    findOrderDetail(a);
                }

            }

        }


        function toOrderShow(orderNo) {
            layer.open({
                type: 2,
                title: '订单详情页面',
                shadeClose: true,
                shade: 0.8,
                area: ['380px', '80%'],
                content: '<%=request.getContextPath()%>/order/auth/toOrderShow?TOKEN='+getToken()+'&orderNo='+orderNo
            });
        }

        function findOrderDetail(a) {
            var s = "待支付";
            if (a == 1) {
                s = "待支付";
            } else if (a == 2) {
                s = "待收货";
            } else if (a == 3) {
                s = "已完成";
            } else {
                s = "已取消";
            }
            var buyerId = cookie.get("USER_ID");
            var pageNo = $("#pageNo_"+s).val();

            token_post(
                "<%=request.getContextPath()%>/order/auth/findOrderDetailList?TOKEN="+getToken(),
                {"orderStatus": s, "buyerId": buyerId, "pageNo" : pageNo},
                function (data) {
                    if (data.data == null) {
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        var o = data.data.list[i];
                        html += "<tr>";
                        html += "<td>";
                        html += "<a href='javascript:toOrderDetailShow(&quot;"+o.childOrderNo+"&quot;)'>"+o.childOrderNo+"</a>";
                        html += "</td>";
                        html += "<td>"+o.productName+":"+o.skuInfo+"</td>";
                        html += "<td>"+o.buyCount+"</td>";
                        html += o.skuRate == '0.00'?"<td>无</td>":"<td>"+o.skuRate+"%</td>";
                        html += "<td>￥"+o.payMoney+"</td>";
                        html += "<td>"+o.name+"</td>";
                        html += o.totalFreight == '0.00' ? "<td>包邮</td>" : "<td>平邮-"+o.totalFreight+"元</td>";
                        html += "<td>"+o.createTime+"</td>";
                        if (s == '已取消') {
                            html += "<td>"+o.updateTime+"</td>";
                        } else {
                            html += "<td>"+o.payTime+"</td>";
                        }

                        if (s == '待收货') {
                            if (o.orderStatus == '待发货') {
                                html += "<td>";
                                html += "<a href='javascript:updateOrderStatus(&quot;"+o.childOrderNo+"&quot;, &quot;"+'已发货'+"&quot;)'>提醒卖家发货</a>";
                                html += "</td>";
                            } else if (o.orderStatus == '已发货') {
                                html += "<td>已提醒</td>";
                            } else {
                                html += "<td>";
                                html += "<a href='javascript:updateOrderStatus(&quot;"+o.childOrderNo+"&quot;, &quot;"+'已完成'+"&quot;)'>确认收货</a>";
                                html += "</td>";
                            }
                        } else {
                            html += "<td><a href = '/platform/toProductShow?productSpuId="+o.productId+"&productSkuId="+o.skuId+"'>再次购买</a></td>"
                        }

                        html += "</tr>";
                    }
                    $("#"+s).append(html);
                    var pageNo = $("#pageNo_"+s).val();
                    var pageHtml = "<input type='button' id = '"+s+"' value='加载更多' onclick='page("+data.data.pages+", "+(parseInt(pageNo) + 1)+","+a+")'>";
                    $("#pageInfo_"+s).html(pageHtml);
                }
            )
        }

        function toOrderDetailShow(childOrderNo) {
            layer.open({
                type: 2,
                title: '订单详情页面',
                shadeClose: true,
                shade: 0.8,
                area: ['380px', '80%'],
                content: '<%=request.getContextPath()%>/order/auth/toOrderShow?TOKEN='+getToken()+'&childOrderNo='+childOrderNo
            });
        }


        function updateOrderStatus(orderNo, orderStatus) {
            token_post(
                "<%=request.getContextPath()%>/order/auth/updateOrderStatus?TOKEN="+getToken(),
                {"orderStatus": orderStatus, "orderNo": orderNo},
                function (data) {
                    window.location.reload();
                }
            )
        }


    </script>
</head>
<body>

<div class="layui-tab">
    <ul class="layui-tab-title" lay-filte="order">
        <li class="layui-this" onclick="findOrder(this.value)" value="1">待支付</li>
        <li onclick="findOrderDetail(this.value)" value="2">待收货</li>
        <li onclick="findOrderDetail(this.value)" value="3">已完成</li>
        <li onclick="findOrderDetail(this.value)" value="4">已取消</li>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <input type="hidden" value="1" id="pageNo_待支付" name="pageNo">
            <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
                <tr align="center">
                    <td>订单号</td>
                    <td>商品名称</td>
                    <td>购买数量</td>
                    <td>付款金额（包括邮费）</td>
                    <td>支付方式</td>
                    <td>邮费</td>
                    <td>下单时间</td>
                    <td>订单状态</td>
                </tr>
                <tbody id="待支付">

                </tbody>
            </table>
            <div id="pageInfo_待支付">

            </div>

        </div>


        <div class="layui-tab-item">
            <input type="hidden" value="1" id="pageNo_待收货" name="pageNo">
            <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
                <tr align="center">
                    <td>订单号</td>
                    <td>商品信息</td>
                    <td>购买数量</td>
                    <td>折扣</td>
                    <td>付款金额（包含邮费）</td>
                    <td>支付方式</td>
                    <td>邮费</td>
                    <td>下单时间</td>
                    <td>付款时间</td>
                    <td>订单状态</td>
                </tr>
                <tbody id="待收货">

                </tbody>
            </table>
            <div id="pageInfo_待收货">

            </div>
        </div>


        <div class="layui-tab-item">
            <input type="hidden" value="1" id="pageNo_已完成" name="pageNo">
            <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
                <tr align="center">
                    <td>订单号</td>
                    <td>商品名称</td>
                    <td>购买数量</td>
                    <td>折扣</td>
                    <td>付款金额（包含邮费）</td>
                    <td>支付方式</td>
                    <td>邮费</td>
                    <td>下单时间</td>
                    <td>付款时间</td>
                    <td>订单状态</td>
                </tr>
                <tbody id="已完成">

                </tbody>
            </table>
            <div id="pageInfo_已完成">

            </div>
        </div>


        <div class="layui-tab-item">
            <input type="hidden" value="1" id="pageNo_已取消" name="pageNo">
            <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
                <tr align="center">
                    <td>订单号</td>
                    <td>商品名称</td>
                    <td>购买数量</td>
                    <td>折扣</td>
                    <td>付款金额（包含邮费）</td>
                    <td>支付方式</td>
                    <td>邮费</td>
                    <td>下单时间</td>
                    <td>取消时间</td>
                    <td>订单状态</td>
                </tr>
                <tbody id="已取消">

                </tbody>
            </table>
            <div id="pageInfo_已取消">

            </div>
        </div>
    </div>
</div>

</body>
</html>
