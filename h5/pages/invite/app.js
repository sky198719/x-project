let item= function (){
    var b = {
        isLogin: function () {
            var a = window.location.href.match(/(?:[?|#|&]token=)([-_=\w\/]+)(?:[&?|]?)/);
            return a ? a[1] : false
        }, open: function (a) {
            location.href = "xxd://pagename=" + a;
            var ifr           = document.createElement('iframe');
            ifr.src           = "xxd://pagename=" + a;
            ifr.style.display = 'none';
            document.body.appendChild(ifr);
            var ifrTimer = setTimeout(function () {
                document.body.removeChild(ifr);
                clearTimeout(ifrTimer)
            }, 1000);

        }, down: function () {
            var f, h;

            function g() {
                if ($("#toapp").length > 0) {
                    $("#toapp").addClass("show").fadeIn(200)
                } else {
                    $('<div id="toapp" style="display: none" class="to-app show"><div><div><b id="chart1" class="mc-chart" data-sum="360"><i><em></em></i><i><em></em></i><b id="countdown">8</b></b><h1>请先下载客户端</h1><p>拥抱梦想 亦懂得积累</p><ul><li><button class="x_downapp">就看网页版</button></li><li><button class="down_app">下载客户端</button></li></ul></div></div></div>').appendTo("body").stop().fadeIn(200)
                }
                var c = 8;
                h     = setInterval(function () {
                    c--;
                    if (c >= 0) {
                        $("#countdown").text(c)
                    } else {
                        clearInterval(h)
                    }
                }, 1000);
                f     = setTimeout(function () {
                    a()
                }, 8000)
            }

            g();
            $(document).on("click", ".x_downapp", function () {
                a()
            });
            function a() {
                clearTimeout(f);
                clearInterval(h);
                $("#toapp").removeClass("show").fadeOut(200, function () {
                    $("#toapp").remove()
                })
            }
        }
    };
    return b
}
export default item()