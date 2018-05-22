/**
 * Created by pufei on 2015/2/9.
 */
/*
 * 设置实名认证
 */
define(['js/card','js/personal/idCertifiedView'], function (card,idCertifiedView) {
    var imgMaxSize = 20480000;
    var idCertifiedCtrl = {
        init: function(event){
            var page = appFunc.getEventDetailPageQuery(event);
            //判断是否需要上传照片
            var realType =page.realType;
            if(realType!=null){
                $$('#uploadIdCardPicFlag').val(realType);
                $$('#realNameImg_page').show();
            }

            /**
             * 事件定义
             * @type {*[]}
             */
            var bindings = [
                {
                    element: '#id-certified-start',
                    event: 'click',
                    handler: idCertifiedCtrl.submitCertified
                },
                {
                    element: '#frontFile',
                    event: 'change',
                    handler: idCertifiedCtrl.frontOnchange
                },
                {

                    element: '#backFile',
                    event: 'change',
                    handler: idCertifiedCtrl.backOnchange
                }
            ];
            idCertifiedView.init({
                    bindings: bindings
            });
            if(idCertifiedCtrl.isCanvasSupported()) {
                $$("#frontFile").on("change",function(e){
                    idCertifiedCtrl.handleFileSelect(e,"frontFile");
                });
                $$("#backFile").on("change",function(e){
                    idCertifiedCtrl.handleFileSelect(e,"backFile");
                });
            }
        },
        frontOnchange:function(){
            /*if($$('#frontFile')[0].files[0].size > imgMaxSize){
                xxdApp.alert('上传图片不能大于2M，请重新上传！','提示',function(){
                    $$('#frontFile').val('');
                    $$('#upFileName').html('上传身份证正面');
                });
                return
            }*/

            if($$('#frontFile')[0].files[0] == null || $$('#frontFile')[0].files[0] == 'undefined'){
                $$('#upFileName').html('上传身份证正面');
            }else{
                $$('#upFileName').html('已选择：'+$$('#frontFile')[0].files[0].name);
            }
        },
        backOnchange:function(){
            /*if($$('#backFile')[0].files[0].size > imgMaxSize){
                xxdApp.alert('上传图片不能大于2M，请重新上传！','提示',function(){
                    $$('#backFile').val('');
                    $$('#backFileName').html('上传身份证正面');
                });
                return
            }*/


            if($$('#backFile')[0].files[0] == null || $$('#backFile')[0].files[0] == 'undefined'){
                $$('#backFileName').html('上传身份证反面');
            }else{
                $$('#backFileName').html('已选择：'+$$('#backFile')[0].files[0].name);
            }
        },
        submitCertified:function(){
            var realname = $$('#realname').val();
            var idCardNo = $$('#idCardNo').val();
            if(''==realname){
                xxdApp.alert('必须输入真实姓名，请重新输入','提示');
                return false;
            }
            if(''==idCardNo){
                xxdApp.alert('必须输入证件号码，请重新输入','提示');
                return false;
            }

            if(!card.isCardValidate(idCardNo)){
                xxdApp.alert('请输入正确的身份证号码','提示');
                return false;
            }
            var uploadIdCardPicFlag = $$('#uploadIdCardPicFlag').val();
            if(uploadIdCardPicFlag == '1'){
                var flag = 0;
                //需要上传身份证图片
                $$("input[name='file']").each(function () {
                    if ("" != $$(this).val()) {
                        flag++;
                    }
                });
                if (flag != 2) {
                    xxdApp.alert("请选择身份证图片，正反两面都必须选择","抱歉");
                    return;
                }
                $$("input[name='file']").each(function () {
                    if ("" != $$(this).val()) {
                        flag++;
                    }
                });
                var result = true;
                var resultNum = 0;
                if (flag < 0) {
                    xxdApp.alert("请选择证件图片","抱歉");
                    return;
                }
            }
            var cardType = 1;
            xxdApp.confirm(realname+' 身份证号码 '+idCardNo,'请再次确认您的信息', function () {

                var pathUp = '';
                var pathBack = '';
                if(uploadIdCardPicFlag == '1') {
                    var formdata = new FormData();
                    formdata.append("frontFile", $$('#frontFile')[0].files[0]);
                    formdata.append("backFile", $$('#backFile')[0].files[0]);
                    xxdApp.showPreloader('正在认证中...');
                    req.callFileUpload({
                        url: 'approve/realNameImg.do',
                        data: formdata,
                        timeout: 120000,
                        success: function (result) {
                            xxdApp.hidePreloader();
                            var data =  $.parseJSON(result);
                            if(data.code == -400){
                                xxdApp.alert(data.info, '提示',function(){
                                    xxdApp.loginScreen();
                                });
                                return false
                            } else if(data.code == -2 || data.code == -3 || data.code == -1){
                                xxdApp.alert(data.info, '提示',function(){
                                    GS.loadPage('personal/personalInfo.html');
                                });
                                return false
                            } else {
                                pathUp = data.pathUp;
                                pathBack = data.pathBack;
                                idCertifiedCtrl.realNameApr(realname,idCardNo,uploadIdCardPicFlag,pathUp,pathBack,cardType)
                            }
                        }
                    });
                }else{
                    idCertifiedCtrl.realNameApr(realname,idCardNo,uploadIdCardPicFlag,pathUp,pathBack,cardType)
                }
            });
        },
        realNameApr :function(realname,idCardNo,uploadIdCardPicFlag,pathUp,pathBack,cardType){
            xxdApp.showPreloader('正在努力认证中，请稍后...');
            req.callPost({
                url: "approve/realNameApr.do",
                data:{
                    realname: realname,
                    idCardNo: idCardNo,
                    uploadIdCardPicFlag:uploadIdCardPicFlag,
                    cardType:cardType,
                    picUp:pathUp,
                    picDown:pathBack
                },
                dataType:'json',
                timeout:120000,
                success: function (data) {
                    xxdApp.hidePreloader();
                    xxdApp.alert(data.msg, '提示', function () {
                        GS.loadPage('personal/personalInfo.html');
                    });
                }
            });
        },
        isCanvasSupported:function() {
            var elem = document.createElement('canvas');
            return !!(elem.getContext && elem.getContext('2d'));
        },
        compress: function(source_img_obj, quality, output_format){

            var mime_type = "image/jpeg";
            if(output_format!=undefined && output_format=="png"){
                mime_type = "image/png";
            }

            var cvs = document.createElement('canvas');
            //naturalWidth真实图片的宽度
            cvs.width = source_img_obj.naturalWidth;
            cvs.height = source_img_obj.naturalHeight;
            var ctx = cvs.getContext("2d").drawImage(source_img_obj, 0, 0);
            var newImageData = cvs.toDataURL(mime_type, quality/100);
            var result_image_obj = new Image();
            result_image_obj.src = newImageData;
            return result_image_obj;
        },
        handleFileSelect:function(evt,id){
            var files = evt.target.files;
            for (var i = 0, f; f = files[i]; i++) {

                // Only process image files.
                if (!f.type.match('image.*')) {
                    continue;
                }

                var reader = new FileReader();

                // Closure to capture the file information.
                reader.onload = (function(theFile) {
                    return function(e) {
                        // Render thumbnail.
                        // console.log(evt.target.files[0]);
                        // console.log(e.target);
                        //console.log(e.target.result);
                        var i = document.getElementById(id);
                        i.src = event.target.result;
                        //console.log($(i).width());
                        //console.log($(i).height());
                        $(i).css('width',$(i).width()/10+'px');
                        //$(i).css('height',$(i).height()/10+'px');
                       // console.log($(i).width());
                        //console.log($(i).height());
                        var quality =  50;
                        i.src = idCertifiedCtrl.compress(i,quality).src;
                        //console.log(i.src);
                        i.style.display = "block";
                    };
                })(f);

                // Read in the image file as a data URL.
                reader.readAsDataURL(f);
            }

        }
    };
    return idCertifiedCtrl;
});