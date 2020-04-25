<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/24
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
    <script src="<%=request.getContextPath()%>\static\js\jquery.validate.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    <script type="text/javascript">


        $(function() {
            findAttr($("#type").val());
        })



        function findAttr(productType) {
            $.get(
                "<%=request.getContextPath()%>/product/spu/findAttrByProductType",
                {"productType":productType},
                function(data) {
                    var attr = data.data;
                    var html = "";
                    for(var i = 0; i < attr.length; i++) {
                        html += "<tr>";
                        html += "<td>" + attr[i].attrName + "";
                        html += "<input type='hidden' value='"+attr[i].attrId+","+attr[i].attrName+"' />"
                        html += "</td>";
                        html += "<td>";
                        var attrValueIdList = attr[i].attrValueIds.split(",");
                        var attrValueList = attr[i].attrValues.split(",");
                        for(var j = 0; j < attrValueIdList.length; j++) {
                            var attrValueId = attrValueIdList[j];
                            html += "<input type = 'checkbox' value = '"+attrValueId+","+attrValueList[j]+"'/>"+attrValueList[j]+""
                        }
                        html += "</td>";
                        html += "</tr>";
                    }
                    $("#tbd").html(html);
                }
            )
        }


        <!-- 自定义属性 -->
        function addAttr() {
            var content = "属性名:<input type='text' id='attrName'/><br>"
            content +=  "属性值:<input type='text' id='attrValue'/>(多个值之间,分割)<br>";
            layer.open({
                type: 1,
                content: content, //这里content是一个普通的String
                title: "自定义SKU",
                shadeClose: true,
                shade: 0.5,
                area: ['50%', '80%'],
                btn: ['确认', '取消'],
                yes: function (index) {
                    var html = "";
                    html += "<tr>";
                    html += "<td>" + $("#attrName").val();
                    html += "<input type='hidden' value='-1' />";
                    html += "<input type='hidden' value='"+$("#attrName").val()+"' />";
                    html += "</td>";
                    html += "<td>";
                    var values = $("#attrValue").val().split(",");
                    for(var i=0; i<values.length; i++){
                        html += "<input type='checkbox' value='-1,"+values[i]+"' />"+ values[i];
                    }
                    html += "</td>";
                    html += "</tr>"
                    $("#tbd").append(html);
                    layer.close(index)
                }
            });

        }



        //生成SKU
        function toSku() {
            var att2 = [];//属性id数组
            var att3 = [];//属性名数组
            var att = [];//属性值id数组
            var att1 = [];//属性值数组

            var $tr = $("#tbd").find("tr");
            for (var i = 0; i< $tr.length; i++) {
                var $td = $($tr[i]).find("td");
                var $attrValues = $($td[1]).find(":checked");
                var attrId = [];
                var attrValue = [];
                if($attrValues.length>0) {
                    att3.push(($($($td[0]).find("input")).val().split(","))[1]);
                    att2.push(($($($td[0]).find("input")).val().split(","))[0]);
                    for (var j = 0; j< $attrValues.length; j++){
                        var attrid = $($attrValues[j]).val().split(",");
                        attrId[j] = attrid[0];
                        attrValue[j] = attrid[1];
                    }
                    att.push(attrId);
                    att1.push(attrValue);
                }
            }
            var dkejId = dkej(att);
            var dkejValue = dkej(att1);
            var html = "";
            for(var i=0; i<dkejId.length; i++) {
                var c = i+1;
                html += "<tr id = 'tr_"+i+"'>";
                html += "<td>"+c+"</td>";
                html += "<td>"+dkejValue[i]+"</td>";
                html += "<td><input type='text' name='productSkuList["+i+"].skuCount' value='10' /></td>";
                html += "<td><input type='text' name='productSkuList["+i+"].skuPrice' value='10'/></td>";
                html += "<td>";
                html += "<input type='text' name ='productSkuList["+i+"].skuRate' value='90'/>";
                html += "</td>";
                html += "<td><input type='button' value='移除' onclick='delSku("+i+")'  /></td>";
                html += "<input type = 'hidden' value ='"+1+"' name='productSkuList["+i+"].skuStatus'>";
                html += "<input type = 'hidden' value ='"+att2+"' name='productSkuList["+i+"].skuAttrIds'>";
                html += "<input type = 'hidden' value = '"+att3+"' name='productSkuList["+i+"].skuAttrNames'>";
                html += "<input type = 'hidden' value='"+dkejId[i]+"' name='productSkuList["+i+"].skuAttrValueIds'>";
                html += "<input type = 'hidden' value='"+dkejValue[i]+"' name='productSkuList["+i+"].skuAttrValueNames'>";
                html += "</tr>";
            }
            var ht = "";
            $("#sku").html(html);
        }


        //笛卡尔积
        function dkej(d) {
            var total = 1;
            for (var i = 0; i < d.length; i++) {
                total *= d[i].length;
            }
            var e = [];
            var itemLoopNum = 1;
            var loopPerItem = 1;
            var now = 1;
            for (var i = 0; i < d.length; i++) {
                now *= d[i].length;
                var index = 0;
                var currentSize = d[i].length;
                itemLoopNum = total / now;
                loopPerItem = total / (itemLoopNum * currentSize);
                var myIndex = 0;
                for (var j = 0; j < d[i].length; j++) {
                    for (var z = 0; z < loopPerItem; z++) {
                        if (myIndex == d[i].length) {
                            myIndex = 0;
                        }
                        for (var k = 0; k < itemLoopNum; k++) {
                            e[index] = (e[index] == null ? "" : e[index] + ":") + d[i][myIndex];
                            index++;
                        }
                        myIndex++
                    }
                }
            }
            return e;
        }

        //移除sku
        function delSku(id) {
            $("#tr_"+id).remove();
        }



        //validate
        $(function () {
            $("#frm").validate({
                rules: {
                    productName: {
                        required: true,
                    },
                    freightId: {
                        required: true,
                    },
                    productDescribe:{
                        required:true,
                    },
                    type:{
                        required:true,
                    },
                    file:{
                        required:true,
                    }
                },
                messages: {
                    productName: {
                        required: "请填写商品名称！！！"
                    },
                    freightId: {
                        required: "没有运费吗？包邮？"
                    },
                    productDescribe:{
                        required:"请填写商品描述"
                    },
                    type:{
                        required:"请填选择商品类型"
                    },
                    file:{
                        required:"请上传图片"
                    }
                }
            });
        });


        //新增商品
        $.validator.setDefaults({
            submitHandler: function () {
                var formData = new FormData($("#frm")[0]);
                var index = layer.load();
                //七牛雲上传图片只能ajax提交
                $.ajax({
                    url:'<%= request.getContextPath() %>/product/spu/addProduct',
                    dataType:'json',
                    type:'POST',
                    data: formData,
                    processData : false, // 使数据不做处理
                    contentType : false, // 不要设置Content-Type请求头信息
                    success: function(data){
                        layer.msg(data.msg,function () {
                            layer.close(index);
                            if (data.code != 200) {
                                return;
                            }
                            window.location.href = "<%= request.getContextPath() %>/product/spu/toShow";
                        })
                    }
                });
            }
        });



    </script>
    <style>
        .error{
            color:red;
        }
    </style>
