package com.wy.enums;

/**
 * 常用字符串校验枚举
 * @author wanyang
 */
public enum RegexEnum {
	/**
	 * 英文和数字
	 */
	REGEX_ENGNUM("^[A-Za-z0-9]+$"),
	/**
	 * 	检查是否中文,不包括标点符号等
	 */
	REGEX_CHINA("^[\u4E00-\u9FA5]+$"),
	/**
	 * 中英文和数字,下划线
	 */
	REGEX_CHENNUM("^[\\u4E00-\\u9FA5A-Za-z0-9_]+$"),
	/**
	 * 检查座机号,手机号
	 */
	REGEX_PHONE("^(15[0-9])|(14[0-9])|(17[0-9])|(18[0-9])\\d{8}$"),
	/**
	 * 检查是否邮箱
	 */
	REGEX_EMAIL("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2}"),
	/**
	 * 身份证,18位和15位
	 */
	REGEX_IDCARD("^[1-9]\\d{5}[12]\\d{3}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)"
			+ "|(^[1-9]\\d{5}\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{2}$"),
	/**
	 * QQ号
	 */
	REGEX_QQ("[1-9][0-9]{4,14}"),
	/**
	 * 邮编
	 */
	REGEX_ZIPCODE("\\d{6}"),
	/**
	 * 是否为货币
	 */
	REGEX_MONEY("^(\\d+(?:\\.\\d+)?)$"),
	/**
	 * 数字
	 */
	REGEX_NUMBER("\\d+"),
	/**
     * 判断字符串是否为统一社会信用代码（18位）<br>
     * 统一代码由十八位的阿拉伯数字或大写英文字母（不使用I、O、Z、S、V）组成。<br>
     * 第1位：登记管理部门代码（共一位字符）[1、5、9、Y]<br>
     * 第2位：机构类别代码（共一位字符）[与第一位合并成，11、12、13、19、51、52、53、59、91、92、93、Y1]组成。<br>
     * 第3位~第8位：登记管理机关行政区划码（共六位阿拉伯数字）[100000~999999]<br>
     * 第9位~第17位：主体标识码（组织机构代码）（共九位字符）<br>
     * 第18位：校验码​（共一位字符）<br>
     */
	REGEX_USCC("^(11|12|13|19|51|52|53|59|91|92|93|Y1)[1-9]{1}[0-9]{5}[0-9A-HJ-NP-RT-UW-Y0-9]{9}[0-90-9A-HJ-NP-RT-UW-Y]{1}$"),
	/**
	 * 银联帐号
	 */
	REGEX_UNIONPAYCARD("^62[0-5]\\d{13,16}$");
	
	private String regex;
	
	RegexEnum(String regex) {
		this.regex = regex;
	}
	
	@Override
	public String toString() {
		return regex;
	}
}