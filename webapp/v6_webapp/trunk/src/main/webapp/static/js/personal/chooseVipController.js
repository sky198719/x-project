/**
 * vip认证--选择vip
 */
define(['js/personal/vipCertifiedView'], function (vipCertifiedView) {
    var empNameArray = [];
    var serviceNumArray = [];
    var serviceNum = ""; 	//客服编号
    var exclusivekf = ""; 	//客服姓名
    var time;
    var chooseVipCtrl = {
        init: function (event) {
        	clearInterval(time);  
            var page = appFunc.getEventDetailPageQuery(event);

            serviceNum = page.serviceNum;
            serviceNum = (serviceNum == undefined || serviceNum == null) ? "" : serviceNum ;
            exclusivekf = page.exclusivekf;
            exclusivekf = (exclusivekf == undefined || exclusivekf == null) ? "" : exclusivekf ;
            chooseVipCtrl.loadData();
            chooseVipCtrl.bindEvent();
        },

        bindEvent: function () {
            /**
             * 事件定义
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '.chooseVip .searchbarInput',
                    event: 'focus',
                    handler: chooseVipCtrl.searchbarInputFocus
                },
                {
                    element: '.chooseVip .searchbarInput',
                    event: 'blur',
                    handler: chooseVipCtrl.searchbarInputBlur
                },
                {
                    element: '.chooseVip .defaultValue',
                    event: 'click',
                    handler: chooseVipCtrl.getDefaultValue
                }
            ];
            appFunc.bindEvents(bindings);
        },
        loadData: function () {
             req.callJSON({
                url: "approve/getEmpNoAndName.do",
                data: {},
                indicator: true,
                success: function (data) {
                	
                	if (data.checkVipExp == null || data.checkVipExp == undefined || data.checkVipExp.length == 0) {
                        $$(".chooseVip #vipexp").val("^[A-Za-z]{3}[0-9]{5}$");
                    } else {
//                    	console.log("checkVipExp" + data.checkVipExp);
                        $$(".chooseVip #vipexp").val(data.checkVipExp);
                    }
                    
                    empNameArray = data.empName;
                    serviceNumArray = data.serviceNum;

                    var autocompleteData = new Array();
                    for (var i = 0; i < empNameArray.length; i++) {
                        autocompleteData.push({"vipCode":serviceNumArray[i],"vipName":empNameArray[i],"vipItem":empNameArray[i] + '(' + serviceNumArray[i] + ')'});
                    }
                    chooseVipCtrl.showVipList({data:autocompleteData,callBack : function () {
                        var bing = [
                            {
                                element: '.chooseVip .vipList li',
                                event: 'click',
                                handler: chooseVipCtrl.vipChoosed
                            }
                        ];
                        appFunc.bindEvents(bing);
                    }});
                    
                }
            });
        },

        showVipList: function (param) {
            req.callGet({
                url: GC.getHtmlPath() + 'personal/chooseVipItem.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    var compiledTemplate = t7.compile(result);
                    var output = compiledTemplate({list: param.data});
                    $$(".chooseVip .vipList").html(output);
                    param.callBack();
                }
            });
        },
        
        vipChoosed: function () {
        	var vipCode = $$(this).data("vipCode");
        	var vipItem = $$(this).data("vipItem");
        	$$("#serviceNum").val(vipCode);
            $$("#serviceNumShow").val(vipItem);
        	mainView.router.back({url:"personal/vipCertified.html"});
        },
        
        getDefaultValue: function () {
        	var vipCode = $$(".chooseVip .searchBarDefaultResult").html();
        	if (!chooseVipCtrl.checkVipCode(vipCode)) {
               xxdApp.alert("您输入的客服编号格式不正确，请重新输入", "提示")
               return;
            }
        	mainView.router.back({url:"personal/vipCertified.html"});
        },
        
        searchbarInputFocus: function () {
        	clearInterval(time);
	        time = window.setInterval(function () {
	        	var value = $$(".chooseVip .searchbarInput").val();
//	        	console.log("searchbarInput value:" + value)
	            $$(".chooseVip .searchBarDefaultResult").html(value);
	        }, 400);  
        },
        
        searchbarInputBlur: function () {
            clearInterval(time);  
        },
        
        checkVipCode: function(vipCode){
        	//非员工信息表中客户编号，根据校验规则进行校验
        	var regExpPass = false;
            var exp = $(".chooseVip #vipexp").val().split(",");
            exp = exp == null || exp.length == 0 ? '^[A-Za-z]{3}[0-9]{5}$' : exp;
            var isReg = false;
            for (var i = 0; i < exp.length; i++) {
                var myreg = new RegExp(exp[i]);
                if (myreg.test(vipCode.toLowerCase())) {
                    resultServiceNum = vipCode;
                    regExpPass = true;
                    break;
                }
            }
			return regExpPass;
        }
    }
    return chooseVipCtrl;
})