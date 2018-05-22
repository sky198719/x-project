define(['js/utils/date'],function(DateHandle){
    var hongbaoList = [];
    var selectList = [];
    var unavailable = [];
    var showParam;
    var selectHongbao = {
        init:function() {
            hongbaoList.length = 0;
            req.callJSON({
                url: "redpacket/list.do",
                data: {
                    currentPage: 1,
                    couponStatus: 1,
                    redStatus: 1
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    if(result.redpacket != undefined && result.redpacket.data != undefined) {
                        var list = result.redpacket.data;
                        for (var i = 0; i < list.length; i++) {
                            var b = list[i];
                            hongbaoList.push({
                                'name': b.name,
                                'faceValue': b.faceValue,
                                'quota': b.quota,
                                'code':b.redCode,
                                'usrDesc':'适用于步步高升，新元宝，月月派及散标（票据贷除外）',
                                'usr':'PC、WAP、APP可用',
                                'productScope':[{
                                    productType:'all'
                                }],
                                'platform':['all'],
                                'type':1,
                                'couponId':0,
                                'bengDate':DateHandle.formatDate('yyyy-MM-dd',new Date(Date.parse(b.addTime.replace(/-/g,"/")))),
                                'validDate': DateHandle.formatDate('yyyy-MM-dd',new Date(Date.parse(b.validDate.replace(/-/g,"/"))))
                            });
                        }
                    }


                    if(result.coupon != undefined && result.coupon.couponList != undefined) {
                        var couponList  = result.coupon.couponList;
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
                                        usrDesc += '新元宝' + bb.termsList.join("/") + '个月';
                                    }else if(bb.productType == 2) {
                                        usrDesc += "步步高升";
                                    } else if(bb.productType == 3) {
                                        usrDesc += "七天大胜";
                                    } else if(bb.productType == 4) {
                                        usrDesc += "月进斗金";
                                    } else if(bb.productType == 6) {
                                        usrDesc += "月月派" + bb.termsList.join("/") + '个月';
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
                                'productScope':b.productScope,
                                'platform':b.platform,
                                'type':2,
                                'couponId':b.couponId,
                                'bengDate':DateHandle.formatDate('yyyy-MM-dd',new Date(b.effectiveStartTime)),
                                'validDate': DateHandle.formatDate('yyyy-MM-dd',new Date(b.effectiveEndTime))
                            });
                        }
                    }
                }
            });

        },

        filter:function(tenderMoney,platform,terms,pType){
            selectList.length = 0;
            unavailable.length = 0;

            for(var i = 0;i < hongbaoList.length;i++) {
                var b = hongbaoList[i];
                var platformStr = b.platform.join(',').toLowerCase();

                if(platformStr.indexOf('all') >= 0 || platformStr.indexOf(platform) >= 0) {
                    if(b.quota <= tenderMoney) {
                        if(b.productScope != undefined) {
                            for(var j = 0 ;j < b.productScope.length;j++) {
                                var bb = b.productScope[j];
                                var productType = bb.productType;
                                if(productType == pType && productType == 5) {
                                    var termsList = bb.termsList;
                                    for (var z = 0; z < termsList.length; z++) {
                                        var cc = termsList[z];
                                        if (cc == terms) {
                                            selectList.push(b);
                                        }
                                    }
                                } else if(productType == pType && productType == 2) {
                                    selectList.push(b);
                                } else if(productType == 'all') {
                                    selectList.push(b);
                                } else {
                                    unavailable.push(b);
                                }
                            }
                        }
                    } else {
                        unavailable.push(b);
                    }
                } else {
                    unavailable.push(b);
                }
            }


            return selectList.length > 0 ? 0 : 1;

        },
        show:function(param) {
            var html = '<div style="width: 100%;height: 100%;background: #efeff4;">';
            html += '<div class="navbar">';
            html += '<div class="navbar-inner">';
            html += '<div class="left"><a href="#" class="link close-popup"> <i class="icon icon-back"></i><span>返回</span></a></div>';
            html += '<div class="center sliding">选择优惠券</div>';
            html += '<div class="right"><a href="#" class="link icon-only close-popup open-panel"> <i class="icon icon-bars"></i></a></div>';
            html += '</div>';
            html += '</div>';
            html += '<div class="list-block" style="background: #fff;padding: 10px;">';
            html += '<label class="label-radio item-content">';
            html += '<div class="row no-gutter couponItem" style="width: 100%" data-code="0" data-value="0">';
            html += '<div class="col-50" style="width: 90%">不使用优惠券</div>';
            html += '<div class="col-50" style="width: 10%">';
            if(param.code == "") {
                html += '<img src="static/img/xxd/123.png" style="width: 25px;margin-top: 10px;" class="couponSelectImg_0"/>';
            } else {
                html += '<img src="static/img/xxd/321.png" style="width: 25px;margin-top: 10px;" class="couponSelectImg_0"/>';
            }
            html += '</div>';
            html += '</div>';
            html += '</label>';
            html += '</div>';
            html += '<div class="list-block media-list">';
            html += '<ul style="background: #efeff4;">';
            if(selectList.length > 0) {
                selectHongbao.sortSelectHongbao(selectList);
                for(var i = 0 ;i < selectList.length; i++) {
                    var b = selectList[i];
                    html += '<li style="background: #fff;padding: 10px;border-bottom: 1px solid #c8c7cc;">';
                    html += '<div class="row no-gutter couponItem" data-code="'+b.code+'" data-value="'+b.faceValue+'" data-type="'+b.type+'" data-couponid="'+b.couponId+'">';
                    html += '<div class="col-25" style="text-align: center;">';
                    html += '<div style="font-size: 1.3rem;color:red;line-height: 1;margin-top: 20px;">'+b.faceValue+'元</div>';
                    html += '<div style="font-size: 0.7rem;">满'+b.quota+'元使用</div>';
                    html += '</div>';
                    html += '<div class="col-50" style="width: 65%;padding-left: 10px;">';
                    html += '<div class="item-title-row">';
                    html += '<div class="item-title" style="font-size: 0.9rem;">'+b.name+'</div>';
                    html += '</div>';
                    html += '<div class="item-subtitle" style="font-size: 0.7rem;color:#8e8e93;">'+b.bengDate+'至'+b.validDate+'</div>';
                    html += '<div class="item-text" style="font-size: 0.7rem;height:auto;min-height: 60px;"><div>'+b.usrDesc+'</div><div>'+b.usr+'</div></div>';
                    html += '</div>';
                    html += '<div class="col-25" style="width: 10%;">';
                    if(param.code == b.code) {
                        html += '<img src="static/img/xxd/123.png" style="width: 25px;margin-top: 40px;" class="couponSelectImg_'+b.code+'"/>';
                    } else {
                        html += '<img src="static/img/xxd/321.png" style="width: 25px;margin-top: 40px;" class="couponSelectImg_'+b.code+'"/>';
                    }
                    html += '</div>';
                    html += '</div>';
                    html += ' </li>';
                }
            }
            if(unavailable.length > 0) {
                selectHongbao.sortToDate(unavailable);
                for(var i = 0 ;i < unavailable.length; i++) {
                    var b = unavailable[i];
                    html += '<li style="background: #fff;padding: 10px;border-bottom: 1px solid #c8c7cc;">';
                    html += '<div class="row no-gutter">';
                    html += '<div class="col-25" style="text-align: center;">';
                    html += '<div style="font-size: 1.3rem;color:#8e8e93;line-height: 1;margin-top: 20px;">'+b.faceValue+'元</div>';
                    html += '<div style="font-size: 0.7rem;color: #8e8e93;">满'+b.quota+'元使用</div>';
                    html += '</div>';
                    html += '<div class="col-50" style="width: 65%;padding-left: 10px;">';
                    html += '<div class="item-title-row">';
                    html += '<div class="item-title" style="font-size: 0.9rem;color:#8e8e93;">'+b.name+'</div>';
                    html += '</div>';
                    html += '<div class="item-subtitle" style="font-size: 0.7rem;color:#8e8e93;">'+b.bengDate+'至'+b.validDate+'</div>';
                    html += '<div class="item-text" style="font-size: 0.7rem;height:auto;min-height: 60px;"><div>'+b.usrDesc+'</div><div>'+b.usr+'</div></div>';
                    html += '</div>';
                    html += '<div class="col-25" style="width: 10%;">';
                    html += '<img src="static/img/xxd/321.png" style="width: 25px;margin-top: 40px;"/>';
                    html += '</div>';
                    html += '</div>';
                    html += ' </li>';
                }
            }


            html += '</ul>';
            html += '</div>';
            html += '</div>';
            $$(".popup-bidhistory").html(html);
            xxdApp.popup('.popup-bidhistory');

            showParam = param;
            appFunc.bindEvents([
                {
                    element:'.couponItem',
                    event:'click',
                    handler:selectHongbao.couponItem
                }
            ]);
        },
        couponItem:function(){
            var code = $(this).attr("data-code");
            var value = $(this).attr("data-value");
            var type = $(this).attr("data-value");
            var couponId = $(this).attr("data-couponid");
            $("couponSelectImg_"+code).attr("src","static/img/xxd/123.png");

            showParam.callBack(code,value,type,couponId);
            xxdApp.closeModal('.popup-bidhistory');
        },
        sortToValue: function (selectList) {
            selectList.sort(function (a, b) {
                return b.faceValue - a.faceValue;
            });
        },
        sortSelectHongbao:function(selectList) {
            var xinshou = new Array();
            var hongbao = new Array();
            for(var i = 0 ;i < selectList.length; i++) {
                var b = selectList[i];
                if(b.type == 1) {
                    xinshou.push(b);
                } else {
                    hongbao.push(b);
                }
            }

            selectHongbao.sortToValue(xinshou);
            selectHongbao.sortToValue(hongbao);

            selectList.length = 0;
            for(var i = 0 ;i < xinshou.length; i++) {
                selectList.push(xinshou[i]);
            }
            for(var i = 0 ;i < hongbao.length; i++) {
                selectList.push(hongbao[i]);
            }

        },
        sortToDate:function(unavailable) {
            unavailable.sort(function(a,b) {
                return DateHandle.parseDate(a.validDate).getTime() -  DateHandle.parseDate(b.validDate).getTime();
            });
        }
    };
    return selectHongbao;
});