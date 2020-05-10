<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/3
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>\static\layui-v2.5.5\layui\css\layui.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jsencrypt.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layui-v2.5.5\layui\layui.all.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\md5-min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\cookie.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\slideVerify\js\jq-slideVerify.js" charset="utf-8"></script>
    <script type="text/javascript">
        //验证码时间
        var countdown = 90;

        layui.use('element', function () {
            var element = layui.element;
            element.on('tab(filter)', function (data) {
                console.log(this); //当前Tab标题所在的原始DOM元素
                console.log(data.index); //得到当前Tab的所在下标
                console.log(data.elem); //得到当前的Tab大容器
            });
        });

        $(function () {
            $("#loginBtn").click(function () {
                var password = md5($("#pwd").val());
                $(":password").val(password);
                $.get("<%=request.getContextPath()%>/platform/login", $("#loginform").serialize(), function (result) {
                    if (result.code == '200') {
                        // 存cookie
                        cookie.set("TOKEN", result.data.token, 22);
                        cookie.set("NICK_NAME", result.data.nickName, 22);
                        cookie.set("USER_ID", result.data.userId, 22);
                        // 刷新父页面
                        parent.window.location.reload();
                    } else {
                        alert(result.msg);
                        $(":password").val("");
                    }
                });
            });
        });

        function sendSms(v) {
            settime(v);
            $.get("<%=request.getContextPath()%>/platform/sendSms",
                {"phoneNumber" : $("#phoneNumber").val(), "verifyCode" : $("#verifyCode").val()},
                function(data){
                    layer.msg(data.msg);
                }
            )
        }

        function loginPhone() {
            var index = layer.load(1,{shade:0.5});
            $.get("<%=request.getContextPath()%>/platform/loginPhone",
                {"phoneNumber" : $("#phoneNumber").val(), "smsCode" : $("#smsCode").val()},
                function(result){
                    layer.close(index);
                    if (result.code == '200') {
                        // 存cookie
                        cookie.set("TOKEN", result.data.token, 22);
                        cookie.set("NICK_NAME", result.data.nickName, 22);
                        cookie.set("USER_ID", result.data.userId, 22);
                        // 刷新父页面
                        parent.window.location.reload();
                    } else {
                        alert(result.msg);
                        $(":password").val("");
                    }
                }
            )
        }



        function settime(val) {
            if (countdown == 0) {
                val.removeAttribute("disabled");
                val.value="免费获取验证码";
                countdown = 90;
                return;
            } else {
                val.setAttribute("disabled", true);
                val.value="重新发送(" + countdown + ")";
                countdown--;
            }
            setTimeout(function() {
                settime(val)
            },1000)
        }



        function imgrefload() {
            $("img").attr("src", "<%=request.getContextPath()%>/platform/getVerifCode?" + Math.random());
        }



    </script>
</head>
<body>

<div class="layui-tab layui-tab-card">
    <ul class="layui-tab-title" lay-filte="login">
        <li class="layui-this">账户普通</li>
        <li>手机登陆</li>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <form id="loginform">
                用户名:<input type="text" name="userName" placeholder="用户名/手机号/邮箱"/><br>
                密码:<input type="password" id="pwd" name="password"/><br>
                <input id="loginBtn" type="button" value="登录"/>
            </form>
        </div>
        <div class="layui-tab-item">
            <form id="aa">
                手机号:<input type="text" id="phoneNumber" name="userName" placeholder="手机号"/><br>
                图形验证码:<img src="<%=request.getContextPath()%>/platform/getVerifCode" id="codeImg" onclick="imgrefload()" alt="" width="100px" height="30px"/><input type="password" id="verifyCode"/><br>
                短信验证码:<input type="text" name="password" id="smsCode"/>
                <input type="button" onclick="sendSms(this)" value="发送短信验证码"/>
                <br>
                <input id="phoneBtn" type="button" value="登录" onclick="loginPhone()"/>
            </form>
        </div>
    </div>
</div>

</body>
</html>
