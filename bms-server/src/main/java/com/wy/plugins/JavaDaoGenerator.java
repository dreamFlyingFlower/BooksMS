package com.wy.plugins;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.SelectAllMethodGenerator;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;

/**
 * 自定义java生成类
 * @author paradiseWy 2018年9月4日
 */
public class JavaDaoGenerator extends JavaMapperGenerator {
	@Override
	public List<CompilationUnit> getCompilationUnits() {
		progressCallback.startTask(Messages.getString("Progress.17", //$NON-NLS-1$
				introspectedTable.getFullyQualifiedTable().toString()));
		CommentGenerator commentGenerator = context.getCommentGenerator();

		FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
		Interface interfaze = new Interface(type);
		interfaze.setVisibility(JavaVisibility.PUBLIC);
		commentGenerator.addJavaFileComment(interfaze);

		String rootInterface = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
		if (!StringUtility.stringHasValue(rootInterface)) {
			rootInterface =
					context.getJavaClientGeneratorConfiguration().getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
		}

		if (StringUtility.stringHasValue(rootInterface)) {
			FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
			interfaze.addSuperInterface(fqjt);
			interfaze.addImportedType(fqjt);
		}

		addCountByExampleMethod(interfaze);
		addDeleteByExampleMethod(interfaze);
		addDeleteByPrimaryKeyMethod(interfaze);
		addInsertMethod(interfaze);
		addInsertSelectiveMethod(interfaze);
		addSelectByExampleWithBLOBsMethod(interfaze);
		addSelectByExampleWithoutBLOBsMethod(interfaze);
		addSelectByPrimaryKeyMethod(interfaze);
		addUpdateByExampleSelectiveMethod(interfaze);
		addUpdateByExampleWithBLOBsMethod(interfaze);
		addUpdateByExampleWithoutBLOBsMethod(interfaze);
		addUpdateByPrimaryKeySelectiveMethod(interfaze);
		addUpdateByPrimaryKeyWithBLOBsMethod(interfaze);
		addUpdateByPrimaryKeyWithoutBLOBsMethod(interfaze);
		// 增加selectAll
		addSelectAllMethod(interfaze);

		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
		if (context.getPlugins().clientGenerated(interfaze, null, introspectedTable)) {
			answer.add(interfaze);
		}

		List<CompilationUnit> extraCompilationUnits = getExtraCompilationUnits();
		if (extraCompilationUnits != null) {
			answer.addAll(extraCompilationUnits);
		}

		return answer;
	}

	/**
	 * 增加eelectAll
	 * 
	 * @param interfaze
	 */
	protected void addSelectAllMethod(Interface interfaze) {
		if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
			AbstractJavaMapperMethodGenerator methodGenerator = new SelectAllMethodGenerator();
			initializeAndExecuteGenerator(methodGenerator, interfaze);
		}
	}
}
