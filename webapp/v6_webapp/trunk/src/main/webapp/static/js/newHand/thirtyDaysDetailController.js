/**
 * Created by chaihuangqi on 2015/12/22.
 */
define(['js/utils/animateNumber', 'js/utils/date','js/common/ami'], function (animateNumber, DateHandle,ami) {
    var pageSize = 10;
    var bid = "";
    var ganame = "七天大胜：七天大胜";
    var categorys = "";
    // 加载flag
    var loading = false;
    var pId;
    var isMeetTheTermOfPurchase;
    var thirtyDaysDetailCtrl = {
        init: function () {
            thirtyDaysDetailCtrl.eventBind();

            req.callGet({
                url: 'product/getWebappProduct.do',
                data: {
                    pCode:"XSCP30T"
                },
                dataType: 'json',
                async: false,
                success: function (result) {
                    if (result.code == 200000) {
                        var data = result.data;
                        pId = data.items.id;
                        isMeetTheTermOfPurchase = data.isMeetTheTermOfPurchase;
                        thirtyDaysDetailCtrl.getNewSavenDaysInfo();
                    }
                }
            });

            thirtyDaysDetailCtrl.pullToRefresh();
        },

        eventBind: function () {
            var bindings = [
                {
                    element: 'div[name="7d_join"]',
                    event: 'click',
                    handler: thirtyDaysDetailCtrl.toSevenDaysTrade
                },
                {
                    element: '#introduce',
                    event: 'click',
                    handler: thirtyDaysDetailCtrl.introduce
                },
                {
                    element: '#qa',
                    event: 'click',
                    handler: thirtyDaysDetailCtrl.qa
                },
                {
                    element: '#trade-record',
                    event: 'click',
                    handler: thirtyDaysDetailCtrl.tradeRecord
                },
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: thirtyDaysDetailCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: thirtyDaysDetailCtrl.infiniteScroll
                },
                {
                    element: '#fenxiantishi',
                    event: 'click',
                    handler: ami.fenxiantishi
                },
                {
                    element: '#tradeList',
                    event: 'click',
                    handler: thirtyDaysDetailCtrl.tradeList
                }
            ];
            appFunc.bindEvents(bindings);
        },

        tradeList:function(){
            GS.loadPage('trade/tradeList.html?pId='+pId);
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            //sevenDaysDetailCtrl.selectSevenDaysRec(1, "pull");
            thirtyDaysDetailCtrl.investOrderList(1, "pull");
            $$("#currentPage").val(1);
            loading = false;
        },
        /**
         * 无限滚动
         */
        infiniteScroll: function () {
            if (!$$(" #trade-record").hasClass("active")) {
                return;
            }
            // 如果正在加载，则退出
            if (loading) {
                return;
            }
            //==========================切记此处不能删除============================
            loading = true;
            //==========================切记此处不能删除============================
            var totalPages = $$("#totalPages").val();
            var currentPage = $$("#currentPage").val();
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if (currentPage > totalPages) {
                //提示已经全部显示
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '数据已全部加载完毕'
                });
                loading = true;
                return;
            }
            //sevenDaysDetailCtrl.selectSevenDaysRec(currentPage, "push");
            thirtyDaysDetailCtrl.investOrderList(currentPage, "push");
            $$("#currentPage").val(currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },

        introduce: function () {
            $$("#qa").removeClass("active");
            $$("#trade-record").removeClass("active");
            $$("#introduce").addClass("active");
            $$("#qa-c").hide();
            $$("#trade-record-c").hide();
            $$("#introduce-c").show();
        },
        qa: function () {
            $$("#introduce").removeClass("active");
            $$("#trade-record").removeClass("active");
            $$("#qa").addClass("active");
            $$("#introduce-c").hide();
            $$("#trade-record-c").hide();
            $$("#qa-c").show();
        },
        tradeRecord: function () {
            $$("#qa").removeClass("active");
            $$("#introduce").removeClass("active");
            $$("#trade-record").addClass("active");
            $$("#qa-c").hide();
            $$("#introduce-c").hide();
            $$("#trade-record-c").show();
        },

        investOrderList:function(currentPage, type){
            var pid = $('#v_deployid').val();
            req.callJSON({
                url: "product/productJoinRecords.do",
                data: {
                    pId: pid,
                    pCode:'XSCP30T',
                    currentPage: currentPage,
                    pageSize: pageSize
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    if(result.code == 200000) {
                        var data = result.data;
                        var totalTender = data.countJoinTime;
                        var totalPage = Math.floor(totalTender / pageSize);
                        totalPage += (totalTender % pageSize) > 0 ? 1 : 0;
                        $$("#totalPages").val(totalPage);
                        totalTender = totalTender > 9999 ? Math.round((totalTender / 10000) * 100) / 100 + "万": totalTender;
                        $$("span[name='7d_total']").html("("+totalTender+")");
                        var sevenDaysList = [];
                        if (data != null) {
                            sevenDaysList = data.items;
                        }
                        if (sevenDaysList != null && sevenDaysList.length != 0) {
                            var html = ""
                            for (var k = 0; k < sevenDaysList.length; k++) {
                                var obj = sevenDaysList[k];
                                html += "<tr>";
                                html += "<td>" + obj.nickName + "</td>";
                                html += "<td>" + appFunc.fmoney(obj.joinAmount, 2) + "</td>";
                                html += "<td>" + DateHandle.formatDate("yyyy-MM-dd HH:mm:ss",new Date(obj.joinDate)) + "</td>";
                                html += "</tr>";
                            }
                            if (type == 'push') {
                                $("#sevenDaysList").append(html);
                            } else {
                                $("#sevenDaysList").html(html);
                            }

                        } else {
                            $("#sevenDaysList").html("<tr align='center' ><td colspan='10'>没有投标记录</td></tr>");
                        }
                        // 加载完毕需要重置
                        xxdApp.pullToRefreshDone();
                    }
                }
            });

            setTimeout(function () {
                if (type == 'pull') {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            }, 5000);
        },

        toSevenDaysTrade:function(){

            if ($$('div[name="7d_join"]').hasClass("sevenDays-button-disable")) {
                return;
            }

            if (isMeetTheTermOfPurchase == false) {
                $$("div[name='7d_goBuy']").parent().parent().css("height", "100px");
                $$("div[name='7d_goBuy']").parent().show();
                $$("div[name='7d_join']").hide();
                var bind = [{
                    element: 'div[name="7d_goBuy"]',
                    event: 'click',
                    handler: thirtyDaysDetailCtrl.goFinancesList
                }];
                appFunc.bindEvents(bind);
                thirtyDaysDetailCtrl.sevenDaysModal({text:'您已经是资深用户，更多高手进阶的产品在等着你哦！',title:'提示'});
            } else {
                GS.loadPage('newHand/thirtyDaysTrade.html?path=newHand');
            }
        },

        getNewSavenDaysInfo: function () {
            req.callGet({
                url: 'product/getProduct.do',
                data: {
                    pCode: 'XSCP30T',
                    pId: pId
                },
                dataType: 'json',
                async: false,
                success: function (result) {
                    if (result.code == 200000) {
                        var data = result.data;
                        $('#v_deployid').val(data.productId);
                        var apr = data.plannedAnnualRate;
                        animateNumber.animate({
                            from: 0,
                            to: apr,
                            steps: 10,
                            intervalNumber: 100,
                            precision: 2,
                            isFloat: true,
                            callBack: function (value) {
                                var values = new String(value).split('.');
                                var html = values[0];
                                if (values.length > 1 && values[1] != '00') {
                                    html += '<span class="font12">.' + values[1] + '</span>';
                                }
                                $$("#7d_apr").html(html);
                            }
                        });

                        $(".pro30apr").html(apr);

                        //产品详情
                        var gaCloseTerm = data.leastPeriod;
                        bid = data.productId;


                        var nhAddApr = apr;
                        var floatApr = data.floatingRate;

                        if (floatApr != null && floatApr != undefined) {
                            $$("span[name='7d_floatApr']").html("+" + floatApr + "%");
                            $$(".newhandActivity").show();
                            $$(".newhandActivity").addClass("animated fadeInLeft");
                            nhAddApr = apr + floatApr;

                            $(".pro30floatapr").html("+"+floatApr+"%");
                            $(".pro30floatapr").show();

                            $(".pro30floataprText").html("+投资金额*"+floatApr+"%/360*30");
                            $(".pro30floataprText").show();
                        }
                        $$("span[name='nh_AddApr']").html(nhAddApr);
                        $$("span[name='nh_apr']").html(apr);

                        var leastAmount = data.leastInvestAmount;
                        $$("#7d_leastAmount").html(leastAmount);

                        var closeTerm = data.leastPeriod;
                        animateNumber.animate({
                            from: 0,
                            to: closeTerm,
                            precision: 0,
                            intervalNumber: 100,
                            steps: 10,
                            isFloat: true,
                            callBack: function (value) {
                                $$("#7d_closeTerm").html(value);
                            }
                        });
                        $$("span[name='7d_closeTerm']").html(closeTerm);
                        $$("#7d_leastAmount1").html(leastAmount);
                        $$("#7d_mostAmount").html(data.mostInvestAmount);

                        var status = data.status;
                        if(status == 236) {
                            thirtyDaysDetailCtrl.disable7dButton({value: '等待发售'});
                        } else if(status == 237) {
                            thirtyDaysDetailCtrl.recovery7dButton({value:'立即抢购'});
                        } else if(status == 238) {
                            thirtyDaysDetailCtrl.disable7dButton({value:'已售罄'});
                        } else {
                            thirtyDaysDetailCtrl.disable7dButton({value:'已结束'});
                        }
                       /* var currentDate = new Date(Date.parse(DateHandle.formatDate('yyyy-MM-dd HH:mm:ss',new Date(result.serverTime)).replace(/-/g,"/")));
                        var sd = new Date(data.activitedStartDate);
                        var ed = new Date(data.activitedEndDate);
                        if (currentDate > ed) {
                            thirtyDaysDetailCtrl.disable7dButton({value: '今日投资已结束'});
                        }
                        if (currentDate < sd) {
                            thirtyDaysDetailCtrl.disable7dButton({value: '等待发售'});
                        }
                        if (parseFloat(data.leftAmount) < 100) {
                            thirtyDaysDetailCtrl.disable7dButton({value: '今日投资已结束'});
                        }*/
                        if (isMeetTheTermOfPurchase == false) {
                            //sevenDaysDetailCtrl.disable7dButton({value:'立即抢购'});
                            $$("div[name='7d_goBuy']").parent().parent().css("height", "100px");
                            $$("div[name='7d_goBuy']").parent().show();
                            $$("div[name='7d_join']").hide();

                            var bind = [{
                                element: 'div[name="7d_goBuy"]',
                                event: 'click',
                                handler: thirtyDaysDetailCtrl.goFinancesList
                            }];
                            appFunc.bindEvents(bind);
                            thirtyDaysDetailCtrl.sevenDaysModal({text:'您已经是资深用户，更多高手进阶的产品在等着你哦！',title:'提示'});
                        }


                        thirtyDaysDetailCtrl.setApr(apr,floatApr);
                    }
                }
            });
        },

        setApr:function(apr,floatApr){
            var result100 = 100 * apr /100 / 360 *30 ;
            var result1000 = 1000 * apr /100 / 360 *30 ;
            var result5000 = 5000 * apr /100 / 360 *30 ;
            var result10000 = 10000 * apr /100 / 360 *30 ;
            if(floatApr != undefined && 0!= floatApr) {
                result100 +=  100 * floatApr /100 / 360 *30 ;
                result1000 +=  1000 * floatApr /100 / 360 *30 ;
                result5000 +=  5000 * floatApr /100 / 360 *30 ;
                result10000 +=  10000 * floatApr /100 / 360 *30 ;
            }

            result100 = Math.floor(result100*100)/100;
            result1000 = Math.floor(result1000*100)/100;
            result5000 = Math.floor(result5000*100)/100;
            result10000 = Math.floor(result10000*100)/100;

            $(".result100").html(result100);
            $(".result1000").html(result1000);
            $(".result5000").html(result5000);
            $(".result10000").html(result10000);
        },


        goFinancesList: function () {
            GS.loadPage('popular/financesList.html?path=popular');
        },

        sevenDaysModal: function (options) {
            xxdApp.modal({
                title: options.title,
                text: options.text,
                buttons: [
                    {
                        text: '取消',
                        onClick: function () {

                        }
                    },
                    {
                        text: '去选购',
                        bold: true,
                        onClick: function () {
                            GS.loadPage('popular/financesList.html?path=popular');
                        }
                    }
                ]
            })
        },
        disable7dButton: function (options) {
            $$('div[name="7d_join"]').removeClass("sevenDays-button");
            $$('div[name="7d_join"]').addClass("sevenDays-button-disable");
            if (options.value != null)
                $$('div[name="7d_join"]').html(options.value);
        },
        recovery7dButton:function(options) {
            $$('div[name="7d_join"]').addClass("sevenDays-button");
            $$('div[name="7d_join"]').removeClass("sevenDays-button-disable");
            if (options.value != null)
                $$('div[name="7d_join"]').html(options.value);
        }
    };
    return thirtyDaysDetailCtrl;
});
