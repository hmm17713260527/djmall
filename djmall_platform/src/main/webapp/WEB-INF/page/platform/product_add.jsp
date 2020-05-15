<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/2
  Time: 18:27
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


        jQuery.validator.addMethod("phone",
            function(value, element) {
                var tel = /^1[3456789]\d{9}$/;
                return tel.test(value)
            }, "请正确填写您的手机号");

        jQuery.validator.addMethod("email",
            function(value, element) {
                var tel = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
                return tel.test(value)
            }, "请正确填写您的邮箱编号");


        $(function(){
            $("#fm").validate({
                //效验规则
                rules: {
                    userName:{
                        required:true,
                        minlength:2,
                        remote: {
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/platform/distinct",
                            data:{
                                userName:function() {
                                    return $("#userName").val();
                                },
                                dataType:"json",
                                dataFilter:function(data,type){
                                    if (data == 'true'){
                                        return true;
                                    }else {
                                        return false	;
                                    }
                                }
                            }
                        }
                    },
                    password:{
                        required:true,
                        minlength:1,
                        digits:true
                    },
                    password2:{
                        required:true,
                        minlength:1,
                        digits:true,
                        equalTo:"#pwd"
                    },
                    phone:{
                        required:true,
                        phone:true,
                        digits:true,
                        remote: {
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/platform/distinct",
                            data:{
                                phone:function() {
                                    return $("#phone").val();
                                },
                                dataType:"json",
                                dataFilter:function(data,type){
                                    if (data == 'true'){
                                        return true;
                                    }else {
                                        return false	;
                                    }
                                }
                            }
                        }
                    },
                    email:{
                        required:true,
                        email:5,
                        remote: {
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/platform/distinct",
                            data:{
                                email:function() {
                                    return $("#email").val();
                                },
                                dataType:"json",
                                dataFilter:function(data,type){
                                    if (data == 'true'){
                                        return true;
                                    }else {
                                        return false	;
                                    }
                                }
                            }
                        }
                    },
                },
                messages:{
                    userName:{
                        required:"请填写名字",
                        minlength:"最少两个字"
                    },
                    password:{
                        required:"请填写密码",
                        minlength:"最少1个字",
                        digits:"只能是数字"
                    },
                    password2:{
                        required:"请确认密码",
                        minlength:"最少1个字",
                        digits:"只能是数字",
                        equalTo:"两次输入密码不同"
                    },
                    phone:{
                        required:"请填写手机号",
                        rangelength:"11位呀",
                        digits:"只能是数字"
                    },
                    email:{
                        required:"请填写你的邮箱",
                        email:"邮箱格式不对"
                    }
                }
            })
        })

        $.validator.setDefaults({
            submitHandler: function() {
                var index = layer.load(1,{shade:0.5});
                var pwd = md5($("#pwd").val());
                var salt = $("#salt").val();
                var md5pwd = md5(pwd + salt);
                $("#pwd").val(md5pwd);
                var formData = new FormData($("#fm")[0]);
                $.ajax({
                    url:'<%=request.getContextPath()%>/platform/add',
                    dataType:'json',
                    type:'POST',
                    // data: $("#fm").serialize(),
                    data: formData,
                    processData : false, // 使数据不做处理
                    contentType : false, // 不要设置Content-Type请求头信息
                    success: function(data){
                        layer.msg(data.msg,function () {
                            layer.close(index);
                            if (data.code != 200) {
                                return;
                            }
                            window.location.href = "<%=request.getContextPath()%>/platform/toShow";
                        })
                    }
                });
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
    <input type="hidden" name="isDel" value="1">
    <input type="hidden" name="status" value="ACTIVE">
    <input type = "hidden" name = "type" value = "3">
    <input type="hidden" name="salt" value="${salt}" id="salt">
    用户名:<input type="text" name="userName" id="userName"><br />
    密码:<input type="password" name="password" id="pwd"><br />
    确认密码:<input type="password" name="password2"><br />
    手机:<input type="text" name="phone" id="phone"><br />
    邮箱:<input type="text" name="email" id="email"><br />
    性别:
    <c:forEach items="${baseDataSexList}" var="s">
        <input type = "radio" name = "sex" value = "${s.code}">${s.name}
    </c:forEach>
    <br/>
    头像：<input type = "file" name = "file"/><br/>
    <br/>
    <input type="submit" value="注册">
</form>

</body>
</html>
