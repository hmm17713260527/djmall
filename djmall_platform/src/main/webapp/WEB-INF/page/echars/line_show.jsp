<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/16
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\echarts.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\cookie.js"></script>
    <script type="text/javascript">

        $(function() {
            show();
        });

        function show() {

            token_post("<%=request.getContextPath() %>/platform/auth/lineShow?TOKEN=" + getToken(),
                {},
                function(data){
                    layer.msg(data.msg);
                    if (data.code != 200) {
                        return;
                    }

                    var months = [];
                    var peopleNumbers = [];
                    for(var i = 0; i < data.data.length; i++){
                        months.push(data.data[i].echarsTime);
                        peopleNumbers.push(data.data[i].echarsCount);
                    }


                    var myChart = echarts.init(document.getElementById('chartmain'));
                    myChart.clear();

                    option = {
                        title: {
                            subtext: '每天登陆的用户量'
                        },
                        xAxis: {
                            type: 'category',
                            data: months
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [{
                            data: peopleNumbers,
                            type: 'line',
                            smooth: true
                        }]
                    };
                    //赋值
                    myChart.setOption(option);

                })
        }




    </script>
</head>
<body>

<div style="border:2px solid #666;width:49%;height:450px;float:left" id="chartmain"></div>

</body>
</html>
