<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, user-scalable=no"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>引导下载页</title>
    <style>
        * {
            font-family: Microsoft YaHei;
        }

        p {
            margin: 0;
        }

        .left {
            float: left;
        }

        .div_spe {
            padding-left: 1%;
            padding-right: 1%;
        }

        .app_name {
            color: #424141;
            font-size: 20px;
        }

        .app_intr {
            color: #6a6a6a;
            font-size: 13px;
            padding-top: 2%;
        }

        .down_btn {
            color: #fff;
            font-size: 20px;
            background-color: #e91d26;
            width: 100%;
            display: inline-block;
            text-align: center;
            margin-top: 2%;
            line-height: 50px;
        }

        .app_des {
            color: #212121;
            font-size: 16px;
            padding-left: 2%;
            border-left: 4px solid #2096e9;
        }

        .app_newer {
            color: #2096e9;
            font-size: 15px;
            margin-top: 2%;
            margin-bottom: 1%;
            margin-left: -8px;

        }

        .newer_info, .fol_heart {
            font-size: 14px;
            color: #7c7b7b;
            margin-top: 2%;
        }

        .newer_info p, .fol_heart p {
            margin-bottom: 1%;
        }

        .to_all_text {
            color: #2096e9;
            font-size: 13px;
            padding-top: 2%;
            padding-bottom: 2%;
            text-align: center;
            border-top: 1px solid #2096e9;
            border-bottom: 1px solid #2096e9;
            cursor: pointer;
        }

        .pl25 {
            padding-left: 8%;
        }

        .change_div {
            position: relative;
            height: 350px;
            margin-top: 2%;
            margin-bottom: 2%;
        }

        .change {
            position: absolute;
            margin: 0 auto;
        }

        .change li {
            display: inline-block;
        }

        .change li img {
            width: 142px;
            height: 252px;
        }

        .disnone {
            /* display: none;*/
        }

        .disblock {
            display: block;
        }

        .swipe {
            overflow: hidden;
            position: relative;
            width: 100%;
            height: 330px;
        }

        .swipe ul {
            overflow: hidden;
        }

        .swipe li div, .swipe div div div {
            padding: 50px 10px;
            background: #1db1ff;
            font-weight: bold;
            color: #fff;
            font-size: 20px;
            text-align: center;
        }

        #slider4 {
            line-height: 0;
            text-align: center;
        }

        #slider4 ul {
            -webkit-transition: left 800ms ease-in 0;
            -moz-transition: left 800ms ease-in 0;
            -o-transition: left 800ms ease-in 0;
            -ms-transition: left 800ms ease-in 0;
            transition: left 800ms ease-in 0;
        }

        #pagenavi a.active {
            color: red;
            background: #007aff;
        }

        #slider li {
            width: 300px;
            float: left;
        }

        #slider li:first-child {
            width: 100px;
            margin: 10px 40px;
        }

        #slider li:last-child {
            width: 500px;
            border: 3px solid #111;
        }

        #slider li:nth-child(2) {
            width: 200px;
        }

        #slider li:nth-child(3) {
            width: 300px;
        }

        #slider li:nth-child(4) {
            width: 400px;
        }

        .swiper-pagination-bullet {
            width: 8px;
            height: 8px;
            display: inline-block;
            border-radius: 100%;
            background: #000;
            opacity: .2;
        }

    </style>
    <script type="text/javascript" src="../static/lib/jquery/jquery1.11.1.min.js"></script>
    <script type="text/javascript" src="../static/lib/touchslider.js"></script>
