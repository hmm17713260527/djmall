<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\md5-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
<script src="<%=request.getContextPath()%>\static\js\jquery.validate.js"></script>
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
                        url: "<%=request.getContextPath()%>/auth/user/distinct",
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
                nickName:{
                    required:true,
                    minlength:2
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
                        url: "<%=request.getContextPath()%>/auth/user/distinct",
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
                        url: "<%=request.getContextPath()%>/auth/user/distinct",
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
                nickName:{
                    required:"请填写昵称",
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
                },
            },
        })
    })

    $.validator.setDefaults({
        submitHandler: function() {
            var index = layer.load(1,{shade:0.5});
            var pwd = md5($("#pwd").val());
            var salt = $("#salt").val();
            var md5pwd = md5(pwd + salt);
            $("#pwd").val(md5pwd);
            $.post("<%=request.getContextPath()%>/auth/user/add",
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
                        parent.window.location.href = "<%=request.getContextPath()%>/auth/user/toShow";
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

</head>
<body>
<form id="fm">
    <input type="hidden" name="isDel" value="1">
    <input type="hidden" name="status" value="11">
    <input type="hidden" name="salt" value="${salt}" id="salt">
    <input type="hidden" name = "_method" value = "POST">
    用户名:<input type="text" name="userName" id="userName"><br />
    昵称:<input type="text" name="nickName" id="nickName"><br />
    密码:<input type="password" name="password" id="pwd"><br />
    确认密码:<input type="password" name="password2"><br />
    手机:<input type="text" name="phone" id="phone"><br />
    邮箱:<input type="text" name="email" id="email"><br />
    性别:
    <c:forEach items="${baseDataSexList}" var="s">
        <input type = "radio" name = "sex" value = "${s.baseId}">${s.name}
    </c:forEach>
    <br/>
    级别:
    <c:forEach items="${roleList}" var="t">
        <input type = "radio" name = "type" value = "${t.roleId}">${t.roleName}
    </c:forEach>
    <br/>
    <a href="<%=request.getContextPath()%>/auth/user/toLogin">已有账号?前去登录</a><br/>
    <input type="submit" value="注册">
</form>
</body>
</html>