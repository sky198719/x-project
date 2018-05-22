define(function () {
    var bindingCtrl = {
        init: function () {

            var bindings = [
				{
				    element: '#popularizeCode',
				    event: 'blur',
				    handler: bindingCtrl.blurPopularizeCode
				},
                {
                    element: '#operBinding',
                    event: 'click',
                    handler: bindingCtrl.Binding
                }
            ];
            appFunc.bindEvents(bindings);
        },
        blurPopularizeCode:function(){
        	//try {XXD_TRACK._trackEvent({category: "webapp_code_binding", action: "code_binding", label: "推广码", value: $$("#popularizeCode").val(), custval: "" });} catch (e) {}
        },
        Binding: function () {
        	//try {XXD_TRACK._trackEvent({category: "webapp_code_binding", action: "binding_button", label: "绑定", value: $$("#popularizeCode").val(), custval: "" });} catch (e) {}
            var popularizeCode = $$("#popularizeCode").val();
            if (popularizeCode == null || popularizeCode == '') {
                xxdApp.alert("请输入推广码", '提示');
                return false;
            }

            req.callPost({
                url: 'popularize/binding.do',
                data:{
                    popularizeCode:popularizeCode
                },
                dataType:'json',
                preloaderTitle:'正在处理，请稍后...',
                timeout:8000,
                success:function(result){
                    if (result.msg == 2) {
                    	//try {XXD_TRACK._trackEvent({category: "webapp_code_binding", action: "binding_success", label: "绑定成功", value: $$("#popularizeCode").val(), custval: "1" });} catch (e) {}
                        xxdApp.alert("操作成功",'提示');
                        $$("#popularizeCode").val('');
                    } else {
                    	//try {XXD_TRACK._trackEvent({category: "webapp_code_binding", action: "binding_success", label: "绑定失败", value: $$("#popularizeCode").val(), custval: "0" });} catch (e) {}
                       xxdApp.alert(result.msg,'提示');
                    }
                }
            });
        }
    };
    return bindingCtrl;
});