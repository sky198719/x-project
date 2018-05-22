require(['base','echarts','float','trackBase','store','header','footer','backTop',"requirejs", 'chinaMap', 'countUp'], function($, echarts, float, track, store){
    var timer = new Date();
    var myTime = timer.getTime(); //本地时间

    function fmoney(s, n) { 
        n = n > 0 && n <= 20 ? n : 2; 
        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
        t = ""; 
        for (i = 0; i < l.length; i++) { 
            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
        } 
        return t.split("").reverse().join("") + "." + r; 
    }

    function fpeople(num) {  
        var result = [ ], counter = 0;  
        num = (num || 0).toString().split('');  
        for (var i = num.length - 1; i >= 0; i--) {  
            counter++;  
            result.unshift(num[i]);  
            if (!(counter % 3) && i != 0) { result.unshift(','); }  
        }  
        return result.join('');  
    } 

    $.ajax({
        url:'/biz/bulletin/operationData',
        type:'get',
        async:false,
        cache:false,
        beforeSend:function(request){
            request.setRequestHeader('clientId','XXD_FRONT_END');
            request.setRequestHeader('clientTime',myTime);
            request.setRequestHeader('s','s');
        },
        success:function(data){
            if(data.code == '200000'){
                $('#eventTime').html(data.data.time);
                $('#myDate').html(data.data.operationDate);
                $.each(data.data.items,function(index,item){
                    if(item.code == 'TOTAL_TRADE'){
                        $('#totalTradeAmount').html(fmoney(item.nvalue,2));
                    }else if(item.code == 'TOTAL_COUNT'){
                        $('#totalTradeNumber').html(fpeople(parseInt(item.nvalue)));
                    }else if(item.code == 'TOTAL_BORROWER'){
                        $('#totalBorrower').html(fpeople(parseInt(item.nvalue)));
                    }else if(item.code == 'TOTAL_LENDER'){
                        $('#totalLender').html(fpeople(parseInt(item.nvalue)));
                    }else if(item.code == 'CURRENT_BORROWER'){
                        $('#currentBorrower').html(fpeople(parseInt(item.nvalue)));
                    }else if(item.code == 'CURRENT_LENDER'){
                        $('#currentLender').html(fpeople(parseInt(item.nvalue)));
                    }else if(item.code == 'LOAN_BALANCE_MONEY'){
                        $('#loanBalanceAmount').html(fmoney(item.nvalue,2));
                    }else if(item.code == 'LOAN_BALANCE_NUM'){
                        $('#loanBalanceNumber').html(fpeople(parseInt(item.nvalue)));
                    }else if(item.code == 'COMPENSATORY_AMOUNT'){
                        $('#compensatoryAmount').html(fmoney(item.nvalue,2));
                    }else if(item.code == 'COMPENSATORY_NUM'){
                        $('#compensatoryNumber').html(fpeople(parseInt(item.nvalue)));
                    }else if(item.code == 'INTEREST_BALANCE'){
                        $('#interestBalanceAmount').html(fmoney(item.nvalue,2));
                    }else if(item.code == 'TOPTEN_BORR0WER_UNPAID_RATIO'){
                        $('#no10BorrowerPercent').html(fmoney(item.nvalue,2));
                    }else if(item.code == 'HIGHEST_BORROWER_UNPAID_RATIO'){
                        $('#no1BorrowerPercent').html(fmoney(item.nvalue,2));
                    }else if(item.code == 'RELATED_LOAN_BALANCE_MONEY'){
                        $('#relatedLoanBalanceAmount').html(fmoney(item.nvalue,2));
                    }else if(item.code == 'RELATED_LOAN_BALANCE_NUM'){
                        $('#relatedLoanBalanceNumber').html(fpeople(parseInt(item.nvalue)));
                    }else if(item.code == 'OVERDUE_AMOUNT'){
                        $('#overdueAmount').html(fmoney(item.nvalue,2));
                    }else if(item.code == 'NUM_OVERDUE'){
                        $('#overdueNumber').html(fpeople(parseInt(item.nvalue)));
                    }else if(item.code == 'OVERDUE_MONEY_ABOVENINETY'){
                        $('#overdue90Amount').html(fmoney(item.nvalue,2));
                    }else if(item.code == 'OVERDUE_NUM_ABOVENINETY'){
                        $('#overdue90Number').html(fpeople(parseInt(item.nvalue)));
                    }else if(item.code == 'REVENUE_MANAGEMENT_FEE_PROPORTION'){
                        $('#percent1').html(item.nvalue);
                    }else if(item.code == 'BOUNDED_FEE_OF_TRANSFER_PROPORTION'){
                        $('#percent2').html(item.nvalue);
                    }else if(item.code == 'UNBOUND_FEE_OF_TRANSFER_PROPORTION'){
                        $('#percent3').html(item.nvalue);
                    }else if(item.code == 'EXIT_FEE_PROPORTION'){
                        $('#percent4').html(item.nvalue);
                    }else if(item.code == 'CHARGE_FOR_BORROWER_PROPORTION'){
                        $('#percent5').html(item.nvalue);
                    }
                });
            }
        },
        error:function(){
            alert('网络异常，请重试！');
            return false;
        }
    });
});
