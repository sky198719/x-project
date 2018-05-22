define(['js/utils/date'], function (DateHandle) {
    var womens = {
        dayControl: function (param) {
            req.callGet({
                url: 'currentDate.do',
                dataType: 'json',
                success: function (result) {
                    if (result.resultCode == 0) {
                        var currentDate = DateHandle.parseDate(result.currentDate);
                        var beginDate = DateHandle.parseDate("2016-03-01 10:00:00");
                        var endDate = DateHandle.parseDate("2016-03-14 23:59:59");
                        if (beginDate < currentDate && currentDate < endDate) {
                            param.callBack();
                        }
                    }
                }
            });
        },

        indexWomensDay: function () {
            womens.dayControl({callBack: function () {
                $$(".indexFundTitle").removeClass("bg-f9ac3a");
                $$(".indexFundTitle").addClass("bg-ff86ae");
                $$("img[name='indexFundImg']").attr("src", "static/img/activity/womensDay/iv2.png");

                $$(".indexPlanTitle").removeClass("bg-f9ac3a");
                $$(".indexPlanTitle").addClass("bg-ff86ae");
                $$("img[name='indexPlanImg']").attr("src", "static/img/activity/womensDay/iv3.png");

                $("img[name='womensDayTopImg']").show();
                $("div[name='indexPlanDiv1']").show();

                $("span[name='fundApr']").parent().addClass("font-ff86ae");
                $("span[name='fundApr']").parent().removeClass("font-3f9bff");
                $("span[name='fund_aprActivity']").addClass("font-ff86ae");
                $("span[name='fund_aprActivity']").removeClass("yellow-color");

                $(".mobile_onl").addClass("bg-ff86ae");
                $(".mobile_onl").html("女神节活动");

                $(".indexPlan #indexPlanApr").parent().addClass("font-ff86ae");
                $(".indexPlan #indexPlanApr").parent().removeClass("font-3f9bff");
            }});
        },

        fundUnInvested:function(){
            womens.dayControl({callBack: function () {
                $$(".fund-womenDay").css("background","url(static/img/activity/womensDay/love.png) no-repeat");
                $$(".fund-womenDay").css("background-size","70px");
                var binding = [
                     {
                         element:'.fund-womenDay',
                         event:'click',
                         handler:womens.toFundWomensDay
                     }
                 ];
                appFunc.bindEvents(binding);
                $$(".fund-double11").hide();
                $$(".fund-womenDay").show();
            }});
        },
        toFundWomensDay:function(){
            GS.loadPage('activity/womensDay.html');
        },
        fundInvested:function() {
            womens.dayControl({callBack: function () {
                $$(".fund-womenDay1").css("background","url(static/img/activity/womensDay/love.png) no-repeat");
                $$(".fund-womenDay1").css("background-size","70px");
                $$(".fund-womenDay1").css("margin-top","10px");

                var binding = [
                     {
                         element:'.fund-womenDay1',
                         event:'click',
                         handler:womens.toFundWomensDay
                     }
                 ];
                appFunc.bindEvents(binding);
                $$(".fund-moneybag").hide();
                $$(".fund-womenDay1").show();
            }});
        },
        planDetail:function() {
            womens.dayControl({callBack: function () {
                $("#planDetailContent").parent().append("<img src='static/img/activity/womensDay/li.png' width='30' style='position: absolute;right: 30px;top: 55px;'/>");
            }});
        },
        financesList:function() {
            womens.dayControl({callBack: function () {
                 $("img[name='financesListLogo']").parent().html("<img src='static/img/activity/womensDay/li.png' width='25px' height='80px' />");
             }});
        },
        planTender:function() {
            womens.dayControl({callBack: function () {
                $("#planTenderContent").parent().append("<img src='static/img/activity/womensDay/li.png' width='30' style='position: absolute;right: 30px;top: 55px;'/>");
             }});
        }
    };

    return womens;
});
