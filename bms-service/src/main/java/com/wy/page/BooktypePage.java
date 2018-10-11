package com.wy.page;

import org.nutz.dao.Dao;
import org.nutz.dao.sql.GroupBy;
import org.nutz.dao.sql.OrderBy;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.lang.Strings;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义参数分页,查询符合条件的用户及相关信息
 * 
 * @author paradiseWy 2018年8月28日
 */
@Getter
@Setter
public class BooktypePage extends BasePage {
	
	private static final long serialVersionUID = 1L;
	private String booktypeName;

	@Override
	public String addColumns() {
		return " * ";
	}

	@Override
	public String addTables() {
		return " tb_booktype ";
	}

	@Override
	public void addCnds(Dao dao, SqlExpressionGroup where) {
		if(Strings.isNotBlank(booktypeName)) {
			where.andLike(" booktype_name ", booktypeName);
		}
	}

	@Override
	public void addGroup(GroupBy group) {
	}
	
	@Override
	public void addOrder(OrderBy order) {
		order.asc(" booktype_id ");
	}
}