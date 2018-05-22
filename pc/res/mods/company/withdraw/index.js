require(['base', "trackBase", 'store', 'juicer'
    , 'companyHeader', 'footer', "dialog", "bankListTpl",'backTop', 'json', "requirejs"
], function ($, track, store, jui, header, footer, dialog, bankListTpl) {
    header.init();
    footer.init();
    $(function() {
        if(parseInt($('.g-left').height()) < parseInt($('.g-right').height())){
            $('.g-left').css('min-height',$('.g-right').height() + 'px');
        }
    });
    function num(obj){
        obj.val(obj.val().replace(/[^\d.]/g,""));
        obj.val(obj.val().replace(/^\./g,""));
        obj.val(obj.val().replace(/\.{2,}/g,"."));
        obj.val(obj.val().replace(".","$#$").replace(/\./g,"").replace("$#$","."));
        obj.val(obj.val().replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'));
    }
    $('.last .filed-user').eq(0).fadeOut(0);
    $('#errorTip1').fadeOut(0);
    $('#errorTip2').fadeOut(0);
    $('.bluetipexc').find('strong').fadeOut(0);
    $.each($('.menu ul li'),function(){
        $(this).find('div').css('display','none');
    });
    $('.menu ul li').eq(0).addClass('showTab');
    $('.showTab').find('div').css('display','block');
    $('.menu ul li a').on('click',function(){
        if($(this).parent('li').attr('class') == 'showTab'){
            $(this).parent('li').removeClass('showTab');
            $(this).parent('li').find('div').css('display','none');
        }else{
            $(this).parent('li').addClass('showTab');
            $(this).parent('li').find('div').css('display','block');
        }
    });
    function getCookie(name){
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
        if(arr=document.cookie.match(reg)){
            return unescape(arr[2]);
        }else{
            return null;
        }
    }
    var fakeToken = getCookie('Token');
    if(fakeToken == '' || fakeToken == null || fakeToken == undefined){
        alert('登录状态异常，请重新登录！');
        window.location.href = '/user/delSessionAndForwardToLogin.html';
        return false;
    }else{
        if(fakeToken.substring(0,1) == '\"'){
            fakeToken = fakeToken.substr(1,fakeToken.length - 2);
        }
    }
    
    $('body').append('<form action="" id="newForm1" name="newForm1" method="post" target="_blank"></form>');
    $('#newForm1').append('<input type="text" id="amt" name="amt" />');
    $('#newForm1').append('<input type="text" id="back_notify_url" name="back_notify_url" />');
    $('#newForm1').append('<input type="text" id="login_id" name="login_id" />');
    $('#newForm1').append('<input type="text" id="mchnt_cd" name="mchnt_cd" />');
    $('#newForm1').append('<input type="text" id="mchnt_txn_ssn" name="mchnt_txn_ssn" />');
    $('#newForm1').append('<input type="text" id="page_notify_url" name="page_notify_url" />');
    $('#newForm1').append('<input type="text" id="signature" name="signature" />');

    var domain;
    var hostarr = window.location.hostname.split('.')
    var env = hostarr.shift()
    if(env === 'www') {
        env = 'static'
    } else {
        env = env + '-static'
    }
    hostarr.unshift(env);
    domain = 'http://' + hostarr.join('.') + '/pc/1.4.3/build/img/';

    var bankList = [
        {'bankCode':'0102','bankName':'中国工商银行','imgUrl':domain + 'bklogo_gs.png'},
        {'bankCode':'0103','bankName':'中国农业银行','imgUrl':domain + 'bklogo_ny.png'},
        {'bankCode':'0104','bankName':'中国银行','imgUrl':domain + 'bklogo_zg.png'},
        {'bankCode':'0105','bankName':'中国建设银行','imgUrl':domain + 'bklogo_js.png'},
        {'bankCode':'0301','bankName':'交通银行','imgUrl':domain + 'bklogo_jt.png'},
        {'bankCode':'0302','bankName':'中信银行','imgUrl':domain + 'bklogo_zx.png'},
        {'bankCode':'0303','bankName':'中国光大银行','imgUrl':domain + 'bklogo_gd.png'},
        {'bankCode':'0304','bankName':'华夏银行','imgUrl':domain + 'bklogo_hx.png'},
        {'bankCode':'0305','bankName':'中国民生银行','imgUrl':domain + 'bklogo_ns.png'},
        {'bankCode':'0306','bankName':'广东发展银行','imgUrl':domain + 'bklogo_gf.png'},
        {'bankCode':'0307','bankName':'平安银行股份有限公司','imgUrl':domain + 'bklogo_pa.png'},
        {'bankCode':'0308','bankName':'招商银行','imgUrl':domain + 'bklogo_zs.png'},
        {'bankCode':'0309','bankName':'兴业银行','imgUrl':domain + 'bklogo_xy.png'},
        {'bankCode':'0310','bankName':'上海浦东发展银行','imgUrl':domain + 'bklogo_pf.png'},
        {'bankCode':'0403','bankName':'中国邮政储蓄银行股份有限公司','imgUrl':domain + 'bklogo_yz.png'},
        {'bankCode':'0315','bankName':'恒丰银行','imgUrl':domain + 'bklogo_hf.png'},
        {'bankCode':'0319','bankName':'徽商银行','imgUrl':domain + 'bklogo_hs.png'},
        {'bankCode':'0322','bankName':'上海农村商业银行','imgUrl':domain + 'bklogo_src.png'},
        {'bankCode':'0314','bankName':'其他农村商业银行','imgUrl':domain + 'bklogo_orc.png'},
        {'bankCode':'0313','bankName':'其他城市商业银行','imgUrl':domain + 'bklogo_occ.png'},
        {'bankCode':'0402','bankName':'其他农村信用合作社','imgUrl':domain + 'bklogo_orcc.png'}
    ]

    var totalAmount = 0; //可提现余额
    //查询银行卡信息
    function cardCheck(){
        $.ajax({
            url:'/userCenter/user/bank/userBandedBankOutSideUse',
            type:'get',
            async:false,
            cache:false,
            beforeSend:function(request){
                request.setRequestHeader('clientId','XXD_INTEGRATION_PLATFORM');
                request.setRequestHeader('clientTime', Date.now().valueOf());
                request.setRequestHeader('token',fakeToken);
            },
            success:function(data){
                if(data.code != '200000'){
                    if(data.code == '200403' || data.code == '200406' || data.code == '200407' || data.code == '200408' || data.code == '200600' || data.code == '200601' || data.code == '200602'){
                        alert(data.message);
                        return false;
                    }else if(data.code == '200400'){
                        alert('您的设备本地时间和北京时间差距较大，请确认和北京时间相同之后重新尝试！');
                        return false;
                    }else if(data.code == '200301' || data.code == '200302' || data.code == '200303' || data.code == '200304' || data.code == '200305'){
                        alert('登录状态异常，请重新登录！');
                        window.location.href = '/user/delSessionAndForwardToLogin.html';
                        return false;
                    }else if(data.code == '100500' || data.code == '100600' || data.code == '100700' || data.code == '100900'){
                        alert(data.message);
                        return false;
                    }else{
                        alert(data.message);
                        return false;
                    }
                }else{
                    if(data.data.code != 0){
                        if(data.data.code == -99){
                            $('#J_selectBank').find('span').html('请选择银行卡');
                        }else{
                            alert(data.data.message);
                            return false;
                        }
                    }else{
                        var index = -1;
                        $.each(bankList,function(i,item){
                            if(item.bankCode == data.data.data.bankcode){
                                index = i
                            }
                        });
                        if(index >= 0 && bankList[index].imgUrl) {
                            $('#J_selectBank').html('<span><img src="' + bankList[index].imgUrl + '">(' + data.data.data.bankaccount + ')</span>');
                        } else {
                            $('#J_selectBank').html('<span>' + bankList[index].bankName + '(' + data.data.data.bankaccount + ')</span>');
                        }
                    }
                }
            },
            error:function(){
                alert('网络异常，请重试！');
                return false;
            }
        });
    }

    //初始化信息
    function initCheck(){
       $.ajax({
            url:'/accountCenter/account/withdraw/initWithdraw',
            type:'get',
            async:false,
            cache:false,
            beforeSend:function(request){
                request.setRequestHeader('clientId','XXD_INTEGRATION_PLATFORM');
                request.setRequestHeader('clientTime',Date.now().valueOf());
                request.setRequestHeader('token',fakeToken);
            },
            success:function(data){
                if(data.code != '200000'){
                    if(data.code == '200403' || data.code == '200406' || data.code == '200407' || data.code == '200408' || data.code == '200600' || data.code == '200601' || data.code == '200602'){
                        alert(data.message);
                        return false;
                    }else if(data.code == '200400'){
                        alert('您的设备本地时间和北京时间差距较大，请确认和北京时间相同之后重新尝试！');
                        return false;
                    }else if(data.code == '200301' || data.code == '200302' || data.code == '200303' || data.code == '200304' || data.code == '200305'){
                        alert('登录状态异常，请重新登录！');
                        window.location.href = '/user/delSessionAndForwardToLogin.html';
                        return false;
                    }else if(data.code == '100500' || data.code == '100600' || data.code == '100700' || data.code == '100900'){
                        alert(data.message);
                        return false;
                    }else{
                        alert(data.message);
                        return false;
                    }
                }else{
                    cardCheck();
                    if(data.data.code != 0){
                        alert(data.data.message);
                        return false;
                    }else{
                        totalAmount = data.data.data.withdrawAmount;
                        $('#balance').html(data.data.data.balanceAmount);
                        $('#restdrawmoney').html(data.data.data.withdrawAmount);
                        $('#withdrawalsTime').html(data.data.data.userWithdrawCount);
                        $('#monthlyWithdrawals').html(data.data.data.configWithdrawCount);
                        if(data.data.data.isWhiteCash == 1){
                            $('.freetips').css('display','none');
                            $('.warmth-warn').find('p').eq(4).css('display','none');
                            $('.warmth-warn').find('p').eq(5).html('5、为了您的提现安全，若您在银行提现界面终止提现操作，消耗的可提现次数预计将于2个小时之后返回。');
                        }else if(data.data.data.isWhiteCash == 0){
                            if(data.data.data.configWithdrawCount < 0){
                                $('.freetips').css('display','none');
                                $('.warmth-warn').find('p').eq(4).css('display','none');
                                $('.warmth-warn').find('p').eq(5).css('display','none');
                            }else if(data.data.data.configWithdrawCount == 0){
                                $('.freetips').css('display','none');
                                $('.warmth-warn').find('p').eq(4).html('5、请联系客服或次月再进行提现。');
                                $('.warmth-warn').find('p').eq(5).css('display','none');
                            }else{
                                $('.freetips').css('display','block');
                                $('.warmth-warn').find('p').eq(4).css('display','block');
                                $('.warmth-warn').find('p').eq(5).css('display','block');
                            }
                        }else{
                            alert('用户状态信息获取失败！');
                        }
                    }
                }
            },
            error:function(){
                alert('网络异常，请重试！');
                return false;
            }
        }); 
    }
    
    //查询开户信息
    function accountCheck(){
        $.ajax({
            url:'/userCenter/user/userInfoByToken',
            type:'get',
            async:false,
            cache:false,
            beforeSend:function(request){
                request.setRequestHeader('clientId','XXD_INTEGRATION_PLATFORM');
                request.setRequestHeader('clientTime',Date.now().valueOf());
                request.setRequestHeader('token',fakeToken);
            },
            success:function(data){
                if(data.code != '200000'){
                    if(data.code == '200403' || data.code == '200406' || data.code == '200407' || data.code == '200408' || data.code == '200600' || data.code == '200601' || data.code == '200602'){
                        alert(data.message);
                        return false;
                    }else if(data.code == '200400'){
                        alert('您的设备本地时间和北京时间差距较大，请确认和北京时间相同之后重新尝试！');
                        return false;
                    }else if(data.code == '200301' || data.code == '200302' || data.code == '200303' || data.code == '200304' || data.code == '200305'){
                        alert('登录状态异常，请重新登录！');
                        window.location.href = '/user/delSessionAndForwardToLogin.html';
                        return false;
                    }else if(data.code == '100500' || data.code == '100600' || data.code == '100700' || data.code == '100900'){
                        alert(data.message);
                        return false;
                    }else{
                        alert(data.message);
                        return false;
                    }
                }else{
                    initCheck();
                    if(data.data.code != 0){
                        alert(data.data.message);
                        return false;
                    }else{
                        if(data.data.data.isopenaccount != 1){
                            dialog({
                                id: "",
                                content: "<div class='openornot'><a class='c_close' href='#'>×</a><div class='m-con-hd'>提示</div>" +
                                "<div class='m-con-bd'>" +
                                "<div class='filed-user'>  " +
                                "<p>尊敬的用户，为了提升您的资金安全，保证顺利投标、充值和提现等操作，建议您尽快开通华瑞银行存管账户</p> </div> " +
                                "<div class='filed-user'>  <p class='clearfix'><a class='btn_left left c_confirm' id='J_submitApply'>开通存管账户</a><a class='btn btn_right right c_close' >暂不开通</a></p> </div>" +
                                "</div>"
                                + "</div>",
                                cancel: function (clo) {
                                    clo.close();
                                },
                                confirm: function () {
                                    window.location.href = 'openAccount.html';
                                }
                            });
                        }else{
                            
                        }
                    }
                }
            },
            error:function(){
                alert('网络异常，请重试！');
                return false;
            }
        });
    }

    accountCheck();

    /* 输入框验证 */
    $('#drawmondy').on('focus',function(){
        $('#errorTip1').fadeOut(0);
    });

    //计算
    function calculate(){
        $.ajax({
            url:'/accountCenter/account/withdraw/withdrawCashTrial',
            type:'get',
            cache:false,
            data:{withdrawCashAmount:$('#drawmondy').val()},
            beforeSend:function(request){
                request.setRequestHeader('clientId','XXD_INTEGRATION_PLATFORM');
                request.setRequestHeader('clientTime',Date.now().valueOf());
                request.setRequestHeader('token',fakeToken);
            },
            success:function(data){
                if(data.code != '200000'){
                    if(data.code == '200403' || data.code == '200406' || data.code == '200407' || data.code == '200408' || data.code == '200600' || data.code == '200601' || data.code == '200602'){
                        alert(data.message);
                        return false;
                    }else if(data.code == '200400'){
                        alert('您的设备本地时间和北京时间差距较大，请确认和北京时间相同之后重新尝试！');
                        return false;
                    }else if(data.code == '200301' || data.code == '200302' || data.code == '200303' || data.code == '200304' || data.code == '200305'){
                        alert('登录状态异常，请重新登录！');
                        window.location.href = '/user/delSessionAndForwardToLogin.html';
                        return false;
                    }else if(data.code == '100500' || data.code == '100600' || data.code == '100700' || data.code == '100900'){
                        alert(data.message);
                        return false;
                    }else{
                        alert(data.message);
                        return false;
                    }
                }else{
                    if(data.data.code != 0){
                        $('#errorTip1').fadeIn(0);
                        $('#errorTip1').html(data.data.message);
                        return false;
                    }else{
                        $('#counter').html(data.data.data.cashFee);
                        $('#arrival').html(data.data.data.cashAmount);
                        $('#errorTip1').fadeOut(0);
                    }
                }
            },
            error:function(){
                alert('网络异常，请重试！');
                return false;
            }
        });
    }

    //提现计算
    $('#drawmondy').on('keyup',function(){
        num($(this));
        if($(this).val() == '' || $(this).val() == undefined || $(this).val() == null){
            return false;
        }else{
            calculate();
        }
    });

    //全额提现
    $('#sumdraw').on('click',function(){
        $('#drawmondy').val(totalAmount);
        calculate();
    });

    //提现
    var cashId;
    function withdrawals(){
        $.ajax({
            url:'/accountCenter/account/withdraw/doWithdrawCash',
            type:'post',
            async:false,
            cache:false,
            headers:{'Content-Type':'application/json'},
            data:'{ "data":{"withdrawSource":"pc","withdrawCashAmount":"' + $('#drawmondy').val() + '"}}',
            beforeSend:function(request){
                request.setRequestHeader('clientId','XXD_INTEGRATION_PLATFORM');
                request.setRequestHeader('clientTime',Date.now().valueOf());
                request.setRequestHeader('token',fakeToken);
            },
            success:function(data){
                if(data.code != '200000'){
                    if(data.code == '200403' || data.code == '200406' || data.code == '200407' || data.code == '200408' || data.code == '200600' || data.code == '200601' || data.code == '200602'){
                        alert(data.message);
                        return false;
                    }else if(data.code == '200400'){
                        alert('您的设备本地时间和北京时间差距较大，请确认和北京时间相同之后重新尝试！');
                        return false;
                    }else if(data.code == '200301' || data.code == '200302' || data.code == '200303' || data.code == '200304' || data.code == '200305'){
                        alert('登录状态异常，请重新登录！');
                        window.location.href = '/user/delSessionAndForwardToLogin.html';
                        return false;
                    }else if(data.code == '100500' || data.code == '100600' || data.code == '100700' || data.code == '100900'){
                        alert(data.message);
                        return false;
                    }else{
                        alert(data.message);
                        return false;
                    }
                }else{
                    if(data.data.code != 0){
                        if(data.data.code == -98){
                            dialog({
                                id: "",
                                content: "<div class='openornot'><a class='c_close' href='#'>×</a><div class='m-con-hd'>提示</div>" +
                                "<div class='m-con-bd'>" +
                                "<div class='filed-user'>  " +
                                "<p style='text-align:center;'>" + data.data.message + "</p> </div> " +
                                "<div class='filed-user'>  <p class='clearfix'><a class='btn c_close' style='margin:0 auto; display:block;'>确认</a></p> </div>" +
                                "</div>"
                                + "</div>",
                                cancel:function (clo) {
                                    clo.close();
                                },
                                confirm:function () {
                                    window.location.href = '/accountshow/myaccount.html';
                                }
                            });
                        }else if(data.data.code == -99){
                            dialog({
                                id: "",
                                content: "<div class='openornot'><a class='c_close' href='#'>×</a><div class='m-con-hd'>提示</div>" +
                                "<div class='m-con-bd'>" +
                                "<div class='filed-user'>  " +
                                "<p>" + data.data.message + "</p> </div> " +
                                "<div class='filed-user'>  <p class='clearfix'><a class='btn c_close' style='margin:0 auto; display:block;'>确认</a></p> </div>" +
                                "</div>"
                                + "</div>",
                                cancel:function (clo) {
                                    clo.close();
                                },
                                confirm:function () {
                                    window.location.href = '/accountshow/myaccount.html';
                                }
                            });
                        }else if(data.data.code == -97){
                            dialog({
                                id: "",
                                content: "<div class='openornot'><a class='c_close' href='#'>×</a><div class='m-con-hd'>提示</div>" +
                                "<div class='m-con-bd'>" +
                                "<div class='filed-user'>  " +
                                "<p style='text-align:center;'>请联系客服或次月再进行提现</p> </div> " +
                                "<div class='filed-user'>  <p class='clearfix'><a class='btn c_close' style='margin:0 auto; display:block;'>确认</a></p> </div>" +
                                "</div>"
                                + "</div>",
                                cancel:function (clo) {
                                    clo.close();
                                },
                                confirm:function () {
                                    window.location.href = '/accountshow/myaccount.html';
                                }
                            });
                        }else if(data.data.code == -1){
                            dialog({
                                id: "",
                                content: "<div class='openornot'><a class='c_close' href='#'>×</a><div class='m-con-hd'>提示</div>" +
                                "<div class='m-con-bd'>" +
                                "<div class='filed-user'>  " +
                                "<p style='text-align:center;'>" + data.data.message + "</p> </div> " +
                                "<div class='filed-user'>  <p class='clearfix'><a class='btn c_close' style='margin:0 auto; display:block;'>确认</a></p> </div>" +
                                "</div>"
                                + "</div>",
                                cancel:function (clo) {
                                    clo.close();
                                },
                                confirm:function () {
                                    window.location.href = '/accountshow/myaccount.html';
                                }
                            });
                        }else if(data.data.code == -3){
                            dialog({
                                id: "",
                                content: "<div class='openornot'><a class='c_close' href='#'>×</a><div class='m-con-hd'>提示</div>" +
                                "<div class='m-con-bd'>" +
                                "<div class='filed-user'>  " +
                                "<p>" + data.data.message + "</p> </div> " +
                                "<div class='filed-user'>  <p class='clearfix'><a class='btn c_close' style='margin:0 auto; display:block;'>确认</a></p> </div>" +
                                "</div>"
                                + "</div>",
                                cancel:function (clo) {
                                    clo.close();
                                },
                                confirm:function () {
                                    window.location.href = '/accountshow/myaccount.html';
                                }
                            });
                        }else{
                            alert(data.data.message);
                        }
                        return false;
                    }else{
                        cashId = data.data.data.mchnt_txn_ssn;
                        $('#newForm1').attr('action',data.data.data.requestUrl);
                        $('#amt').val(data.data.data.amt);
                        $('#back_notify_url').val(data.data.data.back_notify_url);
                        $('#login_id').val(data.data.data.login_id);
                        $('#mchnt_cd').val(data.data.data.mchnt_cd);
                        $('#mchnt_txn_ssn').val(data.data.data.mchnt_txn_ssn);
                        $('#page_notify_url').val(data.data.data.page_notify_url);
                        $('#signature').val(data.data.data.signature);
                        $('#newForm1').submit();
                        $.ajax({
                            url:'/accountCenter/account/withdraw/initWithdraw',
                            type:'get',
                            cache:false,
                            beforeSend:function(request){
                                request.setRequestHeader('clientId','XXD_INTEGRATION_PLATFORM');
                                request.setRequestHeader('clientTime',Date.now().valueOf());
                                request.setRequestHeader('token',fakeToken);
                            },
                            success:function(data){
                                if(data.code != '200000'){
                                    if(data.code == '200403' || data.code == '200406' || data.code == '200407' || data.code == '200408' || data.code == '200600' || data.code == '200601' || data.code == '200602'){
                                        alert(data.message);
                                        return false;
                                    }else if(data.code == '200400'){
                                        alert('您的设备本地时间和北京时间差距较大，请确认和北京时间相同之后重新尝试！');
                                        return false;
                                    }else if(data.code == '200301' || data.code == '200302' || data.code == '200303' || data.code == '200304' || data.code == '200305'){
                                        alert('登录状态异常，请重新登录！');
                                        window.location.href = '/user/delSessionAndForwardToLogin.html';
                                        return false;
                                    }else if(data.code == '100500' || data.code == '100600' || data.code == '100700' || data.code == '100900'){
                                        alert(data.message);
                                        return false;
                                    }else{
                                        alert(data.message);
                                        return false;
                                    }
                                }else{
                                    if(data.data.code != 0){
                                        alert(data.data.message);
                                        return false;
                                    }else{
                                        totalAmount = data.data.data.withdrawAmount;
                                        $('#balance').html(data.data.data.balanceAmount);
                                        $('#restdrawmoney').html(data.data.data.withdrawAmount);
                                        $('#withdrawalsTime').html(data.data.data.userWithdrawCount);
                                        $('#monthlyWithdrawals').html(data.data.data.configWithdrawCount);
                                        if(data.data.data.isWhiteCash == 1){
                                            $('.freetips').css('display','none');
                                            $('.warmth-warn').find('p').eq(4).css('display','none');
                                            $('.warmth-warn').find('p').eq(5).html('5、为了您的提现安全，若您在银行提现界面终止提现操作，消耗的可提现次数预计将于2个小时之后返回。');

                                        }else if(data.data.data.isWhiteCash == 0){
                                            if(data.data.data.configWithdrawCount < 0){
                                                $('.freetips').css('display','none');
                                                $('.warmth-warn').find('p').eq(4).css('display','none');
                                                $('.warmth-warn').find('p').eq(5).css('display','none');
                                            }else if(data.data.data.configWithdrawCount == 0){
                                                $('.freetips').css('display','none');
                                                $('.warmth-warn').find('p').eq(4).html('5、请联系客服或次月再进行提现。');
                                                $('.warmth-warn').find('p').eq(5).css('display','none');
                                            }else{
                                                $('.freetips').css('display','block');
                                                $('.warmth-warn').find('p').eq(4).css('display','block');
                                                $('.warmth-warn').find('p').eq(5).css('display','block');
                                            }
                                        }else{
                                            alert('用户状态信息获取失败！');
                                        }
                                    }
                                }
                            },
                            error:function(){
                                alert('网络异常，请重试！');
                                return false;
                            }
                        });
                        dialog({
                            id: "",
                            content: '<div class="openornot"><a class="c_close" href="#">×</a><div class="m-con-hd">提示</div>' +
                                '<div class="m-con-bd">' +
                                '<div class="filed-user">' +
                                '<p>提现完成前请不要关闭此窗口，完成操作后根据您的情况点击下面按钮。</p> </div>' +
                                '<div class="filed-user">  <p class="clearfix"><a href="javascript:window.location.reload();" class="btn_left left c_confirm" id="J_submitApply">提现完成</a><a class="btn btn_right right c_close" >暂不提现</a></p> </div>' +
                                '</div>'
                                + '</div>',
                            cancel: function (clo) {
                                calculate();
                                $.ajax({
                                    url:'/accountCenter/account/withdraw/withdrawStatus',
                                    type:'get',
                                    data:{cashId:cashId},
                                    async:false,
                                    cache:false,
                                    beforeSend:function(request){
                                        request.setRequestHeader('clientId','XXD_INTEGRATION_PLATFORM');
                                        request.setRequestHeader('clientTime',Date.now().valueOf());
                                        request.setRequestHeader('token',fakeToken);
                                    },
                                    success:function(data){
                                        if(data.code != '200000'){
                                            if(data.code == '200403' || data.code == '200406' || data.code == '200407' || data.code == '200408' || data.code == '200600' || data.code == '200601' || data.code == '200602'){
                                                alert(data.message);
                                                return false;
                                            }else if(data.code == '200400'){
                                                alert('您的设备本地时间和北京时间差距较大，请确认和北京时间相同之后重新尝试！');
                                                return false;
                                            }else if(data.code == '200301' || data.code == '200302' || data.code == '200303' || data.code == '200304' || data.code == '200305'){
                                                alert('登录状态异常，请重新登录！');
                                                window.location.href = '/user/delSessionAndForwardToLogin.html';
                                                return false;
                                            }else if(data.code == '100500' || data.code == '100600' || data.code == '100700' || data.code == '100900'){
                                                alert(data.message);
                                                return false;
                                            }else{
                                                alert(data.message);
                                                return false;
                                            }
                                        }else{
                                            if(data.data.code != 0){
                                                clo.close();
                                                dialog({
                                                    id: "",
                                                    content: "<div class='openornot'><a class='c_close' href='#'>×</a><div class='m-con-hd'>提示</div>" +
                                                    "<div class='m-con-bd'>" +
                                                    "<div class='filed-user'>  " +
                                                    "<p style='text-align:center;'>" + data.data.message + "</p> </div> " +
                                                    "<div class='filed-user'>  <p class='clearfix'><a class='btn c_close' style='margin:0 auto; display:block;'>确认</a></p> </div>" +
                                                    "</div>"
                                                    + "</div>",
                                                    cancel:function (clo) {
                                                        clo.close();
                                                    },
                                                    confirm:function () {
                                                        window.location.reload();
                                                    }
                                                });
                                                return false;
                                            }else{
                                                clo.close();
                                                dialog({
                                                    id: "",
                                                    content: "<div class='openornot'><a class='c_close' href='#'>×</a><div class='m-con-hd'>提示</div>" +
                                                    "<div class='m-con-bd'>" +
                                                    "<div class='filed-user'>  " +
                                                    "<p style='text-align:center;'>提现成功</p> </div> " +
                                                    "<div class='filed-user'>  <p class='clearfix'><a class='btn c_close' style='margin:0 auto; display:block;'>确认</a></p> </div>" +
                                                    "</div>"
                                                    + "</div>",
                                                    cancel:function (clo) {
                                                        window.location.reload();
                                                    },
                                                    confirm:function () {
                                                        window.location.reload();
                                                    }
                                                });
                                            }
                                        }
                                    },
                                    error:function(){
                                        alert('网络异常，请重试！');
                                    }
                                });
                            },
                            confirm: function (dir) {
                                $.ajax({
                                    url:'/accountCenter/account/withdraw/withdrawStatus',
                                    type:'get',
                                    data:{cashId:cashId},
                                    async:false,
                                    cache:false,
                                    beforeSend:function(request){
                                        request.setRequestHeader('clientId','XXD_INTEGRATION_PLATFORM');
                                        request.setRequestHeader('clientTime',Date.now().valueOf());
                                        request.setRequestHeader('token',fakeToken);
                                    },
                                    success:function(data){
                                        if(data.code != '200000'){
                                            if(data.code == '200403' || data.code == '200406' || data.code == '200407' || data.code == '200408' || data.code == '200600' || data.code == '200601' || data.code == '200602'){
                                                alert(data.message);
                                                return false;
                                            }else if(data.code == '200400'){
                                                alert('您的设备本地时间和北京时间差距较大，请确认和北京时间相同之后重新尝试！');
                                                return false;
                                            }else if(data.code == '200301' || data.code == '200302' || data.code == '200303' || data.code == '200304' || data.code == '200305'){
                                                alert('登录状态异常，请重新登录！');
                                                window.location.href = '/user/delSessionAndForwardToLogin.html';
                                                return false;
                                            }else if(data.code == '100500' || data.code == '100600' || data.code == '100700' || data.code == '100900'){
                                                alert(data.message);
                                                return false;
                                            }else{
                                                alert(data.message);
                                                return false;
                                            }
                                        }else{
                                            if(data.data.code != 0){
                                                dir.close();
                                                dialog({
                                                    id: "",
                                                    content: "<div class='openornot'><a class='c_close' href='#'>×</a><div class='m-con-hd'>提示</div>" +
                                                    "<div class='m-con-bd'>" +
                                                    "<div class='filed-user'>  " +
                                                    "<p style='text-align:center;'>" + data.data.message + "</p> </div> " +
                                                    "<div class='filed-user'>  <p class='clearfix'><a class='btn c_close' style='margin:0 auto; display:block;'>确认</a></p> </div>" +
                                                    "</div>"
                                                    + "</div>",
                                                    cancel:function (clo) {
                                                        clo.close();
                                                    },
                                                    confirm:function () {
                                                        window.location.reload();
                                                    }
                                                });
                                                return false;
                                            }else{
                                                dir.close();
                                                dialog({
                                                    id: "",
                                                    content: "<div class='openornot'><a class='c_close' href='#'>×</a><div class='m-con-hd'>提示</div>" +
                                                    "<div class='m-con-bd'>" +
                                                    "<div class='filed-user'>  " +
                                                    "<p style='text-align:center;'>提现成功</p> </div> " +
                                                    "<div class='filed-user'>  <p class='clearfix'><a class='btn c_close' style='margin:0 auto; display:block;'>确认</a></p> </div>" +
                                                    "</div>"
                                                    + "</div>",
                                                    cancel:function (clo) {
                                                        window.location.reload();
                                                    },
                                                    confirm:function () {
                                                        window.location.reload();
                                                    }
                                                });
                                            }
                                        }
                                    },
                                    error:function(){
                                        alert('网络异常，请重试！');
                                    }
                                });
                            }
                        });
                    }
                }
            },
            error:function(){
                alert('网络异常，请重试！');
                return false;
            }
        });
    }

    //提现流程
    $("#setOkbtn").on("click", function () {
        $('#errorTip1').fadeOut(0);
        $.ajax({
            url:'/userCenter/user/userInfoByToken',
            type:'get',
            async:false,
            cache:false,
            beforeSend:function(request){
                request.setRequestHeader('clientId','XXD_INTEGRATION_PLATFORM');
                request.setRequestHeader('clientTime',Date.now().valueOf());
                request.setRequestHeader('token',fakeToken);
            },
            success:function(data){
                if(data.code != '200000'){
                    if(data.code == '200403' || data.code == '200406' || data.code == '200407' || data.code == '200408' || data.code == '200600' || data.code == '200601' || data.code == '200602'){
                        alert(data.message);
                        return false;
                    }else if(data.code == '200400'){
                        alert('您的设备本地时间和北京时间差距较大，请确认和北京时间相同之后重新尝试！');
                        return false;
                    }else if(data.code == '200301' || data.code == '200302' || data.code == '200303' || data.code == '200304' || data.code == '200305'){
                        alert('登录状态异常，请重新登录！');
                        window.location.href = '/user/delSessionAndForwardToLogin.html';
                        return false;
                    }else if(data.code == '100500' || data.code == '100600' || data.code == '100700' || data.code == '100900'){
                        alert(data.message);
                        return false;
                    }else{
                        alert(data.message);
                        return false;
                    }
                }else{
                    if(data.data.code != 0){
                        alert(data.data.message);
                        return false;
                    }else{
                        if(data.data.data.isopenaccount != 1){
                            dialog({
                                id: "",
                                content: "<div class='openornot'><a class='c_close' href='#'>×</a><div class='m-con-hd'>提示</div>" +
                                "<div class='m-con-bd'>" +
                                "<div class='filed-user'>  " +
                                "<p>尊敬的用户，为了提升您的资金安全，保证顺利投标、充值和提现等操作，建议您尽快开通华瑞银行存管账户</p> </div> " +
                                "<div class='filed-user'>  <p class='clearfix'><a class='btn_left left c_confirm' id='J_submitApply'>开通存管账户</a><a class='btn btn_right right c_close' >暂不开通</a></p> </div>" +
                                "</div>"
                                + "</div>",
                                cancel: function (clo) {
                                    clo.close();
                                },
                                confirm: function () {
                                    window.location.href = 'openAccount.html';
                                }
                            });
                        }else{
                            if($('#drawmondy').val() != '' && $('#drawmondy').val() != undefined && $('#drawmondy').val() != null){
                                if(parseInt($('#drawmondy').val()) < 5){
                                    $('#errorTip1').fadeIn(0);
                                    $('#errorTip1').html('提现金额不能少于5元');
                                    return false;
                                }else{
                                    $('#errorTip1').fadeOut(0);
                                    withdrawals();
                                }
                            }else{
                                $('#errorTip1').fadeIn(0);
                                $('#errorTip1').html('请填写提现金额');
                                return false;
                            }
                        }
                    }
                }
            },
            error:function(){
                alert('网络异常，请重试！');
                return false;
            }
        });
    });

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});