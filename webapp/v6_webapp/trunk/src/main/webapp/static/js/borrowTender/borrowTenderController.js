/**
 * 投标
 */
define(['js/borrowTender/borrowTenderView','js/account/openOnAccount'], function (BorrowTenderView,openOnAccount) {
    var redPacketList = [];
    var borrowTenderCtrl = {
        init: function (event) {
            var page = appFunc.getEventDetailPageQuery(event);
            var borrowId = page.borrowId;
            borrowTenderCtrl.loadBorrowInfo(borrowId);
            borrowTenderCtrl.loadBorrowTenderAgreement();
            borrowTenderCtrl.borrowBindEvent();
        },

        borrowBindEvent:function(){
            var bindings = [
                {
                    element: '#confirmTender',
                    event: 'click',
                    handler: borrowTenderCtrl.affirmTender
                },
                {
                    element: '#accountnumber',
                    event: 'keyup',
                    handler: borrowTenderCtrl.tenderAccount
                },
                {
                	element: '#accountnumber',
                	event: 'blur',
                	handler: borrowTenderCtrl.blurTenderAccount
                },
                {
                    element: '#topup_step',
                    event: 'click',
                    handler: borrowTenderCtrl.topup
                },
                {
                 element: '#borrow_redpacketSelect',
                 event: 'change',
                 handler: borrowTenderCtrl.changeTender
                 }
            ];

            BorrowTenderView.bindEvent({
                        bindings: bindings
                    }
            );
        },

        loadBorrowInfo:function(borrowId){
            req.callJSON({
                url: "borrow/tender/quick.do",
                data: {borrowId: borrowId},
                indicator: true,
                success: function (data) {
                    if (data.resultCode != 0) {
                        xxdApp.addNotification({
                            title: '温馨提示',
                            hold: 3000,
                            message: data.msg
                        });
                        return;
                    }

                    BorrowTenderView.tenderDetail(data);
                    $('#v_lowesttender').val(appFunc.fmoney(data.borrow.LOWESTTENDER,2));

                    if (data.redpacketList != null) {
                        redPacketList = data.redpacketList;
                        if (redPacketList.length == 0) {
                            $('#borrow_redpacketSelect').hide();
                            return false;
                        }
                    }
                }
            });
        },

        loadBorrowTenderAgreement:function(){
            req.callGet({
                url: GC.getHtmlPath() + 'borrowTender/borrowTenderAgreement.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    $$(".popup-bidhistory").html(result);
                }
            });
        },
        affirmTender: function () {
            openOnAccount.isOpenOnAccount({
                title: '应监管需要，您需要先开通银行存管账户才能顺利投资哦~',
                callBack: function () {
                    borrowTenderCtrl.affirmTenderEnd();
                }
            });
        },

        /**
         * 确认投标
         */
        affirmTenderEnd: function () {

            if(appFunc.isRiskResult()) {
                return false;
            }

        	try {
        		 var productName = $$("#borrowType").val();
            	 var borroyTimeLimit = $$("#borroyTimeLimit").val();
            	 var borroyRate =  $$("#borroyRate").val();
        		//XXD_TRACK._trackEvent({category: "webapp_borrow_in", action: "affirm_invest", label: "确认投标", value: $$("#accountnumber").val(), custval: "" });
        		//进入结账
        		var borrowName = "散标直投"+$$("#borrowId").val();
        		var categorys = "散标直投/"+borroyRate+"%/"+borroyTimeLimit+"个月/"+productName;
        		CheckOut({id:$$("#borrowId").val(),name:borrowName,category:categorys});
        		
        	} catch (e) {}
            if (!appFunc.isLogin()) {
                xxdApp.loginScreen();
                return;
            }

            //判断是否输入投标金额
            var accountnumber = $$("#accountnumber").val();
            var userAccount = $$("#userAccount").val();
            var borrowlackaccount = $$("#borrowlackaccount").val();
            var borrow_affirm_money = $('#borrow_affirm_money').val();
            var lowesttender = $('#v_lowesttender').val();

            if (!accountnumber) {
                xxdApp.alert('必须填写投标金额', '提示');
                return false;
            }
            if (!appFunc.isFloat(accountnumber)) {
                xxdApp.alert('请正确填写投标金额', '提示');
                return false;
            }

            if (parseFloat(userAccount) < parseFloat(borrow_affirm_money)) {
                xxdApp.alert('账户余额不足，请充值', '提示');
                return false;
            }
            if (parseFloat(accountnumber) > parseFloat(borrowlackaccount)) {
                xxdApp.alert("购买金额大于剩余可投金额！", '提示');
                return false;
            }
            //---当剩余可投金额小于投标限额，购买金额可以小于投标限额
            if (parseFloat(borrowlackaccount)>=parseFloat(lowesttender)) {
                if (parseFloat(accountnumber) < parseFloat(lowesttender)) {
                 xxdApp.alert("购买金额小于投标限额！", '提示');
                 return false;
                 }
            }

            if (!$$("#agreement").is(':checked')) {
                xxdApp.alert("请阅读并同意《新新贷资金出借风险提示函》！", '提示');
                return false;
            }

            var borrowId = $$("#borrowId").val();
            var type = $$("#type").val();

            xxdApp.modalPassword('确认从您的账户扣除' + appFunc.fmoney(borrow_affirm_money, 2) + '元用以投标，请输入支付密码', '支付确认', function (password) {
            	 //try {XXD_TRACK._trackEvent({category: "webapp_borrow_in", action: "popup_affirm_paypass", label: "支付密码确定", value: "", custval: "" });} catch (e) {}
                if (password == null || password == '') {
                    xxdApp.alert('请输入支付密码！');
                    return;
                }
                $$("#confirmTender").attr("disabled", "disabled");
                $$("#confirmTender").html("投标中...");


                xxdApp.showIndicator('正在努力投标...');
                req.callPost({
                    url: 'product/investOrder.do',
                    data: {
                        "pId": borrowId,
                        "pType":type,
                        "pCategory":1,
                        "payPwd": $.md5(password),
                        "tenderAmount": accountnumber,
                        "redEnvelopeCode": $.trim($("#borrow_redpacketSelect").val())
                    },

                    dataType: 'json',
                    timeout: 20000,
                    success: function (result) {
                        xxdApp.hideIndicator();
                        $$("#confirmTender").removeAttr("disabled");
                        $$("#confirmTender").html("确认投标");
                        if (result.code == 300) {
                        	try {XXD_TRACK._trackEvent({category: "webapp_borrow_in", action: "borrow_invest_success", label: "投标失败", value: borrow_affirm_money, custval: "0" });} catch (e) {}
                            xxdApp.alert('您还没有登录或长时间未操作，请先登陆', '登陆提示', function () {
                                xxdApp.loginScreen();
                            });
                        }


                        if (result.code == 200000) {
                            var bizStatus = result.data.bizStatus;
                            if(bizStatus != undefined && bizStatus.code == 'SUCCESS') {
                                xxdApp.alert('投标成功！', '恭喜', function () {
                                    GS.loadPage("borrow/borrowListV2.html");
                                });
                            } else {
                                xxdApp.alert(result.data.bizStatus.message, '提示', function () {
                                });
                            }
                        } else {

                            var msg = result.message;

                            if(result.data != undefined && result.data.bizStatus != undefined && result.data.bizStatus.code != 'SUCCESS') {
                                msg = result.data.bizStatus.message;
                            }

                            msg = msg == '' || msg == undefined ? '投标失败，请重新尝试' : msg;


                            if(result.code == 200305) {
                                msg = "您的账号在别处已登录，请重新登录";
                                xxdApp.alert(msg, '提示', function () {
                                    req.callGet({
                                        url: 'user/logout.do',
                                        success: function (result) {
                                            if (result == '0') {
                                                try {
                                                    XXD_TRACK.track_eventview("logout_success_webapp", "button", "退出成功");
                                                } catch (e) {
                                                }
                                                xxdApp.alert('您已安全退出，祝您生活愉快', '提示', function () {
                                                    GS.reloadPage("index/home.html");
                                                });
                                            }
                                        }
                                    });
                                });
                            } else if (result.code == -3) {
                                xxdApp.alert(msg, '提示', function () {
                                });
                            } else {
                                xxdApp.alert(msg, '提示', function () {
                                    GS.loadPage("borrow/borrowListV2.html");
                                });
                            }

                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                    	//try {XXD_TRACK._trackEvent({category: "webapp_borrow_in", action: "borrow_invest_success", label: "投标失败", value: borrow_affirm_money, custval: "0" });} catch (e) {}
                        xxdApp.hideIndicator();
                        $$("#confirmTender").removeAttr("disabled");
                        $$("#confirmTender").html("确认投标");
                        xxdApp.hidePreloader();
                        xxdApp.alert('系统无响应，请查看投标记录是否投标成功或者重新投标', '抱歉', function () {
                            GS.loadPage("borrow/borrowListV2.html");
                        });
                    }
                });

            }, function (password) {
              	 //try {XXD_TRACK._trackEvent({category: "webapp_borrow_in", action: "popup_affirm_cancel", label: "支付密码取消", value: "", custval: "" });} catch (e) {}
                 if (password == null || password == '') {
                     return;
                 }
             });
        },

        tenderAccount: function () {
            var accountnumber = $$("#accountnumber").val();
            if (accountnumber == null || accountnumber == '') {
                $('#borrow_affirm_money_show').html("0.00");
                $('#borrow_affirm_money').val(0);
                return;
            }
            var userAccount = $$("#userAccount").val();
            var borrowlackaccount = $$("#borrowlackaccount").val();
            if (parseFloat(userAccount) >= parseFloat(borrowlackaccount)) {
                //判断输入金额是否大于可投余额
                if (parseFloat(accountnumber) > parseFloat(borrowlackaccount)) {
                    accountnumber = borrowlackaccount;
                    $$("#accountnumber").val(parseFloat(borrowlackaccount));
                }
            } else {
                //判断输入金额是否大于账户余额
                if (parseFloat(accountnumber) > parseFloat(userAccount)) {
                    accountnumber = userAccount;
                    $$("#accountnumber").val(parseFloat(userAccount));
                }
            }
            var borrow_affirm_money = 0.00;
            borrow_affirm_money = parseFloat(accountnumber);

            $('#borrow_affirm_money_show').html(appFunc.fmoney(borrow_affirm_money, 2));
            $('#borrow_affirm_money').val(borrow_affirm_money);

            borrowTenderCtrl.showRedPacke(accountnumber);
        },
        blurTenderAccount:function(){
        	//大数据部码
            //try {XXD_TRACK._trackEvent({category: "webapp_borrow_in", action: "invest_money", label: "投标金额", value: $$("#accountnumber").val(), custval: "" });} catch (e) {}
        },
        changeTender: function () {
            var accountnumber = $$("#accountnumber").val();
            var borrow_redpacketSelectVal = $('#borrow_redpacketSelect').val();
            var redpacketfacevalue = $("#" + borrow_redpacketSelectVal + "_faceValue_borrow").val() == null ? 0 : $("#" + borrow_redpacketSelectVal + "_faceValue_borrow").val();
            var redpacketQuota = $("#" + borrow_redpacketSelectVal + "_quota_borrow").val();
            //总额+红包金额<最小限额
            if (redpacketfacevalue != null && redpacketfacevalue != "" && parseFloat(accountnumber) < parseFloat(redpacketQuota)) {
                xxdApp.alert('红包使用最小限额:￥' + appFunc.fmoney(redpacketQuota, 2), '提示');
                //$('#borrow_redpacketSelect option').eq(0).attr('selected', 'true');
                $('#borrow_affirm_money_show').html(appFunc.fmoney(accountnumber, 2));
                $('#borrow_affirm_money').val(appFunc.fmoney(accountnumber, 2));
                return false;
            }

            var borrow_affirm_money = 0.00;
            if (accountnumber) {
                borrow_affirm_money = parseFloat(accountnumber) - parseFloat(redpacketfacevalue);
            }

            $('#borrow_affirm_money_show').html(appFunc.fmoney(borrow_affirm_money, 2));
            $('#borrow_affirm_money').val(borrow_affirm_money);
            //try {XXD_TRACK._trackEvent({category: "webapp_borrow_in", action: "borrow_red", label: "使用红包", value: redpacketfacevalue, custval: "" });} catch (e) {}
            return true;
        },
        /**
         * 红包展现
         * @param accountnumber
         */
        showRedPacke: function (accountnumber) {
            if(redPacketList == null || redPacketList.length == 0) {
                return;
            }

            var tempRedPackeList = [];
            var red;
            for(var i = 0; i < redPacketList.length; i++) {
                red = redPacketList[i];
                if(accountnumber >= red.quota){
                    tempRedPackeList.push(red);
                }
            }
            var html = "<option value=''>不使用红包</option>";
            if(tempRedPackeList.length == 0) {
                $('#borrow_redpacketSelect').html(html);
                return;
            }

            for (var i = 0; i < tempRedPackeList.length; i++) {
                var b = tempRedPackeList[i];
                html += '<option value=' + b.redCode + '>' + b.name + '</option>';
                html += '<input type="hidden" id="' + b.redCode + '_faceValue_borrow" value="' + b.faceValue + '"/>';
                html += '<input type="hidden" id="' + b.redCode + '_quota_borrow" value="' + b.quota + '"/>';
            }

            $('#borrow_redpacketSelect').html(html);
        },

        topup: function () {
        	//try {XXD_TRACK._trackEvent({category: "webapp_borrow_in", action: "borrow_recharge", label: "充值", value: "", custval: "" });} catch (e) {}
            GS.goTopup();
        }
    };

    return borrowTenderCtrl;
});
