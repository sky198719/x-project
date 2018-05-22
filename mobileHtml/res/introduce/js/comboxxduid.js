/**
 * Created by gaoshanshan_syp on 2017/8/11.
 */
require(['base', 'requirejs', 'trackBase',"xxdBridge"], function ($, requirejs, track,xxdBridge) {
    track.init();
    var sign;
    sign = GetQueryString("uid");
    var dataUrl = {
        commonUrl:'/tradeCenter/investBiz/showStatus/'
    };

    $("#J_suspension").on('click',function (ev) {
        if (sign == null || sign=='') {
            xxdBridge.open({pagename:'poppage'});
            xxdBridge.open({
                pagename:'regist'
            });
        }else{
            xxdBridge.open({
                pagename: 'myreward'
            });
        }
        ev && event.preventDefault();
    });

    //数据
    ajaxQtds();
    ajaxXyb();
    ajaxBbgs();

    //    七天大胜
    function ajaxQtds() {
        $.xxdAjax({
            url      : dataUrl.commonUrl+'QTDS',
            type:'get',
            clientId: "XXD_INTEGRATION_PLATFORM",
            data     : {},
            dataType : 'json',
            callbacks  : function (data) {
                var result;
                if (data.code == "200000" && (result=data.data.productInfo)) {
                    var apr=result.apr;
                    var floatApr=result.floatApr;
                    $("#J_qtdsApr").html(apr);
                    $("#J_qtdsFloatApr").html(floatApr);
                    $("#J_qtdsApr").attr("qtdsid",data.data.productId);
                }
            },
            error:function () {
                alert('网络异常请重试！');
            }
        })
    }
    //    新元宝
    function ajaxXyb() {
        $.ajax({
            url: '/m/xplan/getLatestSchemeId.do',
            data: {
                currentPage: 1,
                closeTerm: 1,
                pageSize: 10
            },
            dataType: 'json',
            success: function (data) {
                var result;
                if (data.resultCode == 0 && (result=data.schemeInfo)) {
                    var maxApr=result.MAXAPR;
                    var floatApr=result.APP;
                    if(floatApr){
                        maxApr=maxApr+floatApr;
                    }
                    if(maxApr.toString().length>1){
                        $("#J_xybdata").addClass('xyb-change');
                    }

                    $("#J_xybApr").html(maxApr);
                    $("#J_xybApr").attr("planid", result.SCHEMEID);
                }

            }
        });
    }

    function ajaxBbgs() {
        $.xxdAjax({
            url      : dataUrl.commonUrl+'BBGS',
            type:'get',
            clientId: "XXD_INTEGRATION_PLATFORM",
            data     : {},
            dataType : 'json',
            callbacks:function (data) {
                var result;
                if (data.code == "200000" && (result=data.data.productInfo)) {
                    var minapr=result.plannedAnnualRateFrom;
                    var maxapr=result.plannedAnnualRateTo;
                    $("#J_min").attr("bbgsid",data.data.productId);
                    $("#J_min").html(minapr);
                    $("#J_max").html(maxapr);
               }
            },
            error:function () {
                alert('网络异常请重试！');
            }
        });
    }
    //判断是否登录跳转
    $("#J_qtds").on('click',function (ev) {
        var qtdsId=$("#J_qtdsApr").attr("qtdsid");
        xxdBridge.open({
                pagename: 'pddetail',
                productIndex:3,
                productId:qtdsId
            });
        ev && event.preventDefault();
    });
    $("#J_xyb").on('click',function(ev){
        var planId= $("#J_xybApr").attr('planid');
            xxdBridge.open({
                pagename: 'pddetail',
                productIndex:5,
                productId:planId
            });
        ev && event.preventDefault();
    });
    $("#J_bbgs").on('click',function(ev){
        var bbgsId=$("#J_min").attr("bbgsid");
            xxdBridge.open({
                pagename: 'pddetail',
                productIndex:2,
                productId:bbgsId
            });
        ev && event.preventDefault();
    });

    function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)return decodeURI(r[2]);
        return null;
    }

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});
