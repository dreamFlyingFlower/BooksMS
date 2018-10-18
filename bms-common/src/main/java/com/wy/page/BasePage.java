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

	/**
	 * 添加字段,添加的字段不能做特殊的计算,如sum,avg,concat或者带*等,后续会对字段进行处理,将驼峰转下划线
	 * @attention addColumns和addSpecialColumns只会使用一个,先检查addColumns方法,
	 *            若不使用,返回null或空字符串,将会使用addSpecialColumns方法
	 * @return 需要查询的驼峰字段
	 */
	public abstract String addColumns();

	/**
	 * 添加字段,原样添加到sql语句中,注意该方法不会对驼峰字段进行转化为下划线字段,要对照数据库中字段填写
	 * @return 需要查询的原样数据库字段
	 */
	public abstract String addSpecialColumns();

	/**
	 * 添加链接的表
	 * @return 链接的表
	 */
	public abstract String addTables();

	/**
	 * 查询的条件
	 * @param dao 数据库底层操作
	 * @param where 查询条件
	 */
	public abstract void addCnds(Dao dao, SqlExpressionGroup where);

	/**
	 * 分组条件
	 * @param group
	 */
	public abstract void addGroup(GroupBy group);

	/**
	 * 排序添加
	 * @param order
	 */
	public abstract void addOrder(OrderBy order);

	public void addPager(SimpleCriteria cri) {
		cri.setPager(getPageIndex(), getPageSize());
	}
}