<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/6
  Time: 15:17
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
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
    <script src="<%=request.getContextPath()%>\static\js\jquery.validate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\cookie.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    <script type="text/javascript">

        layui.use('rate', function(){
            var rate = layui.rate;

            <c:forEach items="${list}" var="p" varStatus="i">
            //渲染
            rate.render({
                elem: '#comment_${i.index}'  //绑定元素

                ,text: true
                ,setText: function(value){
                    var arrs = {
                        '1': '极差'
                        ,'2': '差'
                        ,'3': '中等'
                        ,'4': '好'
                    };
                    this.span.text(arrs[value] || ( value + "星"));
                }
                ,choose: function(value){
                    $("#commentType_${i.index}").val(value);
                }
            });
            </c:forEach>
        });

        function addComment() {
            var index = layer.load(1);
            token_post(
                "<%=request.getContextPath()%>/order/auth/addComment?TOKEN="+getToken(),
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    window.history.back();
                }
            );
        }

    </script>
</head>
<body>
<form id="fm">
    <table class="layui-table">
        <c:forEach items="${list}" var="p" varStatus="i">
            <tr>
                <td>${p.productName}</td>
                <td>
                    <input type="hidden" name="commentList[${i.index}].orderNo" value="${orderNo}">
                    <input type="hidden" name="commentList[${i.index}].productId" value="${p.productId}">
                    <input type="hidden" name="commentList[${i.index}].parentId" value="0">
                    <input type="hidden" name="commentList[${i.index}].commentType" id="commentType_${i.index}">
                    商品评分：<div id="comment_${i.index}"></div><br>
                    商品评价：<textarea name="commentList[${i.index}].comment"></textarea>
                </td>
            </tr>
        </c:forEach>
    </table>
</form>
<input class="layui-btn" type="button" value="提交评论" onclick="addComment()">
</body>
</html>
