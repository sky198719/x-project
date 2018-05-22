<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <style type="text/css">
        body {
            text-align: center;
            font-family: "微软雅黑";
        }

        td {
            font-size: 14px;
        }
    </style>
    <title>连连支付WAP收银台测试</title>
</head>
<body>

<form name="inputForm" action='<%=request.getContextPath()%>/lianlian/paySubmit.do' method="post">
    <table cellpadding="2" cellspacing="2">
        <tr>
            <td colspan="2" align="center"
                style="font-weight: bold; font-size: 25px;" height="85">连连支付WAP收银台测试
            </td>
        </tr>
        <tr>
            <td><span style="color: red">*</span>交易金额</td>
            <td><input type="text" name="moneyOrder" value="0.01">（单位为RMB-元）</td>
        </tr>
        <tr>
            <td><span></span>卡号</td>
            <td><input type="text" name="cardNo" value=""></td>
        </tr>
        <tr>
            <td>协议号</td>
            <td><input type="text" name="noAgree"></td>
        </tr>
        <tr>
            <td><span style="color: red">*</span>商户用户唯一编号</td>
            <td><input type="text" name="userId" value=""></td>
        </tr>
        <tr>
            <td><input type="submit" value="提交">
                <input type="reset" value="重置">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
