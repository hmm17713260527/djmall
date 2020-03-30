<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/6
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Title</title>
<style type="text/css">
    #a01 {
        text-decoration: none;
        color: red;
    }

    #a02 {
        text-decoration: none;
        color: black;
    }
</style>
<style type="text/css">
    #bgc {
        color: red;
    }
</style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>\static\zTree\zTree_v3\css\zTreeStyle\zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\zTree\zTree_v3\js\jquery.ztree.core.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\zTree\zTree_v3\js\jquery.ztree.exedit.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\zTree\zTree_v3\js\jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\zTree\zTree_v3\js\jquery.ztree.all.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
<script type="text/javascript">
    var treeObj;
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true,
                pIdKey: "pid",
            }
        },

    };

    $(function () {
        $.get(
            "<%=request.getContextPath()%>/auth/role/roleResources/${roleId}",
            function (result) {
                treeObj = $.fn.zTree.init($("#tree"), setting, result.data);
            }
        )
    })

    function update() {
        var index = layer.load(1,{shade:0.5});
        var nodes = treeObj.getCheckedNodes(true);//获取全部被勾选的树节点
        var resourceIds = "";
        for (var i = 0; i < nodes.length; i++) {
            resourceIds += "," + nodes[i].id;
            //resourceIds += nodes[i].id + ",";
        }
        /* resourceIds=resourceIds.substr(0,nodes.length-1);*/
        resourceIds = resourceIds.substring(1);

        $.post(
            "<%=request.getContextPath()%>/auth/role/update/${roleId}",
            {"resourceIds": resourceIds},
            function (data) {

                if (data.code == 200) {
                    layer.msg(data.msg, {
                        icon: 6,
                        time: 1000 //1秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        window.parent.location.reload();
                    });
                } else {
                    layer.msg(data.msg, {icon: 5})
                    layer.close(index);
                }
            }
        )

    }

</script>
</head>
<body>

    <div id="tree" class="ztree"></div>
    <input type="button" value="保存" onclick="update()">

</body>
</html>