</head>
<body>

<form id = "frm">
    <!-- spu -->
    名称：<input type = "text" name = "productName" /><br/>
    邮费：
    <select name = "freightId">
        <option value = "">请选择</option>
        <c:forEach items="${freightList}" var="freight">
            <option value="${freight.freightId}">
                <c:if test="${freight.freight != '运费'}">
                    ${freight.baseName}-${freight.freight}
                </c:if>
                <c:if test="${freight.freight == '运费'}">
                    ${freight.baseName}-包邮
                </c:if>
            </option>
        </c:forEach>
    </select><br/>
    描述：<textarea rows="4px" cols="30px" name="productDescribe"></textarea><br/>
    图片<input type = "file" name = "file"/><br/>
    分类：
    <select id="type" name = "type" onchange="findAttr(this.value)" >
        <c:forEach var="baseData" items="${baseDataList}">
            <option value = "${baseData.code}">${baseData.name}</option>
        </c:forEach>
    </select><br/>



    <!-- sku -->
    SKU<input type = "button" value = "+" onclick="addAttr()" />&nbsp;&nbsp;
    <input type = "button" value = "生成SKU" onclick = "toSku()" />
    <br/>
    <table border="2px">
        <thead>
        <tr>
            <th>属性名</th>
            <th>属性值</th>
        </tr>
        </thead>
        <tbody id = "tbd"></tbody>
    </table>
    <table border="2px">
        <tr>
            <th>编号</th>
            <th>SKU属性</th>
            <th>库存</th>
            <th>价格(元)</th>
            <th>折扣(%)</th>
            <th></th>
        </tr>
        <tbody id = "sku"></tbody>
    </table>
    <input type = "submit" value = "添加" />
</form>

</body>
</html>
