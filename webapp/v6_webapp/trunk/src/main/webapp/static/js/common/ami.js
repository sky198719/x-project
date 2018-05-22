define(function () {
    return ami = {
        automaticMarkingInstruction: function () {
            req.callGet({
                url: GC.getHtmlPath() + 'common/automaticMarkingInstruction.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    req.callGet({
                        url: 'personal/getUserInfo.do',
                        dataType: 'json',
                        success: function (data) {
                            $$(".popup-bidhistory").html(result);
                            $$("#automaticMarkingClose").show();
                            if (data.realInfo != undefined) {
                                $$(".amiRealName").html(data.realInfo.realName);
                                $$(".amiidCardNo").html(data.realInfo.idCardNo);
                            }

                            if (data.user != undefined) {
                                $$(".amiMobile").html(data.user.mobile);
                            }
                            xxdApp.popup('.popup-bidhistory');
                        }
                    });
                }
            });
        },
        fenxiantishi: function () {
            req.callGet({
                url: GC.getHtmlPath() + 'common/tenderAgreement.html?' + GC.getVersion(),
                dataType: 'text',
                success: function (result) {
                    var html = '<div class="toolbar">'
                        + '<div class="toolbar-inner">'
                        + '<div class="left"></div>'
                        + '<div class="right"><a href="#" class="link close-picker">关闭</a></div>'
                        + '</div>'
                        + '</div>';
                    html += '<div class="picker-modal-inner"><div class="content-block" style="height: 100%;overflow-y: scroll;color: #666;font-size: 0.7rem;">' + result + '</div></div>';
                    $$("#indexPickerModal").html(html);
                    xxdApp.pickerModal(".picker-info");
                }
            });
        },
    };
});