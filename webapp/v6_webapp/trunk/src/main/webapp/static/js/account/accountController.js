/**
 * 借款标列表
 */
define(['chart', 'chartDoughnut', 'js/account/accountView', 'js/utils/animateNumber','js/account/openOnAccount'], function (Chart, Doughnut, accountView, animateNumber,openOnAccount) {
    var job = 'default';
    var totalTender = 0;
    var accountCtrl = {
        init: function (event) {
            job = appFunc.getEventDetailPageQuery(event).job;
            $$('#job').val(job);
            accountCtrl.loadAccount();
            accountCtrl.getUser();
//            accountCtrl.collintest();
            accountCtrl.eventBinding();
            accountCtrl.verificationAuthentication();


            req.callJSON({
                url: 'user/userDetailInfo.do',
                success: function (result) {
                    if (result.code == 200000) {
                        var user = result.data.user;
                        var source = user.source;
                        var hasinitremind = user.hasinitremind;
                        if (source == 'CLIENT_XXD_BACKGROUND_BATCH' && hasinitremind == 0) {
                            var mobile = user.mobile;
                            mobile = mobile.substring(0, 3) + '****' + mobile.substring(7, 11);
                            var html = '<div style="text-align: left;line-height: 1.4rem;margin: 10px;">为提升您的资金安全，系统已成功为您开通华瑞银行存管账户，建议您尽快登录官网修改存管账户登录密码。</div>';
                            html += '<div style="color:red;text-align: left;line-height: 1.4rem;margin: 10px;margin-top: 10px;">注：存管账户登录名为您的手机号' + mobile + '，初始默认登录密码及支付密码为手机号后六位。</div>';

                            var title = '<img src="static/img/xxd/piliangcunguan.png" style="width: 120px;margin-top: -60px;"/>';
                            xxdApp.alert(html, title, function () {
                                accountCtrl.confirmInitRemindByToken();
                            });
                        }
                    }
                }
            });

        },
        confirmInitRemindByToken:function(){
            req.callGet({
                url:'user/confirmInitRemindByToken.do',
                dataType:'json',
                success:function(result){
                    console.log(result);
                }
            });
        },
        eventBinding: function () {
            var bindings = [
                {
                    element: '#personal-info',
                    event: 'click',
                    handler: accountCtrl.personalInfo
                },
                {
                    element: '#account-to-recharge',
                    event: 'click',
                    handler: accountCtrl.toRecharge
                },
                {
                    element: '#credit-tender',
                    event: 'click',
                    handler: accountCtrl.creditTender
                },
                {
                    element: '#bid-history',
                    event: 'click',
                    handler: accountCtrl.bidhistory
                },
                {
                    element: '#payment-plan',
                    event: 'click',
                    handler: accountCtrl.paymentPlan
                },
                {
                    element: '#hongbao',
                    event: 'click',
                    handler: accountCtrl.hongbao
                },
                {
                    element: '#popularizeBinding',
                    event: 'click',
                    handler: accountCtrl.popularizeBinding
                },
                {
                    element: '#draw-to-money',
                    event: 'click',
                    handler: accountCtrl.drawMoney
                },
                {
                    element: '#activity',
                    event: 'click',
                    handler: accountCtrl.activity
                },
                {
                    element: '#invitation',
                    event: 'click',
                    handler: accountCtrl.invitation
                },
                {
                    element: '#account_logout',
                    event: 'click',
                    handler: accountCtrl.logout
                },
                {
                    element: '#xxb',
                    event: 'click',
                    handler: accountCtrl.xxb
                },
                {
                    element: '#feedback',
                    event: 'click',
                    handler: accountCtrl.feedback
                }
            ];

            accountView.init({bindings: bindings});
        },

        xxb: function () {
            GS.loadPage("xxb/exchange.html");
        },

        collintest: function () {
            req.callPost({
                url: "account/myincome.do",
                dataType: 'json',
                success: function (data) {
                    if (data.collintest != '0') {
                        //$$('#collintest').html(appFunc.fmoney(data.collintest,2));
                        animateNumber.animate({
                            element: "#collintest",
                            from: 0,
                            to: data.collintest,
                            precision: 2,
                            intervalNumber: 30,
                            steps: 50,
                            valueType: 'money'
                        });
                    }
                }
            });
        },

        getUser: function () {
            req.callPost({
                url: 'user/getCurrentUser.do',
                data: {},
                dataType: 'json',
                success: function (result) {
                    $("div[name='account_userName']").html("您好，" + result.data.user.userName);
                }
            });
        },

        /**
         * 退出
         */
        logout: function () {
            xxdApp.confirm('是否确认要退出？', '确认退出', function () {
                req.callGet({
                    url: 'user/logout.do',
                    success: function (result) {
                        if (result == '0') {
                        	//try{XXD_TRACK.track_eventview("logout_success_webapp", "button", "退出成功");}catch(e){}
                            xxdApp.alert('您已安全退出，祝您生活愉快', '提示',function(){
                                GS.reloadPage("index/home.html");
                            });
                        }
                    }
                });
            });
        },
        verificationAuthentication: function () {
            req.callJSON({
                url: "personal/info.do",
                data: {},
                success: function (data) {
                    // 实名认证 && 支付密码
                    if (!(data.namestatus == '1' && data.paypwd != 0)) {
                        xxdApp.confirm('您还未完善个人资料，请先完善资料，全面体验新新贷服务', '提示', function () {
                            GS.loadPage("personal/personalInfo.html");
                        }, function () {
                            return false;
                        });
                    }
                }
            });
        },
        loadAccount: function () {
            req.callPost({
                url: "account/myaccount.do",
                dataType: 'json',
                indicator: true,
                timeout: 15000,
                success: function (result) {
                    if(result.code == 0) {
                        accountView.showAccount(result);
                        if(result.statistics != undefined && result.statistics.code == 200000) {

                            var statistics = result.statistics;
                            totalTender = statistics.data.qitiandashengMap.EFFECTIVEMONEY;

                            totalTender += statistics.data.sanbiaoMap.EFFECTIVEMONEY;

                            totalTender += statistics.data.stepMap.EFFECTIVEMONEY;

                            totalTender += statistics.data.xfdMap.EFFECTIVEMONEY;

                            totalTender += statistics.data.xinshoubiaoMap.EFFECTIVEMONEY;

                            totalTender += statistics.data.xinyuanbaoMap.EFFECTIVEMONEY;

                            totalTender += statistics.data.yuejindoujinMap.EFFECTIVEMONEY;

                            totalTender += statistics.data.yypMap.EFFECTIVEMONEY;
                        }
                    }
                }
            });
        },
        invitation: function () {
            /**
             * 向后台查询用户是否已同意推广协议
             */
            req.callJSON({
                url: "popularize/goMyPopularizeActivity.do",
                data: {},
                success: function (data) {
                    if (data.isPopularizeAgree == "true") {
                        /**
                         *查询用户是否已经参与新新推广activityCode='consortium_extension'
                         *如果已经参与直接跳转到新新推广页面，如果没有参与则自动报名帮其参与新新推广
                         */
                        req.callJSON({
                            url: "popularize/isJoinActivity.do",
                            data: {activityCode: 'consortium_extension'},
                            success: function (data) {
                                if (data.isJoinActivity == 'true') {
                                    GS.loadPage('popularize/myActivity.html?path=popularize');
                                } else {
                                    accountCtrl.userJoinActivity();
                                }
                            }
                        });
                        GS.loadPage('popularize/myActivity.html?path=popularize');
                    } else {
                        accountCtrl.loadPopularizeAgreement();
                    }
                }
            });
        },
        loadPopularizeAgreement: function () {
            req.callGet({
                url: GC.getHtmlPath() + 'popularize/popularizeAgreement.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    $$("#indexPickerModal").html(result);
                    var bindings = [
                        {
                            element: '#agreeAgreement',
                            event: 'click',
                            handler: accountCtrl.agreeAgreement
                        }
                    ];
                    appFunc.bindEvents(bindings);
                    xxdApp.pickerModal(".picker-info");
                }
            });
        },
        agreeAgreement: function () {
            /**
             * 同意推广协议
             */
            req.callJSON({
                url: "popularize/agreeAgreement.do",
                data: {},
                success: function (data) {
                    if (data.resultCode == 0) {
                        accountCtrl.userJoinActivity();
                    } else {
                        xxdApp.alert(data.msg, "提示");
                    }
                }
            });
        },
        userJoinActivity: function () {
            /**
             帮助用户加入新新推广
             */
            req.callJSON({
                url: "popularize/userJoinActivity.do",
                data: {activityCode: 'consortium_extension'},
                success: function (data) {
                    if (data.resultCode == 0) {
                        GS.loadPage('popularize/myActivity.html?path=popularize');
                    } else {
                        xxdApp.alert("加入新新推广活动失败，请重新尝试", "提示");
                    }
                }
            });
        },


        popularizeBinding: function () {
        	//try {XXD_TRACK._trackEvent({category: "webapp_code_binding", action: "code_binding_button", label: "推广码绑定", value: "", custval: "" });} catch (e) {}
            GS.loadPage("popularize/binding.html?path=popularize");
        },
        hongbao: function () {
            GS.loadPage("account/hongbao.html?path=account");
        },
        toRecharge: function () {
        	//try {XXD_TRACK._trackEvent({category: "webapp_recharge", action: "recharge_btn", label: "充值", value: "", custval: "" });} catch (e) {}
			GS.goTopup();
        },
        personalInfo: function () {
            GS.loadPage("personal/personalInfo.html");
        },

        creditTender: function () {
            GS.loadPage("account/credit.html?path=account");
        },
        bidhistory: function () {
            GS.loadPage("account/bid-history.html?path=account&totalTender=" + totalTender);
        },
        paymentPlan: function () {
            GS.loadPage("account/payment-plan.html?path=account");
        },
        drawMoney: function () {


            req.callJSON({
                url: "personal/info.do",
                data: {},
                indicator: true,
                success: function (data) {
                    if (data.mobile == null) {
                        xxdApp.modal({
                            title: '提示',
                            afterText: '您尚未进行手机认证',
                            buttons: [
                                {
                                    text: '取消',
                                    onClick: function () {
                                    }
                                },
                                {
                                    text: '现在就去',
                                    onClick: function () {
                                        GS.loadPage("personal/pMobile.html");
                                    }
                                }
                            ]
                        });
                    } else if (data.namestatus == '1') {
                        openOnAccount.isOpenOnAccount({
                            title:'为提升您的资金安全，建议您立即开通银行存管账户',
                            callBack:function(){
                                GS.loadPage("account/drawmoney.html?path=account");
                            }
                        });

                    } else {
                        if (data.realname != null ) {
                            if(data.realname.status == 1) {
                                GS.loadPage("account/drawmoney.html?path=account");
                            } else if(data.realname.status == 0) {
                                xxdApp.modal({
                                    title: '提示',
                                    afterText: '您当前实名认证正在审核中，审核通过后请开户',
                                    buttons: [
                                        {
                                            text: '确定',
                                            onClick: function () {
                                            }
                                        }
                                    ]
                                });
                            } else {
                                xxdApp.modal({
                                    title: '提示',
                                    afterText: '您尚未进行实名认证',
                                    buttons: [
                                        {
                                            text: '取消',
                                            onClick: function () {
                                            }
                                        },
                                        {
                                            text: '现在就去',
                                            onClick: function () {
                                                GS.loadPage("personal/idCertified.html?realType=1&path=personal");
                                            }
                                        }
                                    ]
                                });
                            }
                        } else {
                            xxdApp.modal({
                                title: '提示',
                                afterText: '您尚未进行实名认证',
                                buttons: [
                                    {
                                        text: '取消',
                                        onClick: function () {
                                        }
                                    },
                                    {
                                        text: '现在就去',
                                        onClick: function () {
                                            GS.loadPage("personal/idCertified.html?path=personal");
                                        }
                                    }
                                ]
                            });
                        }

                    }
                }
            });

        },
        activity: function () {
            GS.loadPage("weixin/activity/activityLicai.html?path=activity");
        },
        feedback: function () {
            GS.loadPage("account/feedback.html?path=account");
        }
    };
    return accountCtrl;
});
