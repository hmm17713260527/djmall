<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/10
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>Title</title>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jsencrypt.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
<script src="<%=request.getContextPath()%>\static\js\jquery.validate.js"></script>
</head>
<script type="text/javascript">

    $(function() {
        $("#fm").validate({

            rules:{
                freight:{
                    required:true,
                    digits:true
                }
            },
            messages:{
                freight:{
                    required:"不能为空",
                    digits:"只能是数字"
                }
            }


        })
    })

    function update() {
        var index = layer.load(0, {shade:0.5});
        $.post(
            "<%=request.getContextPath()%>/dict/freight/update",
            $("#fm").serialize(),
            function(data){
                layer.close(index);
                layer.msg(data.msg, function(){
                    if (data.code != 200) {
                        return;
                    }
                    parent.window.location.href = "<%=request.getContextPath()%>/dict/freight/toShow";
                });

            })
    }


</script>
<style>
    .error{
        color:red;
    }
</style>
<body>

<form id = "fm">
    <input type="hidden" name = "_method" value = "PUT">
    <input type="hidden" name = "freightId" value="${freight.freightId}"/>
    运费：<input type = "text" name = "freight" value="${freight.freight}"/><br/>
    <input type="button" value="修改" onclick="update()"/>
</form>

</body>
</html>
