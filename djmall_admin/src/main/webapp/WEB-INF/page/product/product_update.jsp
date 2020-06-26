<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/28
  Time: 16:20
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


        $(function(){
            search();
        })

        function search() {
            $.get(
                "<%=request.getContextPath()%>/product/sku/findListByProductId",
                {"productId": ${product.productId}},
                function(data){
                    var html = "";
                    for (var i = 0; i < data.data.length; i++) {
                        var p = data.data[i];
                        html += "<tr>"
                        html += "<td><input type = 'checkbox' name = 'ids' value = '"+p.productSkuId+"'></td>";
                        html += "<td>"+p.skuAttrValueNames+"</td>"
                        html += "<td><input type = 'hidden' id='skuCount_"+p.productSkuId+"' name = 'skuCount' value = '"+p.skuCount+"'>"+p.skuCount+"</td>"
                        html += "<td>"+p.skuPrice+"</td>"
                        html += "<td>"+p.skuRate+"</td>"
                        html += p.isDefault == 0 ? "<td>是</td>" : "<td>否</td>"
                        var a = "";
                        if (p.skuStatus == 0) {
                            a = "上架"
                        } else {
                            a = "下架"
                        }
                        html += "<td>"
                        html += "<input type = 'hidden' id='skuStatus_"+p.productSkuId+"' name = 'skuStatus' value = '"+p.skuStatus+"'>"
                        html += "<input type = 'button' value = '"+a+"' onclick='updateStatus("+p.productSkuId+")'></td>"
                        html += "</tr>"
                    }
                    $("#tbd").html(html);
                    $('input[name=skuAttrValueNames]').val(function(index,value) {
                        return value.replace(',','-');
                    });
                }
            )
        }



        function updateStatus(id) {
            var i = $("#skuStatus_"+id).val();
            var sts = 0;
            if (i == 1) {
                sts = 0;
            } else {
                sts = 1;
            }
            var index = layer.load(0, {shade:0.5});
            $.post("<%=request.getContextPath()%>/product/sku/updateStatus",
                {"productSkuId" : id, "skuStatus" : sts, "_method" : "PUT"},
                function(data){
                    layer.close(index);
                    layer.msg(data.msg, function(){
                        if (data.code != 200) {
                            return;
                        }
                        search();
                    });

                })
        }

        function update() {
            var formData = new FormData($("#frm")[0]);
            var index = layer.load();
            $.ajax({
                url:'<%=request.getContextPath()%>/product/spu/update',
                dataType:'json',
                type:'PUT',
                data: formData,
                processData : false, // 使数据不做处理
                contentType : false, // 不要设置Content-Type请求头信息
                success: function(data){
                    layer.msg(data.msg,function () {
                        layer.close(index);
                        if (data.code != 200) {
                            return;
                        }
                        window.location.href = "<%=request.getContextPath()%>/product/spu/toShow";
                    })
                }
            });
        }


        function updateDefault() {
            var chkValue = $('input[name="ids"]:checked');
            if (chkValue.length == 0) {
                layer.msg('未选中');
            } else if (chkValue.length > 1) {
                layer.msg('多选');
            } else {
                var id = chkValue.val();
                var index = layer.load(0, {shade:0.5});
                $.post("<%=request.getContextPath()%>/product/sku/updateDefault",
                    {"productSkuId" : id, "productId" : '${product.productId}', "isDefault" : "0", "_method" : "PUT"},
                    function(data){
                        layer.close(index);
                        layer.msg(data.msg, function(){
                            if (data.code != 200) {
                                return;
                            }
                            search();
                        });

                    })
            }
        }


        function toUpdate() {
            var chkValue = $('input[name="ids"]:checked');
            if (chkValue.length == 0) {
                layer.msg('未选中');
            } else if (chkValue.length > 1) {
                layer.msg('多选');
            } else {
                var id = chkValue.val();
                layer.open({
                    type: 2,
                    title: '编辑页面',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['380px', '80%'],
                    content: '<%=request.getContextPath()%>/product/sku/toUpdate/' + id
                });

            }
        }

        function toUpdateCount() {
            var chkValue = $('input[name="ids"]:checked');
            if (chkValue.length == 0) {
                layer.msg('未选中');
            } else if (chkValue.length > 1) {
                layer.msg('多选');
            } else {
                var id = chkValue.val();
                var skuCount = $("#skuCount_"+id).val();
                var content = "<input type='button' value='-' onclick='calculate(1)'/>"
                content +=  "<input type='text' id='skuCount' value='"+skuCount+"'/>";
                content +=  "<input type='hidden' id='productSkuId' value='"+id+"'/>";
                content +=  "<input type='button' value='+' onclick='calculate(2)'/>";
                layer.open({
                    type: 1,
                    content: content, //这里content是一个普通的String
                    title: "修改库存",
                    shadeClose: true,
                    shade: 0.5,
                    area: ['30%', '50%'],
                    btn: ['确认', '取消'],
                    yes: function (index) {
                    $.post("<%=request.getContextPath()%>/product/sku/updateCount",
                        {"productSkuId": $("#productSkuId").val(), "skuCount": $("#skuCount").val(), "_method" : "PUT"},
                        function(data){
                            layer.close(index);
                            layer.msg(data.msg, function(){
                                if (data.code != 200) {
                                    return;
                                }
                                search();
                            });

                        })
                    }
                });

            }
        }

        function calculate(i) {
            if (i == 1) {

                if ($("#skuCount").val() < 1) {
                    layer.msg("不能再减啦", {icon: 5});
                    return;
                }
                $("#skuCount").val(parseInt($("#skuCount").val()) - 1);

            } else {
                $("#skuCount").val(parseInt($("#skuCount").val()) + 1);
            }

        }

    </script>
</head>
<body>


<form id = "frm">
    <input type="hidden" name="productSpuId" value="${product.productId}"><br />
    名称:<input type="text" name="productName" value="${product.productName}"><br />

    运费:
    <select name = "freightId">
        <c:forEach items="${freightList}" var="freight">
            <option value="${freight.freightId}" <c:if test="${product.freightId == freight.freightId}">selected</c:if>>
                    ${freight.baseName}-${freight.freight}
            </option>
        </c:forEach>
    </select><br/>
    描述:<input type="text" name="productDescribe" value="${product.productDescribe}"><br />
    图片:<img src="http://qazo01v5q.bkt.clouddn.com/${product.img}" width="100px" height="100px">
    <input type = "file" name = "file"/><br/>
</form>

    分类:${product.name}<br />
    SKU列表<br>
    <input type="button" value="修改库存" onclick="toUpdateCount()">
    <input type="button" value="编辑" onclick="toUpdate()">
    <input type="button" value="设为默认" onclick="updateDefault()"><br />
    <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
        <tr>
            <th>编号</th>
            <th>SKU属性</th>
            <th>库存</th>
            <th>价格(元)</th>
            <th>折扣(%)</th>
            <th>是否默认</th>
            <th></th>
        </tr>
        <tbody id="tbd"></tbody>
    </table>
    <input type="button" value="修改提交" onclick="update()"><br />


</body>
</html>
