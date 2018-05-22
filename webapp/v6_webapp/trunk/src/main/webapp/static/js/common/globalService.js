define(['js/utils/date','js/account/openOnAccount'], function (DateHandle,openOnAccount) {
    var globalService = {
        init: function () {

        },
        getSysConfig: function () {
            var result = {};
            try {
                result = globalService.loadDataJSON({
                    url: 'sysConfig.json',
                    version: DateHandle.formatDate('yyyyMMddHHmm', new Date()),
                    async: false
                });
            } catch (e) {
                xxdLog.debug("globalService.getSysConfig：" + e);
            }
            return result;
        },
        getBorrowType: function () {
            var result = {};
            try {
                result = globalService.loadDataJSON({
                    url: 'borrowType.json',
                    version: DateHandle.formatDate('yyyyMMddHHmm', new Date()),
                    async: false
                });
            } catch (e) {
                xxdLog.debug("globalService.getBorrowType：" + e);
            }
            return result;
        },
        getBankTelephone: function () {
            var result = {};
            try {
                result = globalService.loadDataJSON({
                    url: 'bankTelephone.json',
                    version: DateHandle.formatDate('yyyyMMddHHmm', new Date()),
                    async: false
                });
            } catch (e) {
                xxdLog.debug("globalService.getBankTelephone：" + e);
            }
            return result;
        },

        loadDataJSON: function (options) {
            var result = {};
            var isasync = 'async' in options ? options.async : true;
            req.callGet({
                url: GC.getDataPath() + options.url + '?' + options.version,
                dataType: 'json',
                async: isasync,
                success: function (data) {
                    if (isasync === true) {
                        options.callBack(data);
                    } else {
                        result = data;
                    }
                }
            });
            return result;
        },

        isSupportAnimate: function () {
            if (!GC.isAnimate()) {
                return false;
            }

            if (globalService.isAnroidSysAnimate() && globalService.isIosSysAnimate()) {
                return true;
            }
            return false;
        },
        isAnroidSysAnimate: function () {
            if (!GC.isAnimate()) {
                return false;
            }
            var os = xxdApp.device.os;
            os = os == undefined ? 'other' : os;
            if (os == 'other') {
                return true;
            }
            var osName = os.toLowerCase();
            var osVersion = xxdApp.device.osVersion;
            var android = 'android';
            if (osName == android && osVersion >= "4" && GC.isAndroidAnimate()) {
                return true;
            }
            return false;
        },
        isIosSysAnimate: function () {
            if (!GC.isAnimate()) {
                return false;
            }
            var os = xxdApp.device.os;
            os = os == undefined ? 'other' : os;
            if (os == 'other') {
                return true;
            }
            var osName = os.toLowerCase();
            var osVersion = xxdApp.device.osVersion;
            var ios = 'ios';
            if (osName == ios && osVersion >= "8" && GC.isIosAnimate()) {
                return true;
            }
            return false;
        },
        loadPage: function (path) {
            if (path == undefined || path == '') {
                xxdApp.alert("跳转地址为空，请刷新重新尝试", "抱歉");
                return;
            }
            if (path.indexOf('?') > 0) {
                path += '&v=' + GC.getVersion();
            } else {
                path += '?v=' + GC.getVersion();
            }

            path = GC.getHtmlPath() + path;
            mainView.router.loadPage(path);
        },

        load: function (options) {
            var url = options.url;
            if (url == undefined || url == '') {
                xxdApp.alert("跳转地址为空，请刷新重新尝试", "抱歉");
                return;
            }
            if (url.indexOf('?') > 0) {
                url += '&v=' + GC.getVersion();
            } else {
                url += '?v=' + GC.getVersion();
            }

            url = GC.getHtmlPath() + url;
            mainView.router.load({
                url: url,
                context: options.context
            });
        },

        reloadPage: function (path) {
            if (path.indexOf('?') > 0) {
                path += '&v=' + GC.getVersion();
            } else {
                path += '?v=' + GC.getVersion();
            }

            path = GC.getHtmlPath() + path;
            mainView.router.reloadPage(path);
        },
        checkSysVersion: function () {
            var urlParamVer = req.getUrlParam("v");
            if (urlParamVer == null) {
                console.log("Can't find url param 'v'");
                globalService.getSysVersion({
                    callBack: function (data) {
                        window.location.href = "./?v=" + data.sv;
                    }
                });
            } else {
                globalService.getSysVersion({
                    callBack: function (data) {
                        var newVer = data.sv;
                        if (urlParamVer != newVer) {
                            window.location.href = "./?" + newVer;
                        }
                        $$("span[name='sysVer']").html("V" + newVer);
                    }
                });
            }
        },
        getSysVersion: function (param) {
            req.callGet({
                url: 'staticVersion.do',
                dataType: 'json',
                success: function (data) {
                    param.callBack(data);
                }
            });
        },
        loadHtml: function (options) {
            var isIndicator = 'indicator' in options && options.indicator ? true : false;
            req.callGet({
                url: GC.getHtmlPath() + options.url + "?v=" + GC.getVersion(),
                dataType: 'text',
                data: {},
                indicator: isIndicator,
                success: function (data) {
                    options.callBack(data);
                }
            });
        },
        isLogin: function () {
            var result = false;
            req.callGet({
                url: "user/isLogin.do?" + new Date().getTime(),
                dataType: 'json',
                async: false,
                data: {},
                indicator: true,
                success: function (data) {
                    if (data.isLogin != null) {
                        result = data.isLogin;
                    }
                }
            });
            return result;
        },
        //充值
        goTopup: function () {

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
                                GS.loadPage("account/topup.html?path=account");
                            }
                        });

                    } else {
                        if (data.realname != null) {
                            if(data.realname.status == 1) {
                                GS.loadPage("account/topup.html?path=account");
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
                    }
                }
            });
        }
    };
    return globalService;
});