define(['js/utils/date','js/plan/planUtils'], function (DateHandle,PlanUtils) {
    var myPlanListView = {
        init: function (params) {
        },
        bindEvents: function (param) {
            appFunc.bindEvents(param.bindings);
        },
        showListItem: function (param) {
            var list = "";
            if (param.data.resultList.length != 0) {
                list = param.data.resultList;
                $$("#planHistoryPage").data("totalPage",param.data.pageNums);

                var output = '';
                output += '<div class="list-block media-list"><ul style="background: #efeff4;">';
                for (var i = 0; i < list.length; i++) {
                    var b = list[i];
                    // 过滤撤销的_
                    if(b.STATUS == 5){
                        continue;
                    }
                    var min_max_apr;
                    if (b.MINAPR == b.MAXAPR) {
                        min_max_apr = b.MINAPR + '%';
                    } else {
                        min_max_apr = b.MINAPR + '% ~ ' + b.MAXAPR + '%';
                    }
                    var collectiontype;
                    if (b.userSchemeInfo.collectiontype == 1) {
                        collectiontype = '收益再投资';
                    } else {
                        collectiontype = '提取至账户';
                    }

                    var imgpath = '<img src="';
                    /*switch (parseInt(b.SCHEMESTATUS)) {
                        case 1:
                            imgpath += 'static/img/xxd/reserve.png';
                            break;
                        case 2:
                            imgpath += 'static/img/xxd/pay.png';
                            break;
                        case 3:
                            imgpath += 'static/img/xxd/open.png';
                            break;
                        case 4:
                            imgpath += 'static/img/xxd/lock.png';
                            break;
                        case 5:
                            imgpath += 'static/img/xxd/quit.png';
                            break;
                        case 9:
                            imgpath += 'static/img/xxd/quit.png';
                            break;
                    }*/
                    var statusText = "";/*PlanUtils.schemeToShowStatus(parseInt(b.SCHEMESTATUS),false);*/
                    /*if (b.SCHEMESTATUS == '4') {
                        imgpath = "";
                        statusText = "已结束";
                    } else {*/
                        if(b.userSchemeInfo.status == '3'){
                            imgpath += "static/img/xxd/quit.png";
                            imgpath += '" width="18px">';
                            statusText = "已提前退出";
                        } else if(b.userSchemeInfo.status == '-1'){
                            imgpath = "";
                            statusText = "购买失败";
                        } else if(b.userSchemeInfo.status == '0'){
                            imgpath = "";
                            statusText = "购买中";
                        } else if(b.userSchemeInfo.status == '1'){
                            imgpath = "";
                            statusText = "购买成功";
                        } else if(b.userSchemeInfo.status == '2'){
                            imgpath += "static/img/xxd/quit.png";
                            imgpath += '" width="18px">';
                            statusText = "已退出";
                        } else if(b.userSchemeInfo.status == '4'){
                            imgpath = "";
                            statusText = "正在退出";
                        }
                    /*}*/
                    output += '<li>'+
                                '<div class="card">'+
                                    '<div class="card-header" name="showPlanDetail" data-id="' + b.SCHEMEID + '" data-joinid="'+b.USERSCHEMEID+'">'+
                                        '<span class="font18 text-overflow">'+PlanUtils.schemeType(b.TYPE) + ' - ' + b.PNAME+'</span>'+
                                        '<span class="color-gray" style="margin-right: 5px;">'+imgpath+'&nbsp;' + statusText + '&nbsp;&gt;</span>' +
                                    '</div>'+
                                    '<div class="card-content">'+
                                        '<div class="card-content-inner">';
                                            /*if(!((b.SCHEMESTATUS == '1' || b.SCHEMESTATUS == '2') && b.userSchemeInfo.surplusMoney > 0)){
                                                //已收收益 or 待收收益#14302  已收收益字段改成EARNED
                                                var profitName = "已收收益";
                                                var earnings = 0.0;
                                                if(b.SCHEMESTATUS == '9') {
                                                    profitName = "待收收益";
                                                }
                                                if(b.VERSION == "v2.0") {
                                                    if(b.SCHEMESTATUS == '9') {
                                                        earnings = b.schemeEarnings;
                                                    } else {
                                                        earnings = b.EARNED;
                                                    }
                                                } else if(b.VERSION == "v1.0") {
                                                    earnings = b.schemeEarnings;
                                                }
                                                output += '<div class="width100 mb10"><span class="font18">'+ profitName +'</span><span class="right font18"><span class="font-red">'+appFunc.fmoney(earnings, 2)+'</span>元</span></div>';
                                            }*/
                                   output+= '<div class="width100 mb5"><span>加入金额</span><span class="right userschemeid_ planMoney planMoney'+b.USERSCHEMEID+'">'+appFunc.fmoney(b.USERACCOUNT, 2)+'元</span></div>' +
                                            '<div class="width100 mb5"><span>历史年化收益</span><span class="right">'+min_max_apr+'</span></div>' +
                                             '<div class="width100 mb5"><span>锁定期限</span>' + '<span class="right">'+ b.CLOSETERM +'个月('+DateHandle.formatDate('yyyy-MM-dd',new Date(b.endTime))+'退出)</span></div>' +
                                             '<div class="width100 mb5"><span>我的债权</span><span class="right color-blue userTradeList" data-userschemeid="'+ b.USERSCHEMEID+'">点击查看</span></div>' +
                                             '<div class="width100 mb5" style="display: none;"><span>收益处理</span>' + '<span class="right">' + collectiontype + '</span></div>';
                                           if(b.VERSION != 'v1.0') {
                                               output+= '<div class="width100 mb5"  style="height: 10px;">' + '<span class="right joinDeatail" data-userschemeid="'+ b.USERSCHEMEID+'" data-schemeid="'+b.userSchemeInfo.schemeid+'">加入详情<img src="static/img/xxd/bs.png" data-isshow="0"  class="img_'+b.USERSCHEMEID+'" style="margin: 0 0 -2px 5px;height: 15px;"/></span></div>'+
                                               '<div class="width100 joinDeatail_'+b.USERSCHEMEID+'"></div>';
                                           }
                    output+= '</div>';
                                      // 预定、支付
                                      /*if ((b.SCHEMESTATUS == '1' || b.SCHEMESTATUS == '2') && b.userSchemeInfo.surplusMoney > 0) {
                                          output += '<div class="card-content-inner" style="padding-top: 0px;">'+
                                                          '<fieldset style="border-width: 1px;">'+
                                                            '<legend>&nbsp;&nbsp;<span class="font-dark">支付信息</span>&nbsp;&nbsp;</legend>'+
                                                             '<div class="width100"><span style="padding-left: 10px;">已付定金</span><span class="right" style="padding-right: 10px;">'+appFunc.fmoney(b.userSchemeInfo.earnestMoney, 2)+'元</span></div>'+
                                                             '<div class="width100"><span style="padding-left: 10px;">还需支付</span><span class="right"  style="padding-right: 10px;"><span class="font-red">'+appFunc.fmoney(b.userSchemeInfo.surplusMoney, 2)+'</span>元</span></div>'+
                                                        '</fieldset>'+
                                                        '<div class="width100 font13 font-dark" style="text-align: right;margin-top: 10px;">付款截止时间：'+b.PRESALEPAYEND+'</div>'+
                                                     '</div>';
                                      }*/
                          output += '</div>'+
                                    '<div class="card-footer" >'+
                                         '<div class="row width100">';
                                              var showAgreementButton = '<div class="col-50 button button-big" style="border-color: #2096e9;" data-id="' + b.SCHEMEID + '" name="planAgreement"><a href="#" style="color:#2096e9" >查看协议</a></div>';

                                              if(b.userSchemeInfo.status == '1' && b.SCHEMESTATUS == '3' /*&& b.userSchemeInfo.status != 6 && b.userSchemeInfo.status != 8*/){  //开放、锁定、退出
                                                  output += showAgreementButton +
                                                          '<div class="col-50" data-id="' + b.SCHEMEID + '" ptype="'+ b.TYPE + '" pname="' + b.PNAME + '" forfeitpercent="' + b.forfeitpercent +
                                                          '" name="advance_quit" data-joinid="'+b.USERSCHEMEID+'" data-tendermoney="'+b.USERACCOUNT+'"><a href="#" class="button button-fill button-big dmp-click" style="background: #2096e9;" dmp_type="click" dmp_event="redeem" dmp_target_type="1" dmp_target_id="4.'+b.CLOSETERM+'" dmp_content_lable="' + appFunc.fmoney(b.USERACCOUNT, 2) + '">提前退出</a></div>';
                                              } /*else if ((b.SCHEMESTATUS == '1' || b.SCHEMESTATUS == '2') && b.userSchemeInfo.surplusMoney > 0){
                                                  output += showAgreementButton +
                                                          '<div class="col-50" name="plan_pay_href" data-id="' + b.SCHEMEID + '"><a href="#" class="button button-fill button-big" style="background: #2096e9;" >立即付款</a></div>';
                                              }*/ else {
                                                  output += '<div class="button button-big" style="border-color: #2096e9;width: 100%;" data-id="' + b.SCHEMEID + '" name="planAgreement" ><a href="#" style="color:#2096e9" >查看协议</a></div>';
                                              }
                                   output += '</div>'+
                                    '</div>'+
                                '</div>'+
                            '</li>';

                }
              // var planMoney = document.getElementsByClassName("planMoney")[0];
              // var plan_outMoney = appFunc.fmoney(b.USERACCOUNT, 2);
              // planMoney.setAttribute("dmp_content_span","plan_outMoney");
                output += '</ul></div>';
                if (param.type == 'push') {
                    $("#myPlanList").append(output);
                } else {
                    $("#myPlanList").html(output);
                }
            } else {
                var html = '<div class="list-block media-list" ><ul>' +
                    '<h6 class="font-grey text-center pd10">暂无记录' +
                    '</h6></ul></div>';
                $("#myPlanList").html(html);
            }

            appFunc.bindEvents([
                {
                    element: '.joinDeatail',
                    event: 'click',
                    handler: myPlanListView.joinDeatail
                },
                {
                    element: '.userTradeList',
                    event: 'click',
                    handler: myPlanListView.userTradeList
                }
            ])
        },

        joinDeatail:function(){
            var userschemeid = $$(this).data("userschemeid");
            var img = $(".img_"+ userschemeid);
            var isshow =img.data("isshow");

            if(isshow == "1") {
                img.data("isshow","0");
                img.attr("src","static/img/xxd/bs.png");
                $$(".joinDeatail_" + userschemeid).html("");
                return ;
            }

            var schemeid = $$(this).data("schemeid");
            if(schemeid != undefined &&  "" != schemeid) {
                req.callGet({
                    url:'xplan/getUserSchemeInfo.do',
                    data:{
                        schemeId:schemeid
                    },
                    dataType:'json',
                    success:function(result){
                        if(result.resultCode == 0) {
                            var userschemeidObj = $$(".userschemeid_"+userschemeid);
                            userschemeidObj.html(appFunc.fmoney(result.data.remaccount, 2)+"元");
                        }
                    }
                });
            }

            img.attr("src","static/img/xxd/sb.png");
            img.data("isshow","1");
            req.callGet({
                url:'xplan/queryUserDetailPage.do',
                data:{
                    userSchemeId:userschemeid
                },
                dataType:'json',
                indicator:true,
                success:function(result){
                    if(result.resultCode == 0) {
                        var b;
                        var html = '<div class="width100" style="text-align: center;background: #e8f4ff;font-size: 0.6rem;margin-top: 15px;height: 23px;padding-top: 5px;">'+
                                '<div style="width: 25%;float:left;display: block;">日期</div>' +
                                '<div style="width: 25%;float:left;display: block;">加入金额(元)</div>' +
                                  '<div style="width: 30%;float:left;display: block;">历史年化收益</div>' +
                                  '<div style="width: 20%;float:left;display: block;">加入平台</div>' +
                              '</div>';

                        for(var i = 0 ;i < result.list.length;i++) {
                             b = result.list[i];
                            var t_channel = '';
                            switch (b.channel) {
                                case '1':
                                    t_channel = 'PC端';
                                    break;
                                case '2':
                                    t_channel = 'WEBAPP端';
                                    break;
                                case '3':
                                    t_channel = 'APP端';
                                    break;
                                case '4':
                                    t_channel = 'IOS端';
                                    break;
                                case '5':
                                    t_channel = '安卓端';
                                    break;
                                case 'PC':
                                    t_channel = 'PC端';
                                    break;
                                case 'WEBAPP':
                                    t_channel = 'WEBAPP端';
                                    break;
                                case 'APP':
                                    t_channel = 'APP端';
                                    break;
                            }
                            html += '<div class="width100" style="text-align: center;font-size: 0.6rem;height: 33px;float: left;margin: 4px;">' +
                                        '<div style="width: 25%;float:left;">'+ b.addTime+'</div>' +
                                        '<div style="width: 25%;float:left;">'+appFunc.fmoney(b.account,2)+'</div>' +
                                        '<div style="width: 30%;float:left;">'+ (b.apr == undefined ? '' : (b.apr + '%'))+ (b.floatApr == 0 || b.floatApr == undefined ? '' : '<span class="font-red">+'+ b.floatApr+'%</span>')+'</div>' +
                                        '<div style="width: 20%;float:left;">'+ t_channel +'</div>' +
                                    '</div>';
                        }
                        $$(".joinDeatail_" + userschemeid).html(html);
                    }
                }
            });
        },
        userTradeList:function () {
            var userSchemaId = $$(this).data("userschemeid");
            GS.loadPage("trade/tradeList.html?joinId="+userSchemaId+"&isTender=true");
        }
    };
    return myPlanListView;
});
