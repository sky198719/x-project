define(['js/utils/date'], function (DateHandle) {
    var isCompany = false;
    var borrowContract = {
        init: function (event) {
            var query = appFunc.getEventDetailPageQuery(event);

            var type = query.type;
            if(type == 20) {
                return;
            }

            var borrowId = query.borrowId;

            var source = query.source;
            if(source == 3 || source == 4) {
                $(".borrowContractTitle").html("借款合同");
                borrowContract.toContract(borrowId);
            } else {
                if(borrowId != undefined) {
                    req.callJSON({
                        url: "borrow/detail/" + borrowId + ".do",
                        data: {},
                        dataType: 'json',
                        success: function (data) {

                            var versionSign = borrowContract.versionSign(data.borrow.VERSION);
                            var userType = data.baseInfo.userType;
                            var companyId = data.borrow.companyId;
                            if(companyId != undefined && companyId !='' ) {
                                isCompany = true;
                            }
                            var type = data.borrow.TYPE;
                            if(versionSign ==1) {
                                var url = GC.getHtmlPath() + 'borrow/contract_1_9.html?' + GC.getVersion();
                                var url2 = GC.getHtmlPath() + 'borrow/contract_1_14.html?' + GC.getVersion();
                                if (isCompany) {
                                    url = GC.getHtmlPath() + 'borrow/contract_2_9.html?' + GC.getVersion();
                                    url2 = GC.getHtmlPath() + 'borrow/contract_2_14.html?' + GC.getVersion();
                                }



                                var fddV = data.FddContractVersion != undefined && data.FddContractVersion.data != undefined && data.FddContractVersion.data.code ==1;
                                if(fddV) {
                                    if(isCompany && data.FddContractVersion.data.data == 1) {
                                        if(type == 9) {
                                            url = GC.getHtmlPath() + 'borrow/contract_2_9_new.html?' + GC.getVersion();
                                        } else if(type == 14) {
                                            url2 = GC.getHtmlPath() + 'borrow/contract_2_14_new.html?' + GC.getVersion();
                                        }

                                        FddContractVersion = true;
                                    } else if(data.FddContractVersion.data.data == 1){
                                        if(type == 9) {
                                            url = GC.getHtmlPath() + 'borrow/contract_1_9_new.html?' + GC.getVersion();
                                        } else if(type == 14) {
                                            url2 = GC.getHtmlPath() + 'borrow/contract_1_14_new.html?' + GC.getVersion();
                                        }

                                    }
                                }

                                //9:新商贷,14新车贷,10新房贷
                                if (type == 9 || type == 10) {
                                    req.callGet({
                                        url: url,
                                        dataType: 'text',
                                        async:false,
                                        success: function (result) {
                                            $$(".borrowContract").html(result);
                                        }
                                    });
                                } else if (type == 14) {
                                    req.callGet({
                                        url: url2,
                                        dataType: 'text',
                                        async:false,
                                        success: function (result) {
                                            $$(".borrowContract").html(result);
                                        }
                                    });
                                }

                                $(".laterRate").html(data.laterRate);
                                $("#guarantor").show();

                                if (data.borrowFee != undefined && data.borrowFee[5] != undefined && data.borrowFee[5].fee != undefined) {
                                    if(data.borrow.pledgeType == 1) {
                                        $(".borrowFee5_gps_tr").show();
                                    } else {
                                        $(".borrowFee5_car_tr").show();
                                    }
                                }
                            } else {
                                var oldUrl1 = GC.getHtmlPath() +'borrow/contract_9_old.html?v=' + new Date().getTime();
                                var oldUrl2 = GC.getHtmlPath() +'borrow/contract_10_old.html?v=' + new Date().getTime();
                                var oldUrl3 = GC.getHtmlPath() +'borrow/contract_14_old.html?v=' + new Date().getTime();
                                //9:新商贷,14新车贷,10新房贷
                                if (type == 9) {
                                    $.ajax({
                                        url: oldUrl1,
                                        dataType: 'text',
                                        async:false,
                                        success: function (result) {
                                            $(".borrowContract").html(result);
                                        }
                                    });
                                }else if (type == 10) {
                                    $.ajax({
                                        url: oldUrl2,
                                        dataType: 'text',
                                        async:false,
                                        success: function (result) {
                                            $(".borrowContract").html(result);
                                        }
                                    })
                                } else if (type == 14) {
                                    $.ajax({
                                        url: oldUrl3,
                                        dataType: 'text',
                                        async:false,
                                        success: function (result) {
                                            $(".borrowContract").html(result);
                                        }
                                    });
                                }
                            }
                        }
                    });
                } else {
                    borrowContract.defaultHandler();
                }
            }
        },

        defaultHandler:function(){
            var url = GC.getHtmlPath() + 'borrow/contractApp.html?' + GC.getVersion();
            req.callGet({
                url: url,
                dataType: 'text',
                success: function (result) {
                    $$(".borrowContract").html(result);
                }
            });
        },
        versionSign:function(version){
            //0："20150104", "20150615", "20150501"
            //2："v6.0.0"
           // 1：除以上版本
            if(version == '20150104' || version == '20150615' || version == '20150501') {
                return 0;
            } else if(version == 'v6.0.0') {
                return 2;
            } else {
                return 1;
            }

        },
        toContract:function(borrowId){
            var FddContractVersion = false;
            req.callJSON({
                url: "borrow/borrowcontract/" + borrowId + ".do",
                data: {},
                dataType: 'json',
                success: function (data) {
                    try {
                        var version = data.borrow.version;
                        var companyId = data.borrow.companyId;

                        if(companyId != undefined && companyId !='' ) {
                            isCompany = true;
                        }

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
                        var versionSign = borrowContract.versionSign(version);
                        if(versionSign == 1) {
                            var url = GC.getHtmlPath() + 'borrow/contract_1_9.html?' + GC.getVersion();
                            var url2 = GC.getHtmlPath() + 'borrow/contract_1_14.html?' + GC.getVersion();
                            if (isCompany) {
                                url = GC.getHtmlPath() + 'borrow/contract_2_9.html?' + GC.getVersion();
                                url2 = GC.getHtmlPath() + 'borrow/contract_2_14.html?' + GC.getVersion();
                            }

                            var fddV = data.FddContractVersion != undefined && data.FddContractVersion.data != undefined && data.FddContractVersion.data.code ==1;
                            if(fddV) {
                                if(isCompany && data.FddContractVersion.data.data == 1) {
                                    if(type == 9) {
                                        url = GC.getHtmlPath() + 'borrow/contract_2_9_new.html?' + GC.getVersion();
                                    } else if(type == 14) {
                                        url2 = GC.getHtmlPath() + 'borrow/contract_2_14_new.html?' + GC.getVersion();
                                    }

                                    FddContractVersion = true;
                                } else if(data.FddContractVersion.data.data == 1){
                                    if(type ==9) {
                                        url = GC.getHtmlPath() + 'borrow/contract_1_9_new.html?' + GC.getVersion();
                                    } else if(type == 14) {
                                        url2 = GC.getHtmlPath() + 'borrow/contract_1_14_new.html?' + GC.getVersion();
                                    }
                                }
                            }


                            //9:新商贷,14新车贷,10新房贷
                            if (type == 9 || type == 10) {
                                req.callGet({
                                    url: url,
                                    dataType: 'text',
                                    async:false,
                                    success: function (result) {
                                        $$(".borrowContract").html(result);
                                    }
                                });
                            } else if (type == 14) {
                                req.callGet({
                                    url: url2,
                                    dataType: 'text',
                                    async:false,
                                    success: function (result) {
                                        $$(".borrowContract").html(result);
                                    }
                                });
                            }

                        } else {
                            var oldUrl1 = '../../html/borrow/contract_9_old.html?v=' + new Date().getTime();
                            var oldUrl2 = '../../html/borrow/contract_10_old.html?v=' + new Date().getTime();
                            var oldUrl3 = '../../html/borrow/contract_14_old.html?v=' + new Date().getTime();
                            //9:新商贷,14新车贷,10新房贷
                            if (type == 9) {
                                $.ajax({
                                    url: oldUrl1,
                                    dataType: 'text',
                                    async:false,
                                    success: function (result) {
                                        $(".borrowContract").html(result);
                                    }
                                });
                            }else if (type == 10) {
                                $.ajax({
                                    url: oldUrl2,
                                    dataType: 'text',
                                    async:false,
                                    success: function (result) {
                                        $(".borrowContract").html(result);
                                    }
                                })
                            } else if (type == 14) {
                                $.ajax({
                                    url: oldUrl3,
                                    dataType: 'text',
                                    async:false,
                                    success: function (result) {
                                        $(".borrowContract").html(result);
                                    }
                                });
                            }
                        }


                        $(".fanbenhide").show();
                        $("#borrowTenderList").show();
                        $("#borrowTenderList1").hide();

                        //风险保证金
                        var borrowFee1 = '零元';
                        if (type == 9 && data.borrowFee != undefined && data.borrowFee[1] != undefined && data.borrowFee[1].fee != undefined) {
                            borrowFee1 = appFunc.chineseNumber(data.borrowFee[1].fee);
                        }

                        //信息咨询费
                        var borrowFee2 = '零元';
                        if ( data.borrowFee != undefined && data.borrowFee[2] != undefined && data.borrowFee[2].fee != undefined && data.borrowFee[2].fee != 0) {
                            borrowFee2 = appFunc.chineseNumber(data.borrowFee[2].fee);
                        }

                        //信息服务费
                        var borrowFee3 = '零元';
                        if ((type == 14 || type == 9) && data.borrowFee != undefined && data.borrowFee[3] != undefined && data.borrowFee[3].fee != undefined && data.borrowFee[2].fee != 0) {
                            borrowFee3 = appFunc.chineseNumber(data.borrowFee[3].fee);
                        }

                        var borrowFee5 = '零元';
                        if (data.borrowFee != undefined && data.borrowFee[5] != undefined && data.borrowFee[5].fee != undefined) {
                            borrowFee5 = appFunc.chineseNumber(data.borrowFee[5].fee);

                            $(".borrowFee5_shebei").html(borrowFee5);
                            if(data.borrow.pledgeType == 1) {
                                $(".borrowFee5_gps").html(borrowFee5);
                                $(".borrowFee5_gps_tr").show();
                            } else {
                                $(".borrowFee5_car").html(borrowFee5);
                                $(".borrowFee5_car_tr").show();
                            }
                        }

                        try {
                            if(data.guarantor != undefined) {
                                $(".guarantorBankAccount").html(data.guarantor.bankAccount);
                                $(".guarantorBank").html(data.guarantor.bankName + " " + (data.guarantor.branchBankName == undefined ? '' : data.guarantor.branchBankName.replace(/^(?:\S+)(.{2})$/, "****$1")));
                                $(".guarantorMobile").html(data.guarantor.mobile);
                                $(".guarantorTelephone").html(data.guarantor.telephone);
                                $(".guarantorAddress").html(data.guarantor.address);
                                $(".guarantorIdCardNo").html(data.guarantor.idCardNo);
                                $(".guarantorGuarName").html(data.guarantor.guarName);
                                $("#guarantor").show();
                            }
                        }catch (e) {console.log(e)}

                        try{

                            if(data.borrowGuarantee != undefined && data.borrowGuarantee.code ==200000 ) {
                                var borrowGuarantee = data.borrowGuarantee.data.data[0];
                                $(".fadongjihao").html(borrowGuarantee.engineno);
                                $(".chejiahao").html(borrowGuarantee.frameno);
                                $(".chepaizhao").html(borrowGuarantee.licenseno);
                                $(".chesuoyouren").html(borrowGuarantee.owner);
                            }
                        }catch (e) {console.log(e)}

                        try {
                            if(data.guarantorList != undefined && data.guarantorList.resultCode == 0) {
                                var guarantorArray = data.guarantorList.data;
                                if(guarantorArray.length > 0) {
                                    var html = '';
                                    var idNo= data.rn.idCardNo;
                                    if(idNo.length == 18) {
                                        idNo = idNo.substring(0,6) + "******" + idNo.substring(idNo.length -4,idNo.length);
                                    } else if(idNo.length >= 3 && idNo.length < 18) {
                                        idNo = idNo.substring(0,3) + "****" + idNo.substring(idNo.length-3,idNo.length);
                                    }
                                    for(var i in guarantorArray) {
                                        var guaran = guarantorArray[i];
                                        html +=  '<p class="text-center" style="margin-top: 20px;">担保函</p> <p class="text-indent">相关全体投资人：</p>';
                                        html += ' <p class="text-indent">鉴于：</p>';
                                        html += '<p class="text-indent">1、借款人 '+data.rn.realName+'（身份证号：'+idNo+'） 通过新新贷（上海）金融信息服务有限公司网站www.xinxindai.com（以下简称“新新贷”）与全体投资人签订了编号为 '+data.billingbondCode+'的《借款合同》及相关法律文件（以下简称“主合同”）；</p>';
                                        html += '<p class="text-indent">2、本人/本公司（以下简称“保证人”）已充分了解主合同所有条款，并自愿为借款人在主合同项下的所有应付债务提供无限连带责任担保；</p>';
                                        html += '<p class="text-indent">现本保证人特承诺如下：</p>';
                                        html += '<p class="text-indent">1、本保证人具有签署及履行本保证函的全部法律授权和相应的民事行为能力；本保证人为法人的，为合法设立、有效存续之企业，并由股东以书面决议的形式同意对外担保本债务；如因本保证人未取得保证人法定身份或授权，导致投资人或新新贷损失的，本保证人承诺赔偿受损方的全部损失。</p>';
                                        html += '<p class="text-indent">2、本保证人保证借款人全面履行主合同付款义务，自愿对借款人在主合同项下的所有债务承担无限连带保证责任。</p>';
                                        html += '<p class="text-indent">保证范围包括但不限于：借款本金、利息、逾期违约金、信息服务费、信息管理费、赔偿金以及投资人为实现债权而产生的催收费、律师费、诉讼费、执行费、公证费等。</p>';
                                        html += '<p class="text-indent">担保期限为：本保证人签署本担保函之日起，至主合同履行期满之日起三年。</p>';
                                        html += '<p class="text-indent">3、借款人任何一期的借款期限届满，但未能按照主合同约定偿付应付款项时，新新贷作为互联网金融信息中介服务提供方，有权代表各投资人或相关债权的受让人直接向本保证人索偿，本保证人在收到新新贷第一次出具的书面要求还款通知后七日内，即无条件按通知要求将保证人担保范围内的所有款项主动支付给新新贷指定的银行账户，应支付额计算至保证人实际支付日。上述书面还款通知书对保证人具有法律约束力。</p>'
                                        html += '<p class="text-indent">4、如果保证人未能按前条规定期限履行上述担保责任，由此造成的逾期违约金和投资人、新新贷网站的其它经济损失由保证人承担。保证人承诺：保证人就以上赔偿义务的履行无权提出异议和抗辩。</p>';
                                        html += '<p class="text-indent">5、本保证人及其他人共同对主合同下借款人的债务承担连带保证责任时，新新贷有权代表投资人人或相关债权的受让人向本保证人主张全部债务的保证责任，本保证人无权提出异议和抗辩。</p>';
                                        html += '<p class="text-indent">6、本担保函与主合同具有同等法律效力，自签章之日起生效。</p>';
                                        html += '<p class="text-indent">7、本保证人同意：在履行本担保函过程中如有争议，应通过友好协商解决。经协商不能解决的，按主合同约定的争议解决方式解决。</p>';
                                        html += '<p class="text-indent">8、保证人资料：</p>';
                                        html += '<p><table class="table1">';
                                        html += '<tr><td class="table1Td td1">姓名</td><td class="table1Td">'+guaran.guarName+'</td></tr>';
                                        var idCardNo = guaran.idCardNo;
                                        if(idCardNo.length >= 15) {
                                            idCardNo = idCardNo.substring(0,6) + "**********";
                                        }
                                        html += '<tr><td class="table1Td td1">身份证号/统一社会信用代码</td><td class="table1Td">'+idCardNo+'</td></tr>';
                                        html += '<tr><td class="table1Td td1">联系地址</td><td class="table1Td">'+guaran.address+'</td></tr>';
                                        var telephone = guaran.telephone;
                                        if(telephone.length >= 4) {
                                            telephone = telephone.substring(0,4) + '******';
                                        }
                                        html += '<tr><td class="table1Td td1">固定电话</td><td class="table1Td">'+telephone+'</td></tr>';
                                        var mobile = guaran.mobile;
                                        if(mobile.length >= 11) {
                                            mobile =  mobile.substring(0,3) + '****' + mobile.substring(7,11)
                                        }
                                        html += '<tr><td class="table1Td td1">手机号码</td><td class="table1Td">'+mobile+'</td></tr>';
                                        html += '<tr><td class="table1Td td1">开 户 行</td><td class="table1Td">'+guaran.bankName +'</td></tr>';
                                        var bankAccount = guaran.bankAccount;
                                        if(bankAccount.length >= 2) {
                                            bankAccount = bankAccount.substring(0,2)+'************'+bankAccount.substring(bankAccount.length -2,bankAccount.length);
                                        }
                                        html += '<tr><td class="table1Td td1">银行账号</td><td class="table1Td">'+bankAccount+'</td></tr>';
                                        html += '</table></p>';
                                        if(isCompany) {
                                            html += '<p style="text-align: right">保证人（签字/盖章）：'+guaran.guarName+'</p>';
                                        } else{
                                            html += '<p style="text-align: right">保证人（签章）：'+guaran.guarName+'</p>';
                                        }

                                    }

                                    $("#guarantorList").html(html);
                                }
                            }
                        }catch (e) {
                            console.log(e);
                        }


                        if(isCompany) {
                            if(data.company != undefined) {
                                $(".companyName").html(data.company.companyName);
                                $(".companyReCardId").html(data.company.comReCardId);
                                $(".comRepMobileNo").html(data.company.comRepMobileNo);
                                $(".comRepName").html(data.company.comRepName);
                                $(".companyAddress").html(data.company.companyAddress);
                                $(".busLicenseNo").html(data.company.busLicenseNo);
                            }

                            if(FddContractVersion && data.company1 != undefined) {
                                $(".companyName").html(data.company1.companyName);
                                $(".companyReCardId").html(data.company1.comReCardId);
                                $(".comRepMobileNo").html(data.company1.comRepMobileNo);
                                $(".comRepName").html(data.company1.comRepName);
                                $(".companyAddress").html(data.company1.companyAddress);
                                $(".busLicenseNo").html(data.company1.busLicenseNo);
                            }
                        }


                        $(".billingbondCode").html(data.billingbondCode);
                        if(data.rn != undefined) {
                            $(".borrowRealName").html(data.rn.realName);

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
                                        $(".tenderAccount").html("人民币：" + appFunc.chineseNumber(data.tenderList[i].effectiveMoney));
                                    }
                                }
                            }
                        }

                        if(data.borrowUser != undefined) {
                            $(".borrowMobile").html(data.borrowUser.mobile)
                        }

                        $(".successTime").html(data.successTime);

                        $(".laterRate").html(data.laterRate);
                        $(".bankName").html(data.bank.BANKNAME + "&nbsp;&nbsp;" + data.bank.BANKBRANCH.replace(/^(?:\S+)(.{2})$/, "****$1"));
                        $(".bankAccount").html(data.bank.BANKACCOUNT);
                        $(".borrowUserName").html(data.borrow.userName);
                        $(".borrowAccount").html("￥"+appFunc.chineseNumber(data.borrow.account));
                        $(".borrowPeriod").html(data.borrow.period);


                        if(data.borrowApproved != undefined) {
                            $(".approvedStart").html(data.approvedStart);
                            $(".borrowSignTime").html(data.borrowSignTime);
                            $(".guarantorSignTime").html(data.guarantorSignTime);
                        }

                        $(".approvedEnd").html(data.borrowEndTime);
                        $(".borrowApr").html(data.borrow.apr + "%/年");
                        $(".borrowUSE").html(data.borrow1.USE);
                        $(".borrowPaymentmethodvalue").html(data.borrow1.PAYMENTMETHODVALUE);
                        $(".borrowRepaymentDay").html(data.day);
                        $(".borrowFee1").html(borrowFee1);
                        $(".borrowFee2").html(borrowFee2);
                        $(".borrowFee3").html(borrowFee3);

                        var pledgetype = data.borrow1.PLEDGETYPE;
                        if(pledgetype==2) {
                            $(".pledgetype").html("质押");
                        }



                        //风险保证金
                        var borrowFee1_old = '免收';
                        if (type != 14  && data.borrowFee != undefined && data.borrowFee[1] != undefined && data.borrowFee[1].fee != undefined  && data.borrowFee[1].fee != 0) {
                            borrowFee1_old = appFunc.chineseNumber(data.borrowFee[1].fee);
                        }

                        //信息咨询费
                        var borrowFee2_old = '免收';
                        if ( data.borrowFee != undefined && data.borrowFee[2] != undefined && data.borrowFee[2].fee != undefined && data.borrowFee[2].fee != 0) {
                            borrowFee2_old = appFunc.chineseNumber(data.borrowFee[2].fee);
                        }

                        //信息服务费
                        var borrowFee3_old = '免收';
                        if (type != 10 && type != 14 && data.borrowFee != undefined && data.borrowFee[3] != undefined && data.borrowFee[3].fee != undefined && data.borrowFee[2].fee != 0) {
                            borrowFee3_old = appFunc.chineseNumber(data.borrowFee[3].fee);
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
                    }catch (e) {
                        console.log(e);
                        borrowContract.defaultHandler();
                    }
                }
            });
        }
    };
    return borrowContract;
});