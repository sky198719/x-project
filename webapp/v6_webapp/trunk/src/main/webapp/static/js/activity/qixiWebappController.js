define(['js/utils/date', 'js/common/menu'], function (DateHandle, menu) {
    var beginDate = DateHandle.parseDate("2016-08-03 00:00:00");
    var endDate = DateHandle.parseDate("2016-08-09 23:00:00");

    var qixiWebappCtrl = {
        init: function () {
            qixiWebappCtrl.eventBind();

            var result = qixiWebappCtrl.getUserActivityInfo();
            try {
                var status = qixiWebappCtrl.isActivity();
                switch (status) {
                    case 0:
                        $("#qixirankId").append("<li style='text-align: center;'>活动尚未开始～</li>");
                        break;
                    case 1:

                        $$(".qixiWebapp #yulu").attr("src", "static/img/activity/qixi/btn2_2.png");
                        $$(".qixiWebapp #jinfen").attr("src", "static/img/activity/qixi/btn3_2.png");

                        var list = JSON.parse(result.data).data.xybTenderList.resultList;
                        var html = '';
                        var num = 1;
                            console.log(list.length);
                        for (var i in list) {
                            /*if (num == 8) {
                                break;
                            }  */
                            var obj = list[i];
                            html += '<li>';
                            html += '<span>' + obj.NICKNAME + '</span>';
                            html += '<span>' + appFunc.fmoney(obj.ACCOUNT, 2) + '</span>';
                            html += '<span>_-' + obj.CLOSETERM + '个月</span>';
                            html += '<span>' + obj.ADDTIME + '</span>';
                            html += '</li>';
                            num++;
                        }
                        $("#qixirankId").append(html);
                        break;
                    default:
                        $("#qixirankId").append("<li style='text-align: center;'>活动已结束～</li>");
                }
            } catch (e) {
            }

            try {

                var resultData = JSON.parse(result.data).data;
                if (resultData.userTenderInfo != undefined && resultData.userTenderInfo.CHOICE == 1) {
                    $$(".qixiWebapp #jinfen").attr("src", "static/img/activity/qixi/btn3_3.png");
                    $$(".qixiWebapp #yulu").attr("src", "static/img/activity/qixi/btn2_1.png");
                } else if (resultData.userTenderInfo != undefined && resultData.userTenderInfo.CHOICE == 2) {
                    $$(".qixiWebapp #jinfen").attr("src", "static/img/activity/qixi/btn3_1.png");
                    $$(".qixiWebapp #yulu").attr("src", "static/img/activity/qixi/btn2_3.png");
                }
            } catch (e) {
            }
        },
        getUserActivityInfo: function () {
            var reuslt;
            req.callGet({
                url: 'valentineFestival/getUserActivityInfo.do',
                dataType: 'json',
                async: false,
                success: function (data) {
                    reuslt = data;
                }
            });
            return reuslt;
        },
        eventBind: function () {
            var bind = [
                {
                    element: '.qixiWebapp #tender',
                    event: 'click',
                    handler: qixiWebappCtrl.tender
                },
                {
                    element: '.qixialertBox .close',
                    event: 'click',
                    handler: qixiWebappCtrl.close
                },
                {
                    element: '.qixiWebapp .toDeail',
                    event: 'click',
                    handler: qixiWebappCtrl.getLatestPlanByCloseTerm
                },
                {
                    element: '.qixiWebapp .vote',
                    event: 'click',
                    handler: qixiWebappCtrl.vote
                }
            ];
            appFunc.bindEvents(bind);
        },
        vote: function () {
            var status = qixiWebappCtrl.isActivity();
            switch (status) {
                case 0:
                    xxdApp.modal({
                        title: '提示',
                        text: '活动尚未开始～',
                        verticalButtons: true,
                        buttons: [
                            {
                                text: '确定',
                                onClick: function () {
                                    xxdApp.closeModal();
                                }
                            }
                        ]
                    });
                    break;
                case 1:
                    if (appFunc.isLogin()) {
                        var type = $$(this).data("type");
                        var result = qixiWebappCtrl.getUserActivityInfo();
                        result = JSON.parse(result.data).data;
                        if (result.userTenderInfo == undefined || result.userTenderInfo.ACCOUNT == 0) {
                            xxdApp.modal({
                                title: '提示',
                                text: '您还没有投资_，请投资_后参加活动',
                                verticalButtons: true,
                                buttons: [
                                    {
                                        text: '立即投资',
                                        onClick: function () {
                                            xxdApp.closeModal();
                                        }
                                    }
                                ]
                            });
                        } else if (result.userTenderInfo != undefined && result.userTenderInfo.CHOICE != 0) {

                        } else {
                            if (type == 1) {
                                xxdApp.modal({
                                    title: '提示',
                                    text: '您确定选择“玉露”队进行投资吗，一旦选定无法更改',
                                    verticalButtons: true,
                                    buttons: [
                                        {
                                            text: '确定',
                                            onClick: function () {
                                                qixiWebappCtrl.chooseTeam(2);
                                            }
                                        },
                                        {
                                            text: '我再看看',
                                            onClick: function () {
                                                xxdApp.closeModal();
                                            }
                                        }
                                    ]
                                });
                            } else {
                                xxdApp.modal({
                                    title: '提示',
                                    text: '您确定选择“金风”队进行投资吗，一旦选定无法更改',
                                    verticalButtons: true,
                                    buttons: [
                                        {
                                            text: '确定',
                                            onClick: function () {
                                                qixiWebappCtrl.chooseTeam(1);
                                            }
                                        },
                                        {
                                            text: '我再看看',
                                            onClick: function () {
                                                xxdApp.closeModal();
                                            }
                                        }
                                    ]
                                });
                            }
                        }
                    } else {
                        xxdApp.modal({
                            title: '提示',
                            text: '您还没有登录，请登录后参加活动',
                            verticalButtons: true,
                            buttons: [
                                {
                                    text: '立刻登录',
                                    onClick: function () {
                                        xxdApp.closeModal();
                                        menu.toLogin();
                                    }
                                }
                            ]
                        });
                    }
                    break;
                default:
                    xxdApp.modal({
                        title: '提示',
                        text: '活动已结束～',
                        verticalButtons: true,
                        buttons: [
                            {
                                text: '确定',
                                onClick: function () {
                                    xxdApp.closeModal();
                                }
                            }
                        ]
                    });
            }
        },

        chooseTeam: function (choiceId) {
            req.callPost({
                url: 'valentineFestival/chooseTeam.do',
                data: {
                    choiceId: choiceId
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    if (data.resultCode == 0) {
                        xxdApp.modal({
                            title: '提示',
                            text: '恭喜您已成功参与七夕搭桥活动，感谢您的参与～',
                            verticalButtons: true,
                            buttons: [
                                {
                                    text: '确定',
                                    onClick: function () {
                                        xxdApp.closeModal();
                                    }
                                }
                            ]
                        });

                        if (choiceId == 1) {
                            $$(".qixiWebapp #jinfen").attr("src", "static/img/activity/qixi/btn3_3.png");
                        } else if (choiceId == 2) {
                            $$(".qixiWebapp #yulu").attr("src", "static/img/activity/qixi/btn2_3.png");
                        }
                    } else if (data.resultCode == -1) {

                        xxdApp.modal({
                            title: '提示',
                            text: data.desc,
                            verticalButtons: true,
                            buttons: [
                                {
                                    text: '确定',
                                    onClick: function () {
                                        xxdApp.closeModal();
                                    }
                                }
                            ]
                        });
                    } else if (data.resultCode == -3) {
                        //未登录
                        menu.toLogin();
                    }
                }
            });
        },

        getLatestPlanByCloseTerm: function () {
            var status = qixiWebappCtrl.isActivity();
            switch (status) {
                case 0:
                    xxdApp.modal({
                        title: '提示',
                        text: '活动尚未开始～',
                        verticalButtons: true,
                        buttons: [
                            {
                                text: '确定',
                                onClick: function () {
                                    xxdApp.closeModal();
                                }
                            }
                        ]
                    });
                    break;
                case 1:

                    if (!appFunc.isLogin()) {
                        xxdApp.modal({
                            title: '提示',
                            text: '您还没有登录，请登录后参加活动',
                            verticalButtons: true,
                            buttons: [
                                {
                                    text: '立刻登录',
                                    onClick: function () {
                                        xxdApp.closeModal();
                                        menu.toLogin();
                                    }
                                }
                            ]
                        });
                    }

                    var closeTerm = $$(this).data("closeTerm");
                    if (closeTerm == undefined || closeTerm == null || closeTerm == "") {
                        xxdApp.alert("系统异常，请稍后重试", '抱歉');
                        return;
                    }

                    req.callJSON({
                        url: "xplan/getLatestSchemeId.do",
                        data: {
                            currentPage: 1,
                            closeTerm: closeTerm,
                            pageSize: 10
                        },
                        dataType: 'json',
                        indicator: true,
                        success: function (data) {
                            if (data != null && data.schemeInfo != null) {
                                var planId = data.schemeInfo.SCHEMEID;
                                GS.loadPage('plan/planDetailsV2_act.html?previousPage=qixiWebapp&planId=' + planId);
                            }
                        }
                    });
                    break;
                default:
                    xxdApp.modal({
                        title: '提示',
                        text: '活动已结束～',
                        verticalButtons: true,
                        buttons: [
                            {
                                text: '确定',
                                onClick: function () {
                                    xxdApp.closeModal();
                                }
                            }
                        ]
                    });
            }

        },
        tender: function () {
            var status = qixiWebappCtrl.isActivity();
            switch (status) {
                case 0:
                    xxdApp.modal({
                        title: '提示',
                        text: '活动尚未开始～',
                        verticalButtons: true,
                        buttons: [
                            {
                                text: '确定',
                                onClick: function () {
                                    xxdApp.closeModal();
                                }
                            }
                        ]
                    });

                    break;
                case 1:
                    if (appFunc.isLogin()) {

                    } else {
                        xxdApp.modal({
                            title: '提示',
                            text: '您还没有登录，请登录后参加活动',
                            verticalButtons: true,
                            buttons: [
                                {
                                    text: '立刻登录',
                                    onClick: function () {
                                        xxdApp.closeModal();
                                        menu.toLogin();
                                    }
                                }
                            ]
                        });


                    }
                    break;
                default:
                    xxdApp.modal({
                        title: '提示',
                        text: '活动已结束～',
                        verticalButtons: true,
                        buttons: [
                            {
                                text: '确定',
                                onClick: function () {
                                    xxdApp.closeModal();
                                }
                            }
                        ]
                    });
            }
        },


        isActivity: function () {
            var result = qixiWebappCtrl.getUserActivityInfo();
            result = JSON.parse(result.data).data;
            if (result.activityPhase == 0) {
                return 1
            } else if (-1 == result.activityPhase) {
                return 0;
            } else {
                return 2;
            }
            /* var currentDate = DateHandle.parseDate(appFunc.getCurrentDate());
             if (beginDate > currentDate) {
             return 0;
             } else if (beginDate <= currentDate && currentDate <= endDate) {
             return 1;
             } else {
             return 2;
             }   */
        }
    };
    return qixiWebappCtrl;
});
