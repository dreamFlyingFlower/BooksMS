package com.wy.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author paradiseWy 2018年10月12日
 */
public class Props {

	public static boolean isTodayFolder = false;// 非配置文件属性,判断是否已经新建了当天的文件存放文件夹
	public static final String SUPER_ADMIN;// 超级管理员账号
	public static final String ACCOUNT_TYPE;// 登录可用帐号类型,与数据库字段对应,逗号隔开
	public static final String DEFAULT_USER_ICON;// 默认菜单图标
	public static final String DEFAULT_MENU_ICON;// 默认用户图标
	public static final String FILE_LOCAL;// 上传文件本地存储地址
	public static final String FILE_HTTP;// 服务器访问本地文件地址;不在数据库中存储http地址,防止http变动文件访问不了
	public static final Integer USE_GUI;// 是否启动图书管理系统的gui界面,1是,0否

	static {
		Properties prop = new Properties();
		try (InputStream is = Props.class.getClassLoader().getResourceAsStream("constant.properties")) {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SUPER_ADMIN = prop.getProperty("super_admin");
		ACCOUNT_TYPE =prop.getProperty("account_type","username");
		DEFAULT_USER_ICON =prop.getProperty("default_user_icon");
		DEFAULT_MENU_ICON=prop.getProperty("default_menu_icon");
		FILE_LOCAL=prop.getProperty("file_local");
		FILE_HTTP=prop.getProperty("file_http");
		USE_GUI=Integer.parseInt(prop.getProperty("use_gui","0"));
	}
}