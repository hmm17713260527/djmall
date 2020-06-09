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
    <link rel="stylesheet" href="<%=request.getContextPath()%>\static\layui-v2.5.5\layui\css\layui.css"  media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layui-v2.5.5\layui\layui.js"></script>
    <script src="<%=request.getContextPath()%>\static\js\jquery.validate.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\cookie.js"></script>
    <script type="text/javascript">


        $(function() {
            var skuCount = ${product.skuCount};

            findCount(skuCount);


            search();

            findReq();

        });


        function search() {
            var index = layer.load(0, {shade:0.5});
            token_post("<%=request.getContextPath()%>/plat/comment/show",
                $("#fr").serialize(),
                function(data){
                    layer.close(index);



                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        html +="<div style='border:solid;height: 130px;width: 400px'>";
                        // html +="<div class='lf_rate' lay-data='{value:"+data.data.list[i].commentType+",half:true, readonly:true,theme: '#F70808'}' style='margin:0px, 160px'></div><br/>";
                        html +="<table>";
                        html +="<div id='rate_"+i+"'><div/><br/>";

                        html +="<tr>";
                        html +="<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+data.data.list[i].userName+"</td>";
                        html += "<td colspan='2'>"+data.data.list[i].comment+"</td>";
                        html +="</tr>";

                        html +="<tr align='center'>";
                        html +="<td>"+data.data.list[i].createTime+"</td>";
                        html +="<td></td>";
                        html +="<td>";
                        html +="</td>";
                        html +="</tr>";
                        if (data.data.list[i].commentDTOResp != null) {
                            html +="<tr>";
                            html +="<td style='color:red;'>商家回复：</td>";
                            html += "<td colspan='2' style='color: red'>"+data.data.list[i].commentDTOResp.comment+"</td>";
                            html +="</tr>";

                            html +="<tr align='center'>";
                            html +="<td>"+data.data.list[i].commentDTOResp.createTime+"</td>";
                            html +="<td></td>";
                            html +="<td>";
                            html +="</td>";
                            html +="</tr>";

                        }
                        html +="</table><br/>";
                        html +="</div>";


                    }
                    $("#tbd").append(html);


                    var pageNo = $("#pageNo").val();
                    var pageHtml = "<input type='button' id='page' value='加载更多' onclick='page("+data.data.pages+", "+(parseInt(pageNo) + 1)+")'>";
                    $("#pageInfo").html(pageHtml);

                    layui.use('rate', function(){
                        var rate = layui.rate;

                        for (var i = 0; i < data.data.list.length; i++) {
                            //渲染
                            rate.render({
                                elem: '#rate_'+i  //绑定元素
                                ,value: data.data.list[i].commentType
                                ,readonly: true
                            });
                        }

                    });


                    // layui.use(['rate','jquery'], function(){
                    //     var rate = layui.rate;
                    //     var $ = layui.jquery;
                    //     //多个评分
                    //     layui.each( $('.lf_rate'), function (index, elem) {
                    //         var configTemp = $(elem).attr('lay-data');
                    //         try{
                    //             configTemp = eval('(' + configTemp + ')');
                    //         }catch(e){
                    //             configTemp  = {};
                    //         }
                    //         rate.render($.extend(true,{
                    //             elem:elem
                    //         }, configTemp));
                    //     });
                    //
                    // });

                });
        }

        function findReq() {
            var productId = $("#proId").val();
            $.get(
                "<%=request.getContextPath()%>/plat/comment/findReq",
                {"productId": productId},
                function (data) {
                    $("#rep").html(data.data + "%");
                }
            )
        }


        function page(totalNum, page) {

            if (page > totalNum) {
                $("#page").val("我是有限度的！！！");
                return;
            }
            $("#pageNo").val(page);
            search();
            findReq();
        }

        function searchs() {
            $("#pageNo").val(1);
            $("#tbd").empty();

            search();
        }




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
                    $("#skuCount").val(data.data.skuCount);

                    findCount(data.data.skuCount);
                }
            )
        }

        function jsNumber(a) {
            var count = $("#skuCount").val();
            if (a == 2) {

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
            findCount(count);
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


        function jisuan() {
            //个数
            var sumNumber = $("#number").val();
            //原价
            var skuPrice = $("#skuPrice").val();
            //折扣
            var rate = $("#rate").val();
            //邮费
            var freight;
            if ('${product.freight}' == '包邮') {
                freight = 0;
            } else {
                freight = ${product.freight};
            }

            $("#sumNumber").val(sumNumber);
            $("#oldPrice").val(sumNumber * skuPrice);
            $("#ratePrice").val(rate / 100 * skuPrice * sumNumber);
            $("#freightPrice").val(freight);
            $("#sumPrice").val(rate / 100 * skuPrice * sumNumber + freight);

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
                    jisuan();
                    window.location.href = "<%=request.getContextPath()%>/platform/auth/toUserOrder?TOKEN="+getToken()+"&id="+data.data+"&orderMessage="+$("#frm").serialize()+"&userId="+userId;
                }
            )
        }

    </script>
