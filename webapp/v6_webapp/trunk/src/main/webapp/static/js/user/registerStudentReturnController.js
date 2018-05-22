/**
 * 注册
 */
define(['js/user/registerStudentReturnView'], function (registerStudentReturnView) {
    var registerStudentReturnCtrl = {
        init: function () {
        	  /* var bindings = [
               {
                    element: '#fenqigou',
                    event: 'click',
                    handler: registerStudentReturnCtrl.fenqigou
                }
            ];
        	registerStudentReturnCtrl.init({bindings: bindings});*/
        },

        fenqigou: function () {
            GS.reloadPage("index/home.html");
        }
    };

    return registerStudentReturnCtrl;
});