package com.wy.dao;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.nutz.dao.Sqls;
import org.springframework.stereotype.Repository;

import com.wy.common.ResultException;
import com.wy.entity.Dic;
import com.wy.utils.ListUtils;

@Repository
public class DicDao extends BaseDao<Dic> {

	@Override
	public Dic add(Dic entity) {
		if (hasValue("dicCode", entity.getDicCode())) {
			throw new ResultException(
					MessageFormat.format("字典编码{0}已经被占用,请更换", entity.getDicCode()));
		}
		if (entity.getSort() == null || entity.getSort() <= 0) {
			entity.setSort(getMaxSort());
		}
		return dao.insert(entity);
	}

	/**
	 * 根据父级编号获得父级和直接子级的数据
	 * @param id 父级编号
	 * @return 父级和子级数据
	 */
	public Map<String, Object> getPc(int id) {
		String sql = "select a.dic_id dicId,a.dic_name dicName,a.dic_code dicCode,"
				+ "a.parent_id parentId,a.sort,b.dic_name parentName "
				+ " from ti_dictionary a left join ti_dictionary b on a.parent_id = b.dic_id where a.dic_id = @id";
		return getMap(Sqls.create(sql).setParam("id", id));
	}

	/**
	 * 根据父级dicCode获取直接子级数据
	 * @param dicCode 父级dicCode
	 * @return 直接子级的集合
	 */
	public List<Map<String, Object>> getChildren(String dicCode) {
		String sql = "select dic_id dicId,dic_name dicName,dic_code dicCode from ti_dictionary "
				+ "where parent_id in (select dic_id from ti_dictionary where dic_code = @dicCode) order by sort";
		return getMaps(Sqls.create(sql).setParam("dicCode", dicCode));
	}

	/**
	 * 根据父级编号获得直接子级数据
	 * @param id 父级编号
	 * @return 直接子级数据
	 */
	private List<Map<String, Object>> getChildren(int id) {
		String sql = "select b.dic_id dicId,b.dic_name dicName,b.dic_code dicCode,"
				+ "(select count(*) from ti_dictionary a where a.parent_id = b.dic_Id) childNum "
				+ " from ti_dictionary b where b.parent_id = @id order by b.sort";
		return getMaps(Sqls.create(sql).setParam("id", id));
	}

	/**
	 * 获得字段树形结构
	 * @param id 父级编号
	 * @return 树形结构树
	 */
	public List<Map<String, Object>> getTrees(int id) {
		List<Map<String, Object>> maps = getChildren(id);
		addChild(maps);
		return maps;
	}

	private void addChild(List<Map<String, Object>> maps) {
		if (ListUtils.isBlank(maps)) {
			return;
		}
		for (Map<String, Object> map : maps) {
			if (!Objects.isNull(map.get("childNum"))
					&& Integer.parseInt(map.get("childNum").toString()) > 0) {
				List<Map<String, Object>> childs = getChildren((Integer) map.get("dicId"));
				addChild(childs);
				map.put("children", childs);
			}
		}
	}
}