define(['js/account/myPlanListView','js/plan/planUtils','js/utils/date'],function (myPlanListView,PlanUtils, DateHandle) {
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var planHistoryCtrl = {
        init: function () {
            planHistoryCtrl.eventBind();
        },
        eventBind: function () {
            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: planHistoryCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: planHistoryCtrl.infiniteScroll
                }
            ];

            appFunc.bindEvents(bindings);
            planHistoryCtrl.pullToRefresh();
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            planHistoryCtrl.selectMyPlanList(1, "pull");
            loading = false;
        },
        /**
         * 无限滚动
         */
        infiniteScroll: function () {
            // 如果正在加载，则退出
            if (loading) {
                return;
            }
            //==========================切记此处不能删除============================
            loading = true;
            //==========================切记此处不能删除============================
            var totalPage = $$("#planHistoryPage").data("totalPage");
            var currentPage = $$("#planHistoryPage").data("currentPage");
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if (currentPage > totalPage) {
                //提示标的已经全部显示
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '已经到底了'
                });
                loading = true;
                return;
            }
            planHistoryCtrl.selectMyPlanList(currentPage, "push");
            $$("#planHistoryPage").data("currentPage", currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },
        /**
         * 新元宝记录
         * @param currentPage  当前页
         * @param type 刷新类型
         */
        selectMyPlanList: function (currentPage, type) {
            req.callJSON({
                url: "xplan/mySchemeList.do",
                data: {
                    currentPage: currentPage,
                    pageSize: 100
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    myPlanListView.showListItem({
                        data: data,
                        type: type
                    });

                    planHistoryCtrl.myPlanListBindEvent();
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            });

            setTimeout(function () {
                if (type == 'pull') {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            }, 5000);
        },
        /**
         * 新元宝列表事件绑定
         */
        myPlanListBindEvent: function () {
            var bindings = [
                {
                    element: '#myPlanList div[name="plan_pay_href"]',
                    event: 'click',
                    handler: planHistoryCtrl.toPay
                },
                {
                    element: '#myPlanList div[name="advance_quit"]',
                    event: 'click',
                    handler: planHistoryCtrl.toQuit
                },
                {
                    element: '#myPlanList div[name="planAgreement"]',
                    event: 'click',
                    handler: planHistoryCtrl.planAgreement
                },
                {
                    element: '#myPlanList div[name="showPlanDetail"]',
                    event: 'click',
                    handler: planHistoryCtrl.showPlanDetail
                }
            ];
            myPlanListView.bindEvents({
                        bindings: bindings
                    }
            );
        },
        showPlanDetail: function () {
            var planId = $(this).attr("data-id");
            var joinId = $(this).attr("data-joinid");
            GS.loadPage('plan/planDetail.html?planId=' + planId + '&path=plan' + '&joinId='+joinId);
        },
        investmentDetail:function(joinId) {
            var money = 0;
            req.callJSON({
                url:'product/optimizeSchemeInvestmentDetail.do',
                data:{
                    joinId:joinId
                },
                async:false,
                success:function(result){
                    if(result.code == 200000) {
                        money =  result.data.redEnvelopeAmount;
                    }
                }
            });
            return money;
        },
        advanceQuit: function (planId, ptype, pname, forfeitpercent,tendermoney,joinid) {

            var wy = appFunc.fmoney(tendermoney * (forfeitpercent / 100),2);
            var hongbaoMoney = planHistoryCtrl.investmentDetail(joinid);
            xxdApp.confirm(PlanUtils.schemeType(ptype) + ' - ' + pname + '正处于锁定期，你若选择退出，会产生违约行为，平台会征收你' + forfeitpercent + '%的违约金，(若您使用了红包，红包金额也会收回)提交后不可取消，确认要退出吗？<br>违约金：'+wy+'元' + (hongbaoMoney > 0 ? '<br>扣除红包金额：' + hongbaoMoney +'元' : ''), '提前退出新元宝', function () {
                //新元宝提前退出GA
                gaClickEvent({property1:"转出",property2: "新元宝转出", property3:window.location});
                xxdApp.modalPassword('请输入支付密码', '提前退出新元宝', function (password) {
                    if (password == null || password == '') {
                        xxdApp.alert('请输入支付密码！');
                        return;
                    }
                    xxdApp.showIndicator('正在加载数据...');
                    req.callJSON({
                        url: "xplan/quitScheme.do",
                        data: {
                            schemeId: planId,
                            paypwd: $.md5(password),
                            joinid:joinid
                        },
                        dataType: 'json',
                        success: function (data) {
                            xxdApp.hideIndicator();
                            var result = data.resultCode;
                            if (result == 500) {
                                xxdApp.loginScreen();
                            } else if (result == 0) {
                                redemption({id:data.productId,name:"新元宝赎回",category:"新元宝/" + data.apr + "%/"  + data.terms + "个月",price:data.price,tradeId:data.tradeid,affiliation:data.servicenum});
                                planHistoryCtrl.pullToRefresh();
                            } else if (result == -1) {
                                xxdApp.alert("获取支付密码异常！");
                            } else if (result == -2) {
                                xxdApp.alert("支付密码错误，请重新输入！");
                            } else if (result == 220) {
                                xxdApp.alert("您还未设置支付密码，请去个人信息中心设置！");
                            } else if (result == 230) {
                                xxdApp.alert("支付密码与登录密码一致！");
                            } else {
                                if (result != 301) {
                                    xxdApp.alert(data.msg, '提示');
                                }
                            }
                        }
                    });
                });
            });
        },
        toQuit: function () {
            var planId = $(this).attr("data-id");
            var ptype = $(this).attr("ptype");
            var pname = $(this).attr("pname");
            var tendermoney = $(this).attr("data-tendermoney");
            var joinid = $(this).attr("data-joinid");
            var forfeitpercent = '';
            //查询违约金比例&退出状态
            req.callJSON({
                url: "xplan/loadMyXplanDetail.do",
                data: {
                    planId: planId
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    if (data.scheme != null && data.userSchemeInfo != null) {
                        forfeitpercent = data.scheme.forfeitpercent;
                        if (data.userSchemeInfo.status == 8) {
                            xxdApp.alert("正在退出，请耐心等待...", "提示");
                        } else {
                            planHistoryCtrl.advanceQuit(planId, ptype, pname, forfeitpercent,tendermoney,joinid);
                        }
                    } else {
                        xxdApp.alert("获取新元宝信息有误，请重新尝试或联系客服。", "提示");
                    }
                }
            });
        },
        planAgreement: function () {
            var planId = $(this).attr("data-id");
            // 新元宝协议
            req.callJSON({
                url: "xplan/agreement/" + planId + ".do?noShow=true",
                data: {},
                timeout: 10000,
                indicator: true,
                success: function (data) {
                    data.scheme.pname = PlanUtils.schemeType(data.scheme.type) + ' - ' + data.scheme.pname;

                    if (data.scheme.minApr != data.scheme.maxApr) {
                        data.scheme.maxApr = data.scheme.minApr + '% ~ ' + data.scheme.maxApr + '%';
                    }else{
                    	data.scheme.maxApr = data.scheme.minApr + '%';
                    }

                    data.scheme.leastamount = appFunc.fmoney(data.scheme.leastamount, 2);
                    data.scheme.mostamount = appFunc.fmoney(data.scheme.mostamount, 2);

                    //var presaleBegin = new Date(data.scheme.presaleBegin);
                    var presaleBegin = DateHandle.parseDate(data.scheme.presaleBegin);
                    data.scheme.presaleBegin = presaleBegin.getFullYear() + "年" + (presaleBegin.getMonth() + 1) + "月" + presaleBegin.getDate() + "日";
                    data.scheme.presaleBegin_y = presaleBegin.getFullYear();
                    data.scheme.presaleBegin_m = presaleBegin.getMonth() + 1;
                    data.scheme.presaleBegin_d = presaleBegin.getDate();

                    var presaleEnd = DateHandle.parseDate(data.scheme.presaleEnd);
                    data.scheme.presaleEnd = presaleEnd.getFullYear() + "年" + (presaleEnd.getMonth() + 1) + "月" + presaleEnd.getDate() + "日";
                    data.scheme.presaleEnd_y = presaleEnd.getFullYear();
                    data.scheme.presaleEnd_m = presaleEnd.getMonth() + 1;
                    data.scheme.presaleEnd_d = presaleEnd.getDate();

                    //var opensaleEnd = new Date(data.scheme.opensaleEnd);
                    var opensaleEnd = DateHandle.parseDate(data.scheme.opensaleEnd);
                    data.scheme.opensaleEnd = opensaleEnd.getFullYear() + "年" + (opensaleEnd.getMonth() + 1) + "月" + opensaleEnd.getDate() + "日";
                    data.scheme.opensaleEnd_y = opensaleEnd.getFullYear();
                    data.scheme.opensaleEnd_m = opensaleEnd.getMonth() + 1;
                    data.scheme.opensaleEnd_d = opensaleEnd.getDate();

                    var endTime = new Date(data.endTime);
                    //var endTime = DateHandle.parseDate(data.endTime);
                    data.endTime = endTime.getFullYear() + "年" + (endTime.getMonth() + 1) + "月" + endTime.getDate() + "日";
                    data.endTime_y = endTime.getFullYear();
                    data.endTime_m = endTime.getMonth() + 1;
                    data.endTime_d = endTime.getDate();

                    if (data.userSchemeInfo) {
                        if (data.userSchemeInfo.collectiontype == 1) {
                            data.userSchemeInfo.collectiontype = '收益再投标';
                        } else if (data.userSchemeInfo.collectiontype == 2) {
                            data.userSchemeInfo.collectiontype = '提至新新贷账户';
                        }
                        data.userSchemeInfo.account = appFunc.fmoney(data.userSchemeInfo.account, 2);
                    }

//                    data.user.signedTime = DateHandle.formatDate("yyyy年MM月dd日", new Date(data.scheme.opensaleEnd), null);
                    data.user.signedTime = data.scheme.opensaleEnd;

                    var isFloatApr = data.stragreement.isFloatApr;
                    var aprStr = '';
                    var amountStr = '';
                    if(isFloatApr != undefined && isFloatApr == true) {
                        var prodFloatApr = data.scheme.prodFloatApr;

                        if(data.stragreement.pcAmount !=undefined && data.stragreement.pcAmount != 0) {
                            if(prodFloatApr.pc != undefined) {
                                if(prodFloatApr.pc != 0) {
                                    aprStr += data.scheme.maxApr + "+"+ prodFloatApr.pc+"%" + "(PC)<br>" ;
                                } else {
                                    aprStr += data.scheme.maxApr + "(PC)<br>" ;
                                }
                            }
                            amountStr += appFunc.fmoney(data.stragreement.pcAmount,2) + '元(PC)<br>';
                        }
                        if(data.stragreement.appAmount != undefined && data.stragreement.appAmount != 0) {
                            if(prodFloatApr.app != undefined) {
                                if(prodFloatApr.app != 0) {
                                    aprStr += data.scheme.maxApr + "+"+prodFloatApr.app+"%" + "(APP)<br>" ;
                                } else {
                                    aprStr += data.scheme.maxApr +  "(APP)<br>" ;
                                }
                            }
                            amountStr += appFunc.fmoney(data.stragreement.appAmount,2) + '元(APP)<br>';
                        }
                        if(data.stragreement.webappAmount != undefined && data.stragreement.webappAmount != 0) {
                            if(prodFloatApr.webapp != undefined) {
                                if(prodFloatApr.webapp != 0) {
                                    aprStr += data.scheme.maxApr + "+"+prodFloatApr.webapp+"%" + "(WEBAPP)" ;
                                } else {
                                    aprStr += data.scheme.maxApr +"(WEBAPP)" ;
                                }
                            }
                            amountStr += appFunc.fmoney(data.stragreement.webappAmount,2) + '元(WEBAPP)';
                        }

                    } else {
                        amountStr = data.userSchemeInfo.account;
                        aprStr = data.scheme.maxApr
                    }
                    data.amountStr = amountStr;
                    data.aprStr = aprStr;

                    var v = data.scheme.version;
                    var htmlPath = 'planTender/planAgreement.html';
                    if(v != "v2.0") {
                        htmlPath = 'planTender/planAgreementV1.html';
                    }
                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + htmlPath + '?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                planHistoryCtrl.showPlanAgreement({
                                    result: result,
                                    data: data
                                });
                            }
                        });
                    } catch (e) {
                        xxdApp.hideIndicator();
                    }
                }
            });
        },
        showPlanAgreement: function (param) {
            var compiledTemplate = t7.compile(param.result);
            var output = compiledTemplate({
                userSchemeInfo: param.data.userSchemeInfo,
                realName: param.data.realName,
                user: param.data.user,
                scheme: param.data.scheme,
                endTime: param.data.endTime,
                endTime_y: param.data.endTime_y,
                endTime_m: param.data.endTime_m,
                endTime_d: param.data.endTime_d,
                amountStr:param.data.amountStr,
                aprStr:param.data.aprStr
            });

            $(".popup-bidhistory").html(output);
            xxdApp.popup('.popup-bidhistory');
        },
        toPay: function () {
            var planId = $(this).attr("data-id");
            GS.loadPage('planTender/planTender.html?planId=' + planId);
        }
    };
    return planHistoryCtrl;
});
