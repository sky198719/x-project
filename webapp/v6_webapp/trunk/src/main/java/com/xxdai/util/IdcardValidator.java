package com.xxdai.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class IdcardValidator {
	
	/**
	 * 身份证验证
	 * @param {Object} card
	 * @return {TypeName} 
	 */
	/*var vcity = {
		11 : "北京",12 : "天津",13 : "河北",14 : "山西",
		15 : "内蒙古",21 : "辽宁",22 : "吉林",23 : "黑龙江",
		31 : "上海",32 : "江苏",33 : "浙江",34 : "安徽",
		35 : "福建",36 : "江西",37 : "山东",41 : "河南",
		42 : "湖北",43 : "湖南",44 : "广东",45 : "广西",
		46 : "海南",50 : "重庆",51 : "四川",52 : "贵州",
		53 : "云南",54 : "西藏",61 : "陕西",62 : "甘肃",
		63 : "青海",64 : "宁夏",65 : "新疆",71 : "台湾",
		81 : "香港",82 : "澳门",91 : "国外"
	};*/
	@SuppressWarnings("serial")
	private static Map<String, String> vcity = new HashMap<String, String>() {
		{
			put("11", "北京");
			put("12", "天津");
			put("13", "河北");
			put("14", "山西");
			put("15", "内蒙古");
			put("21", "辽宁");
			put("22", "吉林");
			put("23", "黑龙江");
			put("31", "上海");
			put("32", "江苏");
			put("33", "浙江");
			put("34", "安徽");
			put("35", "福建");
			put("36", "江西");
			put("37", "山东");
			put("41", "河南");
			put("42", "湖北");
			put("43", "湖南");
			put("44", "广东");
			put("45", "广西");
			put("46", "海南");
			put("50", "重庆");
			put("51", "四川");
			put("52", "贵州");
			put("53", "云南");
			put("54", "西藏");
			put("61", "陕西");
			put("62", "甘肃");
			put("63", "青海");
			put("64", "宁夏");
			put("65", "新疆");
			put("71", "台湾");
			put("81", "香港");
			put("82", "澳门");
			put("91", "国外");
		}
	};
	
	/*Map<String, String> city = new HashMap<String, String>();
	isCardValidate = function(card) {
		return isCardNo(card) && checkProvince(card) && checkBirthday(card)
				&& checkParity(card);
	}*/
	Map<String, String> city = new HashMap<String, String>();
	public static boolean isCardValidate(String card) {
		return IdcardValidator.isCardNo(card) && IdcardValidator.checkProvince(card) && IdcardValidator.checkBirthday(card)
				&& IdcardValidator.checkParity(card);
	}
	
	// 检查号码是否符合规范，包括长度，类型
	/*isCardNo = function(card) {
		// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
		var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
		if (reg.test(card) === false) {
			// alert('您的身份证校验位不正确,请重新输入');
			return false;
		}
		return true;
	};*/
	private static boolean isCardNo(String card) {
		// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
//		var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
		if (!Pattern.compile("(^\\d{15}$)|(^\\d{17}(\\d|X)$)").matcher(card).find()) {
			// alert('您的身份证校验位不正确,请重新输入');
			return false;
		}
		return true;
	}
	
	// 取身份证前两位,校验省份
	/*checkProvince = function(card) {
		var province = card.substr(0, 2);
		if (vcity[province] == undefined) {
			return false;
		}
		return true;
	};*/
	private static boolean checkProvince(String card) {
		String province = card.substring(0, 2);
		if (vcity.get(province) == null) {
			return false;
		}
		return true;
	}
	
	// 检查生日是否正确
	/*
	checkBirthday = function(card) {
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
	};*/
	private static boolean checkBirthday(String card) {
		int len = card.length();
		// 身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
		if (len == 15) {
//			var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;
//			String[] arr_data = Pattern.compile("^(\\d{6})(\\d{2})(\\d{2})(\\d{2})(\\d{3})$").split(card);
			int year = Integer.parseInt(card.substring(6, 8));
			int month = Integer.parseInt(card.substring(8, 10));
			int day = Integer.parseInt(card.substring(10, 12));
			Calendar birthday = Calendar.getInstance();
			birthday.set(1900 + year, month - 1, day);
			return verifyBirthday(1900 + year, month, day, birthday);
		}
		// 身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
		if (len == 18) {
//			var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
//			String[] arr_data = Pattern.compile("^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$").split(card);
			int year = Integer.parseInt(card.substring(6, 10));
			int month = Integer.parseInt(card.substring(10, 12));
			int day = Integer.parseInt(card.substring(12, 14));
			Calendar birthday = Calendar.getInstance();
			birthday.set(year, month - 1, day);
			return verifyBirthday(year, month, day, birthday);
		}
		return false;
	}
	
	// 校验日期
	/*verifyBirthday = function(year, month, day, birthday) {
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
	};*/
	private static boolean verifyBirthday(int year, int month, int day, Calendar birthday) {
		Calendar now = Calendar.getInstance();
//		Date now = new Date();
		int now_year = now.get(Calendar.YEAR);
		// 年月日是否合理
		if (birthday.get(Calendar.YEAR) == year && (birthday.get(Calendar.MONTH) + 1) == month
				&& birthday.get(Calendar.DAY_OF_MONTH) == day) {
			// 判断年份的范围（3岁到100岁之间)
			int time = now_year - year;
			if (time >= 3 && time <= 100) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	// 校验位的检测
	/*checkParity = function(card) {
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
	};*/
	private static boolean checkParity(String card) {
		// 15位转18位
		card = IdcardValidator.changeFivteenToEighteen(card);
		int len = card.length();
		if (len == 18) {
			int[] arrInt = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
			String[] arrCh = new String[] { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
			int cardTemp = 0;
			String valnum;
			for (int i = 0; i < 17; i++) {
				cardTemp += Integer.parseInt(card.substring(i, i + 1)) * arrInt[i];
			}
			valnum = arrCh[cardTemp % 11];
			if (valnum.equalsIgnoreCase(card.substring(17, 18))) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	// 15位转18位身份证号
	/*changeFivteenToEighteen = function(card) {
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
	}*/
	private static String changeFivteenToEighteen(String card) {
		if (card.length() == 15) {
			int[] arrInt = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
			String[] arrCh = new String[] { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
			int cardTemp = 0;
			card = card.substring(0, 6) + "19" + card.substring(6, card.length());
			for (int i = 0; i < 17; i++) {
				cardTemp += Integer.parseInt(card.substring(i, i + 1)) * arrInt[i];
			}
			card += arrCh[cardTemp % 11];
			return card;
		}
		return card;
	}
	
	public static void main(String[] args) {
//		String IDCardNum = "411329198906100730";
//		String IDCardNum = "210102198208264114";
		String IDCardNum = "412341234123412341";
		System.out.println(IdcardValidator.isCardValidate(IDCardNum));
	}
	
}
