/**
 * 身份证验证
 * @param {Object} card
 * @return {TypeName}
 */
var vcity = {
    11: "北京", 12: "天津", 13: "河北", 14: "山西",
    15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江",
    31: "上海", 32: "江苏", 33: "浙江", 34: "安徽",
    35: "福建", 36: "江西", 37: "山东", 41: "河南",
    42: "湖北", 43: "湖南", 44: "广东", 45: "广西",
    46: "海南", 50: "重庆", 51: "四川", 52: "贵州",
    53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃",
    63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾",
    81: "香港", 82: "澳门", 91: "国外"
};
function isCardValidate(card) {
    return isCardNo(card) && checkProvince(card) && checkBirthday(card) && checkParity(card);
}
/**
 * 检查号码是否符合规范，包括长度，类型
 * @param card
 * @returns {boolean}
 */
function isCardNo(card) {
    // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
    var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
    if (reg.test(card) === false) {
        // alert('您的身份证校验位不正确,请重新输入');
        return false;
    }
    return true;
}
/**
 * 取身份证前两位,校验省份
 * @param card
 * @returns {boolean}
 */
function checkProvince(card) {
    var province = card.substr(0, 2);
    if (vcity[province] == undefined) {
        return false;
    }
    return true;
}
/**
 * 检查生日是否正确
 * @param card
 * @returns {*}
 */
function checkBirthday(card) {
    var len = card.length;
    // 身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
    if (len == '15') {
        var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;
        var arr_data = card.match(re_fifteen);
        var year = arr_data[2];
        var month = arr_data[3];
        var day = arr_data[4];
        var birthday = new Date('19' + year + '/' + month + '/' + day);
        return verifyBirthday('19' + year, month, day, birthday);
    }
    // 身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
    if (len == '18') {
        var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
        var arr_data = card.match(re_eighteen);
        var year = arr_data[2];
        var month = arr_data[3];
        var day = arr_data[4];
        var birthday = new Date(year + '/' + month + '/' + day);
        return verifyBirthday(year, month, day, birthday);
    }
    return false;
}
/**
 * 校验日期
 * @param year
 * @param month
 * @param day
 * @param birthday
 * @returns {boolean}
 */
function verifyBirthday(year, month, day, birthday) {
    var now = new Date();
    var now_year = now.getFullYear();
    // 年月日是否合理
    if (birthday.getFullYear() == year && (birthday.getMonth() + 1) == month
        && birthday.getDate() == day) {
        // 判断年份的范围（3岁到100岁之间)
        var time = now_year - year;
        if (time >= 3 && time <= 100) {
            return true;
        }
        return false;
    }
    return false;
}
/**
 * 校验位的检测
 * @param card
 * @returns {boolean}
 */
function checkParity(card) {
    // 15位转18位
    card = changeFivteenToEighteen(card);
    var len = card.length;
    if (len == '18') {
        var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8,
            4, 2);
        var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3',
            '2');
        var cardTemp = 0, i, valnum;
        for (i = 0; i < 17; i++) {
            cardTemp += card.substr(i, 1) * arrInt[i];
        }
        valnum = arrCh[cardTemp % 11];
        if (valnum == card.substr(17, 1)) {
            return true;
        }
        return false;
    }
    return false;
}
/**
 * 15位转18位身份证号
 * @param card
 * @returns {*}
 */
function changeFivteenToEighteen(card) {
    if (card.length == '15') {
        var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8,
            4, 2);
        var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3',
            '2');
        var cardTemp = 0, i;
        card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
        for (i = 0; i < 17; i++) {
            cardTemp += card.substr(i, 1) * arrInt[i];
        }
        card += arrCh[cardTemp % 11];
        return card;
    }
    return card;
}

/**
 * 根据银行简称获取银行全称
 * @param bankCode
 * @param payBankDic
 * @returns {string}
 */
function bankpvalue(bankCode, payBankDic) {
    var pvalue = '';
    for (var i = 0; i < payBankDic.length; i++) {
        var p = payBankDic[i];
        if (p.pkey == bankCode) {
            pvalue = p.pvalue;
            return pvalue
        }
    }
    return pvalue;
}
/**
 * 校验支付密码
 * @param arg
 * @returns {string}
 */
function validatePassword(arg) {
    var patter = /^([a-zA-Z0-9])*$/;
    var patter1 = /^([a-zA-Z])*$/;
    var patter2 = /^([0-9])*$/;
    if (arg.length < 6)
        return '密码太短，不足6位';
    if (arg.length > 16)
        return '密码长度不得超过16位';
    if (!patter.test(arg) || (patter1.test(arg) || patter2.test(arg)))
        return '密码需由字母和数字组成';

    return 'true';
}
function validateMobile(arg) {
    var patter = /^0?(13|15|14|17|18)[0-9]{9}$/;
    if (patter.test(arg)) {
        return 'true';
    } else {
        return '请输入正确的手机号码';
    }
}