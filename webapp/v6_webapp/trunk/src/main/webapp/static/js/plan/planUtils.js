define(function () {
    var planUtils = {
        /**
         * 计划类型
         * @param type
         * @returns {string}
         */
        schemeType: function (type) {
            if (type == 1) {
//                return "_季季盈";
                return "_3个月";
            } else if (type == 2) {
//                return "_季季盈元宝双季盈";
                return "_6个月";
            } else if (type == 3) {
//                return "_年年盈";
                return "_12个月";
            } else if (type == 4) {
//                return "_年年盈";
                return "_1个月";
            } else if(type == 7) {
                return "_36个月";
            } else if(type ==5) {
                return "_18个月";
            } else if(type == 6) {
                return "_24个月";
            }
        },
        /**
         * 状态展现
         * @param status
         * @returns {string}
         */
        schemeToShowStatus: function (status,isColor) {
            isColor = isColor == undefined || isColor == null || isColor ? true : false;
            var result = '';
            switch (status){
                /*case 0:
                    result = isColor ? "<span class='ic-orange'>待发布</span>" : "待发布";--
                    break;
                case 1:
                    result = isColor ? "<span class='ic-perple'>预定期</span>" : "预定期";--
                    break;
                case 2:
                    result = isColor ? "<span class='ic-blue' >支付期</span>" : "支付期";--
                    break;
                case 3:
                    result = isColor ? "<span class='ic-green' >开放期</span>" : "开放期";
                    break;
                case 4:
                    result = isColor ? "<span class='ic-orange' >锁定期</span>" : "锁定期";
                    break;
                case 5:
                    result = isColor ? "<span class='ic-blue'>已退出</span>" : "已退出";
                    break;
                case 6:
                    result = isColor ? "<span class='ic-grey'>撤销</span>" : "撤销";--
                    break;
                case 7:
                    result = isColor ? "<span class='ic-orange'>等待进入开放期</span>" : "等待进入开放期";--
                    break;
                case 8:
                    result = isColor ? "<span class='ic-blue'>已退出</span>" : "已退出";
                    break;
                case 9:
                    result = isColor ? "<span class='ic-red'>退出中</span>" : "退出中";
                    break;
                default :
                    result = isColor ? "<span class='ic-grey'>未知</span>" : "未知";*/
            }
            return result;
        }
    };
    return planUtils;
});
