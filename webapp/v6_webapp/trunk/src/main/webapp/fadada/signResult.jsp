<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>签约结果</title>
	<link rel="stylesheet" href="static/css/frozen/frozen.css">
    <script src="static/lib/zepto/zepto.min.js"></script>
    <script src="static/lib/frozen/frozen.js"></script>
</head>
<body ontouchstart style="font-family: Microsoft YaHei;background-color: #FFFFFF;">
	<header class="ui-header ui-header-positive ui-border-b" style="background-color: #FFFFFF;padding:0px;height:50px;">
		<div style="width:100%;height:50px;border-bottom:1px solid #E9E9E9">
			<a href="./#!/static/html/index/home.html"><div style="background-image: url(static/img/xxd/logo.png);background-size:154px 40px;background-repeat:no-repeat;background-position: center;line-height:50px;height:50px;margin:0 auto;" ></div></a>
		</div>
	</header>
	<section class="ui-container">
		<c:choose>
		    <c:when test="${signFlag == 'nosign'}">
		    	<div style="width:100%;height:150px;">
					<div style="background-image: url(static/img/xxd/info.png);background-size:70px;background-repeat:no-repeat;background-position: center;line-height:150px;height:150px;margin:0 auto;" />
				</div>
				<div> 
					<div style="text-align:center;font-size:18px;color:#424242;">
						<%--${info} --%>
						该合同签约已失效，可能因审核失败、
					</div>
					<div style="text-align:center;font-size:18px;color:#424242;">
						流标等其他原因导致。
					</div>
					<div style="text-align:center;font-size:18px;color:#424242;">
						详情请咨询借款人或信贷经理
					</div>
				</div>
		    </c:when>
		    <c:when test="${signFlag == 'success'}">
		    	<div style="width:100%;height:150px;">
					<div style="background-image: url(static/img/xxd/fddSuccess.png);background-size:70px;background-repeat:no-repeat;background-position: center;line-height:150px;height:150px;margin:0 auto;" />
				</div>
				<div>
					<div style="text-align:center;font-size:18px;color:#424242;">
						恭喜，您已经成功签约！
					</div>
					<div style="text-align:center;font-size:18px;color:#424242;">
						请点击下方按钮查阅所签合同
					</div>
				</div>
				<div class="ui-btn-wrap" style="padding-top: 60px;font-size:18px;">
			            <a class="ui-btn-lg ui-btn-primary" id="viewContract" style="background:#3F9BFF;" href="viewFddContract.do?bid=${borrowId}&transactionId=${transactionId}">
			                查看合同
			            </a>
			    </div>
		    </c:when>
		    <c:otherwise>
		    	 <div style="width:100%;height:150px;">
					<div style="background-image: url(static/img/xxd/fddFailed.png);background-size:70px;background-repeat:no-repeat;background-position: center;line-height:150px;height:150px;margin:0 auto;" />
				</div>
				<div>
					<div style="text-align:center;font-size:18px;color:#424242;">
						抱歉，暂未签署成功！
					</div>
					<div style="text-align:center;font-size:18px;color:#424242;">
						请点击下方按钮再次尝试~
					</div>
				</div>
				<div class="ui-btn-wrap" style="padding-top: 50px;font-size:18px;">
			            <a class="ui-btn-lg ui-btn-primary" id="reSign" style="background:#3F9BFF;" href="signFdd.do?bid=${borrowId}&ut=2&transactionId=${transactionId}">
			                再次签约
			            </a>
			    </div>
		    </c:otherwise>
		</c:choose>
		
	</section>
	<footer class="ui-footer" style="height:85px;font-size:13px;color:#AEAEAE;text-align:center;">
		<span>_（上海）金融信息服务有限公司</span><br>
		<span>上海市虹口区四川北路859号中信广场28楼</span><br>
		<span>客服电话：4000-169-521</span><br>
		<span>服务时间：09:00-18:00（工作日）</span>
	</footer>
	
</body>
</html>