/**
 * 借款标详情
 */
define([ 'js/borrow/borrowDetailView'], function ( BorrowDetailView) {

    var borrowDetailCtrl = {
        init: function (event) {
            var query = appFunc.getEventDetailPageQuery(event);

            var bottomHide = query.bottomHide;
            if(bottomHide != undefined && bottomHide == 'true') {
                $$("#goTender").parent().hide();
            }

            $$("#goTender").hide();
            var borrowId = query.borrowId;
            req.callJSON({
                url: "borrow/detail/" + borrowId + ".do",
                data:{},
                indicator:true,
                timeout:10000,
                success:function(data){
                    $$("#goTender").show();
                    if (data.resultCode != 0) {
                        //提示标的已经全部显示
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold: 3000,
                            message: data.msg
                        });

                        return false;
                    }

                    //var userType = data.baseInfo.userType;
                    var companyId = data.borrow1.companyId;
                    var isCompany = false;
                    if(companyId != undefined && "" != companyId) {
                        isCompany = true;
                    }
                    var account = data.borrow.ACCOUNT;
                    //个人
                    if(!isCompany && account <= 200000) {
                         $$("#quotaMange").show();
                         $$("#quotaMange_con").html("该自然人在本平台借款余额未超过人民币20万元，在不同网络借贷信息中介机构平台借款总余额未超过人民币100万元。");
                    } else if(isCompany && account <= 1000000){
                        $$("#quotaMange").show();
                        $$("#quotaMange_con").html("该法人或其他组织在本平台借款余额未超过人民币100万元，在不同网络借贷信息中介机构平台借款总余额未超过人民币500万元。");
                    }

                    //投标按钮
                    BorrowDetailView.setTenderButton(data);
                    //借款标信息
                    BorrowDetailView.borrowBaseInfo(data);
                    //if 自然人 borrowerInfo else 企业 companyInfo
                    if(isCompany) {
                        BorrowDetailView.companyInfos(data);
                    } else {
                        //借款人信息
                        BorrowDetailView.borrowerInfo(data);
                    }

                    //贷款记录
                    BorrowDetailView.loanInfo(data);
                    //费用信息
                    if(data.feeInfo != null) {
                        BorrowDetailView.feeInfo(data.feeInfo);
                    }

                    /**
                     * 事件定义
                     * @type {*[]}
                     */
                    var bindings = [
                        {
                            element: '#checkTenderRecord',
                            event: 'click',
                            handler: borrowDetailCtrl.checkTenderRecord
                        },
                        {
                            element: '#safeGuard',
                            event: 'click',
                            handler: borrowDetailCtrl.safeGuard
                        },
                        {
                            element: '#riskTip',
                            event: 'click',
                            handler: borrowDetailCtrl.riskTip
                        },
                        {
                            element: '#contractModel',
                            event: 'click',
                            handler: borrowDetailCtrl.contractModel
                        },
                        {
                            element: '#goTender',
                            event: 'click',
                            handler: borrowDetailCtrl.goTender,
                           // dataContext: JSON.stringify(resultJson),
                            attrData: {
                                name: "borrowId",
                                value: borrowId
                            }
                        }
                    ];

                    BorrowDetailView.bindEvent({
                            bindings: bindings
                        }
                    );
                }
            });
        },

        /**
         * 跳转投标页面
         */
        goTender: function (e) {
        	 var borrowId = $$(this).attr("borrowId");
        	 var productName = $$("#borrowType").val();
        	 var borroyTimeLimit = $$("#borroyTimeLimit").val();
        	 var borroyRate =  $$("#borroyRate").val();
        	 
        	try {
        		var name = "散标直投"+borrowId;
        		var category = "散标直投/"+borroyRate+"%/"+borroyTimeLimit+"个月/"+productName;
        		//XXD_TRACK._trackEvent({category: "webapp_borrow_in", action: "now_invest", label: "马上投标", value: "", custval: "" });
        		add_to_cart({id:borrowId,name:name,category:category});
        	} catch (e) {}
            if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }
            //var dataContext = JSON.parse($$(this).attr("data-context"));
            var borrowId = $$(this).attr("borrowId");
            GS.loadPage('borrowTender/borrowTender.html?borrowId=' + borrowId);
        },

        /**
         * 查看投标记录详情调用
         */
        checkTenderRecord: function () {
            $$(".popup-bidhistory").html("");
            var borrowId = $$("#borrowId").val();

            req.callJSON({
                url: "borrow/borrowTenderList.do",
                data:{
                    borrowId: borrowId,
                    currentPage: 1
                },
                preloaderTitle:'正在加载数据...',
                success:function(data){
                    if (data.resultCode != 0) {
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold: 3000,
                            message: data.msg
                        });
                        return;
                    }

                    var list = data.resultData;
                    BorrowDetailView.borrowTenderList(list);
                }
            });
        },
        safeGuard: function () {
            var borrowId = $$("#borrowId").val();
            GS.loadPage('borrow/safeGuard.html?borrowId='+borrowId);
        },
        riskTip : function () {
            GS.loadPage('borrow/riskTip.html');
        },
        contractModel : function () {
            var borrowId = $$("#borrowId").val();
            GS.loadPage('borrow/contractModel.html?borrowId='+borrowId+"&source=1");
        }
    };
    return borrowDetailCtrl;
});