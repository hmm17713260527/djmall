<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/7
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<title>Title</title>
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
        data: {
            simpleData: {
                enable: true,
                pIdKey: "parentId",
                idKey: "resId"
            },
            key: {
                name: "resourceName",
                url: "noexist"

            }
        },

    };

    $(function () {
        $.get(
            "<%=request.getContextPath()%>/auth/res/show",
            function (result) {
                treeObj = $.fn.zTree.init($("#tree"), setting, result.data);
            }
        )
    })

    //增二级节点，现在已改，一级二级都可以增
    function saveTwo() {
        var treeOb = $.fn.zTree.getZTreeObj("tree");
        var selectNodes = treeOb.getSelectedNodes();
        if (selectNodes.length > 0) {
            var pId = selectNodes[0].resId;
        } else {
            var pId = 0;
        }
        layer.open({
            type: 2,
            titel: "新增",
            area: ["400px", "500px"],
            content: "<%=request.getContextPath()%>/auth/res/toAdd/" + pId,
            end: function () {
                location.reload();
            }
        });
    }

    function updateRes(value) {
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var selectNode = treeObj.getSelectedNodes();
        if (selectNode.length <= 0) {
            layer.msg("至少选择一个节点操作");
            return false;
        }
        if (selectNode.length > 1) {
            layer.msg("只能选择一个节点操作");
            return false;
        }
        var value = selectNode[0].resId;
        layer.open({
            type: 2,
            titel: "修改",
            area: ["400px", "500px"],
            content: "<%=request.getContextPath()%>/auth/res/toUpdate/" + value,
        });
    }

    function deleRes() {
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var selectNode = treeObj.getSelectedNodes();
        if (selectNode.length <= 0) {
            layer.msg("至少选择一个节点操作");
            return false;
        }
        if (selectNode.length > 1) {
            layer.msg("只能选择一个节点操作");
            return false;
        }
        var value = selectNode[0].resId;


        layer.msg('确定删除?', {
            time: 0 //不自动关闭
            , btn: ['确定', '取消']
            , yes: function () {
                $.post(
                    "<%=request.getContextPath() %>/auth/res/delById",
                    {"id": value,  "_method" : "DELETE"},
                    function (data) {
                        if (data.code == 200) {
                            layer.msg(data.msg, {
                                icon: 6,
                            }, function () {
                                window.location.href = "<%=request.getContextPath()%>/auth/res/toShow";
                            });
                        } else {
                            layer.msg(data.msg, {icon: 5});
                        }
                    })
            }
        })
    }

    //递归自我调用
    function getChildNode(parentNode) {
        var ids = "";
        // 获取子节点
        var childs = parentNode.children;
        for (var i = 0; i < childs.length; i++) {
            ids += childs[i].id + ",";
            if (childs[i].isParent) {
                ids += getChildNode(childs[i]);
            }
        }
        return ids;
    }


</script>
</head>
<body>

    <shiro:hasPermission name="RESOURCE_ADD">
        <input type="button" value="新增" onclick="saveTwo()">
    </shiro:hasPermission>
    <shiro:hasPermission name="RESOURCE_UPDATE">
        <input type="button" value="修改" onclick="updateRes()">
    </shiro:hasPermission>
    <shiro:hasPermission name="RESOURCE_DEL">
        <input type="button" value="删除" onclick="deleRes()">
    </shiro:hasPermission>
    <div id="tree" class="ztree">

</div>

</body>
</html>
