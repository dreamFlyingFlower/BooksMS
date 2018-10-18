package com.wy.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.annotation.CheckAdd;
import com.wy.annotation.CheckUpdate;
import com.wy.annotation.Sort;
import com.wy.enums.TipsEnum;
import com.wy.utils.StrUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 实体类以及分页总类
 * 
 * @attention 分页参数会在分页请求里使用,其他方法会在{@link com.wy.crl.BaseCrl}里使用
 * 
 * @author paradiseWy 2018年8月28日
 */
@Setter
@Getter
public class BaseBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 在实体类中不输出pageIndex和pageSize,只在结果集最外围输出
	 */
	@JSONField(serialize = false)
	private int pageIndex = -1;
	@JSONField(serialize = false)
	private int pageSize;
	
	public boolean validAdd() {
		return true;
	}

	/**
	 * 通用controller使用该方法检查参数必传参数,检查是否需要排序
	 */
	public Map<String,String> validAdd(T t) {
		Class<? extends Object> clazz = t.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Map<String,String> result = new HashMap<>();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				if (field.isAnnotationPresent(CheckAdd.class)) {
					if (Objects.isNull(field.get(t))) {
						result.put("checkErr", TipsEnum.TIP_PARAM_EMPTY.getTips(field.getName()));
						return result;
					}
				}
				if(field.isAnnotationPresent(Sort.class)) {
					Sort sort = field.getAnnotation(Sort.class);
					if(StrUtils.isNotBlank(sort.value())) {
						result.put("sort", sort.value());
					}else {
						result.put("sort", "sort");
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean validUpdate() {
		return true;
	}

	/**
	 * 通用controller使用该方法检查全参修改必传参数
	 */
	public String validUpdate(T t) {
		Class<? extends Object> clazz = t.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(CheckAdd.class) || field.isAnnotationPresent(CheckUpdate.class)) {
				field.setAccessible(true);
				try {
					if (Objects.isNull(field.get(t))) {
						return TipsEnum.TIP_PARAM_EMPTY.getTips(field.getName());
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 判断是否需要分页
	 */
	public boolean hasPage() {
		return pageIndex >= 0 && pageSize > 0;
	}
}