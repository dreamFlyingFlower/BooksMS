package com.hbdh.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.nutz.dao.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbdh.dao.MenuDao;
import com.hbdh.model.Menu;
import com.hbdh.model.Role;
import com.hbdh.utils.ListUtils;

@Service
public class MenuService extends BaseService<Menu> {

	@Autowired
	private MenuDao dao;

	public MenuDao getDao() {
		return dao;
	}

	@Override
	public Map<String, Object> getById(Serializable id) {
		String sql = "select a.*,b.menuName parentName from ti_menu a left join ti_menu "
				+ " b on a.parentId = b.menuId where a.menuId = @Id";
		return dao.getMap(Sqls.create(sql).setParam("id", id));
	}

	public List<Map<String, Object>> getChildren(int id) {
		String sql = "select b.menuId,b.menuName,b.menuIcon,(select count(*) from"
				+ " ti_menu a where a.parentId = b.menuId) childNum from ti_menu b"
				+ " where b.parentId = @id order by b.menuId";
		return dao.getMaps(Sqls.create(sql).setParam("id", id));
	}

	public List<Map<String, Object>> getTree(int id) {
		List<Map<String, Object>> menus = getChildren(id);
		if (menus == null || menus.isEmpty()) {
			return null;
		}
		addChildMenu(menus);
		return menus;
	}

	private void addChildMenu(List<Map<String, Object>> maps) {
		for (Map<String, Object> map : maps) {
			int menuId = Integer.parseInt(map.get("menuId").toString());
			if ((Integer) map.remove("childNum") > 0) {
				List<Map<String, Object>> childs = getChildren(menuId);
				addChildMenu(childs);
				map.put("children", childs);
			}
		}
	}

	/**
	 * 根据菜单id和userid查询当前用户的菜单树
	 * @param menuId 菜单编号
	 * @param userId 用户编号
	 * @return 结果集
	 */
	public List<Map<String, Object>> getUserMenu(int menuId, int userId) {
		Role role = getBaseDao()
				.execute(Sqls
						.fetchEntity(new StringBuffer("select c.* from ti_user a ")
								.append(" inner join tr_userRole b on b.userId = a.userId ")
								.append(" inner join ti_role c on c.roleId = b.roleId ")
								.append(" where a.userId = @userId").toString())
						.setEntity(getBaseDao().getEntity(Role.class)).setParam("userId", userId))
				.getObject(Role.class);
		boolean isAdmin = false;
		if (0 == role.getRoleType()) {
			isAdmin = true;
		}
		List<Map<String, Object>> maps = getChildrenByUserId(menuId, userId, isAdmin);
		addMenus(maps, null, userId, isAdmin);
		return maps;
	}

	/**
	 * 根据菜单id和userId查询当前用户的子菜单列表
	 */
	private List<Map<String, Object>> getChildrenByUserId(int parentId, int userId,
			boolean isAdmin) {
		if (isAdmin) {
			return dao.getMaps(Sqls.create(
					new StringBuilder("select b.menuId,b.menuName,b.menuUrl,b.menuIcon,1 childNum ")
							.append(" from ti_menu b where parentId = @parentId").toString())
					.setParam("parentId", parentId));
		} else {
			String sql = new StringBuilder("select b.menuId,b.menuName,b.menuUrl,b.menuIcon,")
					.append("(select count(*) from ti_menu a where a.parentId= b.menuId) childNum ")
					.append(" from ti_menu b where b.parentId = @parentId and b.menuId in ")
					.append("(select t3.menuId from ti_user t1 inner join tr_userRole t2 on t1.userId = t2.userId ")
					.append(" left join tr_roleMenu t3 on t3.roleId = t2.roleId ")
					.append(" where t1.userId = @userId) order by b.menuId ").toString();
			return dao.getMaps(
					Sqls.create(sql).setParam("parentId", parentId).setParam("userId", userId));
		}
	}

	public List<Map<String, Object>> getRouter(int id) {
		List<Map<String, Object>> maps = getChildren(id);
		addMenus(maps, null, 0, true);
		return maps;
	}

