<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/24
  Time: 22:04
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
    })

    function search() {
        var index = layer.load(0, {shade:0.5});
        $.get("<%=request.getContextPath() %>/product/spu/show",
            $("#frm").serialize(),
            function(data){
                layer.close(index);
                if (data.code != 200) {
                    layer.msg(data.msg);
                    return;
                }

                var html = "";
                for (var i = 0; i < data.data.list.length; i++) {
                    html += "<tr>";
                    html += "<td><input type='checkbox' name = 'ids' value = '"+data.data.list[i].productId+"'/></td>";
                    html += "<td>"+data.data.list[i].productName+"</td>";
                    html += "<td>"+data.data.list[i].name+"</td>";
                    html += data.data.list[i].spuStatus == 1 ? "<td>上架</td>" : "<td>下架</td>";
                    html += "<input type='hidden' id = '"+data.data.list[i].productId+"' value = '"+data.data.list[i].spuStatus+"'/>";
                    html += "<td>"+data.data.list[i].freight+"</td>";
                    html += "<td>";
                    html += "<img src=\"http://qazo01v5q.bkt.clouddn.com/"+data.data.list[i].img+"\" width=\"80px\" height=\"60px\">"
                    html += "</td>";
                    html += "<td>"+data.data.list[i].productDescribe+"</td>";
                    html += "<td>"+data.data.list[i].praise+"</td>";
                    html += "<td>"+data.data.list[i].orderCount+"</td>";
                    html += "</tr>";
                }
                $("#tbd").html(html);

                var pageNo = $("#pageNo").val();
                var pageHtml = "<input type='button' value='上一页' onclick='page("+data.data.pages+", "+(parseInt(pageNo) - 1)+")'>";
                pageHtml += "<input type='button' value='下一页' onclick='page("+data.data.pages+", "+(parseInt(pageNo) + 1)+")')'>";
                $("#pageInfo").html(pageHtml);

            });
    }

    function toSearch() {
        $("#pageNo").val(1);
        search();
    }

    function page(totalNum, page) {

        if (page < 1) {
            layer.msg('已经到首页啦!', {icon:0});
            return;
        }
        if (page > totalNum) {
            layer.msg('已经到尾页啦!!', {icon:0});
            return;
        }
        $("#pageNo").val(page);
        search();

    }




    function toAdd() {
        window.location.href= "<%=request.getContextPath()%>/product/spu/toAdd";
    }

    function updateStatus() {
        var chkValue = $('input[name="ids"]:checked');
        if (chkValue.length == 0) {
            layer.msg('未选中');
        } else if (chkValue.length > 1) {
            layer.msg('多选');
        } else {

            var id = chkValue.val();
            var status = $("#" + id).val();
            var spuStatus = 0;
            if (status == 1) {
                spuStatus = 0;
            } else {
                spuStatus = 1;
            }
            var index = layer.load(0, {shade:0.5});
            $.post("<%=request.getContextPath()%>/product/spu/updateStatus",
                {"productSpuId" : id, "spuStatus" : spuStatus, "_method" : "PUT"},
                function(data){
                    layer.close(index);
                    layer.msg(data.data, function(){
                        if (data.code != 200) {
                            return;
                        }
                        window.location.href = "<%=request.getContextPath()%>/product/spu/toShow";
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
            window.location.href= "<%=request.getContextPath()%>/product/spu/toUpdate/" + id;

        }
    }


</script>
</head>
<body>

<form id="frm">
    <input type="hidden" value="1" id="pageNo" name="pageNo">
    名称：<input type = "text" name = "productName" /><br/>

    <c:forEach var="baseData" items="${baseDataList}">
        <input type="checkbox" name="types" value="${baseData.code}"/>${baseData.name}
    </c:forEach><br/>
    <input type = "button" value = "搜索" onclick = "toSearch()"/><br/>

    <input type = "button" value = "增量索引"/>
    <input type = "button" value = "重量索引"/><br/>

    <shiro:hasPermission name="BTN_PRODUCT_ADD">
        <input type="button" value="新增" onclick="toAdd()"/>
    </shiro:hasPermission>

    <shiro:hasPermission name="BTN_PRODUCT_UPDATE">
        <input type = "button" value = "修改" onclick="toUpdate()"/>
    </shiro:hasPermission>

    <shiro:hasPermission name="BTN_PRODUCT_UPDATE_STATUS">
        <input type = "button" value = "上架/下架" onclick="updateStatus()"/>
    </shiro:hasPermission>
    <input type = "button" value = "查看评论"/>
    <input type = "button" value = "下载导入模板"/>
    <input type = "button" value = "导入"/><br/>

    <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
        <tr>
            <td>id</td>
            <td>名称</td>
            <td>类型</td>
            <td>状态</td>
            <td>邮费</td>
            <td>商品图片</td>
            <td>描述</td>
            <td>点赞量</td>
            <td>订单量</td>
        </tr>

        <tbody id = "tbd">

        </tbody>

    </table>

    <div id="pageInfo">

    </div>

</form>
</body>
</html>
