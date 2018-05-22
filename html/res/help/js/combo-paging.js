require(['paging'], function (paging) {

    if ($("#J_mapmark").length > 0 && $("#J_mapmark").val() != "undefined") {
        var mapmark = $("#J_mapmark").val();
        /*华东地区 分公司地址*/
        loadAreases(mapmark);
    }
    if ($("#J_news").length > 0 && $("#J_news").val() != "undefined") {
        var mark_news = $("#J_news").val();
        //1加载平台公告 2加载最新动态
        loadNews(mark_news);

    }
    if ($("#J_runreports").length > 0 && $("#J_runreports").val() != "undefined") {
        var mark_runreports = $("#J_runreports").val();
        //1年度运营报告 2 季度运营报告 3月度运营报告 4季刊
        loadReports(mark_runreports, "report-season");

    }

    /*联系我们-分公司地址*/
    $(".areas li").each(function () {
        $(this).click(function () {
            $(this).addClass("active").siblings().removeClass("active");
            var type = $(this).attr("about");
            loadAreases(type);
        });
    });

    /*新闻报道*/
    $(".newsreports").children("span").each(function (i, v) {
        $(this).on("click", function () {
            var v_about = $(this).attr("lang");
            $(this).addClass("active").siblings().removeClass("active");
            $("[about='" + v_about + "']").show().siblings().hide();
            var type;
            if (v_about == "recentnews") {//最新动态
                type = 2;
            } else if (v_about == "newstrends") { //新闻动态
                type = 3;
            }
            loadNews(type)
        });
    });

    /*运营报告*/
    $(".reports-type li").each(function () {
        $(this).click(function () {
            var v_about = $(this).attr("lang");
            $(this).addClass("active").siblings().removeClass("active");
            $(".reports-info").children("." + v_about).show().siblings().hide();
            var type;
            if (v_about == "report-moth") {
                type = 3;
            } else if (v_about == "report-season") {
                type = 2;
            } else if (v_about == "report-year") {
                type = 1;
            }
            loadReports(type, v_about);
        });
    });

    /*分公司地址*/
    function loadAreases(type) {
        $(".adresses").html('');
        var pagesize, currentPage;
        if (pages == null || pages.type != type) {        //pages为null 说明数据是第一次加载 需要自定义每页数据量和当前页（设置当前页为0）
            pagesize = 4;
            currentPage = 1;
            $.ajax({
                url: "/biz/bulletin/branchOffice?area=" + type + "&pageSize=" + pagesize + "&currentPage=" + currentPage,
                type: 'get',
                dataType: "json",
                beforeSend: function (request) {
                    request.setRequestHeader("s", "www");
                    request.setRequestHeader("clientId", "001");
                    request.setRequestHeader("clientTime", "001");
                },
                success: function (data) {
                    if (data.code == "200000") {
                        var totalCount = data.data.totalCount;
                        $.each(data.data.items, function (i, v) {
                            var d = new Date(v.establishmentTime),
                                date1 = d.getFullYear() + "年" + (d.getMonth() + 1) + "月" + d.getDate() + "日";
                            $(".adresses").append((i % 2 != 0 ? "<ul>" : "<ul class='mr100'>")
                                + "<li class='name'>" + v.companyName + "</li>" +
                                "<li>公司地址：<span>" + v.companyAddress + "</span></li>" +
                                "<li>成立时间：<span>" + date1 + "</span></li>" +
                                "<li>负责人 ：<span>" + v.personInCharge + "</span></li>" +
                                "<li>联系电话/投诉电话 ：<span>" + v.contactNumber + "</span></li>" +
                                "<li>员工人数 ：<span>" + v.numberOfEmployees + "人</span></li></ul>"
                            )
                        })
                        //初始化pages对象 只需要在第一次初始化时执行
                        //Paraments : 当前页码,总数据条数,每页数据量,需要渲染的目标标签标示 这里的参数中 总数据条数需要从接口返回数据中获取
                        pages = new Pages(currentPage, totalCount, pagesize, '.pagination', loadAreases, type);
                        pages.init()
                    }
                },
                error: function (msg) {
                    console.log("errorMsg：" + JSON.stringify(msg));
                }
            });
        } else {
            $.ajax({
                url: "/biz/bulletin/branchOffice?area=" + type + "&pageSize=" + pages.pageSize + "&currentPage=" + pages.currPage,
                type: 'get',
                dataType: "json",
                beforeSend: function (request) {
                    request.setRequestHeader("s", "www");
                    request.setRequestHeader("clientId", "001");
                    request.setRequestHeader("clientTime", "001");
                },
                success: function (data) {
                    //PS:本代码块执行时 pages已经初始化完毕,所以不需要在执行new Pages操作
                    if (data.code == "200000") {
                        var totalCount = data.data.totalCount;
                        $.each(data.data.items, function (i, v) {
                            var d = new Date(v.establishmentTime),
                                date1 = d.getFullYear() + "年" + (d.getMonth() + 1) + "月" + d.getDate() + "日";
                            $(".adresses").append((i % 2 != 0 ? "<ul>" : "<ul class='mr100'>")
                                + "<li class='name'>" + v.companyName + "</li>" +
                                "<li>公司地址：<span>" + v.companyAddress + "</span></li>" +
                                "<li>成立时间：<span>" + date1 + "</span></li>" +
                                "<li>负责人 ：<span>" + v.personInCharge + "</span></li>" +
                                "<li>联系电话/投诉电话 ：<span>" + v.contactNumber + "</span></li>" +
                                "<li>员工人数 ：<span>" + v.numberOfEmployees + "人</span></li></ul>"
                            )
                        })
                    }
                },
                error: function (msg) {
                    console.log("errorMsg：" + JSON.stringify(msg));
                }
            });
        }
    }

    /*1平台公告 2最新动态 3媒体报道*/
    function loadNews(type) {
        $(".notices").html('');
        var pagesize, currentPage;
        if (pages == null || pages.type != type) {        //pages为null 说明数据是第一次加载 需要自定义每页数据量和当前页（设置当前页为0）
            pagesize = 10;
            currentPage = 1;
            $.ajax({
                url: "/biz/bulletin/newsInfo?currentPage=" + currentPage + "&pageSize=" + pagesize + "&type=" + type,
                type: 'get',
                dataType: "json",
                beforeSend: function (request) {
                    request.setRequestHeader("s", "testxx");
                    request.setRequestHeader("clientId", "testxx");
                    request.setRequestHeader("clientTime", "1459845047000");
                },
                success: function (data) {
                    if (data.code == "200000") {
                        var totalCount = data.data.totalCount;
                        $.each(data.data.items, function (i, v) {
                            $(".notices").append(
                                "<li><a href='" + v.textHref + "'><span class='tit'>" + v.text + "</span><span class='time'>" + v.newsTime + "</span></a></li>"
                            )
                        })
                        //初始化pages对象 只需要在第一次初始化时执行
                        //Paraments : 当前页码,总数据条数,每页数据量,需要渲染的目标标签标示 这里的参数中 总数据条数需要从接口返回数据中获取
                        pages = new Pages(currentPage, totalCount, pagesize, '.pagination', loadNews, type);
                        pages.init()
                    }
                },
                error: function (msg) {
                    console.log("errorMsg：" + JSON.stringify(msg));
                }
            });
        } else {
            $.ajax({
                url: "/biz/bulletin/newsInfo?currentPage=" + pages.currPage + "&pageSize=" + pages.pageSize + "&type=" + type,
                type: 'get',
                dataType: "json",
                beforeSend: function (request) {
                    request.setRequestHeader("s", "testxx");
                    request.setRequestHeader("clientId", "testxx");
                    request.setRequestHeader("clientTime", "1459845047000");
                },
                success: function (data) {
                    //PS:本代码块执行时 pages已经初始化完毕,所以不需要在执行new Pages操作
                    if (data.code == "200000") {
                        var totalCount = data.data.totalCount;
                        $.each(data.data.items, function (i, v) {
                            $(".notices").append(
                                "<li><a href='" + v.textHref + "'><span class='tit'>" + v.text + "</span><span class='time'>" + v.newsTime + "</span></a></li>"
                            )
                        })
                    }

                },
                error: function (msg) {
                    console.log("errorMsg：" + JSON.stringify(msg));
                }
            });
        }
    }

    /*1年度运营报告 2 季度运营报告 3月度运营报告 4季刊*/
    function loadReports(type, v_about) {
        $(".runreports").html('');
        var pagesize, currentPage;
        if (pages == null || pages.type != type) {        //pages为null 说明数据是第一次加载 需要自定义每页数据量和当前页（设置当前页为0）
            pagesize = 9;
            currentPage = 1;
            $.ajax({
                url: "/biz/bulletin/platformReport?currentPage=" + currentPage + "&pageSize=" + pagesize + "&type=" + type,
                type: 'get',
                dataType: "json",
                beforeSend: function (request) {
                    request.setRequestHeader("s", "32 LENGTH CHARS");
                    request.setRequestHeader("clientId", "BOSS_ID");
                    request.setRequestHeader("clientTime", "1459845047000");
                },
                success: function (data) {
                    if (data.code == "200000") {
                        var totalCount = data.data.totalCount;
                        $.each(data.data.items, function (i, v) {
                            if (v.extendUrlBack != null && v.extendUrlBack != "undefined") {
                                $(".runreports").append(((i + 1) % 3 == 2 ? "<li class='mid'>" : "<li>")
                                    + "<div class='replacea'><input type='hidden' value=" + v.textHref + ">"
                                    + "<div class='flip'><img class='front' src=" + v.extendUrlFront + ">"
                                    + "<img class='behind' src=" + v.extendUrlBack + ">"
                                    + "</div>"
                                    + "<p>" + v.text + "</p></div></li>"
                                )
                            } else {
                                $(".runreports").append(((i + 1) % 3 == 2 ? "<li class='mid'>" : "<li>")
                                    + "<div class='fulls'><input type='hidden' value=" + v.textHref + ">" +
                                    "<img src=" + v.extendUrlFront + ">" +
                                    "<p>" + v.text + "</p></div></li>"
                                )
                            }

                        })
                        //初始化pages对象 只需要在第一次初始化时执行
                        //Paraments : 当前页码,总数据条数,每页数据量,需要渲染的目标标签标示 这里的参数中 总数据条数需要从接口返回数据中获取
                        pages = new Pages(currentPage, totalCount, pagesize, '.pagination', loadReports, type);
                        pages.init()
                    }
                },
                error: function (msg) {
                    console.log("errorMsg：" + JSON.stringify(msg));
                }
            });
        } else {
            $.ajax({
                url: "/biz/bulletin/platformReport?currentPage=" + pages.currPage + "&pageSize=" + pages.pageSize + "&type=" + type,
                type: 'get',
                dataType: "json",
                beforeSend: function (request) {
                    request.setRequestHeader("s", "32 LENGTH CHARS");
                    request.setRequestHeader("clientId", "BOSS_ID");
                    request.setRequestHeader("clientTime", "1459845047000");
                },
                success: function (data) {
                    //PS:本代码块执行时 pages已经初始化完毕,所以不需要在执行new Pages操作
                    if (data.code == "200000") {
                        var totalCount = data.data.totalCount;
                        $.each(data.data.items, function (i, v) {
                            if (v.extendUrlBack != null && v.extendUrlBack != "undefined") {
                                $(".runreports").append(((i + 1) % 3 == 2 ? "<li class='mid'>" : "<li>")
                                    + "<div class='replacea'><input type='hidden' value=" + v.textHref + ">"
                                    + "<div class='flip'><img class='front' src=" + v.extendUrlFront + ">"
                                    + "<img class='behind' src=" + v.extendUrlBack + ">"
                                    + "</div>"
                                    + "<p>" + v.text + "</p></div></li>"
                                )
                            } else {
                                $(".runreports").append(((i + 1) % 3 == 2 ? "<li class='mid'>" : "<li>")
                                    + "<div class='fulls'><input type='hidden' value=" + v.textHref + ">" +
                                    "<img src=" + v.extendUrlFront + ">" +
                                    "<p>" + v.text + "</p></div></li>"
                                )
                            }

                        })
                    }

                },
                error: function (msg) {
                    console.log("errorMsg：" + JSON.stringify(msg));
                }
            });
        }
    }

    $(document).on("mouseenter", "div.flip", function () {
        var $front = $(this).find('.front'), $behind = $(this).find('.behind'), speed = 80, dis = 210;
        $front.animate({left: dis / 2, width: 0}, speed, function () {
            $front.hide();
            $behind.show().animate({left: 0, width: dis}, speed);
        });
    });
    $(document).on("mouseleave", "div.flip", function () {
        var $front = $(this).find('.front'), $behind = $(this).find('.behind'), speed = 80, dis = 210;
        $behind.animate({left: dis / 2, width: 0}, speed, function () {
            $behind.hide();
            $front.show().animate({left: 0, width: dis}, speed);
        });
    });
    $(document).on("click", "div.replacea", function () {
        var hiddenv = $(this).children("input").val();
        window.open(hiddenv);
    });
    $(document).on("click", "div.fulls", function () {
        var hiddenv = $(this).children("input").val();
        window.open(hiddenv);
    });
});