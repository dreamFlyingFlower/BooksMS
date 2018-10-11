package com.wy.plugins;

import org.mybatis.generator.api.ShellRunner;

/**
 * 利用mybatis根据数据库生成实体类
 * 
 * @author paradiseWy 2018年8月29日
 */
public class GenetorPlugin {

	public static void main(String[] args) {
		args = new String[] { "-configfile", "src\\main\\resources\\generator\\generator.xml", "-overwrite" };
        ShellRunner.main(args);
	}
}