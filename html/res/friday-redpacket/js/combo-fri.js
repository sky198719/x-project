/**
 * Created by gaoshanshan_syp on 2017/11/22.
 */
require(['base', 'float', 'trackBase', 'store', "requirejs", "paging"], function ($, float, track, store,requirejs) {

    var dataUrl= {
        activityCode:'friday-activity',
        allPrizesUrl:'/activityCenter/fridayActivityBase/getLatestAwardsList', // 所有的中奖列表
        getActivityStatus:'/activityCenter/fridayActivityBase/getActivityStatus',   //活动状态
        getLotteryPeopleAmount:'/activityCenter/fridayActivityBase/getLotteryPeopleAmount', //查询活动中抽过红包的人数
        isLogin:'/feapi/users/loginInfo',
        commonUrl:'/tradeCenter/investBiz/showStatus/',
        xybUrl:'/tradeCenter/XYB/brief',
        yjdjUrl:'/tradeCenter/YJDJ/brief',
        operationDataUrl: '/biz/bulletin/operationData'
    };
    var ux= {
        allPrizes:function() {
            db.getPrize({
                cb:function(r) {
                    var aLis = '';
                    $.each(r, function (i, item) {
                        aLis+='<li><span class="username">' + item.username + '</span><span class="prizename">' + item.prizename + '</span></li>';
                    });
                    $('#J_winData ul').html(aLis);
                    if($("#J_winData ul li").length >= 5){
                        $('.myscroll').vTicker({
                            speed: 500,
                            pause: 2000,
                            animation: 'fade',
                            mousePause: false,
                            showItems: 5
                        });
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
                        $('#J_winData ul').html(aLis);
                    }

                })
            }
            getAllPrizes();
            var timeR=null;
            clearInterval(timeR);
            timeR = setInterval(function () {
                getAllPrizes();
            }, 600000);
        },
        isLogin:function () {
            db.isLogin({
                cd:function (r) {
                    //如果登录成功
                    if(r){
                        window.location.href="/html/friday-redpacket/index.html";
                        // $("#J_registerBottom,#J_specialBtn,#J_start").attr({'href':'/html/friday-redpacket/index.html','target':'_self'});
                        $("#J_register").addClass('dishidden');
                    }
                }
            })
        },
        //活动状态
        oActiveState:function () {
            db.activeState({
                cb: function (r) {
                    if (r === 0) {
                        ux.getPeoplePrizes();
                        ux.allPrizes();
                    }else{
                        $("#J_winData ul").html('<li style="width: 100%;text-align: center">周五09:00开始</li>');
                        if(!$("#J_showPeople").hasClass('disnone')){
                            $("#J_people").addClass('disnone');
                        }
                    }

                }
            });
        },
        getPeoplePrizes:function () {
            db.getPeopleAmount({
                cb:function (r) {
                    if(r>0){
                        $("#J_people").html(r);
                        $("#J_showPeople").removeClass("disnone");
                    }
                }
            })
        },
        xscpShow:function () {
          db.xscpData({
              cb:function (r) {
                  if(r){
                      if(r.floatApr){
                          $(".p-xscp").removeClass('disnone');
                          $(".p-xscp").eq(0).addClass('disnone');
                          $("#J_xscpApr1").html(r.apr);
                          $("#J_xscpFloatApr").html(r.floatApr);
                      }else{
                          $(".p-xscp").removeClass('disnone');
                          $(".p-xscp").eq(1).addClass('disnone');
                          $("#J_xscpApr").html(r.apr);
                      }
                      // $("#J_xscpPeriod").html(r.period);
                      $("#J_xscpLowest").html(r.lowestTender);
                  }
              }
          })
        },
        xybShow:function () {
           db.xybData({
               fnOperation:function (data) {
                   var array = data.items,xybFrom,xybTo,xybAprFrom,xybAprTo;
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
               }
           })
        },
        yjdjShow:function () {
            db.yjdjData({
                cd:function (result) {
                    var rate=result.plannedAnnualRate;
                    if(result.floatingRate){
                        rate=rate+result.floatingRate;
                    }
                    var leastTender= result.leastTenderAmountLabel;
                    $("#J_yjdjApr").html(rate);
                    $("#J_yjdjPeriod").html(result.leastPeriod);
                    $("#J_yjdjStep").html(leastTender);
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
                       var  TOTAL_REGISTER_USER=numberFormat(tradeName);
                       $('#J_registerNum').html(TOTAL_REGISTER_USER);
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
                url: dataUrl.allPrizesUrl,
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                data: {
                    "activityCode": dataUrl.activityCode
                },
                type: 'GET',
                callbacks: function (data) {
                    if (data.code == "200000") {
                        if (data.data.prize!='') {
                            var dataList=data.data.prize;
                            o && o.cb(dataList);
                        }else{
                            $("#J_winData ul").html('<li style="width: 100%;text-align: center">暂无数据</li>')
                        }
                    }
                },
                error: function () {
                    main.tip('网络异常请重试!');
                }
            });
        },
        //已经抽过奖的人数
        getPeopleAmount:function(o) {
            $.xxdAjax({
                url: dataUrl.getLotteryPeopleAmount,
                contentType: 'application/json',
                dataType: 'json',
                clientId: "XXD_ACTIVITY_H5_PAGE",
                type: 'get',
                callbacks: function (r) {
                    if (r && r.code == 200000) {
                        o.cb && o.cb(r.data.data);
                    }
                },
                error: function (r) {
                    main.tip('网络异常请重试!');
                }

            })

        },
        isLogin:function (o) {
            var result=false;
            var token=$.readCookie('Token');
            $.ajax({
                type    : 'GET',
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
                    float.alert({content:msg.errorMsg});
                }
            });
            o && o.cd(result);
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
      yjdjData:function (o) {
          $.xxdAjax({
              url      : dataUrl.yjdjUrl,
              type:'get',
              clientId: "XXD_FRONT_END",
              s: "71824bd75e1b757773d738537f2c9441",
              data     : {},
              dataType : 'json',
              callbacks:function (data) {
                  var result;
                  if (data.code == "200000" && (result=data.data)) {
                      o && o.cd(result);
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
        //固定底部 头部
        scrollFun();
        ux.oActiveState();
        ux.isLogin();
        // 数据展示
        ux.operationShow();
        ux.xscpShow();
        ux.xybShow();
        ux.yjdjShow();
        //去顶部
        $("#J_registerBottom,#J_specialBtn,#J_start,#J_xscpUrl,#J_yjdjUrl,#J_xybUrl").on('click',function (ev) {
            db.isLogin({
                cd:function (result) {
                    if(!result){
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
    function scrollFun() {
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
    }

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