package com.wy.plugins;

import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

import com.wy.utils.StrUtils;

/**
 * 自定义根据数据库表生成实体类,参照{@link com.github.trang.mybatis.generator.plugins.MapperPlugin}
 * 
 * @author paradiseWy 2018年8月30日
 */
public class EntityPlugin extends PluginAdapter {

	// 开始的分隔符,例如 mysql 为 `,sql server 为 [
	private String beginningDelimiter = "";
	// 结束的分隔符,例如 mysql 为 `,sql server 为 ]
	private String endingDelimiter = "";
	// 通用 dao 接口
	private Set<String> daos = new HashSet<>();
	// caseSensitive 默认 false,当数据库表名区分大小写时,可以将该属性设置为 true
	private boolean caseSensitive = false;
	// 强制生成注解,默认 false,设置为 true 后一定会生成 @Table 和 @Column 注解
	private boolean forceAnnotation = false;
	// 数据库模式
	private String schema;
	// Lombok 插件模式
	private LombokType lombok = LombokType.none;
	// 注释生成器
	private CommentGeneratorConfiguration configuration;
	// 实体类基类,必须实现serilization
	private String baseBean;
	// 是否重写父类方法
	private boolean rewrite;

	enum LombokType {
		none, simple, builder, accessors
	}

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public void setContext(Context context) {
		super.setContext(context);
		// 设置默认的注释生成器
		configuration = new CommentGeneratorConfiguration();
		// 设置自定义的注释生成器
		configuration.setConfigurationType(CommentPlugin.class.getCanonicalName());
		context.setCommentGeneratorConfiguration(configuration);
		// 支持 oracle 获取注释 #114
		context.getJdbcConnectionConfiguration().addProperty("remarksReporting", "true");
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);

		String beginningDelimiter = this.properties.getProperty("beginningDelimiter");
		if (StringUtility.stringHasValue(beginningDelimiter)) {
			this.beginningDelimiter = beginningDelimiter;
		}
		configuration.addProperty("beginningDelimiter", this.beginningDelimiter);

		String endingDelimiter = this.properties.getProperty("endingDelimiter");
		if (StringUtility.stringHasValue(endingDelimiter)) {
			this.endingDelimiter = endingDelimiter;
		}
		configuration.addProperty("endingDelimiter", this.endingDelimiter);

		String mappers = this.properties.getProperty("daos");
		if (StringUtility.stringHasValue(mappers)) {
			Collections.addAll(this.daos, mappers.split(","));
		} else {
			throw new RuntimeException("dao 插件缺少必要的 dao 属性!");
		}

		String schema = this.properties.getProperty("schema");
		if (StringUtility.stringHasValue(schema)) {
			this.schema = schema;
		}

		this.forceAnnotation = StringUtility.isTrue(this.properties.getProperty("forceAnnotation"));
		this.caseSensitive = StringUtility.isTrue(this.properties.getProperty("caseSensitive"));

		String lombok = this.properties.getProperty("lombok");
		if (StringUtility.stringHasValue(lombok)) {
			this.lombok = LombokType.valueOf(lombok);
		}

		String baseBean = this.properties.getProperty("baseBean");
		if (StringUtility.stringHasValue(baseBean)) {
			this.baseBean = baseBean;
		}

