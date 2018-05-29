/**
 * 红包列表页面
 */
define(['js/account/hongbaoView', 'js/account/hongbaoModel','js/utils/date'], function (hongbaoView, hongbaoModel,DateHandle) {
    // 加载flag
    var loading = false;
    var hongbaoCtrl = {
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
                    handler: hongbaoCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: hongbaoCtrl.infiniteScroll
                },
                {
                    element: '#my_use_coupon',
                    event: 'show',
                    handler: hongbaoCtrl.initMyUseCoupon
                },
                {
                    element: '#my_used_coupon',
                    event: 'show',
                    handler: hongbaoCtrl.initMyUsedCoupon
                },
                {
                    element: '#my_out_coupon',
                    event: 'show',
                    handler: hongbaoCtrl.initMyOutCoupon
                },
                {
                    element:'.conversionCouponRecord',
                    event:'click',
                    handler:hongbaoCtrl.conversionCouponRecord
                },
                {
                    element:'.couponRecordCode',
                    event:'keyup',
                    handler:hongbaoCtrl.couponRecordCodeKeyup
                }
            ];
            hongbaoView.bindEvents({
                    bindings: bindings
                }
            );
            hongbaoCtrl.pullToRefresh();
        },
        couponRecordCodeKeyup:function(){
            var couponRecordCode = $.trim($(".couponRecordCode").val());
            if(couponRecordCode.length == 8) {
                $$(".conversionCouponRecord").removeClass("couponBut");
                $$(".conversionCouponRecord").addClass("active button-51");
            } else {
                if(!$(".conversionCouponRecord").hasClass("couponBut")) {
                    $$(".conversionCouponRecord").addClass("couponBut");
                    $$(".conversionCouponRecord").removeClass("active button-51");
                }
            }
        },
        conversionCouponRecord:function() {

            if($(".conversionCouponRecord").hasClass("couponBut")) {
                return false;
            }

            var couponRecordCode = $.trim($(".couponRecordCode").val());
            req.callPost({
                url:'product/conversionCouponRecord.do',
                data:{
                    code:couponRecordCode
                },
                dataType:'json',
                success:function(result) {
                    if(result.code == 200000) {
                        if(result.data.code == 0) {
                            xxdApp.modal({
                                title: '提示',
                                afterText: "绑定成功",
                                buttons: [
                                    {
                                        text: '确定',
                                        onClick: function() {
                                            $$(".couponRecordCode").val("");
                                            hongbaoCtrl.selectCouponList(1, 1, "pull");
                                        }
                                    }
                                ]
                            });

                        } else {
                            xxdApp.alert(result.data.message,"提示");
                        }
                    } else {
                        xxdApp.alert(result.message,"提示");
                    }
                }
            });
        },

        initMyUseCoupon: function () {
            $('#hb_currentTab').val('myUseCouponTab');
            hongbaoCtrl.pullToRefresh();
        },
        initMyUsedCoupon: function () {
            $('#hb_currentTab').val('myUsedCouponTab');
            hongbaoCtrl.pullToRefresh();
        },
        initMyOutCoupon: function () {
            $('#hb_currentTab').val('myOutCouponTab');
            hongbaoCtrl.pullToRefresh();
        },
        /**
         * 下拉刷新页面
         */
        pullToRefresh: function () {
            var currentTab = $('#hb_currentTab').val();
            if (currentTab == 'myUseCouponTab') {
                hongbaoCtrl.selectCouponList(1, 1, "pull");
            } else if (currentTab == 'myUsedCouponTab') {
                hongbaoCtrl.selectCouponList(2, 1, "pull");
            } else if (currentTab == 'myOutCouponTab') {
                hongbaoCtrl.selectCouponList(3, 1, "pull");
            }
            $$("#hb_currentPage").val(1);
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
            var totalPage = $$("#hb_totalPage").val();
            var currentPage = $$("#hb_currentPage").val();
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if (currentPage > totalPage) {
                //提示标的已经全部显示
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '已经到底了'
                });
                loading = true;
                return;
            }
            var currentTab = $('#hb_currentTab').val();
            if (currentTab == 'myUseCouponTab') {
                hongbaoCtrl.selectCouponList(1, currentPage, "push");
            } else if (currentTab == 'myUsedCouponTab') {
                hongbaoCtrl.selectCouponList(2, currentPage, "push");
            } else if (currentTab == 'myOutCouponTab') {
                hongbaoCtrl.selectCouponList(3, currentPage, "push");
            }
            $$("#hb_currentTab").val(currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function () {
                loading = false;
            }, 1500);
            //==========================切记此处不能删除============================
        },

        selectCouponList: function (status ,currentPage, type) {
            req.callJSON({
                url: "redpacket/list.do",
                data: {
                    currentPage: currentPage,
                    couponStatus: status,
                    redStatus:status
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    var list = "";
                    var data = result.redpacket;
                    if (data != null && data.data != null) {
                        list = data.data;
                        $$("#hb_totalPage").val(data.totalPages);
                    }
                    var hongbaoList = [];
                    if (list != null && list != "") {
                        for (var i = 0; i < list.length; i++) {
                            var b = list[i];
                            hongbaoList.push({
                                'name': b.name,
                                'faceValue': b.faceValue,
                                'quota': b.quota,
                                'code':b.redCode,
                                'usrDesc':'适用于步步高升，_，_及散标（票据贷除外）',
                                'usr':'PC、WAP、APP可用',
                                'status':status == 1 ? true : false,
                                'bengDate':DateHandle.formatDate('yyyy-MM-dd',new Date(Date.parse(b.addTime.replace(/-/g,"/")))),
                                'validDate': DateHandle.formatDate('yyyy-MM-dd',new Date(Date.parse(b.validDate.replace(/-/g,"/"))))
                            });
                        }
                    }

                    hongbaoCtrl.sortToValue(hongbaoList);

                    if(result.coupon != undefined && result.coupon.couponList != undefined) {

                        var couponList  = result.coupon.couponList;
                        hongbaoCtrl.coupoSortToValue(couponList);
                        for (var i = 0; i < couponList.length; i++) {
                            var b = couponList[i];
                            var usr = '';
                            if(b.platform != undefined) {
                                usr = b.platform.join("、") + '可用';
                            }
                            var usrDesc = '适用于';
                            if(b.productScope != undefined) {
                                for(var j = 0 ;j < b.productScope.length;j++) {
                                    var bb = b.productScope[j];
                                    if(bb.productType == 5) {
                                        usrDesc += '_' + bb.termsList.join("/") + '个月';
                                    } else if(bb.productType == 2) {
                                        usrDesc += "步步高升";
                                    } else if(bb.productType == 3) {
                                        usrDesc += "七天大胜";
                                    } else if(bb.productType == 4) {
                                        usrDesc += "_";
                                    } else if(bb.productType == 6) {
                                        usrDesc += "_" + bb.termsList.join("/") + '个月';
                                    } else if(bb.productType  == 7) {
                                        usrDesc += "散标";
                                    } else if(bb.productType == 8) {
                                        usrDesc += "债权";
                                    }
                                }
                            }

                            hongbaoList.push({
                                'name': b.name,
                                'faceValue': b.amount,
                                'quota': b.quota,
                                'code':b.code,
                                'usrDesc':usrDesc,
                                'usr':usr,
                                'status':status == 1 ? true : false,
                                'bengDate':DateHandle.formatDate('yyyy-MM-dd',new Date(b.effectiveStartTime)),
                                'validDate': DateHandle.formatDate('yyyy-MM-dd',new Date(b.effectiveEndTime))
                            });

                        }
                    }
                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'account/hongbaoNewItem.html?' + GC.getVersion(),
                            dataType: 'text',
                            success: function (result) {
                                hongbaoView.showListItem({
                                    result: result,
                                    hongbaoList: hongbaoList,
                                    type: type,
                                    status : status
                                });
                                // 加载完毕需要重置
                                xxdApp.pullToRefreshDone();
                            }
                        });
                    } catch (e) {
                        xxdApp.hideIndicator();
                    }
                }
            });

            setTimeout(function () {
                if (type == 'pull') {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            }, 5000);
        },
        coupoSortToValue:function(selectList) {
            selectList.sort(function (a, b) {
                return b.amount - a.amount;
            });
        },
        sortToValue: function (selectList) {
            selectList.sort(function (a, b) {
                return b.faceValue - a.faceValue;
            });
        }

    };
    return hongbaoCtrl;
});