	/**
	 * 递归获得下级子菜单
	 * @param maps 上一个结果集
	 * @param parentName 上级名
	 * @param userId 当大于0的时候表示需要根据userid所需权限查询
	 */
	private void addMenus(List<Map<String, Object>> maps, String parentName, int userId,
			boolean isAdmin) {
		if (maps == null || maps.isEmpty()) {
			return;
		}
		for (Map<String, Object> map : maps) {
			// 添加前端所需路径
			List<Object> desMeta = new ArrayList<>();
			if (parentName != null) {
				desMeta.add(parentName);
			}
			desMeta.add(map.get("menuName"));
			// 添加前端所需meta
			Map<String, List<Object>> title = new HashMap<>();
			title.put("title", desMeta);
			map.put("meta", title);
			// 是否有子菜单或子按钮
			if (Integer.parseInt(Objects.toString(map.get("childNum"))) > 0) {
				// 子菜单和子按钮递归查找
				List<Map<String, Object>> childs = new ArrayList<>();
				if (0 == userId) {
					// 0表示不需要关联用户权限信息
					childs = getChildren(Integer.parseInt(String.valueOf((map.get("menuId")))));
					addMenus(childs, String.valueOf(map.get("menuName")), 0, isAdmin);
				} else {
					childs = getChildrenByUserId(
							Integer.parseInt(String.valueOf((map.get("menuId")))), userId, isAdmin);
					addMenus(childs, String.valueOf(map.get("menuName")), userId, isAdmin);
				}
				List<Map<String, Object>> childMenu = new ArrayList<>();
				map.put("children", childMenu);
				if (ListUtils.isNotBlank(childs)) {
					childMenu.addAll(childs);
					// 添加前端所需redirect
					map.put("redirect", childMenu.get(0).get("menuUrl"));
				}
			}
		}
	}

	// public List<Map<String, Object>> getUserMenu(int userId)
	// {
	// String parentId = getStr(
	// Sqls.create("select MenuID from ti_Menu where ParentID =
	// '0' and MenuType = 1"));
	// List<Map<String, Object>> menus = getChildMenu(isAdmin,
	// userId, parentId);
	// if (menus == null || menus.isEmpty())
	// return null;
	// addChildMenu(isAdmin, userId, menus);
	// return menus;
	// }
	//
	// private void addChildMenu(boolean isAdmin, String userId,
	// List<Map<String, Object>> maps) {
	// for (Map<String, Object> map : maps) {
	// String menuId = (String) map.get("MenuID");
	// if ((Integer) map.remove("BtnNum") > 0)
	// map.put("Button", getMenuButton(isAdmin, userId,
	// menuId));
	// if ((Integer) map.remove("ChildNum") > 0) {
	// List<Map<String, Object>> childs = getChildMenu(isAdmin,
	// userId, menuId);
	// addChildMenu(isAdmin, userId, childs);
	// map.put("Child", childs);
	// }
	// }
	// }
	//
	// private List<Map<String, Object>> getChildMenu(boolean
	// isAdmin, String uid, String mid) {
	// List<Map<String, Object>> menus;
	// if (isAdmin) {
	// String sql = "select
	// a.MenuID,a.MenuName,a.NavigateUrl,(select count(*) from"
	// + " ti_Button b where b.MenuID = a.MenuID) BtnNum,(select
	// count(*)"
	// + " from ti_Menu c where c.ParentID = a.MenuID) ChildNum
	// from ti_Menu"
	// + " a where a.ParentID = @MID order by a.SortIndex";
	// menus = getMaps(Sqls.create(sql).setParam("MID", mid));
	// } else {
	// String sql = "select
	// a.MenuID,a.MenuName,a.NavigateUrl,(select count(*) from"
	// + " ti_Button d where d.MenuID = a.MenuID) BtnNum,(select
	// count(*) from"
	// + " ti_Menu e where e.ParentID = a.MenuID) ChildNum from
	// ti_Menu a inner"
	// + " join tr_Role_Menu b on a.MenuID = b.MenuID inner join
	// tr_User_Role c"
	// + " on b.RoleID = c.RoleID where c.UserID = @UID and
	// a.ParentID = @MID"
	// + " order by a.SortIndex";
	// menus = getMaps(Sqls.create(sql).setParam("UID",
	// uid).setParam("MID", mid));
	// }
	// return menus;
	// }
	//
	// private List<Map<String, Object>> getMenuButton(boolean
	// isAdmin, String uid, String mid) {
	// List<Map<String, Object>> buttons;
	// if (isAdmin) {
	// String sql = "select ButtonID,BtnName,BtnType,1 IsCheck
	// from ti_Button"
	// + " where MenuID = @MID order by SortIndex";
	// buttons = getMaps(Sqls.create(sql).setParam("MID", mid));
	// } else {
	// String sql = "select
	// a.ButtonID,a.BtnName,a.BtnType,ISNULL((select 1"
	// + " from tr_Role_Button b inner join tr_User_Role c on
	// c.RoleID"
	// + " = b.RoleID where b.ButtonID = a.ButtonID and c.UserID
	// ="
	// + " @UID),0) IsCheck from ti_Button a where a.MenuID =
	// @MID"
	// + " order by a.SortIndex";
	// buttons = getMaps(Sqls.create(sql).setParam("UID",
	// uid).setParam("MID", mid));
	// }
	// return buttons;
	// }
}