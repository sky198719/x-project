require(['base', 'requirejs', 'trackBase', 'json', 'Swiper'], function ($, requirejs, track) {

    var userToken = window["userToken"]||$.readCookie("userToken");
    $.ajax({
        url:'/feapi/dmp/userinfo.html ',
        contentType: "application/json",
        dataType:"json",
        data: {
            userToken:userToken
        },
        type:"get",
        success:function (res){
            if (res && (status=res.status) && status.code==200) {
                track.init(res);
            } else {
                track.init();
            }
        },
        error:function (data){
            $.log (data);
            track.init();
        }
    });
    // 提示消息插件
    var xui = {
        Prompt: function (conf) {
            var id    = conf.id,
                dim   = conf.dim || false,
                theme = conf.theme || 'default',
                time  = conf.time || 2000,
                icon  = conf.icon,
                iconTag,
                msg   = conf.msg || '',
                mod   = '.xui-prompt';

            if ($(mod + '[dim="true"]').length > 0) {
                dim = false;
            }
            if (icon) {
                icon    = conf.icon;
                iconTag = '<p class="prompt-icon"><b></b></p>';
            } else {
                icon    = '';
                iconTag = '';
            }
            if (!id) {
                id = '#prompt' + ( $(mod).length * 1 + 1);
            }
            if (msg) {
                $('.xui-prompt').remove();
                var code = '<div style="display:none" id="' + id + '" class="xui-prompt" dim="' + dim + '" theme="' + theme + '" icon="' + icon + '"><div class="xui-prompt-dim"><div class="xui-prompt-content">' + iconTag + '<p class="xui-prompt-msg">' + msg + '</p></div></div></div>';
                var $id  = $(code);

                if ($(mod).length === 0) {
                    $('body').addClass('prompt-o-hide').append($id);
                }

                setTimeout(function () {
                    $id.stop().fadeIn(200);
                }, 100);

                setTimeout(function () {
                    $id.stop().fadeOut(function () {
                        $('.xui-prompt').remove();
                        $('body').removeClass('prompt-o-hide');
                    });

                }, parseInt(time));

                return $id;
            }

        }
    };

    // 接收网址传参
    // console.log('获取网址传参并切害为数组');
    var thisSearch = window.location.search.slice(1).split('&');
    // console.log('↓');
    // console.log(thisSearch);
    var urlParameter = {}, userId, sign;
    // console.log('↓');
    if (thisSearch.length > 0) {
        // console.log('如果网传参非空');
        // console.log('↓');
        for (var i = 0; i < thisSearch.length; i++) {
            // console.log(thisSearch[i].split('='));
            urlParameter[thisSearch[i].split('=')[0]] = thisSearch[i].split('=')[1];
        }
        // console.log('分割多个传参为对象');
        // console.log('↓');
        // console.log(urlParameter);
        // console.log('↓');

        userId = urlParameter['userId'];
        sign   = urlParameter['sign'];
    } else {
        // console.log('如果网址传参为空');
        // console.log('↓');
        // console.log('提示消息');
        xui.Prompt({
            icon : 'error',
            msg  : '页面鉴权失败，请返回重新尝试',
            theme: 'topbar'
        });
        // console.log('↓');
        // console.log('2.5秒后退出评测页');
        setTimeout(function () {
            history.go(-1);
        }, 2500)
    }

    // console.log('查看是app打开还是web打开');
    var browser = $('html').attr('browser');
    // console.log('↓');
    // console.log(browser);
    if (browser == 'app') {
        // console.log('如果是app打开');
        // console.log('↓');
        // console.log('判断传参是否完整？');
        // console.log('↓');
        // console.log('判断传参是否完整？');
        // console.log('↓');
        if (!userId || !sign) {
            // console.log('如果传参不完整');
            // console.log('↓');
            // console.log('提示消息');
            xui.Prompt({
                icon : 'error',
                msg  : '页面鉴权失败，请返回重新尝试',
                theme: 'topbar'
            });
            // console.log('↓');
            // console.log('2.5秒后退出评测页');
            setTimeout(function () {
                history.go(-1);
            }, 2500)
        }
        // console.log('如果传参完整');
        // console.log('↓');
        function checkSign() {
            var result = false;
            $.ajax({
                url     : '/m/memberDay922/checkAppSign.do',
                data    : {
                    uid : userId,
                    sign: sign
                },
                type    : "get",
                async   : false,
                dataType: 'json',
                success : function (result) {
                    if (result.resultCode == 0) {
                        result = true;
                    } else if (result.resultCode == 400) {
                        result = false;
                    } else {
                        // alert("页面鉴权失败，请返回重新尝试");
                        xui.Prompt({
                            icon : 'error',
                            msg  : '页面鉴权失败，请返回重新尝试',
                            theme: 'topbar'
                        });

                        setTimeout(function () {
                            history.go(-1);
                        }, 2500)

                    }
                }
            });
            return result;
        }

        checkSign();
    }


    // console.log(userId,sign);

    var riskResult = {
        level1: {
            totalScore: '25分以下',
            typeName  : '风险厌恶型',
            notes     : '亲，您的金融投资风险偏好或承受能力很低，不建议以P2P为主要投资理财渠道。您可购买余额宝类货币基金，如果对P2P感兴趣，可以参加新新贷的“新新宝体验计划”。（本测试仅适用于个人，结果供参考）'
        },
        level2: {
            totalScore: '25—49',
            typeName  : '低风险偏好型',
            notes     : '亲，您的金融投资风险偏好或承受能力较低，可以投资P2P，建议在P2P领域的资产配置不要超过个人可支配收入20%。可以尝试在新新贷做分散投资，如果每月在新做每月固定金额的定投效果会不错哦。（本测试仅适用于个人，结果供参考）'
        },
        level3: {
            totalScore: '50—69',
            typeName  : '中等风险偏好型',
            notes     : '亲，您的金融投资风险偏好或承受能力中等，能承受一定的风险，可以投资P2P，建议在P2P领域的投资不要超过个人可支配收入的40%，可以在新新贷做投资，注意控制生活开支，以及自身资金的流动性哦。（本测试仅适用于个人，结果供参考）'
        },
        level4: {
            totalScore: '70—89',
            typeName  : '高风险偏好型',
            notes     : '亲，您的金融投资风险偏好或承受能力非常高，在P2P领域的投资可以占到个人可收入的60%。新新贷建议您可以适当将资产配置到股市、期货等高风险高风险投资领域，新新贷友情提醒：投资有风险，入市需谨慎。（本测试仅适用于个人，结果供参考）'
        },
        level5: {
            totalScore: '90以上',
            typeName  : '极端风险偏好型',
            notes     : '亲，您的金融投资风险偏好极高，普通P2P理财方式可能无法满足您的投资需求，可对股市、期货等高风险金融资产做配置，并可尝试比特币、众筹、私募股权基金、艺术品、贵金属等投资。新新贷友情提示，投资有风险，入市需谨慎。（本测试仅适用于个人，结果供参考）'
        }
    };

    $(function () {

        var tab1_box = new Swiper('#tab1_box', {
            autoHeight    : true,
            simulateTouch : false,
            onlyExternal  : true,
            prevButton    : '.swiper-button-prev',
            nextButton    : '.swiper-button-next',
            onSlidePrevEnd: function (tab1_box) {
                $('.score' + tab1_box.activeIndex).prop('checked', false);
            }
        });

        var scores = [];
        $(document).on('change', '.xui-radio', function () {
            var sub      = $(this).data('nb'), score = parseInt($(this).val());
            var scoreSub = 'score' + sub;
            $(this).addClass(scoreSub);
            scores.splice(sub, 1, score);
            setTimeout(function () {
                tab1_box.slideNext();
                if (sub === 7) {
                    $('#to_submit').prop('disabled', false);
                }
            }, 400);

        });

        $(document).on('click', '#to_submit', function (e) {
            var win = $(this).parents('.win').index() + 1;
            e.preventDefault();
            var totalScores = 0;
            for (var i = 0; i < scores.length; i++) {
                totalScores += scores[i];
            }

            var thisRiskResult;
            // console.log(totalScores);
            if (totalScores < 25) {
                thisRiskResult = riskResult.level1;
                // console.log(thisRiskResult)
            } else if (25 <= totalScores && totalScores <= 49) {
                thisRiskResult = riskResult.level2;
                // console.log(thisRiskResult)
            } else if (50 <= totalScores && totalScores <= 69) {
                thisRiskResult = riskResult.level3;
                // console.log(thisRiskResult)
            } else if (70 <= totalScores && totalScores <= 89) {
                thisRiskResult = riskResult.level4;
                // console.log(thisRiskResult)
            } else if (totalScores >= 90) {
                thisRiskResult = riskResult.level5;
                // console.log(thisRiskResult)
            }
            $('#total_scores').text(totalScores);
            $('#risk_type_name').text(thisRiskResult.typeName);
            $('#risk_notes').text(thisRiskResult.notes);
            document.title = '风险能力评测结果';

            openWin(win);


            $.ajax({
                url     : '/m/activity/commitRiskExam.do',
                dataType: 'json',
                type    : 'POST',
                data    : {
                    "totalScores": totalScores
                },
                success : function (result) {
                    // console.log(result);
                    if (result.resultCode == 0) {
                        // console.log('success');
                        xui.Prompt({
                            icon : 'success',
                            msg  : result.msg,
                            theme: 'topbar'
                        });
                    } else {
                        xui.Prompt({
                            icon : 'warning',
                            msg  : result.msg,
                            theme: 'topbar'
                        });
                    }

                },
                error   : function () {
                    // console.log('ajax error');
                    xui.Prompt({
                        icon : 'error',
                        msg  : '评测结果提交失败，请检查网络并重新评测!',
                        theme: 'topbar'
                    })
                }
            });

            // 备份，上线前删除
            /*if (browser == 'app') {
             console.log('app');
             $.ajax({
             url     : '/v5_mobile/mobile/user/commitRiskExam.html',
             dataType: 'json',
             type    : 'POST',
             data    : {
             "userId"     : userId,
             "totalScores": totalScores,
             "sign"       : sign
             },
             success : function (result) {
             console.log(result);
             if (result.resultCode == '200000') {
             console.log('success');
             xui.Prompt({
             icon: 'success',
             msg : result.msg,
             theme:'topbar'
             });
             }else{
             xui.Prompt({
             icon: 'warning',
             msg : result.msg,
             theme:'topbar'
             });
             }

             },
             error   : function () {
             // console.log('ajax error');
             xui.Prompt({
             icon: 'error',
             msg : '评测结果提交失败，请检查网络!',
             theme:'topbar'
             })
             }
             });
             } else if (browser == 'web') {
             console.log('web');
             $.ajax({
             url     : '/m/activity/commitRiskExam.do',
             dataType: 'json',
             type    : 'POST',
             data    : {
             "totalScores": totalScores
             },
             success : function (result) {
             console.log(result);
             if (result.resultCode == 0) {
             console.log('success');
             xui.Prompt({
             icon: 'success',
             msg : result.msg,
             theme:'topbar'
             });
             }else{
             xui.Prompt({
             icon: 'warning',
             msg : result.msg,
             theme:'topbar'
             });
             }

             },
             error   : function () {
             // console.log('ajax error');
             xui.Prompt({
             icon: 'error',
             msg : '评测结果提交失败，请检查网络!',
             theme:'topbar'
             })
             }
             });
             }*/


        });

        // 返回
        $(document).on('click', '#toBack', function () {
            history.go(-1);
        });

        // 关闭评测
        $(document).on('click', '#x_assessment', function () {
            $('#dialog1').addClass('show');
        });

        // 继续评测
        $(document).on('click', '#x_dialog1', function () {
            $('#dialog1').removeClass('show');
        });

        // 重新评测
        $(document).on('click', '#reset_scores', function () {
            var win = $(this).parents('.win').index();
            tab1_box.slideTo(0, 0, false);
            $('#tab1_box').find('.xui-radio').prop('checked', false);
            $('#to_submit').prop('disabled', true);
            closeWin(win);
        });

        function openWin(win) {
            $('.win').eq(win).css('display', 'block');
            setTimeout(function () {
                $('.win').eq(win).addClass('open');
            }, 100)


        }

        function closeWin(win) {
            $('.win').eq(win).removeClass('open');
            setTimeout(function () {
                $('.win').eq(win).css('display', 'block');
            }, 500)
        }

    });


}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});