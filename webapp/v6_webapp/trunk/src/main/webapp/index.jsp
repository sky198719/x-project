<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta property="wb:webmaster" content="5ffead0c7bf3e589"/>
    <title>【新新贷官网-移动版】中国领先的P2P网贷,个人投资理财贷款,中小微企业投融资服务平台</title>
    <meta name="keywords" content="P2P 网贷,P2P 理财,投融资,P2P 贷款,无抵押小额贷款" />
    <meta name="description" content="新新贷是中国领先的P2P投资理财及借贷款平台。严格的风控体系及银行托管质保服务专款制度保障您资金安全。新手标7天期13%、活期7%+1%，秒杀标月进斗金、省心理财新元宝1个月起等优质理财产品；新商贷，新车贷，新房贷等专业贷款产品，满足您投资及贷款的不同需求。5秒注册即可获赠新手红包108元。P2P理财、P2P贷款、小额投资、无抵押贷款信用贷款，就上新新贷！" />
    <!-- Path to Framework7 Library CSS-->
    <link rel="stylesheet" href="static/css/framework7/framework7.min.css?v=${sv}">
    <link rel="stylesheet" href="static/css/framework7/framework7.upscroller.css?v=${sv}">
    <link rel="stylesheet" href="static/css/plugins/autocomplete/zepto.autocomplete.css?v=${sv}">
    <link rel="stylesheet" href="static/css/animate.min.css?v=${sv}">
    <!-- Path to your custom app styles-->
    <link rel="stylesheet" href="static/css/app.css?v=${sv}">
    <link href="static/img/favicon.ico" rel="shortcut icon">
    <script data-main="static/js/app" src="static/lib/require.min.js?v=${sv}" defer async="true"></script>
</head>
<body data-userId="${gauserId}" v="${sv}">
<!-- Views-->
<div class="views tabs toolbar-through">
    <div class="view load-view tab active">
        <div class="pages navbar-through">
            <!-- Page, data-page contains page name-->
            <div data-page="index" class="page indexPage" directory="index">
                <div class="spinner">
                    <div class="bounce1"></div>
                    <div class="bounce2"></div>
                    <div class="bounce3"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    (function (doc, win, fontSize) {
        var docEl = doc.documentElement,
                resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
                recalc = function () {
                    var clientWidth = docEl.clientWidth;
                    if (!clientWidth) {
                        return;
                    }
                    docEl.style.fontSize = fontSize * (clientWidth / 320) + 'px';
                };
        if (!doc.addEventListener) {
            return;
        }
        win.addEventListener(resizeEvt, recalc, false);
        doc.addEventListener('DOMContentLoaded', recalc, false);
    })(document, window, 16);
</script>
</html>