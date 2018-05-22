require(['base', "requirejs", "trackBase","echarts_simple","Swiper"], function ($, requirejs,track,echarts,Swiper) {

    var mySwiper = new Swiper('.swiper-container', {
        direction          : 'vertical',
        pagination         : '.swiper-pagination',
        mousewheelControl  : true,
        watchSlidesProgress: true,
        onSlideChangeEnd   : function (swiper) {
//            alert(swiper.activeIndex); //切换结束时，告诉我现在是第几个slide
            if (swiper.activeIndex === 1) {
                amNumber('am_number1');
                amNumber('am_number2');
            } else if (swiper.activeIndex === 2) {
                amNumber('am_number3');
                amNumber2('am_number4', 4);
                amNumber('am_number5');
            } else if (swiper.activeIndex === 4) {
                amNumber('am_number6');
                amNumber('am_number7');
                amNumber('am_number8');
                amNumber('am_number9');
                amNumber2('am_number10', 6);
            }

            if (swiper.activeIndex === 18) {
                $('.ui-arrow').addClass('isend');
            } else {
                $('.ui-arrow').removeClass('isend');
            }
        }
    });

    function amNumber(id) {
        var $id     = $('#' + id);
        var data    = $id.data('nb');
        var newDate = parseInt(data.replace(/,/g, ''));

        var i = 16;

        function rNumber() {
            i--;
            if (i > 0) setTimeout(function () {
                $id.html(addCommas(parseInt(newDate / i)));
                rNumber()
            }, 70);
        }

        rNumber();

        function addCommas(nStr) {
            nStr += '';
            x       = nStr.split('.');
            x1      = x[0];
            x2      = x.length > 1 ? '.' + x[1] : '';
            var rgx = /(\d+)(\d{3})/;
            while (rgx.test(x1)) {
                x1 = x1.replace(rgx, '$1' + ',' + '$2');
            }
            return x1 + x2;
        }
    }

    function amNumber2(id, cs) {
        var $id  = $('#' + id);
        var data = $id.data('nb');

        var i = cs;

        function rNumber() {
            i--;
            if (i > -1) setTimeout(function () {
                $id.html(((data*100) - (i*100))/100);
                rNumber();
            }, 100);
        }
        rNumber();
    }

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
            if(str.code == 200000){
                if (str.data.status.code === 200) {
                    //result = true;
                    track.init(str.data);
                }else {
                    track.init();
                }
            }

        },
        error:function (str) {
            track.init();
        }
    });

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});