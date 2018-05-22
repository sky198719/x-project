require(['base', 'requirejs', 'trackBase' ], function ($, requirejs, track) {
    $(function () {
        document.addEventListener('touchstart', function () {
        });
        function GetQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)return decodeURI(r[2]);
            return null;
        }
        $(document).ready(function () {
            var borrowId = GetQueryString("borrowId");
            var source = GetQueryString("source");
            var uid = GetQueryString("uid");
            var sign = GetQueryString("sign");
            var versionSign = GetQueryString("versionSign");

            if (source == 3 || source == 4) {
                if (uid != undefined && sign != undefined) {
                    $.ajax({
                        url: '/m/memberDay922/checkAppSign.do',
                        data: {
                            uid: uid,
                            sign: sign
                        },
                        async: false,
                        success: function (data) {
                            if (data.resultCode == 0) {
                                $("title").html("借款合同");
                                //标详情
                                borrowDetail(borrowId);
                            } else {
                                alert("页面鉴权失败，请重新进入");
                                defaultHandler();
                            }
                        }
                    });
                } else {
                    alert("非法请求，请重新进入");
                    defaultHandler();
                }
            } else {
                if(borrowId != undefined) {
                    $.ajax({
                        url: "/m/borrow/detail/" + borrowId + ".do",
                        data: {},
                        dataType: 'json',
                        success: function (data) {
                            if(versionSign == 1){
                                if(data.resultCode == "0") {
                                    var userType = data.baseInfo.userType;
                                    var type = data.borrow.TYPE;
                                    var url = '../../html/borrow/contract_2_9.html?v=' + new Date().getTime();
                                    var url2 = '../../html/borrow/contract_2_14.html?' + new Date().getTime();
                                    if (userType == 1) {
                                        url = '../../html/borrow/contract_1_9.html?' + new Date().getTime();
                                        url2 = '../../html/borrow/contract_1_14.html?' + new Date().getTime();
                                    }
                                    //9:新商贷,14新车贷,10新房贷
                                    if (type == 9 || type == 10) {
                                        $.ajax({
                                            url: url,
                                            dataType: 'text',
                                            async: false,
                                            success: function (result) {
                                                $("body").html(result);
                                            }
                                        });
                                    } else if (type == 14) {
                                        $.ajax({
                                            url: url2,
                                            dataType: 'text',
                                            async: false,
                                            success: function (result) {
                                                $("body").html(result);
                                            }
                                        });
                                    } else {
                                        defaultHandler();
                                    }

                                    $(".laterRate").html(data.laterRate);
                                    $("#guarantor").show();

                                    if (data.borrowFee != undefined && data.borrowFee[5] != undefined && data.borrowFee[5].fee != undefined) {
                                        if (data.borrow.pledgeType == 1) {
                                            $(".borrowFee5_gps_tr").show();
                                        } else {
                                            $(".borrowFee5_car_tr").show();
                                        }
                                    }
                                }else {
                                    alert("哎呀( ⊙ o ⊙ )，没有数据呢，请稍后重试~");
                                }
                            }else if(versionSign == 0 || versionSign == 2){
                                if(data.resultCode == "0") {
                                    var userType = data.baseInfo.userType;
                                    var type = data.borrow.TYPE;
                                    var oldUrl1 = '../../html/borrow/contract_9_old.html?v=' + new Date().getTime();
                                    var oldUrl2 = '../../html/borrow/contract_10_old.html?v=' + new Date().getTime();
                                    var oldUrl3 = '../../html/borrow/contract_14_old.html?v=' + new Date().getTime();
                                    //9:新商贷,14新车贷,10新房贷
                                    if (type == 9) {
                                        $.ajax({
                                            url: oldUrl1,
                                            dataType: 'text',
                                            async: false,
                                            success: function (result) {
                                                $("body").html(result);
                                            }
                                        });
                                    } else if (type == 10) {
                                        $.ajax({
                                            url: oldUrl2,
                                            dataType: 'text',
                                            async: false,
                                            success: function (result) {
                                                $("body").html(result);
                                            }
                                        })
                                    } else if (type == 14) {
                                        $.ajax({
                                            url: oldUrl3,
                                            dataType: 'text',
                                            async: false,
                                            success: function (result) {
                                                $("body").html(result);
                                            }
                                        });
                                    }
                                }else {
                                    alert("哎呀( ⊙ o ⊙ )，没有数据呢，请稍后重试~");
                                }
                            }else {
                                alert("页面鉴权失败，请重新进入");
                                defaultHandler();
                            }

                        }
                    });
                } else {
                    defaultHandler();
                }
            }
        });

        function defaultHandler(){
            $.ajax({
                url: "contract-app.html",
                success: function (data) {
                    $("body").html(data);
                }
            });
        }
        function borrowDetail(borrowId) {
            var versionSign = GetQueryString("versionSign");
            $.ajax({
                url: "/m/borrow/borrowcontract/" + borrowId + ".do",
                data: {},
                dataType: 'json',
                success: function (data) {
                    if(versionSign == 1){
                        if(data.resultCode == "0"){
                            var userType = data.baseInfo.userType;
                            var type = data.borrow.type;

                            if(data.rn != undefined) {
                                var idCardType = data.rn.idCardType;
                                if (idCardType != undefined) {
                                    if (idCardType == 1) {
                                        data.rn.idCardType = "身份证";
                                    } else if (idCardType == 2) {
                                        data.rn.idCardType = "澳门特别行政区护照";
                                    } else if (idCardType == 3) {
                                        data.rn.idCardType = "台湾居民来往大陆通行证";
                                    } else if (idCardType == 4) {
                                        data.rn.idCardType = "香港特别行政区护照";
                                    } else {
                                        data.rn.idCardType = "其他";
                                    }
                                }
                            }

                            var url = 'contract_2_9.html?v=' + new Date().getTime();
                            var url2 = 'contract_2_14.html?' + new Date().getTime();
                            if (userType == 1) {
                                url = 'contract_1_9.html?' + new Date().getTime();
                                url2 = 'contract_1_14.html?' + new Date().getTime();
                            }
                            //9:新商贷,14新车贷,10新房贷
                            if (type == 9 || type == 10) {
                                $.ajax({
                                    url: url,
                                    dataType: 'text',
                                    async:false,
                                    success: function (result) {
                                        $("body").html(result);
                                    }
                                });
                            } else if (type == 14) {
                                $.ajax({
                                    url: url2,
                                    dataType: 'text',
                                    async:false,
                                    success: function (result) {
                                        $("body").html(result);
                                    }
                                });
                            }
                        }else {
                            alert("哎呀( ⊙ o ⊙ )，没有数据呢，请稍后重试~");
                        }

                    }else if(versionSign == 0 || versionSign == 2){
                        if(data.resultCode == "0") {
                            var userType = data.baseInfo.userType;
                            var type = data.borrow.type;

                            if (data.rn != undefined) {
                                var idCardType = data.rn.idCardType;
                                if (idCardType != undefined) {
                                    if (idCardType == 1) {
                                        data.rn.idCardType = "身份证";
                                    } else if (idCardType == 2) {
                                        data.rn.idCardType = "澳门特别行政区护照";
                                    } else if (idCardType == 3) {
                                        data.rn.idCardType = "台湾居民来往大陆通行证";
                                    } else if (idCardType == 4) {
                                        data.rn.idCardType = "香港特别行政区护照";
                                    } else {
                                        data.rn.idCardType = "其他";
                                    }
                                }
                            }
                            var oldUrl1 = '../../html/borrow/contract_9_old.html?v=' + new Date().getTime();
                            var oldUrl2 = '../../html/borrow/contract_10_old.html?v=' + new Date().getTime();
                            var oldUrl3 = '../../html/borrow/contract_14_old.html?v=' + new Date().getTime();
                            //9:新商贷,14新车贷,10新房贷
                            if (type == 9) {
                                $.ajax({
                                    url: oldUrl1,
                                    dataType: 'text',
                                    async: false,
                                    success: function (result) {
                                        $("body").html(result);
                                    }
                                });
                            } else if (type == 10) {
                                $.ajax({
                                    url: oldUrl2,
                                    dataType: 'text',
                                    async: false,
                                    success: function (result) {
                                        $("body").html(result);
                                    }
                                })
                            } else if (type == 14) {
                                $.ajax({
                                    url: oldUrl3,
                                    dataType: 'text',
                                    async: false,
                                    success: function (result) {
                                        $("body").html(result);
                                    }
                                });
                            }
                        }else {
                            alert("哎呀( ⊙ o ⊙ )，没有数据呢，请稍后重试~");
                        }
                    }else {
                        alert("页面鉴权失败，请重新进入");
                        defaultHandler();
                    }


                    $(".fanbenhide").show();
                    $("#borrowTenderList").show();
                    $("#borrowTenderList1").hide();

                    //风险保证金
                    var borrowFee1 = '零元';
                    if (type == 9 && data.borrowFee != undefined && data.borrowFee[1] != undefined && data.borrowFee[1].fee != undefined) {
                        borrowFee1 = chineseNumber(data.borrowFee[1].fee);
                    }

                    //信息咨询费
                    var borrowFee2 = '零元';
                    if ( data.borrowFee != undefined && data.borrowFee[2] != undefined && data.borrowFee[2].fee != undefined && data.borrowFee[2].fee != 0) {
                        borrowFee2 = chineseNumber(data.borrowFee[2].fee);
                    }

                    //信息服务费
                    var borrowFee3 = '零元';
                    if ((type == 14 || type == 9) && data.borrowFee != undefined && data.borrowFee[3] != undefined && data.borrowFee[3].fee != undefined && data.borrowFee[2].fee != 0) {
                        borrowFee3 = chineseNumber(data.borrowFee[3].fee);
                    }

                    var borrowFee5 = '零元';
                    if (data.borrowFee != undefined && data.borrowFee[5] != undefined && data.borrowFee[5].fee != undefined) {
                        borrowFee5 = chineseNumber(data.borrowFee[5].fee);

                        if(data.borrow.pledgeType == 1) {
                            $(".borrowFee5_gps").html(borrowFee5);
                            $(".borrowFee5_gps_tr").show();
                        } else {
                            $(".borrowFee5_car").html(borrowFee5);
                            $(".borrowFee5_car_tr").show();
                        }
                    }

                    if(data.guarantor != undefined) {
                        $(".guarantorBankAccount").html(data.guarantor.bankAccount);
                        $(".guarantorBank").html(data.guarantor.bankName + " " + data.guarantor.branchBankName.replace(/^(?:\S+)(.{2})$/, "****$1"));
                        $(".guarantorMobile").html(data.guarantor.mobile);
                        $(".guarantorTelephone").html(data.guarantor.telephone);
                        $(".guarantorAddress").html(data.guarantor.address);
                        $(".guarantorIdCardNo").html(data.guarantor.idCardNo);
                        $(".guarantorGuarName").html(data.guarantor.guarName);
                        $("#guarantor").show();
                    }

                    if(userType == 2) {
                        if(data.company != undefined) {
                            $(".companyName").html(data.company.companyName);
                            $(".companyReCardId").html(data.company.comReCardId);
                            $(".comRepMobileNo").html(data.company.comRepMobileNo);
                            $(".comRepName").html(data.company.comRepName);
                            $(".companyAddress").html(data.company.companyAddress);
                        }
                    }


                    $(".billingbondCode").html(data.billingbondCode);
                    if(data.rn != undefined) {
                        $(".borrowRealName").html(data.rn.realName);
                        // $(".realName").html(data.userRn.realName);
                        var idCardNo= data.rn.idCardNo;
                        if(idCardNo.length == 18) {
                            idCardNo = idCardNo.substring(0,6) + "******" + idCardNo.substring(idCardNo.length -4,idCardNo.length);
                        } else if(idCardNo.length >= 3 && idCardNo.length < 18) {
                            idCardNo = idCardNo.substring(0,3) + "****" + idCardNo.substring(idCardNo.length-3,idCardNo.length);
                        }
                        $(".borrowIdCardNo").html(idCardNo);
                        $(".idCardType").html(data.rn.idCardType);
                    }
                    if(data.user != undefined) {
                        $(".userName").html(data.user.userName);
                        var tenderUserId = data.user.userId;
                        if(data.tenderList != undefined){
                            for(var i = 0; i < data.tenderList.length;i++){
                                if (data.tenderList[i].userId == tenderUserId){
                                    $(".tenderAccount").html("人民币：" + chineseNumber(data.tenderList[i].effectiveMoney));
                                }
                            }
                        }
                    }

                    $(".successTime").html(data.successTime);

                    if(data.borrowUser != undefined) {
                        $(".borrowMobile").html(data.borrowUser.mobile)
                    }

                    $(".laterRate").html(data.laterRate);
                    $(".bankName").html(data.bank.BANKNAME + "&nbsp;&nbsp;" + data.bank.BANKBRANCH.replace(/^(?:\S+)(.{2})$/, "****$1"));
                    $(".bankAccount").html(data.bank.BANKACCOUNT);
                    $(".borrowUserName").html(data.borrow.userName);
                    $(".borrowAccount").html("￥"+chineseNumber(data.borrow.account));
                    $(".borrowPeriod").html(data.borrow.period);




                    if(data.borrowApproved != undefined) {
                        $(".approvedStart").html(data.approvedStart);
                        $(".borrowSignTime").html(data.borrowSignTime);
                        $(".guarantorSignTime").html(data.guarantorSignTime);
                    }

                    $(".approvedEnd").html(data.borrowEndTime);
                    $(".borrowApr").html(data.borrow.apr + "%");
                    $(".borrowUSE").html(data.borrow1.USE);
                    $(".borrowPaymentmethodvalue").html(data.borrow1.PAYMENTMETHODVALUE);
                    $(".borrowRepaymentDay").html(data.day);
                    $(".borrowFee1").html(borrowFee1);
                    $(".borrowFee2").html(borrowFee2);
                    $(".borrowFee3").html(borrowFee3);
                    //风险保证金
                    var borrowFee1_old = '免收';
                    if (type != 14  && data.borrowFee != undefined && data.borrowFee[1] != undefined && data.borrowFee[1].fee != undefined  && data.borrowFee[1].fee != 0) {
                        borrowFee1_old = chineseNumber(data.borrowFee[1].fee);
                    }

                    //信息咨询费
                    var borrowFee2_old = '免收';
                    if ( data.borrowFee != undefined && data.borrowFee[2] != undefined && data.borrowFee[2].fee != undefined && data.borrowFee[2].fee != 0) {
                        borrowFee2_old = chineseNumber(data.borrowFee[2].fee);
                    }

                    //信息服务费
                    var borrowFee3_old = '免收';
                    if (type != 10 && type != 14 && data.borrowFee != undefined && data.borrowFee[3] != undefined && data.borrowFee[3].fee != undefined && data.borrowFee[2].fee != 0) {
                        borrowFee3_old = chineseNumber(data.borrowFee[3].fee);
                    }
                    if(versionSign == 0){
                        if (type == 14 ) {
                            $(".warrantyFund").html("免收");
                            $(".managementCost").html("免收");
                        }else if(type == 9) {
                            $(".warrantyFund").html(borrowFee1_old + "  在借款成功时一次性收取");
                            $(".managementCost").html(borrowFee3_old + "  在借款成功时一次性收取");
                        }else {
                            $(".warrantyFund").html(borrowFee1_old + "  预先冻结；可抵冲该笔贷款的末期还款金额，多退少补");
                            $(".managementCost").html("免收");
                        }
                        $(".serviceCharge").html(borrowFee2_old + "  在借款成功时一次性收取");
                    }
                    if(versionSign == 2){
                        if (type == 14 || type == 10) {
                            $(".warrantyFund").html("签约额度的2%（预先冻结；可抵冲该笔贷款的末期还款金额，多退少补");
                            $(".managementCost").html("免收");
                        }else {
                            $(".warrantyFund").html("签约额度的3%（预先冻结；可抵冲该笔贷款的末期还款金额，多退少补");
                            $(".managementCost").html("1.25%/月；每期还款时收取");
                        }
                        if(type == 14){
                            $(".serviceCharge").html("2%/月；在借款成功时一次性收取");
                        }else if(type == 10){
                            $(".serviceCharge").html("1%/月；在借款成功时一次性收取");
                        }else {
                            $(".serviceCharge").html("0.5%/月；在借款成功时一次性收取");
                        }

                    }

                    var tenderHtml = "";
                    var tenderList = data.tenderList;
                    var userid = data.user == undefined ? 0 : data.user.userId;
                    $.each(tenderList, function (index, obj) {
                        if(userid == obj.userId) {
                            tenderHtml += '<tr><td class="table1Td">' + obj.userName + '</td><td class="table1Td">' + obj.effectiveMoney + '</td></tr>';
                        } else {
                            tenderHtml += '<tr><td class="table1Td">' + obj.nickName + '</td><td class="table1Td">' + obj.effectiveMoney + '</td></tr>';
                        }

                    });
                    $("#borrowTenderList").append(tenderHtml);

                    $(".userRealName").html(data.userRn.realName);
                }
            });
        }
        /* 匹配正则 */
        var RegExpObject = /^(y+|M+|d+|H+|h+|m+|s+|E+|S|a)/;
        /* 参数 */
        var _options;
        /* 匹配值处理 */
        var patternValue = {
            y: function (date) {
                return date.getFullYear().toString().length > 1 ? toFixedWidth(date.getFullYear(), 2) : toFixedWidth(date.getFullYear(), 1);
            },
            yy: function (date) {
                return toFixedWidth(date.getFullYear(), 2);
            },
            yyy: function (date) {
                return toFixedWidth(date.getFullYear(), 3);
            },
            yyyy: function (date) {
                return date.getFullYear().toString();
            },
            M: function (date) {
                return date.getMonth() + 1;
            },
            MM: function (date) {
                return toFixedWidth(date.getMonth() + 1, 2);
            },
            MMM: function (date) {
                return _options.aMonths[date.getMonth()];
            },
            MMMM: function (date) {
                return _options.aLongMonths[date.getMonth()];
            },
            d: function (date) {
                return date.getDate();
            },
            dd: function (date) {
                return toFixedWidth(date.getDate(), 2);
            },
            E: function (date) {
                return _options.aWeeks[date.getDay()];
            },
            EE: function (date) {
                return _options.aLongWeeks[date.getDay()];
            },
            H: function (date) {
                return date.getHours();
            },
            HH: function (date) {
                return toFixedWidth(date.getHours(), 2);
            },
            h: function (date) {
                return date.getHours() % 12;
            },
            hh: function (date) {
                return toFixedWidth(date.getHours() > 12 ? date.getHours() - 12 : date.getHours(), 2);
            },
            m: function (date) {
                return date.getMinutes();
            },
            mm: function (date) {
                return toFixedWidth(date.getMinutes(), 2);
            },
            s: function (date) {
                return date.getSeconds();
            },
            ss: function (date) {
                return toFixedWidth(date.getSeconds(), 2);
            },
            S: function (date) {
                return toFixedWidth(date.getMilliseconds(), 3);
            },
            a: function (date) {
                return _options.a[date.getHours() < 12 ? 0 : 1];
            }
        }

        $.extend({


            formatDate: function (pattern, date, options) {
                /* 未传入时间,设置为当前时间 */
                if (date == undefined)
                    date = new Date();
                /* 传入时间为字符串 */
                if ($.type(date) === "string") {
                    if (date == "") date = new Date();
                    else date = new Date(date.replace(/-/g, "/"));
                }

                var result = [];
                while (pattern.length > 0) {
                    RegExpObject.lastIndex = 0;
                    var matched = RegExpObject.exec(pattern);
                    if (matched) {
                        result.push(patternValue[matched[0]](date));
                        pattern = pattern.slice(matched[0].length);
                    } else {
                        result.push(pattern.charAt(0));
                        pattern = pattern.slice(1);
                    }
                }
                return result.join('');
            },
            parseDate: function (dateValue) {
                return new Date(Date.parse(dateValue.replace(/-/g, "/")));
            },
            /* 补全 */
            toFixedWidth: function (value, length) {
                var result = "00" + value.toString();
                return result.substr(result.length - length);
            },
            addDay: function (date, days) {
                if ($.type(date) === "string") {
                    if (date == "") date = new Date();
                    else date = new Date(date.replace(/-/g, "/"));
                }
                date.setDate(date.getDate() + days);
                return date;
            },
            addMonth: function (date, months) {
                if ($.type(date) === "string") {
                    if (date == "") date = new Date();
                    else date = new Date(date.replace(/-/g, "/"));
                }
                date.setMonth(date.getMonth() + months);
                return date;
            }
        })

        /* 补全 */
        function toFixedWidth(value, length) {
            var result = "00" + value.toString();
            return result.substr(result.length - length);
        }
        /**大写切换*/
        function chineseNumber(dValue) {
            try {
                var maxDec = 2;
                // 验证输入金额数值或数值字符串：
                dValue = dValue.toString().replace(/,/g, "");
                dValue = dValue.replace(/^0+/, ""); // 金额数值转字符、移除逗号、移除前导零
                if (dValue == "") {
                    return "零元"; //元整
                } // （错误：金额为空！）
                else if (isNaN(dValue)) {
                    return "错误：金额不是合法的数值！";
                }
                var minus = ""; // 负数的符号“-”的大写：“负”字。可自定义字符，如“（负）”。
                var CN_SYMBOL = ""; // 币种名称（如“人民币”，默认空）
                if (dValue.length > 1) {
                    if (dValue.indexOf('-') == 0) {
                        dValue = dValue.replace("-", "");
                        minus = "负";
                    } // 处理负数符号“-”
                    if (dValue.indexOf('+') == 0) {
                        dValue = dValue.replace("+", "");
                    } // 处理前导正数符号“+”（无实际意义）
                }
                // 变量定义：
                var vInt = "";
                var vDec = ""; // 字符串：金额的整数部分、小数部分
                var resAIW; // 字符串：要输出的结果
                var parts; // 数组（整数部分.小数部分），length=1时则仅为整数。
                var digits, radices, bigRadices, decimals; // 数组：数字（0~9——零~玖）；基（十进制记数系统中每个数字位的基是10——拾,佰,仟）；大基（万,亿,兆,京,垓,杼,穰,沟,涧,正）；辅币（元以下，角/分/厘/毫/丝）。
                var zeroCount; // 零计数
                var i, p, d; // 循环因子；前一位数字；当前位数字。
                var quotient, modulus; // 整数部分计算用：商数、模数。
                // 金额数值转换为字符，分割整数部分和小数部分：整数、小数分开来搞（小数部分有可能四舍五入后对整数部分有进位）。
                var NoneDecLen = (typeof (maxDec) == "undefined" || maxDec == null || Number(maxDec) < 0 || Number(maxDec) > 5); // 是否未指定有效小数位（true/false）
                parts = dValue.split('.'); // 数组赋值：（整数部分.小数部分），Array的length=1则仅为整数。
                if (parts.length > 1) {
                    vInt = parts[0];
                    vDec = parts[1]; // 变量赋值：金额的整数部分、小数部分
                    if (NoneDecLen) {
                        maxDec = vDec.length > 5 ? 5 : vDec.length;
                    } // 未指定有效小数位参数值时，自动取实际小数位长但不超5。
                    var rDec = Number("0." + vDec);
                    rDec *= Math.pow(10, maxDec);
                    rDec = Math.round(Math.abs(rDec));
                    rDec /= Math.pow(10, maxDec); // 小数四舍五入
                    var aIntDec = rDec.toString().split('.');
                    if (Number(aIntDec[0]) == 1) {
                        vInt = (Number(vInt) + 1).toString();
                    } // 小数部分四舍五入后有可能向整数部分的个位进位（值1）
                    if (aIntDec.length > 1) {
                        vDec = aIntDec[1];
                    } else {
                        vDec = "";
                    }
                } else {
                    vInt = dValue;
                    vDec = "";
                    if (NoneDecLen) {
                        maxDec = 0;
                    }
                }
                if (vInt.length > 44) {
                    return "错误：金额值太大了！整数位长【" + vInt.length.toString() + "】超过了上限——44位/千正/10^43（注：1正=1万涧=1亿亿亿亿亿，10^40）！";
                }
                // 准备各字符数组 Prepare the characters corresponding to the digits:
                digits = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"); // 零~玖
                radices = new Array("", "拾", "佰", "仟"); // 拾,佰,仟
                bigRadices = new Array("", "万", "亿", "兆", "京", "垓", "杼", "穰", "沟", "涧", "正"); // 万,亿,兆,京,垓,杼,穰,沟,涧,正
                decimals = new Array("角", "分", "厘", "毫", "丝"); // 角/分/厘/毫/丝
                resAIW = ""; // 开始处理
                // 处理整数部分（如果有）
                if (Number(vInt) > 0) {
                    zeroCount = 0;
                    for (i = 0; i < vInt.length; i++) {
                        p = vInt.length - i - 1;
                        d = vInt.substr(i, 1);
                        quotient = p / 4;
                        modulus = p % 4;
                        if (d == "0") {
                            zeroCount++;
                        } else {
                            if (zeroCount > 0) {
                                resAIW += digits[0];
                            }
                            zeroCount = 0;
                            resAIW += digits[Number(d)] + radices[modulus];
                        }
                        if (modulus == 0 && zeroCount < 4) {
                            resAIW += bigRadices[quotient];
                        }
                    }
                    resAIW += "元"; //元
                }
                // 处理小数部分（如果有）
                for (i = 0; i < vDec.length; i++) {
                    d = vDec.substr(i, 1);
                    if (d != "0") {
                        resAIW += digits[Number(d)] + decimals[i];
                    }
                }
                // 处理结果
                if (resAIW == "") {
                    resAIW = "零元" + ""; //元
                } // 零元
                if (vDec == "") {
                    resAIW += ""; //整
                } // ...元整
                resAIW = CN_SYMBOL + minus + resAIW; // 人民币/负......元角分/整
                dValue = resAIW;
            } catch (e) {
                console.log(e);
            } finally {
                return dValue;
            }
        }

    });


}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});