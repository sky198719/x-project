/**
 * 借款标列表
 */
define(['js/borrow/borrowListView', 'js/borrow/borrowModel'], function (BorrowListView, Borrow) {
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var borrowCtrl = {
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
                    handler: borrowCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: borrowCtrl.infiniteScroll
                }
            ];

            BorrowListView.bindEvents({
                    bindings: bindings
                }
            );
            borrowCtrl.pullToRefresh();
        },
        showBorrowDetail: function (e) {
            var borrowId = $$(this).attr("data-id");
            GS.loadPage('borrow/borrowDetail.html?borrowId=' + borrowId + "&path=borrow");
        },

        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            borrowCtrl.selectBorrow(1, "pull");
            $$("#currentPage").val(1);
            // 重置加载flag
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
            var totalPage = $$("#totalPage").val();
            var currentPage = $$("#currentPage").val();
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if(currentPage>totalPage){
                //提示标的已经全部显示
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold:3000,
                    message: '数据已全部加载完毕'
                });
                loading = true;
                return;
            }
            borrowCtrl.selectBorrow(currentPage, "push");
            $$("#currentPage").val(currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function(){
                loading = false;
            },1500);
            //==========================切记此处不能删除============================
        },

        /**
         * 查询散标列表
         * @param currentPage  当前页
         * @param type 刷新类型
         */
        selectBorrow: function (currentPage, type) {
            req.callJSON({
                url:"borrow/search/list.do",
                data:{
                    currentPage: currentPage,
                    pageSize: pageSize
                },
                indicator:true,
                timeout:10000,
                success: function(data){
                    if (data.resultCode == 100 || data.resultCode == 200) {
                        //提示标的已经全部显示
                        xxdApp.addNotification({
                            title: '提示',
                            hold:3000,
                            message: '加载数据出错，请重新操作'
                        });
                        return;
                    }

                    if(data.borrowsList == null || data.borrowsList.borrows == undefined ){
                        //提示标的已经全部显示
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold:3000,
                            message: '抱歉，没有加载到数据，请重新操作'
                        });
                        return;
                    }

                    $$("#totalPage").val(data.borrowsList.totalPage);
                    if(data.borrowsList.currentPage>data.borrowsList.totalPage){
                        //提示标的已经全部显示
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold:3000,
                            message: '数据已全部加载完成'
                        });
                        return;
                    }

                    var list = data.borrowsList.borrows;
                    if (list.length == 0) {
                        //提示标的已经全部显示
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold:3000,
                            message: '抱歉，没有加载到数据，请重新操作'
                        });
                        return;
                    }

                    var borrowList = [];
                    for (var k = 0; k < list.length; k++) {
                        var b = list[k];
                        borrowList[k] = new Borrow({
                            "borrowId": b.BORROWID,
                            "name": b.NAME,
                            "apr": b.APR,
                            "timeLimit": b.TIMELIMIT,
                            "status": b.STATUS,
                            "surplusAccount": appFunc.fmoney(b.LACKACCOUNT,2),
                            "borrowPercent": b.BORROWPERCENT,
                            "account": appFunc.fmoney(b.ACCOUNT,2),
                            "isTender": b.isTender,
                            "accountyes": b.ACCOUNTYES,
                            "dayType": b.dayType
                        });
                    }
                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'borrow/borrowListItem.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                BorrowListView.showListItem({result:result,borrowList:borrowList,type:type});
                                borrowCtrl.tenderListBindEvent();
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

        tenderListBindEvent: function(){
            var bindings = [
                {
                     element: '.swipeout-content > .item-content.borrowList',
                     event: 'click',
                     handler: borrowCtrl.showBorrowDetail
                 },
                 {
                     element: '.swipeout-actions-right',
                     event: 'click',
                     handler: borrowCtrl.quickTender
                  }
            ];
            BorrowListView.bindEvents({
                   bindings: bindings
               }
            );
        },
        quickTender:function(){
            if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }
            //快捷投标校验
            var quickTender = false;
            req.callJSON({
                url: "borrow/quickTender.do",
                data: {},
                indicator: true,
                timeout: 10000,
                async: false,
                success: function (data) {
                    if(data.resultCode == -505){
                        xxdApp.alert(data.msg,"提示");
                        xxdApp.loginScreen();
                        quickTender = true;
                    }
                    if(data.quicktCode == -34){
                        xxdApp.alert(data.quicktmsg, '提示', function () {
                            GS.loadPage('personal/personalInfo.html');
                        });
                        quickTender = true;
                    }
                    if(data.paypwd == '0'){
                        xxdApp.alert('您还没有设置支付密码，请先设置支付密码', '提示', function () {
                            GS.loadPage('personal/personalInfo.html');
                        });
                        quickTender = true;
                    }
                }
            });
            if(quickTender){
                return;
            }
            var borrowId = $$(this).attr("data-id");
            req.callJSON({
                url: "borrow/tender/quick.do",
                data: {
                    borrowId:borrowId
                },
                indicator: true,
                timeout: 10000,
                success: function (data) {
                    if(data.resultCode == -505){
                        xxdApp.alert(data.msg,"提示");
                        xxdApp.loginScreen();
                        return false;
                    }
                    if(data.resultCode != 0) {
                        xxdApp.alert("获取借款标信息失败，请重新尝试","抱歉");
                        return false;
                    }

                    //账户剩余金额
                    var usable = data.account.USABLE;
                    if(usable == 0){
                        xxdApp.alert('您的账户可用余额为0，请先充值','提示');
                        return;
                    }
                    //标的剩余可投金额
                    var lackaccount = data.borrow.LACKACCOUNT;
                    var mostTender = data.borrow.MOSTTENDER;
                    //判断标的剩余可投
                    if(lackaccount == 0){
                        xxdApp.alert('这个标已经被抢完了哦，请试试别的标的吧','提示',function(){
                            window.location.reload();
                        });
                        return;
                    }
                    //实际投资金额
                    var realityTA = 0.00;
                    if(usable > lackaccount){
                        realityTA = lackaccount;
                    }else{
                        realityTA = usable;
                    }
                    //如果有最大投标金额限制 那实际投标金额即为最大投标金额
                    if(mostTender> 0){
                        if(realityTA > mostTender){
                            realityTA = mostTender;
                        }
                    }

                    //最终账户扣除金额
                    var borrow_affirm_money = realityTA;

                    var borrow_redpacket_redCode = '';
                    var borrow_redpacket_faceValue = 0.00;

                    var borrow_redpacket_msg = '';

                    var redpacketList = '';
                    if (data.redpacketList != null) {
                        redpacketList = data.redpacketList;
                        for (var i = 0; i < redpacketList.length; i++) {
                            var b = redpacketList[redpacketList.length - i - 1];
                            //总额+红包金额<最小限额
                            if (parseFloat(realityTA) < parseFloat(b.quota)) {
                                continue;
                            }
                            borrow_redpacket_redCode = b.redCode;
                            borrow_redpacket_faceValue = b.faceValue;
                            borrow_affirm_money = parseFloat(realityTA) - parseFloat(b.faceValue);
                            borrow_redpacket_msg = '<h5 class="text-left font-bold">红包抵扣：<span class="font18 font-red">' + appFunc.fmoney(borrow_redpacket_faceValue,2) + '</span>元</h5>';
                            break;
                        }
                    }

                    //判断账户余额是否小于最小投资金额
                    if(parseFloat(usable) < parseFloat(borrow_affirm_money)){
                        xxdApp.alert('账户余额不足，请先充值','提示');
                        return;
                    }

                    var TIMELIMIT = '';
                    if (data.borrow.PAYMENTMETHOD == 6) {
                        TIMELIMIT += ' 个季度';
                    } else {
                        TIMELIMIT += ' 个月';
                    }
                    //设置表单token
                    var formToken = appFunc.setToken({name:"BORROW_QUICK_TENDER", id: borrowId});
                    if(formToken.code != 0) {
                        xxdApp.alert("初始化表单失败，请返回重新进入","抱歉");
                        return;
                    }
                    xxdApp.modal({
                        title:  '<h4>确认投标</h4><h5 class="text-left font-grey">借款标题：'+
                                data.borrow.NAME +'</h5><h5 class="text-left font-grey">标的期限：'+
                                data.borrow.TIMELIMIT + TIMELIMIT +'</h5><h5 class="text-left font-grey">年化收益：<span class="font18 font-red">'+
                                appFunc.fmoney(data.borrow.APR,2) +' </span>%</h5><h5 class="text-left font-bold">投标金额：<span class="font18 font-red">'+
                                appFunc.fmoney(realityTA,2) +' </span>元</h5>' + borrow_redpacket_msg + '<h5 class="text-left font-bold">实际支付：<span class="font18 font-red">'+
                                appFunc.fmoney(borrow_affirm_money,2) +' </span>元</h5>',
                        text: '<h6><a href="#" id="tenderAgreement" class="mt5">我同意《资金出借风险提示函》<br>并确认投标</a></h6>'+
                            '<h5>'+
                            '<input type="password" placeholder="请输入支付密码" class="modal_input express_modalinput mt10">'+
                            '</h5>',
                        buttons: [
                            {
                                text: '取消',
                                onClick: function() {
                                    return;
                                }
                            },
                            {
                                text: '确认投标',
                                onClick: function() {
                                    if (!appFunc.isLogin()) {
                                        xxdApp.loginScreen();
                                        return;
                                    }
                                    var password = $$('.modal_input').val();
                                    if(password == null || password == ''){
                                        xxdApp.alert('请输入支付密码！');
                                        return;
                                    }
                                    xxdApp.showIndicator('正在努力投标...');
                                    req.callPost({
                                        url: "borrow/tender/" + borrowId + ".do",
                                        data: {
                                            payPwd: $.md5($.md5(password)),
                                            tendermoney: borrow_affirm_money,
                                            couponCode: borrow_redpacket_redCode,
                                            tokenName: formToken.data.tokenName,
                                            token: formToken.data.token
                                        },
                                        dataType: 'json',
                                        timeout: 150000,
                                        success: function (data) {
                                            xxdApp.hideIndicator();
                                            if (data.resultCode == -404) {
                                                xxdApp.alert('您还没有登录或长时间未操作，请先登录', '登录提示', function () {
                                                    xxdApp.loginScreen();
                                                });
                                                return;
                                            }
                                            if (data.resultCode != 0) {
                                                if(data.resultCode != 301) {
                                                    xxdApp.alert(data.desc, '提示', function () {
                                                        if (data.resultCode == -34) {
                                                            GS.loadPage('personal/personalInfo.html');
                                                        }
                                                    });
                                                }
                                            } else {
                                                xxdApp.alert('投标成功！', '恭喜',function(){
                                                    window.location.reload();
                                                });
                                            }
                                        },
                                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                                            xxdApp.hidePreloader();
                                            xxdApp.alert('系统无响应，请查看投标记录是否投标成功或者重新投标', '抱歉',function(){
                                                window.location.reload();
                                            });
                                        }
                                    });
                                }
                            }
                        ]
                    });
                    borrowCtrl.open_popup();
                }
            });
        },
        open_popup: function(){
            var bindings = [
                {
                    element: '#tenderAgreement',
                    event: 'click',
                    handler: borrowCtrl.tenderAgreement
                }
            ];
            BorrowListView.bindEvents({
                    bindings: bindings
                }
            );
        },
        tenderAgreement:function(){
            req.callGet({
                url: GC.getHtmlPath() + 'borrowTender/borrowTenderAgreement.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    $$(".popup-bidhistory").html(result);
                }
            });

            xxdApp.popup('.popup-bidhistory');
        }
    };

    return borrowCtrl;
});