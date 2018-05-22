'use strict';

var Dialog = {
    $dom: null,
    // opts 默认参数
    opts: {
        type: 'tips',
        showButtons: false,
        timing: 1500,
        content: '提示信息',
        exClassName: null,
        autoClose: true,
        timer: null,
        cancelText: '取消',
        okText: '确认',
        onOK: null,
        contentFull: false,
        onCancel: null,
        onTimeout: null
    },
    // 创建弹层dom
    _createDialog: function _createDialog() {
        var html = '\n                <div class="qui-dialog">\n                    <div class="qui-dialog-wrap">\n                        <div class="qui-dialog-content">\n                            <div class="qui-dialog-body">\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            ',
                $dom = $(html);
        $('body').append($dom);
        return $dom;
    },
    // 显示弹层
    show: function show(_opts) {
        var self = this,
                opts = $.extend(self.opts, _opts),
                _hide = function _hide() {
                    setTimeout(function () {
                        self.hide();
                        opts.onTimeout && opts.onTimeout();
                    }, opts.timing);
                },
                _showBtn = function _showBtn() {
                    self._showButtons(opts);
                };

        if (!this.$dom) {
            this.$dom = this._createDialog();
        }

        this.setContent(opts.content);

        if (opts.exClassName) {
            this.$dom.addClass(opts.exClassName);
        }

        if (opts.type == 'alert' || opts.type == 'confirm') {
            _showBtn();
        }

        if (opts.contentFull) {
            this.$dom.find('.qui-dialog-content').addClass('content-full');
        } else {
            this.$dom.find('.qui-dialog-content').removeClass('content-full');
        }

        this.$dom.removeClass('hide').addClass('show');
        if (opts.autoClose) {
            _hide();
        }
    },
    // 隐藏
    hide: function hide() {
        this.$dom.removeClass('show ' + this.opts.exClassName).addClass('hide');
        this.reset();
    },
    // 隐藏时 重置一些属性
    reset: function reset() {
        this.$dom.find('.qui-dialog-buttons').remove();
        this.opts.exClassName = this.opts.onTimeout = this.opts.onOK = this.opts.onCancel = null;
    },
    // 显示按钮dom
    _showButtons: function _showButtons(opts) {
        var self = this,
                cancelTextInner = opts.type == 'confirm' ? '<a class="qui-dialog-btn qui-dialog-btn-cancel" href="javascript:;">' + opts.cancelText + '</a>' : '',
                html = '\n                <div class="qui-dialog-buttons">\n                    ' + cancelTextInner + '\n                    <a class="qui-dialog-btn qui-dialog-btn-ok" href="javascript:;">' + opts.okText + '</a>\n                </div>\n            ';
        this.$dom.find('.qui-dialog-content').append(html).find('.qui-dialog-buttons').on('click', '.qui-dialog-btn-ok', function () {
            var result = opts.onOK ? opts.onOK() : true;
            if (result === false) return false;
            self.hide();
        }).on('click', '.qui-dialog-btn-cancel', function () {
            var result = opts.onCancel ? opts.onCancel() : true;
            if (result === false) return false;
            self.hide();
        });
    },
    // 修改内容
    setContent: function setContent() {
        var content = arguments.length <= 0 || arguments[0] === undefined ? this.content : arguments[0];

        if (content !== this.content) this.$dom.find('.qui-dialog-body').html(content);
    }
};


// tips提示，默认3秒后自动关闭
function tips(text) {
    var opts = arguments.length <= 1 || arguments[1] === undefined ? {} : arguments[1];

    opts = $.extend(opts, {
        type: 'tips',
        contentFull: false,
        autoClose: true,
        showButtons: false
    });
    opts.content = text;

    Dialog.show(opts);
}
// alert，一个按钮样式
function alert(text) {
    var opts = arguments.length <= 1 || arguments[1] === undefined ? {} : arguments[1];

    opts = $.extend(opts, {
        type: 'alert',
        contentFull: true,
        autoClose: false,
        showButtons: true
    });
    opts.content = text;

    Dialog.show(opts);
}
// confirm，两个按钮样式
function confirm(text) {
    var opts = arguments.length <= 1 || arguments[1] === undefined ? {} : arguments[1];

    opts = $.extend(opts, {
        type: 'confirm',
        contentFull: true,
        autoClose: false,
        showButtons: true
    });
    opts.content = text;

    Dialog.show(opts);
}
// 设置参数
function setOptions() {
    var opts = arguments.length <= 0 || arguments[0] === undefined ? {} : arguments[0];

    $.extend(Dialog.opts, opts);
}
function show() {
    var opts = arguments.length <= 0 || arguments[0] === undefined ? {} : arguments[0];

    Dialog.show(opts);
}
function hide() {
    Dialog.hide();
}

