package com.wy.plugins;

import java.util.List;

import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;

/**
 * 自定义table实现类
 * @author paradiseWy 2018年9月4日
 */
public class IntroTablePlugin extends IntrospectedTableMyBatis3Impl {
	protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator, List<String> warnings,
			ProgressCallback progressCallback) {
		xmlMapperGenerator = new XmlDaoGenerator();

		initializeAbstractGenerator(xmlMapperGenerator, warnings, progressCallback);
	}

	protected AbstractJavaClientGenerator createJavaClientGenerator() {
		if (context.getJavaClientGeneratorConfiguration() == null) {
			return null;
		}
		AbstractJavaClientGenerator javaGenerator;
		javaGenerator = new JavaDaoGenerator();
		return javaGenerator;
	}
}