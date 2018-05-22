define(['js/utils/date','js/plan/planUtils'], function (DateHandle,PlanUtils) {
    var yypHistoryView = {
        init: function (params) {
        },
        bindEvents: function (param) {
            appFunc.bindEvents(param.bindings);
        },
        showListItem: function (param) {
            var list = "";

            if (param.data.code == 200000) {
                if (param.data.data.list.length != 0) {
                    list = param.data.data.list;
                    $$("#yypHistory #totalPage").val(param.data.data.total);

                    var output = '';
                    output += '<div class="list-block media-list"><ul style="background: #efeff4;">';
                    for (var i = 0; i < list.length; i++) {
                        var yyp = list[i];

                        var status = yyp.status;
                        //状态0支付中1投标中2到期退出3提前退出-1支付失败
                        //只展现所定义和已退出状态
                        if(status == 0 || status == -1){
                            continue;
                        }

                        var imgpath = '<img src="';
                        switch (status) {
                            case 0:
                                imgpath += 'static/img/xxd/pay.png';
                                break;
                            case 1:
                                imgpath += 'static/img/xxd/lock.png';
                                break;
                            case 2:
                                imgpath += 'static/img/xxd/quit.png';
                                break;
                            case 3:
                                imgpath += 'static/img/xxd/quit.png';
                                break;
                            case 4:
                                imgpath += 'static/img/xxd/quit.png';
                                break;
                            case -1:
                                imgpath += 'static/img/xxd/pay.png';
                                break;
                        }
                        imgpath += '" width="15px">';
                        var statusText = yypHistoryView.getStatusText(status);
                        output += '<li>'+
                            '<div class="card">'+
                            '<div class="card-header" ';
                        if(status == 1) {
                            output += ' name="showYypDetail"';
                        }

                        //锁定期限减一天
                        var endDate=new Date(yyp.endDate);
                        var lockDate = DateHandle.addDay(endDate,-1);

                        output+= ' data-id="' + yyp.productId + '" data-joinid="'+yyp.joinId+'">'+
                            '<span class="text-overflow" style="font-size:1rem;max-width: 68%;">'+ '月月派' + yyp.period + '个月 - ' + yyp.periodName+'期' +'</span>'+
                            '<span class="color-gray" style="font-size: 0.7rem;">'+imgpath+statusText + '&gt;</span>' +
                            '</div>'+
                            '<div class="card-content" style="font-size:0.9rem;">'+
                            '<div class="card-content-inner">';
                        var interest = yyp.interest;
                        interest = interest == undefined ? 0 : interest;
                        output+= '<div class="width100 mb10"><span style="font-size:1rem;">待收收益</span><span class="right" style="font-size:1rem;"><span class="font-red">'+appFunc.fmoney(interest, 2)+'</span>元</span></div>';
                        output+= '<div class="width100 mb5"><span>加入金额</span><span class="right yypMoney">'+appFunc.fmoney(yyp.account, 2)+'元</span></div>' +
                            '<div class="width100 mb5"><span>历史年化收益</span><span class="right">'+yyp.joinApr+'%</span></div>' +
                            '<div class="width100 mb5"><span>锁定期限</span><span class="right">'+ yyp.period +'个月('+DateHandle.formatDate('yyyy-MM-dd',lockDate)+'退出)</span></div>' +
                            '<div class="width100 mb5"><span>还款方式</span><span class="right">每月返息，到期通过债权转让退出</span></div>'+
                            '<div class="width100 mb5"><span>我的债权</span><span class="right color-blue userTradeList" data-joinid="'+ yyp.joinId+'">点击查看</span></div>' +
                            '</div>';
                        output += '</div></div></li>';
                        // var dmp_click = document.getElementsByClassName("dmp-click")[0];
                        // var yypMoney = document.getElementsByClassName("yypMoney")[0];
                        // var yyp_outMoney = appFunc.fmoney(yyp.USERACCOUNT, 2);
                        // var yypTerm = "5." + yyp.TERMS;
                        // dmp_click.setAttribute("dmp_target_id","yypTerm");
                        // yypMoney.setAttribute("dmp_content_span","yyp_outMoney");
                    }
                    output += '</ul></div>';
                    if (param.type == 'push') {
                        $("#myYypList").append(output);
                    } else {
                        $("#myYypList").html(output);
                    }
                } else {
                    var html = '<div class="list-block media-list" ><ul>' +
                        '<h6 class="font-grey text-center pd10">暂无记录' +
                        '</h6></ul></div>';
                    $("#myYypList").html(html);
                }
            } else {
                xxdApp.addNotification({ title: '温馨提示', message: param.data.desc, hold: 3000 });
            }

            appFunc.bindEvents([
                {
                    element: '.userTradeList',
                    event: 'click',
                    handler: yypHistoryView.userTradeList
                }
            ])
        },

        getStatusText:function(status){
            var text = "";
            //只展现所定义和已退出状态
            switch (status) {
                case 0:
                    text = "支付中";
                    break;
                case 1:
                    text = "锁定期";
                    break;
                case 2:
                    //到期退出
                    text = "已退出";
                    break;
                case 3:
                    text = "已提前退出";
                    break;
                case 4:
                    text = "退出中";
                    break;
                case -1:
                    text = "支付失败";
                    break;
            }
            return text;
        },
        userTradeList:function () {
            var joinId = $$(this).data("joinid");
            GS.loadPage("trade/tradeList.html?joinId="+joinId+"&isTender=true");
        }

    };
    return yypHistoryView;
});
