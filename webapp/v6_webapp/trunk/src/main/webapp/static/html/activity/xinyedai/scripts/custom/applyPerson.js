require.config({paths:{common:"../common/common"}}),require(["common"],function(e){e.headerLoad("贷款申请",3,!0,"info.html",!1,""),e.simSelect("#loanTerm","#loanTermBox"),e.scrollConfirm("#loanTermBox","#loanTerm"),$(".borrowBox").find("span").html(25*parseInt($.fn.cookie("monthlyIncome"))),$.ajax({url:ajaxlink+"borrowApply/queryXydBorrowInfo.do"+extra,type:"get",dataType:"jsonp",jsonp:"jsonpcallback",success:function(a){0==a.resultCode?($("#houseAddress").find("input").attr("value",a.data.houseAddress),$("#houseElectricityfees").find("input").attr("value",a.data.houseElectricityfees),$("#companyAddress").find("input").attr("value",a.data.companyAddress),$("#relativesName").find("input").attr("value",a.data.relativesName),$("#relativesMobile").find("input").attr("value",a.data.relativesMobile),$("#colleagueName").find("input").attr("value",a.data.colleagueName),$("#colleagueMobile").find("input").attr("value",a.data.colleagueMobile),$("#loanAmount").find("input").attr("value",a.data.loanAmount),void 0==a.data.loanTerm||3==a.data.loanTerm?($("#loanTerm").find("div").html($("#loanTermBox").find("li").eq(0).html()),$("#loanTerm").find("div").attr("value","3"),$("#loanTerm").find("div").addClass("active")):6==a.data.loanTerm?($("#loanTerm").find("div").html($("#loanTermBox").find("li").eq(1).html()),$("#loanTerm").find("div").attr("value",a.data.loanTerm),$("#loanTerm").find("div").addClass("active")):12==a.data.loanTerm&&($("#loanTerm").find("div").html($("#loanTermBox").find("li").eq(2).html()),$("#loanTerm").find("div").attr("value",a.data.loanTerm),$("#loanTerm").find("div").addClass("active"))):e.alertBox(a.msg)},error:function(){e.alertBox("网络异常请重试")}}),0==$.fn.cookie("isPass")?$(".clause").find("img").attr("src","images/custom/apply/hook2.png"):$(".clause").find("img").attr("src","images/custom/apply/hook1.png"),$(".clause").on("click",function(){0==e.emptyInspect("#houseAddress")&&0==e.emptyInspect("#houseElectricityfees")&&0==e.emptyInspect("#companyAddress")&&0==e.emptyInspect("#relativesName")&&0==e.emptyInspect("#relativesMobile")&&0==e.emptyInspect("#colleagueName")&&0==e.emptyInspect("#colleagueMobile")&&0==e.emptyInspect("#loanAmount")&&0==e.emptyInspect("#loanTerm")&&0==e.mobileInspect("#relativesMobile")&&0==e.mobileInspect("#colleagueMobile")&&0==e.numberInspect("#loanAmount")&&e.ajaxSubmit(ajaxlink+"borrowApply/saveXydBorrowInfo.do"+extra,{houseAddress:$("#houseAddress").find("input").val(),houseElectricityfees:$("#houseElectricityfees").find("input").val(),companyAddress:$("#companyAddress").find("input").val(),relativesName:$("#relativesName").find("input").val(),relativesMobile:$("#relativesMobile").find("input").val(),colleagueName:$("#colleagueName").find("input").val(),colleagueMobile:$("#colleagueMobile").find("input").val(),loanAmount:$("#loanAmount").find("input").val(),loanTerm:$("#loanTerm").find("div").attr("value")},"clause.html")}),$(".formBox").find("button").on("click",function(){var a="";0==e.emptyInspect("#houseAddress")&&0==e.emptyInspect("#houseElectricityfees")&&0==e.emptyInspect("#companyAddress")&&0==e.emptyInspect("#relativesName")&&0==e.emptyInspect("#relativesMobile")&&0==e.emptyInspect("#colleagueName")&&0==e.emptyInspect("#colleagueMobile")&&0==e.emptyInspect("#loanAmount")&&0==e.emptyInspect("#loanTerm")&&0==e.mobileInspect("#relativesMobile")&&0==e.mobileInspect("#colleagueMobile")&&0==e.numberInspect("#loanAmount")&&(0==$.fn.cookie("isPass")?(a=0==$.fn.cookie("upLoadType")?"upLoadPerson-clean.html":1==$.fn.cookie("upLoadType")?"upLoadPerson-loan.html":"upLoadPerson-mortgage.html",e.ajaxSubmit(ajaxlink+"borrowApply/saveXydBorrowInfo.do"+extra,{houseAddress:$("#houseAddress").find("input").val(),houseElectricityfees:$("#houseElectricityfees").find("input").val(),companyAddress:$("#companyAddress").find("input").val(),relativesName:$("#relativesName").find("input").val(),relativesMobile:$("#relativesMobile").find("input").val(),colleagueName:$("#colleagueName").find("input").val(),colleagueMobile:$("#colleagueMobile").find("input").val(),loanAmount:$("#loanAmount").find("input").val(),loanTerm:$("#loanTerm").find("div").attr("value")},a)):e.alertBox("请先阅读并同意《借款申请承诺书》"))})});