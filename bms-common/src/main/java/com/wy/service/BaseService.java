package com.wy.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.wy.dao.BaseDao;
import com.wy.entity.BaseBean;
import com.wy.page.BasePage;
import com.wy.result.Result;

/**
 * service公共类
 * 
 * @author paradiseWy 2018年8月28日
 */
public abstract class BaseService<T> {

	public abstract BaseDao<T> getDao();

	@Transactional
	public T add(T t) {
		return getDao().add(t);
	}

	@Transactional
	public T addSort(T t) {
		return getDao().addSort(t);
	}

	@Transactional
	public T addSort(T t, String sortColumn) {
		return getDao().addSort(t, sortColumn);
	}

	@Transactional
	public List<T> adds(List<T> ts) {
		return getDao().adds(ts);
	}

	@Transactional
	public int delete(Serializable id) {
		return getDao().delete(id);
	}

	@Transactional
	public int deletes(List<? extends Serializable> ids) {
		return getDao().deletes(ids);
	}

	@Transactional
	public int update(T t) {
		return getDao().update(t);
	}

	@Transactional
	public int updates(List<T> ts) {
		return getDao().updates(ts);
	}

	@Transactional
	public int update(Map<String, Object> params) {
		return getDao().updateAll(params);
	}

	public boolean hasValue(String column, String value) {
		return getDao().hasValue(column, value);
	}

	public T getById(Serializable id) {
		return getDao().getById(id);
	}

	public Result getList(BaseBean<T> bean) {
		return getDao().getList(bean);
	}

	public Result getListArgs(BaseBean<T> bean) {
		return getDao().getListArgs(bean);
	}

	public Result getPages(BasePage bean) {
		return getDao().getPages(bean);
	}
}