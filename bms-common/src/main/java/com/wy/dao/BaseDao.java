package com.wy.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SimpleCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.wy.common.ResultException;
import com.wy.entity.BaseBean;
import com.wy.page.BasePage;
import com.wy.result.Result;
import com.wy.utils.MapUtils;
import com.wy.utils.StrUtils;

/**
 * 基础base类
 * 
 * @author paradiseWy 2018年8月28日
 */
@SuppressWarnings("unchecked")
public abstract class BaseDao<T> {

	@Autowired
	protected Dao dao;

	Class<T> clazz;

	public BaseDao() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		Type type = parameterizedType.getActualTypeArguments()[0];
		clazz = (Class<T>) type;
	}

	public Dao getDao() {
		return dao;
	}

	public Class<T> getClassOfT() {
		return clazz;
	}

	public Entity<T> getEntry() {
		return dao.getEntity(clazz);
	}

	public boolean hasValue(String column, Object value) {
		return hasValue(getEntry().getTableName(), column, value);
	}

	public boolean hasValue(String table, String column, Object value) {
		Sql sql = Sqls.fetchInt("select count(*) from $table where $key = @value")
				.setVar("table", table).setVar("key", StrUtils.hump2Snake(column))
				.setParam("value", value);
		return dao.execute(sql).getInt() == 1;
	}

	/**
	 * 获得泛型在数据库中的排序字段的当前最大排序值,默认排序字段为sort_index
	 * 
	 * @return 最大排序值
	 */
	public int getMaxSort() {
		return getMaxSort(getEntry().getTableName());
	}

	/**
	 * 获得指定数据库表的排序字段的当前最大排序值,默认排序字段为sort
	 * 
	 * @param tableName 表名
	 * @return 最大排序值
	 */
	public int getMaxSort(String tableName) {
		return getMaxSort("sort", tableName);
	}

	/**
	 * 获得指定数据库表的排序字段的当前最大排序值,默认排序字段为sort
	 * 
	 * @param sortColumn 传递的参数必须是全小写或驼峰,此处会做驼峰转下划线处理
	 *            数据库中的排序字段,规范必须是下划线或全小写;
	 * @param tableName 表名
	 * @return 最大排序值
	 */
	public int getMaxSort(String sortColumn, String tableName) {
		Sql sql = Sqls.fetchInt("select max($sortColumn) from $table")
				.setVar("sortColumn", StrUtils.hump2Snake(sortColumn)).setVar("table", tableName);
		return dao.execute(sql).getInt(-1) + 1;
	}

	/**
	 * 执行存储过程
	 * 
	 * @param procName 存储过程名
	 * @param params 参数
	 */
	public List<Map<String, Object>> execProc(String procName, Object... params) {
		StringBuilder sb = new StringBuilder("exec ").append(procName).append(" ");
		for (Object param : params) {
			sb.append(param).append(",");
		}
		return (List<Map<String, Object>>) dao
				.execute(Sqls.create(sb.toString().substring(0, sb.toString().length() - 1))
						.setCallback(Sqls.callback.maps()))
				.getResult();
	}

	/**
	 * 新增数据
	 * 
	 * @param t 实体类数据
	 * @return 新增回显的实体类数据
	 */
	public T add(T t) {
		return dao.insert(t);
	}

	/**
	 * 数据添加排序,实体类中必须有sort字段,数据库中必须有sort
	 * 
	 * @param t 实体类
	 * @return 添加完成后的数据
	 */
	public T addSort(T t) {
		return addSort(t, "sort");
	}

	/**
	 * 自定义数据添加排序,实体类中的字段必须是驼峰,数据库中是下划线,或都是小写
	 * 
	 * @param t 实体类数据
	 * @param sortColumn 自定义排序字段,必须是驼峰格式或全小写
	 * @return 添加后的数据
	 */
	public T addSort(T t, String sortColumn) {
		try {
			Field field = clazz.getDeclaredField(sortColumn);
			// 若排序字段未传值,则进行数据库查询
			if (field.get(t) == null || Integer.parseInt(field.get(t).toString()) <= 0) {
				int sort = getMaxSort(sortColumn, getEntry().getTableName());
				field.setAccessible(true);
				field.set(t, sort);
			}
			return add(t);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException
				| IllegalAccessException e) {
			e.printStackTrace();
			throw new ResultException("添加排序失败");
		}
	}

	public List<T> adds(List<T> entitys) {
		return dao.fastInsert(entitys);
	}

	/**
	 * 根据id删除某一条数据
	 * 
	 * @param id 主键值
	 */
	public int delete(Serializable id) {
		if (Integer.class == id.getClass() || Long.class == id.getClass()) {
			return dao.delete(clazz, Long.parseLong(id.toString()));
		} else if (String.class == id.getClass()) {
			return dao.delete(clazz, id.toString());
		}
		throw new ResultException("primary key type is error");
	}

	/**
	 * 根据主键值批量删除数据
	 * 
	 * @param ids 主键集合
	 */
	public int deletes(List<? extends Serializable> ids) {
		int i = 0;
		for (Serializable id : ids) {
			i += delete(id);
		}
		return i;
	}

	/**
	 * 根据id整体更新某一条记录,如果实体类的字段值为null,则数据库中有值也归更新成null
	 * 
	 * @param entity 实体类
	 */
	public int update(T entity) {
		return dao.update(entity);
	}

	/**
	 * 根据id更新某一条记录指定数据
	 * 
	 * @param id 主键值
	 * @param params 需要更新的值
	 */
	public int updateById(Map<String, Object> params, Serializable id) {
		String columnName = "";
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(Name.class)) {
				columnName = field.getName();
				break;
			}
		}
		if (StrUtils.isBlank(columnName)) {
			throw new ResultException(MessageFormat.format("the primary key of {0} is loss",
					getEntry().getTableName()));
		}
		return dao.update(clazz, getChain(params), Cnd.where(columnName, "=", id));
	}

	/**
	 * 所有数据部分字段更新
	 * 
	 * @param params 需要更新的数据
	 */
	public int updateAll(Map<String, Object> params) {
		return update(params, null);
	}

	/**
	 * 根据条件进行更新,只适合条件相等的值
	 * 
	 * @param params 需要更新的值
	 * @param cnds 条件
	 */
	public int updateCnd(Map<String, Object> params, Map<String, Object> cnds) {
		if (MapUtils.isNotBlank(cnds)) {
			SimpleCriteria cnd = Cnd.cri();
			for (Map.Entry<String, Object> entry : cnds.entrySet()) {
				cnd.where().andEquals(entry.getKey(), entry.getValue());
			}
			return update(params, cnd);
		} else {
			return update(params, null);
		}
	}

	/**
	 * 根据条件进行更新
	 * 
	 * @param params 需要更新的值
	 * @param cnds 条件
	 */
	public int update(Map<String, Object> params, Condition cnd) {
		if (MapUtils.isBlank(params)) {
			return 0;
		}
		return dao.update(clazz, getChain(params), cnd);
	}

	/**
	 * 参数生成chain链式结构
	 * 
	 * @param params 参数
	 */
	private Chain getChain(Map<String, Object> params) {
		if (MapUtils.isBlank(params)) {
			return null;
		}
		// 处理需要更新的值
		Chain chain = null;
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			if (chain == null) {
				chain = Chain.make(entry.getKey(), entry.getValue());
			} else {
				chain.add(entry.getKey(), entry.getValue());
			}
		}
		return chain;
	}

	/**
	 * 批量全字段更新
	 * 
	 * @param entitys 需要更新的全字段数据
	 */
	public int updates(List<T> entitys) {
		int i = 0;
		for (T t : entitys) {
			i += dao.update(t);
		}
		return i;
	}

	public String getStr(String sqlStr) {
		return dao.execute(Sqls.fetchString(sqlStr)).getString();
	}

	public String getStr(Sql sql) {
		return dao.execute(sql.setCallback(Sqls.callback.str())).getString();
	}

	/**
	 * 根据主键值查找数据
	 * 
	 * @param id 主键值
	 */
	public T getById(Serializable id) {
		if (Integer.class == id.getClass() || Long.class == id.getClass()) {
			return dao.fetch(clazz, Long.parseLong(id.toString()));
		} else {
			return dao.fetch(clazz, id.toString());
		}
	}

	/**
	 * 根据原生sql获得单条记录
	 */
	public Map<String, Object> getMap(String sqlStr) {
		return getMap(Sqls.create(sqlStr));
	}

	public Map<String, Object> getMap(Sql sql) {
		return (Map<String, Object>) dao.execute(sql.setCallback(Sqls.callback.map())).getResult();
	}

	/**
	 * 根据原生sql获得全部记录,不分页
	 */
	public List<Map<String, Object>> getMaps(String sqlStr) {
		return getMaps(Sqls.create(sqlStr));
	}

	public List<Map<String, Object>> getMaps(Sql sql) {
		return (List<Map<String, Object>>) dao.execute(sql.setCallback(Sqls.callback.maps()))
				.getResult();
	}

	/**
	 * 单表查询,无参数查询;有分页参数则分页查询,无分页参数则不分页查询
	 * 
	 * @param entity 单表实体类
	 */
	public Result getList(BaseBean<T> entity) {
		if (entity.hasPage()) {
			return Result.page(
					dao.query(clazz, null, new Pager(entity.getPageIndex(), entity.getPageSize())),
					entity.getPageIndex(), entity.getPageSize(), dao.count(clazz));
		} else {
			return Result.result(dao.query(clazz, null));
		}
	}

	/**
	 * 单表查询,有参数,但是只能查询参数相等的条件;有分页参数则分页查询,若无分页参数则不分页查询
	 * 
	 * @param entity 单表实体类
	 */
	public Result getListArgs(BaseBean<T> entity) {
		Field[] fields = getClass().getDeclaredFields();
		if (fields.length == 0) {
			throw new ResultException(
					MessageFormat.format("entity {0} don't have columns", clazz.getName()));
		}
		SimpleCriteria cri = Cnd.cri();
		for (Field field : fields) {
			field.setAccessible(true);
			if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			try {
				Object value = field.get(entity);
				if (Objects.isNull(value)) {
					continue;
				}
				cri.where().andEquals(field.getName(), value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if (entity.hasPage()) {
			return Result.page(
					dao.query(clazz, cri, new Pager(entity.getPageIndex(), entity.getPageSize())),
					entity.getPageIndex(), entity.getPageSize(), dao.count(clazz, cri));
		} else {
			return Result.result(dao.query(clazz, cri));
		}
	}

	/**
	 * 联表分页查询,参数可有可无;有分页参数则查分页,若无分页参数则查全部数据
	 * 
	 * @param page 过滤参数
	 */
	public Result getPages(BasePage page) {
		int total = 0;
		SimpleCriteria criteria = Cnd.cri();
		page.addCnds(dao, criteria.where());
		page.addGroup(criteria.getGroupBy());
		if (page.hasPage()) {
			Sql dataSql = Sqls.create("select $columns from $table $condition")
					.setVar("columns", page.addColumns()).setVar("table", page.addTables())
					.setCondition(criteria);
			Sql countSql = Sqls.fetchInt("select count(*) from ($dataSql) a ")
					.setVar("dataSql", dataSql.toString()).setCondition(criteria);
			criteria.setPager(new Pager(page.getPageIndex(), page.getPageSize()));
			total = dao.execute(countSql).getInt(0);
			if (total <= 0) {
				return Result.page(null, page.getPageIndex(), page.getPageSize(), 0);
			}
		}
		page.addOrder(criteria.getOrderBy());
		Sql dataSql = Sqls.create("select $columns from $table $condition")
				.setVar("columns",
						StrUtils.isNotBlank(page.addColumns()) ? handleColumns(page.addColumns())
								: page.addSpecialColumns())
				.setVar("table", page.addTables()).setCondition(criteria);
		if (criteria.getPager() != null) {
			dataSql.setPager(criteria.getPager());
		}
		return Result.page(getMaps(dataSql), page.getPageIndex(), page.getPageSize(), total);
	}

	/**
	 * 对驼峰字段进行处理,变成下划线 驼峰别名
	 * @param columns 字段集合
	 * @return 字段字符串
	 */
	private String handleColumns(String columns) {
		String[] src = columns.split(",");
		List<String> des = new ArrayList<>();
		for (String column : src) {
			des.add(MessageFormat.format("{0} {1}", StrUtils.hump2Snake(column),
					column.contains(".") ? column.substring(column.indexOf(".") + 1) : column));
		}
		return String.join(",", des);
	}
}