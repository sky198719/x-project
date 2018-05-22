require(['base', 'float', 'trackBase', 'store', 'header', 'footer', 'backTop', "requirejs", "paging"], function ($, float, track, store) {
    /*财务信息tab切换*/
    $(".finalinfo_f").children("span").each(function (i, v) {
        $(this).on("click", function () {
            var v_about = $(this).attr("lang");
            $(this).addClass("active").siblings().removeClass("active");
            $("[about='" + v_about + "']").show().siblings().hide();
        });
    });
    /*审计报告tab切换*/
    $(".reportinfo_f").children("span").each(function (i, v) {
        $(this).on("click", function () {
            var v_about = $(this).attr("lang");
            $(this).addClass("active").siblings().removeClass("active");
            $("[about='" + v_about + "']").show().siblings('.mt-20').hide();
        });
    });

    /*产品介绍tab切换*/
    $(".prodintr").children("span").each(function (i, v) {
        $(this).on("click", function () {
            var v_about = $(this).attr("lang");
            $(this).addClass("active").siblings().removeClass("active");
            $("[about='" + v_about + "']").show().siblings().hide();
        });
    });

    /*加入我们-点击岗位名称折合岗位详情*/
    $(".jobname span").on("click", function () {
        $(this).parent().next().toggleClass("disnone");
        $(this).toggleClass("job_up");
    });

    /*发展历程-年份切换*/
    $(".tit-time li").each(function () {
        $(this).click(function () {
            $(this).addClass("active").siblings().removeClass("active");
            var about = $(this).attr("about");
            $("." + about).show().siblings().hide();
        });
    });

    /*安全保障-相关专题切换*/
    $(".focus-btn li").each(function () {
        $(this).click(function () {
            var v_about = $(this).attr("about");
            $(this).addClass("active").siblings().removeClass("active");
            $("ul[about='" + v_about + "']").show().siblings("ul").hide();

        });
    });
    //preview
    float.preview({});

    /*左侧菜单 点击标题折合其下菜单*/
    $(".menu h2").on("click", function () {
        $(this).siblings().toggleClass("disnone");
        $(this).children("i").toggleClass("down");
    });

    //organization
    tabs({
        tabsObject: $("#J_tabs"),//tab 栏
    });

    $.each($('.menu'),function(){
        if($(this).find('.active').length == 0){
            $(this).find('ul').addClass('disnone');
        }else{
            $(this).find('h2').find('i').addClass('down');
        }
    });

    function tabs(object) {
        var $tabs = object.tabsObject;
        var $tabsli = $tabs.find("li"), $tabContents = $(".j_tabContent");
        $tabsli.on("click", function (ev) {
            var $me = $(this);
            var index = $tabsli.index($me);
            $tabsli.removeClass("active").eq(index).addClass("active");
            $tabContents.addClass("hide").eq(index).removeClass("hide");
        });
    }

    /*多图预览*/
    function manyPreview(previewParentObj){
        var currentIndex = 0,
            currentSrc = '',
            obj = previewParentObj.find(".mui-morePreview");

        $(document).on("click", ".mui-morePreview", function (e) {
            var u = $(this),
                a = previewParentObj.find(".mui-morePreview");
            currentIndex = a.index(u);
            currentSrc = u.attr("_img");
            $('#J_muiPreviewImg').attr('src',currentSrc);
            $('.mui-dialog').show();
            e && e.preventDefault();
            e && e.stopPropagation();
        });

        $(document).on('click','.pre-img',function(e){
            if (currentIndex - 1 >= 0){
                currentIndex = currentIndex - 1;
                currentSrc = obj.eq(currentIndex).attr("_img");
                $('#J_muiPreviewImg').attr('src',currentSrc);
            }
            e && e.preventDefault();
            e && e.stopPropagation();
        });
        $(document).on('click','.next-img',function(e){
            if (currentIndex + 1 < previewParentObj.find(".mui-morePreview").length){
                currentIndex = currentIndex + 1;
                currentSrc = obj.eq(currentIndex).attr("_img");
                $('#J_muiPreviewImg').attr('src',currentSrc);
            }
            e && e.preventDefault();
            e && e.stopPropagation();
        });

        $(document).on("click",".mui-dialog",  function (e) {
            $('.mui-dialog').hide();
        });
        $(document).on("click", ".close", function (e) {
            $('.mui-dialog').hide();
            e && e.preventDefault();
            e && e.stopPropagation();
        });
        $(document).on("click",".mui-dialog .mui-morePreview",  function (e) {
            e && e.preventDefault();
            e && e.stopPropagation();
        });

    }
    var previewParentObj = $('.finalinfo');
    manyPreview(previewParentObj);
    
    
    
    
    // 布码init
    var userDO = store&&store.get("userDO")||{};
    track.init(userDO);

}, function (err) {
    var con = null;
    if ((con = window.console)) con.log(err);
    else alert(err);
});