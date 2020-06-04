<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/4
  Time: 15:32
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


        function updateCountById(id, count) {
            token_post(
                "<%=request.getContextPath() %>/platform/auth/updateCountById?TOKEN=" + getToken(),
                {"userShoppingId" : id, "productCount" : count},
                function (data) {
                    if (data.code != 200) {
                        layer.msg(data.msg);
                        return;
                    }
                    jisuan();
                })
        }

        function updById(id) {
            token_post(
                "<%=request.getContextPath() %>/platform/auth/updById?TOKEN=" + getToken(),
                {"userShoppingId" : id, "isDel" : 0},
                function (data) {
                    if (data.code != 200) {
                        layer.msg(data.msg);
                        return;
                    }
                    jisuan();
            })
        }

        // 全选
        function checkShopCar(obj) {
            if (obj.checked) {
                $(".c").prop("checked", true);
                updByIds();
            } else {
                $(".c").prop("checked", false);
                updByIds();
            }
            jisuan();
        }

        function updByIds() {
            var length = $("input[name='ids']:checked").length;
            if(length <= 0){
                layer.msg('请至少选择一个!', {icon:0});
                return;
            }
            var str = "";
            $("input[name='ids']:checked").each(function (index, item) {
                if ($("input[name='ids']:checked").length-1==index) {
                    str += $(this).val();
                } else {
                    str += $(this).val() + ",";
                }
            });
            token_post(
                "<%=request.getContextPath() %>/platform/auth/updByIds?TOKEN=" + getToken(),
                {"ids" : str, "isDel" : 0},
                function (data) {
                    if (data.code != 200) {
                        layer.msg(data.msg);
                        return;
                    }
                })
        }

        function jiaNumber(i, id) {

            var productCount = parseInt($("#number_" + i).val()) + 1;
            if (productCount > 200) {
                layer.msg("不能超过200", {icon: 5});
                return;
            }
            var count = parseInt($("#skuCount_" + i).val());
            if (productCount > count) {
                layer.msg("库存不足", {icon: 5});
                $("#number_" + i).val(parseInt($("#skuCount_" + i).val()));
                return;
            }
            $("#number_" + i).val(parseInt($("#number_" + i).val()) + 1);
            updateCountById(id, $("#number_" + i).val());


        }

        function jianNumber(i, id) {
            var productCount = parseInt($("#number_" + i).val());
            if (productCount <= 1) {
                layer.msg("购买不能小于0件", {icon: 5});
                return;
            }
            $("#number_" + i).val(parseInt($("#number_" + i).val()) - 1);
            updateCountById(id, $("#number_" + i).val());
        }

        function chang(skuCount, i) {
            if ($("#number_"+i).val() > skuCount) {
                layer.msg("库存不足", {icon: 5});
                $("#number_"+i).val($("#skuCount_"+i).val())
                return;
            }
            if ($("#number_"+i).val() > 200) {
                layer.msg("不能超过200", {icon: 5});
                return;
            }
            jisuan();
        }

        function search() {
            var index = layer.load(0, {shade:0.5});
            var userId = cookie.get("USER_ID");
            token_post("<%=request.getContextPath() %>/platform/auth/userShoppingShow?TOKEN=" + getToken(),
                {"userId" : userId},
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
                        if (data.data[i].skuCount != 0) {
                            if (data.data[i].isDel == 0) {
                                html += '<input type="checkbox" class="c" checked name = "ids" onclick="updById('+data.data[i].userShoppingId+')" value = '+data.data[i].userShoppingId+'>';

                            } else {
                                html += '<input type="checkbox" class="c" name = "ids" onclick="updById('+data.data[i].userShoppingId+')" value = '+data.data[i].userShoppingId+'>';

                            }
                        }
                        html += "商品名:"+data.data[i].productName+"";
                        html += '</td>';

                        html += "<td>原价:"+data.data[i].skuPriceShow+"</td>";
                        html += '<input type="hidden" value='+data.data[i].skuPrice+' name="oldMoney">';

                        html += '<td>';
                        html += '<input type="button" value="-" onclick="jianNumber('+i+','+data.data[i].userShoppingId+')">';
                        html += '<input type="hidden" name="skuCount" value='+data.data[i].skuCount+' id = "skuCount_'+i+'">';
                        html += '<input type="text" name="productCount" value='+data.data[i].productCount+' id = "number_'+i+'" onchange="chang('+data.data[i].skuCount+','+i+')">';
                        html += '<input type="button" value="+" onclick="jiaNumber('+i+','+data.data[i].userShoppingId+')">';
                        html += '</td>';

                        html += '</tr>';


                        html += '<tr>';
                        html += "<td>"+data.data[i].skuAttrValueNames+"</td>";

                        html += "<td>折扣:"+data.data[i].skuRateShow+"</td>";

                        if (data.data[i].productCount < data.data[i].skuCount) {
                            html += "<td>(有货)</td>";
                        }
                        if (data.data[i].skuCount == 0) {
                            html += "<td>(无货)</td>";
                        }
                        if (data.data[i].productCount > data.data[i].skuCount && data.data[i].skuCount > 0) {
                            html += "<td>(库存不足)</td>";
                        }
                        html += '</tr>';


                        html += '<tr>';
                        html += '<input type="hidden" name="freightCost" value="<c:if test="${data.data[i].freight == '包邮'}">0</c:if><c:if test="${data.data[i].freight != '包邮'}">'+data.data[i].freight+'</c:if>">';
                        html += "<td>邮费："+data.data[i].freight+"</td>";

                        if (data.data[i].ratePriceShow != data.data[i].skuPriceShow) {
                            html += "<td style='color: red'>现价"+data.data[i].ratePriceShow+"</td>";
                        } else {
                            html += "<td>现价"+data.data[i].ratePriceShow+"</td>";
                        }
                        html += '<input type="hidden" value='+data.data[i].ratePrice+' name="rateMoney">';

                        html += '<td>';
                        html += '<a href="<%=request.getContextPath()%>/platform/auth/delUserShopping?TOKEN='+getToken()+'&userShoppingId='+data.data[i].userShoppingId+'">后悔了，不要了</a>';
                        html += '</td>';
                        html += '</tr>';


                        html += '</table>';
                        html += '</fieldset><br>';

                    }
                    $("#tbd").html(html);
                    jisuan();
                });
        }


        //两小数相加解决精度出现的问题
        var numAdd=function (num1, num2) {//要相加的两个数
            var baseNum, baseNum1, baseNum2;
            try {
                baseNum1 = num1.toString().split(".")[1].length;
            } catch (e) {
                baseNum1 = 0;
            }
            try {
                baseNum2 = num2.toString().split(".")[1].length;
            } catch (e) {
                baseNum2 = 0;
            }
            baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
            return ((num1 * baseNum + num2 * baseNum) / baseNum).toFixed(2);
        };

        function jisuan() {
            // 默认数据为0
            var oldPrice = 0;
            var ratePrice = 0;
            var freightPrice = 0;
            // 总商品金额
            var a2 = document.getElementById("a2");
            // 商品折后金额
            var a3 = document.getElementById("a3");
            // 运费
            var a4 = document.getElementById("a4");
            // 应付总额
            var a5 = document.getElementById("a5");
            // 获得对应name的值
            var shopCarIds = $('input[name="ids"]:checked');
            var freightCost = $('input[name="freightCost"]');
            var oldMoney = $('input[name="oldMoney"]');
            var rateMoney = $('input[name="rateMoney"]');
            var skuCount = $('input[name="productCount"]');
            var chk_value = [];
            var chk_value1 = [];
            var chk_value2 = [];
            var chk_value3 = [];
            var chk_value4 = [];
            // 遍历获取对应的数据
            // 复选框id
            shopCarIds.each(function(){
                chk_value.push($(this).val());
            });
            // 原价
            oldMoney.each(function(){
                chk_value1.push($(this).val());
            });
            // 优惠价格
            rateMoney.each(function(){
                chk_value2.push($(this).val());
            });
            // 邮费
            freightCost.each(function(){
                chk_value3.push($(this).val());
            });
            // 商品个数
            skuCount.each(function(){
                chk_value4.push($(this).val());
            });
            // 获取复选框的个数
            var bb = document.getElementsByClassName("c");
            // 件商品
            var a1 = document.getElementById("a1");
            var sum =0;
            for(var i=0;i<bb.length;i++){
                if ( bb[i].checked==true) {
                    oldPrice = numAdd(oldPrice, chk_value1[i] * chk_value4[i]);
                    ratePrice = numAdd(ratePrice, chk_value2[i] * chk_value4[i]);
                    if (chk_value3[i] != 0){
                        // freightPrice += parseInt(chk_value3[i]);
                        freightPrice = numAdd(freightPrice, chk_value3[i]);
                    }
                    sum += parseInt(chk_value4[i]);
                }
            }
            a1.innerHTML=sum;
            $("#sumNumber").val(sum);
            if (oldMoney !== undefined){
                a2.innerHTML = oldPrice;
                $("#oldPrice").val(oldPrice);
                a3.innerHTML = ratePrice;
                $("#ratePrice").val(ratePrice);
                a4.innerHTML = freightPrice;
                $("#freightPrice").val(freightPrice);
            }
            var sumMoney = 0;
            sumMoney = numAdd(ratePrice, freightPrice);
            a5.innerHTML= "￥" + sumMoney
            $("#sumPrice").val(sumMoney);
        }

        function delUserShoppingAll() {
            var length = $("input[name='ids']:checked").length;
            if(length <= 0){
                layer.msg('请至少选择一个!', {icon:0});
                return;
            }
            var str = "";
            $("input[name='ids']:checked").each(function (index, item) {
                if ($("input[name='ids']:checked").length-1==index) {
                    str += $(this).val();
                } else {
                    str += $(this).val() + ",";
                }
            });
            var index = layer.load(1,{shade:0.5});
            token_post("<%=request.getContextPath()%>/platform/auth/delUserShoppingAll?TOKEN="+getToken(),
                {"ids":str},
                function(data){
                    layer.close(index);
                    if(data.code == 200){
                        layer.msg(data.msg, {
                            icon: 6,
                            time: 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function(){
                            window.location.href = "<%=request.getContextPath()%>/platform/auth/toFindUserShopping?TOKEN="+getToken();
                        });
                        return;
                    }
                    layer.msg(data.msg, {icon:5});

                });
        }


        function goSettle() {
            var length = $("input[name='ids']:checked").length;
            if(length <= 0){
                layer.msg('请至少选择一个!', {icon:0});
                return;
            }
            var str = "";
            $("input[name='ids']:checked").each(function (index, item) {
                if ($("input[name='ids']:checked").length-1==index) {
                    str += $(this).val();
                } else {
                    str += $(this).val() + ",";
                }
            });

            var userId = cookie.get("USER_ID");
            cookie.set("IDS", str, 22);

            window.location.href = "<%=request.getContextPath()%>/platform/auth/toUserOrder?TOKEN="+getToken()+"&orderMessage="+$("#fm").serialize()+"&userId="+userId;

        }

    </script>
</head>
<body>

<form id="fm">
    <input type="hidden" name="noExist">
    <input type="hidden" name="sumNumber" id="sumNumber">
    <input type="hidden" name="oldPrice" id="oldPrice">
    <input type="hidden" name="ratePrice" id="ratePrice">
    <input type="hidden" name="freightPrice" id="freightPrice">
    <input type="hidden" name="sumPrice" id="sumPrice">
</form>

<input type="checkbox" onclick="checkShopCar(this)"/>全选
<a href="javascript:delUserShoppingAll()">删除所选商品</a>　
<div id = "tbd"></div>
已选择<span id="a1" style="color: red"></span>件商品，
总商品金额：￥<span id="a2"></span>，
商品折后金额：￥<span id="a3"></span>，
运费：￥<span id="a4"></span><br>
应付总金额：<span id="a5" style="color: red"></span>
<input type="button" value="去结算" onclick="goSettle()">
</body>
</html>
