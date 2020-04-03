<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/3/5
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jsencrypt.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\md5-min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
    <script type="text/javascript">

        if (window.top.document.URL != document.URL) {
            window.top.location = document.URL;
        }

        function inquire(name) {
            $.get(
                "<%=request.getContextPath()%>/auth/user/findSalt",
                {"userName" : name.value},
                function (data) {
                    $("#salt").val(data.data);
                })

        }

        function login() {
            var index = layer.load(0, {shade:0.5});
            var pwd = md5($("#password").val());
            var salt = $("#salt").val();
            var pwds = md5(pwd + salt);
            $("#password").val(pwds);

            $.get("<%=request.getContextPath()%>/auth/user/login",
                $("#fm").serialize(),
                function(data){

                    if(data.code != 200){
                        layer.close(index);
                        layer.msg(data.msg, {icon: 5});
                        return;
                    }
                    layer.msg(data.msg, {
                        icon: 6,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function(){
                        parent.window.location.href = "<%=request.getContextPath()%>/index/toIndex";
                    });

                })
        }


    </script>
</head>
<body>

<form id = "fm">
    <input type="hidden" name = "salt" id = "salt">
    <input type="text" name="userName" placeholder="请输入用户名/手机号/邮箱" onblur="inquire(this)" /><br />
    <input type="password" name="password" placeholder="请输入密码" id="password"/><br />
    <a href="<%=request.getContextPath()%>/auth/user/toAdd">还没有账号?点我去注册!</a><br />
    <a href="<%=request.getContextPath()%>/auth/user/toResetPwd">找回密码/修改密码</a><br />
    <input type="button" value="登录" onclick="login()" /><br />
</form>

</body>
</html>