<body>
<div class="div_spe">
    <div class="top" style="margin-top: 20px;">
        <div class="left"><img src="../static/img/activity/dowApp/logo.png" width="80" height="80"></div>
        <div class="left pl25">
            <p class="app_name">_理财</p>

            <p class="app_intr">168.48万次下载|14.1MB</p>

            <p class="app_intr">官方
                 <span>
                    <img src="../static/img/activity/dowApp/star.png" width="15" height="15">
                    <img src="../static/img/activity/dowApp/star.png" width="15" height="15">
                    <img src="../static/img/activity/dowApp/star.png" width="15" height="15">
                    <img src="../static/img/activity/dowApp/star.png" width="15" height="15">
                    <img src="../static/img/activity/dowApp/star.png" width="15" height="15">
                </span>
            </p>
        </div>
    </div>
    <a class="down_btn" style="margin-top: 20px;">立即下载</a>

    <div class="change_div">

        <div class='swipe'>
            <ul id='slider4'>
                <li style='display:block;'><a href="#"><img src="../static/img/activity/dowApp/0.jpg" alt=""
                                                            height="300px"/></a></li>
                <li><img src="../static/img/activity/dowApp/1.jpg" alt="" height="300px"/></li>
                <li><img src="../static/img/activity/dowApp/2.jpg" alt="" height="300px"/></li>
                <li><img src="../static/img/activity/dowApp/3.jpg" alt="" height="300px"/></li>
            </ul>
        </div>

        <div id="pagenavi" style="text-align:center">
            <a href="#" class="swiper-pagination-bullet active"></a>
            <a href="#" class="swiper-pagination-bullet"></a>
            <a href="#" class="swiper-pagination-bullet"></a>
            <a href="#" class="swiper-pagination-bullet"></a>
        </div>
    </div>

    <div class="app_des">应用描述</div>
    <!--新手有礼-->
    <div class="newer_info">
        <div class="app_newer">【欢乐周三送豪礼】</div>
        <p>每周三10:00-23:59投资_有机会赢取丰富奖品</p>
        <p>〖_〗——<span id="xpan_text"></span> 任选</p>
        <p>最终收益以实际收益为准</p>
    </div>
    <p class="to_all_text">展开全文</p>
    <div id="conent" style="display: none;">
        <div class="fol_heart disnone">
            <div class="app_newer">【劲爆收益限时抢】</div>
            <p>〖_〗——<span id="monthFinance_closeTerm">0</span>天尽享<span id="monthFinance_apr">0</span>%预期年化，每天<span id="monthFinance_startDate"></span>-<span id="monthFinance_endDate"></span>限时抢</p>
        </div>
        <div class="fol_heart disnone">
            <div class="app_newer">【随心理财】</div>
            <p>1、〖新手专享〗注册送礼--注册即享108元现金红包</p>
            <p>2、〖活期〗_--<span id="fund_lowestTender">0</span>元起投，手机端年化<span id="fund_apr">0</span>%</p>
            <p>3、〖定期〗七天大胜--预期年化<span id="7d_apr">0</span>%，<span id="7d_closeTerm">0</span>天可取</p>
        </div>
        <div class="fol_heart disnone">
            <div class="app_newer">【放心保障】</div>
            <p>1、170名高学历风控团队，70%以上为硕士及以上学历，高管主要来自国内外知名的银行、信托、证券等大型金融机构</p>
            <p>2、稳健运作第4年，累计成交额<span id="totalMoney">0</span>亿元，累计为出借人赚取<span id="userTotalMoney">0</span>亿元</p>
        </div>
        <div class="fol_heart disnone">
            <div class="app_newer">【关于我们】</div>
            <p>_使命：用公平金融的理念，致力于提高每个人的生活品质</p>
            <p>客服专线：4000-169-521</p>
            <p>总部地址：上海市虹口区四川北路859号中信广场28F</p>
            <p>微信号：xinxindaicom</p>
            <p>_（上海）金融服务有限公司</p>
        </div>
    </div>

</div>
<div id="disabled1" style="
    display: none;
    width: 100%;
    height: 100%;
    background-color: #000;
    opacity: .8;
    position: fixed;
    left: 0;
    top: 0;
    z-index: 1000;">
</div>
<div id="disabled2" style="
    display: none;
    position: fixed;
    width: 100%;
    left: 15%;
    top: 5%;
    z-index: 1001;
">
    <img src="../static/img/xxd/download/live.png" style="width: 70%;"/>
