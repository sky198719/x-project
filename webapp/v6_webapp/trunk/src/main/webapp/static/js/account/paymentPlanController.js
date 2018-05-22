/**
 * 回款记录
 * Created by pufei on 2015/3/6.
 */
define(['js/account/paymentPlanView','js/account/paymentPlanModel','js/utils/date'], function (paymentPlanView,paymentPlanModel,DateHandle) {
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var paymentPlanCtrl = {
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
                    handler: paymentPlanCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: paymentPlanCtrl.infiniteScroll
                }
            ];
            paymentPlanView.bindEvents({
                    bindings: bindings
                }
            );
            paymentPlanCtrl.pullToRefresh();
        },

        /**
         * 下拉刷新页面
         */
        pullToRefresh:function(){
            paymentPlanCtrl.selectTender(1, "pull");
            $$("#Plan-currentPage").val(1);
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
            var totalPage = $$("#Plan-totalPage").val()
            var currentPage = $$("#Plan-currentPage").val();
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if(totalPage == 0){
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
            paymentPlanCtrl.selectTender(currentPage, "push");
            $$("#Plan-currentPage").val(currentPage);
            // 重置加载flag
            //==========================切记此处不能删除============================
            setTimeout(function(){
                loading = false;
            },1500);
            //==========================切记此处不能删除============================
        },
        /**
         * 投资记录
         * @param currentPage  当前页
         * @param type 刷新类型
         */
        selectTender:function(currentPage, type){

            req.callJSON({
                url:'product/collectingInterestPlan.do',
                data:{
                    type:2,
                    productType:0,
                    currentPage:currentPage,
                    pageSize:30
                },
                indicator: true,
                success:function(result){
                	if (result.code != 200000) {
                        xxdApp.addNotification({ title: '抱歉', message: result.message, hold: 3000 });
                        return;
                    }
                    var list = result.data.items;
                    $$("#Plan-totalPage").val(result.data.totalCount);

                    var creditList = [];
                    if(list != null && list != "" && list.length > 0){
                        for(var i = 0 ; i < list.length ; i++){
                            var b = list[i];
                            var statusDouble = false;
                            /*if(b.STATUS == 0){
                            	statusDouble = false;
                            }
                            var isYYP = false;
                            if(b.name.indexOf("月月派") >= 0){
                            	isYYP = true;
                            }*/
                            var periodName = b.periodName;
                            if(periodName != undefined && periodName != ""){
                                periodName = " - "+periodName;
                            }

                            var periodNo = b.periodNo;
                            if(periodNo != undefined && periodNo != ''){
                                periodName = " - "+periodNo;
                            }
                            var repayAccount = (b.incomeCapital == undefined ? 0 : parseFloat(b.incomeCapital)) + (b.incomeInterest == undefined ? 0 : parseFloat(b.incomeInterest));
                            creditList[i] = new paymentPlanModel({
                                'tenderTitle': b.name,
                                'repayAccount': appFunc.fmoney(repayAccount,2),
                                'pname': periodName,
                                'repayTime': DateHandle.formatDate('yyyy-MM-dd',new Date(b.incomeDate)),
                                'status': '未还款',
                                'statusDouble': statusDouble
                            })
                        }
                    }

                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'account/planList.html?'+ GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                paymentPlanView.showListItem({result:result,creditList:creditList,type:type});
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
        },
        //0、未归还,1、已归还,2、	网站垫付,3、	垫付后还款,4、提现还款
        setCollectionStatus:function (index) {
            var result = "";
            switch (index) {
                case 0:
                    result = "未还款";
                    break;
                case 1:
                    result = "已归还";
                    break;
                case 2:
                    result = "已归还";
                    break;
                case 3:
                    result = "已归还";
                    break;
                case 4:
                    result = "提前还款";
                    break;
                case 5:
                    result = "待签约";
                    break;
                case -1:
                    result = "签约失败";
                    break;
                default:
                    result = "未知状态";
            }
            return result;
        },
        timeyyyyMMddHHmmss2:function (value, stat) {
            if (stat != 4)
                return '-';
            else{
                var values= value.substr(0,10);
                return values;
            }
        }
    }
    return paymentPlanCtrl;
})