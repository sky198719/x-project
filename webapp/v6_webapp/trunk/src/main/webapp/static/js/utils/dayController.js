define(function () {
    var dayController = {
        memberDay: function (param) {
            req.callGet({
                url: 'activity/checkActivity.do',
                data:{
                    activityType:'vip-activity'
                },
                dataType: 'json',
                success: function (result) {
                    if (result.resultCode == 0) {
                        param.callBack();
                    }
                }
            });
        },
        singlesday: function (param) {
            req.callGet({
                url: 'activity/checkSinglesdayActivity.do',
                dataType: 'json',
                success: function (result) {
                    if (result.resultCode == 0) {
                        if (result.status == 0) {
                            param.callBack();
                        }
                    }
                }
            });
        },
        indexSinglesday: function () {
            dayController.singlesday({callBack: function () {
                var singlesdayTwo = $$(".singlesdayTwo");
                singlesdayTwo.css("margin", "0 15% 5px 0");
                $$(".singlesday").show();
            }});
        },
        financesListSinglesday: function () {
            dayController.singlesday({callBack: function () {
                $$(".dq-blue > img").attr("src", "static/img/xxd/1111.png");
                $$(".dq-blue > img").attr("height", "95px");

                $$(".nh-pop > img").attr("src", "static/img/xxd/1111.png");
                $$(".nh-pop > img").attr("height", "95px");
            }});
        },
        detailsSinglesday: function () {
            dayController.singlesday({callBack: function () {
                $$(".singlesdayDetails").show();
            }});
        },
        tenderSinglesday: function () {
             dayController.singlesday({callBack: function () {
                 var html = '<img src="static/img/xxd/1111day.png" style="height: 20px;margin-left: 10px;"/>';
                 var html2 = '<img src="static/img/xxd/1111day2.png" style="height: 20px;margin-left: 10px;"/>';

                 $$("#planActivityTip_tender").html($$("#planActivityTip_tender").html() + html);
                 $$('.stepUpwardTender #itemName').html($$('.stepUpwardTender #itemName').html() + html);

                 $$('.monthFinanceTender #itemName').html($$('.monthFinanceTender #itemName').html()+html);
                 $$(".sevenDaysTrade .sevenDaysName").html($$(".sevenDaysTrade .sevenDaysName").html() + html);
                 $$(".yypTender .yyName").html($$(".yypTender .yyName").html()+html);
                 $$(".fundUnInvested .fundtop").html($$(".fundUnInvested .fundtop").html() + html2);
                 $$(".fundInvested .zuorishouyi").html($$(".fundInvested .zuorishouyi").html()+html2);

                 var html3 = '<div style="margin-top: 10px;" class=""><img src="static/img/xxd/1111day2.png" style="height: 20px;margin-left: 10px;"/></div>';
                 $$(".fundInvested .fundTitle3").html($$(".fundInvested .fundTitle3").html()+html3);
             }});
         },
        indexMemberDay: function () {
            dayController.memberDay({callBack: function () {
                $("div[name='indexPlanDiv1']").hide();
                $("div[name='indexPlanDiv2']").show();
            }});

            dayController.day11({
                callBack:function(){
                    $("div[name='indexPlanDiv11']").show();
                }
            });
        },

        day11:function(param){
            req.callJSON({
                url: 'activity/checkActivity.do',
                data: {
                    code: 'Double-11-activity'
                },
                success: function (result) {
                    if (result.resultCode == 0) {
                        param.callBack();
                    }
                }
            });
        },
        planDetailMemberDay: function () {
            dayController.memberDay({callBack: function () {
//                $("div[name='planDetailDiv1']").show();
//                document.getElementById("planDetailCorner").style.marginTop="15px";

                $$("#tenderApr").css("font-size", "30px");
                $$("#detailAprDiv").css("padding-top", "14px");
                $$("#planActivityTip_detail").show();
                $$("#planActivityTip_detail").addClass("animated fadeInLeft");

                $$("#stepActivityTip_detail").show();
                $$("#stepActivityTip_detail").addClass("animated fadeInLeft");
            }});

            dayController.day11({
                callBack:function(){
                    $$("#planActivityTip_detail").html('<img src="static/img/activity/1111_2.png" height="20">');
                    $$("#planActivityTip_detail").show();
                    $$("#planActivityTip_detail").addClass("animated fadeInLeft");
                }
            });
        },
        planTenderMemberDay: function () {
            dayController.memberDay({callBack: function () {
                $("#planActivityTip_tender").append('<span class="font-red font16" style="padding-left:5px;">会员日抽奖</span>');
            }});

            dayController.day11({callBack: function () {
                $("#planActivityTip_tender").append('<span class="font-red font16" style="padding-left:5px;">双11狂赚</span>');
            }});
        },
        financesListMemberDay: function () {
            dayController.memberDay({callBack: function () {
                //会员日图片替换static/img/xxd/dq-gray.png-->static/img/activity/member_return_gray.png(dq-blue.png-->member_return.png)
                var s = $("img[name='financesListPlanLogo']");

                $.each(s, function () {
                    $(this).attr("height", "80px");
                    var src = $(this).attr("src");
                    if (src != null && src.indexOf("dq-gray") > -1) {
//                        $(this).attr("src","static/img/activity/member_return_gray.png");
                        $(this).attr("src", "static/img/activity/md_cj_list.png");
                    } else {
//                        $(this).attr("src","static/img/activity/member_return.png");
                        $(this).attr("src", "static/img/activity/md_cj_list.png");
                    }
                });
            }});

            dayController.day11({
                callBack:function(){
                    var s = $(".finances1111Logo");

                    $.each(s, function () {
                        $(this).attr("height", "80px");
                        var src = $(this).attr("src");
                        if (src != null && src.indexOf("dq-gray") > -1) {
//                        $(this).attr("src","static/img/activity/member_return_gray.png");
                            $(this).attr("src", "static/img/activity/1111.png");
                        } else {
//                        $(this).attr("src","static/img/activity/member_return.png");
                            $(this).attr("src", "static/img/activity/1111.png");
                        }
                    });
                }
            });
        }
    };
    return dayController;
});
