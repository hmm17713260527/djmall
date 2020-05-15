<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/14
  Time: 21:11
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

        $(function () {
            getProvince(0);
            getCity(${userSite.province});
            getCounty(${userSite.city});
        })

        function getProvince(areaParentId) {
            token_post(
                "<%=request.getContextPath()%>/platform/findAreaByPid",
                {"areaParentId" : areaParentId},
                function (data) {
                    var province = "";
                    province +='<select name="province" onchange="getCity(this.value)">';
                    province += '<option>省</option>';
                    for (var i = 0; i < data.data.length; i++) {
                        var result = data.data[i];
                        if ('${userSite.province}' == result.areaId) {
                            province += '<option value='+result.areaId+' selected>'+result.areaName+'</option>'
                        } else {
                            province += '<option value='+result.areaId+'>'+result.areaName+'</option>'
                        }
                    }
                    province += '</select><br/>';
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

                        if ('${userSite.city}' == result.areaId) {
                            city += '<option value='+result.areaId+' selected>'+result.areaName+'</option>'
                        } else {
                            city += '<option value='+result.areaId+'>'+result.areaName+'</option>'
                        }
                    }
                    city += '</select><br/>';
                    $("#city").html(city)
                }
            )
        }

        //县
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

                        if ('${userSite.county}' == result.areaId) {
                            county += '<option value='+result.areaId+' selected>'+result.areaName+'</option>'
                        } else {
                            county += '<option value='+result.areaId+'>'+result.areaName+'</option>'
                        }

                    }
                    county += '</select><br/>';
                    $("#county").html(county)
                }
            )
        }

        function updateSite() {
            token_post("<%=request.getContextPath()%>/platform/auth/updateSite?TOKEN="+getToken(),
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

    </script>
</head>
<body>

<form id="fm">
    <input type="hidden" name="siteId" value="${userSite.siteId}">
    收件人:<input type="text" name="consignee" value="${userSite.consignee}"><br/>
    手机号:<input type="text" name="phone" value="${userSite.phone}"><br/>
    省市区:<br/>
    <span id="province"></span>
    <span id="city"></span>
    <span id="county"></span>
    街道小区详细位置:<input type="text" name="site" value="${userSite.site}"><br/>
    <input type="button" value="修改" onclick="updateSite()">
</form>

</body>
</html>
