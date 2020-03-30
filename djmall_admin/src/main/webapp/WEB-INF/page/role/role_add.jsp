<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/5
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Title</title>
    <title>Insert title here</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\md5-min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
    <script src="<%=request.getContextPath()%>\static\js\jquery.validate.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    <script type="text/javascript">

        $(function() {
            $("#fm").validate({

                rules:{
                    roleName:{
                        required:true,
                        minlength:2,
                        remote: {
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/auth/role/findRoleByName",
                            data:{
                                roleName:function() {
                                    return $("#roleName").val();
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
                    }
                },
                messages:{
                    roleName:{
                        required:"不能为空",
                        minlength:"最少两个字",
                        remote:"已存在"
                    }
                }


            })
        })



        $.validator.setDefaults({
            submitHandler: function() {
                var index = layer.load(0, {shade:0.5});
                $.post("<%=request.getContextPath() %>/auth/role/add",
                    $("#fm").serialize(),
                    function(data){
                        layer.close(index);
                        layer.msg(data.msg, function(){
                            if (data.code != 200) {
                                return;
                            }
                            parent.window.location.href = "<%=request.getContextPath()%>/auth/role/toShow";
                        });
                    })
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

<form id = "fm">
    <input type="hidden" name = "_method" value = "POST">
    角色名:<input type = "text" name = "roleName" id = "roleName"/><br/>
    <input type = "hidden" name = "isDel" value = "1"/><br/>
    <input type = "submit"/>

</form>

</body>
</html>
