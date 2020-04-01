<%--
  Created by IntelliJ IDEA.
  User: 86150
  Date: 2020/3/6
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\layui-v2.5.5\layui\layui.all.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>\static\layui-v2.5.5\layui\css\layui.css"  media="all">
<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\md5-min.js"></script>
<script src="<%=request.getContextPath()%>\static\js\jquery.validate.js"></script>
<script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
<body>
    <form id="fm">
        <input type="hidden" name="salt" value="${salt}" id="salt">
        <input type="hidden" name="_method" value="PUT">
        <table>
            <tr>
                <td>新密码:</td>
                <td><input type="password" name="password" id="pwd"></td>
            </tr>
            <tr>
                <td>确认密码:</td>
                <td><input type="password" name="password2"></td>
            </tr>
            <tr>
                <td>手机号:</td>
                <td><input type = "text" name = "phone" id="phone" onchange="inquire(this)"><span id="span" style="color: red"></span></td>
            </tr>
            <tr>
                <td>请输入验证码：</td>
                <td><input type = "text" name = "verify"><input type="button" value="获取验证码"  onclick="getCode()" /></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="修改密码"></td>
            </tr>
        </table>
    </form>
</body>
<script type="text/javascript">

    function getCode(){
        $.get(
            "<%=request.getContextPath()%>/auth/user/getVerify",
            {"phone":$("#phone").val()},
            function (data) {
                if (data.code==200){
                    layer.msg(data.msg, {icon: 6});
                } else {
                    layer.msg(data.msg, {icon: 5});
                }
            })
    }


    function inquire(name) {
        $.get(
            "<%=request.getContextPath()%>/auth/user/findPhone",
            {"phone" : name.value},
            function (data) {
                if (data.code != 200) {
                    $("#span").html("手机号未注册或已删除")
                }else{
                    $("#span").html("");
                }
            })
    }


    $(function() {
        $("#fm").validate({
            //效验规则
            rules: {
                password: {
                    required: true,
                    minlength: 1,
                    digits: true
                },
                password2: {
                    required: true,
                    minlength: 1,
                    digits: true,
                    equalTo: "#pwd"
                },
                phone: {
                    required: true,
                    phone: true,
                    digits: true
                },
                messages: {
                    password: {
                        required: "请填写密码",
                        minlength: "最少1个字",
                        digits: "只能是数字"
                    },
                    password2: {
                        required: "请确认密码",
                        minlength: "最少1个字",
                        digits: "只能是数字",
                        equalTo: "两次输入密码不同"
                    },
                    phone: {
                        required: "请填写手机号",
                        rangelength: "11位呀",
                        digits: "只能是数字"
                    }
                }
            }
        })
    })
    jQuery.validator.addMethod("phone",
        function(value, element) {
            var tel = /^1[3456789]\d{9}$/;
            return tel.test(value)
        }, "请正确填写您的手机号");

    $.validator.setDefaults({
        submitHandler: function() {
            var index = layer.load(1,{shade:0.5});
            var pwd = md5($("#pwd").val());
            var salt = $("#salt").val();
            var md5pwd = md5(pwd + salt);
            $("#pwd").val(md5pwd);
            $.post("<%=request.getContextPath()%>/auth/user/updatePwd",
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
                        window.location.href = "<%=request.getContextPath()%>/auth/user/toShow";
                    });
                }
            )
        }
    });

</script>
<!-- 错误时提示颜色 -->
<style>
    .error{
        color:red;
    }
</style>
</html>
