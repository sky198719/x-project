var pages;
function Pages(currPage, totalNums, pageSize, tagName, callback,type) { //Paraments : 当前页码,总数据条数,每页数据量,需要渲染的目标标签标示,回调函数
    this.currPage = currPage;
    this.totalNums = totalNums;
    this.pageSize = pageSize;
    this.tagName = tagName;
    this.callback = callback;
    this.type = type;
     //计算总页数
    if (this.totalNums  % this.pageSize == 0) {
        this.pageNums=(this.totalNums / this.pageSize);
    }else{
        this.pageNums = parseInt(this.totalNums / this.pageSize)+1;
    }
}


Pages.prototype = {
    currPage: 1, totalNums: 0, pageSize: 0, tagName: "", callback: null,type:'',
    init: function () {
        if (this.totalNums == 0) {
            return;
        }

        var html = new StringBuffer();
        $(this.tagName).html(''); //清空原标签的分页内容

        //公共部分
        html.append('<li class="disabled"><a>总页数:&nbsp;&nbsp;&nbsp;' + this.pageNums + '页</a></li>');
        html.append('<li><a href="javascript:pages.toFirst()">首页</a></li>');
        html.append('<li><a href="javascript:pages.toPrevious()">«</a></li>');

        if (this.currPage == null) {        //排除null
            this.currPage = 1;
        }


        //只显示5个页码
        var startPage, endPage;

        if (this.currPage < 3) {                         //处在前3页,不进行滚动显示
            startPage = 1;
            endPage = 5;
        } else if (this.currPage > this.pageNums - 2) {    // 处在最后3页,不进行滚动显示
            startPage = this.pageNums - 4;
            endPage = this.pageNums;
        } else {                                          //处在中间页码,设定滚动显示范围
            startPage = this.currPage - 2;
            endPage = this.currPage + 2;
        }
        if (endPage > this.pageNums ) {
            endPage = this.pageNums ;
        }

        if(startPage<1)startPage = 1;

        for (var i = startPage; i <= endPage; i++) {
            html.append('<li ' + (this.currPage == i ? 'class="active"' : '') + '><a href="javascript:pages.toPage(' + (i) + ')">' + i + '</a></li>');
        }

        html.append('<li><a href="javascript:pages.toNext()">»</a></li>');
        html.append('<li><a href="javascript:pages.toLast()">尾页</a></li>');

        $(this.tagName).html(html.toString());


        //alert(html.toString());
        //document.getElementById("pagination").innerHTML(html.toString());
    },
    toFirst: function () {
        this.currPage = 1;                      //前往第一页
        this.init();
        if (typeof this.callback === "function") {
            this.callback(this.type);
        }
    },
    toLast: function () {                        //前往最后一页
        this.currPage = this.pageNums ;
        this.init();
        if (typeof this.callback === "function") {
            this.callback(this.type);
        }
    },
    toNext: function () {                        //前往下一页
        //判断是否最后一页
        if (pages.currPage == pages.pageNums ) {
            return;
        }
        this.currPage++;
        this.init();

        if (typeof this.callback === "function") {
            this.callback(this.type);
        }
    },
    toPrevious: function () {                    //前往前一页
        //判断是否为第一页
        if (pages.currPage == 1) {
            return;
        }
        this.currPage--;
        this.init();
        if (typeof this.callback === "function") {
            this.callback(this.type);
        }
    },
    toPage: function (pageNum) {                //前往指定页码
        this.currPage = pageNum;
        this.init();
        if (typeof this.callback === "function") {
            this.callback(this.type);
        }
    }
}

function StringBuffer() {
    this.__strings__ = [];
};
StringBuffer.prototype.append = function (str) {
    this.__strings__.push(str);
};
StringBuffer.prototype.toString = function () {
    return this.__strings__.join('');
};