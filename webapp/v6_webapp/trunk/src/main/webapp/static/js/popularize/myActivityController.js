/**
 * 邀请人
 * Created by chaihuangqi on 2015/7/7.
 */
define(['js/popularize/myActivityView','share','js/utils/animateNumber'], function (myActivityView,share,animateNumber) {
    var pageSize = 10;
    // 加载flag
    var loading = false;
    var invitationCtrl = {
        init: function () {
            /**
             * 事件定义
             *  pullToRefresh 下拉刷新
             *  infiniteScroll 无限刷新
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '.pull-to-refresh-content',
                    event: 'refresh',
                    handler: invitationCtrl.pullToRefresh
                },
                {
                    element: '.infinite-scroll',
                    event: 'infinite',
                    handler: invitationCtrl.infiniteScroll
                },
                {
                    element: '.ac-3',
                    event: 'click',
                    handler: invitationCtrl.buttonsClick
                },
                {
                    element:'#exclusiveLink',
                    event:'click',
                    handler:invitationCtrl.exclusiveLink
                },
                {
                    element:'#inviteCode',
                    event:'click',
                    handler:invitationCtrl.inviteCode
                }
            ];
            myActivityView.bindEvents({
                    bindings: bindings
                }
            );

            invitationCtrl.pullToRefresh();
            invitationCtrl.loadPopularizeData();
        },
        inviteCode:function(){
            //isLinkProhibit=0表示活动中状态
            if($$("#popularizeShareBtn").dataset.isLinkProhibit != "0") {
                xxdApp.alert('您当前活动的推广链接已被关闭。如想继续开通，请联系客服。', '提示');
                return ;
            }
            xxdApp.alert("未通过专属链接注册的好友，也可在“我的新新贷>绑定验证码” 页面输入一下邀请码，成为您的被邀用户哦<br>邀请码：" + $$("#popularizeShareBtn").dataset.popularizeCode,"我的邀请码");
        },
        exclusiveLink:function(){
            //isLinkProhibit=0表示活动中状态
            if($$("#popularizeShareBtn").dataset.isLinkProhibit != "0") {
                xxdApp.alert('您当前活动的推广链接已被关闭。如想继续开通，请联系客服。', '提示');
                return ;
            }
            xxdApp.alert("<div style='word-break:break-all;'>" + $$("#popularizeShareBtn").dataset.invitationLink +"</div>","我的专属链接");
        },
        loadPopularizeData:function(){
            req.callPost({
                url:'popularize/invitationData.do',
                data:{
                    activityCode:'consortium_extension'
                },
                dataType:'json',
                indicator:true,
                success:function(data){
                    var popularizeShareBtn = $$("#popularizeShareBtn");
                    popularizeShareBtn.dataset.isLinkProhibit = data.ISLINKPROHIBIT;
                    popularizeShareBtn.dataset.popularizeCode =  data.popularizeCode;
                    var invitationLink = data.frontUrl + "/invite/" + data.popularizeCode + ".html";
                    popularizeShareBtn.dataset.invitationLink = invitationLink;
                    popularizeShareBtn.dataset.content = "【新新贷】您有108元红包未领取，好友" + data.userName + "  邀请您登陆新新贷账户领取红包 " + invitationLink;;
                    popularizeShareBtn.dataset.sharePic = data.webappUrl + "/static/img/xxd/share_popularize.png";
                }
            });
        },

        buttonsClick:function(){
            //isLinkProhibit=0表示活动中状态
            if($$("#popularizeShareBtn").dataset.isLinkProhibit != "0") {
                xxdApp.alert('您当前活动的推广链接已被关闭。如想继续开通，请联系客服。', '提示');
                return ;
            }

            var content = $$("#popularizeShareBtn").dataset.content;
            var buttons1 = [];
            buttons1.push({
               text: '分享到微博',
               onClick: function () {
                   share.sinaShare({
                       url: '',
                       title: content,
                       pic: $$("#popularizeShareBtn").dataset.sharePic
                   });
               }
            });
            if(!appFunc.isWeixin()){
                buttons1.push({
                        text: '发送短信',
                        onClick: function () {
                            var symbol="";
                            var u = navigator.userAgent;
                            if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {
                                symbol="?";
                            } else if (u.indexOf('iPhone') > -1) {
                                if(u.match(/OS [4-7]_\d[_\d]* like Mac OS X/i)) {
                                    //ios8以下的系统
                                    symbol = ";"
                                }else{
                                    symbol = "&"
                                }
                            }
                            location.href = "sms:" + symbol + "body="+content;
                        }
                    }
                );
            }

            var buttons2 = [
                {
                    text: '取消',
                    color: 'red'
                }
            ];
            var groups = [buttons1, buttons2];
            xxdApp.actions(groups);

        },

        /**
         * 下拉刷新页面
         */
        pullToRefresh:function(){
            invitationCtrl.selectPopularize(1, "pull");
            $$("#currentPage").val(1);
            loading = false;
        },
        /**
         * 无限滚动
         */
        infiniteScroll: function () {
            // 如果正在加载，则退出
            if (loading) {
                return;
            }
            //==========================切记此处不能删除============================
            loading = true;
            //==========================切记此处不能删除============================
            var totalPages = $$("#totalPages").val();
            var currentPage = $$("#currentPage").val();
            currentPage = currentPage ? currentPage : 1;
            currentPage = parseInt(currentPage) + 1;
            if (currentPage > totalPages) {
                //提示已经全部显示
                xxdApp.addNotification({
                    title: '温馨提示',
                    hold: 3000,
                    message: '数据已全部加载完毕'
                });
                loading = true;
                return;
            }
            invitationCtrl.selectPopularize(currentPage, "push");
            $$("#currentPage").val(currentPage);
            //==========================切记此处不能删除============================
            setTimeout(function(){
                loading = false;
            },1500);
            //==========================切记此处不能删除============================
        },
        /**
         * 邀请注册用户查询
         * @param currentPage  当前页
         * @param type 刷新类型
         */
        selectPopularize:function(currentPage, type){
            req.callJSON({
                url:"popularize/selectPopularizeUser.do",
                data:{
                    currentPage: currentPage,
                    pageSize: pageSize,
                    activityCode:'consortium_extension'
                },
                dataType:'json',
                indicator: true,
                success:function(data){
                    $$("#totalPages").val(data.totalPages);
                    var totalSize = 0;
                    if(data.totalSize != undefined)  {
                        totalSize = data.totalSize;
                    }
                    //$$("#totalSize").html(data.totalSize);
                    animateNumber.animate({
                        element:'#totalSize',
                        from:0,
                        to:totalSize,
                        precision:0,
                        intervalNumber:30,
                        steps:50
                    });
                    var list = "";
                    if(data.invitationData != null){
                        list = data.invitationData.resultList;
                    }
                    var invitationList = [];
                    if(list!= null && list!=""){
                        for(var i = 0 ; i < list.length ; i++){
                            var b = list[i];
                            var array = b.registerTime.split(" ");
                            var lastTenderTime = b.lastTenderTime == null ? "暂无理财" : b.lastTenderTime;
                            invitationList.push({
                                'userName': b.userName,
                                'lastTenderTime': lastTenderTime,
                                'realName': b.realName == 1 ? true : false,
                                'mobile': b.mobile == 1 ? true : false,
                                'registerDate':array[0],
                                'registerTime': array[1]
                            });
                        }
                    }

                    try {
                        req.callGet({
                            url: GC.getHtmlPath() + 'popularize/myActivityUserList.html?'+ new Date().getTime(),
                            dataType: 'text',
                            success: function (result) {
                                myActivityView.showInvitationListItem({result:result,invitationList:invitationList,type:type });
                                // 加载完毕需要重置
                                xxdApp.pullToRefreshDone();
                            }
                        });
                    } catch (e) {
                        xxdApp.hideIndicator();
                    }
                }
            });

            setTimeout(function(){
                if (type == 'pull') {
                    // 加载完毕需要重置
                    xxdApp.pullToRefreshDone();
                }
            },5000);
        }

    }
    return invitationCtrl;
})