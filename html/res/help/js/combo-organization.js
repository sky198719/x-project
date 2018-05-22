require(['base','echartsAll','float','trackBase','store','header','footer','backTop',"requirejs", 'chinaMap', 'countUp'], function($, echarts, float, track, store){
    var timer = new Date();
    var myTime = timer.getTime(); //本地时间
    var educationColor1 = ['#f49d56','#8ca1d1','#c6d11f'];
    var ageColor1 = ['#8ca1d1','#f49d56','#c6d11f'];
    var educationColor2 = ['#c6d11f','#97b8e0','#99d7eb'];
    var ageColor2 = ['#97b8e0','#c6d11f','#99d7eb'];
    var educationColor3 = ['#c6d11f','#97b8e0','#99d7eb'];
    var ageColor3 = ['#97b8e0','#c6d11f','#99d7eb'];

    function animates(obj,colors,titles,datas){
        var dom = document.getElementById(obj);
        var myChart = echarts.init(dom);
        option = {
            title: {
                text: titles,
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{b}:{d}%"
            },
            legend: {
                orient: 'horizontal',
                y: 'bottom',
            },
            color: colors,
            series: [
                {
                    type:'pie',
                    selectedMode: 'single',
                    radius: [0, '30%'],
                    label: {
                        normal: {
                            position: 'inner'
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    }
                },
                {
                    type:'pie',
                    radius: ['40%', '55%'],
                    label: {
                        normal: {
                            formatter: '{per|{d}%}',
                            rich: {
                                per: {
                                    color: '#000'
                                }
                            }
                        }
                    },
                    data:datas
                }
            ]
        };
        myChart.setOption(option, true);
    }

    $.ajax({
        url:'/biz/bulletin/organizationData',
        type:'get',
        async:false,
        cache:false,
        beforeSend:function(request){
            request.setRequestHeader('clientId','XXD_FRONT_END');
            request.setRequestHeader('clientTime',myTime);
            request.setRequestHeader('s','s');
        },
        success:function(data){
            if(data.code == '200000'){
                var educationData1 = new Array();
                $.each(data.data.employeeEducationItems,function(index,item){
                    var edu1 = {value:item.nvalue,name:item.inforName};
                    educationData1.push(edu1);
                });
                var ageData1 = new Array();
                $.each(data.data.employeeAgeItems,function(index,item){
                    var age1 = {value:item.nvalue,name:item.inforName};
                    ageData1.push(age1);
                });
                var educationData2 = new Array();
                $.each(data.data.riskControlEducationItems,function(index,item){
                    var edu2 = {value:item.nvalue,name:item.inforName};
                    educationData2.push(edu2);
                });
                var ageData2 = new Array();
                $.each(data.data.riskControlAgeItems,function(index,item){
                    var age2 = {value:item.nvalue,name:item.inforName};
                    ageData2.push(age2);
                });
                var educationData3 = new Array();
                $.each(data.data.itEducationItems,function(index,item){
                    var edu3 = {value:item.nvalue,name:item.inforName};
                    educationData3.push(edu3);
                });
                var ageData3 = new Array();
                $.each(data.data.itAgeItems,function(index,item){
                    var age3 = {value:item.nvalue,name:item.inforName};
                    ageData3.push(age3);
                });
                animates('education1',educationColor1,'员工学历结构',educationData1);
                animates('age1',ageColor1,'员工年龄结构',ageData1);
                animates('education2',educationColor2,'风控团队学历结构',educationData2);
                animates('age2',ageColor2,'风控团队年龄结构',ageData2);
                animates('education3',educationColor3,'技术团队学历结构',educationData3);
                animates('age3',ageColor3,'技术团队年龄结构',ageData3);

                $('#organizationDate').html(data.data.organizationDate);
                $('#employeeDate').html(data.data.employeeDate);
                $('#riskControlDate').html(data.data.riskControlDate);
                $('#itDate').html(data.data.itDate);
                $.each(data.data.items,function(index,item){
                    if(item.code == 'EMPLOYEE'){
                        $('#employeeAmount').html(item.nvalue);
                    }else if(item.code == 'IT_PROPORTION'){
                        $('#itPercent').html(item.nvalue);
                    }else if(item.code == 'RISK_CONTROL_PROPORTION'){
                        $('#riskPercent').html(item.nvalue);
                    }
                });
            }
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });
});
