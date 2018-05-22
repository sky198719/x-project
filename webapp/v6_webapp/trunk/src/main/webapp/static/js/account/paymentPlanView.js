/**
 * Created by pufei on 2015/3/6.
 */
define( function () {
    var paymentPlanView = {
        init: function (params) {
        },
        bindEvents:function(param) {
            appFunc.bindEvents(param.bindings);
        },
        showListItem:function(param){
            if(param.creditList.length != 0){
                var compiledTemplate = t7.compile(param.result);
                var output = compiledTemplate({list: param.creditList});
                if (param.type == 'push') {
                    $$("#plan-tender").append(output);
                } else {
                    $$("#plan-tender").html(output);
                }
                $$("#plan-collSum").html(appFunc.fmoney(param.collSum,2));
            }else{
                if(param.type == 'push') {
                    //提示标的已经全部显示
                    xxdApp.addNotification({
                        title: '温馨提示',
                        hold:3000,
                        message: '已经到底了'
                    });
                } else {
                    var html='<h6 class="font-grey text-center pd10">' +
                        '暂无记录' +
                        '</h6>'
                    $$("#plan-tender").html(html);
                }

            }
        }
    };
    return paymentPlanView;
});

