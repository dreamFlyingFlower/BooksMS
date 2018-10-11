package com.wy.plugins;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import com.wy.utils.StrUtils;

/**
 * 自定义生成service和impl,此处不生成impl,{@link com.github.trang.mybatis.generator.plugins.ServicePlugin}
 * 
 * @author paradiseWy 2018年8月30日
 */
public class ServicePlugin extends PluginAdapter {
	private String baseService;
	private String targetProjectMain;
	private String targetPackageService;
	private String targetPackageDao;

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		String baseService = this.properties.getProperty("baseService");
		if (StringUtility.stringHasValue(baseService)) {
			this.baseService = baseService;
		} else {
			throw new RuntimeException("baseService 属性不能为空！");
		}
		String targetProjectMain = this.properties.getProperty("targetProjectMain");
		if (StringUtility.stringHasValue(targetProjectMain)) {
			this.targetProjectMain = targetProjectMain;
		} else {
			throw new RuntimeException("targetProjectMain 属性不能为空！");
		}
		String targetPackageService = this.properties.getProperty("targetPackageService");
		if (StringUtility.stringHasValue(targetPackageService)) {
			this.targetPackageService = targetPackageService;
		} else {
			throw new RuntimeException("targetPackageService 属性不能为空！");
		}
		String targetPackageDao = this.properties.getProperty("targetPackageDao");
		if (StringUtility.stringHasValue(targetPackageDao)) {
			this.targetPackageDao = targetPackageDao;
		} else {
			throw new RuntimeException("targetPackageDao 属性不能为空");
		}
	}

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable table) {
		return Arrays.asList(generateService(table));
	}

	private GeneratedJavaFile generateService(IntrospectedTable table) {
		// 获取实体类型
		FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
		// 生成 Service 名称
		String service = targetPackageService + "." + table.getFullyQualifiedTable().getDomainObjectName()
				+ "Service";
		// 构造 Service 文件
		TopLevelClass clazz = new TopLevelClass(new FullyQualifiedJavaType(service));
		// 设置类名
		String domainObjectName = table.getFullyQualifiedTable().getDomainObjectName();
		// import
		clazz.addImportedType(entityType);
		clazz.addImportedType(
				new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
		clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
		clazz.addAnnotation("@Service(\"" + StrUtils.lowerFirst(domainObjectName + "Service") + "\")");
		clazz.setVisibility(JavaVisibility.PUBLIC);
		clazz.setSuperClass(new FullyQualifiedJavaType(baseService + "<" + entityType.getShortName() + ">"));
		/**
		 * 添加对应的dao以及实现方法
		 */
		// 添加dao类
		Field field = new Field();
		field.addAnnotation("@Autowired");
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(new FullyQualifiedJavaType(entityType.getShortName() + "Dao"));
		clazz.addImportedType(
				new FullyQualifiedJavaType(this.targetPackageDao + "." + entityType.getShortName() + "Dao"));
		field.setName(StrUtils.lowerFirst(entityType.getShortName() + "Dao"));
		clazz.addField(field);

		// 实现抽象类的方法
		java.lang.reflect.Method[] ms;
		try {
			ms = Class.forName(baseService).getMethods();
			for (java.lang.reflect.Method m : ms) {
				// 判断抽象方法
				Method method = new Method();
				if (Modifier.isAbstract(m.getModifiers())) {
					method.addAnnotation("@Override");
					method.setVisibility(JavaVisibility.PUBLIC);
					method.setReturnType(new FullyQualifiedJavaType(
							this.targetPackageDao + "." + entityType.getShortName() + "Dao"));
					method.setName(m.getName());
					method.addBodyLine("return " + StrUtils.lowerFirst(entityType.getShortName() + "Dao;"));
					clazz.addMethod(method);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new GeneratedJavaFile(clazz, targetProjectMain, new DefaultJavaFormatter());
	}
}