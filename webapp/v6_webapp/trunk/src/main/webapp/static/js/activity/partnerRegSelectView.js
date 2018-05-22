define(function () {
    var partnerView = {
        noResult:function(){
            $$(".partnerRegSelect .partnerSelectList").html('<li><div class="item-inner row no-gutter" style="text-align: center;display: block;"><span style="padding-left:15px;">暂无记录</span></div></li>');
            // 加载完毕需要重置
            xxdApp.pullToRefreshDone();
        },
        showUserList: function (param) {
            if (param.userList == undefined || param.userList.length == 0) {
                return;
            }
            req.callGet({
                url: GC.getHtmlPath() + 'activity/partnerRegSelectItemUser.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    var compiledTemplate = t7.compile(result);
                    var output = compiledTemplate({list: param.userList});

                    if (param.type == 'push') {
                        $$(".partnerRegSelect .partnerSelectList").append(output);
                    } else {
                        $$(".partnerRegSelect .partnerSelectList").html(output);
                    }

                    param.callBack();

                    $$(".partnerRegSelect .partnerSelectList").show();
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            });
        },
        showStoreList: function (param) {
            if (param.storeDetailList == undefined || param.storeDetailList.length == 0) {
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '暂无记录'
                });
                // 加载完毕需要重置
                xxdApp.pullToRefreshDone();
                return;
            }
            req.callGet({
                url: GC.getHtmlPath() + 'activity/partnerRegSelectItemStore.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    var compiledTemplate = t7.compile(result);
                    var output = compiledTemplate({list: param.storeDetailList});

                    if (param.type == 'push') {
                        $$(".partnerRegSelect .partnerSelectList").append(output);
                    } else {
                        $$(".partnerRegSelect .partnerSelectList").html(output);
                    }
                    
                    $$(".partnerRegSelect .partnerSelectList").show();
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            });
        }
    };
    return partnerView;
});