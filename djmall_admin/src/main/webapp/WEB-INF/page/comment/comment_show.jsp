<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/7
  Time: 17:38
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
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    <script type="text/javascript">




        $(function() {
            search();

            findReq();
        });

        function search() {
            var index = layer.load(0, {shade:0.5});
            $.get("<%=request.getContextPath() %>/comment/show",
                $("#fm").serialize(),
                function(data){
                    layer.close(index);
                    if (data.code != 200) {
                        layer.msg(data.msg);
                        return;
                    }

                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        html +="<div style='border:solid;height: 130px;width: 400px'>";
                        // html +="<div class='lf_rate' lay-data='{value:"+data.data.list[i].commentType+",half: true, readonly:true,theme: '#F70808'}' style='margin:0px, 160px'></div><br/>";

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
                        if (data.data.list[i].commentDTOResp == null) {
                            html +="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='button' value='回复' onclick='addComment("+data.data.list[i].commentId+")'/>";

                        }
                        html +="</td>";
                        html +="</tr>";
                        if (data.data.list[i].commentDTOResp != null) {
                            html +="<tr>";
                            html +="<td style='color:red;'>商家回复：</td>";
                            html += "<td colspan='2'>"+data.data.list[i].commentDTOResp.comment+"</td>";
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
            var businessId = $("#businessId").val();
            $.get(
                "<%=request.getContextPath()%>/comment/findReq",
                {"businessId": businessId},
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


        function addComment(parentId) {
            layer.open({
                type: 2,
                title: '回复页面',
                shadeClose: true,
                shade: 0.8,
                area: ['380px', '80%'],
                content: '<%=request.getContextPath()%>/comment/reply/' + parentId
            });
        }

    </script>
</head>
<body>
<h2>好评率：<span id="rep" style="color: red"></span></h2>
<form id="fm">
    <input type="radio" name="commentType" value="0" onclick="searchs()" checked/>所有评论
    <input type="radio" name="commentType" value="1" onclick="searchs()"/>好评
    <input type="radio" name="commentType" value="2" onclick="searchs()"/>中评
    <input type="radio" name="commentType" value="3" onclick="searchs()"/>差评
    <input type="hidden" value="1" id="pageNo" name="pageNo">
    <input type="hidden" id="businessId" name="businessId" value="${businessId}">

</form>
<div id="tbd"></div>
<div id="pageInfo">

</div>
</body>
</html>
