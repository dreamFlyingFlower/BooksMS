package com.wy.common;

/**
 * 公共配置类
 * 
 * @author wanyang 2018年7月24日
 *
 */
public class Constants {

	/**
	 * 日志格式化信息
	 */
	public static final String LOG_INFO = "!!!==== {}";
	public static final String LOG_WARN = "@@@===={}";
	public static final String LOG_ERROR ="###===={}";
	
	// shiro中需要的存入session属性
	public static final String SESSION_USER_INFO = "session_user_info";
	public static final String SESSION_USER_PERMISSION = "session_user_permission";
}