</div>
<script>
    $(document).ready(function(){
        sevenDays();
        fund();
        monthFinance();
        xplan();
        getInforMap();
    });

    function getInforMap(){
        $.ajax({
             type: 'GET',
             url: '../getInforMap.do',
             data: {},
             dataType: 'json',
             timeout: 8000,
             success: function (data) {
                 $("#totalMoney").html(ForDight(data.inforMap.TOTAL_TRADE[0].nvalue_special/100000000,2));
                 $("#userTotalMoney").html(ForDight(data.inforMap.TOTAL_INCOME[0].nvalue_special/100000000,2));
             }
         });
    }

    function ForDight(Dight,How){
        Dight = Math.round(Dight*Math.pow(10,How))/Math.pow(10,How);
        return Dight;
    }

    function xplan(){
        var xplanArr = [];
        var xplan = getXplan(1);
        if(xplan != undefined){
            xplanArr.push("1个月（预期年化"+xplan.MINAPR+"%）");
        }

        xplan = getXplan(3);
        xplanArr.push("3个月（预期年化"+xplan.MINAPR+"%）");

        xplan = getXplan(6);
        xplanArr.push("6个月（预期年化"+xplan.MINAPR+"%）");

        xplan = getXplan(12);
        xplanArr.push("12个月（预期年化"+xplan.MINAPR+"%）");

        $("#xpan_text").html(xplanArr.join(","));
    }

    function getXplan(closeTerm){
        var b ;
        $.ajax({
            type: 'GET',
            url: '../xplan/schemeList.do',
            data: {
                currentPage: 1,
                closeTerm: closeTerm,
                pageSize: 1
            },
            dataType: 'json',
            timeout: 8000,
            async:false,
            success: function (data) {
                 var list = data.listData;
                 b = list[0];
            }
        });
        return b;
    }

    function monthFinance(){
        $.ajax({
            type: 'GET',
            url: '../monthFinance/getProudctDetail.do',
            data: {},
            dataType: 'json',
            timeout: 8000,
            success: function (result) {
                var apr = result.data.apr;
                $("#monthFinance_apr").html(apr);

                var closeTerm = result.data.closeTerm;
                $("#monthFinance_closeTerm").html(closeTerm);

                var startTime = new Date(result.data.startDate.replace(/\-/g, "/"));
                var endTime = new Date(result.data.endDate.replace(/\-/g, "/"));

                $("#monthFinance_startDate").html(toFixedWidth(startTime.getHours(),2)+":"+toFixedWidth(startTime.getMinutes(),2));
                $("#monthFinance_endDate").html(toFixedWidth(endTime.getHours(),2)+":"+toFixedWidth(endTime.getMinutes(),2));
            }
        });
    }

    function toFixedWidth(value, length) {
        var result = "00" + value.toString();
        return result.substr(result.length - length);
    }

    function fund(){
        $.ajax({
            type: 'GET',
            url: '../fund/selectFundInfo.do',
            data: {},
            dataType: 'json',
            timeout: 8000,
            success: function (data) {
                var apr = data.fundApr.apr;
                var floatApr = data.fundApr.floatApr;
                $("#fund_apr").html(apr+floatApr);

                $("#fund_lowestTender").html(data.fund.lowestTender);
            }
        });
    }

    function sevenDays(){
        $.ajax({
            type: 'GET',
            url: '../sevenDays/selectSevenDaysInfo.do',
            data: {},
            dataType: 'json',
            timeout: 8000,
            success: function (data) {
                var apr = data.sevenDays.apr;
                var floatApr = data.sevenDays.floatApr;
                $("#7d_apr").html(apr + floatApr);

                var closeTerm = data.sevenDays.closeTerm;
                $("#7d_closeTerm").html(closeTerm);
            }
        });
    }

    function ios() {
        var ua = navigator.userAgent;
        var ipad = ua.match(/(iPad).*OS\s([\d_]+)/);
        var ipod = ua.match(/(iPod)(.*OS\s([\d_]+))?/);
        var iphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/);
        if (ipad || iphone || ipod) {
            return true;
        } else {
            return false;
        }
    }
    function isWeixin() {
        var ua = navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == "micromessenger") {
            return true;
        } else {
            return false;
        }
    }
    $("#disabled1").click(function () {
        $("#disabled1").hide();
        $("#disabled2").hide();
    });
    $("#disabled2").click(function () {
        $("#disabled1").hide();
        $("#disabled2").hide();
    });
    $(".down_btn").click(function () {
        if (isWeixin()) {
            $("#disabled1").show();
            $("#disabled2").show();
        } else {

            if (ios()) {
                $.ajax({
                    type: 'GET',
                    url: '../getCache.do',
                    data: {
                        key: 'APP_IOS_DOWNLOAD'
                    },
                    async: false,
                    dataType: 'json',
                    timeout: 8000,
                    success: function (resp) {
                        window.location.href = resp.value;
                    }
                });
            } else {
                window.location.href = 'http://www.xinxindai.com/static/download/${channel}_release_new.apk';
            }
        }
    });

    $(".to_all_text").click(function () {
        $(this).hide();
        $("#conent").show("slow");
    });
    var active = 0,
            as = document.getElementById('pagenavi').getElementsByTagName('a');
    for (var i = 0; i < as.length; i++) {
        (function () {
            var j = i;
            as[i].onclick = function () {
                t4.slide(j);
                return false;
            }
        })();
    }

    var t4 = new TouchSlider('slider4', {duration: 300, interval: 3000, direction: 0, autoplay: true, align: 'left', mousewheel: false, mouse: true, fullsize: true});
    t4.on('before', function (m, n) {
        as[m].className = 'swiper-pagination-bullet';
        as[n].className = 'swiper-pagination-bullet active';
    })
</script>
</body>