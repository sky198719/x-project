/**
 * 借款标列表
 */
define(['js/personal/personalView'], function (personalView) {
    var data;
    var personalCtrl = {
        init: function () {

            req.callJSON({
                url: "personal/info.do",
                data: {},
                indicator:true,
                success: function (result) {
                    data = result;

                    personalCtrl.realnameappro();

                    personalCtrl.paypwd();

                    personalCtrl.vip();

                    personalCtrl.mobile();

                    personalCtrl.email();

                    personalCtrl.bank();

                    personalCtrl.openAccount();

                }
            });


            req.callGet({
                url: 'activity/getRiskResult.do',
                dataType: 'json',
                success: function (result) {
                    if (result.resultCode == 0 && result.status) {
                        var html = '<div class="item-inner">'
                            + '<div class="item-title-row">'
                            + '<div class="item-title"><h5>风险评测</h5></div>'
                            + '<div class="item-after"><h6>' + result.resultInfo.typeName + '</h6></div>'
                            + '</div>'
                            + '<div class="item-text mt10" style="height:auto;"><h6 class="font-blue">已测评</h6>'
                            + '</div>'
                            + '</div>';
                        $("#riskUrl").html(html);
                    }
                    $("#riskUrl").on("click", function () {
                        window.location.href = result.activity_url + "/html/riskAssessment/riskAssessment-web.html";
                    });
                }
            });
        },
        openAccount: function () {
            var isopenaccount = data.isopenaccount;

            if (isopenaccount != undefined && isopenaccount == 1) {
                var html = '<div class="item-title-row item-title-nobg">'
                    + '<div class="item-title"><h5>银行存管开户</h5></div>'
                    + '</div>'
                    + '<div class="item-text mt10" style="height:auto;"><h6>已开户</h6>'
                    + '</div>';
                $$("#openOnAccount").html(html);
                $$("#openOnAccount_url").attr("href", "#");
            } else {
                $$("#openOnAccount_url").on("click", function () {

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
                        if (data.realname != null) {
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
                            } else  {
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
                    } else {
                        window.location.href = data.activity_url + "webh5/usercenter/openAccount";
                    }
                });
            }

        },
        realnameappro: function () {
            var html = "";
            //实名认证
            $$("#realnameapproUrl").attr("href", GC.getHtmlPath() + "personal/idCertified.html?path=personal");
            if (data.namestatus == '1') {
                html += '<div class="item-title-row item-title-nobg">' +
                    '<div class="item-title"><h5>实名认证</h5></div>' +
                    '<div class="item-after"><h6></h6></div>' +
                    '</div>' +
                    '<div class="item-text mt10" style="height:auto;"><h6>' + data.realname.realName + '(' + data.realname.idCardNo + ')</h6></div>';
                $$("#realnameapproUrl").attr("href", "#");
            } else {
                html = '<div class="item-title-row">' +
                    '<div class="item-title"><h5>实名认证</h5></div>';
                if (data.realname != null && data.realname.status == 0) {
                    html += '<div class="item-after"><h6></h6></div></div>' +
                        '<div class="item-text mt10" style="height:auto;"><h6 class="font-blue">待审核</h6></div>';
                    $$("#realnameapproUrl").attr("href", "#");
                } else if (data.realname != null && data.realname.status == 2) {
                    html += '<div class="item-after"><h6>请重新提交</h6></div></div>' +
                        '<div class="item-text mt10" style="height:auto;"><h6 class="font-blue">' + data.realname.approRemark + '</h6></div>'
                } else if (data.realname != null && data.realname.status == 5) {
                    html += '<div class="item-after"><h6>请重新提交</h6></div></div>' +
                        '<div class="item-text mt10" style="height:auto;"><h6 class="font-blue">' + data.realname.approRemark + '</h6></div>'
                    $$("#realnameapproUrl").attr("href", GC.getHtmlPath() + "personal/idCertified.html?realType=1&path=personal");
                } else {
                    html += '<div class="item-after"><h6>设置</h6></div></div>' +
                        '<div class="item-text mt10" style="height:auto;"><h6 class="font-blue">未设置</h6></div>'
                }
            }
            $$("#realnameappro").html(html);
        },
        paypwd: function () {
            var html = '';
            //支付密码
            if (data.paypwd != 0) {
                html = '<div class="item-title-row item-title-nobg">' +
                    '<div class="item-title"><h5>支付密码</h5></div>' +
                    '<div class="item-after"><h6></h6></div>' +
                    '</div>' +
                    '<div class="item-text mt10" style="height:auto;">已设置</div>';
                $$("#paypwdUrl").attr("href", "#");
                $$("#paypwd").html(html);
            } else {
                $$("#paypwdUrl").attr("href", GC.getHtmlPath() + "personal/setPassword.html?path=personal");
            }
        },
        vip: function () {
            var html = '';
            //vip认证
            $$("#vipUrl").attr("href", GC.getHtmlPath() + "personal/vipCertified.html?path=personal");
            if (data.vip != null) {
                if (data.vip.status == 2) {
                    html = '<div class="item-title-row">' +
                        '<div class="item-title"><h5>VIP认证</h5></div>' +
                        '<div class="item-after"><h6>未通过，请重新提交</h6></div>' +
                        '</div>' +
                        '<div class="item-text mt10" style="height:auto;"><h6 class="font-blue">设置</h6></div>'
                } else if (data.vip.status == 0) {
                    html = '<div class="item-title-row">' +
                        '<div class="item-title"><h5>VIP认证</h5></div>' +
                        '<div class="item-after"><h6>重新设置</h6></div>' +
                        '</div>' +
                        '<div class="item-text mt10" style="height:auto;">';
                    if (data.employeeName != null && data.employeeName != "") {
                        html += '<h6 class="font-blue">专属客服：' + data.employeeName;
                    } else {
                        html += "客服编号：" + data.vip.serviceNum;
                    }
                    html += '&nbsp;&nbsp;&nbsp;待审核';
                    $$("#vipUrl").attr("href", GC.getHtmlPath() + "personal/vipCertified.html?path=personal&serviceNum=" + data.vip.serviceNum);
                } else if (data.vip.status == 1) {
                    if (data.expireDate != null) {
                        var expireDate = data.expireDate;
                        html = '<div class="item-title-row">' +
                            '<div class="item-title"><h5>VIP认证</h5></div>' +
                            '<div class="item-after"><h6>重新认证</h6></div></div>' +
                            '<div class="item-text mt10" style="height:auto;">'
                        if (data.inday < 0) {
                            if (data.employeeName != null && data.employeeName != '') {
                                html += '<h6>专属客服：' + data.employeeName
                            }
                            html += '，VIP认证已过期' +
                                '<br/>到期时间：' + expireDate;
                        } else {
                            if (data.employeeName != null && data.employeeName != '') {
                                html += '<h6>专属客服：' + data.employeeName
                            }
                            html += '，VIP认证即将到期' +
                                '<br/>到期时间：' + expireDate
                        }
                        $$("#vipUrl").attr("href", GC.getHtmlPath() + "personal/vipCertified.html?path=personal&serviceNum=" + data.vip.serviceNum + "&exclusivekf=" + data.employeeName);
                    } else {
                        html = '<div class="item-title-row">' +
                            '<div class="item-title"><h5>VIP认证</h5></div>';
                        html += '<div class="item-after"><h6>修改</h6></div>' +
                            '</div>' +
                            '<div class="item-text mt10" style="height:auto;">'
                        if (data.employeeName == null || data.employeeName == '') {
                            html += '<h6>已认证'
                        } else {
                            html += '<h6>专属客服：' + data.employeeName
                        }
                        $$("#vipUrl").attr("href", GC.getHtmlPath() + "personal/vipCertified.html?path=personal&serviceNum=" + data.vip.serviceNum + "&exclusivekf=" + data.employeeName);
                    }
                }
                html += '</h6></div>'
                $$("#vip").html(html);
            } else {
                $$("#vipUrl").attr("href", GC.getHtmlPath() + "personal/vipCertified.html?path=personal");
            }

        },
        mobile: function () {
            var html = '';
            //手机绑定
            $$("#mobileUrl").attr("href", GC.getHtmlPath() + "personal/pMobile.html?path=personal");
            if (data.mobile != null) {
                html = '<div class="item-title-row item-title-nobg">' +
                    '<div class="item-title"><h5>手机绑定</h5></div>'
                if (data.mobile.status == 1) {
                    html += '<div class="item-after"><h6></h6></div>' +
                        '</div>' +
                        '<div class="item-text mt10" style="height:auto;"><h6>已绑定：' + data.mobile.mobileNo + '</h6></div>';
                }
                $$("#mobileUrl").attr("href", "#");
                $$("#mobile").html(html)
            }
        },
        email: function () {
            var html = '';
            //邮箱绑定
            $$("#emailUrl").attr("href", GC.getHtmlPath() + "personal/pEmail.html?path=personal");
            if (data.email != null) {
                html = '<div class="item-title-row item-title-nobg">' +
                    '<div class="item-title"><h5>邮箱绑定</h5></div>'
                if (data.email.status == 1) {
                    html += '</div>' +
                        '<div class="item-text mt10" style="height:auto;"><h6>已绑定：' + data.email.email + '</h6></div>'
                }

                $$("#email").html(html)
                $$("#emailUrl").attr("href", "#");
            }
        },
        bank: function () {
            if (data.namestatus == 1 && data.mobile != '') {
                var isopenaccount = data.isopenaccount;
                if (isopenaccount != undefined && isopenaccount == 0) {
                    $$("#bankUrl").on("click", function () {
                        xxdApp.modal({
                            title: '提示',
                            afterText: '为提升您的资金安全，建议您立即开通银行存管账户',
                            buttons: [
                                {
                                    text: '取消',
                                    onClick: function () {

                                    }
                                },
                                {
                                    text: '立即开户',
                                    onClick: function () {

                                        window.location.href = data.activity_url + "webh5/usercenter/openAccount";
                                    }
                                }
                            ]
                        });
                    });
                } else {
                    var html = '';
                    //银行卡管理
                    $$("#bankUrl").attr("href", GC.getHtmlPath() + "personal/personalBank.html?path=personal");
                    if (data.bankInfo != null) {
                        html = '<div class="item-title-row">' +
                            '<div class="item-title"><h5>银行卡管理</h5></div>' +
                            '<div class="item-after"><h6>修改</h6></div></div>' +
                            '<div class="item-text mt10" style="height:auto;"><h6>' + data.bankName + ' 尾号：' + data.bankInfo.bankAccount + '</h6></div>';
                        $$("#bank").html(html)
                    }
                }
            } else {

                if (data.mobile == undefined || data.mobile == '') {
                    $$("#bankUrl").on("click", function () {
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
                    });

                    return;
                }

                //没有实名认证 不能去银行卡管理页面
                if (data.namestatus != 1) {

                    $$("#bankUrl").on("click", function () {
                        if (data.realname != null) {
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

                    });
                    return;
                }
            }
        }
    };
    return personalCtrl;
});