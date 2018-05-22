/**
 * Created by wangzhijie on 24/01/2018.
 */
require(['base', 'float', 'trackBase', 'store', "requirejs", "paging","allRoll"], function ($, float, track, store,requirejs) {

    var dataUrl= {
        activityCode:'February-18-vip-activity',
        getLatestAwardsList:'/activityCenter/activityBase/getLatestMaterailAwardsList', // 所有的中奖列表
        getActivityStatus:'/activityCenter/activityBase/getActivityStatus',   //活动状态
        isLogin:'/feapi/users/loginInfo',
        infoUrl:'/feapi/users/accountInfo',    //获取用户登录成功之后的信息
        commonUrl:'/tradeCenter/investBiz/showStatus/',
        xybUrl:'/tradeCenter/XYB/brief',
        operationDataUrl: '/biz/bulletin/operationData'
    };
    var timeR=null,timer=null,result=false,token=$.readCookie('Token');
    var ux= {
        allPrizes:function() {
            db.getPrize({
                cb:function(r) {
                    var aLis = '';
                    $.each(r, function (i, item) {
                        aLis+='<li><span class="username">' + item.username + '</span><span class="prizename">' + item.prizename + '</span></li>';
                    });
                    $('#J_winnerLists').html(aLis);
                    if($("#J_winnerLists li").length >= 3){
                        $('#J_WinData').marquee({
                            direction:'left',
                            speed:'20'
                        })
                    }
                }

            });
            function getAllPrizes() {
                db.getPrize({
                    cb:function(r) {
                        var aLis = '';
                        $.each(r, function (i, item) {
                            aLis+='<li><span class="username">' + item.username + '</span><span class="prizename">' + item.prizename + '</span></li>';
                        });
                        $('#J_winnerLists').html(aLis);
                    }

                })
            }
            getAllPrizes();
            clearInterval(timeR);
            timeR = setInterval(function () {
                getAllPrizes();
            }, 600000);
        },
        isLogin:function () {
            db.isLogin({
                cb:function (r) {
                    if(r){
                        //如果登录成功  注册框改变
                        ux.userInfo();
                        db.activeState({
                            cb: function (r) {
                                if (r === 0 || r===-1) {
                                    //底部隐藏  登录并且活动未开始或者开始中
                                    $("#J_registerMainBottom").addClass('dishidden');
                                }else{
                                    //登录但是活动已经结束  固定底部
                                    ux.scrollFun();
                                }
                            }
                        });
                    }else{
                        //活动未开始且开始中 点击底部按钮滚动到顶部
                        ux.toTop();
                        //未登录 固定底部
                        ux.scrollFun();
                    }
                }
            })
        },
        userInfo:function () {
              db.userInfo({
                 cb:function (r) {
                     if(r.data){
                         var dataInfo=r.data;
                         $("#J_register").addClass('disnone');
                         $("#J_userAccount").html(dataInfo.name);
                         $("#J_registered").removeClass('disnone');
                     }
                 }
              })
        },
        scrollFun:function () {
            $(window).scroll(scrolls);
            scrolls();
            function scrolls() {
                var h = $(document).height();
                var t =document.documentElement.scrollTop || document.body.scrollTop;
                var wh = $(window).height();
                // if(t>93){
                //     $('#J_headerFixed').removeClass('disnone');
                // }else{
                //     $('#J_headerFixed').addClass('disnone');
                // }
                if(t>=675 && h-t-wh>200){
                    $("#J_lastRegister").removeClass("disnone");
                }else{
                    $("#J_lastRegister").addClass("disnone");
                }
            }
        },
        toTop:function () {

            $("#J_registerBottom,#J_specialBtn").on('click',function (ev) {
                //活动结束  点击无效
                if(!$(this).hasClass('activity-end')){
                    $('body,html').animate({scrollTop: 0}, 1000);
                }
            });
            // $("#J_registerBottom,#J_specialBtn").on('click',function (ev) {
            //     db.isLogin({
            //         cb:function (result) {
            //             if(!result){
            //                 $('body,html').animate({scrollTop: 0}, 1000);
            //             }
            //
            //         }
            //     })
            // });

        },
        personClick:function () {
            $("#J_rules").on('click',function(){
                timer=setTimeout(function () {
                    $('#J_popup').removeClass('disnone');
                },300)
            })
        },
        //活动状态
        oActiveState:function () {
            db.activeState({
                cb: function (r) {
                    if (r === 0) {
                        $('#J_winnerLists').html('');
                        $('#J_winnerLists').removeClass("active-w");
                        ux.allPrizes();
                    }
                    if(r === -1){
                        clearInterval(timeR);
                        $('#J_winnerLists').addClass("active-w");
                        $('#J_winnerLists').html('<li>活动未开始</li>');
                    }
                    if(r === 1){
                        clearInterval(timeR);
                        $('#J_winnerLists').html('');
                        $('#J_winnerLists').addClass("active-w");
                        $('#J_winnerLists').html('<li>活动已结束</li>');
                        //底部状态
                        $("#J_registerBottom,#J_specialBtn").addClass('activity-end');
                        $("#J_registerBottom,#J_specialBtn").html('活动已结束');
                    }

                }
            });
        },
        xscpShow:function (callback) {
            db.xscpData({
                cb:function (r) {
                    if(r){
                        var totalApr,xscpDay,glIncomeOne,glIncomeFive;
                        if(r.floatApr){
                            totalApr=r.apr+r.floatApr;
                            $(".p-xscp2").removeClass('disnone');
                            $(".p-xscp1").addClass('disnone');
                            $("#J_xscpApr1").html(r.apr);
                            $("#J_xscpFloatApr").html(r.floatApr);
                        }else{
                            totalApr=r.apr;
                            $(".p-xscp1").removeClass('disnone');
                            $(".p-xscp2").addClass('disnone');
                            $("#J_xscpApr").html(r.apr);
                        }
                        xscpDay=10000*(totalApr/100)/360;
                        //保留两位小数
                        xscpDay=(Math.floor(xscpDay * 100) / 100).toFixed(2);
                        glIncomeOne=10000*(totalApr/100)/12;
                        glIncomeFive=10000*(totalApr/100)/12;
                        callback({'glIncomeOne':glIncomeOne,'glIncomeFive':glIncomeFive});
                        $("#J_dayEarn").html(xscpDay);
                        $("#J_xscpPeriod").html(r.period);
                        $("#J_xscpLowest").html(r.lowestTender);
                    }
                }
            })

        },
        xybShow:function (cbData) {
            db.xybData({
                fnOperation:function (data) {
                    var array = data.items,xybFrom,xybTo,xybAprFrom,xybAprTo,xybAprThree,xybThreeData,glIncomeOne,glIncomeFive;
                    xybFrom= getNvalueByCode(array,1);
                    xybTo=  getNvalueByCode(array,36);
                    xybAprFrom=xybFrom.plannedAnnualRateFrom;
                    xybAprTo=xybTo.plannedAnnualRateFrom;
                    if(xybFrom.floatingRate){
                        xybAprFrom=(xybAprFrom*10+xybFrom.floatingRate*10)/10;
                    }
                    if(xybAprTo.floatingRate){
                        xybAprTo=(xybAprTo*10+xybTo.floatingRate*10)/10;
                    }
                    $("#J_xybForm").html(xybAprFrom);
                    $("#J_xybTo").html(xybAprTo);

                //    攻略收益
                    xybThreeData= getNvalueByCode(array,3);
                    xybAprThree=xybThreeData.plannedAnnualRateFrom;
                    if(xybThreeData.floatingRate){
                        xybAprThree=(xybAprThree*10+xybThreeData.floatingRate*10)/10;
                    }
                    glIncomeOne=cbData.glIncomeOne+10000*(xybAprFrom/100)/12+108;
                    glIncomeOne=(Math.floor(glIncomeOne * 100) / 100).toFixed(2);
                    $("#J_glIncomeOne").html(glIncomeOne);
                    glIncomeFive=cbData.glIncomeFive+50000*(xybAprThree/100)/4+108;
                    glIncomeFive=(Math.floor(glIncomeFive * 100) / 100).toFixed(2);
                    $("#J_glIncomeFive").html(glIncomeFive);
                }
            })
        },
        //    信息披露
        operationShow:function () {
            db.operationData({
                fnOperationData:function (data) {
                    var array = data.items;
                    //注册人数
                    var tradeName = getNvalueByCodeData(array, 'TOTAL_REGISTER_USER');
                    // if (tradeName != '') {
                    //     var  TOTAL_REGISTER_USER = tradeName / Math.pow(10,4);
                    //     $('#J_registerNum').html(TOTAL_REGISTER_USER.toFixed(2));
                    // }
                    if(tradeName!=''){
                        var  TOTAL_REGISTER_USER=tradeName;
                        TOTAL_REGISTER_USER     = TOTAL_REGISTER_USER / 10000;
                        $('#J_registerNum').html(TOTAL_REGISTER_USER.toFixed(2));
                    }

                    //成交金额
                    var totalTrade = getNvalueByCodeData(array, 'TOTAL_TRADE');
                    if (totalTrade != '') {
                        totalTrade = (totalTrade / Math.pow(10, 8)).toFixed(2);
                        $("#J_totalTrade").html(totalTrade);
                    }
                    //为出借人赚取
                    var totalIncome = getNvalueByCodeData(array, 'TOTAL_INCOME');
                    if (totalIncome != '') {
                        totalIncome = (totalIncome / Math.pow(10, 8)).toFixed(2);
                        $("#J_totalIncome").html(totalIncome);
                    }
                    //   时间
                    var time = data.time;
                    if (time != '') {
                        time = time.match(/\d+/g);
                        $("#J_year").html(time[0]);
                        $("#J_totalDay").html(time[1]);
                    }
                }
            })
        }
    };
    //
    var db={
        // 活动状态
        activeState: function (o) {
            $.xxdAjax({
                url        : dataUrl.getActivityStatus+'?activityCode='+dataUrl.activityCode,
                dataType   : "json",
                clientId   : "XXD_ACTIVITY_H5_PAGE",
                type       : "get",
                callbacks : function (r) {
                    if (r && r.code == 200000) {
                        o && o.cb(r.data.data.activityStatus)
                    }
                },
                error      : function (r) {
                    main.tip(r.code);
                }
            });
        },
        //   轮播数据
        getPrize:function (o) {
            $.xxdAjax({
                url: dataUrl.getLatestAwardsList+'?activityCode='+dataUrl.activityCode + '&recordNum=189',
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                type: 'GET',
                callbacks: function (data) {
                    if (data.code == "200000") {
                        if (data.data.prize!='') {
                            var dataList=data.data.prize;
                            o && o.cb(dataList);
                        }else{
                            $('#J_winnerLists').addClass("active-w");
                            $("#J_winnerLists").html('<li>暂无数据</li>');
                        }
                    }
                },
                error: function () {
                    alert('网络异常请重试！');
                }
            });
        },
        isLogin:function (o) {
            $.ajax({
                type    : 'GET',
                contentType: "application/json",
                url     : dataUrl.isLogin+'?userToken='+token,
                async   : false,
                data    : {},
                dataType: 'json',
                success : function (str) {
                    if(str.code == "200000"){
                        if (str.data.status.code == 200) {
                            result=true;
                        }
                    }
                },
                error:function () {
                    float.alert({content:'网络异常请重试'});
                }
            });
            o && o.cb(result);
        },
        //用户信息
        userInfo:function (o) {
            $.xxdAjax({
                type    : 'get',
                url     : dataUrl.infoUrl+'?token='+token,
                data:{},
                dataType: 'json',
                callbacks:function (result) {
                    if(result.code==="200000"){
                         o && o.cb(result);
                    }
                },
                error:function () {
                    alert('网络异常请重试！');
                }

            });
        },
        //    产品数据展示  新手专享
        xscpData:function (o) {
            $.xxdAjax({
                url      : dataUrl.commonUrl+'XSCP30T',
                type:'get',
                clientId: "XXD_INTEGRATION_PLATFORM",
                data     : {},
                dataType : 'json',
                callbacks:function (data) {
                    var result;
                    if (data.code == "200000" && (result=data.data.productInfo)) {
                        o && o.cb(result);
                    }
                },
                error:function () {
                    alert('网络异常请重试！');
                }

            });
        },
        //    产品数据展示 新元宝
        xybData:function (o) {
            $.xxdAjax({
                url      : dataUrl.xybUrl,
                type:'get',
                clientId: "XXD_FRONT_END",
                data     : {},
                dataType : 'json',
                callbacks:function (data) {
                    var result;
                    if (data.code == "200000"&& (result=data.data)) {
                        o && o.fnOperation(result);
                    }
                },
                error:function () {
                    alert('网络异常请重试！');
                }

            });
        },
        //    信息披露数据
        operationData:function (o) {
            $.ajax({
                url: dataUrl.operationDataUrl,
                contentType: "application/json",
                dataType: "json",
                type: "get",
                beforeSend: function (request) {
                    request.setRequestHeader("s", "www");
                    request.setRequestHeader("clientId", "001");
                    request.setRequestHeader("clientTime", "001");
                },
                success: function (result) {
                    var r= result.data;
                    if (result.code == 200000 && r) {
                        o && o.fnOperationData(r);
                    }
                },
                error: function (data) {
                    $.log(data);
                }
            })
        }

    };

    $(function(){
        //点击弹窗显示活动规则
        ux.personClick();
        //活动状态
        ux.oActiveState();
        //用户登录或者未登录的提示
        ux.isLogin();
        // // 数据展示
        ux.operationShow();
        ux.xscpShow(function (data) {
            // console.log(data);
            ux.xybShow(data);
            // ux.xybEarn(data);
        });
        $('#J_close').on('click',function(ev){
            var ev=ev || event;
            clearInterval(timer);
            $('#J_popup').addClass("disnone");
            ev.stopPropagation();
        });
        $('#xscp_invest').on('click',function(ev){
            db.isLogin({
                cb:function (r) {
                    if(r){
                        $("#xscp_invest").attr({'href':'/detail/thirtytender.html','target':'_self'});
                    }else{
                        $('body,html').animate({scrollTop: 0}, 1000);
                    }
                }
            })
        });
        $('#xyb_invest').on('click',function(ev){
            db.isLogin({
                cb:function (r) {
                    if(r){
                        $("#xyb_invest").attr({'href':'/xplan/search/list.html','target':'_self'});
                    }else{
                        $('body,html').animate({scrollTop: 0}, 1000);

                    }
                }
            })
        });


        //兼容不支持placeholder的浏览器[ie浏览器，并且10以下均采用替代方式处理]
        if ((navigator.appName == "Microsoft Internet Explorer") && (document.documentMode < 10 || document.documentMode == undefined)) {
            var $placeholder = $("input[placeholder]");
            for (var i = 0; i < $placeholder.length; i++) {
                if ($placeholder.eq(i).attr("data-auto") == "password") {
                    $placeholder.eq(i).siblings('.psw-cur').removeClass('disnone');
                } else {
                    $placeholder.eq(i).val($placeholder.eq(i).attr("placeholder"));
                }
            }
            $placeholder.focus(function () {
                if ($(this).attr("data-auto") == "password") {
                    $(this).siblings('.psw-cur').addClass('disnone');
                }else{
                    if ($(this).val() == $(this).attr("placeholder")) {
                        $(this).val("");
                    }
                }
            }).blur(function () {
                if ($(this).attr("data-auto") == "password") {
                    if ($(this).val() == "") {
                        $(this).siblings('.psw-cur').removeClass('disnone');
                    }
                } else {
                    if ($(this).val() == "") {
                        $(this).val($(this).attr("placeholder"));
                    }
                }
            });
        }
    });
    //xyb动态获取数据
    function getNvalueByCode(array, leastPeriod) {
        if (!array || !(array instanceof Array)) {
            return 0;
        }
        var length = array.length;
        for (var i = 0; i < length; i++) {
            if(array[i].leastPeriod == leastPeriod) {
                return array[i];
            }
        }
        return 0;
    }
    //operate
    function getNvalueByCodeData(array, code) {
        if (!array || !(array instanceof Array)) {
            return 0;
        }
        var length = array.length;
        for (var i = 0; i < length; i++) {
            if (array[i].code == code) {
                return array[i].nvalue;
            }
        }
        return 0;
    }
    //数字变成千分位表达式
    function numberFormat(num) {
        var number = new Number(num);
        var str = number.toString();
        var kNum=str.replace(/\d{1,3}(?=(\d{3})+$)/g,function(s){
            return s+','
        });
        return kNum;
    }


    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);
}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});