		String rewrite = this.properties.getProperty("rewriter");
		if (StringUtility.stringHasValue(rewrite)) {
			this.rewrite = Boolean.valueOf(rewrite);
		}
	}

	public String getDelimiterName(String name) {
		StringBuilder nameBuilder = new StringBuilder();
		if (StringUtility.stringHasValue(schema)) {
			nameBuilder.append(schema).append(".");
		}
		nameBuilder.append(beginningDelimiter);
		nameBuilder.append(name);
		nameBuilder.append(endingDelimiter);
		return nameBuilder.toString();
	}

	/**
	 * 处理实体类的包和 @Table 注解,引入lombok
	 */
	private void processEntityClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// import lombok
		addLombok(topLevelClass);
		// import nutz
		topLevelClass.addImportedType("org.nutz.dao.entity.annotation.Column");
		topLevelClass.addImportedType("org.nutz.dao.entity.annotation.Table");

		String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
		// 如果包含空格,或者需要分隔符,需要完善
		if (StringUtility.stringContainsSpace(tableName)) {
			tableName = context.getBeginningDelimiter() + tableName + context.getEndingDelimiter();
		}

		// 是否忽略大小写,对于区分大小写的数据库,会有用
		if (caseSensitive && !topLevelClass.getType().getShortName().equals(tableName)) {
			topLevelClass.addAnnotation("@Table(\"" + getDelimiterName(tableName) + "\")");
		} else if (!topLevelClass.getType().getShortName().equalsIgnoreCase(tableName)) {
			topLevelClass.addAnnotation("@Table(\"" + getDelimiterName(tableName) + "\")");
		} else if (StringUtility.stringHasValue(schema) || StringUtility.stringHasValue(beginningDelimiter)
				|| StringUtility.stringHasValue(endingDelimiter)) {
			topLevelClass.addAnnotation("@Table(\"" + getDelimiterName(tableName) + "\")");
		} else if (forceAnnotation) {
			topLevelClass.addAnnotation("@Table(\"" + getDelimiterName(tableName) + "\")");
		}

		// 添加父类
		if (StrUtils.isNotBlank(baseBean)) {
			// 基础父类
			FullyQualifiedJavaType parent = new FullyQualifiedJavaType(baseBean);
			topLevelClass.setSuperClass(parent);
			// 实体类
			FullyQualifiedJavaType entity = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
			parent.addTypeArgument(entity);
			// 是否重写父类方法
			if (rewrite) {
				rewrite(entity, topLevelClass);
			}
		}
		// 添加serialization的常量
		addSerialization(topLevelClass);
	}

	private void addLombok(TopLevelClass topLevelClass) {
		switch (lombok) {
			case none:
				break;
			case simple:
				topLevelClass.addImportedType("lombok.NoArgsConstructor");
				topLevelClass.addImportedType("lombok.Getter");
				topLevelClass.addImportedType("lombok.Setter");
				topLevelClass.addAnnotation("@Getter");
				topLevelClass.addAnnotation("@NoArgsConstructor");
				topLevelClass.addAnnotation("@Setter");
				break;
			case accessors:
				topLevelClass.addImportedType("lombok.NoArgsConstructor");
				topLevelClass.addImportedType("lombok.experimental.Accessors");
				topLevelClass.addImportedType("lombok.Getter");
				topLevelClass.addImportedType("lombok.Setter");
				topLevelClass.addAnnotation("@Accessors(fluent = true)");
				topLevelClass.addAnnotation("@Getter");
				topLevelClass.addAnnotation("@NoArgsConstructor");
				topLevelClass.addAnnotation("@Setter");
				break;
			case builder:
				topLevelClass.addImportedType("lombok.NoArgsConstructor");
				topLevelClass.addImportedType("lombok.AllArgsConstructor");
				topLevelClass.addImportedType("lombok.Builder");
				topLevelClass.addImportedType("lombok.Getter");
				topLevelClass.addImportedType("lombok.Setter");
				topLevelClass.addAnnotation("@AllArgsConstructor");
				topLevelClass.addAnnotation("@Builder");
				topLevelClass.addAnnotation("@Getter");
				topLevelClass.addAnnotation("@NoArgsConstructor");
				topLevelClass.addAnnotation("@Setter");
				break;
			default:
				break;
		}
	}

	/**
	 * 重写父类方法,若是返回值有泛型,则无法添加,需要手动进行处理
	 * 
	 * @param entity ibatis的实体类
	 * @param topLevelClass ibatis的顶级类
	 */
	private void rewrite(FullyQualifiedJavaType entity, TopLevelClass topLevelClass) {
		try {
			// 重写父类所有方法,不重写以get,set开头的方法,
			// 无法判断方法是否来自于Object,重写了Object的方法,需要删除
			Class<?> parentClazz = Class.forName(baseBean);
			java.lang.reflect.Method[] ms = parentClazz.getMethods();
			for (java.lang.reflect.Method m : ms) {
				// 不需要重写的方法
				if (notRewrite(m)) {
					continue;
				}
				Method method = new Method();
				method.addAnnotation("@Override");
				method.setVisibility(JavaVisibility.PUBLIC);
				method.setReturnType(new FullyQualifiedJavaType(m.getReturnType().getTypeName()));
				method.setName(m.getName());
				java.lang.reflect.Parameter[] parameters = m.getParameters();
				for (java.lang.reflect.Parameter param : parameters) {
					Parameter _param = null;
					// 若是泛型,则添加该泛型的实体类
					if (param.getParameterizedType() != null) {
						_param = new Parameter(entity, StrUtils.lowerFirst(entity.getShortName()));
					} else {
						// 若不是泛型,由于拿不到参数的名称,只能使用arg0,arg1代替
						_param = new Parameter(new FullyQualifiedJavaType(param.getType().getTypeName()),
								param.getName() + (int) (Math.random() * 10));
					}
					method.addParameter(_param);
				}
				addReturn(m, method);
				topLevelClass.addImportedType(new FullyQualifiedJavaType(m.getReturnType().getTypeName()));
				topLevelClass.addMethod(method);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 不需要重写的方法
	 */
	private boolean notRewrite(java.lang.reflect.Method m) {
		// 不重写get,set方法
		if (m.getName().indexOf("get") > -1 || m.getName().indexOf("set") > -1) {
			return true;
		}
		// 不重写private,static,final,native方法
		if (Modifier.isFinal(m.getModifiers()) || Modifier.isNative(m.getModifiers())
				|| Modifier.isPrivate(m.getModifiers()) || Modifier.isStatic(m.getModifiers())) {
			return true;
		}
		// 不重写Object的equals,toString方法
		if (m.getName().equals("equals") || m.getName().equals("toString")) {
			return true;
		}
		return false;
	}

	/**
	 * 添加简单的返回值
	 * 
	 * @param m jdk的反射method
	 * @param method ibatis的反射method
	 */
	private void addReturn(java.lang.reflect.Method m, Method method) {
		if (m.getReturnType() != void.class) {
			if (m.getReturnType() == Integer.class || m.getReturnType() == int.class
					|| m.getReturnType() == Short.class || m.getReturnType() == short.class) {
				method.addBodyLine("return 0;");
			} else if (m.getReturnType() == Float.class) {
				method.addBodyLine("return 0f;");
			} else if (m.getReturnType() == Double.class) {
				method.addBodyLine("return 0d;");
			} else if (m.getReturnType() == Boolean.class || m.getReturnType() == boolean.class) {
				method.addBodyLine("return false;");
			} else {
				method.addBodyLine("return null;");
			}
		}
	}

	/**
	 * 添加serialization的常量
	 * 
	 * @param topLevelClass
	 */
	private void addSerialization(TopLevelClass topLevelClass) {
		Field field = new Field();
		field.setFinal(true);
		field.setInitializationString("1L");
		field.setName(PluginConstant.SERIAL_VERSION_UID);
		field.setStatic(true);
		field.setType(new FullyQualifiedJavaType("long"));
		field.setVisibility(JavaVisibility.PRIVATE);
		topLevelClass.addField(field);
	}

	/**
	 * 生成带 BLOB 字段的对象
	 */
	@Override
	public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		processEntityClass(topLevelClass, introspectedTable);
		return true;
	}

	/**
	 * 处理实体类的字段
	 */
	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass,
			IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
			ModelClassType modelClassType) {
		// 添加注解
		if (field.isTransient()) {
			field.addAnnotation("@Transient");
		}
		for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
			if (introspectedColumn == column) {
				if (introspectedColumn.isStringColumn()) {
					topLevelClass.addImportedType("org.nutz.dao.entity.annotation.EL");
					topLevelClass.addImportedType("org.nutz.dao.entity.annotation.Name");
					topLevelClass.addImportedType("org.nutz.dao.entity.annotation.Prev");
					field.addAnnotation("@Name");
					field.addAnnotation("@Prev(els = @EL(\"uuid()\"))");
				} else {
					topLevelClass.addImportedType("org.nutz.dao.entity.annotation.Id");
					field.addAnnotation("@Id");
				}
				break;
			}
		}
		String column = introspectedColumn.getActualColumnName();
		if (StringUtility.stringContainsSpace(column)
				|| introspectedTable.getTableConfiguration().isAllColumnDelimitingEnabled()) {
			column = introspectedColumn.getContext().getBeginningDelimiter() + column
					+ introspectedColumn.getContext().getEndingDelimiter();
		}
		// 添加@Column注解
		if (!column.equals(introspectedColumn.getJavaProperty())) {
			field.addAnnotation("@Column(hump=true)");
		} else if (StringUtility.stringHasValue(beginningDelimiter)
				|| StringUtility.stringHasValue(endingDelimiter)) {
			field.addAnnotation("@Column(hump=true)");
		} else if (forceAnnotation) {
			// field.addAnnotation("@Column(name = \"" +
			// getDelimiterName(column) +
			// "\",hump=true)");
			field.addAnnotation("@Column(hump=true)");
		}
		// 若从数据库发现主键自增
		if (introspectedColumn.isIdentity()) {
			if (introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement()
					.equals("JDBC")) {
				// field.addAnnotation("@GeneratedValue(generator =
				// \"JDBC\")");
			} else {
				// field.addAnnotation("@GeneratedValue(strategy =
				// GenerationType.IDENTITY)");
			}
		} else if (introspectedColumn.isSequenceColumn()) {
			// 在 Oracle 中,如果需要是 SEQ_TABLENAME,那么可以配置为 select SEQ_{1}
			// from dual
			String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
			String sql = MessageFormat.format(
					introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement(),
					tableName, tableName.toUpperCase());
			field.addAnnotation(
					"@GeneratedValue(strategy = GenerationType.IDENTITY, generator = \"" + sql + "\")");
		}
		return true;
	}

	/**
	 * 生成基础实体类
	 */
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		processEntityClass(topLevelClass, introspectedTable);
		return true;
	}

	/**
	 * 生成实体类注解 KEY 对象
	 */
	@Override
	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		processEntityClass(topLevelClass, introspectedTable);
		return true;
	}
}