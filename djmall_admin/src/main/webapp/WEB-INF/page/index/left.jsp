<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>left</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>\static\zTree\zTree_v3\css\zTreeStyle\zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>\static\zTree\zTree_v3\js\jquery.ztree.core.js"></script>
</head>
<script type="text/javascript">
	var setting = {
		async: {
			enable: true,//接收json格式数据
			url:"<%=request.getContextPath()%>/auth/res/toList",//路径
			autoParam:["id"],//返回的参数
		},
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
		callback: {
			onClick: function (event, treeId, treeNode) {
				if (!treeNode.isParent) {
					parent.right.location.href = "<%=request.getContextPath()%>" + treeNode.url;
				}
			}
		}
	};

	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting);
	});
</script>
<body align="center">

<div id="treeDemo" class="ztree">

</div>

</body>
</html>