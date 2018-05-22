require(['jquery', 'requirejs', 'xui_user_v101', 'com', 'mcPrompt_v300'], function ($, requirejs, xui_user, com, mcMsg) {

    console.log(com.twoNum(9));
    console.log(com.twoNum(10));


    mcMsg.o({
        msg:'aaa',
        icon:'loading'
    });

    //登录实例
    xui_user.Login({
        //容器*必写（多个实例时容器不可同一个CLASS）
        frame   : '#login1',
        //用户名输入框
        user    : '#ccc',
        //密码输入框
        password: '#bbb',
        //验证码输入框
        vCode   : '#mmm',
        //验证码图片
        vCodeImg: '#mmmimg',
        //提交登录按钮
        submit  : '.ux_login_submit',
        //提示回调
        prompt  : function (result) {
            console.log(result.msg);
            // 提示识别码的应用示例
            if (result.code == 102) {
                alert('用使消息类型识别码做一个判断')
            }
        },
        //仅登录成功回调
        success : function (result) {
            console.log(result.msg);
        }
    });

    //注册实例
    xui_user.Register({
        //容器*必写（多个实例时容器不可同一个CLASS）
        frame   : '#reg1',
        //用户名输入框
        user    : '#aaa1',
        //密码输入框
        password: '#bbb1',
        //验证码输入框
        vCode   : '#ccc1',
        //验证码图片
        vCodeImg: '#ddd1',
        //手机验证码
        mCode   : '#eee1',
        //手机验证码发送按钮
        mCodeBtn: '#fff1',
        //提交注册按钮
        submit  : '#ggg1',
        prompt  : function (result) {
            console.log(result.msg);
            // 提示识别码的应用示例
            if (result.code == 200) {
                alert('用使消息类型识别码自定义提示消息：如：亲，可以给我手机号嘛！么么哒！')
            }
        },
        //仅登录成功回调
        success : function (result) {
            console.log(result.msg);
        }
    });

}, function (err) {
    console.log(err);
});