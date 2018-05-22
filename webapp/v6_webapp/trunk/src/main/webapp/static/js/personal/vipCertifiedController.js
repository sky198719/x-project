/**
 * vip认证
 * Created by pufei on 2015/2/10.
 */
define(['js/personal/vipCertifiedView'], function (vipCertifiedView) {
    var formToken = {tokenName: '', token: ''};
    var empNameArray = [];
    var serviceNumArray = [];
    var serviceNum = ""; 	//客服编号
    var exclusivekf = ""; 	//客服姓名
    var vipCertifiedCtrl = {
        init: function (event) {
            var page = appFunc.getEventDetailPageQuery(event);
			
            //设置表单token
            formToken = appFunc.setToken({name: "VIP_CERTIFIED", id: ""});
            
            serviceNum = page.serviceNum;
            serviceNum = (serviceNum == undefined || serviceNum == null) ? "" : serviceNum ;
            $$("#serviceNum").val(serviceNum);
            exclusivekf = page.exclusivekf;
            exclusivekf = (exclusivekf == undefined || exclusivekf == null) ? "" : exclusivekf ;
            vipCertifiedCtrl.loadVipCertified(serviceNum,exclusivekf);
            vipCertifiedCtrl.bindEvent();
        },

        bindEvent: function () {
            /**
             * 事件定义
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '#vip-certified-start',
                    event: 'click',
                    handler: vipCertifiedCtrl.submitUpdvip
                },
                {
                    element: '#serviceNumShow',
                    event: 'click',
                    handler: vipCertifiedCtrl.goChooseVip
                }
            ];
            vipCertifiedView.init({
                bindings: bindings
            });
        },
        loadVipCertified: function (serviceNum,exclusivekf) {
            req.callJSON({
                url: "personal/vipCertified.do",
                data: {},
                indicator: true,
                success: function (data) {
                    var customServices = data.customServices;
                    //var html='';
                    var vipkey = [];
                    vipkey[0] = "请选择";
                    var vipValues = [];
                    vipValues[0] = "";
                    // var isSelect = false;
                    for (var i = 0; i < customServices.length; i++) {
                        var b = customServices[i];
                        vipkey[i + 1] = b.pkey;
                        vipValues[i + 1] = b.pvalue;
                    }

                    var vipDefault = '';
                    for (var i = 0; i < vipValues.length; i++) {
                        if (vipValues[i] == serviceNum) {
                            vipDefault = vipkey[i];
                            break;
                        }
                    }

                    var pickerVip = xxdApp.picker({
                        input: '#picker-vip',
                        rotateEffect: true,
                        toolbarTemplate: '<div class="toolbar">' +
                                '<div class="toolbar-inner">' +
                                '<div class="left"><a href="#" class="link toolbar-randomize-link">取消</a></div>' +
                                '<div class="right">' +
                                '<a href="#" class="link close-picker">确定</a>' +
                                '</div>' +
                                '</div> ' +
                                '</div> ',
                        cols: [
                            {
                                textAlign: 'center',
                                values: vipkey
                            }
                        ],
                        onChange: function (p, value, displayValue) {
                            for (var i = 0; i < vipkey.length; i++) {
                                if (vipkey[i] == value) {
                                	$$("#serviceNum").val(vipValues[i]);
                                    $$("#serviceNumShow").val(vipValues[i]);
                                    break;
                                }
                            }

                            if (value == '请选择') {
                                $$("#serviceNumShow").attr("placeholder", "请输入客服编号");
                            }
                        },
                        onOpen: function (picker) {
                            if (vipDefault != '') {
                                picker.cols[0].setValue(vipDefault);
                            }
                            picker.container.find('.toolbar-randomize-link').on('click', function () {
                                picker.close();
                            });
                        }
                    });

//                    if (vipDefault != '') {
//                        pickerVip.open();
//                    }
                    if(exclusivekf != ""){
                    	$$("#serviceNumShow").val(exclusivekf+"("+serviceNum+")");
                    }else{
	                    $$("#serviceNumShow").val(serviceNum);
                    }
                }
            });
        },

        goChooseVip: function () {
            GS.loadPage("personal/chooseVip.html?path=personal&serviceNum="+serviceNum+"&exclusivekf="+exclusivekf);
        },

        submitUpdvip: function () {

            
            if (formToken.code != 0) {
                xxdApp.alert("初始化表单失败，请返回重新进入", "抱歉");
                return;
            }

            var vipCode = $$("#serviceNum").val();
            if (vipCode == "") {
                xxdApp.alert("请输入客服编号", '提示');
                return false;
            }
//            console.log("vipCode:" + vipCode);
            xxdApp.showIndicator('正在努力申请，请稍后...');
            req.callJSON({
                url: "approve/vipApply.do",
                data: {
                    "serviceNum": vipCode,
                    "tokenName": formToken.data.tokenName,
                    "token": formToken.data.token
                },
                success: function (data) {
                    xxdApp.hideIndicator();
                    if (data.resultCode == 1) {
                        xxdApp.alert(data.msg, '恭喜', function () {
                            GS.loadPage('personal/personalInfo.html');
                        })
                    } else if (data.resultCode == -25) {
                        xxdApp.alert("您已绑定财富顾问，如需修改，请联系客服4000-169-521", '抱歉');
                        //设置表单token
                        formToken = appFunc.setToken({name: "VIP_CERTIFIED", id: ""});
                    } else {
                        xxdApp.alert(data.msg, '抱歉');
                        //设置表单token
            			formToken = appFunc.setToken({name: "VIP_CERTIFIED", id: ""});
                    }
                }
            });
        }
    }
    return vipCertifiedCtrl;
})