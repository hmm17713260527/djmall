<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery.validate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\cookie.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
<script type="text/javascript">

    jQuery.validator.addMethod("email",
        function(value, element) {
            var tel = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
            return tel.test(value)
        }, "请正确填写您的邮箱编号");


    $(function(){
        $("#fm").validate({
            //效验规则
            rules: {
                nickName:{
                    required:true,
                    minlength:2
                },
                email:{
                    required:true,
                    email:5
                }
            },
            messages:{
                nickName:{
                    required:"请填写昵称",
                    minlength:"最少两个字"
                },
                email:{
                    required:"请填写你的邮箱",
                    email:"邮箱格式不对"
                }
            }
        })
    });

        $.validator.setDefaults({
            submitHandler: function() {
                var formData = new FormData($("#fm")[0]);
                var index = layer.load();
                $.ajax({
                    url:'<%=request.getContextPath()%>/platform/auth/platUpdateUser?TOKEN='+getToken(),
                    dataType:'json',
                    type:'POST',
                    data: formData,
                    processData : false, // 使数据不做处理
                    contentType : false, // 不要设置Content-Type请求头信息
                    success: function(data){
                        layer.msg(data.msg,function () {
                            layer.close(index);
                            if (data.code != 200) {
                                return;
                            }
                            window.location.href = "<%=request.getContextPath()%>/platform/auth/toIndex?TOKEN=" + getToken();
                        })
                    }
                });
            }
        });


    function authName() {
        var userName = $("#userName").val();
        var nickName = $("#nickName").val();
        if (userName == nickName) {
            layer.msg("昵称不能与用户名一致！！！");
            return;
        }
    }

</script>
    <style>
        .error{
            color:red;
        }
    </style>
</head>
<body>

<form id = "fm">
    <input type="hidden" name = "userId" value="${user.userId}"/>
    <input type="hidden" id="userName" name = "userName" value="${user.userName}"/>
    昵称:<input type = "text" id="nickName" name = "nickName" value="${user.nickName}" onblur="authName()"/><br/>
    头像：<img src="http://q9cgmldxi.bkt.clouddn.com/${user.img}" width="100px" height="100px">
    <input type = "file" name = "file"/><br/>
    邮箱:<input type = "text" id="email" name = "email" value="${user.email}"/><br/>
    性别:<input type = "radio" name = "sex" value="MAN" <c:if test = "${user.sex == 'MAN'}">checked</c:if>/>男
    <input type = "radio" name = "sex" value="WOMAN" <c:if test = "${user.sex == 'WOMAN'}">checked</c:if>/>女<br/>
    <input type="submit" value="修改"/>
</form>

</body>
</html>
