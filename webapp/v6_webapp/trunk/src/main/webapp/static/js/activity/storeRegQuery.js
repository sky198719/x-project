$(function(){
    $(".jdate").jdate();
    
    // $("#beginDate").val(formatDate(new Date(),"yyyy-MM-dd"));
    // $("#endDate").val(formatDate(new Date(),"yyyy-MM-dd"));
    
    $("#pCode").val(getStoreUrlParam("pCode"));
    $("#currentPage").val("0");
    $("#totalPage").val("1");
    $("#pageSize").val("10");
    $("#selectType").val("2");
    
    queryStoreSumData();
    
    var phoneNo = getStoreUrlParam("phoneNo");
    if(phoneNo != null && phoneNo != ""){
        $("#phoneNo").val(phoneNo);
        $("#currentPage").val("1");
        getStoreResultByPhone();
    }

});
    
getStoreUrlParam = function (name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)
         return  unescape(r[2]); 
     return null;
};

(function (){
    
    $('#refreshSumData').click(function(){
        queryStoreSumData();
    });
    $('#queryByDate').click(function(){
        queryStoreSumData();
        $("#currentPage").val("1");
        getStoreResultByDate();
    });
    $('#queryByPhone').click(function(){
        $("#currentPage").val("1");
        getStoreResultByPhone();
    });
    queryStoreSumData = function(){
        $('#loadingDiv').css('display','block');
        var pCode = $("#pCode").val();
         
         $.ajax({
             type:'get',
             url:'../../../activity/store/getSumData.do',
             data: {
                 'pCode':pCode
             },
             dataType: 'json',
             timeout:5000,
             success:function(data){
                $('#loadingDiv').css('display','none');
                 if(data.resultCode == 0){
                    $("#pName").html(data.pName);
                    $("#sumToday").html(data.daypromotion);
                    $("#sumTotal").html(data.cumulativepromotion);
                 }else{
                     swal("", "操作失败，请稍后重试...", "error");
                 }
               
            },
            error: function(){
                $('#loadingDiv').css('display','none');
                swal("", "操作失败，请稍后重试...", "error");
            }
        });
    };
    getStoreResultByPhone = function(){
        $("#selectType").val("1");
        var phoneNo = $("#phoneNo").val();
        var pCode = $("#pCode").val();
        var currentPage = $("#currentPage").val();
        var pageSize = $("#pageSize").val();
        var params={};
        params.beginDate = "";
        params.endDate = "";
        params.phoneNo = phoneNo;
        params.pCode = pCode;
        params.currentPage = currentPage;
        params.pageSize = pageSize;
        getStoreListData(params);
    };
    getStoreResultByDate = function(){
        $("#selectType").val("2");
        var beginDate = $("#beginDate").val();
        var endDate = $("#endDate").val();
        var pCode = $("#pCode").val();
        var currentPage = $("#currentPage").val();
        var pageSize = $("#pageSize").val();
        var params={};
        params.beginDate = beginDate;
        params.endDate = endDate;
        params.phoneNo = "";
        params.pCode = pCode;
        params.currentPage = currentPage;
        params.pageSize = pageSize;
        getStoreListData(params);
    };
    getStoreListData = function(params){
        $('#loadingDiv').css('display','block');
        var beginDate = params.beginDate;
        var endDate = params.endDate;
        var phoneNo = params.phoneNo;
        var pCode = params.pCode;
        var currentPage = params.currentPage;
        var pageSize = params.pageSize;
        var listContent = "";
        if(currentPage==1){
            listContent='<tr>'+
                            '<td>姓名/手机号</td>'+
                            '<td>时间</td>'+
                            '<td>状态</td>'+
                        '</tr>';
        }
         
         $.ajax({
             type:'get',
             url:'../../../activity/store/getStoreListData.do',
             data: {
                 'pCode':pCode,
                 'beginDate':beginDate,
                 'endDate':endDate,
                 'currentPage':currentPage,
                 'pageSize':pageSize,
                 'phoneNo':phoneNo
             },
             dataType: 'json',
             timeout:15000,
             success:function(data){
                $('#loadingDiv').css('display','none');
                if(data.resultCode == 0){
                    //总页数
                    $("#totalPage").val(Math.ceil(parseInt(data.sumNumber)/parseInt(pageSize)));
                    //查询时间段的总推广数
                    if(phoneNo==null || phoneNo==""){
                        $("#queryTotal").html(data.sumNumber);
                        $(".tuiguang").css("visibility","visible");
                    }else{
                        $(".tuiguang").css("visibility","hidden");
                    }
                    
                    var userInfos = data.userInfo;
                    if(userInfos != null){
                        console.log(userInfos.length);
                        if(userInfos.length == 0) {
                            listContent += '<tr><td colspan="3" style="text-align:left;">暂无记录</td></tr>';
                        } else {
                            for (var i=0; i < userInfos.length; i++) {
                              var user = userInfos[i];
                              var exchangeStatus = "";
                              if(user.exchangeStatus == '0'){
                                  exchangeStatus = '<td><button class="button search-btn bg-ff6633" onclick="exchangeEnsure(\''+ user.phone +'\')">兑换</button></td>';
                              }else if(user.exchangeStatus == '1'){
                                  exchangeStatus = '<td class="font16">已兑换</td>';
                              }else{
                                  exchangeStatus = '<td class="font16"></td>';
                              }
                              
                              listContent += '<tr>'+
                                                '<td>'+
                                                    '<p>'+ user.username +'</p>'+
                                                    '<p>'+ user.phone +'</p>'+
                                                '</td>'+
                                                '<td>'+ user.regTime +'</td>'+
                                                exchangeStatus+
                                            '</tr>'; 
                            };
                        }   
                    } 
                    if(currentPage==1){
                        $('#storeQueryList').html(listContent);
                    }else{
                        $('#storeQueryList').append(listContent);
                    }                     
                    
                }else{
                    swal("", "操作失败，请稍后重试...", "error");
                }
            },
            error: function(xhr, type){
                $('#loadingDiv').css('display','none');
                swal("", "操作失败，请稍后重试...", "error");
            }
            
        });
    };

    // dropload
    var dropload = $('#storeQueryPage').dropload({
        domUp : {
            domClass   : 'dropload-up',
            domRefresh : '<div class="dropload-refresh">↓下拉刷新</div>',
            domUpdate  : '<div class="dropload-update">↑释放更新</div>',
            domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>'
        },
        domDown : {
            domClass   : 'dropload-down',
            domRefresh : '<div class="dropload-refresh">↑上拉加载更多</div>',
            domUpdate  : '<div class="dropload-update">↓释放加载</div>',
            domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>'
        },
        loadUpFn : function(me){
            setTimeout(function(){
                queryStoreSumData();
                me.resetload();
            },500);
        },
        loadDownFn : function(me){
            
            setTimeout(function(){
                var currentPage = parseInt($("#currentPage").val());
                var totalPage = parseInt($("#totalPage").val());
                currentPage = currentPage + 1;
                if(currentPage > totalPage){
                    swal("", "已到最后一页...", "warning");
                }else{
                    $("#currentPage").val(currentPage);
                    var selectType = $('#selectType').val();
                    if(selectType == 1) {
                        getStoreResultByPhone();
                    } else {
                        getStoreResultByDate();
                    }
                    //getStoreListData(currentPage);
                }
                me.resetload();
            },800);
        }
    });
    exchangeEnsure = function(phoneNo){
        
        if(phoneNo==null || phoneNo==""){
            swal("", "手机号不能为空", "error");
            return false;
        }else{
            swal({
                text: "确认兑换？",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: '#DD6B55',
                confirmButtonText: '确定',
                cancelButtonText: "取消"
            },
            function(isConfirm){
                if (isConfirm){
                  exchange(phoneNo);
                } else {
                  return false;
                }
            });
        }
        
    };
    exchange = function(phoneNo){
                        
            var pCode = $("#pCode").val();
            $('#loadingDiv').css('display','block');
            $.ajax({
                 type:'get',
                 url:'../../../activity/store/exchange.do',
                 data: {
                     'pCode':pCode,
                     'phoneNo':phoneNo
                 },
                 dataType: 'json',
                 timeout:10000,
                 success:function(data){
                    $('#loadingDiv').css('display','none');
                    if(data.resultCode == 0){
                       swal("", "兑换成功", "success");
                       getStoreResultByDate();
                    }else{
                        swal("", "兑换失败，请稍后重试...", "error");
                        getStoreResultByDate();
                    }
                    
                },
                error: function(){
                    $('#loadingDiv').css('display','none');
                    swal("", "兑换失败，请稍后重试...", "error");
                    getStoreResultByDate();
                }
            });
    };
    //格式化日期,
    formatDate = function(dateStr,format){
        var date = new Date(dateStr);
        var paddNum = function(num){
          num += "";
          return num.replace(/^(\d)$/,"0$1");
        };
        //指定格式字符
        var cfg = {
           yyyy : date.getFullYear() //年 : 4位
          ,yy : date.getFullYear().toString().substring(2)//年 : 2位
          ,M  : date.getMonth() + 1  //月 : 如果1位的时候不补0
          ,MM : paddNum(date.getMonth() + 1) //月 : 如果1位的时候补0
          ,d  : date.getDate()   //日 : 如果1位的时候不补0
          ,dd : paddNum(date.getDate())//日 : 如果1位的时候补0
          ,hh : date.getHours()  //时
          ,mm : date.getMinutes() //分
          ,ss : date.getSeconds() //秒
        };
        format || (format = "yyyy-MM-dd hh:mm:ss");
        return format.replace(/([a-z])(\1)*/ig,function(m){return cfg[m];});
      };
    
    function gotoTop(min_height) {
        //预定义返回顶部的html代码，它的css样式默认为不显示
        var gotoTop_html = '<div id="gotoTop">返回顶部</div>';
        //将返回顶部的html代码插入页面上id为page的元素的末尾
        $("#page").append(gotoTop_html);
        $("#gotoTop").click(//定义返回顶部点击向上滚动的动画
        function() {
            $('html,body').animate({
                scrollTop : 0
            }, 700);
        }).hover(//为返回顶部增加鼠标进入的反馈效果，用添加删除css类实现
        function() {
            $(this).addClass("hover");
        }, function() {
            $(this).removeClass("hover");
        });
        //获取页面的最小高度，无传入值则默认为600像素
        min_height ? min_height = min_height : min_height = 600;
        //为窗口的scroll事件绑定处理函数
        $(window).scroll(function() {
            //获取窗口的滚动条的垂直位置
            var s = $(window).scrollTop();
            //当窗口的滚动条的垂直位置大于页面的最小高度时，让返回顶部元素渐现，否则渐隐
            if (s > min_height) {
                $("#gotoTop").fadeIn(100);
            } else {
                $("#gotoTop").fadeOut(200);
            };
        });
    };
    $('#gotoTop').click(function(){
        gotoTop(600);
    });
    
})();
