<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%
    String pay_amount = request.getParameter("money");
%>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, user-scalable=no"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>提现成功</title>
    <style>
        .button.button-51 {
            font-size: 20px;
            height: 50px;
            line-height: 50px;
            background: #3f9bff;
            border-color: #3f9bff;
        }
        .button.active {
            background: #007aff;
            color: #fff;
        }
        .button {
            border: 1px solid #007aff;
            color: #007aff;
            text-decoration: none;
            text-align: center;
            display: block;
            border-radius: 5px;
            line-height: 27px;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
            -webkit-appearance: none;
            -moz-appearance: none;
            -ms-appearance: none;
            appearance: none;
            background: 0 0;
            padding: 0 10px;
            margin: 0;
            height: 29px;
            white-space: nowrap;
            position: relative;
            overflow: hidden;
            text-overflow: ellipsis;
            font-size: 14px;
            font-family: inherit;
            cursor: pointer;
        }
    </style>
</head>
<body style="padding: 0.7rem;font-size: 0.7rem;">
<div style="    text-align: center;">
    <img src="${path}/static/img/xxd/drawmoneySuccess.png" style="    margin-top: 30%;
    width: 65%;"/>
</div>
<div style="    text-align: center;
    margin: 40px 0 40px 0;">
    <div style="    font-size: 0.9rem;
    color: #007aff;
    margin-bottom: 20px;">恭喜你，提现成功！</div>
    <div style="    font-size: 0.9rem;
    color: #007aff;">成功提现：<span id="money"><%=pay_amount%></span>元</div>
</div>
<a href="#" class="button button-51 active" id="confirm">确认</a>
</body>
<script src="${path}/static/lib/jquery/jquery1.11.1.min.js"></script>
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
    $(document).ready(function () {
        $("#confirm").on('click',function(){
            window.location.href="${path}/#!/static/html/account/drawmoney.html?path=account";
        });
    });

</script>
</html>

