<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/7
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
    <script src="<%=request.getContextPath()%>\static\js\jquery.validate.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
    <script type="text/javascript">

        $(function(){
            $("#fm").validate({
                //效验规则
                rules: {
                    resourceName:{
                        required:true,
                        minlength:2,
                        remote: {
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/auth/res/findResourceByName",
                            data:{
                                resourceName:function() {
                                    return $("#resourceName").val();
                                },
                                dataType:"json",
                                dataFilter:function(data,type){
                                    if (data == 'true'){
                                        return true;
                                    }else {
                                        return false;
                                    }
                                }
                            }
                        }
                    },
                    resourceCode:{
                        required:true,
                        minlength:6,
                        remote: {
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/auth/res/findResourceByCode",
                            data:{
                                resourceCode:function() {
                                    return $("#resourceCode").val();
                                },
                                dataType:"json",
                                dataFilter:function(data,type){
                                    if (data == 'true'){
                                        return true;
                                    }else {
                                        return false;
                                    }
                                }
                            }
                        }
                    },
                },
                messages:{
                    resourceName:{
                        required:"名字必填",
                        minlength:"最少两个字儿"
                    },
                    resourceCode:{
                        required:"编码必填",
                        minlength:"最少6个字儿"
                    },
                },
            })
        })

        $.validator.setDefaults({
            submitHandler: function() {
                if ($("#parentId").val() != ${parentId}) {
                    layer.msg("上级节点不得修改", {icon: 5});
                    return;
                }
                var index = layer.load(1,{shade:0.5});
                /* layer.confirm('确定添加吗?', {icon: 3, title:'提示'}, function(index){ */
                $.post("<%=request.getContextPath()%>/auth/res/add",
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
                            layer.close(index);
                            parent.window.location.href = "<%=request.getContextPath()%>/auth/res/toShow";
                        });
                    }
                )
                /*  });
                 layer.close(index); */
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
    <input type="text" name="parentId" value="${parentId}" placeholder="上级节点" id="parentId"><br>
    <input type="text" name="resourceName" id="resourceName" placeholder="资源名称"><br />
    <input type="text" name="url" id="url" placeholder="URL"><br />
    <input type="text" name="resourceCode" id="resourceCode" placeholder="资源编码"><br />
    <select name="type">
        <option value="1">菜单</option>
        <option value="0">按钮</option>
    </select>
    <br/>
    <input type="hidden" name="isDel" value="1">
    <input type="submit" value="添加">
</form>

</body>
</html>
