<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/14
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\md5-min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
    <script src="<%=request.getContextPath()%>\static\js\jquery.validate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\cookie.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    <script type="text/javascript">


        $(function() {
            findAreaByPid();
        });


        function findAreaByPid() {
            token_post(
                "<%=request.getContextPath()%>/platform/findAreaByPid",
                {"areaParentId" : 0},
                function (data) {
                    var province = "";
                    province +='<select name="province" onchange="getCity(this.value)">';
                    province += '<option>省</option>';
                    for (var i = 0; i < data.data.length; i++) {
                        var result = data.data[i];
                        province += '<option value="'+result.areaId+'">'+result.areaName+'</option>'
                    }
                    province += '</select>';
                    $("#province").html(province)
                }
            )
        }

        function getCity(areaParentId) {
            token_post(
                "<%=request.getContextPath()%>/platform/findAreaByPid",
                {"areaParentId" : areaParentId},
                function (data) {
                    var city = "";
                    city +='<select name="city" onchange="getCounty(this.value)">';
                    city += '<option>市</option>';
                    for (var i = 0; i < data.data.length; i++) {
                        var result = data.data[i];
                        city += '<option value="'+result.areaId+'">'+result.areaName+'</option>'
                    }
                    city += '</select>';
                    $("#city").html(city)
                }
            )
        }

        function getCounty(areaParentId) {
            token_post(
                "<%=request.getContextPath()%>/platform/findAreaByPid",
                {"areaParentId" : areaParentId},
                function (data) {
                    var county = "";
                    county +='<select name="county">';
                    county += '<option>县</option>';
                    for (var i = 0; i < data.data.length; i++) {
                        var result = data.data[i];
                        county += '<option value="'+result.areaId+'">'+result.areaName+'</option>'
                    }
                    county += '</select>';
                    $("#county").html(county)
                }
            )
        }

        $(function(){
            $("#fm").validate({
                rules: {
                    consignee:{
                        required:true
                    },
                    phone:{
                        required:true,
                        digits:true
                    },
                    site:{
                        required:true
                    },
                    province:{
                        required:true
                    },
                    city:{
                        required:true
                    },
                    county:{
                        required:true
                    }
                },
                messages:{
                    consignee:{
                        required:"没人收货？"
                    },
                    phone:{
                        required:"请填写手机号",
                        digits:"只能是数字"
                    },
                    site:{
                        required:"请填写详细地址"
                    },
                    province:{
                        required:"请选择省份"
                    },
                    city:{
                        required:"请选择城市"
                    },
                    county:{
                        required:"请选择县"
                    }
                }
            })
        });

        $.validator.setDefaults({
            submitHandler: function() {
                var index = layer.load(1,{shade:0.5});
                var userId = cookie.get("USER_ID");
                $("#userId").val(userId);
                token_post("<%=request.getContextPath()%>/platform/auth/addUserSite?TOKEN="+getToken(),
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
                            parent.window.location.href = "<%=request.getContextPath()%>/platform/auth/toUserSiteShow?TOKEN="+getToken();
                        });
                    }
                )
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

<form id="fm">
    收件人<input type="text" name="consignee"><br>
    手机号<input type="text" name="phone"><br>
    省市区
    <span id="province"></span>
    <span id="city"></span>
    <span id="county"></span><br>
    街道小区详细位置<input type="text" name="site"><br>
    <input type="hidden" name="userId" id="userId">
    <input type="submit" value="提交">
</form>

</body>
</html>
