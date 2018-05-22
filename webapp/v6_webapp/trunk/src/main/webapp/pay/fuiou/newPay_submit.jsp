<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <title>富友支付</title>
</head>
<body>
<c:choose>
    <c:when test="${msg != null}">
        <div style="width:100%; height:120px; padding-top:30px; text-align: center; border-bottom: 1px solid #dedede; font-size: 16px; color: #666; font-weight: 300; background:#fff;">
            <label style="color: red;">${msg}</label>
        </div>
        <div style="margin-top:20px; width:100%; text-align:center;">
            <c:choose>
                <c:when test="${code != null && code == -1}">
                    <a href="${path}/#!/static/html/personal/personalInfo.html" style="width:40%; text-align:center; padding:10px 0; background:#2096e9; color:#fff; font-weight:900; font-size:18px; border-radius:6px; text-decoration:none; display:inline-block; margin:0 10px;">立即认证</a>
                </c:when>
                <c:otherwise>
                    <a href="${path}/#!/static/html/borrow/borrowListV2.html" style="width:40%; text-align:center; padding:10px 0; background:#2096e9; color:#fff; font-weight:900; font-size:18px; border-radius:6px; text-decoration:none; display:inline-block; margin:0 10px;">立即投标</a>
                    <a href="${path}/#!/static/html/account/topup.html?path=account" style="width:40%; text-align:center; padding:10px 0; background:#2096e9; color:#fff; font-weight:900; font-size:18px; border-radius:6px; text-decoration:none; display:inline-block; margin:0 10px;">继续充值</a>
                </c:otherwise>
            </c:choose>
        </div>
    </c:when>
    <c:otherwise>
        <section>
            <div class="form_warp">
                <form id="form" action="${payurl}" method="post">
                    <input type="hidden" id="mchnt_cd" name="mchnt_cd" value='${mchnt_cd}'/>
                    <input type="hidden" id="mchnt_txn_ssn" name="mchnt_txn_ssn" value='${mchnt_txn_ssn}'/>
                    <input type="hidden" id="login_id" name="login_id" value='${login_id}'/>
                    <input type="hidden" id="amt"  name="amt" value='${amt}'/>
                    <input type="hidden" id="back_notify_url" name="back_notify_url" value='${back_notify_url}'/>
                    <input type="hidden" id="page_notify_url" name="page_notify_url" value='${page_notify_url}'/>
                    <input type="hidden" id="signature" name="signature" value='${signature}'/>
                </form>
            </div>
        </section>
        <script language="javascript" type="text/javascript">
            window.onload = function () {
                document.getElementById("form").submit();
            }
        </script>
    </c:otherwise>
</c:choose>
</body>
</html>
