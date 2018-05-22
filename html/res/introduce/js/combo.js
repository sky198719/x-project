require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs"], function ($, float, track, store) {

    var isLogin = 0; //是否登录 0已登录 1未登录
    var timer = new Date();
    var myTime = timer.getTime(); //本地时间

    //读取cookie
    function getCookie(name){
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
        if(arr=document.cookie.match(reg)){
            return unescape(arr[2]);
        }else{
            return null;
        }
    }
    var realToken;
    if(getCookie('Token') != '' && getCookie('Token') != undefined && getCookie('Token') != null){
        realToken = getCookie('Token');
    }else{
        realToken = getCookie('userToken');
    }

    //登录判断
    var userId;
    $.ajax({
        url:'/feapi/users/loginInfo?userToken=' + realToken,
        type:'get',
        dataType:'json',
        success:function(data){
            if(data.code == '200000'){
                if(data.data.status.code == 200){
                    isLogin = 0;
                    userId = data.data.id;
                    $.ajax({
                        url:'/tradeCenter/InvestOrder/welfare/' + userId,
                        type:'get',
                        beforeSend:function(request){
                            request.setRequestHeader('clientId','XXD_INTEGRATION_PLATFORM');
                            request.setRequestHeader('clientTime',myTime);
                        },
                        success:function(data){
                            var data2 = JSON.parse(data);
                            if(data2.data.status == 1){
                                $('#xybnew').find('button').addClass('negtive');
                            }else{
                                $('#xybnew').find('button').click(function(){
                                    window.open('/detail/thirtytender.html');
                                });
                            }
                        },
                        error:function () {
                            alert('网络异常，请重试！');
                            return false;
                        }
                    });
                }
            }else{
                isLogin = 1;
                $('#xybnew').find('button').click(function(){
                    window.open('/detail/thirtytender.html');
                });
            }
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });

    // 说明文案弹出
    $('.info').hover(function(){
        $(this).find('strong').fadeIn(0);
    },function(){
        $(this).find('strong').fadeOut(0);
    });

    // 跳转
    $('#xyb').find('button').click(function(){
        window.open('/xplan/search/list.html');
    });
    $('#yyp').find('button').click(function(){
        window.open('/promotion/yyp.html');
    });
    $('#yjdj').find('button').click(function(){
        window.open('/detail/monthgold.html');
    });
    $('#investList2').find('i').click(function(){
        window.open('/html/help/platform.html');
    });

    // 接口投资金额处理
    function initAmount(obj){
        if(obj.substring(obj.length-2,obj.length) == '万元'){
            obj = obj.substring(0,obj.indexOf('.')) + obj.substring(obj.indexOf('.') + 1,obj.indexOf('万')) + '00';
        }else{
            obj = obj.substring(0,obj.indexOf('元'));
        }
        return parseInt(obj);
    }

    // 数字处理逗号分隔
    function initNumber(obj){
        //return obj.toString().replace(/(\d)(?=(?:\d{3})+$)/g,'$1,');
        return obj;
    }

    // 数字处理强制保留两位小数
    function initEarn(obj){
        obj = Math.floor(obj * 100) / 100;
        return obj.toFixed(2);
    }

    // 新元宝抵扣红包
    function xybChange(obj){
        var amount = 0;
        if(obj >= 100 && obj < 500){
            amount = 0;
        }else if(obj >= 500 && obj < 1000){
            amount = 5;
        }else if(obj >= 1000 && obj < 1500){
            amount = 10;
        }else if(obj >= 1500 && obj < 2000){
            amount = 15;
        }else if(obj >= 2000 && obj < 2500){
            amount = 25;
        }else if(obj >= 2500 && obj < 3000){
            amount = 30;
        }else if(obj >= 3000 && obj < 3500){
            amount = 35;
        }else if(obj >= 3500 && obj < 5000){
            amount = 40;
        }else if(obj >= 5000 && obj < 5500){
            amount = 68;
        }else if(obj >= 5500 && obj < 6000){
            amount = 73;
        }else if(obj >= 6000 && obj < 6500){
            amount = 78;
        }else if(obj >= 6500 && obj < 7000){
            amount = 83;
        }else if(obj >= 7000 && obj < 7500){
            amount = 93;
        }else if(obj >= 7500 && obj < 8000){
            amount = 98;
        }else if(obj >= 8000 && obj < 8500){
            amount = 103;
        }else if(obj >= 8500){
            amount = 108;
        }
        return amount;
    }

    // 初始化动画函数
    function initAnimate(product,amount,rate){
        if(product == '#xyb'){
            earn = amount * rate / 100 / 12 + 108;
        }else if(product == '#yjdj'){
            earn = amount * rate / 100 / 360 * 31;
        }else{
            earn = amount * rate / 100 / 12;
        }
        $(product).find('p').html(initEarn(earn));
        $(product).find('input').val(initNumber(amount));
        $(product).find('span').attr('class','current');
        amountEarn = parseFloat(parseFloat($('#xybnew p').html()) + parseFloat($('#xyb p').html()) + parseFloat($('#yyp p').html()) + parseFloat($('#yjdj p').html())).toFixed(2);
        $('.release').find('strong').html(initEarn(amountEarn));
    }

    $(window).scroll(function(){
        if(startFlag == 4){
            if($(window).scrollTop() >= 1000){
                if(animateFlag == 0){
                    animateFlag = 1;
                    setTimeout(function(){
                        initAnimate('#xybnew',10000,parseFloat($('#xybnew').find('font').html()));
                        $('#xybnew').find('input').attr('disabled',true);
                        $('#xybnew').addClass('current');
                    },1000);
                    setTimeout(function(){
                        initAnimate('#xyb',8500,parseFloat(xybRate));
                        $('#xyb').find('input').attr('disabled',false);
                        $('#xyb').addClass('current');
                        $('#xyb').find('font').html(parseInt((parseFloat($('#xyb').find('p').html()) * 12 / parseFloat($('#xyb').find('input').val())) * 100));
                    },2000);
                    setTimeout(function(){
                        initAnimate('#yyp',10000,parseFloat($('#yyp').find('font').html()));
                        $('#yyp').find('input').attr('disabled',false);
                        $('#yyp').addClass('current');
                    },3000);
                    setTimeout(function(){
                        initAnimate('#yjdj',3000,parseFloat($('#yjdj').find('font').html()));
                        $('#yjdj').find('input').attr('disabled',false);
                        $('#yjdj').addClass('current');
                    },4000);
                    setTimeout(function(){
                        $('#investList2').find('span').attr('class','current');
                        $('#investList2').find('li').addClass('current');
                    },5000);
                }
            }
        }
    });

    $('#investList2').find('span').click(function(){
        if($(this).attr('class') == 'current'){
            $(this).attr('class','');
            $(this).parent('li').attr('class','');
        }else{
            $(this).attr('class','current');
            $(this).parent('li').attr('class','current');
        }
    });

    // 初始化数据主要逻辑
    var animateFlag = 0;
    var startFlag = 0;
    var xybRate = 0;
    var xybEarn = 0;
    function initInvest(url,product){
        $.ajax({
            url:url,
            type:'get',
            beforeSend:function(request){
                request.setRequestHeader('clientId','XXD_FRONT_END');
                request.setRequestHeader('clientTime',myTime);
                request.setRequestHeader('s','s');
            },
            success:function(data){
                if(data.code == '200000'){
                    var leastAmount;
                    var mostAmount;
                    var rate;
                    var earn;
                    var amountEarn;
                    if(product == '#xybnew'){
                        if(data.data.floatingRate){
                            $(product).find('font').html(data.data.plannedAnnualRate + data.data.floatingRate);
                            rate = data.data.plannedAnnualRate + data.data.floatingRate;
                        }else{
                            $(product).find('font').html(data.data.plannedAnnualRate);
                            rate = data.data.plannedAnnualRate;
                        }
                        leastAmount = data.data.leastTenderAmountLabel;
                        mostAmount = data.data.mostTenderAmountLabel;
                    }else if(product == '#xyb'){
                        $.each(data.data.items,function(index,item){
                            if(item.frozenPeriod == '1'){
                                if(item.floatingRate){
                                    //$(product).find('font').html(item.plannedAnnualRateFrom + item.floatingRate);
                                    rate = item.plannedAnnualRateFrom + item.floatingRate;
                                    xybRate = item.plannedAnnualRateFrom + item.floatingRate;
                                }else{
                                    //$(product).find('font').html(item.plannedAnnualRateFrom);
                                    rate = item.plannedAnnualRateFrom;
                                    xybRate = item.plannedAnnualRateFrom;
                                }
                                leastAmount = item.leastTenderAmountLabel;
                                mostAmount = item.mostTenderAmountLabel;
                            }
                        });
                    }else if(product == '#yyp'){
                        if(data.data.floatingRate){
                            $(product).find('font').html(data.data.plannedAnnualRateFrom + data.data.floatingRate);
                            rate = data.data.plannedAnnualRateFrom + data.data.floatingRate;
                        }else{
                            $(product).find('font').html(data.data.plannedAnnualRateFrom);
                            rate = data.data.plannedAnnualRateFrom;
                        }
                        leastAmount = data.data.leastTenderAmountLabel;
                        mostAmount = data.data.mostTenderAmountLabel;
                    }else{
                        if(data.data.floatingRate){
                            $(product).find('font').html(data.data.plannedAnnualRate + data.data.floatingRate);
                            rate = data.data.plannedAnnualRate + data.data.floatingRate;
                        }else{
                            $(product).find('font').html(data.data.plannedAnnualRate);
                            rate = data.data.plannedAnnualRate;
                        }
                        leastAmount = data.data.leastTenderAmountLabel;
                        mostAmount = data.data.mostTenderAmountLabel;
                    }
                    startFlag++;
                    if(startFlag == 4){
                        if($(window).scrollTop() >= 1000){
                            if(animateFlag == 0){
                                animateFlag = 1;
                                setTimeout(function(){
                                    initAnimate('#xybnew',10000,parseFloat($('#xybnew').find('font').html()));
                                    $('#xybnew').find('input').attr('disabled',true);
                                    $('#xybnew').addClass('current');
                                },1000);
                                setTimeout(function(){
                                    initAnimate('#xyb',8500,parseFloat(xybRate));
                                    $('#xyb').find('input').attr('disabled',false);
                                    $('#xyb').addClass('current');
                                    $('#xyb').find('font').html(parseInt((parseFloat($('#xyb').find('p').html()) * 12 / parseFloat($('#xyb').find('input').val())) * 100));
                                },2000);
                                setTimeout(function(){
                                    initAnimate('#yyp',10000,parseFloat($('#yyp').find('font').html()));
                                    $('#yyp').find('input').attr('disabled',false);
                                    $('#yyp').addClass('current');
                                },3000);
                                setTimeout(function(){
                                    initAnimate('#yjdj',3000,parseFloat($('#yjdj').find('font').html()));
                                    $('#yjdj').find('input').attr('disabled',false);
                                    $('#yjdj').addClass('current');
                                },4000);
                                setTimeout(function(){
                                    $('#investList2').find('span').attr('class','current');
                                    $('#investList2').find('li').addClass('current');
                                },5000);
                            }
                        }
                    }
                    $(product).find('input').blur(function(){
                        if($(this).val() == '' || $(this).val() == null || $(this).val() == undefined){
                            $(this).val('');
                            $(product).attr('class','');
                            $(product).find('span').attr('class','');
                            $(product).find('p').html('0.00');
                            $('#xyb').find('font').html(parseInt(21));
                        }else if($(this).val() < initAmount(leastAmount) && $(this).val() != '' && $(this).val() != null && $(this).val() != undefined){
                            alert('不能小于最小投资金额' + leastAmount);
                            $(this).val(initNumber(initAmount(leastAmount)));
                            if(product == '#xyb'){
                                earn = initAmount(leastAmount) * rate / 100 / 12 + xybChange(initAmount(leastAmount));
                                $('#xyb').find('font').html(parseInt((initEarn(earn) * 12 / initNumber(initAmount(leastAmount))) * 100));
                            }else if(product == '#yjdj'){
                                earn = initAmount(leastAmount) * rate / 360 / 100 * 31;
                            }else{
                                earn = initAmount(leastAmount) * rate / 100 / 12;
                            }
                            $(product).find('p').html(initEarn(earn));
                            $(product).find('span').attr('class','current');
                            $(product).attr('class','current');
                        }else if($(this).val() > initAmount(mostAmount) && $(this).val() != '' && $(this).val() != null && $(this).val() != undefined){
                            alert('不能大于最大投资金额' + mostAmount);
                            $(this).val(initNumber(initAmount(mostAmount)));
                            if(product == '#xyb'){
                                earn = initAmount(mostAmount) * rate / 100 / 12 + xybChange(initAmount(mostAmount));
                                $('#xyb').find('font').html(parseInt((initEarn(earn) * 12 / initNumber(initAmount(mostAmount))) * 100));
                            }else if(product == '#yjdj'){
                                earn = initAmount(mostAmount) * rate / 360 / 100 * 31;
                            }else{
                                earn = initAmount(mostAmount) * rate / 100 / 12;
                            }
                            $(product).find('p').html(initEarn(earn));
                            $(product).find('span').attr('class','current');
                            $(product).attr('class','current');
                        }else{
                            $(this).val(initNumber($(this).val()));
                            if($(this).val()%100 != 0 && $(this).val() != '' && $(this).val() != null && $(this).val() != undefined){
                                alert('只能输入100的整数倍');
                                $(this).val(parseInt($(this).val() / 100) * 100);
                                $(product).find('span').attr('class','current');
                                $(product).attr('class','current');
                            }
                            if(product == '#xyb'){
                                earn = $(this).val() * rate / 100 / 12 + xybChange($(this).val());
                                $('#xyb').find('font').html(parseInt((initEarn(earn) * 12 / initNumber($(this).val())) * 100));
                            }else if(product == '#yjdj'){
                                earn = $(this).val() * rate / 360 / 100 * 31;
                            }else{
                                earn = $(this).val() * rate / 100 / 12;
                            }
                            $(product).find('p').html(initEarn(earn));
                            $(product).find('span').attr('class','current');
                            $(product).attr('class','current');
                        }
                        amountEarn = parseFloat(parseFloat($('#xybnew p').html()) + parseFloat($('#xyb p').html()) + parseFloat($('#yyp p').html()) + parseFloat($('#yjdj p').html())).toFixed(2);
                        $('.release').find('strong').html(initEarn(amountEarn));
                    });
                    $(product).find('span').click(function(){
                        if(product == '#xybnew'){
                            return false;
                        }else{
                            if($(this).attr('class') == 'current'){
                                if(product == '#xyb'){
                                    $(this).parent('li').find('font').html(parseInt(21));
                                }
                                $(this).attr('class','');
                                $(this).parent('li').attr('class','');
                                $(this).parent('li').find('input').val('');
                                $(this).parent('li').find('p').html('0.00');
                                amountEarn = parseFloat(parseFloat($('#xybnew p').html()) + parseFloat($('#xyb p').html()) + parseFloat($('#yyp p').html()) + parseFloat($('#yjdj p').html())).toFixed(2);
                                $('.release').find('strong').html(initEarn(amountEarn));
                            }else{
                                $(this).attr('class','current');
                                $(this).parent('li').attr('class','current');
                                if(product == '#xyb'){
                                    initAnimate('#xyb',8500,parseFloat(xybRate));
                                    $('#xyb').find('font').html(parseInt((parseFloat($('#xyb').find('p').html()) * 12 / parseFloat($('#xyb').find('input').val())) * 100));
                                }else if(product == '#yyp'){
                                    initAnimate('#yyp',10000,parseFloat($('#yyp').find('font').html()));
                                }else if(product == '#yjdj'){
                                    initAnimate('#yjdj',3000,parseFloat($('#yjdj').find('font').html()));
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    // 调用主函数
    initInvest('/tradeCenter/XSCP30T/brief','#xybnew');
    initInvest('/tradeCenter/XYB/brief','#xyb');
    initInvest('/tradeCenter/YYP/brief','#yyp');
    initInvest('/tradeCenter/YJDJ/brief','#yjdj');

    // 布码init
    var userDO = store && store.get("userDO") || {};
    track.init(userDO);

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});
