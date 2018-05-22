/**
 * Created by chaihuangqi on 2016/4/28.
 */
define([], function () {
    var fdController = {
        init: function () {
            fdController.getFeedBackInfo();
            fdController.eventBind();
        },
        eventBind: function () {
            var bindings = [
                {
                    element: 'a[name="feedback"]',
                    event: 'click',
                    handler: fdController.submit
                }
            ];
            appFunc.bindEvents(bindings);

        },

        getFeedBackInfo: function () {
            req.callPost({
                url: 'account/getFeedBackInfo.do',
                data: {},
                dataType: 'json',
                async: false,
                indicator: true,
                success: function (result) {
                    if (result != null && result.degrees != null) {
                        var list = result.degrees;

                        var fbList = [];
                        for (var k = 0; k < list.length; k++) {
                            var b = list[k];
                            if (b.status == 1) {
                                fbList.push({
                                    "id": b.id,
                                    "name": b.name
                                });
                            }
                        }
                        try {
                            req.callGet({
                                url: GC.getHtmlPath() + 'account/feedbackList.html?' + GC.getVersion(),
                                dataType: 'text',
                                success: function (result) {
                                    fdController.showListItem({result: result, fbList: fbList});
                                    // 加载完毕需要重置
                                    xxdApp.pullToRefreshDone();
                                }
                            });
                        } catch (e) {
                            xxdApp.hideIndicator();
                        }
                    }

                }
            });

        },
        showListItem: function (param) {
            var compiledTemplate = t7.compile(param.result);
            var output = compiledTemplate({list: param.fbList});
            $$("#feedbackList").html(output);
            $$(".list-block").show();
        },
        submit: function () {
            var s = document.getElementsByName("feedbackItem");
            var s2 = new Array();
            for (var i = 0; i < s.length; i++) {
                if (s[i].checked) {
                    s2.push(s[i].value);
                }
            }
            if (s2.length == 0) {
                xxdApp.alert("请至少选择一项", "提示");
                return;
            }
            var advice = $$("#fb_advise").val();
            if (s2.join(',').indexOf("-1") >= 0) {
                if (advice == null || advice == "") {
                    xxdApp.alert("意见反馈不能为空", "提示");
                    return;
                }
                if (advice.length < 3) {
                    xxdApp.alert("意见反馈不能少于三个字", "提示");
                    return;
                }
            }
            req.callPost({
                url: 'account/addFeedback.do',
                data: {
                    content: advice,
                    items: s2.join(",")
                },
                dataType: 'json',
                async: false,
                indicator: true,
                success: function (result) {
                    if (result.resultCode == 1) {
                        fdController.clearData();
                        GS.loadPage("account/feedbackReturn.html");
                    } else {
                        xxdApp.alert(result.msg, "提示");
                    }
                }
            });
        },
        clearData: function () {
            var s = document.getElementsByName("feedbackItem");
            for (var i = 0; i < s.length; i++) {
                if (s[i].checked) {
                    s[i].checked = false;
                }
            }
            $$("#fb_advise").val('');
        }
    };
    return fdController;
});
