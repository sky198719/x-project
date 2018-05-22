define(function(){

    var xxbExhangeCtl = {
        init:function(){
        	mainView.showNavbar();
            var bindings = [
                {
                    element: '#xxb_randImage',
                    event: 'click',
                    handler: xxbExhangeCtl.refVerifyCode
                },
                {
                    element:'#xxb_exchangemoney',
                    event:'keyup',
                    handler:xxbExhangeCtl.xxbMoney
                },
                {
                    element:'#xxb_exchange',
                    event:'click',
                    handler:xxbExhangeCtl.doExchange
                },
                {
                    element:'#xxb_exchange_logs',
                    event:'click',
                    handler:xxbExhangeCtl.goExchangeLogs
                }
            ];
            appFunc.bindEvents(bindings);
            xxbExhangeCtl.getXxbInfo();
            xxbExhangeCtl.hasPayPassword();

        },
        doExchange:function(){
            req.callJSON({
                url: "personal/info.do",
                data: {},
                async:false,
                preloaderTitle: true,
                success: function (data) {
                    var isopenaccount = data.isopenaccount;
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
                    } else if (data.namestatus != '1') {
                        if (data.realname != null ) {
                            if(data.realname.status == 0) {
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
                            } else if(data.realname.status == 5) {
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
                    } else if(isopenaccount != undefined && isopenaccount == 0) {
                        xxdApp.modal({
                            title: '提示',
                            afterText: '为提升您的资金安全，建议您立即开通银行存管账户',
                            buttons: [
                                {
                                    text: '取消',
                                    onClick: function() {

                                    }
                                },
                                {
                                    text: '立即开户',
                                    onClick: function() {

                                        window.location.href = data.activity_url + "webh5/usercenter/openAccount";
                                    }
                                }
                            ]
                        });
                    } else {

                        if (data.paypwd != 0) {
                        }else{
                            xxdApp.alert("请先设置账户支付密码", '提示', function () {
                                GS.loadPage("personal/setPassword.html?path=personal&from=exchangeXXCoin");
                            });

                        }

                        if ($$("#xxb_exchange").hasClass("button-51-disable")) {
                            return;
                        }
                        var xxb_exchangemoney = $$("#xxb_exchangemoney").val();
                        if(xxb_exchangemoney == null || xxb_exchangemoney == '') {
                            //xxdApp.alert("请输入需要兑换的金额","抱歉");
                            $$("#xe_amount_note").html("请输入需要兑换的金额");
                            document.getElementById("xe_amount_note").style.display = "block";
                            return;
                        } else {
                            document.getElementById("xe_amount_note").style.display = "none";
                        }

                        if(xxb_exchangemoney>1000) {
                            //xxdApp.alert("每次兑换金额最大不能超过1000元","抱歉");
                            $$("#xe_amount_note").html("每次兑换金额最大不能超过1000元");
                            document.getElementById("xe_amount_note").style.display = "block";
                            return;
                        } else {
                            document.getElementById("xe_amount_note").style.display = "none";
                        }

                        if(xxb_exchangemoney<0.1) {
                            $$("#xe_amount_note").html("兑换金额不能小于0.1元，请重新输入");
                            document.getElementById("xe_amount_note").style.display = "block";
                            return;
                        }else if(xxb_exchangemoney*100%2!=0) {
                            $$("#xe_amount_note").html("每次必须兑换整数个新新币");
                            document.getElementById("xe_amount_note").style.display = "block";
                            return;
                        } else {
                            document.getElementById("xe_amount_note").style.display = "none";
                        }

                        var xxb_pwd = $$("#xxb_pwd").val();
                        if(xxb_pwd == null || xxb_pwd == '') {
                            //xxdApp.alert("请输入您的支付密码","抱歉");
                            $$("#xe_pwd_note").html("请输入您的支付密码");
                            document.getElementById("xe_pwd_note").style.display = "block";
                            return;
                        } else {
                            document.getElementById("xe_pwd_note").style.display = "none";
                        }

                        var xxb_randCode = $$("#xxb_randCode").val();
                        if(xxb_randCode == null || xxb_randCode == '') {
                            //xxdApp.alert("请输入图片验证码","抱歉");
                            $$("#xe_randcode_note").html("请输入图片验证码");
                            document.getElementById("xe_randcode_note").style.display = "block";
                            return;
                        } else {
                            document.getElementById("xe_randcode_note").style.display = "none";
                        }
                        $$("#xxb_exchange").html("兑换中...");
                        $$("#xxb_exchange").removeClass("button-51");
                        $$("#xxb_exchange").addClass("button-51-disable");

                        var xxb_exchange_num = $$("#xxb_exchange_num").html();
                        req.callJSON({
                            url: 'xxb/exchange.do',
                            data: {
                                exchangeNum:xxb_exchange_num,
                                pwd:$.md5(xxb_pwd),
                                randCode:xxb_randCode
                            },
                            dataType: 'json',
                            success: function (result) {

                                $$("#xxb_exchange").removeClass("button-51-disable");
                                $$("#xxb_exchange").addClass("button-51");
                                $$("#xxb_exchange").html("确认兑换");

                                console.log(result);
                                if(result.code == 200000 && result.data.code == 0) {
                                    xxdApp.modal({
                                        title: '提示',
                                        text: '恭喜您，兑换成功！',
                                        buttons: []
                                    });
                                    setTimeout(function () {
                                        xxdApp.closeModal();
                                        xxbExhangeCtl.refreshXxbInfo();
                                        GS.loadPage("xxb/exchangeLogs.html?path=xxb");
                                    }, 2000);
                                } else {
                                    var msg = result.message;
                                    if(result.data.code != 0) {
                                        msg = result.data.message;
                                    }
                                    xxdApp.modal({
                                        title:  '提示',
                                        text: msg,
                                        buttons: [
                                        ]
                                    });
                                    setTimeout(function(){
                                        xxdApp.closeModal();
                                    },2000);

                                }

                                xxbExhangeCtl.refVerifyCode();
                            }
                        });
                    }
                }
            });
        },
        xxbMoney:function(){
            var coins = $$("span[name='xxb_coins']").data("coins");
            var value = $(this).val();
            value=value.replace(/[^\d.]/g,"").replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数的数字
            $(this).val(value);
            var xxb_exchange = value * 50;
            $$("#xxb_exchange_num").html(xxb_exchange);
            if (coins > 5000) {
                if(xxb_exchange > 5000) {
                    $$("#xe_amount_note").html("每次兑换金额最大不能超过1000元");
                    document.getElementById("xe_amount_note").style.display = "block";
                    $$("#xxb_exchange_num").html("50000");
                } else {
                    document.getElementById("xe_amount_note").style.display = "none";
                }
            }else {
                if(xxb_exchange > coins) {
                    $$("#xe_amount_note").html("新新币不足，您最多可兑换"+appFunc.fmoney(coins/50,2) + "元");
                    document.getElementById("xe_amount_note").style.display = "block";
                    $$("#xxb_exchange_num").html(coins);
                } else {
                    document.getElementById("xe_amount_note").style.display = "none";
                }
            }

            var money = $$("span[name='xxb_money']").data("money");
            money = money > 1000 ? 1000:money;
            if(value > money)  {
                $(this).val(money);
            }
        },
        getXxbInfo:function(){
             req.callJSON({
                 url: 'xxb/getXxbInfo.do',
                    data: {},
                    dataType: 'json',
                    success: function (result) {
                        $$("span[name='xxb_coins']").html(result.levels.coins+"个");
                        $$("span[name='xxb_coins']").data("coins",result.levels.coins);
                        $$("span[name='xxb_money']").html(appFunc.fmoney(result.levels.coins/50,2) + "元");
                        $$("span[name='xxb_money']").data("money",result.levels.coins/50);
                    }
             });
        },
        hasPayPassword:function(){
             req.callJSON({
                 url: 'personal/info.do',
                    data: {},
                    dataType: 'json',
                    indicator: true,
                    success: function (result) {
                        var isopenaccount = result.isopenaccount;
                        if(isopenaccount != undefined && isopenaccount == 1) {
                            if (result.paypwd != 0) {
                            }else{
                                xxdApp.alert("请先设置账户支付密码", '提示', function () {
                                    GS.loadPage("personal/setPassword.html?path=personal&from=exchangeXXCoin");
                                });
                            }
                        }
                    }
             });
        },
        refVerifyCode:function(){
            document.getElementById("xxb_randImage").src = "randCode/createVerifyCode.do?" + Math.random();
        },
        goExchangeLogs:function(){
            GS.loadPage("xxb/xxbDetail.html?path=xxb");
        },
        refreshXxbInfo:function() {
            xxbExhangeCtl.getXxbInfo();
            $$("#xxb_exchangemoney").val('');
            $$("#xxb_exchange_num").html('0');
            $$("#xxb_pwd").val('');
            $$("#xxb_randCode").val('');
            xxbExhangeCtl.refVerifyCode();
        }
    };
    return xxbExhangeCtl;
});