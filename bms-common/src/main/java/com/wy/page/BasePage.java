package com.wy.page;

import org.nutz.dao.Dao;
import org.nutz.dao.sql.GroupBy;
import org.nutz.dao.sql.OrderBy;
import org.nutz.dao.util.cri.SimpleCriteria;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.wy.entity.BaseBean;

/**
 * 多表联合查询分页
 * @author paradiseWy 2018年8月28日
 */
public abstract class BasePage extends BaseBean<Object> {

	private static final long serialVersionUID = 1L;
	
	public abstract String addColumns();

	public abstract String addTables();

	public abstract void addCnds(Dao dao, SqlExpressionGroup where);

	public abstract void addGroup(GroupBy group);

	public abstract void addOrder(OrderBy order);

	public void addPager(SimpleCriteria cri) {
		cri.setPager(getPageIndex(), getPageSize());
	}
}