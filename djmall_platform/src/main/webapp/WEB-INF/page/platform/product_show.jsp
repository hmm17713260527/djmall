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

            var userId = cookie.get("USER_ID");
            $("#userLikeId").val(userId);

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
                        // html += "<td>"+data.data.list[i].skuCount+"</td>";
                        html += "<td>"+data.data.list[i].name+"</td>";
                        // html += "<td>"+data.data.list[i].skuRateShow+"</td>";
                        html += "<td>"+data.data.list[i].skuRate+"%</td>";
                        html += "<td>"+data.data.list[i].freight+"</td>";
                        html += "<td>";
                        html += "<img src=\"http://qck9lda56.bkt.clouddn.com/"+data.data.list[i].img+"\" width=\"80px\" height=\"60px\">"
                        html += "</td>";
                        html += "<td>"+data.data.list[i].productDescribe+"</td>";

                        if (userId == null) {
                            html += "<td><a href='javascript:like("+2+","+data.data.list[i].productId+")'  style='color: #8D8D8D'>♥</a></td>";
                        } else {
                            // if (data.data.list[i].status == null) {
                            //     html += "<td><a href='javascript:like("+1+","+data.data.list[i].productId+")'  style='color: #8D8D8D'>♥</a></td>";
                            //
                            // } else {
                                html += data.data.list[i].status == 2 ? "<td><a href='javascript:like("+1+","+data.data.list[i].productId+")'  style='color: #8D8D8D'>♥"+data.data.list[i].count+"</a></td>" : "<td><a href='javascript:like("+2+","+data.data.list[i].productId+")'  style='color: red'>♥"+data.data.list[i].count+"</a></td>";

                            //}
                        }
                        // if (data.data.list[i].status == 1) {
                        //     html += "<td><a href='javascript:like("+userId+")'  style='color: #8D8D8D'>♥</a></td>";
                        // } else {
                        //     html += "<td><a href='javascript:like("+userId+")'  style='color: #8D8D8D'>♥</a></td>";
                        //
                        // }
                        html += "</tr>";
                    }
                    $("#tbd").html(html);

                    var pageNo = $("#pageNo").val();
                    var pageHtml = "<input type='button' value='上一页' onclick='page("+data.data.pages+", "+(parseInt(pageNo) - 1)+")'>";
                    pageHtml += "<input type='button' value='下一页' onclick='page("+data.data.pages+", "+(parseInt(pageNo) + 1)+")')'>";
                    $("#pageInfo").html(pageHtml);

                });
        }


        function like(status, productId) {
            var userId = cookie.get("USER_ID");
            if (userId == null) {
                layer.msg('未登陆');
                return;
            }

            token_post(
                "<%=request.getContextPath()%>/platform/auth/updateProductLike?TOKEN="+getToken(),
                {"userId": userId, "status": status, "productId" : productId},
                function (data) {
                    search();
                }
            )

        }

        function toSearch() {
            $("#pageNo").val(0);
            search();
        }

        function page(totalNum, page) {

            if (page < 0) {
                layer.msg('已经到首页啦!', {icon:0});
                return;
            }
            if (totalNum <= page) {
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
    <input type="hidden" value="0" id="pageNo" name="pageNo">
    <input type="hidden" value="1" name="spuStatus">
    <input type="hidden" id="userLikeId" name="userLikeId">
    名称：<input type = "text" name = "productName" />
    <input type = "button" value = "search" onclick = "toSearch()"/><br/>

    价格：<input type = "text" name = "skuPriceMin" /> - <input type = "text" name = "skuPriceMax" /><br/>
    分类：
    <c:forEach var="baseData" items="${baseDataList}">
<%--        <input type="checkbox" name="types" value="${baseData.code}"/>${baseData.name}--%>
        <input type="radio" name="type" value="${baseData.code}"/>${baseData.name}
    </c:forEach><br/>

    <table cellpadding='12px' cellspacing='0px' border='1px'  bordercolor='gray' bgcolor='pink'>
        <tr>
            <td>名称</td>
            <td>价格</td>
<%--            <td>库存</td>--%>
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
