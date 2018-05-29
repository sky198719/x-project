<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>充值成功</title>
</head>
<body style="background:#F9F9F9;font-family:'Microsoft YaHei';">
<c:choose>
    <c:when test="${status == '-1'}">
        <div style="width:100%; height:120px; padding-top:30px; text-align: center; border-bottom: 1px solid #dedede; font-size: 16px; color: #666; font-weight: 300; background:#fff;">
            糟糕，充值失败
            <div style="margin-top: 20px;">
                <span><a href="${path}/#!/static/html/account/topup.html?path=account"  style="color:#2096e9;text-decoration:none;">继续充值</a></span>
            </div>
        </div>

    </c:when>
    <c:when test="${status == '0'}">
        <div style="width:100%; height:120px; padding-top:30px; text-align: center; border-bottom: 1px solid #dedede; font-size: 16px; color: #666; font-weight: 300; background:#fff;">
            恭喜您，充值成功
            <div style="color: #999; margin:10px auto;">
                成功充值：
                <span style="color: #F33;">${pay_amount}</span>
                元
            </div>
            <div style="margin-top: 20px;">
                <span><a href="${path}/#!/static/html/account/topup.html?path=account"  style="color:#2096e9;text-decoration:none;">继续充值</a></span>
            </div>
        </div>
    </c:when>
</c:choose>
<div style="margin-top:20px; width:100%; text-align:center;">
    <div style="display: none;">
        <a href="${path}/#!/static/html/fund/fundUnInvested.html?path=fund" style="width:40%; text-align:center; padding:10px 0; background:#fbad36; color:#fff; font-size:18px; border-radius:6px; text-decoration:none; display:inline-block; margin:0 10px;">投资_</a>
    </div>
    <div style="margin-top: 20px;">
        <a href="${path}/#!/static/html/borrow/borrowListV2.html" style="width:40%; text-align:center; padding:10px 0; background:#2096e9; color:#fff; font-size:18px; border-radius:6px; text-decoration:none; display:inline-block; margin:0 10px;">投资散标</a>
    </div>
    <div style="margin-top: 20px;">
        <a href="${path}/#!/static/html/popular/financesList.html" style="width:40%; text-align:center; padding:10px 0; background:#2096e9; color:#fff; font-size:18px; border-radius:6px; text-decoration:none; display:inline-block; margin:0 10px;">热门理财</a>
    </div>
</div>
</body>
<script type="text/javascript" src="${path}/static/js/utils/analytics_plugin.js"></script>
<script type="text/javascript" src="${path}/static/lib/jquery/jquery.js"></script>
<script type="text/javascript" src="${path}/static/lib/jquery/jquery.md5.js"></script>
<script type="text/javascript">
$(function(){
	try{
		var gauserId = '${loginUser.userId}';
		gaInits(gauserId);
		var status = '${status}';
		var monery = '${pay_amount}';
		if(status == '0'){
			ga('set', 'dimension7', '充值用户');
			ga('send', 'event',"充值", "充值成功", monery);//info传充值方式+充值金额，如建设银行_10000
		}
	}catch(e){}
})
</script>
</html>
