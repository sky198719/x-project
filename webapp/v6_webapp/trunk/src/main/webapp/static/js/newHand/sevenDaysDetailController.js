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
    var sevenDaysDetailCtrl = {
        init: function () {
            xxdApp.modal({
                title: '温馨提示',
                text: '七天大胜已下架，请选择其他产品',
                buttons: [
                    {
                        text: '确定',
                        bold: true,
                        onClick: function () {
                            GS.loadPage('index/home.html');
                        }
                    }
                ]
            });
            return;



            sevenDaysDetailCtrl.eventBind();
            //sevenDaysDetailCtrl.getSevenDaysInfo();

            req.callGet({
                url: 'product/getWebappProduct.do',
                data: {
                    pCode:"QTDS"
                },
                dataType: 'json',
                async: false,
                success: function (result) {
                    if (result.code == 200000) {
                        var data = result.data;
                        pId = data.items.id;
                        isMeetTheTermOfPurchase = data.isMeetTheTermOfPurchase;
                        sevenDaysDetailCtrl.getNewSavenDaysInfo();
                    }
                }
            });

            sevenDaysDetailCtrl.pullToRefresh();
        },

        eventBind: function () {
            var bindings = [
                {
                    element: 'div[name="7d_join"]',
                    event: 'click',
                    handler: sevenDaysDetailCtrl.toSevenDaysTrade
                },
                {
                    element: '#introduce',
                    event: 'click',
                    handler: sevenDaysDetailCtrl.introduce
                },
                {
                    element: '#qa',
                    event: 'click',
                    handler: sevenDaysDetailCtrl.qa
                },
                {
                    element: '#trade-record',
                    event: 'click',
                    handler: sevenDaysDetailCtrl.tradeRecord
                },
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: sevenDaysDetailCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: sevenDaysDetailCtrl.infiniteScroll
                },
                {
                    element: '#fenxiantishi',
                    event: 'click',
                    handler: ami.fenxiantishi
                },
                {
                    element: '#tradeList',
                    event: 'click',
                    handler: sevenDaysDetailCtrl.tradeList
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
            sevenDaysDetailCtrl.investOrderList(1, "pull");
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
            sevenDaysDetailCtrl.investOrderList(currentPage, "push");
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
                    pCode:'QTDS',
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
        	try {
        		//XXD_TRACK._trackEvent({category: "webapp_sevengold_in", action: "detail_buy_once", label: "立即抢购", value: "", custval: "" });
        		//加入购物车
        		add_to_cart({id:bid,name:ganame,category:categorys});
        	} catch (e) {}
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
                    handler: sevenDaysDetailCtrl.goFinancesList
                }];
                appFunc.bindEvents(bind);
                //sevenDaysDetailCtrl.sevenDaysModal({text:'您已经是资深用户，更多高手进阶的产品在等着你哦！',title:'提示'});
            } else {
                GS.loadPage('newHand/sevenDaysTrade.html?path=newHand');
            }
        },

        getNewSavenDaysInfo: function () {
            req.callGet({
                url: 'product/getProduct.do',
                data: {
                    pCode: 'QTDS',
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

                        //产品详情
                        var gaCloseTerm = data.leastPeriod;
                        bid = data.productId;
                        ganame = "七天大胜：七天大胜";
                        categorys = "七天大胜/" + apr + "%/" + gaCloseTerm + "天";
                        try {
                            product_detail({id: bid, name: ganame, category: categorys});
                        } catch (e) {
                        }

                        var nhAddApr = apr;
                        var floatApr = data.floatingRate;
                        if (floatApr != null && floatApr != undefined) {
                            $$("span[name='7d_floatApr']").html("+" + floatApr + "%");
                            $$(".newhandActivity").show();
                            $$(".newhandActivity").addClass("animated fadeInLeft");
                            nhAddApr = apr + floatApr;
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
                        var currentDate = new Date(Date.parse(DateHandle.formatDate('yyyy-MM-dd HH:mm:ss',new Date(result.serverTime)).replace(/-/g,"/")));
                        var sd = new Date(data.activitedStartDate);
                        var ed = new Date(data.activitedEndDate);
                        if (currentDate > ed) {
                            sevenDaysDetailCtrl.disable7dButton({value: '今日投资已结束'});
                        }
                        if (currentDate < sd) {
                            sevenDaysDetailCtrl.disable7dButton({value: '等待发售'});
                        }
                        if (parseFloat(data.leftAmount) < 100) {
                            sevenDaysDetailCtrl.disable7dButton({value: '今日投资已结束'});
                        }
                        if (isMeetTheTermOfPurchase == false) {
                            //sevenDaysDetailCtrl.disable7dButton({value:'立即抢购'});
                            $$("div[name='7d_goBuy']").parent().parent().css("height", "100px");
                            $$("div[name='7d_goBuy']").parent().show();
                            $$("div[name='7d_join']").hide();

                            var bind = [{
                                element: 'div[name="7d_goBuy"]',
                                event: 'click',
                                handler: sevenDaysDetailCtrl.goFinancesList
                            }];
                            appFunc.bindEvents(bind);
                            // sevenDaysDetailCtrl.sevenDaysModal({text:'您已经是资深用户，更多高手进阶的产品在等着你哦！',title:'提示'});
                        }
                    }
                }
            });
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
        }
    };
    return sevenDaysDetailCtrl;
});