</head>
<body>

<form id="frm">
    <input type="hidden" name="noExist">
    <input type="hidden" name="sumNumber" id="sumNumber">
    <input type="hidden" name="oldPrice" id="oldPrice">
    <input type="hidden" name="ratePrice" id="ratePrice">
    <input type="hidden" name="freightPrice" id="freightPrice">
    <input type="hidden" name="sumPrice" id="sumPrice">

</form>

<form id="fm">
    <input type="hidden" id="userId" name="userId">
    <input type="hidden" name="productSpuId" value="${product.productId}">
    <input type="hidden" name="isDel" value="1">
<%--    <input type="hidden" id="skuCount">--%>
    <input type="hidden" id="skuPrice" name="skuPrice" value="${product.skuPrice}">
    <input type="hidden" id = "rate" name="skuRate" value="${product.skuRate}">
    <img src="http://qazo01v5q.bkt.clouddn.com/${product.img}" width="100px" height="100px"><br>
    名称:${product.productName}&nbsp;
    原价:<span id="skuPrice">${product.skuPrice}</span><br>
    折扣:
    <span id="skuRate">${product.skuRateShow}</span>
    邮费:${product.freight}<br>
    商品描述:<span id="productDescribe">${product.productDescribe}</span><br>
    点赞量:${product.praise}&nbsp;评论量:${product.productOrder}<br>
    选择商品系信息
    <c:forEach items="${product.productSkuList}" var="p">
        <input type="radio" name="productSkuId" value="${p.productSkuId}"<c:if test="${p.productSkuId == product.productSkuId}">checked</c:if> onclick="getProSku(this.value)">${p.skuAttrValueNames}
    </c:forEach><br>
    <input type="hidden" id="skuCount" name="skuCount">
    购买数量:
    <input type="button" value="-" onclick="jsNumber(1)">
    <input type="text" value="1" name="productCount" id="number">
    <input type="button" value="+" onclick="jsNumber(2)"><br>
    <span id="count" style="color: red"></span><br>
    <input type="button" value="加入购物车" onclick="addUserShopping()">
    <input type="button" value="立即购买" onclick="userShopping()">
</form>


<h2>好评率：<span id="rep" style="color: red"></span></h2>
<form id="fr">
    <input type="radio" name="commentType" value="0" onclick="searchs()" checked/>所有评论
    <input type="radio" name="commentType" value="1" onclick="searchs()" />好评
    <input type="radio" name="commentType" value="2" onclick="searchs()"/>中评
    <input type="radio" name="commentType" value="3" onclick="searchs()"/>差评
    <input type="hidden" value="1" id="pageNo" name="pageNo">
    <input type="hidden" id="proId" name="productId" value="${product.productId}">

</form>
<div id="tbd"></div>
<div id="pageInfo">

</div>



</body>
</html>
