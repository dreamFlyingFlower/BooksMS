package com.wy.utils;

import java.util.ArrayList;
import java.util.List;

import com.wy.enums.RegexEnum;

/**
 * 字符串utils
 * @author paradiseWy 2018年8月28日
 */
public class StrUtils {

	private StrUtils() {
	}
	
	/**
	 * 字符串是否为空,空字符串也判断为空
	 */
	public static boolean isBlank(CharSequence str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		int length = str.length();
		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(CharSequence str) {
		return !isBlank(str);
	}

	public static boolean isBlank(CharSequence... args) {
		for (CharSequence arg : args) {
			if (isNotBlank(arg)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(CharSequence... args) {
		for (CharSequence arg : args) {
			if (isBlank(arg)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断数组是否为空,不判断数组中的数据是否为空
	 */
	public static boolean isBlanks(String[] array) {
		return array == null || array.length == 0;
	}

	public static boolean isNotBlanks(String[] array) {
		return !isBlank(array);
	}

	/**
	 * 将目标字符串的首字母变成大写
	 */
	public static String upperFirst(String src) {
		if (isNotBlank(src) && src.length() >0) {
			char c = src.charAt(0);
			if (c >= 'a' && c <= 'z') {
				String temp = String.valueOf(c);
				return src.replaceFirst(temp, temp.toUpperCase());
			}
		}
		return src;
	}

	/**
	 * 将目标字符串的首字母变成小写
	 */
	public static String lowerFirst(String src) {
		if (isNotBlank(src) && src.length() >0) {
			char c = src.charAt(0);
			if (c >= 'A' && c <= 'Z') {
				String temp = String.valueOf(c);
				return src.replaceFirst(temp, temp.toLowerCase());
			}
		}
		return src;
	}

	/**
	 * 合法性检查
	 * 
	 * @param des 目标字符串
	 * @param type 检查类型,手机号,qq号等
	 */
	public static boolean checkValidity(String des, RegexEnum type) {
		if (type == null) {
			return false;
		}
		return checkValidity(des, type.toString());
	}

	/**
	 * 合法性检查
	 * 
	 * @param des 目标字符串
	 * @param pattern 检查类型
	 */
	public static final boolean checkValidity(CharSequence des, String pattern) {
		if (isBlank(des) || isBlank(pattern)) {
			return false;
		}
		return des.toString().matches(pattern);
	}

	/**
	 * 判断一个字符串是否全是中文,包括中文标点符号等
	 */
	public static boolean isChinese(String str) {
		char[] array = str.toCharArray();
		for (char c : array) {
			if (!isChinese(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否为中文字符,包括标点等 CJK的意思是“Chinese，Japanese，Korea”的简写
	 * ，实际上就是指中日韩三国的象形文字的Unicode编码 CJK_UNIFIED_IDEOGRAPHS:4E00-9FBF:CJK 统一表意符号
	 * CJK_COMPATIBILITY_IDEOGRAPHS:F900-FAFF:CJK 兼容象形文字
	 * CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A:3400-4DBF:CJK 统一表意符号扩展A
	 * CJK_SYMBOLS_AND_PUNCTUATION:3000-303F:CJK 符号和标点
	 * HALFWIDTH_AND_FULLWIDTH_FORMS:FF00-FFEF:半角及全角形式
	 * GENERAL_PUNCTUATION:2000-206F:常用标点
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	/**
	 * 去掉字符串中所有的空格, 换行,制表符
	 */
	public static String removeSpace(CharSequence str) {
		if (isBlank(str)) {
            return null;
        }
        final int sz = str.length();
        final char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }
        if (count == sz) {
            return str.toString();
        }
        return new String(chs, 0, count);
	}

	/**
	 * 判断一个字符串中是否含有另外一个字符串,不区分大小写
	 * 
	 * @param src 原字符串
	 * @param des 需要检索的字符串
	 */
	public static int indexOf(CharSequence src, CharSequence des) {
		try {
			if (isBlank(src) || isBlank(des)) {
				throw new Exception("参数不能为空");
			}
			return (src.toString().toLowerCase()).indexOf(des.toString().toLowerCase());
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 判断一个字符串中是否含有另外一个字符串,不区分大小写
	 */
	public static boolean contains(CharSequence src, CharSequence des) {
		return indexOf(src, des) > -1;
	}

	/**
	 * 将带下划线的字段名(蛇底式)变成驼峰式
	 */
	public static String snake2Hump(String column) {
		if (column.indexOf("_") != -1) {
			String[] strs = column.split("_");
			StringBuilder sb = new StringBuilder(strs[0]);
			for (int i = 1; i < strs.length; i++) {
				sb.append(upperFirst(strs[i]));
			}
			return sb.toString();
		}
		return column;
	}
	
	/**
	 * 将蛇底式字段转换成驼峰式
	 */
	public static String hump2Snake(String str) {
		int leng = str.length();
		int i= 0;
		int index = 0;
		List<String> res =new ArrayList<>();
		while(i<leng) {
			if(Character.isUpperCase(str.charAt(i))) {
				if(i ==0) {
					continue;
				}
				res.add(str.substring(index, i).toLowerCase());
				index = i;
			}
			i++;
		}
		res.add(str.substring(index).toLowerCase());
		return String.join("_", res);
	}
	
	/**
	 * 判断一个字符串是否能转成数字
	 */
	public static boolean isNumeric(CharSequence cs) {
        if (isBlank(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
}