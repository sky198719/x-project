/**
 * 撤销债权转让申请
 * Created by zhangyi on 2015/10/13.
 */
define(['js/trade/tradeRequestRollbackView'], function (tradeRequestRollbackView) {
    var tenderId, requestId;
    var tradeRequestRollbackCtrl = {
        init: function (event) {
            var query = appFunc.getEventDetailPageQuery(event);
            tenderId = query.tenderId;
            requestId = query.requestId;
            tradeRequestRollbackCtrl.loadData();
            tradeRequestRollbackCtrl.eventBind();
        },
        eventBind: function () {
            var bind = [
                {
                    element: '#trrb_submitButton',
                    event: 'click',
                    handler: tradeRequestRollbackCtrl.tradeRequestRollback
                }
            ];
            appFunc.bindEvents(bind);
        },
        loadData: function () {
            req.callJSON({
                url: "traderequest/checkTradeRequest.do",
                data: {
                    requestId: requestId,
                    tenderId: tenderId,
                    type: "2"
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    if (data.resultCode >= 0) {
                        tradeRequestRollbackView.showTradeRequestRollback(data);
                    } else {
                        if (data.resultCode == -99) {
                            //session超时，跳转登陆界面
                            xxdApp.loginScreen();
                            return;
                        }

                        xxdApp.alert(data.msg, '抱歉', function () {
                            GS.loadPage('account/tradeTransfering.html?path=account');
                        });
                    }
                }
            });
        },
        tradeRequestRollback: function () {
            //设置表单token
            var formToken = appFunc.setToken({name:"TRADE_REQUEST_ROLLBACK", id: requestId});
            if(formToken.code != 0) {
                xxdApp.alert("初始化表单失败，请返回重新进入","抱歉");
                return;
            }
            xxdApp.confirm('确定撤销转让吗？', '撤销转让',
                    function () {
                        xxdApp.showIndicator('正在撤销，请稍后');
                        req.callPost({
                            url: 'traderequest/rollbackRadeRequest.do',
                            data: {
                                requestId: requestId,
                                tokenName: formToken.data.tokenName,
                                token: formToken.data.token
                            },
                            dataType: 'json',
                            success: function (data) {
                                xxdApp.hideIndicator();
                                if (data.resultCode == -99) {
                                    xxdApp.loginScreen();
                                } else if (data.resultCode < 0) {
                                    xxdApp.alert(data.msg, "抱歉");
                                } else if (data.resultCode == 0){
                                    GS.loadPage('account/tradeTransfering.html?path=account');
                                }
                            }
                        });
                    },
                    function () {
                    }
            );
        }
    };
    return tradeRequestRollbackCtrl;
});

