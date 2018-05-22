define(['js/borrow/borrowUtils'], function (borrowUtils) {
    var pId;
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var joinId;
    var isUser = false;
    var isTender = false;
    var activity_url;
    var pType;
    var tradeListCtrl = {
        init: function (event) {
            var query = appFunc.getEventDetailPageQuery(event);
            pId = query.pId;
            joinId = query.joinId;
            isTender = query.isTender;
            pType = query.pType;
            if( isTender != undefined && isTender == 'true') {
                isTender = true;
                $$(".tradeItemName").html('债权列表');
            }else {
                $$(".tradeItemName").html('债权列表<img src="static/img/xxd/yiwen.png" style="height: 15px;margin-left: 10px;" class="tradeListyiwen">');
                appFunc.bindEvents([{
                    element: '.tradeListyiwen',
                    event: 'click',
                    handler: tradeListCtrl.tradeListyiwen
                }]);
            }

            if(joinId != undefined) {
                isUser = true;
            }

            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: tradeListCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: tradeListCtrl.infiniteScroll
                }
            ];
            appFunc.bindEvents(bindings);

            tradeListCtrl.pullToRefresh();
        },
        tradeListyiwen:function(){
            xxdApp.alert("您的资金将可能投资到以下任意一个或多个项目中，您可在投资后前往“我的新新贷—投资记录”查询详细的匹配记录","提示");
        },
        pullToRefresh:function(){
            tradeListCtrl.loanRelationshipRecord(1,"pull");
            $$(".tradeList").data("currentPage",1);
            // 重置加载flag
            loading = false;
        },
        infiniteScroll: function () {
            // 如果正在加载，则退出
            if (loading) {
                return;
            }
            //==========================切记此处不能删除============================
            loading = true;
            //==========================切记此处不能删除============================
            var totalPage = isUser ? 1000000 : $$(".tradeList").data("totalPage");
            var currentPage = $$(".tradeList").data("currentPage");
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
            tradeListCtrl.loanRelationshipRecord(currentPage, "push");
            $$(".tradeList").data("currentPage",currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function(){
                loading = false;
            },1500);
            //==========================切记此处不能删除============================
        },
        loanRelationshipRecord:function(currentPage,type){
            var url = 'product/queryFinanceBorrowList.do';
            var data = {
                pId: pId,
                currentPage: currentPage,
                pageSize: pageSize
            };

            if(joinId != undefined) {
                if(pType == 'xinshoubiao') {
                    url = 'product/bondsList.do';
                    data = {
                        joinId: joinId,
                        currentPage: currentPage,
                        pageSize: pageSize
                    };
                } else {
                    url = 'product/userLoanRelationshipRecord.do';
                    data = {
                        joinId: joinId,
                        currentPage: currentPage,
                        pageSize: pageSize
                    };
                }
            }
            req.callJSON({
                url: url,
                data: data,
                indicator:true,
                success: function (result) {
                    activity_url = result.activity_url;
                    if (result.code == 200000) {
                        var tradeList = [];
                        var totalCount = result.data.totalCount;
                        if(isUser) {
                            if(pType == 'xinshoubiao') {
                                totalCount = result.data.total;
                            }

                            if(totalCount == 0 && currentPage == 1) {
                                var html = "<li style='text-align: center;'>";
                                html += "<img src='static/img/xxd/pic_03.png' style='width: 6rem;margin-top: 2rem;'/><div style='text-align: center;margin-top: 15px;color:#70b5ff'>正在匹配中......</div></li>";
                                $$(".tradListul").html(html);
                                return;
                            } else if(totalCount == 0) {
                                //提示标的已经全部显示
                                xxdApp.addNotification({
                                    title: '温馨提示',
                                    hold:3000,
                                    message: '数据已全部加载完毕'
                                });
                                loading = true;
                                return;
                            }
                        } else {
                            $$(".tradeList").data("totalPage",result.data.pages);

                            if(totalCount == 0) {
                                var html = "<li style='text-align: center;'>";
                                html += "<img src='static/img/xxd/pic_03.png' style='width: 6rem;margin-top: 2rem;'/><div style='text-align: center;margin-top: 15px;color:#70b5ff'>正在匹配中......</div></li>";
                                $$(".tradListul").html(html);
                                return;
                            }
                        }

                        if(pType == 'xinshoubiao') {
                            var list = result.data.list;
                            for(var i in list) {
                                var b = list[i];
                                tradeList.push({
                                    loanName:b.name,
                                    loanPeriod:b.period,
                                    loanPeriodUnit:borrowUtils.toPeriodunit(b.periodUnit),
                                    loanApr:b.apr,
                                    borrowId:b.borrowid,
                                    type:b.isNew == 1 ? 20 : '',
                                    loanAmount:appFunc.fmoney(b.effectivemoney,2)
                                });
                            }

                        } else {
                            var list = result.data.items;

                            if(list != undefined) {
                                for(var i in list) {
                                    var b = list[i];
                                    tradeList.push({
                                        loanName:b.name,
                                        loanPeriod:b.period,
                                        loanPeriodUnit:borrowUtils.toPeriodunit(b.periodUnit),
                                        loanApr:b.apr,
                                        borrowId:b.borrowId,
                                        loanAmount:appFunc.fmoney(b.account,2)
                                    });
                                }
                            } else {
                                list = result.data.item;
                                for(var i in list) {
                                    var b = list[i];
                                    tradeList.push({
                                        loanName:b.loanName,
                                        loanPeriod:b.loanPeriod,
                                        loanPeriodUnit:borrowUtils.toPeriodunit(b.loanPeriodUnit),
                                        loanApr:b.loanApr,
                                        borrowId:b.borrowId,
                                        type:b.type,
                                        loanAmount:appFunc.fmoney(b.investAmount,2)
                                    });
                                }
                            }
                        }


                        var htmlUrl = GC.getHtmlPath() + 'trade/tradeListItem.html?' + GC.getVersion();
                        if(isTender) {
                            htmlUrl =  GC.getHtmlPath() + 'trade/tradeListTenderItem.html?' + GC.getVersion();
                        }
                        try {
                            req.callGet({
                                url: htmlUrl,
                                dataType: 'text',
                                success: function (result) {
                                    var compiledTemplate = t7.compile(result);
                                    var output = compiledTemplate({list:tradeList});

                                    if (type == 'push') {
                                        $$(".tradListul").append(output);
                                    } else {
                                        $$(".tradListul").html(output);
                                    }

                                    var bind = [{
                                        element: '.tradeListItemLink',
                                        event: 'click',
                                        handler: tradeListCtrl.tradeListItemLink
                                    }];
                                    if(isTender) {
                                        bind.push({
                                            element: '.tradeListContract',
                                            event: 'click',
                                            handler: tradeListCtrl.tradeListContract
                                        });
                                    }
                                    appFunc.bindEvents(bind);

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

            setTimeout(function(){
                if (type == 'pull') {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            },5000);
        },
        tradeListItemLink:function(){
            var borrowId = $$(this).data("bid");
            var type = $$(this).data("type");
            if(type == "20") {
                var url = activity_url + "consumes/detail?bidCode="+borrowId;
                window.location.href = url;
            }else {
                GS.loadPage("borrow/borrowDetail.html?borrowId=" + borrowId + "&path=borrow&bottomHide=true");
            }
        },
        tradeListContract:function(){
            var borrowId = $$(this).data("bid");
            var type = $$(this).data("type");
            GS.loadPage('borrow/contractModel.html?borrowId='+borrowId+"&source=3&type="+type);
        }
    };
    return tradeListCtrl;
});