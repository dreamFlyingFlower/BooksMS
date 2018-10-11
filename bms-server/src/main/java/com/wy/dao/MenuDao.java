package com.wy.dao;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.nutz.dao.Sqls;
import org.springframework.stereotype.Repository;

import com.wy.entity.Menu;
import com.wy.utils.ListUtils;

@Repository
public class MenuDao extends BaseDao<Menu> {

	/**
	 * 根据菜单编号拿到子菜单
	 * 
	 * @param id 菜单父级编号
	 */
	public List<Map<String, Object>> getChildMenus(int id) {
		String sql = "select a.menu_id menuId,a.menu_name menuName,a.menu_url menuUrl,menu_icon menuIcon,"
				+ "(select count(*) from ti_menu where a.parent_id = menu_id) childNum "
				+ "from ti_menu a where a.parent_id = @id order by a.sort";
		return getMaps(Sqls.create(sql).setParam("id", id));
	}

	/**
	 * 根据菜单编号获得菜单树
	 * 
	 * @param id 菜单编号
	 */
	public List<Map<String, Object>> getTreeMenu(int id) {
		List<Map<String, Object>> maps = getChildMenus(id);
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
				List<Map<String, Object>> childs = getTreeMenu(
						Integer.parseInt(map.get("menuId").toString()));
				addChild(childs);
				map.put("children", childs);
			}
		}
	}
}