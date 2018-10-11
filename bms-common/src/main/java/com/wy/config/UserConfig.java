package com.wy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 自定义配置,不需要进行国际化的值才用该文件;注意只需要set方法,且不能是static的方法
 * 
 * @Value只能在spring组件内使用,所以要使用static再赋值一次,或者不用sping,直接读取配置文件也可
 * 
 * @author paradiseWy
 */
@Configuration
@PropertySource("classpath:user_config.properties")
public class UserConfig {
	public static boolean isTodayFolder = false;//非配置文件属性,判断是否已经新建了当天的文件存放文件夹
	
	public static String superAdmin;// 超级管理员账号
	public static String accountType;// 登录可用帐号类型,与数据库字段对应,逗号隔开
	public static String defaultUserIcon;// 默认菜单图标
	public static String defaultMenuIcon;// 默认用户图标
	public static String fileLocal;// 上传文件本地存储地址
	public static String fileHttp;//服务器访问本地文件地址;不在数据库中存储http地址,防止http变动文件访问不了

	@Value("${superAdmin}")
	public void setSuperAdmin(String superAdmin) {
		UserConfig.superAdmin = superAdmin;
	}

	@Value("${accountType}")
	public void setAccountType(String accountType) {
		UserConfig.accountType = accountType;
	}

	@Value("${defaultMenuIcon}")
	public void setDefaultMenuIcon(String defaultMenuIcon) {
		UserConfig.defaultMenuIcon = defaultMenuIcon;
	}

	@Value("${defaultUserIcon}")
	public void setDefaultUserIcon(String defaultUserIcon) {
		UserConfig.defaultUserIcon = defaultUserIcon;
	}

	@Value("${fileLocal}")
	public void setFileLocal(String fileLocal) {
		UserConfig.fileLocal = fileLocal;
	}
	
	@Value("${fileHttp}")
	public void setFileHttp(String fileHttp) {
		UserConfig.fileHttp = fileHttp;
	}
}