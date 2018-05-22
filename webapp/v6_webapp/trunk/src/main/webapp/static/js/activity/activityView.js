/**
 * Created by pufei on 2015/4/9.
 */
define(function(){
    var activityView = {
        init: function(param){
            appFunc.bindEvents(param.bindings);
        },
        setAtvResult:function(result){
            if(result <= 1){
                result=1
                $$('#typeTest').html('你的理财战斗力为：<br><br><br><a style="color:#682323">觉悟吧骚年！<br>点击查看<span class="f-36">最便捷</span>的赚钱方式吧！</a>');
                $$('#gofixedbg').html('求安慰')
            } else if(result == 2){
                $$('#typeTest').html('你的理财战斗力为：<br><br><br><a style="color:#682323">你还可以更有钱，试试用<span class="f-36">新新贷</span>吧！</a>');
            } else if(result == 3){
                $$('#typeTest').html('你的理财战斗力为：<br><br><br><a style="color:#682323">你那么会赚钱，试试<span class="f-36">钱生钱</span>的快感吧！</a>');
            } else if(result >= 4){
                result=4
                $$('#typeTest').html('你的理财战斗力为：<br><br><br><a style="color:#682323">骚年，<span class="f-36">换一种</span>方式赚钱吧！</a>');
            }
            $$('#typeImg').html('<img src="static/img/activity/txt-'+ result +'.png" class="typeImg">');
            $$('#atvImg').html('<img src="static/img/activity/gg-'+ result +'.png" style="height: 200px;"/>');
        }
    };
    return activityView;
});