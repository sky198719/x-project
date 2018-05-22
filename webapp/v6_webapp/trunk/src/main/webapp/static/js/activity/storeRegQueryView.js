define(function () {
    var storeView = {
        noStoreUser:function(){
            $$(".storeRegQuery .storeUserList").html('<li><div class="item-inner row no-gutter"><span style="padding-left:15px;">暂无记录</span></div></li>');
        },
        showStoreUser: function (param) {
            if (param.userList == undefined || param.userList.length == 0) {
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
                url: GC.getHtmlPath() + 'activity/storeRegQueryUserItem.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    var compiledTemplate = t7.compile(result);
                    var output = compiledTemplate({list: param.userList});

                    if (param.type == 'push') {
                        $$(".storeRegQuery .storeUserList").append(output);
                    } else {
                        $$(".storeRegQuery .storeUserList").html(output);
                    }

                    param.callBack();

                    $$(".storeRegQuery .storeUserList").show();
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            });
        }
    };
    return storeView;
});