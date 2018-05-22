<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>信贷员工APP推广页面</title>
    <link rel="stylesheet" href="static/css/frozen/frozen.css">
    <script src="static/lib/zepto/zepto.min.js"></script>
    <script src="static/lib/frozen/frozen.js"></script>
</head>
<body ontouchstart>
<header class="ui-header ui-header-positive ui-border-b">
    <h1>信贷员工专属app推广</h1>
</header>
<section class="ui-container">
    <section id="form">
        <div class="demo-item">
            <p style="font-size: 16px;padding: 10px;">请输入您的员工编号：</p>
            <div class="demo-block">
                <div class="ui-form ui-border-t">
                    <form action="#">
                        <div class="ui-form-item ui-form-item-pure ui-border-b">
                            <input type="text" placeholder="编号为xxd+5位数字比如 xxd00123" id="empNo">
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div  class="ui-whitespace" style="margin-top: 10px;font-size: 14px;">
            温馨提示：字母不区分大小写
        </div>
        <div class="ui-btn-wrap">
            <button class="ui-btn-lg ui-btn-danger" id="btn1">
                生成专属推广URL
            </button>
        </div>
        <div class="ui-whitespace" style="-webkit-user-select: initial;padding-top: 20px;display: none;" id="myDiv">
            <h1>您的专属推广URL：</h1>
            <p class="ui-txt-feeds" id="myUrl" style="-webkit-user-select: initial;word-break:break-all;"></p>
            <p style="margin-top: 20px;"><h1>点击链接进入你的专属页面，赶紧分享给你的亲朋好友吧。</h1></p>
        </div>
    </section>
</section>
</body>
<script type="text/javascript">
    (function (){
        $("#btn1").click(function(){
            var empNo = $("#empNo").val();
            if(empNo == null || empNo == "") {
                var dia = $.dialog({
                    title:'提示',
                    content:'请输入您的员工编号',
                    button:["确认"]
                });
                $("#dialogButton0").click(function(){
                    $(".ui-dialog").dialog("hide");
                });
                return false;
            }

            $("#myDiv").show();
            var url = "http://www.xinxindai.com/m/empapp/" + empNo + ".do" ;
            $("#myUrl").html("<a href='"+url+"'>"+url+"</a>");
        });

    })();
</script>
</html>