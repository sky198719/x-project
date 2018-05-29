define(['js/plan/planUtils','js/utils/animateNumber'], function (PlanUtils,animateNumber) {
    var planSwiperIntervalArr = [];
    var planSwiperView = {
        init: function (params) {
        },
        bindEvents: function (param) {
            appFunc.bindEvents(param.bindings);
        },
        initXplanSwiper: function (data) {
            var html = '';
            var list = data.listData;
            if (typeof(list) != "undefined" && list.length > 0) {
                for (var k = 0; k < list.length; k++) {
                    var b = list[k];

                    if (b.STATUS == 5) {
                        continue;
                    }
                    if (b.schemeStatus < 4) {
                        html += '<div class="swiper-slide xyb-swiper-slide">';
                            html += '<div class="xyb-container">';
                                html += '<h4 class="xyb-title">' + PlanUtils.schemeType(b.TYPE) + ' - ' + b.PNAME + '</h4>';
                                html += '<div class="xyb-img">';
                                    html += '<img src="static/img/xxd/xyb-bg_03.png">';
                                    html += '<h5>预期最高年化收益</h5>';
                                    html += '<h1><span  id="planApr'+ k +'" name="planApr" data-maxapr="'+b.MAXAPR+'">0</span><span style="font-size:30px;">%</span></h1>';
                                html += '</div>';
                                html += '<h5 class="xyb-h5">计划金额：<span class="font18">' + appFunc.fmoney(b.ACCOUNT, 2) + '</span> 元</h5>';
                                html += '<h5 class="xyb-h5">锁定期限：<span class="font-red font18">' + b.CLOSETERM + '</span> 个月</h5>';
                                html += '<h5 class="xyb-h5">保障方式：<span class="font-green">本金 + 利息</span></h5>';

                                if (b.schemeStatus == 0) {
                                    html += "<a href='#' data-id='" + b.SCHEMEID + "' class='btn-standard btn-xyb' presalebegin='" + b.PRESALEBEGIN + "' name='presale_" + b.SCHEMEID + "'>待预定</a>";
                                    html += "<span class='btn-standard btn-xyb' id='presale_" + b.SCHEMEID + "_time' style='display: none'></span>";
                                } else {
                                    if (b.REMACOUNT == 0) {
                                        html += "<a href='#' data-id='" + b.SCHEMEID + "' class='btn-standard btn-xyb'>     已满额     </a>";
                                    } else {
                                        html += "<a href='#' data-id='" + b.SCHEMEID + "' class='btn-standard btn-xyb'>     去加入    </a>";
                                    }
                                }
                            html += '</div>';
                        html += '</div>';
                    }
                }

                if (html == '') {
                    $$('div[name="xyb-none-div"]').show();
                }

                html = '<div class="swiper-container xyb-swiper-container" name="xyb-swiper-container">' +
                        '<div class="swiper-wrapper xyb-swiper-wrapper" name="xyb-swiper-wrapper">' + html;

                html += '</div>' +
                            '<div class="swiper-pagination xyb-swiper-pagination" id="xyb-swiper-pagination"></div>' +
                        '</div>';

                $$('#planSwiperContent').html(html);
                $$(".xyb-container span[name='planApr']").each(function(index,value){
                    animateNumber.animate({
                        element:'#'+value.id,
                        from:0,
                        to:value.dataset.maxapr,
                        steps:10,
                        intervalNumber:100
                    });
                });


                for (var j = 0; j < planSwiperIntervalArr.length; j++) {
                    clearInterval(planSwiperIntervalArr[j]);
                }
                planSwiperIntervalArr = [];

                var presaleList = $("a[name^='presale_']");
                if (typeof(presaleList) != "undefined" && presaleList.length > 0) {
                    try {
                        // 当前时间
                        var currentDateTime = new Date().getTime();
                        planSwiperIntervalArr.push(window.setInterval(function () {
                            currentDateTime = new Number(currentDateTime) + 1000;
                        }, 1000));
                        presaleList.each(function () {
                            var presalebegin = $(this).attr("presalebegin");
                            var thisName = $(this).attr("name");

                            planSwiperIntervalArr.push(window.setInterval(function () {
                                var date = new Date(new Number(currentDateTime));
                                var currentDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
                                var timeStr = appFunc.getTime(currentDate, presalebegin, 'color:#fff;font-size:15px;',{dd:true,HH:true,mm:true,ss:true});
                                if (timeStr == "0") {
                                    $("a[name='" + thisName + "']").show();
                                    $("#" + thisName + "_time").hide();
                                    $("a[name='" + thisName + "']").html("&nbsp;&nbsp;&nbsp;&nbsp;去加入&nbsp;&nbsp;&nbsp;&nbsp;");
                                } else {
                                    $("a[name='" + thisName + "']").hide();
                                    $("#" + thisName + "_time").show();
                                    $("#" + thisName + "_time").html(timeStr + "后开始");
                                }
                            }, 1000));
                        });
                    } catch (e) {
                        console.log('_定时器异常');
                    }
                }

                var mySwiper = xxdApp.swiper('div[name="xyb-swiper-container"]', {
                    pagination: '#xyb-swiper-pagination',
                    spaceBetween: 50,// 50px滑动间隙
                    cube: {
                        slideShadows: true,
                        shadow: true,
                        shadowOffset: 20,
                        shadowScale: 0.94
                    }
                });
            } else {
                $$('div[name="xyb-none-div"]').show();
            }
        }

    };
    return planSwiperView;
});
