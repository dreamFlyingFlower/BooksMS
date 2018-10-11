package com.wy.plugins;

import java.text.MessageFormat;
import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

import com.wy.utils.StrUtils;

/**
 * 重写mapper.xml文件的名字
 * 
 * @author paradiseWy 2018年9月4日
 */
public class RenameDaoXmlPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		// 获得实体类名称
		FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		// 获得dao统一后缀
		String suffix = introspectedTable.getTableConfiguration().getMapperName();
		// 生成新的mapper.xml文件名称
		introspectedTable.setMyBatis3XmlMapperFileName(MessageFormat.format("{0}{1}.{2}", entityType.getShortName(),
				StrUtils.isNotBlank(suffix) ? suffix : "Dao", "xml"));
	}
}