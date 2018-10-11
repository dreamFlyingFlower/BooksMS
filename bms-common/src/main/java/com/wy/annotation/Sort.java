package com.wy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在basebean中检测是否有排序字段;本注解在{@link com.wy.entity.BaseBean}中使用
 * 
 * @author paradiseWy
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Sort {

	/**
	 * 若是为空字符串,则默认排序字段为sort,
	 * 若不为空则以value值作为排序字段,且value值代表的字段是java实体类字段,非数据库字段
	 */
	String value() default "";
}