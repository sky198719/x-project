define(['js/utils/date'], function (DateHandle) {
    var yypId;
    var joinId;
    var paymentPlanStr = {
        init: function (event) {
            var query = appFunc.getEventDetailPageQuery(event);
            yypId = query.yypId;
            joinId = query.joinId;

            if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
            }

            paymentPlanStr.getProInfo();
            paymentPlanStr.queryJoinInfoByUserIdAndJoinId();
            paymentPlanStr.getInvestInfo();
            paymentPlanStr.getmentPlan();
            paymentPlanStr.bindEvent();
        },

        bindEvent: function () {
            var binding = [
                {
                    element: '#yyp-quit',
                    event: 'click',
                    handler: paymentPlanStr.toQuit
                },
                {
                    element: '.paymentPlan #yypAgreement',
                    event: 'click',
                    handler: paymentPlanStr.yypAgreement
                }
            ];
            appFunc.bindEvents(binding);
        },
        yypAgreement: function () {
            // 月月派协议
            req.callJSON({
                url: "yyp/agreementData/" + joinId + ".do",
                data: {},
                timeout: 15000,
                indicator: true,
                success: function (data) {
                    data.yypInfo.PNAME = '月月派' + data.yypInfo.TERMS + '个月';// - ' + data.yypInfo.NAME;

                    data.yypInfo.APR = data.yypInfo.APR + '%';

                    data.yypInfo.LEASTAMOUNT = appFunc.fmoney(data.yypInfo.LOWESTTENDER, 2);
                    data.yypInfo.MOSTAMOUNT = appFunc.fmoney(data.yypInfo.MOSTTENDER, 2);

                    data.yypInfo.OPENTIME = DateHandle.formatDate('yyyy年MM月dd日', data.yypInfo.OPENTIME);
                    data.yypInfo.CLOSETIME = DateHandle.formatDate('yyyy年MM月dd日', data.yypInfo.CLOSETIME);

                    var date = new Date(data.userJoinInfo.addTime.replace(/\-/g, "/"));
                    date = DateHandle.addDay(date, 1);
                    data.userJoinInfo.lockBegin = DateHandle.formatDate('yyyy年MM月dd日', date);
                    //锁定期限减一天
                    var lockDate = DateHandle.addDay(data.userJoinInfo.expireTime,-1);
                    data.userJoinInfo.lockEnd = DateHandle.formatDate('yyyy年MM月dd日', lockDate);

                    data.userJoinInfo.account = appFunc.fmoney(data.userJoinInfo.account, 2);

                    data.userJoinInfo.addTime = DateHandle.formatDate("yyyy年MM月dd日", data.userJoinInfo.addTime);
                    
                    var realName = data.userInfo.realName;
                    if(realName == null || realName == "" || realName == undefined){
                    	data.userInfo.realName = {};
                    }

                    data.noContent = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                    
                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'yyp/yypAgreement.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                paymentPlanStr.showYypAgreement({
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
        showYypAgreement: function (param) {
            var compiledTemplate = t7.compile(param.result);
            var output = compiledTemplate({
                userJoinInfo: param.data.userJoinInfo,
                userInfo: param.data.userInfo,
                productInfo: param.data.yypInfo,
                noContent:param.data.noContent
            });

            $(".popup-bidhistory").html(output);
            xxdApp.popup('.popup-bidhistory');
        },
        toQuit: function () {
            var yypThis = $$(this);
            var pterms = yypThis.data("terms");
            var pname = yypThis.data("name");
            var forfeitpercent = yypThis.data("forfeitpercent");

            paymentPlanStr.advanceQuit(joinId, pterms, pname, forfeitpercent);
        },
        advanceQuit: function (joinId, pterms, pname, forfeitpercent) {
            xxdApp.confirm('月月派' + pterms + '个月 - ' + pname + '正处于锁定期，您若选择退出，会产生违约行为，平台会征收您' + forfeitpercent + '%的违约金' + '，提交后不可取消，确认要退出吗？', '提前退出月月派', function () {
                //月月派提前退出GA
                gaClickEvent({property1:"转出",property2: "月月派转出", property3: window.location});
                xxdApp.modalPassword('请输入支付密码', '提前退出月月派', function (password) {
                    if (password == null || password == '') {
                        xxdApp.alert('请输入支付密码！');
                        return;
                    }
                    xxdApp.showIndicator('正在加载数据...');
                    req.callJSON({
                        url: "yyp/quit.do",
                        data: {
                            joinId: joinId,
                            payPwd: $.md5($.md5(password))
                        },
                        dataType: 'json',
                        success: function (data) {
                            xxdApp.hideIndicator();
                            if (data.resultCode == 0) {
                                redemption({id:data.productId,name:"月月派赎回",category:"月月派赎回/" + data.apr + "%/" + data.terms + "个月",price:data.price,tradeId:data.tradeid,affiliation:data.servicenum});
                                xxdApp.alert(data.desc, '提示', function () {
                                	mainView.router.back({url:"yyp/yypHistory.html?path=yyp?v=" + GC.getVersion()});
                                });
                            } else {
                                xxdApp.alert(data.desc, '提示');
                            }
                        }
                    });
                });
            });
        },
        queryJoinInfoByUserIdAndJoinId: function () {
            req.callJSON({
                url: 'yyp/queryJoinInfoByUserIdAndJoinId.do',
                data: {
                    joinId: joinId
                },
                success: function (result) {
                    if (result.resultCode != 0) {
                        console.log("queryJoinInfoByUserIdAndJoinId=" + result);
                        return;
                    }
                    //0支付中1投标中2到期退出3提前退出-1支付失败

                    var status = result.data.status;
                    var statusText = '';
                    if (status == 0) {
                        statusText = '支付中';
                    } else if (status == 1) {
                        statusText = '收益中';
                    } else if (status == 2) {
                        statusText = '已退出';
                    } else if (status == 3) {
                        statusText = '已提前退出';
                    } else if (status == 4) {
                        statusText = '退出中';
                    } else if (status == -1) {
                        statusText = '支付失败';
                    }
                    $$(".paymentPlan #status").html(statusText);
                }
            });
        },
        getInvestInfo: function () {
            req.callJSON({
                url: 'yyp/queryInvestInfo.do',
                data: {
                    joinId: joinId
                },
                success: function (result) {
                    if (result.resultCode != 0) {
                        xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: result.desc
                        });
                        return;
                    }

                    $$(".paymentPlan #EFFECTIVEMONEY").html(appFunc.fmoney(result.data.EFFECTIVEMONEY, 2));
                    $$(".paymentPlan #REALINTEREST").html(appFunc.fmoney(result.data.REALINTEREST, 2));
                    $$(".paymentPlan #WAITINTEREST").html(appFunc.fmoney(result.data.WAITINTEREST, 2));
                }
            });
        },
        getProInfo: function () {
            req.callJSON({
                url: 'yyp/detail/' + yypId + '.do',
                data: {},
                indicator: true,
                success: function (result) {
                    if (result.resultCode != 0) {
                        xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: result.desc
                        });
                        return;
                    }
                    var b = result.data;
                    $$(".paymentPlan #apr").html(b.APR);
                    $$(".paymentPlan #name").html("月月派" + b.TERMS + "个月 " + b.NAME);
                    var dmp_click_yyp = "5." + b.TERMS;
                    $("#yyp-quit").attr("dmp_target_id", dmp_click_yyp);
					//锁定期显示提前退出                    
                    if(b.STATUS == 3){
	                    $$("#yyp-quit").data("forfeitpercent", b.FORFEITPERCENT);
	                    $$("#yyp-quit").data("terms", b.TERMS);
	                    $$("#yyp-quit").data("name", b.NAME);
	                    $$("#yyp-quit").show();
	                    $$(".center").css("left","0px");
                    }

                }
            });
        },
        getmentPlan: function () {
            req.callGet({
                url: 'yyp/queryInterestList.do',
                data: {
                    joinId: joinId
                },
                dataType: 'json',
                success: function (data) {
                    if (data.resultCode != 0) {
                        xxdApp.addNotification({
                            title: '抱歉',
                            hold: 3000,
                            message: data.desc
                        });
                        return;
                    }

                    var oneList = [];
                    var twoList = [];
                    for (var i in data.resultList) {
                        var b = data.resultList[i];
                        if (b.STATUS == 0) {
                            twoList.push(b);
                        } else {
                            oneList.push(b);
                        }
                    }
                    
//                    oneList.push({
//                    	REALCAPITAL:10000,
//                    	REALINTEREST:1000,
//                    	REALTIME:"2016-09-28 11:09:35"
//                    })
//                    oneList.push({
//                    	REALCAPITAL:100000,
//                    	REALINTEREST:1000,
//                    	REALTIME:"2016-09-28 11:09:35"
//                    })
//                    twoList.push(twoList[0]);
//                    twoList.push(twoList[0]);
//                    twoList.push(twoList[0]);
//                    twoList.push(twoList[0]);

                    if (oneList.length > 0) {
                        oneList = paymentPlanStr.sortOneList(oneList);
                        var html = '';
                        for (var i in oneList) {
                            var b = oneList[i];
                            html += '<div class="row ';
                            if ((parseFloat(i) + 1) != oneList.length) {
                                html += 'yyp-mentplan-border-bottom';
                            }
                            html += ' lidiv">';

                            html += '<div class="col-33 text-center" style="width: 30%;">' + appFunc.fmoney(b.REALCAPITAL, 2) + '</div>';
                            html += '<div class="col-33 text-center"  style="width: 30%;">' + appFunc.fmoney(b.REALINTEREST, 2) + '</div>';
                            var REALTIME = b.REALTIME;
                            REALTIME = REALTIME.substring(0, 10);
                            html += '<div class="col-33"  style="width: 40%;text-align: left;">' + REALTIME + '</div>';
                            html += ' </div>';
                        }
                        $$(".yyp-mentplan-ul-oneLi").html(html);
                        $$(".yyp-mentplan-ul-one").show();
                    }



                    if (twoList.length > 0) {
                        twoList = paymentPlanStr.sortTwoList(twoList);
                        var html = '';
                        for (var i in twoList) {
                            var b = twoList[i];
                            html += '<div class="row ';
                            if ((parseFloat(i) + 1) != twoList.length) {
                                html += 'yyp-mentplan-border-bottom';
                            }
                            html += ' lidiv"';
                            if (i == 0) {
                                html += ' style="color:#F39800" ';
                            }
                            html += '>';

                            html += '<div class="col-33 text-center" style="width: 30%;">' + appFunc.fmoney(b.PLANNEDCAPITAL, 2) + '</div>';
                            html += '<div class="col-33 text-center" style="width: 30%;">' + appFunc.fmoney(b.PLANNEDINTEREST, 2) + '</div>';

                            var PLANNEDTIME = b.PLANNEDTIME;
                            PLANNEDTIME = PLANNEDTIME.substring(0, 10);
                            if (i == 0) {
                                html += '<div class="col-33" style="width: 40%;text-align: left;">';
                                html += '<span>' + PLANNEDTIME + '</span>';
                                html += ' <span class="yyp-mentplan-span">下一期</span>';
                                html += '</div>';
                            } else {
                                html += '<div class="col-33" style="width: 40%;text-align: left;">' + PLANNEDTIME + '</div>';
                            }

                            html += '</div>';
                        }

                        $$(".yyp-mentplan-ul-twoLi").html(html);
                        $$(".yyp-mentplan-ul-two").show();
                    }

                }
            });
        },
        sortOneList:function(list){
            list.sort(function(a,b){
                 return DateHandle.parseDate(a.REALTIME).getTime() -  DateHandle.parseDate(b.REALTIME).getTime();
             });
            return list;
        },
        sortTwoList:function(list){
            list.sort(function(a,b){
                 return DateHandle.parseDate(a.PLANNEDTIME).getTime() -  DateHandle.parseDate(b.PLANNEDTIME).getTime();
             });
            return list;
        }
    };
    return paymentPlanStr;
});
