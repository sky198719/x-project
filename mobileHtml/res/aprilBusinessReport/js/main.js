require(['base', "requirejs", "trackBase", "echarts_simple", "Swiper"], function ($, requirejs, track, echarts, Swiper) {

    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

        if (arr = document.cookie.match(reg))

            return (arr[2]);
        else
            return null;
    }

    $.ajax({
        url     : '/feapi/users/loginInfo?userToken=' + getCookie('userToken'),
        dataType: 'json',
        async   : false,
        data    : {},
        type    : 'GET',
        success : function (str) {
            if(str.code == '200000'){
                track.init(str.data);
            }else {
                track.init();
            }
        },
        error:function (str) {
            track.init();
        }
    });

    var mySwiper = new Swiper('.swiper-container', {
        direction          : 'vertical',
        pagination         : '.swiper-pagination',
        mousewheelControl  : true,
        watchSlidesProgress: true,
        onSlideChangeEnd   : function (swiper) {
//            alert(swiper.activeIndex); //切换结束时，告诉我现在是第几个slide
            if (swiper.activeIndex === 1) {
                // amNumber('am_number1');
                // amNumber('am_number2');
            } else if (swiper.activeIndex === 2) {
                // amNumber('am_number3');
                // amNumber2('am_number4', 4);
                // amNumber('am_number5');
            } else if (swiper.activeIndex === 4) {
                // amNumber('am_number6');
                // amNumber('am_number7');
                // amNumber('am_number8');
                // amNumber('am_number9');
                // amNumber2('am_number10', 6);
            }

            if (swiper.activeIndex === 14) {
                $('.ui-arrow').addClass('isend');
            } else {
                $('.ui-arrow').removeClass('isend');
            }
        }
    });


    // page3
    var myChart      = echarts.init(document.getElementById('page3-chart-canvas'));
    var page3_chart1 = {
        legend: {
            data: [
                '商业贸易 39.1%',
                '食品餐饮 8.7%',
                '建筑建材 5.8%',
                '交通运输 5.8%',
                '机械设备 4.3%',
                '电子信息 2.9%',
                '金属制品 2.9%',
                '纺织轻工 1.5%',
                '化学化工 1.5%',
                '酒店旅游 1.5%',
                '其它 26%']
        },
        color : [
            '#ffb10b',
            '#f9df18',
            '#fff182',
            '#5762f7',
            '#8e95f8',
            '#bdc0ed',
            '#7d97b3',
            '#5a8cc4',
            '#799fca',
            '#c7dcf5',
            '#fffef1'
        ],
        series: [
            {
                name      : '访问来源',
                type      : 'pie',
                center    : ['50%', '50%'],
                radius    : ['44%', '49%'],
                startAngle: '75',
                itemStyle : {
                    normal: {
                        label: {
                            show: false
                        }
                    }
                },
                data      : [
                    {value: 22.8, name: '商业贸易 22.8%'},
                    {value: 14.1, name: '建筑建材 8.7%'},
                    {value: 8.7, name: '食品餐饮 5.8%'},
                    {value: 8.2, name: '纺织轻工 5.8%'},
                    {value: 7.1, name: '金属制品 4.3%'},
                    {value: 4.9, name: '电子信息 2.9%'},
                    {value: 4.9, name: '农林牧渔 2.9%'},
                    {value: 4.4, name: '机械设备 1.5%'},
                    {value: 3.8, name: '信息服务 1.5%'},
                    {value: 3.3, name: '交通运输 1.5%'},
                    {value: 17.8, name: '其它 26%'}

                ]
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(page3_chart1);


    var myChart6     = echarts.init(document.getElementById('page6-chart-canvas'));
    var page6_chart1 = {
        legend: {
            data: [
                '90后 11.5%',
                '80后 30.2%',
                '70后 22.6%',
                '60后 18.9%',
                '60前 16.8%'
            ]
        },
        color : [
            '#f9df18',
            '#fff182',
            '#85a0be',
            '#9ea5ff',
            '#5762f7'
        ],
        series: [
            {
                name     : '访问来源',
                type     : 'pie',
                center   : ['51%', '50%'],
                radius   : ['66%', '72%'],
                itemStyle: {
                    normal: {
                        label: {
                            show: false
                        }
                    }
                },
                data     : [
                    {value: 11.5, name: '90后 11.5%'},
                    {value: 30.2, name: '80后 30.2%'},
                    {value: 22.6, name: '70后 22.6%'},
                    {value: 18.9, name: '60后 18.9%'},
                    {value: 16.8, name: '60前 16.8%'}

                ]
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart6.setOption(page6_chart1);


    // function amNumber(id) {
    //     var $id     = $('#' + id);
    //     var data    = $id.data('nb');
    //     var newDate = parseInt(data.replace(/,/g, ''));
    //
    //     var i = 16;
    //
    //     function rNumber() {
    //         i--;
    //         if (i > 0) setTimeout(function () {
    //             $id.html(addCommas(parseInt(newDate / i)));
    //             rNumber()
    //         }, 70);
    //     }
    //
    //     // rNumber();
    //
    //     // function addCommas(nStr) {
    //     //     nStr += '';
    //     //     x       = nStr.split('.');
    //     //     x1      = x[0];
    //     //     x2      = x.length > 1 ? '.' + x[1] : '';
    //     //     var rgx = /(\d+)(\d{3})/;
    //     //     while (rgx.test(x1)) {
    //     //         x1 = x1.replace(rgx, '$1' + '$2');
    //     //     }
    //     //     return x1 + x2;
    //     // }
    // }

    // function amNumber2(id, cs) {
    //     var $id  = $('#' + id);
    //     var data = $id.data('nb');
    //
    //     var i = cs;
    //
    //     function rNumber() {
    //         i--;
    //         if (i > -1) setTimeout(function () {
    //             $id.html(((data*100) - (i*100))/100);
    //             rNumber();
    //         }, 100);
    //     }
    //     rNumber();
    // }
    /*
     var myChart9     = echarts.init(document.getElementById('page9-chart-canvas'));
     var page9_chart1 = {
     legend: {
     data: [
     '90后 14.3%',
     '80后 32.3%',
     '70后 20.1%',
     '60后 17.3%',
     '60前 16%'
     ]
     },
     color : [
     '#945d28',
     '#c58b53',
     '#e8b98b',
     '#945d28',
     '#c58b53'
     ],
     series: [
     {
     name     : '访问来源',
     type     : 'pie',
     center   : ['51%', '50%'],
     radius   : ['50%', '56%'],
     itemStyle: {
     normal: {
     label: {
     show: false
     }
     }
     },
     data     : [
     {value: 14.3, name: '90后 14.3%'},
     {value: 32.3, name: '80后 32.3%'},
     {value: 20.1, name: '70后 20.1%'},
     {value: 17.3, name: '60后 17.3%'},
     {value: 16.0, name: '60前 16.0%'}

     ]
     }
     ]
     };

     // 使用刚指定的配置项和数据显示图表。
     myChart9.setOption(page9_chart1);


     var myChart10     = echarts.init(document.getElementById('page10-chart-canvas'));
     var page10_chart1 = {
     legend: {
     data: [
     '活期 25.1%',
     '1月期 20.6%',
     '3月期 5.9%',
     '6月期 3.1%',
     '12月期 32.3%',
     '其他 10.1%']
     },
     color : [
     '#945d28',
     '#945d28',
     '#e8b98b',
     '#c58b53',
     '#945d28',
     '#c58b53'
     ],
     series: [
     {
     name     : '访问来源',
     type     : 'pie',
     center   : ['51。5%', '50%'],
     radius   : ['56%', '62%'],
     itemStyle: {
     normal: {
     label: {
     show: false
     }
     }
     },
     data     : [
     {value: 26.2, name: '活期 26.2%'},
     {value: 26.2, name: '1月期 26.2%'},
     {value: 5.9, name: '3月期 5.9%'},
     {value: 3.1, name: '6月期 3.1%'},
     {value: 32.3, name: '12月期 32.3%'},
     {value: 10.1, name: '其他 10.1%'}

     ]
     }
     ]
     };

     // 使用刚指定的配置项和数据显示图表。
     myChart10.setOption(page10_chart1);

     */




}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});