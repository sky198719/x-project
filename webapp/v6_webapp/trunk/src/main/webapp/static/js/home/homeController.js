/**
 * 主页
 */
define([ 'zeptoMd5', 'js/home/homeView','js/utils/date','js/utils/animateNumber','js/home/homeAnimate',
    'js/activity/womensDay','js/utils/dayController','js/common/productUtil','js/utils/download',
    'js/account/openOnAccount'],
    function (zeptoMd5, HomeView,DateHandle,animateNumber,homeAnimate,womensDay,dayController,productUtil,download,openOnAccount) {
    var enableFlag = { monthFinance: 0, stepUpward: 0, xyb: 0, yyp: 0 };	//0:开抢之前；1:开抢；2:抢购结束
    var clickType = { monthFinance: 0, stepUpward: 0, xyb: 0, yyp: 0 };	//0:DIV；1:BUTTON
    var stepUpwardInfo = {};
    var XSCP30TisMeetTheTermOfPurchase = false;
    var homeCtrl = {
        init: function () {
            stepUpwardInfo = {};
            //dayController.indexMemberDay();
            enableFlag = {monthFinance:0,stepUpward:0,xyb:0,yyp:0};
            clickType = {monthFinance:0,stepUpward:0,xyb:0,yyp:0};
            homeCtrl.loadData();
            download.show();
            openOnAccount.indexIsOpenOnAccount();
        },

        loadData:function(){
            homeCtrl.bindEventAll();
            homeCtrl.indexBanner();
            //_
            homeCtrl.getMonthFinance();
            homeCtrl.getStepUpward();
            homeCtrl.property30();
            homeCtrl.initXplanSwiper();
            homeCtrl.getYyp();

            homeCtrl.indexNotice();
            homeCtrl.checkNewAnnounce();
        },

        indexNotice:function(){
            try {
                var sysConfig = GS.getSysConfig();
                var sw = sysConfig.indexNotice.switch;
                if(sw == undefined || sw == "false") {
                    return;
                }

                var notices = sysConfig.indexNotice.notices;
                if(notices != undefined && notices.length > 0){
                    var html = '<marquee scrollamount="3">';
                    html += '<p>';
                    var tempNum = 0;
                    for(var i = 0; i < notices.length; i++) {
                        var n = notices[i];
                        if(n.switch == undefined || n.switch == "false") {
                            continue;
                        }
                        html += n.content;
                        tempNum ++;
                    }
                    html += '</p></marquee>';
                    $$('div[name="indexNotice"]').html(html);
                    if(tempNum > 0) {
                        $$('div[name="indexNotice"]').show();
                    }
                }
            } catch (e) {
            }
        },

        bindEventAll: function () {
            var bindings = [
                {
                    element: 'a[name="index_borrowListUrl"]',
                    event: 'click',
                    handler: homeCtrl.browseBorrowList
                },
                {
                    element: 'a[name="index_financeList"]',
                    event: 'click',
                    handler: homeCtrl.financeList
                },
                {
                    element: '.monthFinanceHome',
                    event: 'click',
                    handler: homeCtrl.toMonthFinanceDetail
                },
                {
                    element: '.monthFinanceHome #monthFinanceHomeTenderBtn',
                    event: 'click',
                    handler: homeCtrl.toMonthFinanceTender
                },
                {
                    element:'button[name="index_7dJoin"]',
                    event:'click',
                    handler:homeCtrl.sevenDaysJoin
                },
                {
                    element: 'div[name="index_sevenDays"]',
                    event: 'click',
                    handler: homeCtrl.sevenDaysEntrance
                },
                {
                    element: '.indexPlan',
                    event: 'click',
                    handler: homeCtrl.toXybDetail
                },
                {
                    element: '.indexPlan #indexPlanTenderBtn',
                    event: 'click',
                    handler: homeCtrl.toXybTender
                },
                {
                    element:'span[name="home_help"]',
                    event:'click',
                    handler:homeCtrl.help
                },
                {
                    element:'.stepUpwardHome',
                    event:'click',
                    handler:homeCtrl.toStepUpwardDetail
                },
                {
                    element: '.stepUpwardHome #stepUpwardHomeTenderBtn',
                    event: 'click',
                    handler: homeCtrl.toStepUpwardTender
                }
            ];
            HomeView.render({
                bindings: bindings
            });
        },


        sameWeek:function(now){
            var oneDayTime = 1000*60*60*24;
            var now_other =parseInt(now.getTime()/oneDayTime);
            return parseInt((now_other+4)/7);
        },
        //检查是否有新公告
        checkNewAnnounce:function(){
            var hasNewAnnounce = false;

            req.callJSON({
                url: "announce/getList.do",
                data: {
                    "currentPage": 1,
                    "pageSize": 10,
                    "type":"1"
                },
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    try{
                        if(data.resultCode == 0){
                            var list = data.data;
                        }
                        if (list != null && list != "") {
                            var hasRead = localStorage.hasRead;
                            if(hasRead == undefined || hasRead == null){
                                hasRead = [];
                            }else{
                                hasRead = JSON.parse(localStorage.hasRead)
                            }
                            for (var i = 0; i < list.length; i++) {
                                var al = list[i];
                                var isNew = true;
                                for(var j = 0; j < hasRead.length ; j++ ){
                                    if(hasRead[j] == al.id){
                                        isNew = false;
                                        break;
                                    }
                                }
                                if(isNew == false){
                                    continue;
                                }
                                hasNewAnnounce = true;
                                if(hasNewAnnounce == true){
                                    break;
                                }
                            }
                            if(hasNewAnnounce){
                                $$(".indexMenuIcon").attr("src","static/img/xxd/panel_newAnnounce.png");
                            }else{
                                $$(".indexMenuIcon").attr("src","static/img/xxd/panel.png");
                            }
                        }

                    }catch (e) {
                        xxdApp.hideIndicator();
                        console.log("获取公告活动失败，请稍后重试...");
                    }
                },
                error: function(xhr, type){
                    console.log("获取公告活动失败,ajax error...");
                }
            });
        },

        help:function(){
            GS.loadPage("common/help.html");
        },

        //首页banner
        indexBanner: function () {
            req.callPost({
                url: 'indexppt.do',
                data: {
                    bannerType: 6
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    if (result != null) {
                        HomeView.indexBanner(result);
                    }
                }
            });
        },
        /**
         * 跳转到散标列表
         */
        browseBorrowList: function () {
            GS.loadPage("borrow/borrowListV2.html");
            xxdApp.closeModal('.popover');
        },

        /** 热门理财列表 */
        financeList: function () {
            GS.loadPage("popular/financesList.html?path=popular");
        },

        /** _ */
        planList: function () {
            GS.loadPage("plan/planSwiper.html?path=plan");
        },

        /** _ */
        initXplanSwiper: function () {
            req.callJSON({
                url: 'xplan/getSchemeForNewIndex.do',
                dataType: 'json',
                indicator: true,
                success: function (data) {
                    if (data.schemeInfo != null && data.schemeInfo != "") {
                        //HomeView.indexXyb(data);
                        var schemeInfo = data.schemeInfo;
                        $$('.indexPlan #indexPlanId').val(schemeInfo.SCHEMEID);
                        var apr = schemeInfo.MINAPR;
//			            $$('.indexPlan #indexPlanApr').html(apr);
                        animateNumber.animate({
                            from: 0,
                            to: apr,
                            steps: 10,
                            intervalNumber: 100,
                            precision: 2,
                            isFloat: true,
                            callBack: function (value) {
                                var values = new String(value).split('.');
                                var html = values[0];
                                if(values.length > 1 && values[1] != '00') {
                                    html +='<span class="font12 font-blue">.' + values[1] + '</span>';
                                }
                                $$(".indexPlan #indexPlanApr").html(html);
                            }
                        });

                        var increaseApr =  schemeInfo.WEBAPP;
                        if(increaseApr != undefined && increaseApr != null && increaseApr > 0) {
                            $$(".indexPlan .increaseApr").html("+" + increaseApr + "%");
                            $$(".indexPlan .increaseApr").css("display","inline");
                        }

                        $$('.indexPlan #indexPlanCloseTerm').html(schemeInfo.CLOSETERM);
                        $$('.indexPlan #indexPlanCloseTerm').attr("data-pname",schemeInfo.PNAME);
                        $$('.indexPlan #indexPlanCloseTerm').attr("data-type",schemeInfo.TYPE);

                        var status = schemeInfo.schemeStatus;  // 0:待发布,1:预定期,2:支付期,3:开放期,4:锁定期,5:退出,6:撤销,7:等待公开加入
                        //抢购时间
                        //var startTime = schemeInfo.OPENSALEBEGIN.replace(/\-/g, "/");
                        //var startTimeHHmm = DateHandle.formatDate('HH:mm',startTime);
                        //$$(".indexPlan #startTime").html(startTimeHHmm);
                        //是否已满额
                        if(schemeInfo.REMACOUNT <= 0) {
                            //显示已抢完
                            $$(".indexPlan #indexPlanTenderBtn").html('已抢光').css("background-color","#cccccc");
                            enableFlag.xyb = 2;
                        }else{
                            if (status == 0 || status == 7) {
                                $$(".indexPlan #indexPlanTenderBtn").html('等待发售').css("background-color","#cccccc").css("color","#666");
                                enableFlag.xyb = 0;
                            } else if (status == 1 || status == 2 || status == 3) {
                                $$(".indexPlan #indexPlanTenderBtn").html('立即抢购');
                                enableFlag.xyb = 1;
                            } else {
                                $$(".indexPlan #indexPlanTenderBtn").html('已抢光').css("background-color","#cccccc");
                                enableFlag.xyb = 2;
                            }
                        }


                        try {
                            var bidType = schemeInfo.BIDTYPE;
                            if(2 == bidType) {
                                req.callJSON({
                                    url: 'product/activityLabel.do',
                                    data: {
                                        productId:schemeInfo.SCHEMEID
                                    },
                                    dataType: 'json',
                                    success:function(result1) {
                                        if(result1.code == 200000 && result1.data != undefined && result1.data.data != undefined) {
                                            var remark = result1.data.data.remark;
                                            if(remark.length > 7) {
                                                $$(".activityLabel_xyb").html("<marquee  scrollamount='1' style='font-size: 12px;background-color: #ff7365;padding: 2px 5px;width: 79%;text-align: center;'>"+remark+'</marquee>');
                                            } else {
                                                $$(".activityLabel_xyb").html("<span style='font-size: 12px;background-color: #ff7365;padding: 2px 5px;width: 79%;text-align: center;'>"+remark+'</span>');
                                            }

                                            $$(".activityLabel_xyb").show();
                                        }
                                    }
                                });
                            }
                        }catch (e) {
                            console.log(e);
                        }
                    }
                    //homeCtrl.planDetailEvent(data);
                }
            });
        },

        /** _ */

        toTednerYyp:function(){
            var btn =  $$(".yypIndex .home-ul button");
            var yypId = btn.data("yypId");
            var status = btn.data("status");
            if(status == 2) {
                GS.loadPage("yyp/yypTender.html?yypId="+yypId);
            }
            //阻止事件冒泡
            event.stopPropagation();
        },

        toYypDetail:function(){
            var yypId = $$(".yypIndex .home-ul").data("yypId");
            GS.loadPage("yyp/yypDetails.html?yypId="+yypId);
        },

        getYyp:function(){
            var yyp;
            req.callJSON({
                url: 'product/getWebappProduct.do',
                data:{
                    pCode: "YYP",
                    currentPage:1,
                    pageSize:40
                },
                dataType: 'json',
                // async:false,

                success: function (result) {
                    if(result.code == 200000) {
                        var list = result.data.items;

                        var yyp;
                        for (var i = 0; i < list.length; i++) {
                            var b = list[i];
                            if(b.status==2){
                                yyp=b;
                                break;
                            }else{
                                // yyp=b;
                            }
                        }
                        if(yyp==null||yyp.length<=0){
                            for (var i = 0; i < list.length; i++) {
                                var b = list[i];
                                if(b.status==1){
                                    yyp=b;
                                    break;
                                }else{
                                    // yyp=b;
                                }
                            }
                        }
                        if(yyp == null) {
                            return;
                        }
                        //$$(".yypIndex #indexYypApr").html(yyp.apr);
                        animateNumber.animate({
                            from: 0,
                            to: yyp.apr,
                            steps: 10,
                            intervalNumber: 100,
                            precision: 2,
                            isFloat: true,
                            callBack: function (value) {
                                var values = new String(value).split('.');
                                var html = values[0];
                                if(values.length > 1 && values[1] != '00') {
                                    html +='<span class="font12 font-blue">.' + values[1] + '</span>';
                                }
                                $$(".yypIndex #indexYypApr").html(html);
                            }
                        });
                        $$(".yypIndex #indexYypCloseTerm").html(yyp.terms);
                        var yypBtn = $$(".yypIndex #indexYypTenderBtn");
                        var status = yyp.status;
                        if(status == 1) {
                            yypBtn.css("background","#cccccc");
                            yypBtn.html("等待发售");
                        } else if(status == 2) {
                            yypBtn.html("立即抢购");
                            if(yyp.remAccount == 0) {
                                yypBtn.css("background","#cccccc");
                                yypBtn.html("已抢光");
                            }
                        } else if(status == 3){
                            yypBtn.html("收益中");
                            yypBtn.css("background","#cccccc");
                        }

                        $$(".yypIndex .home-ul").data("yypId",yyp.id);
                        var btn = $$(".yypIndex .home-ul button");
                        btn.data("yypId",yyp.id);
                        btn.data("status",yyp.status);

                        var bindEvent = [
                            {
                                element: '.yypIndex .home-ul',
                                event: 'click',
                                handler: homeCtrl.toYypDetail
                            },
                            {
                                element: '.yypIndex .home-ul button',
                                event: 'click',
                                handler: homeCtrl.toTednerYyp
                            }
                        ];
                        appFunc.bindEvents(bindEvent);
                        $$(".yypIndex").show();

                        try {
                            var bidType = yyp.bidType;
                            if(2 == bidType) {
                                req.callJSON({
                                    url: 'product/activityLabel.do',
                                    data: {
                                        productId:yyp.id
                                    },
                                    dataType: 'json',
                                    success:function(result1) {
                                        if(result1.code == 200000 && result1.data != undefined && result1.data.data != undefined) {
                                            var remark = result1.data.data.remark;
                                            if(remark.length > 7) {
                                                $$(".activityLabel_yyp").html("<marquee  scrollamount='1' style='font-size: 12px;background-color: #ff7365;padding: 2px 5px;width: 79%;text-align: center;'>"+remark+'</marquee>');
                                            } else {
                                                $$(".activityLabel_yyp").html("<span style='font-size: 12px;background-color: #ff7365;padding: 2px 5px;width: 79%;text-align: center;'>"+remark+'</span>');
                                            }

                                            $$(".activityLabel_yyp").show();
                                        }
                                    }
                                });
                            }
                        }catch (e) {
                            console.log(e);
                        }
                    }
                }
            });
            return yyp;
        },

        property30: function () {
            req.callGet({
                url: 'product/getWebappProduct.do',
                data: {
                    pCode:'XSCP30T'
                },
                dataType: 'json',
                indicator: true,
                success: function (result) {
                    if(result.code == 200000) {
                        XSCP30TisMeetTheTermOfPurchase = result.data.isMeetTheTermOfPurchase;
                        if(XSCP30TisMeetTheTermOfPurchase != undefined && XSCP30TisMeetTheTermOfPurchase != true) {
                            $$("div[name='index_sevenDays']").hide();
                            return;
                        }

                        var data = result.data.items;
                        if(data == undefined) {
                            $$("div[name='index_sevenDays']").hide();
                            return;
                        }

                        var apr = data.apr;
                        animateNumber.animate({
                            from: 0,
                            to: apr,
                            steps: 10,
                            intervalNumber: 100,
                            precision: 2,
                            isFloat: true,
                            callBack: function (value) {
                                var values = new String(value).split('.');
                                var html = values[0];
                                if(values.length > 1 && values[1] != '00') {
                                    html +='<span class="font12 font-blue">.' + values[1] + '</span>';
                                }
                                       $$("span[name='7dApr']").html(html);
                            }
                        });
                        var floatApr = data.floatApr;
                        if(floatApr!=null&&floatApr!=undefined) {
                            $$("span[name='7d_floatApr']").html("+"+floatApr+"%");
                            $(".newhandActivityHome").show();
                        }
                        $(".newhand_add_apr").parent().show();
                        $$("span[name='7d_floatApr']").attr("data-deployId",data.id);

                        var leastAmount = data.lowestTender;
                        $$("span[name='7d_leastAmount']").html(leastAmount);
                        var closeTerm = data.period;
                        animateNumber.animate({
                            from: 0,
                            to: closeTerm,
                            precision: 0,
                            intervalNumber: 100,
                            steps: 10,
                            isFloat: true,
                            callBack: function (value) {
                                $$("span[name='7d_closeTerm']").html(value);
                            }
                        });

                        var status = data.status;

                        if(status == 1) {
                            homeCtrl.disable7dButton({value: '等待发售'});
                        } else if(status == 2) {
                            homeCtrl.recovery7dButton({value:'立即抢购'});
                        } else if(status == 3) {
                            homeCtrl.disable7dButton({value:'已售罄'});
                        } else {
                            homeCtrl.disable7dButton({value:'已结束'});
                        }
                        /*var currentDate = new Date(Date.parse(DateHandle.formatDate('yyyy-MM-dd HH:mm:ss',new Date(result.serverTime)).replace(/-/g,"/")));
                        var sd = new Date(data.openTime);
                        var ed = new Date(data.closeTime);
                        if(currentDate>ed) {
                            homeCtrl.disable7dButton({value:'已结束'});
                        }
                        if(currentDate<sd) {
                            homeCtrl.disable7dButton({value:'等待发售'});
                        }
                        if(parseFloat(data.remAccount)<100) {
                            homeCtrl.disable7dButton({value:'已售罄'});
                        }
                        if (result.data.isMeetTheTermOfPurchase == false) {
                            homeCtrl.disable7dButton({value:'立即抢购'});
                        }*/
                    }
                }
            });
        },
        disable7dButton: function (options) {
            $$('button[name="index_7dJoin"]').removeClass("blue-btn");
            $$('button[name="index_7dJoin"]').addClass("blue-btn-disable");
            if(options.value != null)
                $$('button[name="index_7dJoin"]').html(options.value);
        },
        recovery7dButton: function (options) {
            $$('button[name="index_7dJoin"]').addClass("blue-btn");
            $$('button[name="index_7dJoin"]').removeClass("blue-btn-disable");
            if(options.value != null)
                $$('button[name="index_7dJoin"]').html(options.value);
        },
        /** 向后台查询是否可以投资七天大胜 */
        sevenDaysJoin: function (even) {
            if($$('button[name="index_7dJoin"]').hasClass("blue-btn-disable")) {
                return;
            }
            if (XSCP30TisMeetTheTermOfPurchase) {
                GS.loadPage("newHand/thirtyDaysTrade.html?path=newHand");
            }
            //阻止事件冒泡
            even.stopPropagation();
        },
        sevenDaysEntrance: function () {
            GS.loadPage("newHand/thirtyDaysDetail.html?path=newHand");
        },
        calculationIncome: function (money, apr, dayNum) {
            var resultMoney = 0;
            req.callGet({
                url:'fund/calculationIncome.do',
                data:{
                    money:money,
                    apr:apr,
                    dayNum:dayNum
                },
                dataType:'json',
                async:false,
                success:function(data){
                    if(data.resultCode == 0) {
                        resultMoney = data.resultMoney
                    }
                }
            });
            return resultMoney;
        },
        //_
        getMonthFinance: function () {
            enableFlag.monthFinance = 0;
            req.callJSON({
                url: 'product/getWebappProduct.do',
                data: {
                    pCode:"YJDJ"
                },
                dataType: 'json',
                success: function (result) {
                    try{
                        if(result.code == 200000 && result.data.items != undefined ){
                            var resultData = result.data.items;
                            var serverTime = result.serverTime.replace(/\-/g, "/");
                            var currentServerTime = new Date(serverTime).getTime();
                            //年化利率
                            var apr = resultData.apr;
                            // alert(apr);
                            animateNumber.animate({
                                from: 0,
                                to: apr,
                                steps: 10,
                                intervalNumber: 100,
                                precision: 2,
                                isFloat: true,
                                callBack: function (value) {
                                    var values = new String(value).split('.');
                                    var html = values[0];
                                    if(values.length > 1 && values[1] != '00') {
                                        html +='<span class="font12 font-blue">.' + values[1] + '</span>';
                                    }
                                    $$(".monthFinanceHome #monthFinanceApr").html(html);
                                }
                            });
                            //期限$$("body").attr("data-userId",result.user.userId);
                            $$(".monthFinanceHome #monthFinanceDays").html(resultData.period);
                            // alert(resultData.period);
                            //$$(".monthFinanceHome #monthFinanceDays").attr("data-terms",resultData.terms);
                            $$(".monthFinanceHome #monthFinanceDays").attr("data-deployId",resultData.deployId);
                            //起投金额
                            var tenderStartAmount = resultData.lowestTender;
                            $$(".monthFinanceHome #tenderStartAmount").html(tenderStartAmount);
                            //抢购时间
                            var startTime = new Date(resultData.openTime);
                            var endTime = new Date(resultData.closeTime);
                            var startTimeHHmm = DateHandle.formatDate('HH:mm',startTime);
                            $$(".monthFinanceHome #startTime").html(startTimeHHmm);
                            $$(".monthFinanceHome #endTime").html(DateHandle.formatDate('HH:mm',endTime));
                            //已到开始时间 未到结束时间
                            if(	currentServerTime >= new Date(startTime).getTime()
                                && currentServerTime <= new Date(endTime).getTime()){
                                //是否已满额
                                if (parseFloat(resultData.remAccount) == 0 || parseFloat(resultData.remAccount) < parseFloat(tenderStartAmount)) {
                                    //显示已抢完
                                    $$(".monthFinanceHome #monthFinanceHomeTenderBtn").html('本场结束').css("background-color","#cccccc");
                                    enableFlag.monthFinance = 2;
                                }else{
                                    //按钮
                                    $$(".monthFinanceHome #monthFinanceHomeTenderBtn").html("立即抢购");
                                    enableFlag.monthFinance = 1;
                                }
                            }else{
                                //已过结束时间
                                if(currentServerTime > new Date(endTime).getTime()){
                                    //显示已抢完
                                    $$(".monthFinanceHome #monthFinanceHomeTenderBtn").html('本场结束').css("background-color","#cccccc");
                                    enableFlag.monthFinance = 2;
                                }
                                //未到开始时间
                                else{
                                    //显示**开抢
                                    //$$(".monthFinanceHome #monthFinanceHomeTenderBtn").html(startTimeHHmm + '开抢').css("background-color","#cccccc");
                                    //显示等待发售
                                    $$(".monthFinanceHome #monthFinanceHomeTenderBtn").html('等待发售').css("background-color","#cccccc").css("color","#666");
                                    enableFlag.monthFinance = 0;
                                }
                            }

                            $$(".monthFinanceHome").show();
                        }else{
                            console.log("获取_产品信息失败，请稍后重试...");
                        }
                    }catch (e) {
                        console.log(e.message);
                        console.log("获取_产品信息失败，请稍后重试...");
                    }
                },
                error: function(xhr, type){
                    console.log("ajax error...");
                    console.log("获取_产品信息失败，请稍后重试...");
                }
            });
        },
        //步步高升
        getStepUpward: function () {
            if(productUtil.stepUpwardShowOrNot()){
                $$(".stepUpwardHome").show();
                req.callJSON({
                    url: 'product/getWebappProduct.do',
                    data: {
                        pCode:"BBGS"
                    },
                    dataType: 'json',
                    indicator: true,
                    success: function (result) {
                        try{
                            enableFlag.stepUpward = 0;
                            if(result.code == 200000){
                                var resultData = result.data.items;
                                stepUpwardInfo = resultData;
                                //年化利率
                                var floatapr = resultData.floatapr;
                                var marginTop = 0;

                                var minApr = parseFloat(resultData.minapr) + parseFloat(floatapr);
                                var maxApr = parseFloat(resultData.maxapr) + parseFloat(floatapr);
                                var aprLength = minApr.toString().length + maxApr.toString().length;
                                var screenWidth=window.screen.width;

                                if(aprLength > 6){
                                    $$(".stepUpwardHome .home_stepUpwardApr").css("font-size","18px").css("margin-top", 24 + marginTop + "px");
                                    if(screenWidth <= 360) {
                                        $$(".stepUpwardHome .home_stepUpwardApr").css("font-size","16px").css("margin-top",22 + marginTop + "px");
                                        $$(".stepUpwardHome .stepUpwardAprPercent").css("font-size","13px");
                                    }
                                }else if(aprLength > 4){
                                    $$(".stepUpwardHome .home_stepUpwardApr").css("font-size","20px").css("margin-top",20 + marginTop + "px");
                                    if(screenWidth <= 360) {
                                        $$(".stepUpwardHome .home_stepUpwardApr").css("font-size","19px").css("margin-top",20 + marginTop + "px");
                                        $$(".stepUpwardHome .stepUpwardAprPercent").css("font-size","13px");
                                    }
                                }else{
                                    $$(".stepUpwardHome .home_stepUpwardApr").css("margin-top",16 + marginTop + "px");
                                    if(screenWidth <= 360) {
                                        $$(".stepUpwardHome .home_stepUpwardApr").css("font-size","22px").css("margin-top",18 + marginTop + "px");
                                    }
                                }

                                animateNumber.animate({
                                    from: 0,
                                    to: minApr,
                                    steps: 10,
                                    intervalNumber: 100,
                                    precision: 2,
                                    isFloat: true,
                                    callBack: function (value) {
                                        var values = new String(value).split('.');
                                        var html = values[0];
                                        if(values.length > 1 && values[1] != '00') {
                                            html +='<span class="font12 font-blue">.' + values[1] + '</span>';
                                        }
                                        $$(".stepUpwardHome .stepUpwardMinApr").html(html);
                                    }
                                });
                                animateNumber.animate({
                                    from: 0,
                                    to: maxApr,
                                    steps: 10,
                                    intervalNumber: 100,
                                    precision: 2,
                                    isFloat: true,
                                    callBack: function (value) {
                                        var values = new String(value).split('.');
                                        var html = values[0];
                                        if(values.length > 1 && values[1] != '00') {
                                            html +='<span class="font12 font-blue">.' + values[1] + '</span>';
                                        }
                                        $$(".stepUpwardHome .stepUpwardMaxApr").html(html);
                                    }
                                });
                                //起投金额
                                var tenderStartAmount = resultData.userlowesttender;
                                $$(".stepUpwardHome .tenderStartAmount").html(tenderStartAmount);
                                $("#stepMinApr").val(resultData.minapr);
                                $("#stepId").val(resultData.stepid);
                                $("#stepMaxApr").val(resultData.maxapr);
                                //是否已满额
                                if (parseFloat(resultData.remaccount) == 0 || parseFloat(resultData.remaccount) < parseFloat(tenderStartAmount)) {
                                    //显示已抢完
                                    $$(".stepUpwardHome #stepUpwardHomeTenderBtn").html('本场结束').css("background-color","#cccccc");
                                    enableFlag.stepUpward = 2;
                                }else{
                                    $$(".stepUpwardHome #stepUpwardHomeTenderBtn").html("立即抢购");
                                    enableFlag.stepUpward = 1;
                                }

                                try {
                                    var serverTime = DateHandle.parseDate(result.serverTime).getTime();
                                    var activityStartDate = resultData.activityStartDate;
                                    var activityEndDate = resultData.activityEndDate;

                                    if(activityStartDate < serverTime && serverTime < activityEndDate ){
                                        req.callJSON({
                                            url: 'product/activityLabel.do',
                                            data: {
                                                productId:resultData.stepid
                                            },
                                            dataType: 'json',
                                            success:function(result1) {
                                                if(result1.code == 200000 && result1.data != undefined && result1.data.data != undefined) {
                                                    var remark = result1.data.data.remark;
                                                    if(remark.length > 7) {
                                                        $$(".activityLabel_step").html("<marquee  scrollamount='1' style='font-size: 12px;background-color: #ff7365;padding: 2px 5px;width: 79%;text-align: center;'>"+remark+'</marquee>');
                                                    } else {
                                                        $$(".activityLabel_step").html("<span style='font-size: 12px;background-color: #ff7365;padding: 2px 5px;width: 79%;text-align: center;'>"+remark+'</span>');
                                                    }

                                                    $$(".activityLabel_step").show();
                                                }
                                            }
                                        });
                                    }

                                }catch (e) {
                                    console.log(e);
                                }

                            }else{
                                console.log("获取步步高升产品信息失败，请稍后重试...");
                            }
                        }catch (e) {
                            console.log(e.message);
                            console.log("获取步步高升产品信息失败，请稍后重试...");
                        }
                    },
                    error: function(xhr, type){
                        console.log("ajax error...");
                        console.log("获取步步高升产品信息失败，请稍后重试...");
                    }
                });
            }
        },
        //_详情页
        toMonthFinanceDetail: function () {
            window.event? window.event.cancelBubble = true : e.stopPropagation();
        	if(clickType.monthFinance == 1){
        		clickType.monthFinance = 0;
        		return;
        	}
	        	try {
	        		var monthBtn = $$(".monthFinanceHome #monthFinanceHomeTenderBtn").html()
	            	if(monthBtn == '立即抢购'){
		        		//XXD_TRACK._trackEvent({category: "webapp_monthgold_in", action: "index_buy_once", label: "立即抢购", value: "", custval: "" });
		        		//GA部署
		        		var borrowId = $$(".monthFinanceHome #monthFinanceDays").attr("data-deployId");
		        		var timelimit = $$(".monthFinanceHome #monthFinanceDays").html();
		        		var apr = $$(".monthFinanceHome #monthFinanceApr").html();
		        		var terms = $$(".monthFinanceHome #monthFinanceDays").attr("data-terms");
		            	var name = "_-"+terms;
		            	var category = "_/"+apr+"%/"+timelimit+"天";
		            	product_click({id:borrowId,name:name,category:category,list:"首页大显身手"});
	            	}
	        	} catch (e) {}
        	
        	
        	
            GS.loadPage('monthFinance/monthFinanceDetails.html');
        },
        //_购买页
        toMonthFinanceTender: function () {
        	clickType.monthFinance = 1;
        	if(enableFlag.monthFinance == 0 || enableFlag.monthFinance == 2){
        		clickType.monthFinance = 0;
        		homeCtrl.toMonthFinanceDetail();
        	}else if(enableFlag.monthFinance == 1){
        		try {
	        		var monthBtn = $$(".monthFinanceHome #monthFinanceHomeTenderBtn").html()
	            	if(monthBtn == '立即抢购'){
	            		//XXD_TRACK._trackEvent({category: "webapp_monthgold_in", action: "index_buy_once", label: "立即抢购", value: "", custval: "" });
	            		//GA部署
		        		var bid = $$(".monthFinanceHome #monthFinanceDays").attr("data-deployId");
		        		var timelimit = $$(".monthFinanceHome #monthFinanceDays").html();
		        		var apr = $$(".monthFinanceHome #monthFinanceApr").html();
		        		var terms = $$(".monthFinanceHome #monthFinanceDays").attr("data-terms");
		            	var name = "_-"+terms;
		            	var category = "_/"+apr+"%/"+timelimit+"天";
		            	add_to_cart({id:bid,name:name,category:category});
	            	}
	        	} catch (e) {}
	        	
        		GS.loadPage('monthFinance/monthFinanceTender.html');
        	}
        },
        //步步高升详情页
        toStepUpwardDetail: function () {
        	if(clickType.stepUpward == 1){
        		clickType.stepUpward = 0;
        		return;
        	}
        	
        	try {
            		//XXD_TRACK._trackEvent({category: "webapp_step_in", action: "index_buy_once", label: "首页立即抢购", value: "", custval: "" });
            		//GA部署
	        		var stepId = $("#stepId").val();
	        		var stepMinApr = $("#stepMinApr").val();
	        		var stepMaxApr = $("#stepMaxApr").val();
	            	var stepName = "步步高升:步步高升";
	            	var category = "步步高升/"+stepMinApr+"%~"+stepMaxApr+"%/1个月";
	            	product_click({id:stepId,name:stepName,category:category,list:"首页步步高升"});
        	} catch (e) {}
        	
            GS.loadPage('stepUpward/stepUpwardDetail.html');
        },
        //步步高升购买页
        toStepUpwardTender: function () {
        	clickType.stepUpward = 1;
        	if(enableFlag.stepUpward == 0 || enableFlag.stepUpward == 2){
        		clickType.stepUpward = 0;
        		homeCtrl.toStepUpwardDetail();
        	}else if(enableFlag.stepUpward == 1){
        		if (!appFunc.isLogin()) {
	                xxdApp.loginScreen();
	                return;
	            }
        		var userStepAccount = productUtil.getUserStepUpwardInfo();
        		if(parseFloat(userStepAccount.remaCapitalTotal) >= parseFloat(stepUpwardInfo.userMostTender)){
	            	xxdApp.alert('您的个人可购买额度已满额', '提示');
	            	return;
	            }else{
	            	try {
	            		//XXD_TRACK._trackEvent({category: "webapp_step_in", action: "index_buy_once", label: "首页立即抢购", value: "", custval: "" });
	            		//GA部署
		        		var stepId = $("#stepId").val();
		        		var stepMinApr = $("#stepMinApr").val();
		        		var stepMaxApr = $("#stepMaxApr").val();
		            	var stepName = "步步高升:步步高升";
		            	var category = "步步高升/"+stepMinApr+"%~"+stepMaxApr+"%/1个月";
		            	add_to_cart({id:stepId,name:stepName,category:category});
	            	} catch (e) {}
	            	
	        		GS.loadPage('stepUpward/stepUpwardTender.html');
	            }
        	}
        },
        //_详情页
        toXybDetail: function () {
            if(enableFlag.xyb == 0){
                return;
            }
            if(clickType.xyb == 1){
                clickType.xyb = 0;
                return;
            }
            var planId = $$(".indexPlan #indexPlanId").val();
            var planBtn =  $$(".indexPlan #indexPlanTenderBtn").html();
           if(planBtn =='立即抢购'){
	            try {
	           	 //XXD_TRACK._trackEvent({category: "webapp_xplan_in", action: "index_now_join", label: "立即抢购", value: "", custval: "" });
	           	//GA部署
	           	 var apr = $$(".indexPlan #indexPlanApr").html();
	           	 var closeterm = $$('.indexPlan #indexPlanCloseTerm').html();
	           	 var pName ="";
	           	 var type = $$('.indexPlan #indexPlanCloseTerm').attr("data-type");
	           	 if(type == 1){
	           		  pName = "_3个月 - "+ $$('.indexPlan #indexPlanCloseTerm').attr("data-pname");
	           	 }else if(type == 2){
	           		 pName = "_6个月 - "+ $$('.indexPlan #indexPlanCloseTerm').attr("data-pname");
	           	 }else if(type == 3){
	           		 pName = "_12个月 - "+ $$('.indexPlan #indexPlanCloseTerm').attr("data-pname");
	           	 }else if(type == 4){
		    		 pName = "_1个月 - "+ $$('.indexPlan #indexPlanCloseTerm').attr("data-pname");
		    	 }
	              //	var apr = ((minApr != maxApr)?minApr+"~"+maxApr:minApr);
	            	var category = "_/"+apr+"%/"+closeterm+"个月";
	            	product_click({id:planId,name:pName,category:category,list:"首页_列表"});
	            } catch (e) {}
            }

            GS.loadPage('plan/planDetailsV2.html?planId=' + planId + "&path=plan");
        },
        //_购买页
        toXybTender: function () {
        	 var planBtn =  $$(".indexPlan #indexPlanTenderBtn").html();
        	if(enableFlag.xyb == 0){
        		return;
        	}
        	clickType.xyb = 1;
        	if(enableFlag.xyb == 2){
        		clickType.xyb = 0;
        		homeCtrl.toXybDetail();
        	}else if(enableFlag.xyb == 1){
        		var planId = $$(".indexPlan #indexPlanId").val();
        		 if(planBtn =='立即抢购'){
    			     try {
    			    	 //XXD_TRACK._trackEvent({category: "webapp_xplan_in", action: "index_now_join", label: "立即抢购", value: "", custval: "" });
    			    	 
    			    	//GA部署
    			    	 var apr = $$(".indexPlan #indexPlanApr").html();
    			    	 var closeterm = $$('.indexPlan #indexPlanCloseTerm').html();
    			    	 var pName ="";
    			    	 var type = $$('.indexPlan #indexPlanCloseTerm').attr("data-type");
    			    	 if(type == 1){
    			    		  pName = "_3个月 - "+ $$('.indexPlan #indexPlanCloseTerm').attr("data-pname");
    			    	 }else if(type == 2){
    			    		 pName = "_6个月 - "+ $$('.indexPlan #indexPlanCloseTerm').attr("data-pname");
    			    	 }else if(type == 3){
    			    		 pName = "_12个月 - "+ $$('.indexPlan #indexPlanCloseTerm').attr("data-pname");
    			    	 }else if(type == 4){
    			    		 pName = "_1个月 - "+ $$('.indexPlan #indexPlanCloseTerm').attr("data-pname");
    			    	 }
    			       //	var apr = ((minApr != maxApr)?minApr+"~"+maxApr:minApr);
    			     	var category = "_/"+apr+"%/"+closeterm+"个月";
    			     	add_to_cart({id:planId,name:pName,category:category});
    			     } catch (e) {}
            	 }
            	GS.loadPage('planTender/planTender.html?planId=' + planId);
        	}
        }
    };

    return homeCtrl;
});
