package com.wy.plugins;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import com.wy.utils.StrUtils;

/**
 * 生成dao层方法
 * 
 * @author paradiseWy
 */
public class DaoPlugin extends PluginAdapter {

	// 基础dao类
	private String baseDao;
	// dao层工程路径
	private String targetProjectMain;
	// dao层包名
	private String targetPackageDao;

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);

		String baseDao = this.properties.getProperty("baseDao");
		if (StringUtility.stringHasValue(baseDao)) {
			this.baseDao = baseDao;
		}

		String targetProjectMain = this.properties.getProperty("targetProjectMain");
		if (StringUtility.stringHasValue(targetProjectMain)) {
			this.targetProjectMain = targetProjectMain;
		} else {
			throw new RuntimeException("dao 缺少必要的工程路径");
		}

		String targetPackageDao = this.properties.getProperty("targetPackageDao");
		if (StringUtility.stringHasValue(targetPackageDao)) {
			this.targetPackageDao = targetPackageDao;
		} else {
			throw new RuntimeException("dao 缺少必要的包路径");
		}
	}

	/**
	 * 不使用mybatis生成mapper接口,要生成dao类,返回true的时候表示生成
	 */
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		return false;
	}

	/**
	 * 生成额外的dao类,而不是接口
	 */
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		// 生成的dao类后缀名
		String clazzName = introspectedTable.getTableConfiguration().getMapperName();
		// 获取实体类
		FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		// 生成实体类dao类
		TopLevelClass daoClass = new TopLevelClass(
				new FullyQualifiedJavaType(MessageFormat.format("{0}.{1}{2}", targetPackageDao,
						entityType.getShortName(), StrUtils.isNotBlank(clazzName) ? clazzName : "Dao")));
		daoClass.setVisibility(JavaVisibility.PUBLIC);
		// import 实体类
		daoClass.addImportedType(entityType);
		FullyQualifiedJavaType baseDaoClass = null;
		// 基础dao类
		if (StrUtils.isNotBlank(baseDao)) {
			baseDaoClass = new FullyQualifiedJavaType(baseDao);
			baseDaoClass.addTypeArgument(entityType);
			daoClass.setSuperClass(baseDaoClass);
		}
		// 添加 @Repository 注解
		daoClass.addAnnotation("@Repository");
		// import 接口
		daoClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
		List<GeneratedJavaFile> result = new ArrayList<>();
		GeneratedJavaFile daoFile = new GeneratedJavaFile(daoClass, targetProjectMain,
				new DefaultJavaFormatter());
		result.add(daoFile);
		return result;
	}
}