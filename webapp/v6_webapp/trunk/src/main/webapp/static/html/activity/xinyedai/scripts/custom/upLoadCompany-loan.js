require.config({paths:{common:"../common/common"}}),require(["common"],function(i){i.headerLoad("上传资料",4,!0,"applyCompany.html",!1,""),$.ajax({url:ajaxlink+"borrowApply/queryAllUploadPicPathXyd.do"+extra,type:"post",dataType:"jsonp",jsonp:"jsonpcallback",data:{identity:$.fn.cookie("borrowType"),houseType:$.fn.cookie("upLoadType")},success:function(n){0==n.resultCode?($.each(n.data.idcardUrl,function(i,n){"absent"==n?$("#idcardUrl").find("li").eq(i).find("img").attr("src","images/custom/upload/upload.jpg"):$("#idcardUrl").find("li").eq(i).find("img").attr("src",n)}),$.each(n.data.loanUrl,function(i,n){"absent"==n?$("#loanUrl").find("li").eq(i).find("img").attr("src","images/custom/upload/upload.jpg"):$("#loanUrl").find("li").eq(i).find("img").attr("src",n)}),$.each(n.data.creditUrl,function(i,n){"absent"==n?$("#creditUrl").find("li").eq(i).find("img").attr("src","images/custom/upload/upload.jpg"):$("#creditUrl").find("li").eq(i).find("img").attr("src",n)}),$.each(n.data.licenseUrl,function(i,n){"absent"==n?$("#licenseUrl").find("li").eq(i).find("img").attr("src","images/custom/upload/upload.jpg"):$("#licenseUrl").find("li").eq(i).find("img").attr("src",n)}),$.each(n.data.managementUrl,function(i,n){"absent"==n?$("#managementUrl").find("li").eq(i).find("img").attr("src","images/custom/upload/upload.jpg"):$("#managementUrl").find("li").eq(i).find("img").attr("src",n)})):i.alertBox(n.msg)},error:function(){i.alertBox("网络异常请重试")}}),$.each($("ol").find("li"),function(){$(this).click(function(){$(window.frames[0].document).find("form").attr("action",ajaxlink2+"borrowApply/uploadPicXyd.do?picType="+$(this).attr("value")),$(window.frames[0].document).find("input").click()})});var n=document.getElementsByTagName("iframe")[0].contentWindow.location.href;setInterval(function(){document.getElementsByTagName("iframe")[0].contentWindow.location.href!=n&&window.location.reload()},1e3),$(".formBox").find("button").on("click",function(){var n=0;$.each($("#idcardUrl").find("li").find("img"),function(){return"images/custom/upload/upload.jpg"!=$(this).attr("src")&&void n++}),n==$("#idcardUrl").find("li").find("img").length?window.location.href="success.html":i.alertBox("请核实是否上传了所有图片")})});