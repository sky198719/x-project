/**
 * Created by gaoshanshan_syp on 2017/6/9.
 */
require(['base','requirejs'], function($,requirejs) {
    var pages = {
        currentPage: 1,
        pageSize: 9,
        type: 4

    }
    var bol=true;
    loadYear(pages.currentPage);
    // loadContent(pages.type);
    // //企业季刊
    $('#quarterly-list').on('click','li',function (ev) {
        $(this).addClass("quar-active").siblings().removeClass("quar-active");
        var order=$(this).attr("order");
        $("."+order).show().siblings().hide();
    })

    function loadYear(currentpage) {
            $.ajax({
                url: '/biz/bulletin/platformReport?currentPage=' + currentpage + "&pageSize=" + pages.pageSize + "&type=" + pages.type,
                dataType: "json",
                type: 'get',
                beforeSend: function (request) {
                    request.setRequestHeader("s", "32 LENGTH CHARS");
                    request.setRequestHeader("clientId", "BOSS_ID");
                    request.setRequestHeader("clientTime", "1459845047000");
                },
                success: function (data) {
                    if (data.code == "200000") {
                        var yearlist = data.data.yearList;
                        var dataitem = data.data.items;
                        var totalCount = data.data.totalCount;
                        var len = dataitem.length;
                        var currentVal;
                        console.log(dataitem);
                        if (yearlist.length > 0) {
                            for (var y = 0; y < yearlist.length; y++) {
                                if(bol) {
                                    $('#quarterly-list').append((y == 0 ? '<li class="quar-active quar-first" order="order-' + (y + 1) + '">' : '<li order="order-' + (y + 1) + '">') + yearlist[y] + '</li>');
                                    $('#quarterly-content').append((y == 0 ? '<ul class="quarterly-main quarterly-first clearfix  order-' + (y + 1) + '">' : '<ul class="quarterly-main disnone clearfix  order-' + (y + 1) + '">') + '</ul>');
                                }
                                for (var i = 0; i < len; i++) {
                                    if (dataitem[i]['text'].substr(0, 4) == yearlist[y]) {
                                        $('#quarterly-content .order-' + (y + 1) + '').append(" <li>" +
                                            "<a class='quar-img' href=" + dataitem[i].textHref + "><img src=" + dataitem[i].extendUrl + "></a><a class='quar-title' href=" + dataitem[i].textHref + ">" + dataitem[i].text + "</a></li>");
                                    }
                                }
                            }

                        } else {
                            $('#quarterly-list').html('没有数据');
                        }
                        //遍历下边分页的数据
                        bol=false;
                        if (currentpage * (pages.pageSize - 1) < totalCount) {
                            currentVal = Math.ceil((totalCount - currentpage * (pages.pageSize - 1)) / 8);
                            loadYear(currentVal + 1);
                        } else {
                            return;
                        }
                    } else {
                        alert('网络错误，请重新尝试');
                    }
                },
                error: function (msg) {
                    console.log("errorMsg：" + JSON.stringify(msg));
                }
            })



    }

    // function loadContent(currentpage) {
    //  $.ajax({
    //     url: '/biz/bulletin/platformReport?currentPage=' + currentpage + "&pageSize=" + pages.pageSize + "&type=" + pages.type,
    //     dataType: "json",
    //     type: 'get',
    //     beforeSend: function (request) {
    //         request.setRequestHeader("s", "32 LENGTH CHARS");
    //         request.setRequestHeader("clientId", "BOSS_ID");
    //         request.setRequestHeader("clientTime", "1459845047000");
    //     },
    //     success: function (data) {
    //         var yearlist = data.data.yearList;
    //         var dataitem = data.data.items;
    //         var totalCount = data.data.totalCount;
    //         var len = dataitem.length;
    //         var currentVal;
    //         console.log(dataitem);
    //         if (yearlist.length > 0) {
    //             for (var y = 0; y < yearlist.length; y++) {
    //                 for (var i = 0; i < len; i++) {
    //                     if (dataitem[i]['text'].substr(0, 4) == yearlist[y]) {
    //                         $('#quarterly-content .order-' + (y + 1) + '').append(" <li>" +
    //                             "<a class='quar-img' href=" + dataitem[i].textHref + "><img src=" + dataitem[i].extendUrl + "></a><a class='quar-title' href=" + dataitem[i].textHref + ">" + dataitem[i].text + "</a></li>");
    //                     }
    //                 }
    //             }
    //         } else {
    //             $('#quarterly-list').html('没有数据');
    //         }
    //
    //     }
    //
    // })
    //
    // }

});


