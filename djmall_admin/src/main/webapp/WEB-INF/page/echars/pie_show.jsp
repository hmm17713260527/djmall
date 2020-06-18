<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/16
  Time: 11:10
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
    <script type="text/javascript">


        $(function() {
            show();
        });

        function show() {
            $.get("<%=request.getContextPath() %>/auth/user/pieShow",
                {},
                function(data){
                    layer.msg(data.msg);
                    if (data.code != 200) {
                        return;
                    }

                    var months = [];
                    var peopleNumbers = [];
                    for(var i = 0; i < data.data.length; i++){
                        months.push(data.data[i].productName);
                        peopleNumbers.push(data.data[i].echarsCount);
                    }

                    var res=[];
                    $.each(data.data,function(key,v){
                        res.push({
                            value:v.echarsCount,
                            name:v.productName
                        });
                    });



                    var myChart = echarts.init(document.getElementById('chartmain'));
                    myChart.clear();

                    option = {
                        title: {
                            text: '各商品订单比例',
                            subtext: '纯属虚构',
                            left: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: '{a} <br/>{b} : {c} ({d}%)'
                        },
                        legend: {
                            orient: 'vertical',
                            left: 'left',
                            data: months
                        },
                        series: [
                            {
                                name: '各商品订单比例',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data:res,
                                //  data: [
                                //     {value: data.data[0].echarsCount, name: data.data[0].productName},
                                //     {value: data.data[1].echarsCount, name: data.data[1].productName},
                                //     {value: data.data[2].echarsCount, name: data.data[2].productName},
                                //     {value: data.data[3].echarsCount, name: data.data[3].productName},
                                //     {value: data.data[4].echarsCount, name: data.data[4].productName}
                                // ],




                    emphasis: {
                                    itemStyle: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    };

                    myChart.setOption(option);

                })

        }


    </script>
</head>
<body>
<div style="border:2px solid #666;width:49%;height:450px;float:left" id="chartmain"></div>

</body>
</html>
