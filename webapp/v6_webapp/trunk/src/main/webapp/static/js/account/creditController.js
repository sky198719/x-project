/**
 * 交易明细
 * Created by pufei on 2015/3/6.
 */
define(['js/account/creditView','js/account/creditModel','js/utils/date'], function (creditView,creditModel,DateHandle) {
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var creditCtrl = {
        init: function () {

            /**
             * 事件定义
             *  pullToRefresh 下拉刷新
             *  infiniteScroll 无限刷新
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: creditCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: creditCtrl.infiniteScroll
                }
            ];
            creditView.bindEvents({
                    bindings: bindings
                }
            );
            creditCtrl.pullToRefresh();
            creditCtrl.beseInfo();
        },

        /**
         * 下拉刷新页面
         */
        pullToRefresh:function(){
            creditCtrl.selectTender(1, "pull");
            $$("#tcurrentPage").val(1);
            loading = false;
        },
        /**
         * 无限滚动
         */
        infiniteScroll: function () {
            // 如果正在加载，则退出
            if (loading) {
                return;
            }
            //==========================切记此处不能删除============================
            loading = true;
            //==========================切记此处不能删除============================
            var totalPage = $$("#ttotalPage").val()
            var currentPage = $$("#tcurrentPage").val();
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if(currentPage>totalPage){
                //提示标的已经全部显示
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold:3000,
                    message: '已经到底了'
                });
                // 设置flag
                loading = true;
                return;
            }
            creditCtrl.selectTender(currentPage, "push");
            $$("#tcurrentPage").val(currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function(){
                loading = false;
            },1500);
            //==========================切记此处不能删除============================
        },

        beseInfo:function(){
            req.callJSON({
                url:"account/moneyRecord.do",
                data:{
                    currentPage: 1,
                    pageSize: 1
                },
                success:function(data){
                    $$("#cUserMoney").html(appFunc.fmoney(data.usemoney,2));
                    $$("#cFrozen").html(appFunc.fmoney(data.frozen,2));
                    $$("#cXxb").html(appFunc.fmoney(data.xxb,2));
                }
            });
        },
        /**
         * 投资记录
         * @param currentPage  当前页
         * @param type 刷新类型
         */
        selectTender:function(currentPage, type){

            req.callJSON({
                url:"product/tradingRecord.do",
                data:{
                    type:0,
                    currentPage: currentPage,
                    pageSize: pageSize
                },
                dataType:'json',
                indicator: true,
                success:function(result){
                    var list = [];
                    if(result.code == 200000) {
                        if(result.data.items != null){
                            list = result.data.items;
                        }

                        var totalTender = result.data.totalCount;
                        var totalPage = Math.floor(totalTender / pageSize);
                        totalPage += (totalTender % pageSize) > 0 ? 1 :0;
                        $$("#ttotalPage").val(totalPage);
                    }

                    var creditList = [];
                    if(list!= null && list!=""){
                        for(var i = 0 ; i < list.length ; i++){
                            var b = list[i];
                            var moneyType ="";
                            var moneyTypeDouble = false;
                            if(b.status == 1 || b.status == 3){
                                moneyType ="-";
                            }else if(b.status == 2){
                                moneyTypeDouble = true;
                                moneyType ="+";
                            }
                            creditList[i] = new creditModel({
                                'addTime': DateHandle.formatDate("yyyy-MM-dd HH:mm:ss",new Date(b.tradingDate)),
                                'operatorType': b.operateType,
                                'workMoney': appFunc.fmoney(b.tradingAmount,2),
                                'moneyType': moneyType,
                                'moneyTypeDouble': moneyTypeDouble,
                                'remark': b.remark
                            })
                        }
                    }

                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'account/creditList.html?'+ GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                creditView.showListItem({result:result,creditList:creditList,type:type});
                                // 加载完毕需要重置
                                xxdApp.pullToRefreshDone();
                            }
                        });
                    } catch (e) {
                        xxdApp.hideIndicator();
                    }
                }
            });

            setTimeout(function(){
                if (type == 'pull') {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            },5000);
        }
    }
    return creditCtrl;
})