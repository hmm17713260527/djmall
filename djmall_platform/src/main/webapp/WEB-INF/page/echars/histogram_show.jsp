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
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\token.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>\static\js\cookie.js"></script>
    <script type="text/javascript">



        $(function() {
            show();
        })

        function show() {
            token_post("<%=request.getContextPath() %>/platform/auth/histogramShow?TOKEN=" + getToken(),
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

                    //var yMax = 10;
                    option = {
                        title: {
                            text: '特性示例：渐变色 阴影 点击缩放',
                            subtext: '每天成交的订单'
                        },
                        xAxis: {
                            data: months,
                            axisLabel: {
                                inside: true,
                                textStyle: {
                                    color: '#fff'
                                }
                            },
                            axisTick: {
                                show: false
                            },
                            axisLine: {
                                show: false
                            },
                            z: 10
                        },
                        yAxis: {
                            axisLine: {
                                show: false
                            },
                            axisTick: {
                                show: false
                            },
                            axisLabel: {
                                textStyle: {
                                    color: '#999'
                                }
                            }
                        },
                        dataZoom: [
                            {
                                type: 'inside'
                            }
                        ],
                        series: [
                            { // For shadow
                                type: 'bar',
                                itemStyle: {
                                    normal: {color: 'rgba(0,0,0,0.05)'}
                                },
                                barGap:'-100%',
                                barCategoryGap:'40%',
                                data: peopleNumbers,
                                animation: false
                            },
                            {
                                type: 'bar',
                                itemStyle: {
                                    normal: {
                                        color: new echarts.graphic.LinearGradient(
                                            0, 0, 0, 1,
                                            [
                                                {offset: 0, color: '#83bff6'},
                                                {offset: 0.5, color: '#188df0'},
                                                {offset: 1, color: '#188df0'}
                                            ]
                                        )
                                    },
                                    emphasis: {
                                        color: new echarts.graphic.LinearGradient(
                                            0, 0, 0, 1,
                                            [
                                                {offset: 0, color: '#2378f7'},
                                                {offset: 0.7, color: '#2378f7'},
                                                {offset: 1, color: '#83bff6'}
                                            ]
                                        )
                                    }
                                },
                                data: peopleNumbers
                            }
                        ]
                    };

                    // Enable data zoom when user click bar.
                    var zoomSize = 6;
                    myChart.on('click', function (params) {
                        console.log(months[Math.max(params.dataIndex - zoomSize / 2, 0)]);
                        myChart.dispatchAction({
                            type: 'dataZoom',
                            startValue: months[Math.max(params.dataIndex - zoomSize / 2, 0)],
                            endValue: months[Math.min(params.dataIndex + zoomSize / 2, peopleNumbers.length - 1)]
                        });
                    });

                    myChart.setOption(option);
                })
        }



    </script>
</head>
<body>

<div style="border:2px solid #666;width:49%;height:450px;float:left" id="chartmain"></div>


</body>
</html>
