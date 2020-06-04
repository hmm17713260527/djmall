<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/1
  Time: 18:10
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


        //判断当前窗口路径与加载路径是否一致。
        if(window.top.document.URL != document.URL){
            //将窗口路径与加载路径同步
            window.top.location = document.URL;
        }

        $(function () {
            // 是否登录
            if (check_login()) {
                var nickName = cookie.get("NICK_NAME");
                $("#login").html(nickName);
                $("#login").attr("href", "<%=request.getContextPath()%>/platform/auth/toIndex?TOKEN=" + getToken());
            }
        });

        function toFindUserShopping() {
            window.location.href = "<%=request.getContextPath()%>/platform/auth/toFindUserShopping?TOKEN=" + getToken();
        }

        $(function() {
            search();
        });

        function search() {
            var index = layer.load(0, {shade:0.5});
            $.get("<%=request.getContextPath() %>/platform/show",
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
                        html += "<td><a href = '/platform/toProductShow?productSpuId="+data.data.list[i].productId+"'>"+data.data.list[i].productName+"</a></td>"
                        html += "<td>"+data.data.list[i].skuPrice+"</td>";
                        html += "<td>"+data.data.list[i].skuCount+"</td>";
                        html += "<td>"+data.data.list[i].name+"</td>";
                        html += "<td>"+data.data.list[i].skuRateShow+"</td>";
                        html += "<td>"+data.data.list[i].freight+"</td>";
                        html += "<td>";
                        html += "<img src=\"http://qazo01v5q.bkt.clouddn.com/"+data.data.list[i].img+"\" width=\"80px\" height=\"60px\">"
                        html += "</td>";
                        html += "<td>"+data.data.list[i].productDescribe+"</td>";
                        html += "<td>小星星</td>";
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
            window.location.href = "<%=request.getContextPath()%>/platform/toAdd";
        }

        // 首页
        function toIndex() {
            window.location.href = "<%=request.getContextPath()%>/platform/toShow";
        }
        // 去登陆
        function toLogin() {
            layer.open({
                type: 2,
                content: "<%=request.getContextPath()%>/platform/toLogin",
                title: "登录",
                shade: 0.6,
                area: ['300px', '300px']
            });
        }



    </script>
</head>
<body>

<form id="frm">
    <a  href="javascript:toIndex()">首页</a>&nbsp;
    <a id="login" href="javascript:toLogin()">登陆</a>&nbsp;
    <a  href="javascript:toAdd()">注册</a>&nbsp;
    <a  href="javascript:toFindUserShopping()">我的购物车</a><br/>
    <input type="hidden" value="1" id="pageNo" name="pageNo">
    <input type="hidden" value="1" name="spuStatus">
    名称：<input type = "text" name = "productName" />
    <input type = "button" value = "search" onclick = "toSearch()"/><br/>

    价格：<input type = "text" name = "skuPriceMin" /> - <input type = "text" name = "skuPriceMax" /><br/>
    分类：
    <c:forEach var="baseData" items="${baseDataList}">
        <input type="checkbox" name="types" value="${baseData.code}"/>${baseData.name}
    </c:forEach><br/>

    <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
        <tr>
            <td>名称</td>
            <td>价格</td>
            <td>库存</td>
            <td>分类</td>
            <td>折扣</td>
            <td>邮费</td>
            <td>图片</td>
            <td>描述</td>
            <td>点赞</td>
        </tr>

        <tbody id = "tbd">

        </tbody>

    </table>

    <div id="pageInfo">

    </div>

</form>

</body>
</html>
